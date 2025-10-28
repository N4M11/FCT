# -*- coding: utf-8 -*-

from odoo import models, fields, api, Command
from datetime import *
from odoo.exceptions import UserError, ValidationError


class Reparation(models.Model):
    _name = 'workshop.reparation'
    _inherit = ['mail.thread','mail.activity.mixin']
    _description = 'Devices reparations specifications'

    reparation_tag_id = fields.Many2many("workshop.reparation.tag", string="Reparation Tag")
    timesheet_ids = fields.One2many("workshop.reparation.timesheet", "reparation_id", string="Timesheet", tracking=True)
    device_ids=fields.One2many("workshop.reparation.device","reparation_id",string="Device")
    line_ids = fields.One2many("workshop.reparation.line","reparation_id",string="Line", tracking=True)

    name = fields.Char(readonly=True, store=True)
    @api.model
    def create(self, vals):
        vals['name'] = self.env['ir.sequence'].next_by_code('reparation.name')
        return super(Reparation, self).create(vals)


    NOW = datetime.now()
    today = fields.Date(default=NOW)
    received_date = fields.Date(required=True, default=NOW)
    reception_date = fields.Date(tracking=True)
    # computed --> quan status=delivered pero es podrá modificar
    delivery_date = fields.Date(tracking=True)

    # crear vista de form amb el boolean que conecti amb aquest modul (workshoop_client) i aqui sempre sigui true
    client = fields.Many2one("res.partner", string="Client", required=True, domain=lambda self: self._compute_client_domain())
    def _compute_client_domain(self):
        return [('workshop_client', '=', True)]


    problem_description = fields.Char(required=True, tracking=True)
    solution_description = fields.Char(tracking=True)
    observations = fields.Char(tracking=True)

    estate = fields.Selection(
        selection=[('closed', 'Closed'), ('open', 'Open')],
        store=True,
        copy=False,
        default='open'
    )

    status = fields.Selection(
        string="Status",
        selection=[('new', 'New'), ('repairing', 'Repairing'), ('repaired', 'Repaired'), ('delivered', 'Delivered'),
                   ('canceled', 'Canceled')],
        default='new',
        required=True, copy=False,
        store=True,
        tracking=True,
        group_expand="_read_group_status_ids",
        readonly=True
    )

    @api.model
    def _read_group_status_ids(self, status_values, domain, order):
        return [key for key, val in self._fields['status'].get_description(self.env)['selection']]


    # invisible -> es sumará a la factura com a má d'obra
    # temps de reparació total*30
    cost = fields.Float(compute="_compute_cost")

    @api.depends('timesheet_ids')
    def _compute_cost(self):
        for record in self:
            if record.timesheet_ids:
                total_time = sum(self.mapped('timesheet_ids.time'))
                record.cost=total_time*30
                print(total_time)

    # data d'entrega esperada
    expected_completion_date = fields.Date(store=True, tracking=True)
    completion_date = fields.Date(default=NOW, store=True, tracking=True)

    technician = fields.Many2one("hr.employee", default=lambda self: self.env.user, tracking=True)

    # botons
    def action_repair(self):
        for record in self:
            new = self.search([
                ('status', '=', 'new')
            ])

            if not record.device_ids:
                raise UserError("No device introduced")
            if not record.expected_completion_date:
                raise UserError("Expected completion date missing")

            if new:
                record.status = 'repairing'
            else:
                raise UserError("Status error. It should be 'new'")

    def action_cancel(self):
        for record in self:
            record.status = 'canceled'
            record.estate='closed'

    def action_repaired(self):
        for record in self:
            if not record.solution_description:
                raise UserError("Solution description is required")
            elif not record.timesheet_ids:
                raise UserError("No record in timesheet")

            stock_moves = self.env['stock.move']
            location_id = self.env.ref('stock.stock_location_stock').id
            location_dest_id = self.env.ref(
                'stock.stock_location_customers').id

            for line in record.line_ids:
                if line.product_id.type != 'product':
                    continue

                if line.quantity <= 0:
                        raise UserError(f"Insufficient stock for {line.product_id.name}")

                available_qty = line.product_id.qty_available - line.product_id.outgoing_qty

                if available_qty < line.quantity:
                    raise UserError(
                        f"Insufficient stock for {line.product_id.name}\n"
                        f"Required: {line.quantity}\n Available: {available_qty}"
                    )

                stock_moves |= self.env['stock.move'].create({
                    'name': f"RWS-{record.name}: {line.product_id.name}",
                    'product_id': line.product_id.id,
                    'product_uom_qty': line.quantity,
                    'product_uom': line.product_id.uom_id.id,
                    'location_id': location_id,
                    'location_dest_id': location_dest_id,
                    'origin': record.name,
                })


            stock_moves._action_confirm()
            stock_moves._action_done()

            record.status = 'repaired'


    ####################################

    def action_delivered(self):
        for record in self:
            if record.delivery_date:
                record.status = 'delivered'
            else:
                raise UserError("Delivery date is not set")



    def action_reopen(self):
        self.estate = 'open'
        self.status = 'repairing'

    def action_close(self):
        for record in self:
            if not record.reception_date:
                raise UserError("Reception date is not set")
            else:
                record.estate = 'closed'

    def action_invoice(self):

        record = self[0]

        reparation_product = self.env.ref('workshop.product_workshop_reparation')

        if not reparation_product:
            raise UserError("Product 'Reparation' not found")

        journal = self.env['account.journal'].search([
            ('type', '=', 'sale'),
            ('company_id', '=', self.env.company.id)
        ], limit=1)

        if not journal:
            raise UserError("Invoice journal not found")


        invoice = self.env["account.move"].create({
            "partner_id": record.client.id,
            "workshop_invoice": True,
            "move_type": "out_invoice",
            "journal_id": journal.id,
            "invoice_date": record.NOW,
            "invoice_line_ids": [
                # REPARACIÓ
                Command.create({
                    "name": "Reparation",
                    "quantity": 1,
                    "price_unit": record.cost,
                    "product_id": reparation_product.id,
                    "tax_ids": [(6, 0, [])]  # impostos (0)
                }),
                # MATERIALS
                *[
                    Command.create({
                        "name": line.product_id.name,
                        "quantity": line.quantity,
                        "price_unit": line.price,
                        "product_id": line.product_id.id,
                        "tax_ids": [(6, 0, [])]  # impostos (0)
                    })
                    for line in self.line_ids
                    if line.product_id and line.quantity > 0
                ]

            ],
        })

        return {
            'type': 'ir.actions.act_window',
            'res_model': 'account.move',
            'res_id': invoice.id,
            'views': [(False, 'form')],
            'view_mode': 'form',
            'target': 'current',
        }

    @api.constrains('expected_completion_date')
    def _check_completion_date(self):
        for record in self:
            if not record.expected_completion_date:
                continue
            input_date = fields.Date.to_date(record.expected_completion_date)
            today = date.today()
            if input_date < today:
                raise ValidationError('Expected completion date must be greater or equal to the actual date')

    @api.constrains('delivery_date')
    def _check_delivery_date(self):
        for record in self:
            if not record.delivery_date:
                continue
            input_date = fields.Date.to_date(record.delivery_date)
            received_d= fields.Date.to_date(record.received_date)
            if input_date < received_d:
                raise ValidationError("Delivery date can't be anterior to received date")

    @api.constrains('reception_date')
    def _check_reception_date(self):
        for record in self:
            if not record.reception_date:
                continue
            input_date = fields.Date.to_date(record.reception_date)
            reception_d = fields.Date.to_date(record.delivery_date)
            if input_date < reception_d:
                raise ValidationError("Reception date can't be anterior to delivery date")

    @api.constrains('device_ids')
    def _check_device_count(self):
        for record in self:
            if len(record.device_ids) > 1:
                raise ValidationError("Only one device for reparation")

    _sql_constraints = [
        ("check_completion_date", "CHECK(completion_date IS NOT NULL)",
         "Completion date is not set"),

    ]
