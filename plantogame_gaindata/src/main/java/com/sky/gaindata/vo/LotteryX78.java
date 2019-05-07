package com.sky.gaindata.vo;

import java.util.Date;

/**
 * x78彩票记录类
 */
public class LotteryX78 {
    private String lotteryCode;
    private String issue;
    private String openNumber;
    private Date openTime;
    private boolean open;
    private Date createdTime;

    @Override
    public String toString() {
        return "LotteryX78{" +
                "lotteryCode='" + lotteryCode + '\'' +
                ", issue='" + issue + '\'' +
                ", openNumber='" + openNumber + '\'' +
                ", openTime=" + openTime +
                ", open=" + open +
                ", createdTime=" + createdTime +
                '}';
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getOpenNumber() {
        return openNumber;
    }

    public void setOpenNumber(String openNumber) {
        this.openNumber = openNumber;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
