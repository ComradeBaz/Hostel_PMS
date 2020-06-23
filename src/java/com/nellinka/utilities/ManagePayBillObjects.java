/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ComradeBaz
 */
public class ManagePayBillObjects {

    public static List<PayBillObject> makePayBillObject(List<GuestExtrasObject> objects, float amountPaid, float totalAmount) {
        
        List<PayBillObject> retList = new ArrayList<>();

        for (GuestExtrasObject gEO : objects) {
            if (!gEO.isIsDeposit()) {
                PayBillObject newObject = new PayBillObject(gEO.getItemName(), gEO.getItemAmount());
                retList.add(newObject);
            }
        }
        PayBillObject amountPaidObject = new PayBillObject("Paid", amountPaid);
        PayBillObject totalAmountObject = new PayBillObject("Total", totalAmount);
        retList.add(amountPaidObject);
        retList.add(totalAmountObject);
        
        return retList;
    }

    public static List<PayBillObject> addToMakePayBillObjectList(String itemName, float itemAmount, List<PayBillObject> theList) {

        PayBillObject newObject = new PayBillObject(itemName, itemAmount);
        theList.add(newObject);

        return theList;
    }
}
