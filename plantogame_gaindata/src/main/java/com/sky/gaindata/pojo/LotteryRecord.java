package com.sky.gaindata.pojo;

import com.sky.gaindata.vo.Lottery;
import com.sky.gaindata.vo.LotteryDf;
import com.sky.gaindata.vo.LotteryK22;
import com.sky.gaindata.vo.LotteryX78;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * lotteryRecord实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "tb_lottery_record")
public class LotteryRecord implements Serializable {
    @Id
    private String id;//id
    private String gamekey;//彩种代码
    private String gid;//开奖期号
    private Date openTime;//开奖时间
    private String award; //开奖结果
    private String nextOpenIssue;//“下期开奖期号”
    private Date nextOpenTime;//下期开奖时间
    private Integer totalCount; //全天期数
    private Integer openCount; //当前期数
    private Date serverTime;//服务器时间 server_time
    private Date createTime;//创建时间

    //此构造函数不可去掉  去掉的话JPA报错  No default constructor for entity:
    public LotteryRecord() {

    }

    public LotteryRecord(LotteryK22 lotteryDf) {
        this.gid = lotteryDf.getTurnNum();
        this.award = lotteryDf.getOpenNum();
        this.openTime = lotteryDf.getOpenTime();
    }

    public LotteryRecord(LotteryDf lotteryDf) {
        this.gid = lotteryDf.getExpect();
        this.award = lotteryDf.getOpencode();
        this.openTime = lotteryDf.getOpentime();
    }

    public LotteryRecord(LotteryX78 lotteryX78) {
        this.gid = lotteryX78.getIssue();
        this.award = lotteryX78.getOpenNumber();
        this.openTime = lotteryX78.getOpenTime();
        this.gamekey = lotteryX78.getLotteryCode();
        this.serverTime = lotteryX78.getCreatedTime();
    }

    public LotteryRecord(Lottery lottery) {
        this.gid = lottery.getOpenIssue();
        this.award = lottery.getOpenResult();
        this.openTime = lottery.getOpenTime();
        this.nextOpenIssue = lottery.getNextOpenIssue();
        this.nextOpenTime = lottery.getNextOpenTime();
        this.openCount = lottery.getOpenCount();
        this.totalCount = lottery.getTotalCount();
        this.serverTime = lottery.getServerTime();
    }

    @Override
    public String toString() {
        return "LotteryRecord{" +
                "id='" + id + '\'' +
                ", gamekey='" + gamekey + '\'' +
                ", gid='" + gid + '\'' +
                ", openTime=" + openTime +
                ", award='" + award + '\'' +
                ", nextOpenIssue='" + nextOpenIssue + '\'' +
                ", nextOpenTime=" + nextOpenTime +
                ", totalCount=" + totalCount +
                ", openCount=" + openCount +
                ", serverTime=" + serverTime +
                ", createTime=" + createTime +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGamekey() {
        return gamekey;
    }

    public void setGamekey(String gamekey) {
        this.gamekey = gamekey;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getNextOpenIssue() {
        return nextOpenIssue;
    }

    public void setNextOpenIssue(String nextOpenIssue) {
        this.nextOpenIssue = nextOpenIssue;
    }

    public Date getNextOpenTime() {
        return nextOpenTime;
    }

    public void setNextOpenTime(Date nextOpenTime) {
        this.nextOpenTime = nextOpenTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
