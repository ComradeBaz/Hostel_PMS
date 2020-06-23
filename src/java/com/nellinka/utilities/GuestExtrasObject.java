/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.utilities;

/**
 *
 * @author ComradeBaz
 * The class is used to gather three pieces of data in one place
 * The information is returned in a list
 * editguest.xhtml iterates over the list to populate the extras 
 * table displayed on the page
 */
public class GuestExtrasObject {
    
    private int itemCount;
    private String itemName;
    private float itemAmount;
    private int guestId;
    private int extrasEntryId;
    private boolean isDeposit;
    private int reservationId;
    
    // This constructor called when items are added to the guest from the editguest.xhtml page
    public GuestExtrasObject(int itemCount, String itemName, float itemAmount, int guestId, int extrasEntryId, boolean isDeposit, int reservationId) {
        this.itemCount = itemCount;
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.guestId = guestId;
        this.extrasEntryId = extrasEntryId;
        this.isDeposit = isDeposit;
        this.reservationId = reservationId;
    }
    // This constructor called when items are added to the guest from the editguest.xhtml page
    public GuestExtrasObject(int itemCount, String itemName, float itemAmount, int guestId, int extrasEntryId) {
        this.itemCount = itemCount;
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.guestId = guestId;
        this.extrasEntryId = extrasEntryId;
    }
    // This constructer called when checking in a guest for the first time
    // There are no existing entries in the GuestExtras table so there is no extrasEntryId
    public GuestExtrasObject(int itemCount, String itemName, float itemAmount, int guestId) {
        this.itemCount = itemCount;
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.guestId = guestId;
    }
    // This constructor called from CheckInFlow.getExtrasToDisplay()
    public GuestExtrasObject(int itemCount, String itemName, float itemAmount) {
        this.itemCount = itemCount;
        this.itemName = itemName;
        this.itemAmount = itemAmount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(float itemAmount) {
        this.itemAmount = itemAmount;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getExtrasEntryId() {
        return extrasEntryId;
    }

    public void setExtrasEntryId(int extrasEntryId) {
        this.extrasEntryId = extrasEntryId;
    }

    public boolean isIsDeposit() {
        return isDeposit;
    }

    public void setIsDeposit(boolean isDeposit) {
        this.isDeposit = isDeposit;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
        
    @Override
    public String toString() {
        return "GuestExtrasObject{" + "itemCount=" + itemCount + ", itemName=" + itemName + ", itemAmount=" + itemAmount + '}';
    }
}
