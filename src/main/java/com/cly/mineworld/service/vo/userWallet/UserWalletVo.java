package com.cly.mineworld.service.vo.userWallet;

//我的钱包VO
public class UserWalletVo {

    private String userStrId; // varchar(45) NOT NULL COMMENT '用户id',
    private Double accountVad; // 账户余额
    private Double toBeReleasedVad; // 待释放余额
    private Double accountDeh; //以太币
    private Double toBeReleasedDeh; //待释放以太币
    private Double directionalVad; //定向vad

    public Double getDirectionalVad() {
        return directionalVad;
    }

    public void setDirectionalVad(Double directionalVad) {
        this.directionalVad = directionalVad;
    }

    public Double getAccountDeh() {
        return accountDeh;
    }

    public void setAccountDeh(Double accountDeh) {
        this.accountDeh = accountDeh;
    }

    public Double getToBeReleasedDeh() {
        return toBeReleasedDeh;
    }

    public void setToBeReleasedDeh(Double toBeReleasedDeh) {
        this.toBeReleasedDeh = toBeReleasedDeh;
    }

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

    public Double getToBeReleasedVad() {
        return toBeReleasedVad;
    }

    public void setToBeReleasedVad(Double toBeReleasedVad) {
        this.toBeReleasedVad = toBeReleasedVad;
    }

}
