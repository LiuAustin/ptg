package com.smallchill.system.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.system.model.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/7/14.
 */
@Controller
@RequestMapping("/comment")
public class CommentController  extends BaseController implements ConstShiro {
    private static String LIST_SOURCE = "comment.list";
    private static String BASE_PATH = "/system/comment/";
    private static String CODE = "comment";
    private static String PREFIX = "blade_comment";

//    @Autowired
//    CommentService commentService;
    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "comment.html";
    }

    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm) {
        if(ShiroKit.lacksRole(ADMINISTRATOR)){
            return REDIRECT_UNAUTH;
        }
        mm.put("code", CODE);
        return BASE_PATH + "comment_add.html";
    }

//    @RequestMapping(KEY_ADD + "/{id}")
//    public String add(@PathVariable Integer id, ModelMap mm) {
//        if (null != id) {
//            Comment comment = commentService.findById(id);
//            mm.put("PCODE", comment.getCode());
//            mm.put("LEVELS", comment.getLevels() + 1);
//            mm.put("NUM", commentService.findLastNum(comment.getCode()));
//        }
//        mm.put("code", CODE);
//        return BASE_PATH + "comment_add.html";
//    }

    @Json
    @RequestMapping("/listAll")
    public List<Comment> listAll(){
        Blade blade = Blade.create(Comment.class);
        List<Comment> comment = blade.findBy("comment_state='1'", Comment.class);
        return comment;
    }
    @Json
    @RequestMapping("/addComment")
    public AjaxResult addComment(Comment comment){
        Blade blade = Blade.create(Comment.class);
        comment.setComment_createtime(new Date());
        boolean temp = blade.save(comment);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }
    @Json
    @RequestMapping("/updateComment")
    public AjaxResult updateComment(Comment comment){
        Blade blade = Blade.create(Comment.class);
        boolean temp = blade.updateBy("comment_content_t=('"+comment.getComment_content_t()+"')","id=("+comment.getId()+")",comment);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        Comment comment = Blade.create(Comment.class).findById(id);
        mm.put("model", JsonKit.toJson(comment));
        mm.put("code", CODE);
        return BASE_PATH + "menu_edit.html";
    }

    @RequestMapping(KEY_VIEW + "/{id}")
    @Permission(ADMINISTRATOR)
    public String view(@PathVariable Integer id, ModelMap mm) {

//        Comment menu = service.findById(id);
        Blade blade = Blade.create(Comment.class);
        Comment comment = blade.findById(id);
//        Comment parent = blade.findById(comment.getId());
//        String pname = (null == parent) ? "" : parent.getComment_content();
        CMap cmap = CMap.parse(comment);
//        cmap.set("pname", pname);
        mm.put("model", JsonKit.toJson(cmap));
        mm.put("code", CODE);
        return BASE_PATH + "comment_view.html";
    }



    @Json
//    @Before(MenuValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
//        Comment menu = mapping(PREFIX, Comment.class);
//        menu.setStatus(1);
//        boolean temp = service.save(menu);
        Comment comment = mapping(PREFIX, Comment.class);
        comment.setComment_createtime(new Date());
        boolean temp = Blade.create(Comment.class).save(comment);
        if (temp) {
            CacheKit.removeAll(SYS_CACHE);
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @Json
//    @Before(MenuValidator.class)
    @RequestMapping(KEY_UPDATE)
//    @Permission(ADMINISTRATOR)
    public AjaxResult update() {
//        Menu menu = mapping(PREFIX, Menu.class);
//        boolean temp = service.update(menu);
        Comment comment = mapping(PREFIX, Comment.class);
        boolean temp =  Blade.create(Comment.class).update(comment);
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
        int cnt = Blade.create(Comment.class).deleteByIds(getParameter("ids"));
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }

    }

//    @Json
//    @RequestMapping(KEY_RESTORE)
////    @Permission(ADMINISTRATOR)
//    public AjaxResult restore(@RequestParam String ids) {
//
//        Comment comment = mapping(PREFIX, Comment.class);
//        int temp =  Blade.create(Comment.class).deleteByIds(ids);
////        boolean temp = service.updateStatus(ids, 1);
//        if (temp==1) {
//            CacheKit.removeAll(SYS_CACHE);
//            return success(RESTORE_SUCCESS_MSG);
//        } else {
//            return error(RESTORE_FAIL_MSG);
//        }
//
//    }

    @Json
    @RequestMapping(KEY_REMOVE)
//    @Permission(ADMINISTRATOR)
    public AjaxResult remove(@RequestParam String ids) {
        int cnt = Blade.create(Comment.class).deleteByIds(getParameter("ids"));
//        int cnt = service.deleteByIds(ids);
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }


    @Json
    @RequestMapping("/auditOk")
    public AjaxResult auditOk() {
        String ids = getParameter("ids");
        Blade blade = Blade.create(Comment.class);
        CMap updateMap = CMap.init().set("comment_state", 1).set("ids", Convert.toIntArray(ids));
        boolean temp = blade.updateBy("comment_state = #{comment_state}", "id in (#{join(ids)})", updateMap);
        if (temp) {
            return success("审核成功!");
        } else {
            return error("审核失败!");
        }
    }

    @Json
    @RequestMapping("/auditRefuse")
    public AjaxResult auditRefuse() {
        String ids = getParameter("ids");
        CMap updateMap = CMap.init().set("comment_state", 2).set("ids", Convert.toIntArray(ids));
        boolean temp = Blade.create(Comment.class).updateBy("comment_state = #{comment_state}", "id in (#{join(ids)})", updateMap);
        if (temp) {
            return success("审核拒绝成功!");
        } else {
            return error("审核拒绝失败!");
        }
    }
}
