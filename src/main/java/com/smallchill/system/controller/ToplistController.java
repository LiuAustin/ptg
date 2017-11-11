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
import com.smallchill.system.model.TopList;
import org.beetl.sql.core.engine.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;



/**
 * Created by Administrator on 2017/10/19/0019.
 */
@CrossOrigin(origins = "*", maxAge = 3600)//Controller类或其方法上加@CrossOrigin注解，来使之支持跨域
@Controller
@RequestMapping("/toplist")
public class ToplistController extends BaseController{

    private static String LIST_SOURCE = "toplist.list";
    private static String BASE_PATH = "/system/toplist/";
    private static String CODE = "toplist";
    private static String PREFIX = "blade_toplist";

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "toplist.html";
    }

/*    @RequestMapping(KEY_MAIN)
    public String index(ModelMap mm){
        mm.put("code", CODE);
        return BASE_PATH + "toplist.html";
    }*/

    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }
    @Json
    @RequestMapping("/listAll")
    public Page<TopList> listAll() {
        Page<TopList> page=new Page<TopList>();
        List<TopList> topList = Blade.create(TopList.class).find("select blade_toplist.*,blade_attach.url from blade_toplist join blade_attach on blade_toplist.picture=blade_attach.id ORDER BY blade_toplist.createtime DESC", TopList.class);
        page.setList(topList);
        return page;
    }
    @Json
    @RequestMapping("/listToplist")
    public Page<TopList> listToplist(String name) {
        Page<TopList> page=new Page<TopList>();
        List<TopList> toplist = Blade.create(TopList.class).find("select blade_toplist.*,blade_attach.url from blade_toplist join blade_attach on blade_toplist.picture=blade_attach.id where title like CONCAT('%',('" + name + "'),'%') ORDER BY blade_content.createtime DESC ", TopList.class);
        page.setList(toplist);
        return page;
    }

    /*前端按分类展示的接口*/
    @Json
    @RequestMapping("/listTop")
    public Page<TopList> listTop(String topcategory) {
        Page<TopList> page = new Page<TopList>();
        if (Integer.parseInt(topcategory) == 3) {
            List<TopList> toplist = Blade.create(TopList.class).find("select blade_toplist.* from blade_toplist where topcategory = ('" + topcategory + "')  ORDER BY blade_toplist.contenttime DESC ", TopList.class);
            page.setList(toplist);
        }else {
            List<TopList> toplist = Blade.create(TopList.class).find("select blade_toplist.*,blade_attach.url from blade_toplist join blade_attach on blade_toplist.picture=blade_attach.id where topcategory = ('" + topcategory + "') ORDER BY blade_toplist.contenttime DESC ", TopList.class);
            page.setList(toplist);
        }
        return page;
    }


    //前端获取详情页信息接口，根据点击量，浏览人数自增
/*    @Json
    @RequestMapping("/findView")
    public List<TopList> listCon(String id) {
        Blade blade = Blade.create(TopList.class);
        TopList toplist = Blade.create(TopList.class).findById(id);
        int  looknum = toplist.getLooknumber()+1;
        boolean temp = blade.updateBy("looknumber=("+looknum+")","id=("+toplist.getId()+")",toplist);
        List<TopList> toplists = Blade.create(TopList.class).find("select blade_toplist.* from blade_toplist where blade_toplist.id = (" + id + ")  ", TopList.class);
        return toplists;
    }*/

    @RequestMapping(KEY_ADD)
   public String add(ModelMap mm) {
       mm.put("code", CODE);
       return BASE_PATH + "toplist_add.html";
   }
    @RequestMapping(KEY_ADD + "/{id}")
    public String add(@PathVariable Integer id, ModelMap mm) {
        if (null != id) {
            TopList toplist = Blade.create(TopList.class).findById(id);
            CMap cmap = CMap.parse(toplist);
            cmap.set("roleName", SysCache.getRoleName(toplist.getId()));
            mm.put("toplist", cmap);
            mm.put("code", CODE);
        }
        mm.put("code", CODE);
        return BASE_PATH + "dict_add.html";
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        TopList toplist = Blade.create(TopList.class).findById(id);
        mm.put("model", JsonKit.toJson(toplist));
        mm.put("code", CODE);
        return BASE_PATH + "toplist_edit.html";
    }

    @RequestMapping(KEY_VIEW + "/{id}")
    public String view(@PathVariable Integer id, ModelMap mm) {
        Blade blade = Blade.create(TopList.class);
        TopList toplist = blade.findById(id);
        CMap cmap = CMap.parse(toplist);
        //将“分类”字段显示成字典中对应的文字
        cmap.set("topcategoryName",SysCache.getDictName("109",toplist.getTopcategory()));
        mm.put("model", JsonKit.toJson(cmap));
        mm.put("code", CODE);
        return BASE_PATH + "toplist_view.html";
    }

    @Json
//    @Before(ContentValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
        TopList toplist = mapping(PREFIX, TopList.class);
        toplist.setCreatetime(new Date());
        Integer userid= (Integer) ShiroKit.getUser().getId();
        toplist.setCreater(userid);
        boolean temp = Blade.create(TopList.class).save(toplist);
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
        TopList toplist = mapping(PREFIX, TopList.class);
        toplist.setUpdatetime(new Date());
        toplist.setUpdater((Integer) ShiroKit.getUser().getId());
        boolean temp =  Blade.create(TopList.class).update(toplist);
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
        int cnt = Blade.create(TopList.class).deleteByIds(getParameter("ids"));
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
        int cnt = Blade.create(TopList.class).deleteByIds(getParameter("ids"));
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


    @RequestMapping("/updateToplist")
    public AjaxResult updatePassword(){
        Blade blade = Blade.create(TopList.class);
        String toplistId = getParameter("toplist.id");
//        String password = getParameter("user.newPassword");
        TopList toplist = blade.findById(toplistId);
        boolean temp = blade.update(toplist);
        if (temp) {
//            ShiroKit.clearCachedAuthenticationInfo(user.getAccount());
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
}







