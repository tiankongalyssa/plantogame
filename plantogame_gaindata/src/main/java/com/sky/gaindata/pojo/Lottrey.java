package com.sky.gaindata.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * lottrey实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_lottrey")
public class Lottrey implements Serializable{

	@Id
	private String id;//id


	
	private String gamekey;//彩种代码
	private String name;//彩种名称
	private Integer state;//是否激活
	private java.util.Date createTime;//create_time
	private String createUser;//create_user

	
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

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	@Override
	public String toString() {
		return "Lottrey{" +
				"id='" + id + '\'' +
				", gamekey='" + gamekey + '\'' +
				", name='" + name + '\'' +
				", state=" + state +
				", createTime=" + createTime +
				", createUser='" + createUser + '\'' +
				'}';
	}
}
