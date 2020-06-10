package com.cly.mineworld.service.vo.userHoterTeam;

//用户酒店管理团队VO
public class UserHotelTeamVo {

    private String teamId;  //varchar(45) NOT NULL,
    private String teamName; //varchar(45) NOT NULL COMMENT '团队名称',
    private String teamDesc; // varchar(200) DEFAULT NULL COMMENT '团队描述',
    private String teamCategor; // varchar(45) NOT NULL COMMENT '团队分类：1=普通管理团队',
    private String teamImage;  //varchar(200) DEFAULT NULL COMMENT '团队图片',
    private Integer workStatus; // int(1) NOT NULL COMMENT '工作状态：1=工作中，2=已经停止工作',
    private String workStartTime; // varchar(45) NOT NULL COMMENT '工作开始时间',
    private String workEndTime; // varchar(45) NOT NULL COMMENT '工作结束时间',
    private String alreadyManagerTime;   //当前时间-工作开始时间

    public String getAlreadyManagerTime() {
        return alreadyManagerTime;
    }

    public void setAlreadyManagerTime(String alreadyManagerTime) {
        this.alreadyManagerTime = alreadyManagerTime;
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

    public String getTeamDesc() {
        return teamDesc;
    }

    public void setTeamDesc(String teamDesc) {
        this.teamDesc = teamDesc;
    }

    public String getTeamCategor() {
        return teamCategor;
    }

    public void setTeamCategor(String teamCategor) {
        this.teamCategor = teamCategor;
    }

    public String getTeamImage() {
        return teamImage;
    }

    public void setTeamImage(String teamImage) {
        this.teamImage = teamImage;
    }

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
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
}
