package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户充值订单实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_recharge_order")
public class MUserRechargeOrder {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "user_str_id")
	private String userStrId;//varchar(45) NOT NULL用户strid
	
	@Column(name = "user_token_address")
	private String userTokenAddress;//varchar(500) NOT NULL用户代币地址
	
	@Column(name = "recharge_type")
	private Integer rechargeType;//int(2) NOT NULL充值类型：1=VAD
	
	@Column(name = "recharge_token_type")
	private Integer rechargeTokenType;//int(2) NOT NULL充值的代币类型：1=USDT
	
	@Column(name = "recharge_token_order_id")
	private String rechargeTokenOrderId;//text NOT NULL代币充值订单id
	
	@Column(name = "recharge_token_order_timestamp")
	private Integer rechargeTokenOrderTimestamp;//int(11) NOT NULL代币充值订单时间戳
	
	@Column(name = "recharge_token_order_amount")
	private Double rechargeTokenOrderAmount;//double NOT NULL代币充值金额
	
	@Column(name = "recharge_amount")
	private Double rechargeAmount;//double NOT NULL充值金额
	
	@Column(name = "recharge_category")
	private Integer rechargeCategory;//充值类别：1=用户自主充值，2=后台代充
	
	@Column(name = "status")
	private Integer status;//int(1) NOT NULL
	
	@Column(name = "modify_time")
	private Integer modifyTime;//int(11) NOT NULL
	
	@Column(name = "create_time")
	private Integer createTime;//int(11) NOT NULL

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

	public String getUserStrId() {
		return userStrId;
	}

	public void setUserStrId(String userStrId) {
		this.userStrId = userStrId;
	}

	public String getUserTokenAddress() {
		return userTokenAddress;
	}

	public void setUserTokenAddress(String userTokenAddress) {
		this.userTokenAddress = userTokenAddress;
	}

	public Integer getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}

	public Integer getRechargeTokenType() {
		return rechargeTokenType;
	}

	public void setRechargeTokenType(Integer rechargeTokenType) {
		this.rechargeTokenType = rechargeTokenType;
	}

	public String getRechargeTokenOrderId() {
		return rechargeTokenOrderId;
	}

	public void setRechargeTokenOrderId(String rechargeTokenOrderId) {
		this.rechargeTokenOrderId = rechargeTokenOrderId;
	}

	public Integer getRechargeTokenOrderTimestamp() {
		return rechargeTokenOrderTimestamp;
	}

	public void setRechargeTokenOrderTimestamp(Integer rechargeTokenOrderTimestamp) {
		this.rechargeTokenOrderTimestamp = rechargeTokenOrderTimestamp;
	}

	public Double getRechargeTokenOrderAmount() {
		return rechargeTokenOrderAmount;
	}

	public void setRechargeTokenOrderAmount(Double rechargeTokenOrderAmount) {
		this.rechargeTokenOrderAmount = rechargeTokenOrderAmount;
	}

	public Double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
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

	public Integer getRechargeCategory() {
		return rechargeCategory;
	}

	public void setRechargeCategory(Integer rechargeCategory) {
		this.rechargeCategory = rechargeCategory;
	}
}
