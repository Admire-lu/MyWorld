package com.cly.mineworld.service.entity;

import javax.persistence.*;

/**
 * 用户道具使用历史实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_item_use_history")
public class MUserItemUseHistory {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "item_str_id")
	private String itemStrId;// varchar(45) NOT NULL,
	
	@Column(name = "use_user_str_id")
	private String useUserStrId;//varchar(45) NOT NULL COMMENT '使用者user_id',
	
	@Column(name = "be_used_user_str_id")
	private String beUsedUserStrId;//varchar(45) DEFAULT NULL COMMENT '被使用者str_id',
	
	@Column(name = "use_desc")
	private String useDesc;//varchar(200) NOT NULL COMMENT '使用描述',

	@Column(name = "amount")
	private Double amount;//double DEFAULT '0' COMMENT '使用道具作用金额，例如：偷窃多少金额',

	@Column(name = "history_type")
	private Integer historyType;//int(1) NOT NULL COMMENT '历史类型：1=盗窃，2=被盗窃',
	
	@Column(name = "status")
	private Integer status;//int(1) NOT NULL
	
	@Column(name = "modify_time")
	private Integer modifyTime;//int(11) NOT NULL
	
	@Column(name = "create_time")
	private Integer createTime;//int(11) NOT NULL

	public Integer getHistoryType() {
		return historyType;
	}

	public void setHistoryType(Integer historyType) {
		this.historyType = historyType;
	}

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

	public String getItemStrId() {
		return itemStrId;
	}

	public void setItemStrId(String itemStrId) {
		this.itemStrId = itemStrId;
	}

	public String getUseUserStrId() {
		return useUserStrId;
	}

	public void setUseUserStrId(String useUserStrId) {
		this.useUserStrId = useUserStrId;
	}

	public String getBeUsedUserStrId() {
		return beUsedUserStrId;
	}

	public void setBeUsedUserStrId(String beUsedUserStrId) {
		this.beUsedUserStrId = beUsedUserStrId;
	}

	public String getUseDesc() {
		return useDesc;
	}

	public void setUseDesc(String useDesc) {
		this.useDesc = useDesc;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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
