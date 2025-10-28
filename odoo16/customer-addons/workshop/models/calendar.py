# -*- coding: utf-8 -*-
from odoo import api, fields, models


class CalendarEvent(models.Model):
    _inherit = 'calendar.event'

    reparation_id = fields.Many2one(
        'workshop.reparation', 'Reparation', index=True, ondelete='set null')