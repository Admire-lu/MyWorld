package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户资金明细订单实体
 * @author WilliamLam
 *
 */
@Entity
@Table(name = "m_user_fund_detail_order")
public class MUserFundDetailOrder {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "user_str_id")
	private String userStrId;//varchar(45) NOT NULL
	
	@Column(name = "order_category")
	private Integer orderCategory;//int(2) NOT NULL订单类别：1=转入明细，2=转出明细，3=支出明细，4=奖励明细，5=充币记录，6=提币记录
	
	@Column(name = "relation_order_str_id")
	private String relationOrderStrId;//varchar(45) NOT NULL关联订单strId
	
	@Column(name = "token_category")
	private Integer tokenCategory;//int(2) NOT NULL代币类别：1=VAD，2=DEH
	
	@Column(name = "amount")
	private Double amount;//double NOT NULL交易金额
	
	@Column(name = "before_amount")
	private Double beforeAmount;//double NOT NULL交易之前的用户代币数量
	
	@Column(name = "after_amount")
	private Double afterAmount;//double NOT NULL交易后的用户代币数量
	
	@Column(name = "remarks")
	private String remarks;//varchar(200) NOT NULL备注
	
	@Column(name = "order_status")
	private Integer orderStatus;//int(1) NOT NULL订单状态：1=完成，2=失败
	
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

	public Integer getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(Integer orderCategory) {
		this.orderCategory = orderCategory;
	}

	public String getRelationOrderStrId() {
		return relationOrderStrId;
	}

	public void setRelationOrderStrId(String relationOrderStrId) {
		this.relationOrderStrId = relationOrderStrId;
	}

	public Integer getTokenCategory() {
		return tokenCategory;
	}

	public void setTokenCategory(Integer tokenCategory) {
		this.tokenCategory = tokenCategory;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBeforeAmount() {
		return beforeAmount;
	}

	public void setBeforeAmount(Double beforeAmount) {
		this.beforeAmount = beforeAmount;
	}

	public Double getAfterAmount() {
		return afterAmount;
	}

	public void setAfterAmount(Double afterAmount) {
		this.afterAmount = afterAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
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
}
