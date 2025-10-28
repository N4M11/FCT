from odoo import models, fields

class MailActivity(models.Model):
    _name = 'mail.activity'
    _inherit = ['mail.thread', 'mail.activity']

    def action_create_calendar_event(self):
        """ Small override of the action that creates a calendar.

        If the activity is linked to a crm.lead through the "opportunity_id" field, we include in
        the action context the default values used when scheduling a meeting from the crm.lead form
        view.
        e.g: It will set the partner_id of the crm.lead as default attendee of the meeting. """

        action = super(MailActivity, self).action_create_calendar_event()
        reparation = self.calendar_event_id.reparation_id
        if reparation:
            reparation_action_context = reparation.action_schedule_meeting(smart_calendar=False).get('context', {})
            reparation_action_context['initial_date'] = self.calendar_event_id.start

            action['context'].update(reparation_action_context)

        return action
