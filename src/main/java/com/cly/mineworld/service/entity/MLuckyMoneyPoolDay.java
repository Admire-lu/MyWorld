package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 每天红包池
 * @author WilliamLam
 *
 */
@Entity
@Table(name = "m_lucky_money_pool_day")
public class MLuckyMoneyPoolDay {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "pool_date")
	private String poolDate;//varchar(45) NOT NULL红包池日期
	
	@Column(name = "pool_amout")
	private Double poolAmout;//double NOT NULL红包池总金额

	@Column(name = "count")
	private Integer count;//`int(11) DEFAULT NULL COMMENT '红包个数，0=无限',
	
	@Column(name = "status")
	private Integer status;//int(1) NOT NULL
	
	@Column(name = "modify_time")
	private Integer modifyTime;//int(11) NOT NULL
	
	@Column(name = "create_time")
	private Integer createTime;//int(11) NOT NULL

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public String getPoolDate() {
		return poolDate;
	}

	public void setPoolDate(String poolDate) {
		this.poolDate = poolDate;
	}

	public Double getPoolAmout() {
		return poolAmout;
	}

	public void setPoolAmout(Double poolAmout) {
		this.poolAmout = poolAmout;
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
