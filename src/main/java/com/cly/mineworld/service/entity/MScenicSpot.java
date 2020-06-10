package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 景点实体
 * @author william
 *
 */
@Entity
@Table(name = "m_scenic_spot")
public class MScenicSpot {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "region_str_id")
	private String regionStrId;//varchar(45) NOT NULL所属区域
	
	@Column(name = "scenic_name")
	private String scenicName;//varchar(45) NOT NULL景点名称
	
	@Column(name = "scenic_desc")
	private String scenicDesc;//varchar(200) NOT NULL景点描述
	
	@Column(name = "scenic_image")
	private String scenicImage;//varchar(200) NULL景点图片
	
	@Column(name = "status")
	private Integer status;//int(1) NULL
	
	@Column(name = "modify_time")
	private Integer modifyTime;//int(11) NULL
	
	@Column(name = "create_time")
	private Integer createTime;//int(11) NULL

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

	public String getRegionStrId() {
		return regionStrId;
	}

	public void setRegionStrId(String regionStrId) {
		this.regionStrId = regionStrId;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public String getScenicDesc() {
		return scenicDesc;
	}

	public void setScenicDesc(String scenicDesc) {
		this.scenicDesc = scenicDesc;
	}

	public String getScenicImage() {
		return scenicImage;
	}

	public void setScenicImage(String scenicImage) {
		this.scenicImage = scenicImage;
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
