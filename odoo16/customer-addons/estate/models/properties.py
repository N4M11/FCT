from odoo import fields, models, api
from datetime import *
from dateutil.relativedelta import *
from odoo.exceptions import UserError
# posar algun botó per a tornar a habilitar oferta, com posar-la com a new

class Properties(models.Model):
    _name = "estate.property"
    _description = "Estate properties"
    _order = "id desc"

    name=fields.Char(required=True, default="Unknown")
    description=fields.Text()
    postcode=fields.Char()

    NOW = datetime.now()
    date_availability=fields.Date(copy=False ,default=NOW+relativedelta(months=+3), required=False)
    expected_price=fields.Float(required=True)
    selling_price=fields.Float(compute="_compute_selling_price",readonly=True,store=True)
    @api.depends('offer_ids.status')
    def _compute_selling_price(self):
        for record in self:
            accepted_offer = record.offer_ids.filtered(
                lambda o: o.status == 'accepted'
            )
            record.selling_price = accepted_offer.price if accepted_offer else False

    bedrooms=fields.Integer(default=2)
    living_area=fields.Integer(default=0)
    facades=fields.Integer()
    garage=fields.Boolean(default=False)

    garden=fields.Boolean(default=False)
    @api.onchange('garden')
    def _onchange_garden(self):
        if self.garden:
            self.garden_area = 10
            self.garden_orientation = "north"
        else:
            self.garden_area = 0
            self.garden_orientation = False

    garden_area=fields.Integer()
    garden_orientation=fields.Selection(
        string='Garden Orientation',
        selection=[('north', 'North'),('south', 'South'),('east', 'East'),('west','West')]
    )
    active=fields.Boolean(default=True)

    status=fields.Selection(
        string='Status',
        selection=[('new', 'New'),('received','Offer Received'),('accepted','Offer Accepted'),('sold', 'Sold'),('canceled','Canceled')],
        required=True, copy=False,
        default='new',
        compute='_compute_status',
        store=True
    )
    @api.depends('offer_ids.status')
    def _compute_status(self):
        for record in self:
            if not record.offer_ids:
                record.status = 'new'
            elif any(offer.status == 'accepted' for offer in record.offer_ids):
                record.status = 'accepted'
            elif record.offer_ids:
                record.status = 'received'

    offer_ids=fields.One2many("estate.property.offer","property_id",string="Offers")
    @api.depends('offer_ids')
    def _compute_offer_received(self):
        for record in self:
            if record.offer_ids and record.status == 'new':
                record.status = 'received'

    best_offer = fields.Float(compute="_compute_best_offer", string="Best Offer")
    @api.depends('offer_ids.price')
    def _compute_best_offer(self):
        for record in self:
            if record.offer_ids:
                record.best_offer = max(record.offer_ids.mapped('price'))
            else:
                record.best_offer = 0.0


    def action_sold(self):
        for record in self:
            if record.status=='canceled':
                raise UserError("No se puede marcar una venta cancel·lada como vendida.")
            bought = self.search([
                ('status', '=', 'accepted'),
            ], limit=1)
            if bought:
                record.status='sold'
            else:
                raise UserError("Comprador no especificado. No se ha aceptado ninguna oferta.")


    def action_cancel(self):
        for record in self:
            if record.status=='sold':
                raise UserError("Propiedad ya vendida. No se puede marcar una venta como cancelada.")
            record.status='canceled'


    property_type_id = fields.Many2one("estate.property.type", string="Property Type")
    salesperson=fields.Many2one("res.users",default=lambda self: self.env.user)
    buyer=fields.Many2one("res.partner",string="Buyer",compute="_compute_buyer")
    @api.depends('offer_ids.price', 'offer_ids.status', 'offer_ids.partner_id', 'status')
    def _compute_buyer(self):
        for record in self:

            accepted_offer = record.offer_ids.filtered(
                lambda o: o.status == 'accepted'
            )
            record.buyer = accepted_offer[0].partner_id if accepted_offer else False


    property_tag_id = fields.Many2many("estate.property.tag", string="Property Tag")


    total_area=fields.Integer(compute="_total")
    @api.depends('living_area','garden_area')
    def _total(self):
        for record in self:
            record.total_area=record.living_area+record.garden_area


    # no es pugui eliminar si el state no es nou o cancelat
    def unlink(self):
        for record in self:
            if record.status not in('new','canceled'):
                raise UserError('You cannot delete a property which status is not new or canceled.')
        return super(Properties,self).unlink()

    _sql_constraints = [
        ('check_expected_price','CHECK(expected_price>=0 AND selling_price>=0)',
         'El precio no puede ser un valor negativo'),

        ('check_selling_price',
         'CHECK(selling_price =0 OR expected_price =0 OR selling_price >= expected_price * 0.90)',
         'El precio de venta no puede ser menor al 90% del precio esperado')
    ]











