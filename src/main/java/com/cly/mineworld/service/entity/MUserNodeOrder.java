package com.cly.mineworld.service.entity;
import javax.persistence.*;

/**
 * 用户节点订单实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_node_order")
public class MUserNodeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//int(11) NOT NULL

    @Column(name = "str_id")
    private String strId;//varchar(45) NOT NULL

    @Column(name = "user_str_id")
    private String userStrId;  //varchar(45) NOT NULL COMMENT '用户strid',

    @Column(name = "user_id")
    private Integer userId;  // nt(11) NOT NULL,'用户id',

    @Column(name = "invite_code")
    private String inviteCode; // varchar(45) NOT NULL COMMENT '邀请码',

    @Column(name = "mobile")
    private String mobile; //varchar(11) NOT NULL COMMENT '手机号码',

    @Column(name = "order_verify_status")
    private Integer orderVerifyStatus; //int(1) NOT NULL DEFAULT '2' COMMENT '审核状态：1=已经通过审核，2=未审核',

    @Column(name = "order_read_status")
    private Integer orderReadStatus; //int(1) NOT NULL DEFAULT '2' COMMENT '订单已读状态：1=已读，2=未读',

    @Column(name = "order_reviewer_str_id")
    private String orderReviewerStrId; //varchar(45) DEFAULT NULL COMMENT '审核的管理员strId',

    @Column(name = "node_lv")
    private Integer nodeLv; //int(2) NOT NULL DEFAULT '0' COMMENT '节点等级',

    @Column(name = "status")
    private Integer status; //int(1) NOT NULL DEFAULT '1',

    @Column(name = "modify_time")
    private Integer modifyTime; //int(11) NOT NULL,

    @Column(name = "create_time")
    private Integer createTime; //int(11) NOT NULL,

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getOrderVerifyStatus() {
        return orderVerifyStatus;
    }

    public void setOrderVerifyStatus(Integer orderVerifyStatus) {
        this.orderVerifyStatus = orderVerifyStatus;
    }

    public Integer getOrderReadStatus() {
        return orderReadStatus;
    }

    public void setOrderReadStatus(Integer orderReadStatus) {
        this.orderReadStatus = orderReadStatus;
    }

    public String getOrderReviewerStrId() {
        return orderReviewerStrId;
    }

    public void setOrderReviewerStrId(String orderReviewerStrId) {
        this.orderReviewerStrId = orderReviewerStrId;
    }

    public Integer getNodeLv() {
        return nodeLv;
    }

    public void setNodeLv(Integer nodeLv) {
        this.nodeLv = nodeLv;
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
