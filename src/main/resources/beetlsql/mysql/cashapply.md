list
===
SELECT
	blade_cashapply.*, bd.name
FROM
	blade_cashapply
LEFT JOIN blade_dict bd ON blade_cashapply.cashstatus = bd.num
AND bd. CODE = 107 
