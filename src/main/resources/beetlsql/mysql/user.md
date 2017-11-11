list
===
SELECT
blade_user.*,bd.name as CardType,bd1.name as Deposit
FROM
blade_user
LEFT JOIN blade_dict bd ON blade_user.bankcardtype=bd.num AND bd.code=105
LEFT JOIN blade_dict bd1 ON blade_user.bankdeposit=bd1.num AND bd1.code=106

ptglist
===
SELECT
blade_user.*,bd.name as CardType,bd1.name as Deposit
FROM
blade_user
LEFT JOIN blade_dict bd ON blade_user.bankcardtype=bd.num AND bd.code=105
LEFT JOIN blade_dict bd1 ON blade_user.bankdeposit=bd1.num AND bd1.code=106
WHERE blade_user.id !=1

culist
===
SELECT
blade_user.*,bd.name as CardType,bd1.name as Deposit
FROM
blade_user
LEFT JOIN blade_dict bd ON blade_user.bankcardtype=bd.num AND bd.code=105
LEFT JOIN blade_dict bd1 ON blade_user.bankdeposit=bd1.num AND bd1.code=106
WHERE blade_user.recommendnum=#{userid.userid()}

mysqllist
===
select u.*,
	d.name as SEXNAME,
	e.name as STATUSNAME,
	dept.simpleName as DEPTNAME,
	(select GROUP_CONCAT(NAME) from blade_role where  INSTR(CONCAT(",",u.ROLEID,","),CONCAT(",",ID,","))>0) ROLENAME
from blade_user u 
	left join (select num,name from blade_dict where code=101) d on u.sex=d.num 
	left join (select num,name from blade_dict where code=901) e on u.status=e.num 
	left join (select id,simpleName from blade_dept) dept on u.deptId=dept.id
	
findUserCombo
===
select id,name as TEXT from blade_user

selectPhone
===
SELECT phone FROM blade_user WHERE id=#userid#