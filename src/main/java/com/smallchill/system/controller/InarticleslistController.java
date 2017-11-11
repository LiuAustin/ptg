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
import com.smallchill.system.model.InarticlesList;
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
@RequestMapping("/inarticleslist")
public class InarticleslistController extends BaseController{

    private static String LIST_SOURCE = "inarticleslist.list";
    private static String BASE_PATH = "/system/inarticleslist/";
    private static String CODE = "inarticleslist";
    private static String PREFIX = "blade_inarticleslist";

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "inarticleslist.html";
    }

/*    @RequestMapping(KEY_MAIN)
    public String index(ModelMap mm){
        mm.put("code", CODE);
        return BASE_PATH + "inarticleslist.html";
    }*/

    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }
    @Json
    @RequestMapping("/listAll")
    public Page<InarticlesList> listAll() {
        Page<InarticlesList> page=new Page<InarticlesList>();
        List<InarticlesList> inarticleslist = Blade.create(InarticlesList.class).find("select blade_inarticleslist.*,blade_attach.url from blade_inarticleslist join blade_attach on blade_inarticleslist.picture=blade_attach.id ORDER BY blade_inarticleslist.createtime DESC", InarticlesList.class);
        page.setList(inarticleslist);
        return page;
    }
    @Json
    @RequestMapping("/listInarticleslist")
    public Page<InarticlesList> listInarticleslist(String name) {
        Page<InarticlesList> page=new Page<InarticlesList>();
        List<InarticlesList> inarticleslist = Blade.create(InarticlesList.class).find("select blade_inarticleslist.*,blade_attach.url from blade_inarticleslist join blade_attach on blade_inarticleslist.picture=blade_attach.id where title like CONCAT('%',('" + name + "'),'%') ORDER BY blade_inarticleslist.createtime DESC ", InarticlesList.class);
        page.setList(inarticleslist);
        return page;
    }

    /*前端按分类展示的接口*/
    @Json
    @RequestMapping("/listInarticles")
    public Page<InarticlesList> listInarticles(String category,String pageNum,String pageSize) {
        Page<InarticlesList> page=new Page<InarticlesList>();
        int pn=Integer.parseInt(pageNum);
        int ps=Integer.parseInt(pageSize);
        int pnps=pn*ps;
        List<InarticlesList> inarticleslist = Blade.create(InarticlesList.class).find("select il.id,il.category,il.title,il.summary,il.contenttime from blade_inarticleslist il where category = ('"+ category +"') ORDER BY il.contenttime DESC LIMIT "+pnps+","+ps+"", InarticlesList.class);
        Integer totalCount=Blade.create(InarticlesList.class).count("category=('"+category+"')",InarticlesList.class);
        int totalpage=totalCount/ps;
        if (totalCount % ps>0){
            totalpage++;
        }
        page.setList(inarticleslist);
        page.setTotalPage(totalpage);
        return page;
    }

    //前端获取详情页信息接口，根据点击量，浏览人数自增
/*    @Json
    @RequestMapping("/findView")
    public List<InarticlesList> listCon(String id) {
        Blade blade = Blade.create(InarticlesList.class);
        InarticlesList inarticleslist = Blade.create(InarticlesList.class).findById(id);
        int  looknum = inarticleslist.getLooknumber()+1;
        boolean temp = blade.updateBy("looknumber=("+looknum+")","id=("+inarticleslist.getId()+")",inarticleslist);
        List<InarticlesList> inarticleslists = Blade.create(InarticlesList.class).find("select blade_inarticleslist.* from blade_inarticleslist where blade_inarticleslist.id = (" + id + ")  ", InarticlesList.class);
        return inarticleslists;
    }*/

    @RequestMapping(KEY_ADD)
   public String add(ModelMap mm) {
       mm.put("code", CODE);
       return BASE_PATH + "inarticleslist_add.html";
   }
    @RequestMapping(KEY_ADD + "/{id}")
    public String add(@PathVariable Integer id, ModelMap mm) {
        if (null != id) {
            InarticlesList inarticleslist = Blade.create(InarticlesList.class).findById(id);
            CMap cmap = CMap.parse(inarticleslist);
            cmap.set("roleName", SysCache.getRoleName(inarticleslist.getId()));
            mm.put("toplist", cmap);
            mm.put("code", CODE);
        }
        mm.put("code", CODE);
        return BASE_PATH + "dict_add.html";
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        InarticlesList inarticleslist = Blade.create(InarticlesList.class).findById(id);
        mm.put("model", JsonKit.toJson(inarticleslist));
        mm.put("code", CODE);
        return BASE_PATH + "inarticleslist_edit.html";
    }

    @RequestMapping(KEY_VIEW + "/{id}")
    public String view(@PathVariable Integer id, ModelMap mm) {
        Blade blade = Blade.create(InarticlesList.class);
        InarticlesList inarticleslist = blade.findById(id);
        CMap cmap = CMap.parse(inarticleslist);
        //将“分类”字段显示成字典中对应的文字
        cmap.set("categoryName",SysCache.getDictName("108",inarticleslist.getCategory()));
        mm.put("model", JsonKit.toJson(cmap));
        mm.put("code", CODE);
        return BASE_PATH + "inarticleslist_view.html";
    }

    @Json
//    @Before(ContentValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
        InarticlesList inarticleslist = mapping(PREFIX, InarticlesList.class);
        inarticleslist.setCreatetime(new Date());
        Integer userid= (Integer) ShiroKit.getUser().getId();
        inarticleslist.setCreater(userid);
        boolean temp = Blade.create(InarticlesList.class).save(inarticleslist);
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
        InarticlesList inarticleslist = mapping(PREFIX, InarticlesList.class);
        inarticleslist.setUpdatetime(new Date());
        inarticleslist.setUpdater((Integer) ShiroKit.getUser().getId());
        boolean temp =  Blade.create(InarticlesList.class).update(inarticleslist);
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
        int cnt = Blade.create(InarticlesList.class).deleteByIds(getParameter("ids"));
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
        int cnt = Blade.create(InarticlesList.class).deleteByIds(getParameter("ids"));
        if (cnt > 0) {
            CacheKit.removeAll(SYS_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }


/*    private int findLastNum(String code){
        try{
            Blade blade = Blade.create(InarticlesList.class);
            InarticlesList inarticleslist = blade.findFirstBy("code = #{code} order by num desc", CMap.init().set("code", code));
            return inarticleslist.getLooknumber() + 1;
        }
        catch(Exception ex){
            return 1;
        }
    }*/


    @RequestMapping("/updateInarticleslist")
    public AjaxResult updatePassword(){
        Blade blade = Blade.create(InarticlesList.class);
        String inarticleslistId = getParameter("toplist.id");
//        String password = getParameter("user.newPassword");
        InarticlesList inarticleslist = blade.findById(inarticleslistId);
        boolean temp = blade.update(inarticleslist);
        if (temp) {
//            ShiroKit.clearCachedAuthenticationInfo(user.getAccount());
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }
}







