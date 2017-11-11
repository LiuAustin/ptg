package com.smallchill.common.beetl;

import com.smallchill.core.shiro.ShiroKit;

/**
 * Created by Administrator on 2017/11/04/0004.
 */
//获取当前登录用户名id，作为user.md文件里的where条件值，传入参数为#{userid.userid()}
public class UserId {

    public Integer userid(){
        return (Integer) ShiroKit.getUser().getId();
    }
}
