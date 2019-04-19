package com.sky.plantogame.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ChangLong {
    @JsonInclude(JsonInclude.Include.NON_NULL)  //为空不返回
    private String site; //位置
    private String name;
    private Integer nameCount;
    @JsonIgnore
    private Boolean nameFlag; //判断是否连续
    private String type;
    private Integer typeCount;
    @JsonIgnore
    private Boolean typeFlag;//判断是否连续

    @Override
    public String toString() {
        return "ChangLong{" +
                "site='" + site + '\'' +
                ", name='" + name + '\'' +
                ", nameCount=" + nameCount +
                ", nameFlag=" + nameFlag +
                ", type='" + type + '\'' +
                ", typeCount=" + typeCount +
                ", typeFlag=" + typeFlag +
                '}';
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNameCount() {
        return nameCount;
    }

    public void setNameCount(Integer nameCount) {
        this.nameCount = nameCount;
    }

    public Boolean getNameFlag() {
        return nameFlag;
    }

    public void setNameFlag(Boolean nameFlag) {
        this.nameFlag = nameFlag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(Integer typeCount) {
        this.typeCount = typeCount;
    }

    public Boolean getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(Boolean typeFlag) {
        this.typeFlag = typeFlag;
    }
}