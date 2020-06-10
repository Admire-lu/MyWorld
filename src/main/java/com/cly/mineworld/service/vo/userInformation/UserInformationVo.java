package com.cly.mineworld.service.vo.userInformation;

//用户信息Vo 包含团队等级
public class UserInformationVo {

    private Integer id;//int(11) NOT NULL
    private String strId;//varchar(45) NOT NULL
    private String nickname;//varchar(45) NOT NULL
    private String psw;//varchar(45) NOT NULL
    private String mobile;//varchar(13) NULL
    private String email;//varchar(45) NULL
    private String headImage;//varchar(200) NULL
    private String transactionPsw;//varchar(45) NULL交易密码
    private String userType;//varchar(45) NOT NULL用户类型
    private Integer exp;//int(11) NOT NULL
    private Integer lv;//int(11) NOT NULL
    private Double vad;//double NOT NULL
    private Double deh;
    private String userLevelCode;
    private Integer status;//int(1) NOT NULL
    private Integer modifyTime;//int(11) NOT NULL
    private Integer createTime;//int(11) NOT NULL
    private String tokenAddress;//varchar(200) DEFAULT NULL COMMENT 'token地址'@Column(name = "token_address")
    private Double freezeAmount; // double DEFAULT '0' COMMENT '冻结金额',
    private String userTeamLv; //团队等级

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
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

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getTransactionPsw() {
        return transactionPsw;
    }

    public void setTransactionPsw(String transactionPsw) {
        this.transactionPsw = transactionPsw;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getLv() {
        return lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

    public Double getVad() {
        return vad;
    }

    public void setVad(Double vad) {
        this.vad = vad;
    }

    public String getUserLevelCode() {
        return userLevelCode;
    }

    public void setUserLevelCode(String userLevelCode) {
        this.userLevelCode = userLevelCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Integer modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public Double getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(Double freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public String getUserTeamLv() {
        return userTeamLv;
    }

    public void setUserTeamLv(String userTeamLv) {
        this.userTeamLv = userTeamLv;
    }

	public Double getDeh() {
		return deh;
	}

	public void setDeh(Double deh) {
		this.deh = deh;
	}
}
