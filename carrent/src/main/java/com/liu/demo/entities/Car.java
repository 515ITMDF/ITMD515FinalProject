package com.liu.demo.entities;

import javax.persistence.*;

@Entity
@Table(name="car")
public class Car {

    @Id
    private String id;
    private String type;
    private String seat;
    private float per_week;
    private float per_hour;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public float getPer_week() {
        return per_week;
    }

    public void setPer_week(float per_week) {
        this.per_week = per_week;
    }

    public float getPer_hour() {
        return per_hour;
    }

    public void setPer_hour(float per_hour) {
        this.per_hour = per_hour;
    }
}
