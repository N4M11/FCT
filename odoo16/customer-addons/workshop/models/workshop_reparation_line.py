# -*- coding: utf-8 -*-

from odoo import models, fields,api


class Reparation(models.Model):
    _name = 'workshop.reparation.line'
    _description = "Reparations lines"

    reparation_id = fields.Many2one("workshop.reparation")
    product_id = fields.Many2one('product.product', string='Material',
                                 domain="[('product_tmpl_id.workshop_product', '=', True)]")
    # pasar x actualitzar stock
    quantity = fields.Integer(default=1)

    # compute-> preu unitari de la peça
    price = fields.Float(compute='_compute_price', store=True,string='Unit price')


    @api.depends('product_id')
    def _compute_price(self):
        for line in self:
            line.price = line.product_id.list_price if line.product_id else 0.0