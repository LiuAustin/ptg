package com.smallchill.system.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.system.model.User;

/**
 * Created by Administrator on 2017/11/04/0004.
 */
public interface UserService extends IService<User>{

    public Integer selectPhone(Integer userid);
}
