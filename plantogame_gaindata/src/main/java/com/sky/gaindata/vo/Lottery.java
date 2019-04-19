package com.sky.gaindata.vo;
import java.util.Date;

public class Lottery {
    private Date openTime;
    private String openIssue;
    private String openResult;
    private Date nextOpenTime;
    private String nextOpenIssue;
    private Integer totalCount;
    private Integer openCount;
    private Date serverTime;

    @Override
    public String toString() {
        return "Lottery{" +
                "openTime=" + openTime +
                ", openIssue='" + openIssue + '\'' +
                ", openResult='" + openResult + '\'' +
                ", nextOpenTime=" + nextOpenTime +
                ", nextOpenIssue='" + nextOpenIssue + '\'' +
                ", totalCount=" + totalCount +
                ", openCount=" + openCount +
                ", serverTime=" + serverTime +
                '}';
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public String getOpenIssue() {
        return openIssue;
    }

    public void setOpenIssue(String openIssue) {
        this.openIssue = openIssue;
    }

    public String getOpenResult() {
        return openResult;
    }

    public void setOpenResult(String openResult) {
        this.openResult = openResult;
    }

    public Date getNextOpenTime() {
        return nextOpenTime;
    }

    public void setNextOpenTime(Date nextOpenTime) {
        this.nextOpenTime = nextOpenTime;
    }

    public String getNextOpenIssue() {
        return nextOpenIssue;
    }

    public void setNextOpenIssue(String nextOpenIssue) {
        this.nextOpenIssue = nextOpenIssue;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getOpenCount() {
        return openCount;
    }

    public void setOpenCount(Integer openCount) {
        this.openCount = openCount;
    }

    public Date getServerTime() {
        return serverTime;
    }

    public void setServerTime(Date serverTime) {
        this.serverTime = serverTime;
    }
}
