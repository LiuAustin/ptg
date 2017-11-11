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
import com.smallchill.system.model.LegalList;
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
@RequestMapping("/legallist")
public class LegallistController extends BaseController{

    private static String LIST_SOURCE = "legallist.list";
    private static String BASE_PATH = "/system/legallist/";
    private static String CODE = "legallist";
    private static String PREFIX = "blade_legallist";

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "legallist.html";
    }

/*    @RequestMapping(KEY_MAIN)
    public String index(ModelMap mm){
        mm.put("code", CODE);
        return BASE_PATH + "legallist.html";
    }*/

    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }
    @Json
    @RequestMapping("/listAll")
    public Page<LegalList> listAll() {
        Page<LegalList> page=new Page<LegalList>();
        List<LegalList> legallist = Blade.create(LegalList.class).find("select blade_legallist.*,blade_attach.url from blade_legallist join blade_attach on blade_legallist.picture=blade_attach.id ORDER BY blade_legallist.createtime DESC", LegalList.class);
        page.setList(legallist);
        return page;
    }
    @Json
    @RequestMapping("/listLegallist")
    public Page<LegalList> listLegallist(String name) {
        Page<LegalList> page=new Page<LegalList>();
        List<LegalList> legallist = Blade.create(LegalList.class).find("select blade_legallist.*,blade_attach.url from blade_legallist join blade_attach on blade_legallist.picture=blade_attach.id where title like CONCAT('%',('" + name + "'),'%') ORDER BY blade_legallist.createtime DESC ", LegalList.class);
        page.setList(legallist);
        return page;
    }

    /*前端按分类展示的接口*/
    @Json
    @RequestMapping("/listLegal")
    public Page<LegalList> listLegal(String legalcategory,String pageNum,String pageSize) {
        Page<LegalList> page=new Page<LegalList>();
        int pn=Integer.parseInt(pageNum);
        int ps=Integer.parseInt(pageSize);
        int pnps=pn*ps;
        List<LegalList> legallist = Blade.create(LegalList.class).find("select ll.id,ll.legalcategory,ll.title,ll.summary,ll.contenttime from blade_legallist ll where legalcategory = ('"+ legalcategory +"') ORDER BY ll.contenttime DESC LIMIT "+pnps+","+ps+"", LegalList.class);
        Integer totalCount=Blade.create(LegalList.class).count("legalcategory=('"+legalcategory+"')",LegalList.class);
        int totalpage=totalCount/ps;
        if (totalCount % ps>0){
            totalpage++;
        }
        page.setList(legallist);
        page.setTotalPage(totalpage);
        return page;
    }

    //前端获取详情页信息接口，根据点击量，浏览人数自增
/*    @Json
    @RequestMapping("/findView")
    public List<LegalList> listCon(String id) {
        Blade blade = Blade.create(LegalList.class);
        LegalList legallist = Blade.create(LegalList.class).findById(id);
        int  looknum = legallist.getLooknumber()+1;
        boolean temp = blade.updateBy("looknumber=("+looknum+")","id=("+legallist.getId()+")",legallist);
        List<LegalList> legalLists = Blade.create(LegalList.class).find("select blade_legallist.* from blade_legallist where blade_legallist.id = (" + id + ")  ", LegalList.class);
        return legalLists;
    }*/

    @RequestMapping(KEY_ADD)
   public String add(ModelMap mm) {
       mm.put("code", CODE);
       return BASE_PATH + "legallist_add.html";
   }
    @RequestMapping(KEY_ADD + "/{id}")
    public String add(@PathVariable Integer id, ModelMap mm) {
        if (null != id) {
            LegalList legallist = Blade.create(LegalList.class).findById(id);
            CMap cmap = CMap.parse(legallist);
            cmap.set("roleName", SysCache.getRoleName(legallist.getId()));
            mm.put("legallist", cmap);
            mm.put("code", CODE);
        }
        mm.put("code", CODE);
        return BASE_PATH + "dict_add.html";
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        LegalList legallist = Blade.create(LegalList.class).findById(id);
        mm.put("model", JsonKit.toJson(legallist));
        mm.put("code", CODE);
        return BASE_PATH + "legallist_edit.html";
    }

    @RequestMapping(KEY_VIEW + "/{id}")
    public String view(@PathVariable Integer id, ModelMap mm) {
        Blade blade = Blade.create(LegalList.class);
        LegalList legallist = blade.findById(id);
        CMap cmap = CMap.parse(legallist);
        //将“分类”字段显示成字典中对应的文字
        cmap.set("legalcategoryName",SysCache.getDictName("118",legallist.getLegalcategory()));
        mm.put("model", JsonKit.toJson(cmap));
        mm.put("code", CODE);
        return BASE_PATH + "legallist_view.html";
    }

    @Json
//    @Before(ContentValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
        LegalList legallist = mapping(PREFIX, LegalList.class);
        legallist.setCreatetime(new Date());
        Integer userid= (Integer) ShiroKit.getUser().getId();
        legallist.setCreater(userid);
        boolean temp = Blade.create(LegalList.class).save(legallist);
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
        LegalList legallist = mapping(PREFIX, LegalList.class);
        legallist.setUpdatetime(new Date());
        legallist.setUpdater((Integer) ShiroKit.getUser().getId());
        boolean temp =  Blade.create(LegalList.class).update(legallist);
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
        int cnt = Blade.create(LegalList.class).deleteByIds(getParameter("ids"));
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
        int cnt = Blade.create(LegalList.class).deleteByIds(getParameter("ids"));
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
        Blade blade = Blade.create(LegalList.class);
        String legallistId = getParameter("toplist.id");
//        String password = getParameter("user.newPassword");
        LegalList legallist = blade.findById(legallistId);
        boolean temp = blade.update(legallist);
        if (temp) {
//            ShiroKit.clearCachedAuthenticationInfo(user.getAccount());
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
}







