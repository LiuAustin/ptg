package com.smallchill.system.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.system.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27/0025.
 */
//前端详情页显示数据通用接口
@CrossOrigin(origins = "*",maxAge = 3600)//Controller类或其方法上加@CrossOrigin注解，来使之支持跨域
@Controller
@RequestMapping("/commonfindview")
public class CommonFindViewController extends BaseController{

    @Json
    @RequestMapping("/findView")
    public Object commonInterface(String menuId,String id){
        switch (menuId)
        {
            case "1"://头条管理
                return listTop(Integer.parseInt(id));

            case "2"://新闻中心
                return listNews(Integer.parseInt(id));

            case "3"://特色工作
                return listFearticles(Integer.parseInt(id));

            case "4"://信息公开
                return listInarticles(Integer.parseInt(id));

            case "5"://法律工作
                return listLegal(Integer.parseInt(id));

        }

        //用if else判断也可以

        /*if (menuname.equals("新闻中心")){
            //System.out.println(listNews(Integer.parseInt(id)));
            List<NewsList> newslists=listNews(Integer.parseInt(id));
            return newslists;
        }else if (menuname.equals("信息公开")){
            List<InarticlesList> inarticleslists=listInarticles(Integer.parseInt(id));
            return inarticleslists;
        }else {
            List<LegalList> legalLists=listLegal(Integer.parseInt(id));
            return legalLists;
        }*/
        return null;
    }

    //头条管理：1
    public TopList listTop(Integer id) {
        Blade blade = Blade.create(TopList.class);
        TopList toplist = Blade.create(TopList.class).findById(id);
        int looknum = 0;
        try {
            looknum = toplist.getLooknumber()+1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        boolean temp = blade.updateBy("looknumber=(" + looknum + ")", "id=(" + toplist.getId() + ")", toplist);
        List<TopList> toplists = Blade.create(TopList.class).find("select tl.title,tl.articlesource,tl.contenttime,tl.author,tl.looknumber,tl.maincontent from blade_toplist tl where id = (" + id + ")  ", TopList.class);
        TopList tl = toplists.get(0);
        return tl;
        }
    }

    //新闻中心：2
    public NewsList listNews(Integer id) {
        Blade blade = Blade.create(NewsList.class);
        NewsList newslist = Blade.create(NewsList.class).findById(id);
        int looknum = 0;
        try {
            looknum = newslist.getLooknumber()+1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        boolean temp = blade.updateBy("looknumber=(" + looknum + ")", "id=(" + newslist.getId() + ")", newslist);
        List<NewsList> newslists = Blade.create(NewsList.class).find("select nl.title,nl.articlesource,nl.contenttime,nl.author,nl.looknumber,nl.maincontent from blade_newslist nl where id = " + id + " ", NewsList.class);
        NewsList nl = newslists.get(0);
        return nl;
        }
    }

    //特色工作：3
    public FearticlesList listFearticles(Integer id) {
        Blade blade = Blade.create(FearticlesList.class);
        FearticlesList fearticleslist = Blade.create(FearticlesList.class).findById(id);
        int looknum = 0;
        try {
            looknum = fearticleslist.getLooknumber()+1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        boolean temp = blade.updateBy("looknumber=("+looknum+")","id=("+fearticleslist.getId()+")",fearticleslist);
        List<FearticlesList> fearticleslists = Blade.create(FearticlesList.class).find("select fl.title,fl.articlesource,fl.contenttime,fl.author,fl.looknumber,fl.maincontent from blade_fearticleslist fl where id = (" + id + ")  ", FearticlesList.class);
        FearticlesList fl= fearticleslists.get(0);
        return fl;
        }
    }

    //信息公开：4
    public InarticlesList listInarticles(Integer id) {
        Blade blade = Blade.create(InarticlesList.class);
        InarticlesList inarticleslist = Blade.create(InarticlesList.class).findById(id);
        int looknum = 0;
        try {
            looknum = inarticleslist.getLooknumber()+1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        boolean temp = blade.updateBy("looknumber=(" + looknum + ")", "id=(" + inarticleslist.getId() + ")", inarticleslist);
        List<InarticlesList> inarticleslists = Blade.create(InarticlesList.class).find("select il.title,il.articlesource,il.contenttime,il.author,il.looknumber,il.maincontent from blade_inarticleslist il where id = " + id + " ", InarticlesList.class);
        InarticlesList il = inarticleslists.get(0);
        return il;
        }
    }

    //法律工作：5
    public LegalList listLegal(Integer id) {
        Blade blade = Blade.create(LegalList.class);
        LegalList legallist = Blade.create(LegalList.class).findById(id);
        int looknum = 0;
        try {
            looknum = legallist.getLooknumber()+1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        boolean temp = blade.updateBy("looknumber=(" + looknum + ")", "id=(" + legallist.getId() + ")", legallist);
        List<LegalList> legalLists = Blade.create(LegalList.class).find("select ll.title,ll.articlesource,ll.contenttime,ll.author,ll.looknumber,ll.maincontent from blade_legallist ll where id = " + id + " ", LegalList.class);
        LegalList ll = legalLists.get(0);
        return ll;
        }
    }
}
