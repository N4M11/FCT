# -*- coding: utf-8 -*-
from odoo import models, fields
from datetime import *



class Reparation(models.Model):
    _name = 'workshop.reparation.timesheet'
    _description = 'Reparations timesheets'

    reparation_id=fields.Many2one("workshop.reparation",string="Reparation",required=True)

    NOW = datetime.now()
    date = fields.Date(default=NOW, required=True)
    description = fields.Char(required = True)
    time = fields.Float(required = True)

    _sql_constraints = [
        ('check_time', 'CHECK(time>=0)',
         'Invalid time value'),
    ]