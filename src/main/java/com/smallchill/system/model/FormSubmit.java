package com.smallchill.system.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

@Table(name = "blade_formsubmit")
@BindID(name = "id")
@SuppressWarnings("serial")
public class FormSubmit extends BaseModel{
    private Integer id;

    private Integer recommendNum;//推荐人编号

    private Integer serviceCenter;//服务中心编号

    private String username;//登录用户名

    private String password;//登陆密码（默认1-6）

    private String salt;//密码盐

    private String securePwd;//安全密码（提现使用）

    private String ssalt;//密码盐

    private String realName;//真实姓名

    private Integer bankCardType;//银行卡类型

    private String bankAccount;//银行卡账号信息

    private String bankDeposit;//开户行

    private String bankAddress;

    private Integer phoneNum;

    private Integer qqNum;

    private String wechatAccount;

    private String receiverName;

    private String deliveryAddress;

    private String purchasedGoods;

    private Integer roleId;

    private Date createTime;

    private Integer creatorId;

    private Date auditTime;

    private Integer auditorId;

    private Integer userNum;
    
    private Integer auditState;

    @AutoID
    @SeqID(name = "SEQ_USER")

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecommendNum() {
        return recommendNum;
    }

    public void setRecommendNum(Integer recommendNum) {
        this.recommendNum = recommendNum;
    }

    public Integer getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(Integer serviceCenter) {
        this.serviceCenter = serviceCenter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSecurePwd() {
        return securePwd;
    }

    public void setSecurePwd(String securePwd) {
        this.securePwd = securePwd;
    }

    public String getSsalt() {
        return ssalt;
    }

    public void setSsalt(String ssalt) {
        this.ssalt = ssalt;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(Integer bankCardType) {
        this.bankCardType = bankCardType;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankDeposit() {
        return bankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getQqNum() {
        return qqNum;
    }

    public void setQqNum(Integer qqNum) {
        this.qqNum = qqNum;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPurchasedGoods() {
        return purchasedGoods;
    }

    public void setPurchasedGoods(String purchasedGoods) {
        this.purchasedGoods = purchasedGoods;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }
}