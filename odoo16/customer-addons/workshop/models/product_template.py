from odoo import models, fields
class ProductTemplate(models.Model):
    _inherit = 'product.template'

    workshop_product = fields.Boolean(default=False, string="Workshop product")

    #boolean x diferenciar modul
    workshop_product = fields.Boolean(default=False,string="Workshop product")


