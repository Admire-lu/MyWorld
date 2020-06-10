package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户资产合成订单实体
 * @author WilliamLam
 *
 */
@Entity
@Table(name = "m_user_assets_synthesis_order")
public class MUserAssetsSynthesisOrder {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "user_str_id")
	private String userStrId;//varchar(45) NOT NULL
	
	@Column(name = "vad")
	private Double vad;//double NULL
	
	@Column(name = "deh")
	private Double deh;//double NULL
	
	@Column(name = "synthesis_amount")
	private Double synthesisAmount;//double NULL合成后数量
	
	@Column(name = "synthesis_status")
	private Integer synthesisStatus;//int(1) NOT NULL合成状态：1=合成完毕，2=合成中
	
	@Column(name = "start_time")
	private String startTime;//int(11) NOT NULL开始合成时间
	
	@Column(name = "end_time")
	private String endTime;//int(11) NOT NULL合成结束时间
	
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

	public Double getVad() {
		return vad;
	}

	public void setVad(Double vad) {
		this.vad = vad;
	}

	public Double getDeh() {
		return deh;
	}

	public void setDeh(Double deh) {
		this.deh = deh;
	}

	public Double getSynthesisAmount() {
		return synthesisAmount;
	}

	public void setSynthesisAmount(Double synthesisAmount) {
		this.synthesisAmount = synthesisAmount;
	}

	public Integer getSynthesisStatus() {
		return synthesisStatus;
	}

	public void setSynthesisStatus(Integer synthesisStatus) {
		this.synthesisStatus = synthesisStatus;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
