# -*- coding: utf-8 -*-

from odoo import models, fields


class Devices(models.Model):
    _name = 'workshop.reparation.device'
    _description = 'Devices to repair'

    device_type_id = fields.Many2one("workshop.reparation.device.type", string="Device type")
    reparation_id = fields.Many2one("workshop.reparation",string="Reparation")

    model = fields.Char(required=True)
    brand = fields.Char()
    serial_number = fields.Char()
    purchase_date = fields.Date()
    
    fromOdoo=fields.Boolean(default=True)




