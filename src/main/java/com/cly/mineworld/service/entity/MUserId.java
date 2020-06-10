package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户身份证信息实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_id")
public class MUserId {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;// varchar(45) NOT NULL,
	
	@Column(name = "user_str_id")
	private String userStrId;// varchar(45) NOT NULL,
	
	@Column(name = "id_no")
	private String idNo;// varchar(45) NOT NULL COMMENT '身份证号',
	
	@Column(name = "id_realname")
	private String idRealname;// varchar(45) NOT NULL COMMENT '身份证真实名字',
	
	@Column(name = "id_image_one")
	private String idImageOne;// varchar(500) NOT NULL COMMENT '身份证照-1',
	
	@Column(name = "id_image_two")
	private String idImageTwo;// varchar(500) NOT NULL COMMENT '身份证照-2',
	
	@Column(name = "id_image_three")
	private String idImageThree;// varchar(500) DEFAULT NULL,
	
	@Column(name = "authentication_status")
	private Integer authenticationStatus;
	
	@Column(name = "status")
	private Integer status;// int(1) NOT NULL DEFAULT '1',
	
	@Column(name = "modify_time")
	private Integer modifyTime;// int(11) NOT NULL,
	
	@Column(name = "create_time")
	private Integer createTime;// int(11) NOT NULL,

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

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdRealname() {
		return idRealname;
	}

	public void setIdRealname(String idRealname) {
		this.idRealname = idRealname;
	}

	public String getIdImageOne() {
		return idImageOne;
	}

	public void setIdImageOne(String idImageOne) {
		this.idImageOne = idImageOne;
	}

	public String getIdImageTwo() {
		return idImageTwo;
	}

	public void setIdImageTwo(String idImageTwo) {
		this.idImageTwo = idImageTwo;
	}

	public String getIdImageThree() {
		return idImageThree;
	}

	public void setIdImageThree(String idImageThree) {
		this.idImageThree = idImageThree;
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

	public Integer getAuthenticationStatus() {
		return authenticationStatus;
	}

	public void setAuthenticationStatus(Integer authenticationStatus) {
		this.authenticationStatus = authenticationStatus;
	}
}
