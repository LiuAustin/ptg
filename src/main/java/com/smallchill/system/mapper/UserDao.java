package com.smallchill.system.mapper;
import com.smallchill.system.model.User;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/11/08/0008.
 */



public interface UserDao extends BaseMapper<User> {

    @Sql(value = "select phone from blade_user where id = ?")
    public Integer selectPhone(@Param("userid") Integer userid);
}
