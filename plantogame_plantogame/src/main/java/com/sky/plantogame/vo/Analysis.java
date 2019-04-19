package com.sky.plantogame.vo;

public class Analysis {
    private String gameKey;
    private Integer place; // 第几位
    private Integer number; //目标数字
    private Integer appear;//出现次数
    private Integer maxMiss;//最大遗漏
    private Integer meanMiss;//平均遗漏
    private Integer succession;//最大连出

    @Override
    public String toString() {
        return "Analysis{" +
                "gameKey='" + gameKey + '\'' +
                ", place=" + place +
                ", number=" + number +
                ", appear=" + appear +
                ", maxMiss=" + maxMiss +
                ", meanMiss=" + meanMiss +
                ", succession=" + succession +
                '}';
    }

    public String getGameKey() {
        return gameKey;
    }

    public void setGameKey(String gameKey) {
        this.gameKey = gameKey;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getAppear() {
        return appear;
    }

    public void setAppear(Integer appear) {
        this.appear = appear;
    }

    public Integer getMaxMiss() {
        return maxMiss;
    }

    public void setMaxMiss(Integer maxMiss) {
        this.maxMiss = maxMiss;
    }

    public Integer getMeanMiss() {
        return meanMiss;
    }

    public void setMeanMiss(Integer meanMiss) {
        this.meanMiss = meanMiss;
    }

    public Integer getSuccession() {
        return succession;
    }

    public void setSuccession(Integer succession) {
        this.succession = succession;
    }
}
