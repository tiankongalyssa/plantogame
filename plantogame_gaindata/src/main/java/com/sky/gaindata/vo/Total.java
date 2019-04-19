package com.sky.gaindata.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 总和
 */
@Entity
@Table(name="tb_synthesize_total")
public class Total {
    @Id
    private String id;
    private Date createTime; //创建日期
    private String gamekey; //游戏名
    private String gid; //期数
    private String award; //开奖号码
    private String total; //总和
    private String size; //大小
    private String oddEven; //单双
    private String dragonTiger; //龙虎
    private String fistMidBack; //前三

    @Override
    public String toString() {
        return "Total{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", gamekey='" + gamekey + '\'' +
                ", gid='" + gid + '\'' +
                ", award='" + award + '\'' +
                ", total='" + total + '\'' +
                ", size='" + size + '\'' +
                ", oddEven='" + oddEven + '\'' +
                ", dragonTiger='" + dragonTiger + '\'' +
                ", fistMidBack='" + fistMidBack + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOddEven() {
        return oddEven;
    }

    public void setOddEven(String oddEven) {
        this.oddEven = oddEven;
    }

    public String getDragonTiger() {
        return dragonTiger;
    }

    public void setDragonTiger(String dragonTiger) {
        this.dragonTiger = dragonTiger;
    }

    public String getFistMidBack() {
        return fistMidBack;
    }

    public void setFistMidBack(String fistMidBack) {
        this.fistMidBack = fistMidBack;
    }
}
