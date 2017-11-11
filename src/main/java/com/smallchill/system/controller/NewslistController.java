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
import com.smallchill.system.model.NewsList;
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
@RequestMapping("/newslist")
public class NewslistController extends BaseController{

    private static String LIST_SOURCE = "newslist.list";
    private static String BASE_PATH = "/system/newslist/";
    private static String CODE = "newslist";
    private static String PREFIX = "blade_newslist";

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "newslist.html";
    }

/*    @RequestMapping(KEY_MAIN)
    public String index(ModelMap mm){
        mm.put("code", CODE);
        return BASE_PATH + "newslist.html";
    }*/

    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }
    @Json
    @RequestMapping("/listAll")
    public Page<NewsList> listAll() {
        Page<NewsList> page=new Page<NewsList>();
        List<NewsList> newslist = Blade.create(NewsList.class).find("select blade_newslist.*,blade_attach.url from blade_newslist join blade_attach on blade_newslist.picture=blade_attach.id ORDER BY blade_newslist.createtime DESC", NewsList.class);
        page.setList(newslist);
        return page;
    }
    @Json
    @RequestMapping("/listNewsList")
    public Page<NewsList> listNewsList(String name) {
        Page<NewsList> page=new Page<NewsList>();
        List<NewsList> newslist = Blade.create(NewsList.class).find("select blade_newslist.*,blade_attach.url from blade_newslist join blade_attach on blade_newslist.picture=blade_attach.id where title like CONCAT('%',('" + name + "'),'%') ORDER BY blade_newslist.createtime DESC ", NewsList.class);
        page.setList(newslist);
        return page;
    }

    /*前端按分类展示的接口*/
    @Json
    @RequestMapping("/listNews")
    public Page<NewsList> listNews(String category,String pageNum,String pageSize) {
        Page<NewsList> page=new Page<NewsList>();
        int pn=Integer.parseInt(pageNum);
        int ps=Integer.parseInt(pageSize);
        int pnps=pn*ps;
        //System.out.println("select blade_newslist.*,blade_attach.url from blade_newslist join blade_attach on blade_newslist.picture=blade_attach.id where category = ('"+ category +"') ORDER BY blade_newslist.contenttime DESC LIMIT "+pnps+","+ps+"");
        List<NewsList> newslist = Blade.create(NewsList.class).find("select nl.id,nl.category,nl.title,nl.summary,nl.contenttime from blade_newslist nl where category = ('"+ category +"') ORDER BY nl.contenttime DESC LIMIT "+pnps+","+ps+"", NewsList.class);
        Integer totalCount=Blade.create(NewsList.class).count("category=('"+ category +"')",NewsList.class);
        //System.out.println(totalCount);
        int totalpage=totalCount/ps;
        if (totalCount % ps>0){
            totalpage++;
        }
        page.setList(newslist);
        page.setTotalPage(totalpage);
        return page;
    }

    //前端获取详情页信息接口，根据点击量，浏览人数自增
    @Json
    @RequestMapping("/findView")
    public List<NewsList> listCon(String id) {

        Blade blade = Blade.create(NewsList.class);
        NewsList newslist = Blade.create(NewsList.class).findById(id);
        int  looknum = newslist.getLooknumber()+1;
        boolean temp = blade.updateBy("looknumber=("+looknum+")","id=("+newslist.getId()+")",newslist);
        List<NewsList> newslists = Blade.create(NewsList.class).find("select blade_newslist.* from blade_newslist where blade_newslist.id = (" + id + ")  ", NewsList.class);
        return newslists;
    }

    @RequestMapping(KEY_ADD)
   public String add(ModelMap mm) {
       mm.put("code", CODE);
       return BASE_PATH + "newslist_add.html";
   }
    @RequestMapping(KEY_ADD + "/{id}")
    public String add(@PathVariable Integer id, ModelMap mm) {
        if (null != id) {
            NewsList newslist = Blade.create(NewsList.class).findById(id);
            CMap cmap = CMap.parse(newslist);
            cmap.set("roleName", SysCache.getRoleName(newslist.getId()));
            mm.put("newslist", cmap);
            mm.put("code", CODE);
        }
        mm.put("code", CODE);
        return BASE_PATH + "dict_add.html";
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        NewsList newslist = Blade.create(NewsList.class).findById(id);
        mm.put("model", JsonKit.toJson(newslist));
        mm.put("code", CODE);
        return BASE_PATH + "newslist_edit.html";
    }

    @RequestMapping(KEY_VIEW + "/{id}")
    public String view(@PathVariable Integer id, ModelMap mm) {
        Blade blade = Blade.create(NewsList.class);
        NewsList newslist = blade.findById(id);
        CMap cmap = CMap.parse(newslist);
        //将“分类”字段显示成字典中对应的文字
        cmap.set("categoryName",SysCache.getDictName("108",newslist.getCategory()));
        mm.put("model", JsonKit.toJson(cmap));
        mm.put("code", CODE);
        return BASE_PATH + "newslist_view.html";
    }

    @Json
//    @Before(ContentValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
        NewsList newslist = mapping(PREFIX, NewsList.class);
        newslist.setCreatetime(new Date());
        Integer userid= (Integer) ShiroKit.getUser().getId();
        newslist.setCreater(userid);
        boolean temp = Blade.create(NewsList.class).save(newslist);
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
        NewsList newslist = mapping(PREFIX, NewsList.class);
        newslist.setUpdatetime(new Date());
        newslist.setUpdater((Integer) ShiroKit.getUser().getId());
        boolean temp =  Blade.create(NewsList.class).update(newslist);
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
        int cnt = Blade.create(NewsList.class).deleteByIds(getParameter("ids"));
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
        int cnt = Blade.create(NewsList.class).deleteByIds(getParameter("ids"));
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }


/*    private int findLastNum(String code){
        try{
            Blade blade = Blade.create(NewsList.class);
            NewsList NewsList = blade.findFirstBy("code = #{code} order by num desc", CMap.init().set("code", code));
            return NewsList.getLooknumber() + 1;
        }
        catch(Exception ex){
            return 1;
        }
    }*/


    @RequestMapping("/updateNewsList")
    public AjaxResult updatePassword(){
        Blade blade = Blade.create(NewsList.class);
        String newslistId = getParameter("newslist.id");
//        String password = getParameter("user.newPassword");
        NewsList newslist = blade.findById(newslistId);
        boolean temp = blade.update(newslist);
        if (temp) {
//            ShiroKit.clearCachedAuthenticationInfo(user.getAccount());
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
}







