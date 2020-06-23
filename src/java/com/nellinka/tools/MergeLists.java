/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tools;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ComradeBaz
 */
public class MergeLists {
    
    public static List<Integer> mergeLists(List<Integer> listOne, List<Integer> listTwo) {
        // listOne is days available from the guest checkInDate to the end of this month
        // If a bed is not available for this month don't search next month
        List<Integer> listToReturn = new ArrayList<>();
        for(int i=0; i<listOne.size(); i++) {
            for(int j=0; j<listTwo.size(); j++) {
                if(listOne.get(i).equals(listTwo.get(j)))
                    listToReturn.add(listOne.get(i));
            }
        }
        return listToReturn;
    }
}
