list
===
SELECT
blade_legallist.*,bd.name,
cbu.name AS createUser,
ubu.name AS updateUser
FROM
blade_legallist
LEFT JOIN blade_user cbu ON blade_legallist.creater = cbu.id
LEFT JOIN blade_user ubu ON blade_legallist.updater = ubu.id
LEFT JOIN blade_dict bd ON blade_legallist.legalcategory=bd.num AND bd.code=118

listCategoryView
===
select d.name from blade_legallist t,blade_dict d where d.code=118
and d.num=t.topcategory