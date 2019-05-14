package com.sky.gaindata.vo;

import java.util.Date;

public class LotteryK22 {
    private String turnNum;
    private String openNum;
    private Date openTime;

    @Override
    public String toString() {
        return "LotteryK22{" +
                "turnNum='" + turnNum + '\'' +
                ", openNum='" + openNum + '\'' +
                ", openTime=" + openTime +
                '}';
    }

    public String getTurnNum() {
        return turnNum;
    }

    public void setTurnNum(String turnNum) {
        this.turnNum = turnNum;
    }

    public String getOpenNum() {
        return openNum;
    }

    public void setOpenNum(String openNum) {
        this.openNum = openNum;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }
}
