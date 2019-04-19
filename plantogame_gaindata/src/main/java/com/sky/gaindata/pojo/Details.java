package com.sky.gaindata.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * details实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_details")
public class Details implements Serializable{

	@Id
	private String id;//id
	private String gid;//期号
	private String gamekey;//彩种代码
	private String award;//开奖结果
	private java.util.Date time;//开奖时间
	private String zero;//开奖第0位
	private String one;//开奖第1位
	private String tow;//开奖第2位
	private String three;//开奖第3位
	private String four;//开奖第4位
	private String five;//开奖第5位
	private String six;//开奖第6位
	private String seven;//开奖第7位
	private String eight;//开奖第8位
	private String nine;//开奖第9位
	private String beforeZero;
	private String beforeOne;
	private String beforeTow;
	private String beforeThree;
	private String beforeFour;
	private String beforeFive;
	private String beforeSix;
	private String beforeSeven;
	private String beforeEight;
	private String beforeNine;
	private java.util.Date createTime;//create_time

	@Override
	public String toString() {
		return "Details{" +
				"id='" + id + '\'' +
				", gid='" + gid + '\'' +
				", gamekey='" + gamekey + '\'' +
				", award='" + award + '\'' +
				", time=" + time +
				", zero='" + zero + '\'' +
				", one='" + one + '\'' +
				", tow='" + tow + '\'' +
				", three='" + three + '\'' +
				", four='" + four + '\'' +
				", five='" + five + '\'' +
				", six='" + six + '\'' +
				", seven='" + seven + '\'' +
				", eight='" + eight + '\'' +
				", nine='" + nine + '\'' +
				", beforeZero='" + beforeZero + '\'' +
				", beforeOne='" + beforeOne + '\'' +
				", beforeTow='" + beforeTow + '\'' +
				", beforeThree='" + beforeThree + '\'' +
				", beforeFour='" + beforeFour + '\'' +
				", beforeFive='" + beforeFive + '\'' +
				", beforeSix='" + beforeSix + '\'' +
				", beforeSeven='" + beforeSeven + '\'' +
				", beforeEight='" + beforeEight + '\'' +
				", beforeNine='" + beforeNine + '\'' +
				", createTime=" + createTime +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGamekey() {
		return gamekey;
	}

	public void setGamekey(String gamekey) {
		this.gamekey = gamekey;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getZero() {
		return zero;
	}

	public void setZero(String zero) {
		this.zero = zero;
	}

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public String getTow() {
		return tow;
	}

	public void setTow(String tow) {
		this.tow = tow;
	}

	public String getThree() {
		return three;
	}

	public void setThree(String three) {
		this.three = three;
	}

	public String getFour() {
		return four;
	}

	public void setFour(String four) {
		this.four = four;
	}

	public String getFive() {
		return five;
	}

	public void setFive(String five) {
		this.five = five;
	}

	public String getSix() {
		return six;
	}

	public void setSix(String six) {
		this.six = six;
	}

	public String getSeven() {
		return seven;
	}

	public void setSeven(String seven) {
		this.seven = seven;
	}

	public String getEight() {
		return eight;
	}

	public void setEight(String eight) {
		this.eight = eight;
	}

	public String getNine() {
		return nine;
	}

	public void setNine(String nine) {
		this.nine = nine;
	}

	public String getBeforeZero() {
		return beforeZero;
	}

	public void setBeforeZero(String beforeZero) {
		this.beforeZero = beforeZero;
	}

	public String getBeforeOne() {
		return beforeOne;
	}

	public void setBeforeOne(String beforeOne) {
		this.beforeOne = beforeOne;
	}

	public String getBeforeTow() {
		return beforeTow;
	}

	public void setBeforeTow(String beforeTow) {
		this.beforeTow = beforeTow;
	}

	public String getBeforeThree() {
		return beforeThree;
	}

	public void setBeforeThree(String beforeThree) {
		this.beforeThree = beforeThree;
	}

	public String getBeforeFour() {
		return beforeFour;
	}

	public void setBeforeFour(String beforeFour) {
		this.beforeFour = beforeFour;
	}

	public String getBeforeFive() {
		return beforeFive;
	}

	public void setBeforeFive(String beforeFive) {
		this.beforeFive = beforeFive;
	}

	public String getBeforeSix() {
		return beforeSix;
	}

	public void setBeforeSix(String beforeSix) {
		this.beforeSix = beforeSix;
	}

	public String getBeforeSeven() {
		return beforeSeven;
	}

	public void setBeforeSeven(String beforeSeven) {
		this.beforeSeven = beforeSeven;
	}

	public String getBeforeEight() {
		return beforeEight;
	}

	public void setBeforeEight(String beforeEight) {
		this.beforeEight = beforeEight;
	}

	public String getBeforeNine() {
		return beforeNine;
	}

	public void setBeforeNine(String beforeNine) {
		this.beforeNine = beforeNine;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
