/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author David O'Brien
 */

@Entity
@Table(name="check_ins")
public class CheckIn implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="check_in_id")
    private int checkInId;
    
    @OneToOne
    @JoinColumn(name="guest_id")
    private Guest guest;
    
    @Column(name="room_name")
    private String roomName;
    
    @Column(name="bed_number")
    private int bedNumber;
    
    @Column(name="check_in_date")
    @Temporal(TemporalType.DATE)
    private Date checkInDate;
    
    @Column(name="check_out_date")
    @Temporal(TemporalType.DATE)
    private Date checkOutDate;
    
    @Column(name="rate")
    private float rate;
    
    public CheckIn() {
        // No arg constructor
    }
    /*
    public CheckIn(int guestId, int room, int bedNumber, Date cinDate, Date coutDate, float rate) {
        this.guestId = guestId;
        this.roomId = room;
        this.bedNumber = bedNumber;
        this.checkInDate = cinDate;
        if(coutDate != null)
            this.checkOutDate = coutDate;
        this.rate = rate;
    }
*/
        public CheckIn(Guest guest, String room, int bedNumber, Date cinDate, Date coutDate, float rate) {
        this.guest = guest;
        this.roomName = room;
        this.bedNumber = bedNumber;
        this.checkInDate = cinDate;
        if(coutDate != null)
            this.checkOutDate = coutDate;
        this.rate = rate;
    }
    
    public void setCheckInId(int id) {
        this.checkInId = id;
    }
    public int getCheckInId() {
        return checkInId;
    }
/*
    public void setGuestId(int gid) {
        this.guestId = gid;
    }
    public int getGuestId() {
        return guestId;
    }
*/

    public Guest getGuest() {
        return guest;
    }
    public void setGuest(Guest guest) {
        this.guest = guest;
    }   
    public void setRoomName(String rm) {
        this.roomName = rm;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setBedNumber(int bdnbr) {
        this.bedNumber = bdnbr;
    }
    public int getBedNumber() {
        return bedNumber;
    }
    public void setCheckInDate(Date cindate) {
        this.checkInDate = cindate;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }
    public void setCheckOutDate(Date coutdate) {
    this.checkOutDate = coutdate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    public void setRate(float rt) {
        this.rate = rt;
    } 
    public float getRate() {
        return this.rate;
    }
    @Override
    public String toString() {
        return roomName + " " + checkInDate + " " + checkOutDate + " " + bedNumber 
                + " " + rate;
    }
}
