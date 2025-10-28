{
    'name': 'Repair Workshop',
    'version': '1.0',
    'depends': ['base', 'hr', 'stock', 'mail'],
    'application': True,
    'installable': True,
    'license': 'LGPL-3',

    'data': [
        'security/ir.model.access.csv',
        'data/demo.xml',
        'views/workshop_views.xml',
        'views/res_partner.xml',
        'views/reparation_tag_views.xml',
        'views/device_type_views.xml',
        'views/workshop_material_views.xml',
        'views/invoice_views.xml',
        'report/workshop_reparation_template.xml',
        'views/asset.xml',
        'views/workshop_menu_views.xml',
    ],



    'images': ['static/description/icon.png'],
}
