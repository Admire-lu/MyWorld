package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user")
public class MUser {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "nickname")
	private String nickname;//varchar(45) NOT NULL
	
	@Column(name = "psw")
	private String psw;//varchar(45) NOT NULL
	
	@Column(name = "mobile")
	private String mobile;//varchar(13) NULL
	
	@Column(name = "email")
	private String email;//varchar(45) NULL
	
	@Column(name = "head_image")
	private String headImage;//varchar(200) NULL
	
	@Column(name = "transaction_psw")
	private String transactionPsw;//varchar(45) NULL交易密码
	
	@Column(name = "user_type")
	private String userType;//varchar(45) NOT NULL用户类型
	
	@Column(name = "exp")
	private Integer exp;//int(11) NOT NULL
	
	@Column(name = "lv")
	private Integer lv;//int(11) NOT NULL
	
	@Column(name = "vad")
	private Double vad;//double NOT NULL
	
	@Column(name = "deh")
	private Double deh;//
	
	@Column(name = "user_level_code")
	private String userLevelCode;
	
	@Column(name = "status")
	private Integer status;//int(1) NOT NULL
	
	@Column(name = "modify_time")
	private Integer modifyTime;//int(11) NOT NULL
	
	@Column(name = "create_time")
	private Integer createTime;//int(11) NOT NULL

	@Column(name = "token_address")
	private String tokenAddress;//varchar(200) DEFAULT NULL COMMENT 'token地址'@Column(name = "token_address")

	@Column(name = "freeze_amount")
	private Double freezeAmount; // double DEFAULT '0' COMMENT '冻结金额',




	public Double getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Double freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStrId() {
		return strId;
	}

	public void setStrId(String strId) {
		this.strId = strId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getTransactionPsw() {
		return transactionPsw;
	}

	public void setTransactionPsw(String transactionPsw) {
		this.transactionPsw = transactionPsw;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Integer getLv() {
		return lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	public Double getVad() {
		return vad;
	}

	public void setVad(Double vad) {
		this.vad = vad;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Integer modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public String getUserLevelCode() {
		return userLevelCode;
	}

	public void setUserLevelCode(String userLevelCode) {
		this.userLevelCode = userLevelCode;
	}

	public String getTokenAddress() {
		return tokenAddress;
	}

	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	}

	public Double getDeh() {
		return deh;
	}

	public void setDeh(Double deh) {
		this.deh = deh;
	}
}
