package com.smallchill.system.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 * Created by lqh on 2017/11/08.
 */
@Table(name = "blade_cashapply")
@BindID(name = "id")
@SuppressWarnings("serial")
//提现申请跟踪表
public class CashApply extends BaseModel{
    private Integer id;//主键
    private String account;//登录用户名
    private double cashintegral;//用户需要体现的积分
    private Date cashtime;//申请提现时间
    private Date audittime;//审核提现时间
    private Integer cashstatus;//提现状态
    private String auditeruser;//审核人

    @AutoID
    @SeqID(name = "SEQ_CASHAPPLY")

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getCashintegral() {
        return cashintegral;
    }

    public void setCashintegral(double cashintegral) {
        this.cashintegral = cashintegral;
    }

    public Date getCashtime() {
        return cashtime;
    }

    public void setCashtime(Date cashtime) {
        this.cashtime = cashtime;
    }

    public Date getAudittime() {
        return audittime;
    }

    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    public Integer getCashstatus() {
        return cashstatus;
    }

    public void setCashstatus(Integer cashstatus) {
        this.cashstatus = cashstatus;
    }

    public String getAuditeruser() {
        return auditeruser;
    }

    public void setAuditeruser(String auditeruser) {
        this.auditeruser = auditeruser;
    }
}
