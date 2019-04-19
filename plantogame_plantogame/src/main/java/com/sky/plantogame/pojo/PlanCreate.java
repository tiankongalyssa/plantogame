package com.sky.plantogame.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * planCreate实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_plan_create")
public class PlanCreate implements Serializable{

	@Id
	private String id;//id
	private String gamekey;//彩种代码
	private String gidFirst;//预测第1期号
	@JsonIgnore
	private String gidSecond;//预测第2期号
	@JsonIgnore
	private String gidThird;//预测第3期号
	@JsonIgnore
	private String present;//当前期号
	private String type;
	private Integer win;//0：挂 1：第一期中，2：第二期中，3：第三期中
	private Integer times; //倍数
	private String myriaPlane;//以","隔开(从统计表中分析数据选择中奖数字排名最高的5位)
	private String myriaAward;//当期开奖号码
	private Integer probability;//中奖机率(根据计划期号与是否猜中来确定，每开一期更新一次)
	@JsonIgnore
	private java.util.Date createTime;//create_time
	@JsonIgnore
	private java.util.Date updateTime;//update_time

	@Override
	public String toString() {
		return "PlanCreate{" +
				"id='" + id + '\'' +
				", gamekey='" + gamekey + '\'' +
				", gidFirst='" + gidFirst + '\'' +
				", gidSecond='" + gidSecond + '\'' +
				", gidThird='" + gidThird + '\'' +
				", present='" + present + '\'' +
				", type='" + type + '\'' +
				", win=" + win +
				", times=" + times +
				", myriaPlane='" + myriaPlane + '\'' +
				", myriaAward='" + myriaAward + '\'' +
				", probability=" + probability +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
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

	public String getGidFirst() {
		return gidFirst;
	}

	public void setGidFirst(String gidFirst) {
		this.gidFirst = gidFirst;
	}

	public String getGidSecond() {
		return gidSecond;
	}

	public void setGidSecond(String gidSecond) {
		this.gidSecond = gidSecond;
	}

	public String getGidThird() {
		return gidThird;
	}

	public void setGidThird(String gidThird) {
		this.gidThird = gidThird;
	}

	public String getPresent() {
		return present;
	}

	public void setPresent(String present) {
		this.present = present;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getWin() {
		return win;
	}

	public void setWin(Integer win) {
		this.win = win;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getMyriaPlane() {
		return myriaPlane;
	}

	public void setMyriaPlane(String myriaPlane) {
		this.myriaPlane = myriaPlane;
	}

	public String getMyriaAward() {
		return myriaAward;
	}

	public void setMyriaAward(String myriaAward) {
		this.myriaAward = myriaAward;
	}

	public Integer getProbability() {
		return probability;
	}

	public void setProbability(Integer probability) {
		this.probability = probability;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
