package com.cly.mineworld.service.vo.userNotice;



//用户公告Vo
public class UserNoticeVo {

    private String strId;// varchar(45) NOT NULL,
    private String  noticeTitle;// varchar(45) NOT NULL, 公告标题
    private String  noticeContent;// text NOT NULL,  公告内容
    private Integer  releaseStatus;// int(1) NOT NULL COMMENT '发布状态：1=已发布，2=未发布',
    private String  creatorStrId;// varchar(45) DEFAULT NULL COMMENT '创建者strid',
    private String createTime;// int(11) NOT NULL,  发布时间

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Integer getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Integer releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getCreatorStrId() {
        return creatorStrId;
    }

    public void setCreatorStrId(String creatorStrId) {
        this.creatorStrId = creatorStrId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
