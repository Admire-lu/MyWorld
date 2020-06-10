package com.cly.mineworld.service.entity;
import javax.persistence.*;

/**
 * 用户资金冻结订单实体
 * @author william
 *
 */
@Entity
@Table(name = "m_user_freeze_order")
public class MUserFreezeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//int(11) NOT NULL

    @Column(name = "str_id")
    private String strId;//varchar(45) NOT NULL

    @Column(name = "user_str_id")
    private String userStrId;  //varchar(45) NOT NULL COMMENT '用户strid',

    @Column(name = "freeze_type")
    private Integer freezeType; //int(1) NOT NULL COMMENT '冻结类型：1=用户提现冻结'

    @Column(name = "freeze_amount")
    private Double freezeAmount; //double NOT NULL DEFAULT '0' COMMENT '冻结金额',

    @Column(name = "before_freeze_amount")
    private Double beforeFreezeAmount; //double NOT NULL DEFAULT '0' COMMENT '冻结前金额',

    @Column(name = "after_freeze_amount")
    private Double afterFreezeAmount; //double NOT NULL DEFAULT '0' COMMENT '冻结后金额',

    @Column(name = "freeze_status")
    private Integer freezeStatus; //int(1) NOT NULL COMMENT '冻结状态：1=冻结中，2=已解冻',

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

    public Integer getFreezeType() {
        return freezeType;
    }

    public void setFreezeType(Integer freezeType) {
        this.freezeType = freezeType;
    }

    public Double getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(Double freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public Double getBeforeFreezeAmount() {
        return beforeFreezeAmount;
    }

    public void setBeforeFreezeAmount(Double beforeFreezeAmount) {
        this.beforeFreezeAmount = beforeFreezeAmount;
    }

    public Double getAfterFreezeAmount() {
        return afterFreezeAmount;
    }

    public void setAfterFreezeAmount(Double afterFreezeAmount) {
        this.afterFreezeAmount = afterFreezeAmount;
    }

    public Integer getFreezeStatus() {
        return freezeStatus;
    }

    public void setFreezeStatus(Integer freezeStatus) {
        this.freezeStatus = freezeStatus;
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
