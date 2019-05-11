package com.sky.gaindata.vo;

import java.util.Date;

/**
 * df接口数据实体
 */
public class LotteryDf {
    private String expect; // 期号
    private String opencode; //开奖
    private Date opentime;
    private Date opentimestamp;

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getOpencode() {
        return opencode;
    }

    public void setOpencode(String opencode) {
        this.opencode = opencode;
    }

    public Date getOpentime() {
        return opentime;
    }

    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    public Date getOpentimestamp() {
        return opentimestamp;
    }

    public void setOpentimestamp(Date opentimestamp) {
        this.opentimestamp = opentimestamp;
    }

    @Override
    public String toString() {
        return "LotteryDf{" +
                "expect='" + expect + '\'' +
                ", opencode='" + opencode + '\'' +
                ", opentime=" + opentime +
                ", opentimestamp=" + opentimestamp +
                '}';
    }
}
