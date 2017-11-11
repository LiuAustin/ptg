package com.smallchill.system.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.tool.SysCache;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.system.model.CashApply;
import com.smallchill.system.model.Content;
import com.smallchill.system.model.User;
import com.smallchill.system.service.UserService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.beetl.sql.core.engine.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lqh on 2017/11/08.
 */
@Controller
@RequestMapping("/cashapply")
public class CashApplyController extends BaseController {

    private static String LIST_SOURCE = "cashapply.list";
    private static String BASE_PATH = "/system/cashapply/";
    private static String CODE = "cashapply";
    private static String PREFIX = "blade_cashapply";
    private static String USERPREFIX = "blade_user";

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "cashapply.html";
    }



    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }

    @RequestMapping(KEY_ADD+"/{id}")
    public String add(@PathVariable Integer id,ModelMap mm) {
        System.out.println(id);

        mm.put("code", CODE);
        return BASE_PATH + "cashapply_cash.html";
    }

    /*@RequestMapping(KEY_ADD)
    public String add(ModelMap mm) {
        Integer userid= (Integer) ShiroKit.getUser().getId();//获取当前登录用户的id
        //查找当前登录用户的手机号
        List<User> userList=Blade.create(User.class).find("SELECT phone FROM blade_user WHERE id=('"+userid+"')",User.class);
        //获取当前登录用户的手机号
        String nowUserPhone=userList.get(0).getPhone();
        //根据当前登录用户的手机号查找使用该手机号注册账号的用户id，按创建时间升序排列
        List<User> samePhoneList=Blade.create(User.class).find("SELECT id,account,createtime FROM blade_user WHERE phone =('"+nowUserPhone+"') ORDER BY createtime ASC",User.class);
        //获取排在第一行的用户id
        Integer mainAccountId=samePhoneList.get(0).getId();
        mm.put("code", CODE);
        //return BASE_PATH + "cashapply_cash.html";
        if (userid == mainAccountId){     //排在第一行的用户id和当前登录用户名id比较，若相等则为主账号，有权限提现积分
            return BASE_PATH + "cashapply_cash.html";
        }else {
            return BASE_PATH + "cashapply_cash1.html";
        }
    }*/

    /*@RequestMapping(KEY_ADD + "/{id}")
    public String add(@PathVariable Integer id, ModelMap mm) {
        if (null != id) {
            Content content = Blade.create(Content.class).findById(id);
            CMap cmap = CMap.parse(content);
            cmap.set("roleName", SysCache.getRoleName(content.getId()));
            mm.put("content", cmap);
            mm.put("code", CODE);
        }
        mm.put("code", CODE);
        return BASE_PATH + "dict_add.html";
    }*/

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        Content content = Blade.create(Content.class).findById(id);
        mm.put("model", JsonKit.toJson(content));
        mm.put("code", CODE);
        return BASE_PATH + "content_edit.html";
    }

    @RequestMapping(KEY_VIEW + "/{id}")
    public String view(@PathVariable Integer id, ModelMap mm) {
        Blade blade = Blade.create(CashApply.class);
        CashApply cashApply = blade.findById(id);
        CMap cmap = CMap.parse(cashApply);
        //从字典中取出中文状态在前端展示
        cmap.set("cashstatusName",SysCache.getDictName("107",cashApply.getCashstatus()));
        mm.put("model", JsonKit.toJson(cmap));
        mm.put("code", CODE);
        return BASE_PATH + "cashapply_view.html";
    }


    @Json
//    @Before(ContentValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
     /*   String ids = getParameter("ids");
        List<User> userList = Blade.create(User.class).findBy("id in (#{join(ids)})", CMap.init().set("ids", Convert.toIntArray(ids)));
        String userAccount = userList.get(0).getAccount();
        System.out.println(userAccount);*/


        CashApply cashApply = mapping(PREFIX, CashApply.class);
        cashApply.setCashtime(new Date());
        cashApply.setCashstatus(3);
        User user = mapping(USERPREFIX,User.class);
        double userCashintegral=cashApply.getCashintegral();
        user.setCashintegral(userCashintegral);

        Integer userid= (Integer) ShiroKit.getUser().getId();//获取当前登录用户的id
        //查找当前登录用户的手机号
        List<User> userList=Blade.create(User.class).find("SELECT phone FROM blade_user WHERE id=('"+userid+"')",User.class);
        //获取当前登录用户的手机号
        String nowUserPhone=userList.get(0).getPhone();
        //根据当前登录用户的手机号查找使用该手机号注册账号的用户id，按创建时间升序排列
        List<User> samePhoneList=Blade.create(User.class).find("SELECT id,account,createtime FROM blade_user WHERE phone =('"+nowUserPhone+"') ORDER BY createtime ASC",User.class);
        //获取排在第一行的用户id
        Integer mainAccountId=samePhoneList.get(0).getId();
        if (userid == mainAccountId) {     //排在第一行的用户id和当前登录用户名id比较，若相等则为主账号，有权限提现积分
            boolean temp = Blade.create(CashApply.class).save(cashApply);
            boolean temp1 = Blade.create(User.class).save(user);
            if (temp == temp1) {
                CacheKit.removeAll(SYS_CACHE);
                return success("积分提现申请成功！");
            } else {
                return error(SAVE_FAIL_MSG);
            }
        }else {
            return error("您不是主账号，无法进行提现操作！");
        }
    }

    //积分提现申请审核通过接口，保存审核时间
    @Json
    @RequestMapping("/auditOk")
    public AjaxResult auditOk() {
        Blade blade = Blade.create(CashApply.class);
        CashApply cashApply = mapping(PREFIX,CashApply.class);
        cashApply.setAudittime(new Date());
        String name = ShiroKit.getUser().getName();
        cashApply.setAuditeruser(name);
        String audituser = cashApply.getAuditeruser();
        String ids = getParameter("ids");
        CMap updateMap = CMap.init().set("cashstatus",1).set("ids", Convert.toIntArray(ids));
        boolean temp = blade.updateBy("cashstatus = #{cashstatus}","id in (#{join(ids)})",updateMap);
        boolean temp1 = blade.updateBy("auditeruser = ('"+audituser+"')","id in (#{join(ids)})",updateMap);
        if (temp == temp1) {
            return success("积分提现申请审核通过成功！");
        }else {
            return error("积分提现申请审核通过失败！");
        }
    }

    //积分提现申请审核拒绝接口，保存审核时间
    @Json
    @RequestMapping("/auditRefuse")
    public AjaxResult auditRefuse() {
        Blade blade = Blade.create(CashApply.class);
        CashApply cashApply = mapping(PREFIX,CashApply.class);
        cashApply.setAudittime(new Date());
        String name = ShiroKit.getUser().getName();
        cashApply.setAuditeruser(name);
        String audituser = cashApply.getAuditeruser();
        String ids = getParameter("ids");
        CMap updateMap = CMap.init().set("cashstatus",2).set("ids",Convert.toIntArray(ids));
        boolean temp = blade.updateBy("cashstatus = #{cashstatus}","id in (#{join(ids)})",updateMap);
        boolean temp1 = blade.updateBy("auditeruser = ('"+audituser+"')","id in (#{join(ids)})",updateMap);
        if (temp == temp1) {
            return success("积分提现申请审核拒绝成功！");
        }else {
            return error("积分提现申请审核拒绝失败！");
        }
    }

    @Json
//    @Before(DictValidator.class)
    @RequestMapping(KEY_UPDATE)
    public AjaxResult update() {
        Content content = mapping(PREFIX, Content.class);
        boolean temp =  Blade.create(Content.class).update(content);
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
        int cnt = Blade.create(Content.class).deleteByIds(getParameter("ids"));
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
        int cnt = Blade.create(Content.class).deleteByIds(getParameter("ids"));
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }


    private int findLastNum(String code){
        try{
            Blade blade = Blade.create(Content.class);
            Content content = blade.findFirstBy("code = #{code} order by num desc", CMap.init().set("code", code));
            return content.getLooknumber() + 1;
        }
        catch(Exception ex){
            return 1;
        }
    }


    @RequestMapping("/updateContent")
    public AjaxResult updatePassword(){
        Blade blade = Blade.create(Content.class);
        String contentId = getParameter("content.id");
//        String password = getParameter("user.newPassword");
        Content content = blade.findById(contentId);
        boolean temp = blade.update(content);
        if (temp) {
//            ShiroKit.clearCachedAuthenticationInfo(user.getAccount());
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
}
