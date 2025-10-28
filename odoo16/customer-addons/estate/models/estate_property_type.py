from odoo import fields, models, api
class PropertyType(models.Model):
    _name = "estate.property.type"
    _description = "Type of property"
    _order = "sequence desc"

    property_type_id=fields.Many2one("estate.property", string="Property Type")
    name=fields.Char(required=True)

    property_ids=fields.One2many("estate.property", "property_type_id", string="Properties")
    property_offer_ids=fields.One2many("estate.property.offer","property_type_id", string="Offers")
    property_offer_count=fields.Integer(compute="_offer_count")
    @api.depends('property_offer_ids')
    def _offer_count(self):
        for record in self:
            record.property_offer_count=len(record.property_offer_ids)

    def action_open_offers(self):
        self.ensure_one()
        return {
            "name": "Property Offers",
            "type": "ir.actions.act_window",
            "view_mode": "tree,form",
            "res_model": "estate.property.offer",
            "domain": [("id", "in", self.property_offer_ids.ids)],
            "target": "current",
            "context": {
                "search_default_property_type_id": self.id
            }
        }


    sequence=fields.Integer('sequence', default=1)

    _sql_constraints = [
        ('check_name', 'unique(name)',
         'Existing type')
    ]



