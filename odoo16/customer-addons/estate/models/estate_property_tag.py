from odoo import fields, models
class PropertyTag(models.Model):
    _name = "estate.property.tag"
    _description = "Tags of the property"
    _order = "name"

    property_tag_id=fields.Many2many("estate.property", string="Property Tag")
    name=fields.Char(required=True)

    color=fields.Integer()

    _sql_constraints = [
        ('check_name', 'unique(name)',
         'Existing tag')
    ]


