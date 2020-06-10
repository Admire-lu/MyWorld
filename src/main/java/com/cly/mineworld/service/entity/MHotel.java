package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 酒店实体
 * @author william
 *
 */
@Entity
@Table(name = "m_hotel")
public class MHotel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "hotel_name")
	private String hotelName;//varchar(45) NOT NULL酒店名称
	
	@Column(name = "hotel_desc")
	private String hotelDesc;//varchar(200) NOT NULL酒店描述
	
	@Column(name = "hotel_image")
	private String hotelImage;//varchar(200) NULL酒店图片
	
	@Column(name = "start_cost")
	private Double startCost;//double NOT NULL开店费用
	
	@Column(name = "hotel_lv")
	private Integer hotelLv;//int(2) NOT NULL酒店等级：1=经济酒店，2=舒适酒店，3=精品酒店，4=豪华酒店，5=尊享酒店
	
	@Column(name = "day_profit")
	private Double dayProfit;//double NOT NULL每天收益
	
	@Column(name = "day_pay")
	private Double dayPay;//double NOT NULL每天支出
	
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

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelDesc() {
		return hotelDesc;
	}

	public void setHotelDesc(String hotelDesc) {
		this.hotelDesc = hotelDesc;
	}

	public String getHotelImage() {
		return hotelImage;
	}

	public void setHotelImage(String hotelImage) {
		this.hotelImage = hotelImage;
	}

	public Double getStartCost() {
		return startCost;
	}

	public void setStartCost(Double startCost) {
		this.startCost = startCost;
	}

	public Integer getHotelLv() {
		return hotelLv;
	}

	public void setHotelLv(Integer hotelLv) {
		this.hotelLv = hotelLv;
	}

	public Double getDayProfit() {
		return dayProfit;
	}

	public void setDayProfit(Double dayProfit) {
		this.dayProfit = dayProfit;
	}

	public Double getDayPay() {
		return dayPay;
	}

	public void setDayPay(Double dayPay) {
		this.dayPay = dayPay;
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
