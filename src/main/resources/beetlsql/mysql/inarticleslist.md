list
===
SELECT
blade_inarticleslist.*,bd.name,
cbu.name AS createUser,
ubu.name AS updateUser
FROM
blade_inarticleslist
LEFT JOIN blade_user cbu ON blade_inarticleslist.creater = cbu.id
LEFT JOIN blade_user ubu ON blade_inarticleslist.updater = ubu.id
LEFT JOIN blade_dict bd ON blade_inarticleslist.category=bd.num AND bd.code=108

listCategoryView
===
select d.name from blade_inarticleslist t,blade_dict d where d.code=108 
and d.num=t.category