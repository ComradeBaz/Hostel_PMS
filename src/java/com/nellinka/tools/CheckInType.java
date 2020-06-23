/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tools;

import com.nellinka.staticdata.CheckInTypes;
import java.util.Date;

/**
 *
 * @author ComradeBaz
 */
public class CheckInType {
    
    public CheckInType() {
        // No arg constructor
    }
    public static CheckInTypes getCheckInType(Date checkInDate, Date checkOutDate) {
        
        CheckInTypes other = CheckInTypes.CHECK_IN_AFTER_NEXT_MONTH;
        
        if((DateUtility.guestStayStartingThisMonth(checkInDate, checkOutDate))
                && (DateUtility.guestStayEndingThisMonth(checkOutDate)))
            return CheckInTypes.CHECK_IN_OUT_THIS_MONTH;
        
        if((DateUtility.guestStayStartingThisMonth(checkInDate, checkOutDate))
                && (DateUtility.guestCheckingOutOnTheFirstOfNextMonth(checkInDate, checkOutDate)))
            return CheckInTypes.CHECK_IN_THIS_MONTH_OUT_FIRST_OF_NEXT_MONTH;
        
        if((DateUtility.guestStayStartingThisMonth(checkInDate, checkOutDate))
                && (DateUtility.guestStayingUntilNextMonth(checkInDate, checkOutDate)))
            return CheckInTypes.CHECK_IN_THIS_MONTH_OUT_NEXT_MONTH;
        
        if((DateUtility.guestStayStartingThisMonth(checkInDate, checkOutDate))
                && (DateUtility.guestStayEndingAfterNextMonth(checkInDate, checkOutDate)))
            return CheckInTypes.CHECK_IN_THIS_MONTH_OUT_MONTH_AFTER_NEXT;
        
        if((DateUtility.guestStayStartingNextMonth(checkInDate, checkOutDate))
                && (DateUtility.guestStayingUntilNextMonth(checkInDate, checkOutDate)))
                return CheckInTypes.CHECK_IN_OUT_NEXT_MONTH;
        
        if((DateUtility.guestStayStartingNextMonth(checkInDate, checkOutDate))
                && (DateUtility.guestStayEndingAfterNextMonth(checkInDate, checkOutDate)))
            return CheckInTypes.CHECK_IN_NEXT_MONTH_OUT_THE_MONTH_AFTER;
        
        return other;
    }
}
