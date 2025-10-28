from odoo import fields, models, Command
from odoo.exceptions import UserError


class Properties(models.Model):
    _inherit = 'estate.property'

    property_ids = fields.One2many("estate.property", "buyer")

    # move_type -> out_invoice, partner id from estate.property
    # self.env[model_name].create(values) to create an object

    # To create an invoice, we need the following information:
    #
    #     a partner_id: the customer
    #     a move_type: it has several possible values
    #     a journal_id: the accounting journal

    def action_sold(self):

        for record in self:

            journal = self.env['account.journal'].search([
                ('type', '=', 'sale'),
                ('company_id', '=', self.env.company.id)
            ], limit=1)

            if not journal:
                raise UserError("Invoice journal not found")

            invoice = self.env["account.move"].create(
                {
                    "partner_id": record.buyer.id,
                    "move_type": "out_invoice",
                    "journal_id": journal.id,
                    "invoice_date":record.NOW,
                    "invoice_line_ids": [
                        Command.create({
                            "name": record.name,
                            "quantity": 1,
                            "price_unit": record.selling_price

                        }),
                        Command.create({
                            "name": "6%",
                            "quantity": 1,
                            "price_unit": record.selling_price*0.06

                        }),
                        Command.create({
                            "name": "Administrative fees",
                            "quantity": 1,
                            "price_unit": 100.00

                        })
                    ],
                }
            )
        invoice.action_post()
        return super().action_sold()
