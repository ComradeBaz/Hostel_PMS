/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tools;

/**
 *
 * @author ComradeBaz
 * Class is used to store values to be displayed when the user navigates to 
 * the showguest page of the checkoutflow and the current guest has previously 
 * moved bed during their current stay
 */
public class ShowMovedBedReservations {
    
    private int oldNoOfDays;
    private float oldRate;
    private String oldRoom;
    final private float oldTotal;

    public ShowMovedBedReservations(String room, int noDays, float rate) {
        this.oldRoom = room;
        this.oldNoOfDays = noDays;
        this.oldRate = rate;
        oldTotal = rate * noDays;
    }
    
    public int getOldNoOfDays() {
        return oldNoOfDays;
    }

    public void setOldNoOfDays(int oldNoOfDays) {
        this.oldNoOfDays = oldNoOfDays;
    }

    public String getOldRoom() {
        return oldRoom;
    }

    public void setOldRoom(String oldRoom) {
        this.oldRoom = oldRoom;
    }
    
    public float getOldRate() {
        return oldRate;
    }

    public void setOldRate(float oldRate) {
        this.oldRate = oldRate;
    }

    public float getOldTotal() {
        return oldTotal;
    }  
}
