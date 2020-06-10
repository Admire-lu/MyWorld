package com.cly.mineworld.service.entity;

import javax.persistence.*;

/**
 * 用户转账订单实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_transfer_order")
public class MUserTransferOrder {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//int(11) NOT NULL

    @Column(name = "str_id")
    private String strId;//varchar(45) NOT NULL

    @Column(name = "transfer_user_str_id")
    private String transferUserStrId;//varchar(45) NOT NULL COMMENT '转账者strId',

    @Column(name = "payee_user_str_id")
    private String payeeUserStrId;// varchar(45) NOT NULL COMMENT '收款人strid',

    @Column(name = "transfer_amount")
    private Double transferAmount;// double NOT NULL DEFAULT '0' COMMENT '转账金额',

    @Column(name = "transfer_type")
    private Integer transferType;// int(1) NOT NULL COMMENT '转账类型：1=VAD互转',

    @Column(name = "transfer_before_amount")
    private Double transferBeforeAmount;//double NOT NULL DEFAULT '0' COMMENT '转账者转账前金额',

    @Column(name = "transfer_after_amount")
    private Double transferAfterAmount;//double NOT NULL DEFAULT '0' COMMENT '转账者转账后金额',

    @Column(name = "payee_before_amount")
    private Double payeeBeforeAmount;//double NOT NULL DEFAULT '0' COMMENT '收款者收款前金额',

    @Column(name = "payee_after_amount")
    private Double payeeAfterAmount;//double NOT NULL DEFAULT '0' COMMENT '收款者收款后金额',

    @Column(name = "order_status")
    private Integer orderStatus;//int(1) NOT NULL DEFAULT '1' COMMENT '订单状态：1=互转成功，2=互转失败',

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

    public String getTransferUserStrId() {
        return transferUserStrId;
    }

    public void setTransferUserStrId(String transferUserStrId) {
        this.transferUserStrId = transferUserStrId;
    }

    public String getPayeeUserStrId() {
        return payeeUserStrId;
    }

    public void setPayeeUserStrId(String payeeUserStrId) {
        this.payeeUserStrId = payeeUserStrId;
    }

    public Double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public Double getTransferBeforeAmount() {
        return transferBeforeAmount;
    }

    public void setTransferBeforeAmount(Double transferBeforeAmount) {
        this.transferBeforeAmount = transferBeforeAmount;
    }

    public Double getTransferAfterAmount() {
        return transferAfterAmount;
    }

    public void setTransferAfterAmount(Double transferAfterAmount) {
        this.transferAfterAmount = transferAfterAmount;
    }

    public Double getPayeeBeforeAmount() {
        return payeeBeforeAmount;
    }

    public void setPayeeBeforeAmount(Double payeeBeforeAmount) {
        this.payeeBeforeAmount = payeeBeforeAmount;
    }

    public Double getPayeeAfterAmount() {
        return payeeAfterAmount;
    }

    public void setPayeeAfterAmount(Double payeeAfterAmount) {
        this.payeeAfterAmount = payeeAfterAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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
