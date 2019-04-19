package com.sky.gaindata.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 快3
 */
@Entity
@Table(name = "tb_k3")
public class K3 {
    @Id
    private String id;
    private String gamekey;
    private String gid;
    private String award;
    private String numTrend; //号码走势
    private String summation; //和值
    private String sumForm; //和值形态
    private String numForm;//号码形态
    private Date createTime;

    public K3(){

    }
    public K3(LotteryRecord lotteryRecord) {
        this.gamekey = lotteryRecord.getGamekey();
        this.gid = lotteryRecord.getGid();
        this.award = lotteryRecord.getAward();
        this.createTime = new Date();
    }

    @Override
    public String toString() {
        return "K3{" +
                "id='" + id + '\'' +
                ", gamekey='" + gamekey + '\'' +
                ", gid='" + gid + '\'' +
                ", award='" + award + '\'' +
                ", numTrend='" + numTrend + '\'' +
                ", summation=" + summation +
                ", sumForm='" + sumForm + '\'' +
                ", numForm='" + numForm + '\'' +
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

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getNumTrend() {
        return numTrend;
    }

    public void setNumTrend(String numTrend) {
        this.numTrend = numTrend;
    }

    public String getSummation() {
        return summation;
    }

    public void setSummation(String summation) {
        this.summation = summation;
    }

    public String getSumForm() {
        return sumForm;
    }

    public void setSumForm(String sumForm) {
        this.sumForm = sumForm;
    }

    public String getNumForm() {
        return numForm;
    }

    public void setNumForm(String numForm) {
        this.numForm = numForm;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
