package cn.pengan.entity;

import java.util.Date;

public class Award {
    private Long awardId;
    private String awardName;
    private String awardDesc;
    private String awardImg;
    private Integer point;
    private Integer priority;
    private Date createTime;
    private Date expireTime;
    private Date lastEditTime;
    private Integer enable_status;
    private Long shopId;

    public Long getAwardId() {
        return awardId;
    }

    public void setAwardId(Long awardId) {
        this.awardId = awardId;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardDesc() {
        return awardDesc;
    }

    public void setAwardDesc(String awardDesc) {
        this.awardDesc = awardDesc;
    }

    public String getAwardImg() {
        return awardImg;
    }

    public void setAwardImg(String awardImg) {
        this.awardImg = awardImg;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getEnable_status() {
        return enable_status;
    }

    public void setEnable_status(Integer enable_status) {
        this.enable_status = enable_status;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "Award{" +
                "awardId=" + awardId +
                ", awardName='" + awardName + '\'' +
                ", awardDesc='" + awardDesc + '\'' +
                ", awardImg='" + awardImg + '\'' +
                ", point=" + point +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                ", lastEditTime=" + lastEditTime +
                ", enable_status=" + enable_status +
                ", shopId=" + shopId +
                '}';
    }
}
