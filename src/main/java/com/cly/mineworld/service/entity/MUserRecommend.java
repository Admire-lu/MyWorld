package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户推荐关系实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_recommend")
public class MUserRecommend {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "recommend_user_str_id")
	private String recommendUserStrId;//varchar(45) NOT NULL推荐人id
	
	@Column(name = "recommended_user_str_id")
	private String recommendedUserStrId;//varchar(45) NOT NULL被推荐人id
	
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

	public String getRecommendUserStrId() {
		return recommendUserStrId;
	}

	public void setRecommendUserStrId(String recommendUserStrId) {
		this.recommendUserStrId = recommendUserStrId;
	}

	public String getRecommendedUserStrId() {
		return recommendedUserStrId;
	}

	public void setRecommendedUserStrId(String recommendedUserStrId) {
		this.recommendedUserStrId = recommendedUserStrId;
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
