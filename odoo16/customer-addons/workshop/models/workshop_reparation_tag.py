from odoo import fields, models
class PropertyTag(models.Model):
    _name = "workshop.reparation.tag"
    _description = "Reparations tags"
    _order = "name"

    property_tag_id=fields.Many2many("workshop.reparation", string="Reparation Tag")
    name=fields.Char(required=True)

    color=fields.Integer()

    _sql_constraints = [
        ('check_name', 'unique(name)',
         'Existing tag')
    ]