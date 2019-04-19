package com.sky.plantogame.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="tb_synthesize_guan_ya")
public class GuanYa {
    @Id
    private String id;
    private String gamekey;
    private String gid;
    private String award;
    private String guanYa;
    private String size;
    private String oddEven;
    private String dragonTigerOne;
    private String dragonTigerTow;
    private String dragonTigerThree;
    private String dragonTigerFour;
    private String dragonTigerFive;
    private Date createTime;

    @Override
    public String toString() {
        return "GuanYa{" +
                "id='" + id + '\'' +
                ", gamekey='" + gamekey + '\'' +
                ", gid='" + gid + '\'' +
                ", award='" + award + '\'' +
                ", guanYa='" + guanYa + '\'' +
                ", size='" + size + '\'' +
                ", oddEven='" + oddEven + '\'' +
                ", dragonTigerOne='" + dragonTigerOne + '\'' +
                ", dragonTigerTow='" + dragonTigerTow + '\'' +
                ", dragonTigerThree='" + dragonTigerThree + '\'' +
                ", dragonTigerFour='" + dragonTigerFour + '\'' +
                ", dragonTigerFive='" + dragonTigerFive + '\'' +
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

    public String getGuanYa() {
        return guanYa;
    }

    public void setGuanYa(String guanYa) {
        this.guanYa = guanYa;
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

    public String getDragonTigerOne() {
        return dragonTigerOne;
    }

    public void setDragonTigerOne(String dragonTigerOne) {
        this.dragonTigerOne = dragonTigerOne;
    }

    public String getDragonTigerTow() {
        return dragonTigerTow;
    }

    public void setDragonTigerTow(String dragonTigerTow) {
        this.dragonTigerTow = dragonTigerTow;
    }

    public String getDragonTigerThree() {
        return dragonTigerThree;
    }

    public void setDragonTigerThree(String dragonTigerThree) {
        this.dragonTigerThree = dragonTigerThree;
    }

    public String getDragonTigerFour() {
        return dragonTigerFour;
    }

    public void setDragonTigerFour(String dragonTigerFour) {
        this.dragonTigerFour = dragonTigerFour;
    }

    public String getDragonTigerFive() {
        return dragonTigerFive;
    }

    public void setDragonTigerFive(String dragonTigerFive) {
        this.dragonTigerFive = dragonTigerFive;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
