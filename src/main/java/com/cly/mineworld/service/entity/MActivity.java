package com.cly.mineworld.service.entity;

import javax.persistence.*;

/**
 * 2019年10月16日活动实体
 */
@Entity
@Table(name = "m_activity_20191016")
public class MActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//int(11) NOT NULL

    @Column(name = "str_id")
    private String strId;//varchar(45) NOT NULL

    @Column(name = "user_id")
    private Integer userId;  // nt(11) NOT NULL,'用户id',

    @Column(name = "user_str_id")
    private String userStrId;  //varchar(45) NOT NULL COMMENT '用户strid',

    @Column(name = "amount")
    private Double amount; // double NOT NULL DEFAULT '0' COMMENT '特殊金额数量（定向币）',

    @Column(name = "amount_type")
    private Integer amountType; // int(1) NOT NULL DEFAULT '1' COMMENT '金额类型：1=定向VAD',

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserStrId() {
        return userStrId;
    }

    public void setUserStrId(String userStrId) {
        this.userStrId = userStrId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getAmountType() {
        return amountType;
    }

    public void setAmountType(Integer amountType) {
        this.amountType = amountType;
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
