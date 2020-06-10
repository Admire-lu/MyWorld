package com.cly.mineworld.service.entity;

import javax.persistence.*;

/**
 * 后台管理节点订单实体
 */
@Entity
@Table(name = "m_node_invite_code")
public class MNodeInviteCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//int(11) NOT NULL

    @Column(name = "str_id")
    private String strId;//varchar(45) NOT NULL

    @Column(name = "invite_code")
    private String inviteCode; // varchar(45) NOT NULL COMMENT '邀请码',

    @Column(name = "node_lv")
    private Integer nodeLv; // int(2) NOT NULL COMMENT '节点等级',

    @Column(name = "code_use_status")
    private Integer codeUseStatus; //` int(1) NOT NULL DEFAULT '2' COMMENT '邀请码使用状态：1=已使用，2=未使用',

    @Column(name = "provide_status")
    private Integer provideStatus; //` int(1) NOT NULL DEFAULT '2' COMMENT '邀请码发放状态：1=已发放，2=未发放',

    @Column(name = "use_user_str_id")
    private String useUserStrId; //` varchar(45) DEFAULT NULL COMMENT '使用人用户strId',

    @Column(name = "use_time")
    private Integer useTime; //` int(11) DEFAULT NULL COMMENT '使用时间',

    @Column(name = "creator_str_id")
    private String creatorStrId; //varchar(45) NOT NULL COMMENT '创建者（后台管理员）strId',

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

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Integer getNodeLv() {
        return nodeLv;
    }

    public void setNodeLv(Integer nodeLv) {
        this.nodeLv = nodeLv;
    }

    public Integer getCodeUseStatus() {
        return codeUseStatus;
    }

    public void setCodeUseStatus(Integer codeUseStatus) {
        this.codeUseStatus = codeUseStatus;
    }

    public Integer getProvideStatus() {
        return provideStatus;
    }

    public void setProvideStatus(Integer provideStatus) {
        this.provideStatus = provideStatus;
    }

    public String getUseUserStrId() {
        return useUserStrId;
    }

    public void setUseUserStrId(String useUserStrId) {
        this.useUserStrId = useUserStrId;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
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
