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
import com.smallchill.system.model.FearticlesList;
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
@RequestMapping("/fearticleslist")
public class FearticleslistController extends BaseController{

    private static String LIST_SOURCE = "fearticleslist.list";
    private static String BASE_PATH = "/system/fearticleslist/";
    private static String CODE = "fearticleslist";
    private static String PREFIX = "blade_fearticleslist";

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "fearticleslist.html";
    }

/*    @RequestMapping(KEY_MAIN)
    public String index(ModelMap mm){
        mm.put("code", CODE);
        return BASE_PATH + "fearticleslist.html";
    }*/

    @Json
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }
    @Json
    @RequestMapping("/listAll")
    public Page<FearticlesList> listAll() {
        Page<FearticlesList> page=new Page<FearticlesList>();
        List<FearticlesList> fearticleslist = Blade.create(FearticlesList.class).find("select blade_fearticleslist.*,blade_attach.url from blade_fearticleslist join blade_attach on blade_fearticleslist.picture=blade_attach.id ORDER BY blade_fearticleslist.createtime DESC", FearticlesList.class);
        page.setList(fearticleslist);
        return page;
    }
    @Json
    @RequestMapping("/listFearticleslist")
    public Page<FearticlesList> listFearticleslist(String name) {
        Page<FearticlesList> page=new Page<FearticlesList>();
        List<FearticlesList> fearticleslist = Blade.create(FearticlesList.class).find("select blade_fearticleslist.*,blade_attach.url from blade_fearticleslist join blade_attach on blade_fearticleslist.picture=blade_attach.id where title like CONCAT('%',('" + name + "'),'%') ORDER BY blade_fearticleslist.createtime DESC ", FearticlesList.class);
        page.setList(fearticleslist);
        return page;
    }

    /*前端按分类展示的接口*/
    @Json
    @RequestMapping("/listFearticles")
    public Page<FearticlesList> listFearticles(String pageNum,String pageSize) {
        Page<FearticlesList> page=new Page<FearticlesList>();
        int pn=Integer.parseInt(pageNum);
        int ps=Integer.parseInt(pageSize);
        int pnps=pn*ps;
        List<FearticlesList> fearticleslist = Blade.create(FearticlesList.class).find("select blade_fearticleslist.*,blade_attach.url from blade_fearticleslist join blade_attach on blade_fearticleslist.picture=blade_attach.id ORDER BY blade_fearticleslist.contenttime DESC LIMIT "+pnps+","+ps+"", FearticlesList.class);
        Integer totalCount=Blade.create(FearticlesList.class).count("",FearticlesList.class);
        //System.out.println(totalCount);
        int totalpage=totalCount/ps;
        if (totalCount % ps>0){
            totalpage++;
        }
        page.setList(fearticleslist);
        page.setTotalPage(totalpage);
        return page;
    }

    //前端获取详情页信息接口，根据点击量，浏览人数自增
/*    @Json
    @RequestMapping("/findView")
    public List<FearticlesList> listCon(String id) {
        Blade blade = Blade.create(FearticlesList.class);
        FearticlesList fearticleslist = Blade.create(FearticlesList.class).findById(id);
        int  looknum = fearticleslist.getLooknumber()+1;
        boolean temp = blade.updateBy("looknumber=("+looknum+")","id=("+fearticleslist.getId()+")",fearticleslist);
        List<FearticlesList> fearticleslists = Blade.create(FearticlesList.class).find("select blade_fearticleslist.* from blade_fearticleslist where blade_fearticleslist.id = (" + id + ")  ", FearticlesList.class);
        return fearticleslists;
    }*/

    @RequestMapping(KEY_ADD)
   public String add(ModelMap mm) {
       mm.put("code", CODE);
       return BASE_PATH + "fearticleslist_add.html";
   }
    @RequestMapping(KEY_ADD + "/{id}")
    public String add(@PathVariable Integer id, ModelMap mm) {
        if (null != id) {
            FearticlesList fearticleslist = Blade.create(FearticlesList.class).findById(id);
            CMap cmap = CMap.parse(fearticleslist);
            cmap.set("roleName", SysCache.getRoleName(fearticleslist.getId()));
            mm.put("toplist", cmap);
            mm.put("code", CODE);
        }
        mm.put("code", CODE);
        return BASE_PATH + "dict_add.html";
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable Integer id, ModelMap mm) {
        FearticlesList fearticleslist = Blade.create(FearticlesList.class).findById(id);
        mm.put("model", JsonKit.toJson(fearticleslist));
        mm.put("code", CODE);
        return BASE_PATH + "fearticleslist_edit.html";
    }

    @RequestMapping(KEY_VIEW + "/{id}")
    public String view(@PathVariable Integer id, ModelMap mm) {
        Blade blade = Blade.create(FearticlesList.class);
        FearticlesList fearticleslist = blade.findById(id);
        CMap cmap = CMap.parse(fearticleslist);
        //将“分类”字段显示成字典中对应的文字
        cmap.set("categoryName",SysCache.getDictName("108",fearticleslist.getCategory()));
        mm.put("model", JsonKit.toJson(cmap));
        mm.put("code", CODE);
        return BASE_PATH + "fearticleslist_view.html";
    }

    @Json
//    @Before(ContentValidator.class)
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
        FearticlesList fearticleslist = mapping(PREFIX, FearticlesList.class);
        fearticleslist.setCreatetime(new Date());
        Integer userid= (Integer) ShiroKit.getUser().getId();
        fearticleslist.setCreater(userid);
        boolean temp = Blade.create(FearticlesList.class).save(fearticleslist);
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







