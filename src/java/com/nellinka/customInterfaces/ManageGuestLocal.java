/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.customInterfaces;

import com.nellinka.entities.CheckedInGuests;
import com.nellinka.entities.Extras;
import com.nellinka.tools.ShowMovedBedReservations;
import com.nellinka.utilities.GuestExtrasObject;
import com.nellinka.utilities.OtherReservationsObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ComradeBaz
 */
@Local
public interface ManageGuestLocal {

    public void saveExtras(List<GuestExtrasObject> someExtras, int guestId);

    public List<GuestExtrasObject> buildGuestExtrasObject(int guestId, List<Extras> theExtras, boolean ignoreZero, float conversionRate, int reservationId);

    public CheckedInGuests saveGuestCheckIn(int guestId, String firstName, String lastName, String idNumber, String country, String roomName, int bedNumber, Date checkInDate, Date checkOutDate,
            float rate, String currency, float conversionRate, float discount);
    
    public CheckedInGuests getCheckedInGuest(int guestId);
    
    public List<GuestExtrasObject> updateExtrasCountForCheckIn(int guestId, List<GuestExtrasObject> extrasObjectsToAddToCheckIn, 
            String button, String itemNameString, String itemAmountString);
    
    public List<GuestExtrasObject> updateExtrasObjectToDisplay(List<GuestExtrasObject> guestExtrasObjectToDisplay, String itemNameString, String button);
    
    public float calculateTotal(CheckedInGuests cInGuest, Boolean deleteExtras, int lengthOfStay, float rate);
    
    public List<CheckedInGuests> getMovedBedReservations(CheckedInGuests checkedInGuest);
    
    public List<ShowMovedBedReservations> getMovedBedReservationsObject(List<CheckedInGuests> reservations);
    
    public float addAmountForOtherReservationsToTotal(List<CheckedInGuests> reservations, float theAmount);
    
    public void saveGuestCheckOut(CheckedInGuests aGuest);   
    
    public int saveEditGuestReservation(CheckedInGuests theGuest, String roomName, int bedNumber, Date checkInDate,Date checkOutDate, float rate);
    
    public void updateItemCount(int guestId, int entryId, String itemName, boolean addToItemCount);
    
    public boolean fixItemCount(String extraName, List<GuestExtrasObject> guestExtrasObject);
    
    public int saveGuest(String country, String idNumber, String firstName, String lastName);
    
    public int saveGuestFirstLastName(String firstName, String lastName);
    
    public int saveGuestToCheckInGuestsTable(int guestId, String firstName, String lastName, String idNumber, String country, String currency);
    
    public int saveGuestToCheckInGuestsTableFirstLastName(int guestId, String firstName, String lastName);
    
    public void saveUpdateToReservation(CheckedInGuests theGuest, String roomName, int bedNumber, Date checkInDate, Date checkOutDate, float rate, float discount);
    
    public List<GuestExtrasObject> updateExtrasItemCount(List<GuestExtrasObject> guestExtrasObject, String button, int guestId, String itemName, int entryId);
    
    public float payTheBill(CheckedInGuests aGuest, float theAmount);
    
    public List<Integer> getPossibleNewBedNumbersMore(int bedNumber, String roomName );
    
    public List<Integer> getPossibleNewBedNumbersLess(int bedNumber, String roomName );
    
    public int getClosestAvailableBedNumber(List<Integer> availableBeds, List<Integer> possibleBeds);
    
    public void saveNote(int reservationId, int guestId, String theText);
    
    public List<OtherReservationsObject> getOtherReservationsObject(List<CheckedInGuests> reservations);
    
    public void removeGuestByReservationId(int reservationId);
}
