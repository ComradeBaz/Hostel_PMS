/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tools;

/**
 *
 * @author ComradeBaz
 */
public class FreeBed {
    
    private String roomName;
    private int bedNumber;
    private int dayAsInt;
    private int reservationId;
    
    public FreeBed() {
        // No arg constructor
    }
    
    public FreeBed(String roomName, int bedNumber, int dayAsInt) {
        this.roomName = roomName;
        this.bedNumber = bedNumber;
        this.dayAsInt = dayAsInt;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public int getDayAsInt() {
        return dayAsInt;
    }

    public void setDayAsInt(int dayAsInt) {
        this.dayAsInt = dayAsInt;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
}
