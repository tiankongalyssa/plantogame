package com.sky.plantogame.vo;

public class AnalysisResult {
    private String gameKey;
    private String place;
    private String appear;//出现次数
    private String maxMiss;//最大遗漏
    private String meanMiss;//平均遗漏
    private String succession;//最大连出

    @Override
    public String toString() {
        return "AnalysisResult{" +
                "gameKey='" + gameKey + '\'' +
                ", place='" + place + '\'' +
                ", appear='" + appear + '\'' +
                ", maxMiss='" + maxMiss + '\'' +
                ", meanMiss='" + meanMiss + '\'' +
                ", succession='" + succession + '\'' +
                '}';
    }

    public String getGameKey() {
        return gameKey;
    }

    public void setGameKey(String gameKey) {
        this.gameKey = gameKey;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAppear() {
        return appear;
    }

    public void setAppear(String appear) {
        this.appear = appear;
    }

    public String getMaxMiss() {
        return maxMiss;
    }

    public void setMaxMiss(String maxMiss) {
        this.maxMiss = maxMiss;
    }

    public String getMeanMiss() {
        return meanMiss;
    }

    public void setMeanMiss(String meanMiss) {
        this.meanMiss = meanMiss;
    }

    public String getSuccession() {
        return succession;
    }

    public void setSuccession(String succession) {
        this.succession = succession;
    }
}
