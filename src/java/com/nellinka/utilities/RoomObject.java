/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.utilities;

import java.io.Serializable;

/**
 *
 * @author ComradeBaz
 */
public class RoomObject implements Serializable {
    private String roomName;
    private long availableBeds;
    private long availableToday;
    private long availableTomorrow;
    // Today or tomorrow
    private String day;
    
    public RoomObject() {
        // no arg constructor
    }
    public RoomObject(String roomName, long availableBeds, String day) {
        this.roomName = roomName;
        this.availableBeds = availableBeds;
        this.day = day;
    }

    public RoomObject(String roomName, long availableToday, long availableTomorrow) {
        this.roomName = roomName;
        this.availableToday = availableToday;
        this.availableTomorrow = availableTomorrow;
    }
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(long availableBeds) {
        this.availableBeds = availableBeds;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getAvailableToday() {
        return availableToday;
    }

    public void setAvailableToday(long availableToday) {
        this.availableToday = availableToday;
    }

    public long getAvailableTomorrow() {
        return availableTomorrow;
    }

    public void setAvailableTomorrow(long availableTomorrow) {
        this.availableTomorrow = availableTomorrow;
    }
}
