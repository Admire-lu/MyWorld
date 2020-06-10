package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户收益实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_profit")
public class MUserProfit {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "user_str_id")
	private String userStrId;//varchar(45) NOT NULL
	
	@Column(name = "profit_category")
	private String profitCategory;//varchar(10) NOT NULL收益类别：1=静态收益，2=动态收益
	
	@Column(name = "profit_amount")
	private Double profitAmount;//double NOT NULL收益金额
	
	@Column(name = "profit_remarks")
	private String profitRemarks;//varchar(45) NOT NULL
	
	@Column(name = "before_amount")
	private Double beforeAmount;//double NOT NULL增加前的金额
	
	@Column(name = "after_amount")
	private Double afterAmount;//double NOT NULL增加后的金额
	
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

	public String getProfitCategory() {
		return profitCategory;
	}

	public void setProfitCategory(String profitCategory) {
		this.profitCategory = profitCategory;
	}

	public Double getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(Double profitAmount) {
		this.profitAmount = profitAmount;
	}

	public String getProfitRemarks() {
		return profitRemarks;
	}

	public void setProfitRemarks(String profitRemarks) {
		this.profitRemarks = profitRemarks;
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
}
