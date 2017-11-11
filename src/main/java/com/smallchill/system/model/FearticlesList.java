package com.smallchill.system.model;

import com.smallchill.core.annotation.BindID;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;


/**
 * Created by Administrator on 2017/10/19/0019.
 */

@Table(name = "blade_fearticleslist")
@BindID(name = "id")
@SuppressWarnings("serial")
//头条列表
public class FearticlesList {
    private Integer id;
    private String category;
    private String picture;
    private String summary;
    private Integer creater;
    private Integer updater;
    private Date contenttime;
    private Date createtime;
    private Date updatetime;
    private String title;
    private String maincontent;
    private String jumplink;
    private String url;
    private String articlesource;
    private String author;
    private Integer looknumber;

    @AutoID
    @SeqID(name = "SEQ_FEARTICLESLIST")
    //@SeqID(name = "SEQ_OLOG")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public Date getContenttime() {
        return contenttime;
    }

    public void setContenttime(Date contenttime) {
        this.contenttime = contenttime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaincontent() {
        return maincontent;
    }

    public void setMaincontent(String maincontent) {
        this.maincontent = maincontent;
    }

    public String getJumplink() {
        return jumplink;
    }

    public void setJumplink(String jumplink) {
        this.jumplink = jumplink;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url=url;
    }

    public String getArticlesource() {
        return articlesource;
    }

    public void setArticlesource(String articlesource) {
        this.articlesource = articlesource;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getLooknumber() {
        return looknumber;
    }

    public void setLooknumber(Integer looknumber) {
        this.looknumber = looknumber;
    }
}
