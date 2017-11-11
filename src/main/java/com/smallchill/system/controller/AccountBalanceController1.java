package com.smallchill.system.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.tool.SysCache;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.system.model.LegalList;
import com.smallchill.system.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/11/06/0006.
 */
@Controller
@RequestMapping("/accountbalance")
public class AccountBalanceController1 extends BaseController {

    /*private static String LIST_SOURCE = "accountbalance.list";
    private static String BASE_PATH = "/system/accountbalance/";
    private static String CODE = "accountbalance";
    private static String PREFIX = "blade_user";

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "accountbalance.html";
    }

    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }

    //积分提现按钮的接口

    *//*@RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        User user = Blade.create(User.class).findById(id);
        mm.put("model", JsonKit.toJson(user));
        mm.put("code", CODE);
        return BASE_PATH + "accountbalance_cash.html";
    }*//*
    @Json
    @RequestMapping("/cash"+"/{id}")
    public String cash(@PathVariable Integer id,ModelMap mm){
        User user = Blade.create(User.class).findById(id);
        CMap cmap = CMap.parse(user);
        cmap.set("currentintegral", user.getCurrentintegral());
        mm.put("model", JsonKit.toJson(cmap));
        mm.put("code",CODE);
        return BASE_PATH + "accountbalance_cash.html";
    }*/
}
