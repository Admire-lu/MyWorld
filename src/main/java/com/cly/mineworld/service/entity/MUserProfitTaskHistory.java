package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户收益计算任务调度历史实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_profit_task_history")
public class MUserProfitTaskHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;//varchar(45) NOT NULL
	
	@Column(name = "task_date")
	private String taskDate;//varchar(45) NOT NULL任务调用日期
	
	@Column(name = "task_start_time")
	private String taskStartTime;//varchar(45) NOT NULL任务开始时间
	
	@Column(name = "task_end_time")
	private String taskEndTime;//varchar(45) NOT NULL任务结束时间
	
	@Column(name = "run_status")
	private Integer runStatus;//int(1) NOT NULL运行状态：1=已运行完毕，2=未运行
	
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

	public String getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}

	public String getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(String taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public String getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(String taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public Integer getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
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
