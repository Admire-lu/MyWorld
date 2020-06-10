package com.cly.mineworld.service.vo.userProfit;

//用户收益VO
public class UserProfitVo {

    private String userStrId; // varchar(45) NOT NULL COMMENT '用户id',
    private Double hotelProfit; //酒店收益
    private Double invitationProfit; // 邀请收益
    private Double teamProfit; // 团队收益
    private Double theftProfit; // 盗窃收益
    private Double beiTheftProfit; // 被盗窃收益
    private Double totalProfit; //合计收益

    public Double getTheftProfit() {
        return theftProfit;
    }

    public void setTheftProfit(Double theftProfit) {
        this.theftProfit = theftProfit;
    }

    public Double getBeiTheftProfit() {
        return beiTheftProfit;
    }

    public void setBeiTheftProfit(Double beiTheftProfit) {
        this.beiTheftProfit = beiTheftProfit;
    }

    public String getUserStrId() {
        return userStrId;
    }

    public void setUserStrId(String userStrId) {
        this.userStrId = userStrId;
    }

    public Double getHotelProfit() {
        return hotelProfit;
    }

    public void setHotelProfit(Double hotelProfit) {
        this.hotelProfit = hotelProfit;
    }

    public Double getInvitationProfit() {
        return invitationProfit;
    }

    public void setInvitationProfit(Double invitationProfit) {
        this.invitationProfit = invitationProfit;
    }

    public Double getTeamProfit() {
        return teamProfit;
    }

    public void setTeamProfit(Double teamProfit) {
        this.teamProfit = teamProfit;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }
}
