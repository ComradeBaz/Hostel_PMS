/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.customInterfaces;

import com.nellinka.entities.CheckedInGuests;
import com.nellinka.entities.Extras;
import com.nellinka.entities.RoomGrid;
import com.nellinka.entities.RoomGridTwo;
import com.nellinka.entities.Rooms;
import com.nellinka.tools.FreeBed;
import com.nellinka.utilities.GuestExtrasObject;
import com.nellinka.utilities.ReservationObject;
import com.nellinka.utilities.RoomObject;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ComradeBaz
 */
@Local
public interface ManageHostelLocal {

    public List<CheckedInGuests> getSavedGuestsNotCheckedIn();

    public List<CheckedInGuests> getSavedGuestsCheckedIn();

    public List<GuestExtrasObject> getExtrasToDisplay();

    public List<Extras> getAllExtrasFromDb();

    public List<Rooms> getHostelRooms();
    
    public List<String> getHostelRoomNames();
    
    public int deleteRoom(String roomName);

    public List<Integer> updateRoomGrid(String rmName, int bdNr, Date ckInDate, Date ckOutDate, int reservationId, boolean isDeleteRequest);

    public void updateTheGridTables(String theTable, String rmName, int bdNr, int reservationId, Date ckInDate, Date ckOutDate, boolean isGoToEndDate, boolean isDeleteRequest);

    public CheckedInGuests getCheckedInGuestByGuestId(int guestId);

    public CheckedInGuests getCheckedInGuestByReservationAndGuestId(int reservationId, int guestId);

    public List<ReservationObject> getReservationObjectList(Date checkInDate, Date checkOutDate);

    public List<Integer> addFreeBedsToList(String aRoom, Date cInDate, Date cOutDate, CheckedInGuests guestToEdit, boolean isEditGuestType);

    public List<Integer> searchRoomGridInterface(Date cInDate, Date cOutDate, List<RoomGridInterface> newList, String aRoom, boolean searchToMonthEnd, CheckedInGuests guestToEdit);

    public List<CheckedInGuests> getSavedGuestsMovedBed();

    public CheckedInGuests getCheckedInGuestByGuestIdRoomNameBedNumber(int guestId, String roomName, int bedNumber);

    public void emptyRooms();

    public List<FreeBed> getBedsEmptyToday();
    
    public List<CheckedInGuests> getGuestsCheckingOutToday();
    
    public List<FreeBed> getGuestBedAvailableToday(List<CheckedInGuests> guestsCheckingOutToday, List<FreeBed> freeBedsToday);
    
    public void initialiseTheGridView();
    
    public boolean getIsBedEmptyByDay(String roomName, int bedNumber, Date date);
    
     public List<String> getAllRoomGridRooms();
     
     public List<Integer> getGridOccupants(String table, String roomName);
     
     public List<RoomGrid> getRoomGrid();
     
     public List<RoomGridTwo> getRoomGridTwo();
     
     public List<RoomObject> getAvailability();
     
     public int getTotalBeds();
     
     public int getTotalOccupancy();
     
     public void saveExtraItem(String extraName, float extraAmount, int isDepositValue);
     
     public void saveRoom(String roomName, int numberOfBeds, float rate);
     
     public List<CheckedInGuests> getCheckedInGuestByReservationId(int reservationId);
     
     public List<GuestExtrasObject> getCheckInExtrasToDisplay();
     
     public List<GuestExtrasObject> getCheckInExtrasToDisplayByCurrency(String currency);
     
     public int getNumberOfBedsByRoomName(String roomName);
     
     public int getReservationIdByRoomAndBedNumberAndDate(String roomName, int bedNumber, int dayAsInt, String theTable);
     
     public CheckedInGuests getOneCheckedInGuestByReservationId(int reservationId);
     
     public List<CheckedInGuests> getSavedGuestsCheckedInToday();
     
     public List<CheckedInGuests> updateGuestsCheckingOutToday(List<CheckedInGuests> theList, CheckedInGuests theGuest, Date newCheckOutDate);
     
     public List<String> getAllCountries();
     
     public List<CheckedInGuests> searchCheckedInGuests(String searchString);
     
     public List<Rooms> getOpenRooms();
     
     public List<Extras> getAllCheckInExtrasFromDb();
     
     public void updateRoom(String roomName, int numberOfBeds, float rate);
     
}
