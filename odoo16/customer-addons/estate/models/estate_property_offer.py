from odoo import fields, models, api
from datetime import *; from dateutil.relativedelta import *
from odoo.exceptions import UserError


class PropertyOffer(models.Model):
    _name="estate.property.offer"
    _description="People price offers"
    _order = "price desc"


    price=fields.Float()
    status = fields.Selection(
        string='Status',
        selection=[('accepted', 'Accepted'), ('refused', 'Refused')],
        copy=False,
        readonly=True
    )

    partner_id=fields.Many2one("res.partner",string="Partner",required=True)
    property_id=fields.Many2one("estate.property",string="Property", required=True)
    property_type_id=fields.Many2one(related="property_id.property_type_id", store=True)

    NOW = datetime.now()
    validity=fields.Integer(default=7,string="Validity(days)")
    create_date=fields.Date(copy=False ,default=NOW)
    date_deadline=fields.Date(compute="_validity_date",inverse="_inverse_validity_date")

    @api.depends('validity', 'create_date')
    def _validity_date(self):
        for record in self:
            if record.create_date and record.validity:
                record.date_deadline = record.create_date + relativedelta(days=+record.validity)
            else:
                record.date_deadline = False
    def _inverse_validity_date(self):
        for record in self:
            if record.create_date and record.date_deadline:
                delta = record.date_deadline - record.create_date
                record.validity = delta.days
            else:
                record.validity = 0

    sold = fields.Selection(
        selection=[('sold', 'Sold'), ('canceled', 'Canceled')],
        copy=False
    )

    def action_accepted(self):
        for record in self:
            existing = self.search([
                ('property_id', '=', record.property_id.id),
                ('status', '=', 'accepted'),
                ('id', '!=', record.id)
            ], limit=1)

            if existing:
                raise UserError("This property already has an offer accepted")

            record.status = 'accepted'

            # record.property_id.write({
            #     'status': 'accepted',
            #     'selling_price': record.price
            # })

    def action_refused(self):
        self.status='refused'

    def write_o(self, vals):
        res = super().write(vals)
        if 'status' in vals:
            self._update_property_status()
        return res

    def _update_property_status(self):
        for offer in self:
            offer.property_id.write_o({'status': 'accepted'})


    @api.model
    def create(self, vals):
        property_id = vals['property_id']
        new_price = vals['price']

        offers = self.search([('property_id', '=', property_id)])

        if offers:
            min_price = min(offers.mapped('price'))
            if new_price <= min_price:
                raise UserError(
                    f"The offer price ({new_price}) should be greater than the minimum existing ({min_price})"
                )
        return super(PropertyOffer, self).create(vals)

    def write(self, vals):
        if 'price' in vals:
            for offer in self:
                offers = self.search([
                    ('property_id', '=', offer.property_id.id),
                    ('id', '!=', offer.id)
                ])
                if offers:
                    min_price = min(offers.mapped('price'))
                    if vals['price'] <= min_price:
                        raise UserError(
                            f"Error setting price ({vals['price']}). It should be greater than the minimum existing ({min_price})"
                        )

        return super(PropertyOffer, self).write(vals)



    _sql_constraints = [
        ('check_expected_price', 'CHECK(price>=0)',
         'Price can\'t be negative value')
    ]







