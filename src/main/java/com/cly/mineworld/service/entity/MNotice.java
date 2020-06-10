package com.cly.mineworld.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 公告实体
 * @author william
 *
 */
@Entity
@Table(name = "m_notice")
public class MNotice {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;//int(11) NOT NULL
	
	@Column(name = "str_id")
	private String strId;// varchar(45) NOT NULL,
	
	@Column(name = "notice_title")
	private String  noticeTitle;// varchar(45) NOT NULL,
	
	@Column(name = "notice_content")
	private String  noticeContent;// text NOT NULL,
	
	@Column(name = "release_status")
	private Integer  releaseStatus;// int(1) NOT NULL COMMENT '发布状态：1=已发布，2=未发布',
	
	@Column(name = "creator_str_id")
	private String  creatorStrId;// varchar(45) DEFAULT NULL COMMENT '创建者strid',
	
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

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Integer getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(Integer releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public String getCreatorStrId() {
		return creatorStrId;
	}

	public void setCreatorStrId(String creatorStrId) {
		this.creatorStrId = creatorStrId;
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
