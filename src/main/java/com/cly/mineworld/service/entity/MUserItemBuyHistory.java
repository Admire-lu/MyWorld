package com.cly.mineworld.service.entity;

import javax.persistence.*;

/**
 * 用户购买道具历史实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_item_buy_history")
public class MUserItemBuyHistory {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "user_str_id")
	private String userStrId;// varchar(45) NOT NULL,
	
	@Column(name = "item_str_id")
	private String itemStrId;//varchar(45) NOT NULL COMMENT '道具str_id',
	
	@Column(name = "item_category_str_id")
	private String itemCategoryStrId;//varchar(45) NOT NULL COMMENT '道具类别str_id',
	
	@Column(name = "item_use_count")
	private Integer itemUseCount;//int(2) NOT NULL COMMENT '道具是用次数',,

	@Column(name = "buy_date")
	private String buyDate;//varchar(45) NOT NULL COMMENT '购买日期，格式（2019-10-20）',

	@Column(name = "history_type")
	private Integer historyType;//int(1) NOT NULL DEFAULT '1' COMMENT '历史类型：1=购买历史',
	
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

	public String getItemCategoryStrId() {
		return itemCategoryStrId;
	}

	public void setItemCategoryStrId(String itemCategoryStrId) {
		this.itemCategoryStrId = itemCategoryStrId;
	}

	public Integer getItemUseCount() {
		return itemUseCount;
	}

	public void setItemUseCount(Integer itemUseCount) {
		this.itemUseCount = itemUseCount;
	}

	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public Integer getHistoryType() {
		return historyType;
	}

	public void setHistoryType(Integer historyType) {
		this.historyType = historyType;
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
