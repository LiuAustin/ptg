package com.smallchill.system.service.impl;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.system.mapper.UserDao;
import com.smallchill.system.model.User;
import com.smallchill.system.service.UserService;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.beetl.sql.test.MysqlDBConfig.*;

/**
 * Created by Administrator on 2017/11/04/0004.
 */


@Service
public class UserServiceImpl extends BaseService<User> implements UserService{

 @Autowired
 UserService userService;
   /* @Autowired
    UserDao dao;*/
    /*String driver="com.mysql.jdbc.Driver";
    String url="jdbc:mysql://122.152.216.132:3306/ptg";
    String userName="root";
    String password="sa123456";

    ConnectionSource source = ConnectionSourceHelper.getSimple(driver,
            url, userName, password);
    DBStyle mysql = new MySqlStyle();
    SQLLoader loader = new ClasspathLoader("/beetlsql");
    DefaultNameConversion nameConversion=new DefaultNameConversion();
    UnderlinedNameConversion nc = new  UnderlinedNameConversion();
    SQLManager sqlManager = new SQLManager(mysql,loader,source,nameConversion,
            new Interceptor[]{new DebugInterceptor()});
    UserDao dao = sqlManager.getMapper(UserDao.class);*/
    @Override
    public Integer selectPhone(Integer userid){
        return 0;

    }
}
