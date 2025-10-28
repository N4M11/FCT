{
    'name': 'RealEstate',
    'version': '1.0',
    'depends': ['base'],
    'application': True,
    'license': 'LGPL-3',

    'data': [
	'security/ir.model.access.csv',

    'views/estate_property_views.xml',
    'views/property_type_views.xml',
    'views/property_tag_views.xml',
    'views/res_users_views.xml',
    'views/estate_menu_views.xml',
    ],
}
