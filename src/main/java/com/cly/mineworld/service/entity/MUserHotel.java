package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户酒店实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_hotel")
public class MUserHotel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "user_str_id")
	private String userStrId;
	
	@Column(name = "hotel_str_id")
	private String hotelStrId;//varchar(45) NOT NULL酒店str_id
	
	@Column(name = "hotel_custom_name")
	private String hotelCustomName;//varchar(45) NULL酒店自定义名称
	
	@Column(name = "do_business_days")
	private Integer doBusinessDays;//int(11) NOT NULL总营业天数
	
	@Column(name = "profit")
	private Double profit;//double NOT NULL总收入
	
	@Column(name = "do_business_status")
	private Integer doBusinessStatus;//int(1) NOT NULL营业状态：1=营业中，2=停业中
	
	@Column(name = "stop_business_days")
	private Integer stopBusinessDays;//int(11) NOT NULL总已经停业天数
	
	@Column(name = "scenic_spot_str_id")
	private String scenicSpotStrId;//
	
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

	public String getHotelStrId() {
		return hotelStrId;
	}

	public void setHotelStrId(String hotelStrId) {
		this.hotelStrId = hotelStrId;
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

	public String getScenicSpotStrId() {
		return scenicSpotStrId;
	}

	public void setScenicSpotStrId(String scenicSpotStrId) {
		this.scenicSpotStrId = scenicSpotStrId;
	}

	public String getUserStrId() {
		return userStrId;
	}

	public void setUserStrId(String userStrId) {
		this.userStrId = userStrId;
	}
}
