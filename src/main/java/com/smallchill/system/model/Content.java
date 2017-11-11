package com.smallchill.system.model;

import com.smallchill.core.annotation.BindID;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 * Created by lenovo on 2017/7/14.
 */
@Table(name = "blade_content")
@BindID(name = "id")
@SuppressWarnings("serial")
//角色表
public class Content {
    private Integer id;
    private String contentid;
    private String title;
    private String content;
    private Date createtime;
    private Integer looknumber;
    private String picture;
    private String returnmessage;
    private String label;
    private String url;
    private String contents;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @AutoID
    @SeqID(name = "SEQ_OLOG")


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Integer getLooknumber() {
        return looknumber;
    }

    public void setLooknumber(Integer looknumber) {
        this.looknumber = looknumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getReturnmessage() {
        return returnmessage;
    }

    public void setReturnmessage(String returnmessage) {
        this.returnmessage = returnmessage;
    }
}
