package com.cly.mineworld.service.entity;

import javax.persistence.*;

/**
 * 道具购买次数实体
 * @author william
 *
 */
@Entity
@Table(name = "m_item_total_count")
public class MItemTotalCount {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;// varchar(45) NOT NULL,
	
	@Column(name = "item_str_id")
	private String  itemStrId;// varchar(45) NOT NULL,,
	
	@Column(name = "item_count")
	private Integer itemCount;// int(11) NOT NULL,

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
