package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 基础数据-道具类别实体
 * @author WilliamLam
 *
 */
@Entity
@Table(name = "m_base_item_category")
public class MBaseItemCategory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strd;//varchar(45) NOT NULL
	
	@Column(name = "category_name")
	private String categoryName;//varchar(45) NOT NULL道具名称
	
	@Column(name = "category_desc")
	private String categoryDesc;//varchar(200) NOT NULL道具描述
	
	@Column(name = "category_type")
	private Integer category_type;//int(1) NOT NULL道具分类：1=偷窃类，2=防护类
	
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

	public String getStrd() {
		return strd;
	}

	public void setStrd(String strd) {
		this.strd = strd;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public Integer getCategory_type() {
		return category_type;
	}

	public void setCategory_type(Integer category_type) {
		this.category_type = category_type;
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
