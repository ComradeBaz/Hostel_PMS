/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.staticdata;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ComradeBaz
 */
public class GuestStatus {
    
    // Guest has been saved but not checked in
    public static int getSavedStatus() {
        return 0;
    }
    public static int getCheckedInStatus(){
        return 1;
    }
    public static int getCheckedOutStatus(){
        return 2;
    }
    public static int getMovedBedStatus(){
        return 3;
    }
    public static List<String> getAllGuestStatus() {
        List<String> retValues = new ArrayList<>();
        retValues.add("Saved");
        retValues.add("Checked In");
        retValues.add("Checked Out");
        retValues.add("Changed Room");
        
        return retValues;
    }
    public static List<Integer> getAllGuestStatusInteger() {
        List<Integer> retValues = new ArrayList<>();
        retValues.add(0);
        retValues.add(1);
        retValues.add(2);
        retValues.add(3);
        
        return retValues;
    }
}
