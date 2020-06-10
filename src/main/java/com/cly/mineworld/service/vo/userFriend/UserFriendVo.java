package com.cly.mineworld.service.vo.userFriend;

//邀请好友VO
public class UserFriendVo {

    private String userStrId;  // varchar(45) NOT NULL,
    private Long inviteTotalNumber; //邀请总人数
    private Double totalProfit; //获得总收益

    public String getUserStrId() {
        return userStrId;
    }

    public void setUserStrId(String userStrId) {
        this.userStrId = userStrId;
    }

    public Long getInviteTotalNumber() {
        return inviteTotalNumber;
    }

    public void setInviteTotalNumber(Long inviteTotalNumber) {
        this.inviteTotalNumber = inviteTotalNumber;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }
}
