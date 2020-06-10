package com.cly.mineworld.service.vo.userTask;

/**
 * 酒店VO
 * @author william
 *
 */
public class TaskHotelVo {

	private String hotelStrId;//varchar(45) NOT NULL
	private String hotelName;//varchar(45) NOT NULL酒店名称
	private Integer hotelLv;//int(2) NOT NULL酒店等级：1=经济酒店，2=舒适酒店，3=精品酒店，4=豪华酒店，5=尊享酒店
	private Double hotelDayProfit;//double NOT NULL每天收益
	private Double hotelDayPay;//double NOT NULL每天支出
	private String userStrId;//用户id
	private Integer userHotelId;//用户酒店id

	public String getHotelStrId() {
		return hotelStrId;
	}

	public void setHotelStrId(String hotelStrId) {
		this.hotelStrId = hotelStrId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Integer getHotelLv() {
		return hotelLv;
	}

	public void setHotelLv(Integer hotelLv) {
		this.hotelLv = hotelLv;
	}

	public Double getHotelDayProfit() {
		return hotelDayProfit;
	}

	public void setHotelDayProfit(Double hotelDayProfit) {
		this.hotelDayProfit = hotelDayProfit;
	}

	public Double getHotelDayPay() {
		return hotelDayPay;
	}

	public void setHotelDayPay(Double hotelDayPay) {
		this.hotelDayPay = hotelDayPay;
	}

	public String getUserStrId() {
		return userStrId;
	}

	public void setUserStrId(String userStrId) {
		this.userStrId = userStrId;
	}

	public Integer getUserHotelId() {
		return userHotelId;
	}

	public void setUserHotelId(Integer userHotelId) {
		this.userHotelId = userHotelId;
	}
}
