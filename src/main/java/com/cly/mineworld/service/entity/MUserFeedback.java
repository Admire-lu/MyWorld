package com.cly.mineworld.service.entity;


import javax.persistence.*;

/**
 * 用户意见反馈实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_feedback")
public class MUserFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // `id` int(11) NOT NULL AUTO_INCREMENT,

    @Column(name = "str_id")
    private String strId;//varchar(45) NOT NULL

    @Column(name = "user_str_id")
    private String userStrId;  //varchar(45) NOT NULL COMMENT '用户strid',

    @Column(name = "feedback_title")
    private String feedbackTitle; //varchar(45) NOT NULL COMMENT '反馈标题',

    @Column(name = "feedback_content")
    private String feedbackContent; //text NOT NULL COMMENT '反馈内容',

    @Column(name = "feedback_type")
    private Integer feedbackType; // int(1) NOT NULL DEFAULT '1' COMMENT '反馈分类：1=留言，2=申诉',

    @Column(name = "reply_content")
    private String replyContent; // text NOT NULL COMMENT '后台回复内容',

    @Column(name = "manager_read_status")
    private Integer managerReadStatus;//int(1) NOT NULL DEFAULT '2' COMMENT '后台已读状态：1=已读，2=未读',

    @Column(name = "reply_manager_str_id")
    private String replyManagerStrId; // varchar(45) NOT NULL COMMENT '回复的管理员strId',

    @Column(name = "reply_status")
    private Integer replyStatus; // int(1) NOT NULL DEFAULT '2' COMMENT '回复状态：1=已回复，2=未回复',

    @Column(name = "status")
    private Integer status;//int(1) NOT NULL DEFAULT '1',

    @Column(name = "modify_time")
    private Integer modifyTime;// int(11) NOT NULL,

    @Column(name = "create_time")
    private Integer createTime; //`int(11) NOT NULL,

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

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public Integer getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Integer feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Integer getManagerReadStatus() {
        return managerReadStatus;
    }

    public void setManagerReadStatus(Integer managerReadStatus) {
        this.managerReadStatus = managerReadStatus;
    }

    public String getReplyManagerStrId() {
        return replyManagerStrId;
    }

    public void setReplyManagerStrId(String replyManagerStrId) {
        this.replyManagerStrId = replyManagerStrId;
    }

    public Integer getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(Integer replyStatus) {
        this.replyStatus = replyStatus;
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
