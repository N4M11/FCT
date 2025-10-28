from odoo import models, fields
class AccountMove(models.Model):
    _inherit = 'account.move'

    #boolean x diferenciar modul
    workshop_invoice = fields.Boolean(default=False,string="Workshop invoice")


