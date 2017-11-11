package com.smallchill.system.model;

import com.smallchill.core.annotation.BindID;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 * Created by lenovo on 2017/7/14.
 */
@Table(name = "blade_comment")
@BindID(name = "id")
@SuppressWarnings("serial")
//角色表
public class Comment {
    private Integer id;
    private String comment_id;
    private String comment_uid;
    private String comment_content;
    private String comment_score;
    private String comment_state;
    private Date comment_createtime;
    private String comment_content_t;




    @AutoID
    @SeqID(name = "SEQ_OLOG")

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_uid() {
        return comment_uid;
    }

    public void setComment_uid(String comment_uid) {
        this.comment_uid = comment_uid;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_score() {
        return comment_score;
    }

    public void setComment_score(String comment_score) {
        this.comment_score = comment_score;
    }

    public String getComment_state() {
        return comment_state;
    }

    public void setComment_state(String comment_state) {
        this.comment_state = comment_state;
    }

    public Date getComment_createtime() {
        return comment_createtime;
    }

    public void setComment_createtime(Date comment_createtime) {
        this.comment_createtime = comment_createtime;
    }

    public String getComment_content_t() {
        return comment_content_t;
    }

    public void setComment_content_t(String comment_content_t) {
        this.comment_content_t = comment_content_t;
    }
}
