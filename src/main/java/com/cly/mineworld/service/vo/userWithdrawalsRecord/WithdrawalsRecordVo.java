package com.cly.mineworld.service.vo.userWithdrawalsRecord;

//提现记录VO
public class WithdrawalsRecordVo {

    private String userStrId; // varchar(45) NOT NULL COMMENT '用户id',
    private String date; //日期,
    private Double amountOfMoney; //金额
    private String cashOutStatus; //状态

    public String getUserStrId() {
        return userStrId;
    }

    public void setUserStrId(String userStrId) {
        this.userStrId = userStrId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(Double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public String getCashOutStatus() {
        return cashOutStatus;
    }

    public void setCashOutStatus(String cashOutStatus) {
        this.cashOutStatus = cashOutStatus;
    }
}
