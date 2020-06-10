package com.cly.mineworld.service.vo.userTeams;

//用户团队VO
public class UserTeamsVo {

    private String userStrId; // varchar(45) NOT NULL COMMENT '用户id',
    private Integer friendTotal; //好友数量
    private Integer teamTotal; //团队数量
    private Long friendId;  //好友账号
    private String nickName; //昵称
    private String mobile;//手机
    private String email;//邮箱
    private Double teamPerformance;//团队业绩
    private String membershipLevel; //1=青铜，2=白银，3=黄金，4=白金，5=砖石，6=王者',
    private String registrationTime; //注册时间
    private Integer hisFriend; //他的好友

    public String getUserStrId() {
        return userStrId;
    }

    public void setUserStrId(String userStrId) {
        this.userStrId = userStrId;
    }

    public Integer getFriendTotal() {
        return friendTotal;
    }

    public void setFriendTotal(Integer friendTotal) {
        this.friendTotal = friendTotal;
    }

    public Integer getTeamTotal() {
        return teamTotal;
    }

    public void setTeamTotal(Integer teamTotal) {
        this.teamTotal = teamTotal;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getTeamPerformance() {
        return teamPerformance;
    }

    public void setTeamPerformance(Double teamPerformance) {
        this.teamPerformance = teamPerformance;
    }

    public String getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Integer getHisFriend() {
        return hisFriend;
    }

    public void setHisFriend(Integer hisFriend) {
        this.hisFriend = hisFriend;
    }
}
