package com.cly.mineworld.service.vo.userRank;

//用户等级VO
public class UserRankVo {

    private String userStrId; // varchar(45) NOT NULL COMMENT '用户id',
    private Integer teamLv; // ` int(1) NOT NULL COMMENT '团队等级：1=青铜，2=白银，3=黄金，4=白金，5=砖石，6=王者',
    private Long teamCount; // `team_count` int(11) NOT NULL DEFAULT '1' COMMENT '团队总人数',
    private Double teamAmountCount; // `团队总充值金额
    private Long bronzeCount; // 青铜人数
    private Long silverCount; // 白银人数
    private Long goldCount; // 黄金人数
    private Long platinumCount; // 白金人数
    private Long diamondsCount; // 钻石人数
    private Long theKingCount; // 王者人数

    public String getUserStrId() {
        return userStrId;
    }

    public void setUserStrId(String userStrId) {
        this.userStrId = userStrId;
    }

    public Integer getTeamLv() {
        return teamLv;
    }

    public void setTeamLv(Integer teamLv) {
        this.teamLv = teamLv;
    }

    public Long getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(Long teamCount) {
        this.teamCount = teamCount;
    }

    public Double getTeamAmountCount() {
        return teamAmountCount;
    }

    public void setTeamAmountCount(Double teamAmountCount) {
        this.teamAmountCount = teamAmountCount;
    }

    public Long getBronzeCount() {
        return bronzeCount;
    }

    public void setBronzeCount(Long bronzeCount) {
        this.bronzeCount = bronzeCount;
    }

    public Long getSilverCount() {
        return silverCount;
    }

    public void setSilverCount(Long silverCount) {
        this.silverCount = silverCount;
    }

    public Long getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(Long goldCount) {
        this.goldCount = goldCount;
    }

    public Long getPlatinumCount() {
        return platinumCount;
    }

    public void setPlatinumCount(Long platinumCount) {
        this.platinumCount = platinumCount;
    }

    public Long getDiamondsCount() {
        return diamondsCount;
    }

    public void setDiamondsCount(Long diamondsCount) {
        this.diamondsCount = diamondsCount;
    }

    public Long getTheKingCount() {
        return theKingCount;
    }

    public void setTheKingCount(Long theKingCount) {
        this.theKingCount = theKingCount;
    }
}
