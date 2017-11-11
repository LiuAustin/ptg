package com.smallchill.common.beetl;

import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.system.model.User;

import java.util.List;

/**
 * Created by Administrator on 2017/11/10/0010.
 */
public class UserPhone {

    public String userPhone(){
        Integer userid = (Integer) ShiroKit.getUser().getId();
        List<User> userList= Blade.create(User.class).find("SELECT phone FROM blade_user WHERE id=('"+userid+"')",User.class);
        //获取当前登录用户的手机号
        String nowUserPhone=userList.get(0).getPhone();
        return nowUserPhone;
    }
}
