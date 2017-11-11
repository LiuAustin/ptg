package com.smallchill.system.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.tool.SysCache;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.system.model.FearticlesList;
import com.smallchill.system.model.FormSubmit;
import org.beetl.sql.core.engine.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;



@CrossOrigin(origins = "*", maxAge = 3600)//Controller类或其方法上加@CrossOrigin注解，来使之支持跨域
@Controller
@RequestMapping("/formsubmit")
public class FormSubmit2Controller extends BaseController{

    private static String LIST_SOURCE = "formsubmit.list";
    private static String BASE_PATH = "/system/formsubmit/";
    private static String CODE = "formsubmit";
    private static String PREFIX = "blade_formsubmit";

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "formsubmit.html";
    }


    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }

    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "formsubmit_add.html";
    }





    @Json
    //@Before(UserValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
        FormSubmit formsubmit = mapping(PREFIX, FormSubmit.class);
        String pwd = formsubmit.getPassword();
        String spwd=formsubmit.getSecurePwd();
        String salt = ShiroKit.getRandomSalt(5);
        String ssalt=ShiroKit.getRandomSalt(5);
        String pwdMd5 = ShiroKit.md5(pwd, salt);//MD5加密
//		String spwdMd5=ShiroKit.md5(spwd,ssalt);
        formsubmit.setPassword(pwdMd5);
//		formsubmit.setSecurePwd(spwdMd5);
        formsubmit.setSalt(salt);
        formsubmit.setSsalt(ssalt);
        formsubmit.setAuditState(3);
        formsubmit.setCreateTime(new Date());
        Integer userid= (Integer) ShiroKit.getUser().getId();
        formsubmit.setCreatorId(userid);
        boolean temp = Blade.create(FormSubmit.class).save(formsubmit);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @Json
//    @Before(DictValidator.class)
    @RequestMapping(KEY_UPDATE)
    public AjaxResult update() {
        FearticlesList fearticleslist = mapping(PREFIX, FearticlesList.class);
        fearticleslist.setUpdatetime(new Date());
        fearticleslist.setUpdater((Integer) ShiroKit.getUser().getId());
        boolean temp =  Blade.create(FearticlesList.class).update(fearticleslist);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
    @Json
    @RequestMapping(KEY_DEL)
//    @Permission(ADMINISTRATOR)
    public AjaxResult del() {
        int cnt = Blade.create(FearticlesList.class).deleteByIds(getParameter("ids"));
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }

    }
    @Json
    @RequestMapping(KEY_REMOVE)
    public AjaxResult remove() {
        int cnt = Blade.create(FearticlesList.class).deleteByIds(getParameter("ids"));
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }


/*    private int findLastNum(String code){
        try{
            Blade blade = Blade.create(TopList.class);
            TopList toplist = blade.findFirstBy("code = #{code} order by num desc", CMap.init().set("code", code));
            return toplist.getLooknumber() + 1;
        }
        catch(Exception ex){
            return 1;
        }
    }*/


    @RequestMapping("/updateFearticleslist")
    public AjaxResult updatePassword(){
        Blade blade = Blade.create(FearticlesList.class);
        String fearticleslistId = getParameter("fearticleslist.id");
//        String password = getParameter("user.newPassword");
        FearticlesList fearticleslist = blade.findById(fearticleslistId);
        boolean temp = blade.update(fearticleslist);
        if (temp) {
//            ShiroKit.clearCachedAuthenticationInfo(user.getAccount());
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
}







