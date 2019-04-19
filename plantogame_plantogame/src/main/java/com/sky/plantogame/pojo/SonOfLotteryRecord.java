package com.sky.plantogame.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SonOfLotteryRecord extends LotteryRecord{
    private boolean isOpened;
    private String nowTimes; //倍数 及名称   eg: 新 1倍 老 1倍
    @JsonInclude(JsonInclude.Include.NON_NULL)  //为空不返回
    private String oldTimes;
    private String nowPlan;
    @JsonInclude(JsonInclude.Include.NON_NULL)  //为空不返回
    private String oldPlan;

    public SonOfLotteryRecord(LotteryRecord lotteryRecord) {
        this.award = lotteryRecord.getAward();
        this.id = lotteryRecord.getId();
        this.gid = lotteryRecord.getGid();
        this.gamekey = lotteryRecord.getGamekey();
        this.award = lotteryRecord.getAward();
        this.openTime = lotteryRecord.getOpenTime();
        this.openCount = lotteryRecord.getOpenCount();
        this.nextOpenIssue = lotteryRecord.getNextOpenIssue();
        this.nextOpenTime = lotteryRecord.getNextOpenTime();
        this.totalCount = lotteryRecord.getTotalCount();
        this.serverTime = lotteryRecord.getServerTime();
        this.createTime = lotteryRecord.getCreateTime();
    }

    @Override
    public String toString() {
        return "SonOfLotteryRecord{" +
                "isOpened=" + isOpened +
                ", nowTimes='" + nowTimes + '\'' +
                ", oldTimes='" + oldTimes + '\'' +
                ", nowPlan='" + nowPlan + '\'' +
                ", oldPlan='" + oldPlan + '\'' +
                ", id='" + id + '\'' +
                ", gamekey='" + gamekey + '\'' +
                ", gid='" + gid + '\'' +
                ", award='" + award + '\'' +
                ", openTime=" + openTime +
                ", nextOpenIssue='" + nextOpenIssue + '\'' +
                ", nextOpenTime=" + nextOpenTime +
                ", totalCount=" + totalCount +
                ", openCount=" + openCount +
                ", serverTime=" + serverTime +
                ", createTime=" + createTime +
                '}';
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public String getNowTimes() {
        return nowTimes;
    }

    public void setNowTimes(String nowTimes) {
        this.nowTimes = nowTimes;
    }

    public String getOldTimes() {
        return oldTimes;
    }

    public void setOldTimes(String oldTimes) {
        this.oldTimes = oldTimes;
    }

    public String getNowPlan() {
        return nowPlan;
    }

    public void setNowPlan(String nowPlan) {
        this.nowPlan = nowPlan;
    }

    public String getOldPlan() {
        return oldPlan;
    }

    public void setOldPlan(String oldPlan) {
        this.oldPlan = oldPlan;
    }
}
