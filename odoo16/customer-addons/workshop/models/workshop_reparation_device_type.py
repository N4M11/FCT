from odoo import fields, models, api
class DeviceType(models.Model):
    _name = "workshop.reparation.device.type"
    _description = "Types of devices"

    device_type_id = fields.Many2one("workshop.reparation.device", string="Device Type")
    name = fields.Char(required=True)

    device_ids = fields.One2many("workshop.reparation.device", "device_type_id", string="Reparations")

    _sql_constraints = [
        ('check_name', 'unique(name)',
         'Existing type')
    ]