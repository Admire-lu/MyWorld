package com.cly.mineworld.service.vo.userWithdrawals;

/**
 * 提现数据Vo
 */
public class UserWithdrawalsVo {
    private String userStrId; // varchar(45) NOT NULL COMMENT '用户id',
    private Double accountVad; // 账户余额
    private Double actualArrival; // 实际到账
    private Double minerCost; // 矿工费
    private Double leastAccount; // 单笔提现最少VAD/DEH
    private Double mostAccount; // 单笔提现最多VAD/DEH
    private Integer typesOfWithdrawal; //提现类型

    public String getUserStrId() {
        return userStrId;
    }

    public void setUserStrId(String userStrId) {
        this.userStrId = userStrId;
    }

    public Double getAccountVad() {
        return accountVad;
    }

    public void setAccountVad(Double accountVad) {
        this.accountVad = accountVad;
    }

    public Double getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(Double actualArrival) {
        this.actualArrival = actualArrival;
    }

    public Double getMinerCost() {
        return minerCost;
    }

    public void setMinerCost(Double minerCost) {
        this.minerCost = minerCost;
    }

    public Double getLeastAccount() {
        return leastAccount;
    }

    public void setLeastAccount(Double leastAccount) {
        this.leastAccount = leastAccount;
    }

    public Double getMostAccount() {
        return mostAccount;
    }

    public void setMostAccount(Double mostAccount) {
        this.mostAccount = mostAccount;
    }

    public Integer getTypesOfWithdrawal() {
        return typesOfWithdrawal;
    }

    public void setTypesOfWithdrawal(Integer typesOfWithdrawal) {
        this.typesOfWithdrawal = typesOfWithdrawal;
    }
}
