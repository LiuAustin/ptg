package com.smallchill.system.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

@Table(name = "blade_user")
@BindID(name = "id")
@SuppressWarnings("serial")
//用户表
public class User extends BaseModel {
	private Integer id; //主键
	private String account; //账号
	private Integer deptid; //部门id
	private String email; //邮箱
	private String name; //姓名
	private String password; //密码
	private String salt; //密码盐
	private String phone; //手机号
	private String roleid; //角色id
	private Integer sex; //性别
	private Integer status; //状态
	private Integer version; //版本号
	private Date birthday; //生日
	private Date createtime; //创建时间
	//新增
	private Integer recommendnum;//推荐人编号
	private Integer servicecenter;//服务中心编号
	private String securepwd;//安全密码（提现使用）
	private String realname;//真实姓名
	private Integer bankcardtype;//银行卡类型
	private String bankaccount;//银行卡账号信息
	private String bankdeposit;//开户行
	private String bankaddress;
	private Integer phoneNum;
	private Integer qqNum;
	private String wechataccount;
	private String receivername;
	private String deliveryaddress;
	private String purchasedgoods;
	private Integer creatorid;
	private String createruser;
	private Date audittime;
	private Integer auditorid;
	private String auditeruser;
	private Integer usernum;
	private Integer auditstate;
	private double integral;//积分
	private double vouchers;//购物券
	private double currentintegral;//当前积分总和
	private Integer cashstatus;//提现状态
	private double cashintegral;

	@AutoID //mysql自增
	@SeqID(name = "SEQ_USER") //oracle sequence自增
	//两者只需要写一个,根据数据库不同来选择
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

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	//新增
	public Integer getRecommendnum() {
		return recommendnum;
	}

	public void setRecommendnum(Integer recommendnum) {
		this.recommendnum = recommendnum;
	}

	public Integer getServicecenter() {
		return servicecenter;
	}

	public void setServicecenter(Integer servicecenter) {
		this.servicecenter = servicecenter;
	}

	public String getSecurepwd() {
		return securepwd;
	}

	public void setSecurepwd(String securepwd) {
		this.securepwd = securepwd;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getBankcardtype() {
		return bankcardtype;
	}

	public void setBankcardtype(Integer bankcardtype) {
		this.bankcardtype = bankcardtype;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getBankdeposit() {
		return bankdeposit;
	}

	public void setBankdeposit(String bankdeposit) {
		this.bankdeposit = bankdeposit;
	}

	public String getBankaddress() {
		return bankaddress;
	}

	public void setBankaddress(String bankaddress) {
		this.bankaddress = bankaddress;
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

	public String getWechataccount() {
		return wechataccount;
	}

	public void setWechataccount(String wechataccount) {
		this.wechataccount = wechataccount;
	}

	public String getReceivername() {
		return receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	public String getDeliveryaddress() {
		return deliveryaddress;
	}

	public void setDeliveryaddress(String deliveryaddress) {
		this.deliveryaddress = deliveryaddress;
	}

	public String getPurchasedgoods() {
		return purchasedgoods;
	}

	public void setPurchasedgoods(String purchasedgoods) {
		this.purchasedgoods = purchasedgoods;
	}

	public Integer getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(Integer creatorid) {
		this.creatorid = creatorid;
	}

	public String getCreateruser() {
		return createruser;
	}

	public void setCreateruser(String createruser) {
		this.createruser = createruser;
	}

	public Date getAudittime() {
		return audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	public Integer getAuditorid() {
		return auditorid;
	}

	public void setAuditorid(Integer auditorid) {
		this.auditorid = auditorid;
	}

	public String getAuditeruser() {
		return auditeruser;
	}

	public void setAuditeruser(String auditeruser) {
		this.auditeruser = auditeruser;
	}

	public Integer getUsernum() {
		return usernum;
	}

	public void setUsernum(Integer usernum) {
		this.usernum = usernum;
	}

	public Integer getAuditstate() {
		return auditstate;
	}

	public void setAuditstate(Integer auditstate) {
		this.auditstate = auditstate;
	}

	public double getIntegral() {
		return integral;
	}

	public void setIntegral(double integral) {
		this.integral = integral;
	}

	public double getVouchers() {
		return vouchers;
	}

	public void setVouchers(double vouchers) {
		this.vouchers = vouchers;
	}

	public double getCurrentintegral() {
		return currentintegral;
	}

	public void setCurrentintegral(double currentintegral) {
		this.currentintegral = currentintegral;
	}

	public Integer getCashstatus() {
		return cashstatus;
	}

	public void setCashstatus(Integer cashstatus) {
		this.cashstatus = cashstatus;
	}

	public double getCashintegral() {
		return cashintegral;
	}

	public void setCashintegral(double cashintegral) {
		this.cashintegral = cashintegral;
	}
}
