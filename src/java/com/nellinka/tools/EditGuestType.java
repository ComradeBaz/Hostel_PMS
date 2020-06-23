/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tools;

import com.nellinka.entities.CheckedInGuests;
import com.nellinka.staticdata.EditGuestTypes;
import java.util.Date;

/**
 *
 * @author ComradeBaz
 */
public class EditGuestType {
    public static EditGuestTypes getEditGuestType(CheckedInGuests theGuest, Date checkInDate, Date checkOutDate, String roomName, int bedNumber) {
        
        boolean checkInDate_edited = !(theGuest.getCheckInDate().equals(checkInDate));
        boolean checkOutDate_edited = !(theGuest.getCheckOutDate().equals(checkOutDate));
        boolean roomName_edited = !(theGuest.getRoomName().equals(roomName));
        boolean bedNumber_edited = !((theGuest.getBedNumber() == bedNumber));
        
        // Most likely scenario
        if((checkOutDate_edited) &&(!checkInDate_edited)) {
            if(((!roomName_edited) && (!bedNumber_edited)))
                return EditGuestTypes.EDIT_CHECK_OUT_DATE;
            if((roomName_edited))
                return EditGuestTypes.EDIT_CHECK_OUT_DATE_AND_ROOM_AND_BED_NUMBER;
            if((!roomName_edited) && (bedNumber_edited))
                return EditGuestTypes.EDIT_CHECK_OUT_DATE_AND_BED_NUMBER;
            
        }
        if((checkInDate_edited) && (!checkOutDate_edited)) {
             if((!roomName_edited) && (!bedNumber_edited))
                return EditGuestTypes.EDIT_CHECK_IN_DATE;
            if(roomName_edited)
                return EditGuestTypes.EDIT_CHECK_IN_DATE_AND_ROOM_AND_BED_NUMBER;
            if((!roomName_edited) && (bedNumber_edited))
                return EditGuestTypes.EDIT_CHECK_IN_DATE_AND_BED_NUMBER;
        }
        if((checkInDate_edited) && (checkOutDate_edited)) {
            if((!roomName_edited) && (!bedNumber_edited))
                return EditGuestTypes.EDIT_CHECK_IN_DATE_AND_CHECK_OUT_DATE;
            if(roomName_edited)
                return EditGuestTypes.EDIT_CHECK_IN_DATE_AND_CHECK_OUT_DATE_AND_ROOM_AND_BED_NUMBER;
            if((!roomName_edited) && (bedNumber_edited))
                return EditGuestTypes.EDIT_CHECK_IN_DATE_AND_CHECK_OUT_DATE_AND_BED_NUMBER;
        }
        if((!checkInDate_edited) && (!checkOutDate_edited)) {
            if(roomName_edited)
                return EditGuestTypes.EDIT_ROOM_AND_BED_NUMBER;
            if((!roomName_edited) && (bedNumber_edited))
                return EditGuestTypes.EDIT_BED_NUMBER;
        }
        
       return EditGuestTypes.UNKNOWN;
    }
}
