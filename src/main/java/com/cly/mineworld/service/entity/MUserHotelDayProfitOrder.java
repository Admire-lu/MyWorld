package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户酒店每天收益历史订单
 * @author william
 *
 */
@Entity
@Table(name = "m_user_hotel_day_profit_order")
public class MUserHotelDayProfitOrder {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "user_hotel_id")
	private Integer userHotelId;//int(11) NOT NULL用户酒店id
	
	@Column(name = "user_hotel_profit")
	private Double userHotelProfit;//double NOT NULL原始收益
	
	@Column(name = "user_hotel_deduct_profit")
	private Double userHotelDeductProfit;//double NOT NULL被扣除收益
	
	@Column(name = "user_hotel_final_profit")
	private Double userHotelFinalProfit;//double NOT NULL
	
	@Column(name = "profit_date")
	private String profitDate;//varchar(45) NOT NULL收益日期
	
	@Column(name = "status")
	private Integer status;//int(1) NOT NULL
	
	@Column(name = "modify_time")
	private Integer modifyTime;//int(11) NOT NULL
	
	@Column(name = "create_time")
	private Integer createTime;//

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

	public Integer getUserHotelId() {
		return userHotelId;
	}

	public void setUserHotelId(Integer userHotelId) {
		this.userHotelId = userHotelId;
	}

	public Double getUserHotelProfit() {
		return userHotelProfit;
	}

	public void setUserHotelProfit(Double userHotelProfit) {
		this.userHotelProfit = userHotelProfit;
	}

	public Double getUserHotelDeductProfit() {
		return userHotelDeductProfit;
	}

	public void setUserHotelDeductProfit(Double userHotelDeductProfit) {
		this.userHotelDeductProfit = userHotelDeductProfit;
	}

	public Double getUserHotelFinalProfit() {
		return userHotelFinalProfit;
	}

	public void setUserHotelFinalProfit(Double userHotelFinalProfit) {
		this.userHotelFinalProfit = userHotelFinalProfit;
	}

	public String getProfitDate() {
		return profitDate;
	}

	public void setProfitDate(String profitDate) {
		this.profitDate = profitDate;
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
