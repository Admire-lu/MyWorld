package com.cly.mineworld.service.vo.userCapitalDetails;

//资金明细VO
public class CapitalDetailsVo {

    private String userStrId; // varchar(45) NOT NULL COMMENT '用户id',
    private String date; //日期,
    private Double amountOfMoney; //金额
    private String type; //类型
    private String remarks;  //备注

    public Double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(Double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
