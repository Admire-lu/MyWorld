package com.cly.mineworld.service.vo.userHotel;

/**
 * 用户酒店VO
 * @author william
 *
 */
public class UserHotelVo {

	private String hotelStrId;//varchar(45) NOT NULL
	private String hotelName;//varchar(45) NOT NULL酒店名称
	private String hotelDesc;//varchar(200) NOT NULL酒店描述
	private String hotelImage;//varchar(200) NULL酒店图片
	private Double startCost;//double NOT NULL开店费用
	private Integer hotelLv;//int(2) NOT NULL酒店等级：1=经济酒店，2=舒适酒店，3=精品酒店，4=豪华酒店，5=尊享酒店
	private Double dayProfit;//double NOT NULL每天收益
	private Double dayPay;//double NOT NULL每天支出
	private String hotelCustomName;//varchar(45) NULL酒店自定义名称
	private Integer doBusinessDays;//int(11) NOT NULL总营业天数
	private Double profit;//double NOT NULL总收入
	private Integer doBusinessStatus;//int(1) NOT NULL营业状态：1=营业中，2=停业中
	private Integer stopBusinessDays;//int(11) NOT NULL总已经停业天数
	private String userHotelStrId; //用户酒店唯一标识
	private String openTime; //酒店开设时间
	private String surplusManagerTime;  //结束时间减去当前时间 剩余管理时间
	private String regionName; //区域信息

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getSurplusManagerTime() {
		return surplusManagerTime;
	}

	public void setSurplusManagerTime(String surplusManagerTime) {
		this.surplusManagerTime = surplusManagerTime;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getUserHotelStrId() {
		return userHotelStrId;
	}

	public void setUserHotelStrId(String userHotelStrId) {
		this.userHotelStrId = userHotelStrId;
	}

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

	public String getHotelCustomName() {
		return hotelCustomName;
	}

	public void setHotelCustomName(String hotelCustomName) {
		this.hotelCustomName = hotelCustomName;
	}

	public Integer getDoBusinessDays() {
		return doBusinessDays;
	}

	public void setDoBusinessDays(Integer doBusinessDays) {
		this.doBusinessDays = doBusinessDays;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Integer getDoBusinessStatus() {
		return doBusinessStatus;
	}

	public void setDoBusinessStatus(Integer doBusinessStatus) {
		this.doBusinessStatus = doBusinessStatus;
	}

	public Integer getStopBusinessDays() {
		return stopBusinessDays;
	}

	public void setStopBusinessDays(Integer stopBusinessDays) {
		this.stopBusinessDays = stopBusinessDays;
	}
}
