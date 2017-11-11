package com.smallchill.system.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.tool.SysCache;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.system.model.Content;
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
@RequestMapping("/content")
public class ContentController extends BaseController {

    private static String LIST_SOURCE = "content.list";
    private static String BASE_PATH = "/system/content/";
    private static String CODE = "content";
    private static String PREFIX = "blade_content";

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "content.html";
    }
    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }

//    @Json
//    @RequestMapping("/static"+KEY_LIST)
//    public Object listAll() {
//        Object gird = paginate(LIST_SOURCE);
//        return gird;
//    }
    @Json
    @RequestMapping("/listAll")
    public Page<Content> listAll() {
        Page<Content> page=new Page<Content>();
        List<Content> content = Blade.create(Content.class).find("select blade_content.*,blade_attach.url from blade_content join blade_attach on blade_content.picture=blade_attach.id ORDER BY blade_content.createtime DESC", Content.class);
        page.setList(content);
        return page;
    }
    @Json
    @RequestMapping("/listContent")
    public Page<Content> listContent(String name) {
        Page<Content> page=new Page<Content>();
        List<Content> content = Blade.create(Content.class).find("select blade_content.*,blade_attach.url from blade_content join blade_attach on blade_content.picture=blade_attach.id where title like CONCAT('%',('" + name + "'),'%') ORDER BY blade_content.createtime DESC ", Content.class);
        page.setList(content);
        return page;
    }
    @Json
    @RequestMapping("/listNews")
    public Page<Content> listNews(String label) {
        Page<Content> page=new Page<Content>();
        List<Content> content = Blade.create(Content.class).find("select blade_content.*,blade_attach.url from blade_content join blade_attach on blade_content.picture=blade_attach.id where label = ('"+ label +"') ORDER BY blade_content.createtime DESC ", Content.class);
        page.setList(content);
        return page;
    }
    @Json
    @RequestMapping("/findView")
    public List<Content> listCon(String id) {
        Blade blade = Blade.create(Content.class);
        Content content = Blade.create(Content.class).findById(id);
        int  looknum = content.getLooknumber()+1;
        boolean temp = blade.updateBy("looknumber=("+looknum+")","id=("+content.getId()+")",content);
        List<Content> contents = Blade.create(Content.class).find("select blade_content.*,blade_attach.url from blade_content join blade_attach on blade_content.picture=blade_attach.id where blade_content.id = (" + id + ")  ", Content.class);
        return contents;
    }
    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "content_add.html";
    }
    @RequestMapping(KEY_ADD + "/{id}")
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
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        Content content = Blade.create(Content.class).findById(id);
        mm.put("model", JsonKit.toJson(content));
        mm.put("code", CODE);
        return BASE_PATH + "content_edit.html";
    }

    @RequestMapping(KEY_VIEW + "/{id}")
    public String view(@PathVariable Integer id, ModelMap mm) {
        Blade blade = Blade.create(Content.class);
        Content content = blade.findById(id);
//        Content parent = blade.findById(content.getId());
//        String pname = (null == parent) ? "" : parent.getContent();
        CMap cmap = CMap.parse(content);
//        cmap.set("pname", pname);
        mm.put("model", JsonKit.toJson(cmap));
        mm.put("code", CODE);
        return BASE_PATH + "content_view.html";
    }


    @Json
//    @Before(ContentValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
        Content content = mapping(PREFIX, Content.class);
        boolean temp = Blade.create(Content.class).save(content);
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
