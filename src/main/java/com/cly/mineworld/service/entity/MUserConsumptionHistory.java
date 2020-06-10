package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户消费历史实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_consumption_history")
public class MUserConsumptionHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "user_str_id")
	private String userStrId;//varchar(45) NOT NULL
	
	@Column(name = "consumption_category_str_id")
	private String consumptionCategoryStrId;//varchar(45) NOT NULL消费类别strId
	
	@Column(name = "amount")
	private Double amount;//double NOT NULL消费的金额
	
	@Column(name = "before_amount")
	private Double beforeAmount;//double NOT NULL消费前的总金额
	
	@Column(name = "after_amount")
	private Double afterAmount;//double NOT NULL消费后的总金额
	
	@Column(name = "sub_order_str_id")
	private String subOrderStrId;
	
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

	public String getConsumptionCategoryStrId() {
		return consumptionCategoryStrId;
	}

	public void setConsumptionCategoryStrId(String consumptionCategoryStrId) {
		this.consumptionCategoryStrId = consumptionCategoryStrId;
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

	public String getSubOrderStrId() {
		return subOrderStrId;
	}

	public void setSubOrderStrId(String subOrderStrId) {
		this.subOrderStrId = subOrderStrId;
	}
}
