package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户团队信息实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_team")
public class MUserTeam {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "team_name")
	private String teamName;//varchar(45) NOT NULL
	
	@Column(name = "team_leader_str_id")
	private String teamLeaderStrId;//varchar(45) NOT NULL团队长strId
	
	@Column(name = "team_lv")
	private Integer teamLv;//int(1) NOT NULL团队等级：1=青铜，2=白银，3=黄金，4=白金，5=砖石，6=王者
	
	@Column(name = "team_desc")
	private String teamDesc;//varchar(45) NOT NULL团队描述
	
	@Column(name = "team_count")
	private Integer teamCount;//int(11) NOT NULL团队总人数
	
	@Column(name = "team_recharge_amount")
	private Double teamRechargeAmount;
	
	@Column(name = "status")
	private Integer status;//int(1) NOT NULL
	
	@Column(name = "modify_time")
	private Integer modifyTime;//int(11) NOT NULL
	
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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamLeaderStrId() {
		return teamLeaderStrId;
	}

	public void setTeamLeaderStrId(String teamLeaderStrId) {
		this.teamLeaderStrId = teamLeaderStrId;
	}

	public Integer getTeamLv() {
		return teamLv;
	}

	public void setTeamLv(Integer teamLv) {
		this.teamLv = teamLv;
	}

	public String getTeamDesc() {
		return teamDesc;
	}

	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}

	public Integer getTeamCount() {
		return teamCount;
	}

	public void setTeamCount(Integer teamCount) {
		this.teamCount = teamCount;
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

	public Double getTeamRechargeAmount() {
		return teamRechargeAmount;
	}

	public void setTeamRechargeAmount(Double teamRechargeAmount) {
		this.teamRechargeAmount = teamRechargeAmount;
	}
}
