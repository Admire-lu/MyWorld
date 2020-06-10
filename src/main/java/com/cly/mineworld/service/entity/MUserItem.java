package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户道具实体
 * @author WilliamLam
 *
 */
@Entity
@Table(name = "m_user_item")
public class MUserItem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "user_str_id")
	private String userStrId;//varchar(45) NOT NULL
	
	@Column(name = "item_str_id")
	private String itemStrId;//varchar(45) NOT NULL道具strId
	
	@Column(name = "item_count")
	private Integer itemCount;//int(11) NOT NULL道具数量
	
	@Column(name = "item_lv")
	private Integer itemLv;//int(2) NOT NULL道具等级
	
	@Column(name = "item_use_count")
	private Integer itemUseCount;//int(3) NOT NULL道具可使用次数
	
	@Column(name = "item_category_str_id")
	private String itemCategoryStrId;
	
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

	public String getUserStrId() {
		return userStrId;
	}

	public void setUserStrId(String userStrId) {
		this.userStrId = userStrId;
	}

	public String getItemStrId() {
		return itemStrId;
	}

	public void setItemStrId(String itemStrId) {
		this.itemStrId = itemStrId;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public Integer getItemLv() {
		return itemLv;
	}

	public void setItemLv(Integer itemLv) {
		this.itemLv = itemLv;
	}

	public Integer getItemUseCount() {
		return itemUseCount;
	}

	public void setItemUseCount(Integer itemUseCount) {
		this.itemUseCount = itemUseCount;
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

	public String getItemCategoryStrId() {
		return itemCategoryStrId;
	}

	public void setItemCategoryStrId(String itemCategoryStrId) {
		this.itemCategoryStrId = itemCategoryStrId;
	}
}
