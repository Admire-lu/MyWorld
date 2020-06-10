package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户酒店管理团队实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_hotel_manager_team")
public class MUserHotelManagerTeam {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "user_hotel_str_id")
	private String userHotelStrId;//varchar(45) NOT NULL用户酒店id
	
	@Column(name = "user_str_id")
	private String userStrId;
	
	@Column(name = "hotel_manager_team_str_id")
	private String hotelManagerTeamStrId;//varchar(45) NOT NULL酒店管理团队id
	
	@Column(name = "work_status")
	private Integer workStatus;//int(1) NOT NULL工作状态：1=工作中，2=已经停止工作
	
	@Column(name = "work_start_time")
	private String workStartTime;//int(11) NOT NULL工作开始时间
	
	@Column(name = "work_end_time")
	private String workEndTime;//int(11) NOT NULL工作结束时间
	
	@Column(name = "status")
	private Integer status;//int(1) NOT NULL
	
	@Column(name = "modify_time")
	private Integer modifyTime;//int(11) NOT NULL修改时间
	
	@Column(name = "create_time")
	private Integer createTime;//int(11) NOT NULL创建时间

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

	public String getUserHotelStrId() {
		return userHotelStrId;
	}

	public void setUserHotelStrId(String userHotelStrId) {
		this.userHotelStrId = userHotelStrId;
	}

	public String getHotelManagerTeamStrId() {
		return hotelManagerTeamStrId;
	}

	public void setHotelManagerTeamStrId(String hotelManagerTeamStrId) {
		this.hotelManagerTeamStrId = hotelManagerTeamStrId;
	}

	public Integer getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}

	public String getWorkStartTime() {
		return workStartTime;
	}

	public void setWorkStartTime(String workStartTime) {
		this.workStartTime = workStartTime;
	}

	public String getWorkEndTime() {
		return workEndTime;
	}

	public void setWorkEndTime(String workEndTime) {
		this.workEndTime = workEndTime;
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

	public String getUserStrId() {
		return userStrId;
	}

	public void setUserStrId(String userStrId) {
		this.userStrId = userStrId;
	}
}
