package com.sky.plantogame.vo;

import java.util.List;

public class HotCool {
    private String place;
    private String gameKey;
    private List<Num> hot;
    private List<Num> warmth;
    private List<Num> cool;

    @Override
    public String toString() {
        return "HotCool{" +
                "place='" + place + '\'' +
                ", gameKey='" + gameKey + '\'' +
                ", hot=" + hot +
                ", warmth=" + warmth +
                ", cool=" + cool +
                '}';
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getGameKey() {
        return gameKey;
    }

    public void setGameKey(String gameKey) {
        this.gameKey = gameKey;
    }

    public List<Num> getHot() {
        return hot;
    }

    public void setHot(List<Num> hot) {
        this.hot = hot;
    }

    public List<Num> getWarmth() {
        return warmth;
    }

    public void setWarmth(List<Num> warmth) {
        this.warmth = warmth;
    }

    public List<Num> getCool() {
        return cool;
    }

    public void setCool(List<Num> cool) {
        this.cool = cool;
    }
}
