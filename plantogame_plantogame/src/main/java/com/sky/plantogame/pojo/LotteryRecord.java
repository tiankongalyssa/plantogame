package com.sky.plantogame.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * lotteryRecord实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_lottery_record")
public class LotteryRecord implements Serializable{
	@Id
	protected String id;//id
	protected String gamekey;//彩种代码
	protected String gid;//开奖期号
	protected String award;//开奖结果
	protected Date openTime;
	protected String nextOpenIssue;//“下期开奖期号”
	protected Date nextOpenTime;//下期开奖时间
	@JsonInclude(JsonInclude.Include.NON_NULL)  //为空不返回
	protected Integer totalCount;
	@JsonInclude(JsonInclude.Include.NON_NULL)  //为空不返回
	protected Integer openCount;
	protected Date serverTime;
	@JsonIgnore
	protected Date createTime;//创建时间

	@Override
	public String toString() {
		return "LotteryRecord{" +
				"id='" + id + '\'' +
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

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
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
