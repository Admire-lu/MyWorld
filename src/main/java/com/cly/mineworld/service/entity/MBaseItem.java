package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 道具信息实体
 * @author WilliamLam
 *
 */
@Entity
@Table(name = "m_base_item")
public class MBaseItem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "item_category_str_id")
	private String itemCategoryStrId;//varchar(45) NOT NULL道具类别strId
	
	@Column(name = "item_name")
	private String itemName;//varchar(45) NOT NULL道具名称
	
	@Column(name = "item_lv")
	private Integer itemLv;//int(2) NOT NULL道具等级
	
	@Column(name = "item_price")
	private Double itemPrice;//double NOT NULL道具价格
	
	@Column(name = "item_use_count")
	private Integer itemUseCount;//int(2) NOT NULL买一个能用多少次
	
	@Column(name = "item_desc")
	private String itemDesc;//text NOT NULL道具描述
	
	@Column(name = "item_type")
	private Integer itemType;//int(1) NOT NULL道具类型：1=偷窃类，2=保护类
	
	@Column(name = "status")
	private Integer status;//int(1) NULL
	
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

	public String getItemCategoryStrId() {
		return itemCategoryStrId;
	}

	public void setItemCategoryStrId(String itemCategoryStrId) {
		this.itemCategoryStrId = itemCategoryStrId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getItemLv() {
		return itemLv;
	}

	public void setItemLv(Integer itemLv) {
		this.itemLv = itemLv;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Integer getItemUseCount() {
		return itemUseCount;
	}

	public void setItemUseCount(Integer itemUseCount) {
		this.itemUseCount = itemUseCount;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
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
}
