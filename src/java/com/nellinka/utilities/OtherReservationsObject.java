/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.utilities;

import java.util.Date;

/**
 *
 * @author ComradeBaz
 */
public class OtherReservationsObject {
    
    private String itemName;
    private String itemValue;
    
    public OtherReservationsObject() {
        // No arg constructor
    }
    public OtherReservationsObject(String itemName, String itemValue) {
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
