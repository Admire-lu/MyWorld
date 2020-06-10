package com.cly.mineworld.service.entity;


import javax.persistence.*;

/**
 * 用户提现订单实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_cash_out_order")
public class MUserCashOutOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//int(11) NOT NULL

    @Column(name = "str_id")
    private String strId;//varchar(45) NOT NULL

    @Column(name = "user_str_id")
    private String userStrId;  //varchar(45) NOT NULL COMMENT '用户strid',

    @Column(name = "freeze_order_str_id")
    private String freezeOrderStrId; //varchar(45) NOT NULL COMMENT '冻结订单strid',

    @Column(name = "amount")
    private Double amount; //double NOT NULL DEFAULT '0' COMMENT '提现金额'

    @Column(name = "cash_out_status")
    private Integer cashOutStatus; //int(1) NOT NULL COMMENT '提现状态：1=等待审核中，2=通过审核，3=不通过审核'

    @Column(name = "remark_msg")
    private String remarkMsg; //varchar(200) DEFAULT NULL COMMENT '备注信息',

    @Column(name = "user_mobile")
    private String userMobile;//varchar(45) NULL用户手机号

    @Column(name = "user_email")
    private String userEmail;//varchar(45) NULL用户邮箱

    @Column(name = "token_address")
    private String tokenAddress;//varchar(200) NULL提现地址

    @Column(name = "status")
    private Integer status; //int(1) NOT NULL,

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

    public String getFreezeOrderStrId() {
        return freezeOrderStrId;
    }

    public void setFreezeOrderStrId(String freezeOrderStrId) {
        this.freezeOrderStrId = freezeOrderStrId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCashOutStatus() {
        return cashOutStatus;
    }

    public void setCashOutStatus(Integer cashOutStatus) {
        this.cashOutStatus = cashOutStatus;
    }

    public String getRemarkMsg() {
        return remarkMsg;
    }

    public void setRemarkMsg(String remarkMsg) {
        this.remarkMsg = remarkMsg;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
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
