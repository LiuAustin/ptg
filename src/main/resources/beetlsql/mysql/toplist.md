list
===
SELECT
blade_toplist.*,bd.name,
cbu.name AS createUser,
ubu.name AS updateUser
FROM
blade_toplist
LEFT JOIN blade_user cbu ON blade_toplist.creater = cbu.id
LEFT JOIN blade_user ubu ON blade_toplist.updater = ubu.id
LEFT JOIN blade_dict bd ON blade_toplist.topcategory=bd.num AND bd.code=109

listCategoryView
===
select d.name from blade_toplist t,blade_dict d where d.code=109
and d.num=t.topcategory