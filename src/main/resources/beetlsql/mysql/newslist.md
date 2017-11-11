list
===
SELECT
blade_newslist.*,bd.name,
cbu.name AS createUser,
ubu.name AS updateUser
FROM
blade_newslist
LEFT JOIN blade_user cbu ON blade_newslist.creater = cbu.id
LEFT JOIN blade_user ubu ON blade_newslist.updater = ubu.id
LEFT JOIN blade_dict bd ON blade_newslist.category=bd.num AND bd.code=108

listCategoryView
===
select d.name from blade_newslist t,blade_dict d where d.code=108 
and d.num=t.category