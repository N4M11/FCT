from odoo import models, fields
class ResPartner(models.Model):
    _inherit = 'res.partner'
    reparation_ids=fields.One2many("workshop.reparation","client")

    #boolean x diferenciar modul
    workshop_client = fields.Boolean(default=False,string="Workshop client")








