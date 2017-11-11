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
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.system.model.Content;
import com.smallchill.system.model.User;
import org.beetl.sql.core.engine.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by lenovo on 2017/7/14.
 */
@Controller
@RequestMapping("/accountbalance")
public class AccountBalanceController extends BaseController {

    private static String LIST_SOURCE = "accountbalance.list";
    private static String PTGLIST_SOURCE = "accountbalance.pglist";
    private static String CULIST_SOURCE = "accountbalance.culist";
    private static String BASE_PATH = "/system/accountbalance/";
    private static String CODE = "accountbalance";
    private static String USERCODE = "user";
    private static String PREFIX = "blade_user";

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "accountbalance.html";
    }

      /*@RequestMapping("/")
      public String index(ModelMap mm) {
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
          if (userid == mainAccountId) {     //排在第一行的用户id和当前登录用户名id比较，若相等则为主账号，有权限提现积分
              //User user=mapping(PREFIX,User.class);
              CMap updateMap = CMap.init().set("roleid", 11).set("ids", Convert.toIntArray(Integer.toString(userid)));
              Blade.create(User.class).updateBy("roleid = #{roleid}","id in (#{join(ids)})",updateMap);

              return BASE_PATH + "accountbalance.html";
          }else {
              return BASE_PATH + "accountbalance.html";
          }
        }*/


    @Json
    @RequestMapping(KEY_LIST)
    public Object list(){
        String userRoleId=ShiroKit.getUser().getRoles();//获取当前登录用户名的角色id
        //管理员roleid：1，拼团购管理员roleid：9，普通用户roleid：10
        if (userRoleId.equals("1")){
            return adminlist();
        }else if (userRoleId.equals("9")){
            return ptgadminlist();
        }else {
            return commonuserlist();
        }
    }

    //管理员
    public Object adminlist() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }
    //拼团购管理员
    public Object ptgadminlist() {
        Object gird = paginate(PTGLIST_SOURCE);
        return gird;
    }
    //普通用户
    public Object commonuserlist() {
        Object gird = paginate(CULIST_SOURCE);
        return gird;
    }


    //PC前端数据展示接口
    /*@Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }*/

    //积分提现申请按钮接口
    /*@RequestMapping(KEY_ADD)
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "accountbalance_cash.html";
    }*/
    @RequestMapping(KEY_ADD + "/{id}")
    public String add(@PathVariable Integer id, ModelMap mm) {
        if (null != id) {
            User user = Blade.create(User.class).findById(id);
            //CMap cmap = CMap.parse(user);
            //cmap.set("roleName", SysCache.getRoleName(user.getId()));
            //mm.put("content", cmap);
            mm.put("code", CODE);
            return BASE_PATH + "accountbalance_cash.html";
        }else {
            return error1();
        }

        //return BASE_PATH + "dict_add.html";
    }


    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        User user = Blade.create(User.class).findById(id);
        CMap cmap = CMap.parse(user);
        cmap.set("currentintegral",user.getCurrentintegral());
        mm.put("model", JsonKit.toJson(cmap));
        mm.put("id", id);
        mm.put("code", CODE);
        return BASE_PATH + "accountbalance_cash.html";
    }

    /*@RequestMapping(KEY_VIEW + "/{id}")
    public String view(@PathVariable Integer id, ModelMap mm) {
        User user = Blade.create(User.class).findById(id);
        CMap cmap = CMap.parse(user);
        mm.put("user", cmap);
        mm.put("code", USERCODE);
        return BASE_PATH + "accountbalance_view.html";
    }*/

    @RequestMapping(KEY_VIEW + "/{id}")
    //@Permission({ ADMINISTRATOR, ADMIN })
    public String view(@PathVariable Integer id, ModelMap mm) {
        User user = Blade.create(User.class).findById(id);
        CMap cmap = CMap.parse(user);
        cmap.set("deptName", SysCache.getDeptName(user.getDeptid()))
            .set("roleName", SysCache.getRoleName(user.getRoleid()))
            .set("sexName", SysCache.getDictName(101, user.getSex()));
        mm.put("user", cmap);
        mm.put("code", USERCODE);
        return BASE_PATH + "accountbalance_view.html";
    }


    @Json
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
        User user = mapping(PREFIX, User.class);
        user.setCashstatus(3);
        boolean temp = Blade.create(User.class).save(user);
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
        User user = mapping(PREFIX, User.class);
        user.setCashstatus(3);
        boolean temp =  Blade.create(User.class).update(user);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success("提现申请成功，等待管理员审核！");
        } else {
            return error("提现申请失败，请重新操作！");
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
