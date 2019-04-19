package com.sky.plantogame.vo;


public class Num {
    private Integer place;
    private Integer value;

    public Num(Integer place, Integer value) {
        this.place = place;
        this.value = value;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Num{" +
                "place=" + place +
                ", value=" + value +
                '}';
    }
}
