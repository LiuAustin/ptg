list
===
SELECT
blade_fearticleslist.*,bd.name,
cbu.name AS createUser,
ubu.name AS updateUser
FROM
blade_fearticleslist
LEFT JOIN blade_user cbu ON blade_fearticleslist.creater = cbu.id
LEFT JOIN blade_user ubu ON blade_fearticleslist.updater = ubu.id
LEFT JOIN blade_dict bd ON blade_fearticleslist.category=bd.num AND bd.code=108

listCategoryView
===
select d.name from blade_fearticleslist t,blade_dict d where d.code=108 
and d.num=t.category