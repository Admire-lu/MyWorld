package com.cly.mineworld.service.vo.userRealnameInformation;

//实名认证详情的Vo
public class UserRealNameInformationVo {

    private String userStrId;// varchar(45) NOT NULL,
    private String idNo;// varchar(45) NOT NULL COMMENT '身份证号',
    private String idRealName;// varchar(45) NOT NULL COMMENT '身份证真实名字',
    private String idImageOne;// varchar(500) NOT NULL COMMENT '身份证照-1',
    private String idImageTwo;// varchar(500) NOT NULL COMMENT '身份证照-2',
    private String authenticationStatus; //认证状态：1=已通过认证，2=未通过认证
    private Integer status;// int(1) NOT NULL DEFAULT '1',
    private String country;// 国家
    private String nickName;// 昵称
    private String mobile; //手机

    public String getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public String getUserStrId() {
        return userStrId;
    }

    public void setUserStrId(String userStrId) {
        this.userStrId = userStrId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdRealName() {
        return idRealName;
    }

    public void setIdRealName(String idRealName) {
        this.idRealName = idRealName;
    }

    public String getIdImageOne() {
        return idImageOne;
    }

    public void setIdImageOne(String idImageOne) {
        this.idImageOne = idImageOne;
    }

    public String getIdImageTwo() {
        return idImageTwo;
    }

    public void setIdImageTwo(String idImageTwo) {
        this.idImageTwo = idImageTwo;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
