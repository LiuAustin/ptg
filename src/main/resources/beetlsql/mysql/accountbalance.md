list
===
select * from blade_user 

pglist
===
select * from blade_user where id !=1

culist
===
select * from blade_user where phone = #{userphone.userPhone()}

findCurrentIntegral
===
select currentintegral from blade_user where id = 1