package com.cly.mineworld.service.vo.userManagerTeam;

//用户管理团队VO
public class UserManagerTeamVo {

    private String userStrId; // varchar(45) NOT NULL COMMENT '用户id',
    private String userHotelStrId; // varchar(45) NOT NULL COMMENT '用户酒店id',
    private String hotelManagerTeamStrId; // varchar(45) NOT NULL COMMENT '酒店管理团队id',
    private String hotelName; //酒店名称
    private String hotelCustomName; //用户酒店自定义名称
    private String teamId;  //varchar(45) NOT NULL,
    private String teamName; //varchar(45) NOT NULL COMMENT '团队名称',,
    private String teamCategory; // varchar(45) NOT NULL COMMENT '团队分类：1=普通管理团队',
    private String teamDesc; // varchar(45) NOT NULL COMMENT '团队描述
    private String workStartTime; // varchar(45) NOT NULL COMMENT '工作开始时间',
    private String workEndTime; // varchar(45) NOT NULL COMMENT '工作结束时间',
    private String surplusManagerTime;   //结束时间减去当前时间
    private String userHotelName;   //用户酒店名称

    public String getTeamDesc() {
        return teamDesc;
    }

    public void setTeamDesc(String teamDesc) {
        this.teamDesc = teamDesc;
    }

    public String getUserHotelName() {
        return userHotelName;
    }

    public void setUserHotelName(String userHotelName) {
        this.userHotelName = userHotelName;
    }

    public String getHotelCustomName() {
        return hotelCustomName;
    }

    public void setHotelCustomName(String hotelCustomName) {
        this.hotelCustomName = hotelCustomName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getTeamCategory() {
        return teamCategory;
    }

    public void setTeamCategory(String teamCategory) {
        this.teamCategory = teamCategory;
    }

    public String getUserStrId() {
        return userStrId;
    }

    public void setUserStrId(String userStrId) {
        this.userStrId = userStrId;
    }

    public String getUserHotelStrId() {
        return userHotelStrId;
    }

    public void setUserHotelStrId(String userHotelStrId) {
        this.userHotelStrId = userHotelStrId;
    }

    public String getHotelManagerTeamStrId() {
        return hotelManagerTeamStrId;
    }

    public void setHotelManagerTeamStrId(String hotelManagerTeamStrId) {
        this.hotelManagerTeamStrId = hotelManagerTeamStrId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(String workStartTime) {
        this.workStartTime = workStartTime;
    }

    public String getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(String workEndTime) {
        this.workEndTime = workEndTime;
    }

    public String getSurplusManagerTime() {
        return surplusManagerTime;
    }

    public void setSurplusManagerTime(String surplusManagerTime) {
        this.surplusManagerTime = surplusManagerTime;
    }
}
