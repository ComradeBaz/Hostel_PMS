/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.customInterfaces;

import com.nellinka.entities.CheckedInGuests;
import com.nellinka.entities.Extras;
import com.nellinka.entities.GlobalVariables;
import com.nellinka.entities.RoomGrid;
import com.nellinka.entities.RoomGridTwo;
import com.nellinka.entities.Rooms;
import com.nellinka.staticdata.CheckInTypes;
import com.nellinka.staticdata.GuestStatus;
import com.nellinka.tools.CheckInType;
import com.nellinka.tools.ConvertArrayToList;
import com.nellinka.tools.DateUtility;
import com.nellinka.tools.FreeBed;
import com.nellinka.tools.Logger;
import com.nellinka.tools.MergeLists;
import com.nellinka.utilities.GuestExtrasObject;
import com.nellinka.utilities.OtherReservationsObject;
import com.nellinka.utilities.ReservationObject;
import com.nellinka.utilities.RoomObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ComradeBaz
 */
@Stateless
public class ManageHostel implements ManageHostelLocal {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private CachedDataLocal cachedData;

    private int totalBeds;
    private int totalOccupancy;

    @Override
    public int getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(int totalBeds) {
        this.totalBeds = totalBeds;
    }

    @Override
    public int getTotalOccupancy() {
        return totalOccupancy;
    }

    public void setTotalOccupancy(int totalOccupancy) {
        this.totalOccupancy = totalOccupancy;
    }

    //Method to return guests from table where status <> checkedIn/movedBed
    // Results displayed on checkinguest.xhtml
    @Override
    public List<CheckedInGuests> getSavedGuestsNotCheckedIn() {

        int status = GuestStatus.getSavedStatus();

        List<CheckedInGuests> newList = entityManager.createNamedQuery("getSavedGuestsNotCheckedIn")
                .setParameter("param1", status)
                .getResultList();

        return newList;
    }

    @Override
    // This method will returns guests who have been checked in
    public List<CheckedInGuests> getSavedGuestsCheckedIn() {

        List<CheckedInGuests> newList = entityManager.createNamedQuery("getSavedGuestsCheckedIn")
                .setParameter("param1", GuestStatus.getCheckedInStatus()).getResultList();

        return newList;
    }

    @Override
    // This method will returns guests who have been checked in
    public List<CheckedInGuests> getSavedGuestsCheckedInToday() {

        List<CheckedInGuests> newList = entityManager.createNamedQuery("getSavedGuestsCheckedInToday")
                .setParameter("param1", new Date())
                .setParameter("param2", new Date())
                .setParameter("param3", GuestStatus.getCheckedInStatus())
                .setParameter("param4", GuestStatus.getMovedBedStatus())
                .getResultList();

        return newList;
    }

    // Find a checkedInGuest by guestId
    @Override
    public CheckedInGuests getCheckedInGuestByGuestId(int guestId) {

        List<CheckedInGuests> gList = entityManager.createNamedQuery("getGuestByGuestId")
                .setParameter("param1", guestId).getResultList();
        CheckedInGuests cInGuest = gList.get(0);

        return cInGuest;
    }

    // Find a checkedInGuest by guestId
    @Override
    public List<CheckedInGuests> getCheckedInGuestByReservationId(int reservationId) {

        // Guests checking out on the first of the month are temporarily
        // saved with a minus value reservationId - get the correct Id
        if (reservationId < 0) {
            reservationId = -(reservationId);
        }

        return entityManager.createNamedQuery("getGuestByReservationId")
                .setParameter("param1", reservationId).getResultList();
    }

    @Override
    public List<String> getAllCountries() {

        return entityManager.createNamedQuery("getAllCountries")
                .getResultList();
    }

    @Override
    public CheckedInGuests getOneCheckedInGuestByReservationId(int reservationId) {

        Logger.safePrint("Getting guest from dB, reservationId = " + reservationId);

        List<CheckedInGuests> newList = entityManager.createNamedQuery("getGuestByReservationId")
                .setParameter("param1", reservationId)
                .getResultList();

        return newList.get(0);
    }

    // Find a checkedInGuest by guestId
    @Override
    public CheckedInGuests getCheckedInGuestByReservationAndGuestId(int reservationId, int guestId) {

        Logger.safePrint("Getting guest from dB, reservationId = " + reservationId + ", guestId = " + guestId);

        List<CheckedInGuests> gList = entityManager.createNamedQuery("getGuestByReservationAndGuestId")
                .setParameter("param1", reservationId)
                .setParameter("param2", guestId)
                .getResultList();
        CheckedInGuests cInGuest = gList.get(0);

        return cInGuest;
    }

    @Override
    public int getReservationIdByRoomAndBedNumberAndDate(String roomName, int bedNumber, int dayAsInt, String theTable) {

        List<RoomGridInterface> results = new ArrayList<>();

        if (theTable.equalsIgnoreCase("room_grid_two")) {
            results = entityManager.createNamedQuery("getABedWithOccupants_roomGridTwo")
                    .setParameter("param1", roomName)
                    .setParameter("param2", bedNumber)
                    .getResultList();
        } else if (theTable.equalsIgnoreCase("room_grid")) {
            results = entityManager.createNamedQuery("getABedWithOccupants")
                    .setParameter("param1", roomName)
                    .setParameter("param2", bedNumber)
                    .getResultList();
        }

        List<Integer> occupants = results.get(0).getBedOccupantsList();

        Logger.safePrint("Returning reservationId by Room, BedNumber and Date: " + occupants.get(dayAsInt));

        return occupants.get(dayAsInt);
    }

    @Override
    public List<CheckedInGuests> getSavedGuestsMovedBed() {

        return entityManager.createNamedQuery("getSavedGuestsMovedBed")
                .setParameter("param1", GuestStatus.getMovedBedStatus())
                .getResultList();
    }
    // Get data for movedBed reservations
    // Put them in a list to be displayed on checkoutguest.showguest.xhtml

    @Override
    public CheckedInGuests getCheckedInGuestByGuestIdRoomNameBedNumber(int guestId, String roomName, int bedNumber) {

        Logger.safePrint("Getting checkedInGuest by RoomName and BedNumber. RoomName is " + roomName + " and BedNumber is " + bedNumber);

        List<CheckedInGuests> newList = entityManager.createNamedQuery("getCheckedInGuestByGuestIdRoomNameBedNumber")
                .setParameter("param1", guestId)
                .setParameter("param2", roomName)
                .setParameter("param3", bedNumber)
                .getResultList();

        return newList.get(0);
    }

    @Override
    public int getNumberOfBedsByRoomName(String roomName) {
        List<Integer> tempList = entityManager.createNamedQuery("getNumberOfBedsByRoomName")
                .setParameter("param1", roomName)
                .getResultList();

        return tempList.get(0);
    }

    // Get the available extras from the database so they can be displayed
    // as a choice when the user is checking in
    // Create an object with "count" that can be updated by Ajax requests
    // when the user clicks the +- buttons on the add extras table checkinguest2.xhtml
    @Override
    public List<GuestExtrasObject> getExtrasToDisplay() {

        GuestExtrasObject tempObject;

        List<GuestExtrasObject> retValue = new ArrayList<>();

        List<Extras> tempValue = entityManager.createNamedQuery("getExtras").getResultList();

        for (Extras ex : tempValue) {
            tempObject = new GuestExtrasObject(0, ex.getItemName(), ex.getItemAmount());
            retValue.add(tempObject);
        }

        return retValue;
    }

    // Method return deposit items to be shown at checkIn
    @Override
    public List<GuestExtrasObject> getCheckInExtrasToDisplay() {

        GuestExtrasObject tempObject;

        List<GuestExtrasObject> retValue = new ArrayList<>();

        List<Extras> tempValue = entityManager.createNamedQuery("getCheckInExtras").getResultList();

        for (Extras ex : tempValue) {
            tempObject = new GuestExtrasObject(0, ex.getItemName(), ex.getItemAmount());
            retValue.add(tempObject);
        }

        return retValue;
    }

    // Method return deposit items to be shown at checkIn
    @Override
    public List<GuestExtrasObject> getCheckInExtrasToDisplayByCurrency(String currency) {

        GuestExtrasObject tempObject;

        List<GuestExtrasObject> retValue = new ArrayList<>();

        List<Extras> tempValue = entityManager.createNamedQuery("getCheckInExtras").getResultList();

        for (Extras ex : tempValue) {
            tempObject = new GuestExtrasObject(0, ex.getItemName(), (ex.getItemAmount() * cachedData.getConversionRate(currency)));
            retValue.add(tempObject);
        }

        return retValue;
    }

    // Get the set of extras available so they can be displayed to the user
    @Override
    public List<Extras> getAllExtrasFromDb() {

        return entityManager.createNamedQuery("getExtras").getResultList();
    }

    @Override
    public List<Extras> getAllCheckInExtrasFromDb() {

        return entityManager.createNamedQuery("getCheckInExtras").getResultList();
    }

    @Override
    public List<CheckedInGuests> searchCheckedInGuests(String searchString) {
        Logger.safePrint("Search String: " + searchString);
        return entityManager.createQuery(searchString)
                .getResultList();
    }

    // Method to add Extras to dB so they are available to add to guest reservation
    @Override
    public void saveExtraItem(String extraName, float extraAmount, int isDepositValue) {

        Logger.safePrint("Saving an new extra - " + extraName + ". The amount is " + extraAmount + " and it is a deposit: " + isDepositValue);
        Extras extraItem = new Extras(extraName, extraAmount, isDepositValue);
        entityManager.persist(extraItem);
    }

    @Override
    public List<Rooms> getOpenRooms() {
        return entityManager.createNamedQuery("getOpenRooms")
                .getResultList();
    }

    // Add a new room to the hostel
    @Override
    public void saveRoom(String roomName, int numberOfBeds, float rate) {

        Logger.safePrint("Adding a new room, " + roomName + ", with " + numberOfBeds + " beds");
        // Remove whiteapace/non-visible characters so name is valid
        roomName = roomName.replaceAll("\\s+", "");

        boolean addNewRoomToDataBase = true;
        // Check if the rooms exists in the dB (it was a previously a room but deleted)
        List<Rooms> newList = entityManager.createNamedQuery("getRooms")
                .getResultList();
        for (Rooms r : newList) {
            if (r.getRoomName().equalsIgnoreCase(roomName)) {
                r.setIsOpen(1);
                r.setNumberOfBeds(numberOfBeds);
                r.setRate(rate);
                addNewRoomToDataBase = false;
            }
        }

        if (addNewRoomToDataBase) {
            // Add the newRoom to the rooms table
            Rooms newRoom = new Rooms(roomName, numberOfBeds, rate);
            entityManager.persist(newRoom);
            entityManager.flush();
        }
        // Get the correct table name (tables alternate, on the first month RoomGrid is this_month
        // Next month it changes
        String currentMonth = "";
        String nxtMonth = "";
        //userTransaction.begin();
        GlobalVariables thsMonth = entityManager.find(GlobalVariables.class, "this_month");
        GlobalVariables neMonth = entityManager.find(GlobalVariables.class, "next_month");

        currentMonth = thsMonth.getItemValue();
        nxtMonth = neMonth.getItemValue();

        // Add data to the RoomGrid table so it can be displayed on the room grid views
        int bedNumber = -1;
        for (int j = 1; j <= numberOfBeds; j++) {
            bedNumber = j;
            // Guest List for the room
            // Array of 31/32 (for months with 30/31 days) convert to List and persist()
            // The first entry in the list is the bed number
            // Array size is calculated based on this month and next month
            // The room is added to two tables, one representing this month and one for next month
            int[] tempArrayThisMonth = new int[DateUtility.getNoOfDaysInCurrentMonth() + 1];
            int daysInNextMonth = DateUtility.getNoOfDaysNextMonth(DateUtility.getNextMonth());
            int[] tempArrayNextMonth = new int[daysInNextMonth + 1];

            // Populate occupants list with 0's, 0 means empty bed
            // The reservationId is added once a guest checks into a room/bed
            for (int indx = 0; indx < tempArrayThisMonth.length; indx++) {
                // Add the bed number in the first position of the array
                // The bed number on the gridView table is take from this value
                if (indx == 0) {
                    tempArrayThisMonth[indx] = j;
                } else {
                    tempArrayThisMonth[indx] = 0;
                }
            }
            for (int indx = 0; indx < tempArrayNextMonth.length; indx++) {
                // Add the bed number in the first position of the array
                // The bed number on the gridView table is take from this value
                if (indx == 0) {
                    tempArrayNextMonth[indx] = j;
                } else {
                    tempArrayNextMonth[indx] = 0;
                }
            }
            // Initialise the object
            RoomGrid newRoomGrid = new RoomGrid();
            RoomGridTwo newRoomGridTwo = new RoomGridTwo();

            if (currentMonth.equalsIgnoreCase("room_grid")) {
                List<Integer> theOccupants = ConvertArrayToList.convertIntArrayToList(tempArrayThisMonth);
                newRoomGrid = new RoomGrid(roomName, bedNumber, theOccupants);

                theOccupants = ConvertArrayToList.convertIntArrayToList(tempArrayNextMonth);
                newRoomGridTwo = new RoomGridTwo(roomName, bedNumber, theOccupants);
            } else if (currentMonth.equalsIgnoreCase("room_grid_two")) {
                List<Integer> theOccupants = ConvertArrayToList.convertIntArrayToList(tempArrayThisMonth);
                newRoomGridTwo = new RoomGridTwo(roomName, bedNumber, theOccupants);

                theOccupants = ConvertArrayToList.convertIntArrayToList(tempArrayNextMonth);
                newRoomGrid = new RoomGrid(roomName, bedNumber, theOccupants);
            }
            entityManager.persist(newRoomGrid);
            entityManager.flush();
            entityManager.persist(newRoomGridTwo);
            entityManager.flush();
        }
    }

    @Override
    public void updateRoom(String roomName, int numberOfBeds, float rate) {

        Rooms theRoom = entityManager.find(Rooms.class, roomName);
        theRoom.setNumberOfBeds(numberOfBeds);
        theRoom.setRate(rate);

    }

    // Return a list of the rooms in the hostel
    @Override
    public List<Rooms> getHostelRooms() {

        return entityManager.createNamedQuery("getRoomNames").getResultList();

    }

    // Get all roomGrid rooms
    @Override
    public List<String> getAllRoomGridRooms() {

        return entityManager.createNamedQuery("getAllRoomGridRooms").getResultList();
    }

    @Override
    public List<Integer> getGridOccupants(String table, String roomName) {

        Query query = entityManager.createQuery(
                "Select r.bedOccupantsList from " + table + " r where r.roomName "
                + "=:param1");

        query.setParameter("param1", roomName);

        return query.getResultList();
    }

    @Override
    public List<RoomGrid> getRoomGrid() {

        return entityManager.createNamedQuery("getAllEntries")
                .getResultList();
    }

    @Override
    public List<RoomGridTwo> getRoomGridTwo() {
        return entityManager.createNamedQuery("getAllEntries_roomGridTwo")
                .getResultList();
    }

    // Get the entry from room_grid and add guest details
    // Add the guest's reservationId to the room/bed chosen by the user during checkIn
    @Override
    public List<Integer> updateRoomGrid(String rmName, int bdNr, Date ckInDate, Date ckOutDate, int reservationId, boolean isDeleteRequest) {

        List<RoomGrid> tempList;
        // Initialise result
        List<Integer> theResults = new ArrayList<>();
        // bedNumber in the gridDisplay array is bedNumber +1 
        // The first entry in this list is the bedNumber to display on the gridView table
        //int guestBedNr = bdNr + 1;
        // Get the reservation type and update table(s) depending on the result
        CheckInTypes checkInType = CheckInType.getCheckInType(ckInDate, ckOutDate);
        Logger.safePrint("Updating the grid table. CheckInType: " + checkInType.toString());

        switch (checkInType) {
            case CHECK_IN_OUT_THIS_MONTH:
                updateTheGridTables(cachedData.getTableForThisMonth(), rmName, bdNr, reservationId, ckInDate, ckOutDate, false, isDeleteRequest);
                break;
            case CHECK_IN_THIS_MONTH_OUT_FIRST_OF_NEXT_MONTH:
                updateTheGridTables(cachedData.getTableForThisMonth(), rmName, bdNr, reservationId, ckInDate, DateUtility.getLastDayOfThisMonth(ckInDate), true, isDeleteRequest);
                break;
            case CHECK_IN_THIS_MONTH_OUT_NEXT_MONTH:
                // Get the dates for this month and next month
                // Add the data to the appropriate table
                List<ReservationObject> results = getReservationObjectList(ckInDate, ckOutDate);
                for (ReservationObject rO : results) {
                    updateTheGridTables(rO.getWhatTable(), rmName, bdNr, reservationId, rO.getStartDate(), rO.getEndDate(), rO.isGoToEndDate(), isDeleteRequest);
                }
                break;
            case CHECK_IN_THIS_MONTH_OUT_MONTH_AFTER_NEXT:
                // Get the dates for this month and next month
                // Add the data to the appropriate table
                List<ReservationObject> myResults = getReservationObjectList(ckInDate, DateUtility.getLastDayOfNextMonth(ckOutDate));
                for (ReservationObject rO : myResults) {
                    updateTheGridTables(rO.getWhatTable(), rmName, bdNr, reservationId, rO.getStartDate(), rO.getEndDate(), rO.isGoToEndDate(), isDeleteRequest);
                }
                break;
            case CHECK_IN_OUT_NEXT_MONTH:
                updateTheGridTables(cachedData.getTableForNextMonth(), rmName, bdNr, reservationId, ckInDate, ckOutDate, false, isDeleteRequest);
                break;
            case CHECK_IN_NEXT_MONTH_OUT_THE_MONTH_AFTER:
                updateTheGridTables(cachedData.getTableForNextMonth(), rmName, bdNr, reservationId, ckInDate, ckOutDate, true, isDeleteRequest);
                break;
            case CHECK_IN_AFTER_NEXT_MONTH:
            // Do nothing
        }

        return theResults;
    }

    // Update the correct roomGrid table depending on the type of com.nellinka.staticdata.CheckInTypes
    @Override
    public void updateTheGridTables(String theTable, String rmName, int bdNr, int reservationId, Date ckInDate, Date ckOutDate, boolean isGoToEndDate, boolean isDeleteRequest) {

        Logger.safePrint("About to update the " + theTable + " table");
        List<RoomGrid> roomGridResults = new ArrayList<>();
        List<RoomGridTwo> roomGridTwoResults = new ArrayList<>();
        List<Integer> theResults = new ArrayList<>();
        int start = 0, finish = 0;

        RoomGrid currentRoomGrid = new RoomGrid();
        RoomGridTwo currentRoomGridTwo = new RoomGridTwo();

        try {
            // Get the occupants associated with each room and bed number of the table
            if (theTable.equalsIgnoreCase("room_grid")) {
                Query query = entityManager.createNamedQuery("getRoomGridEntryByRoomNameAndBedNumber");
                query.setParameter("param1", rmName);
                query.setParameter("param2", bdNr);
                roomGridResults = query.getResultList();
                currentRoomGrid = roomGridResults.get(0);
                theResults = currentRoomGrid.getBedOccupantsList();

            } else if (theTable.equalsIgnoreCase("room_grid_two")) {
                Query query = entityManager.createNamedQuery("getRoomGridTwoEntryByRoomNameAndBedNumber");
                query.setParameter("param1", rmName);
                query.setParameter("param2", bdNr);
                roomGridTwoResults = query.getResultList();
                currentRoomGridTwo = roomGridTwoResults.get(0);
                theResults = currentRoomGridTwo.getBedOccupantsList();
            }
            start = DateUtility.getIntStartDate(ckInDate);
            finish = DateUtility.getIntEndDate(ckOutDate);

            // Boolean is set only for the first month of a
            // CHECK_IN_THIS_MONTH_OUT_NEXT_MONTH reservation type
            if (!isGoToEndDate) {
                finish = finish - 1;
            }

            // The list of days for the bedNumber assigned to the new guest
            // Cycle through the list and add the reservationId for the dates on 
            // which the guest is staying
            int index;

            for (index = 0; index < theResults.size(); index++) {
                // index = 1 for dayNumber 1
                if ((index >= start) && (index <= finish)) {
                    // Remove the entry at the current index
                    theResults.remove(index);
                    // Add the new value
                    if (!isDeleteRequest) {
                        theResults.add(index, reservationId);
                    } // If it is a deleteRequest add the 0 at index that was removed
                    // in the line theResults.remove(index);
                    else if (isDeleteRequest) {
                        theResults.add(index, 0);
                    }
                }
            }
            // If the guest is checking out early update the table and free the bed
            // Get the next day, check if the guest's reservation is set
            // If it is, remove it
            for (int indexTwo = finish + 1; indexTwo < theResults.size(); indexTwo++) {
                if (theResults.get(indexTwo) == reservationId) {
                    theResults.remove(indexTwo);
                    theResults.add(indexTwo, 0);
                    continue;
                }
                break;
            }
            if (theTable.equalsIgnoreCase("room_grid")) {
                currentRoomGrid.setBedOccupantsList(theResults);
            } else if (theTable.equalsIgnoreCase("room_grid_two")) {
                currentRoomGridTwo.setBedOccupantsList(theResults);
            }

        } catch (IllegalStateException
                | SecurityException e) {

            Logger.safePrint("CheckInFlow.updateTheGridTables() error " + e.getMessage());
        }
    }

    // If the guest is checking in one month and out the next this function
    // manipulates date objects so the correct data can be persisted to the correct table
    // The tables store reservation data - dates, rooms, beds and are queried when
    // checking in a guest to check availability
    // They also determine the information displayed on the room grids (gridview-flow)
    @Override
    public List<ReservationObject> getReservationObjectList(Date checkInDate, Date checkOutDate) {

        List<ReservationObject> returnValues = new ArrayList<>();
        Date thisMonthCheckInDate = checkInDate;
        Date thisMonthCheckOutDate = DateUtility.getLastDayOfThisMonth(checkInDate);
        ReservationObject thisMonthObject = new ReservationObject(thisMonthCheckInDate,
                thisMonthCheckOutDate, cachedData.getTableForThisMonth(), true, "thisMonth");

        returnValues.add(thisMonthObject);

        Date nextMonthCheckInDate = DateUtility.getFirstDayOfNextMonth(checkOutDate);
        Date nextMonthCheckOutDate = checkOutDate;
        if (nextMonthCheckOutDate == DateUtility.getLastDayOfNextMonth(checkOutDate)) {
            ReservationObject nextMonthObject = new ReservationObject(nextMonthCheckInDate,
                    nextMonthCheckOutDate, cachedData.getTableForNextMonth(), true, "nextMonth");
            returnValues.add(nextMonthObject);
        } else {
            ReservationObject nextMonthObject = new ReservationObject(nextMonthCheckInDate,
                    nextMonthCheckOutDate, cachedData.getTableForNextMonth(), false, "nextMonth");
            returnValues.add(nextMonthObject);
        }

        //returnValues.add(nextMonthObject);
        return returnValues;
    }

    // Check the availability based on the roomName and dates - return any beds 
    // available for the roomName for the duration of the ENTIRE stay
    // If the stay spans two months it is broken in two. The first month and second months
    // days are stored in rooms_grid and rooms_grid_two, one for "this" month and 
    // one for "next" month
    // The table for the month (this/next) alternates for each month, it is switched on the 
    // first of every month
    // The boolean is "TRUE" only if the method has been called from the edit button on checkoutguest.xhtml
    @Override
    public List<Integer> addFreeBedsToList(String aRoom, Date cInDate, Date cOutDate, CheckedInGuests guestToEdit, boolean isEditGuestType) {

        Logger.safePrint("Getting available beds");

        CheckInTypes checkInType;

        if (isEditGuestType) {
            checkInType = CheckInTypes.EDIT_GUEST;
        } else {
            checkInType = CheckInType.getCheckInType(cInDate, cOutDate);
        }
        Logger.safePrint("Getting free beds - checkInType: " + checkInType.toString());
        List<Integer> thisMonth = new ArrayList<>();
        List<Integer> nextMonth = new ArrayList<>();

        List<RoomGridInterface> newList = new ArrayList<>();
        List<RoomGridInterface> aList = new ArrayList<>();
        // Get the List of RoomGrid and RoomGridTwo entries
        try {
            newList = entityManager.createNamedQuery("getAllEntries").getResultList();
            aList = entityManager.createNamedQuery("getAllEntries_roomGridTwo").getResultList();
        } catch (IllegalStateException
                | SecurityException e) {

        }

        switch (checkInType) {
            case CHECK_IN_OUT_THIS_MONTH:
                if (cachedData.getTableForThisMonth().equalsIgnoreCase("room_grid")) {
                    thisMonth = searchRoomGridInterface(cInDate, cOutDate, newList, aRoom, false, guestToEdit);
                } else if (cachedData.getTableForThisMonth().equalsIgnoreCase("room_grid_two")) {
                    thisMonth = searchRoomGridInterface(cInDate, cOutDate, aList, aRoom, false, guestToEdit);
                }
                return thisMonth;
            case CHECK_IN_THIS_MONTH_OUT_FIRST_OF_NEXT_MONTH:
                // Change the checkout date to the last date of this month
                // And search including the checkout date (searchToMonthEnd = true)
                if (cachedData.getTableForThisMonth().equalsIgnoreCase("room_grid")) {
                    thisMonth = searchRoomGridInterface(cInDate, DateUtility.getLastDayOfThisMonth(cInDate), newList, aRoom, true, guestToEdit);
                } else if (cachedData.getTableForThisMonth().equalsIgnoreCase("room_grid_two")) {
                    thisMonth = searchRoomGridInterface(cInDate, DateUtility.getLastDayOfThisMonth(cInDate), aList, aRoom, true, guestToEdit);
                }
                return thisMonth;
            case CHECK_IN_THIS_MONTH_OUT_NEXT_MONTH:
                List<ReservationObject> results = getReservationObjectList(cInDate, cOutDate);
                for (ReservationObject rO : results) {
                    if (rO.getWhatTable().equals("room_grid") && (rO.getWhatMonth().equalsIgnoreCase("thisMonth"))) {
                        thisMonth = searchRoomGridInterface(rO.getStartDate(), rO.getEndDate(), newList, aRoom, rO.isGoToEndDate(), guestToEdit);
                    } else if (rO.getWhatTable().equals("room_grid") && (rO.getWhatMonth().equalsIgnoreCase("nextMonth"))) {
                        nextMonth = searchRoomGridInterface(rO.getStartDate(), rO.getEndDate(), newList, aRoom, rO.isGoToEndDate(), guestToEdit);
                    } else if ((rO.getWhatTable().equalsIgnoreCase("room_grid_two")) && (rO.getWhatMonth().equalsIgnoreCase("thisMonth"))) {
                        thisMonth = searchRoomGridInterface(rO.getStartDate(), rO.getEndDate(), aList, aRoom, rO.isGoToEndDate(), guestToEdit);
                    } else if ((rO.getWhatTable().equalsIgnoreCase("room_grid_two")) && (rO.getWhatMonth().equalsIgnoreCase("nextMonth"))) {
                        nextMonth = searchRoomGridInterface(rO.getStartDate(), rO.getEndDate(), aList, aRoom, rO.isGoToEndDate(), guestToEdit);
                    }
                }
                List<Integer> listToReturn = MergeLists.mergeLists(thisMonth, nextMonth);
                return listToReturn;
            case CHECK_IN_THIS_MONTH_OUT_MONTH_AFTER_NEXT:
                List<ReservationObject> theResults = getReservationObjectList(cInDate, DateUtility.getLastDayOfNextMonth(cOutDate));
                for (ReservationObject rO : theResults) {
                    if (rO.getWhatTable().equals("room_grid") && (rO.getWhatMonth().equalsIgnoreCase("thisMonth"))) {
                        thisMonth = searchRoomGridInterface(rO.getStartDate(), rO.getEndDate(), newList, aRoom, rO.isGoToEndDate(), guestToEdit);
                    } else if (rO.getWhatTable().equals("room_grid") && (rO.getWhatMonth().equalsIgnoreCase("nextMonth"))) {
                        nextMonth = searchRoomGridInterface(rO.getStartDate(), rO.getEndDate(), newList, aRoom, rO.isGoToEndDate(), guestToEdit);
                    } else if ((rO.getWhatTable().equalsIgnoreCase("room_grid_two")) && (rO.getWhatMonth().equalsIgnoreCase("thisMonth"))) {
                        thisMonth = searchRoomGridInterface(rO.getStartDate(), rO.getEndDate(), aList, aRoom, rO.isGoToEndDate(), guestToEdit);
                    } else if ((rO.getWhatTable().equalsIgnoreCase("room_grid_two")) && (rO.getWhatMonth().equalsIgnoreCase("nextMonth"))) {
                        nextMonth = searchRoomGridInterface(rO.getStartDate(), rO.getEndDate(), aList, aRoom, rO.isGoToEndDate(), guestToEdit);
                    }
                }
                List<Integer> theListToReturn = MergeLists.mergeLists(thisMonth, nextMonth);
                return theListToReturn;
            case CHECK_IN_OUT_NEXT_MONTH:
                if (cachedData.getTableForNextMonth().equalsIgnoreCase("room_grid")) {
                    nextMonth = searchRoomGridInterface(cInDate, cOutDate, newList, aRoom, false, guestToEdit);
                } else if (cachedData.getTableForNextMonth().equalsIgnoreCase("room_grid_two")) {
                    nextMonth = searchRoomGridInterface(cInDate, cOutDate, aList, aRoom, false, guestToEdit);
                }
                return nextMonth;
            case CHECK_IN_NEXT_MONTH_OUT_THE_MONTH_AFTER:
                // It's not possible to assign a guest to a bed past next month
                // This returns the available beds until the end of next month
                if (cachedData.getTableForNextMonth().equalsIgnoreCase("room_grid")) {
                    nextMonth = searchRoomGridInterface(cInDate, cOutDate, newList, aRoom, true, guestToEdit);
                } else if (cachedData.getTableForNextMonth().equalsIgnoreCase("room_grid_two")) {
                    nextMonth = searchRoomGridInterface(cInDate, cOutDate, aList, aRoom, true, guestToEdit);
                }
                return nextMonth;
            case CHECK_IN_AFTER_NEXT_MONTH:
                Logger.safePrint("Do nothing");
            case EDIT_GUEST:
                // The only time this case is reached is if a user clicks the editGuest button on checkedinguests.xhtml 
                // editGuest button is a pen icon in the firstName cell of the table shown on the page
                // Set the list as per CHECK_IN_OUT_THIS_MONTH but include the guest's current bedNumber 
                // in the list returned if the guest is staying in the same room
                if (cachedData.getTableForThisMonth().equalsIgnoreCase("room_grid")) {
                    thisMonth = searchRoomGridInterface(cInDate, cOutDate, newList, aRoom, false, guestToEdit);
                } else if (cachedData.getTableForThisMonth().equalsIgnoreCase("room_grid_two")) {
                    thisMonth = searchRoomGridInterface(cInDate, cOutDate, aList, aRoom, false, guestToEdit);
                }
                return thisMonth;
        }

        return new ArrayList<>();
    }

    // Method called if the guest stay spans this month and goes into next month
    // Break the reservation into two parts, for this month and next month
    // Same function but searches the other grid view table
    // RoomGrid/RoomGridTwo can represent either this month or next month, it's alternates
    @Override
    public List<Integer> searchRoomGridInterface(Date cInDate, Date cOutDate, List<RoomGridInterface> newList,
            String aRoom, boolean searchToMonthEnd, CheckedInGuests guestToEdit) {

        Logger.safePrint("Searching the roomGrid for available beds");

        int startInt = DateUtility.getIntStartDate(cInDate);
        int lengthOfStay = DateUtility.getLengthOfStay(cInDate, cOutDate);
        List<Integer> retValues = new ArrayList<>();
        int currentBedNumber;

        // Get the bed numbers up to the end of this month
        for (RoomGridInterface rg : newList) {
            if (rg.getRoomName().equalsIgnoreCase(aRoom)) {
                // If .get(startInt) == 0 the bed is free on the day of the month
                // represented by startInt. If so, search from then for lenghtOfStay days
                // to see if it's available for the duration of the guest's stay
                // If it's a negative value it is the first of the month and guests supposed
                // to be checking out have been temporarily added to the grid with minus values
                // to allow a user to extend them on the grid if necessary
                // negative values are included in the response
                if (((rg.getBedOccupantsList().get(startInt)) == 0)
                        || ((rg.getBedOccupantsList().get(startInt))) == guestToEdit.getReservationId()
                        || ((rg.getBedOccupantsList().get(startInt))) < 0) {
                    currentBedNumber = rg.getBedNumber();
                    // If the bed is empty today
                    if (!searchToMonthEnd) {
                        for (int i = startInt; i <= startInt + lengthOfStay; i++) {
                            if ((rg.getBedOccupantsList().get(i)) == 0
                                    || ((rg.getBedOccupantsList().get(i))) == guestToEdit.getReservationId()
                                    || ((rg.getBedOccupantsList().get(startInt))) < 0) {
                                // If the bed is empty for the duration of the guest's stay
                                // cOutDate - 1 because the guest doesn't stay the night they check out
                                // -1 logic doesn't apply if the guest is staying until next month
                                // cOutDate will become 30/31 depending on how many days this month
                                if (i == DateUtility.getIntEndDate(cOutDate) - 1) {
                                    // Add the bed number to the available beds for the current roomName
                                    retValues.add(rg.getBedNumber());
                                    break;
                                }
                            } else if ((rg.getBedOccupantsList().get(i)) != 0) {
                                break;
                            }
                        }
                    } else if (searchToMonthEnd) {
                        for (int i = startInt; i <= startInt + lengthOfStay; i++) {
                            if ((rg.getBedOccupantsList().get(i)) == 0
                                    || ((rg.getBedOccupantsList().get(i))) == guestToEdit.getReservationId()) {
                                if (i == DateUtility.getIntEndDate(cOutDate)) {
                                    // Add the bed number to the available beds for the current roomName
                                    retValues.add(rg.getBedNumber());
                                    break;
                                }
                            } else if ((rg.getBedOccupantsList().get(i)) != 0) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return retValues;
    }

    @Override
    public void emptyRooms() {

        Logger.safePrint("Emptying the rooms, this will delete all guests");

        List<RoomGridInterface> roomGrid = entityManager.createNamedQuery("getAllEntries").getResultList();
        List<RoomGridInterface> roomGridTwo = entityManager.createNamedQuery("getAllEntries_roomGridTwo").getResultList();

        List<Integer> occupantsRoomGrid = new ArrayList<>();
        List<Integer> occupantsRoomGridTwo = new ArrayList<>();

        for (RoomGridInterface bed : roomGrid) {
            occupantsRoomGrid = bed.getBedOccupantsList();
            for (int i = 1; i < occupantsRoomGrid.size(); i++) {
                occupantsRoomGrid.remove(i);
                occupantsRoomGrid.add(i, 0);
            }
        }
        for (RoomGridInterface bed : roomGridTwo) {
            occupantsRoomGridTwo = bed.getBedOccupantsList();
            for (int i = 1; i < occupantsRoomGridTwo.size(); i++) {
                occupantsRoomGridTwo.remove(i);
                occupantsRoomGridTwo.add(i, 0);
            }
        }
    }

    // Get all the free beds for today
    @Override
    public List<FreeBed> getBedsEmptyToday() {

        List<RoomGridInterface> roomGrid = entityManager.createNamedQuery("getAllEntries").getResultList();
        List<RoomGridInterface> roomGridTwo = entityManager.createNamedQuery("getAllEntries_roomGridTwo").getResultList();

        int todayInt = DateUtility.getIntDate(new Date());
        List<FreeBed> returnValues = new ArrayList<>();

        String thisMonthTable = cachedData.getTableForThisMonth();

        if (thisMonthTable.equalsIgnoreCase("room_grid")) {
            for (RoomGridInterface rGI : roomGrid) {
                if ((rGI.getBedOccupantsList().get(todayInt)) == 0) {
                    FreeBed freeBed = new FreeBed(rGI.getRoomName(), rGI.getBedNumber(), todayInt);
                    returnValues.add(freeBed);
                }
            }

        } else if (thisMonthTable.equalsIgnoreCase("room_grid_two")) {
            for (RoomGridInterface rGI : roomGridTwo) {
                if ((rGI.getBedOccupantsList().get(todayInt)) == 0) {
                    FreeBed freeBed = new FreeBed(rGI.getRoomName(), rGI.getBedNumber(), todayInt);
                    returnValues.add(freeBed);
                }
            }
        }
        return returnValues;
    }

    @Override
    public boolean getIsBedEmptyByDay(String roomName, int bedNumber, Date checkInDate) {
        // Pass the checkOutDate Date object associated with the guest
        // Increment checkOutDate by 1 to get the correct Date
        // Determine what table should be searched
        // Then do the search
        List<RoomGridInterface> results = new ArrayList<>();
        String theTable = "";
        int dayAsInt = DateUtility.getIntDate(checkInDate);

        if (DateUtility.guestStayStartingThisMonth(checkInDate, checkInDate)) {
            theTable = cachedData.getTableForThisMonth();
            // get the relevant occupant list
            // check the day
            // if it's == 0 save the new information
            results = entityManager.createNamedQuery("getABedWithOccupants")
                    .setParameter("param1", roomName)
                    .setParameter("param2", bedNumber)
                    .getResultList();
        } else {
            theTable = cachedData.getTableForNextMonth();
            results = entityManager.createNamedQuery("getABedWithOccupants_roomGridTwo")
                    .setParameter("param1", roomName)
                    .setParameter("param2", bedNumber)
                    .getResultList();
        }

        List<Integer> occupants = results.get(0).getBedOccupantsList();
        if (occupants.get(dayAsInt) == 0) {
            Logger.safePrint("Checking if bed " + bedNumber + " in " + roomName + " is empty today " + checkInDate + ": " + Boolean.TRUE);

            return Boolean.TRUE;
        }
        Logger.safePrint("Checking if bed " + bedNumber + " in " + roomName + " is empty today " + checkInDate + ": " + Boolean.FALSE);

        return Boolean.FALSE;
    }

    @Override
    public List<CheckedInGuests> getGuestsCheckingOutToday() {
        Date today = new Date();

        return entityManager.createNamedQuery("getGuestsCheckingOutToday")
                .setParameter("param1", today)
                .setParameter("param2", GuestStatus.getCheckedInStatus())
                .getResultList();

    }

    // Update the list so the guest checkOutDate is updated on guestcheckingouttoday.xhtml
    @Override
    public List<CheckedInGuests> updateGuestsCheckingOutToday(List<CheckedInGuests> theList, CheckedInGuests theGuest, Date newCheckOutDate) {

        for (CheckedInGuests cIG : theList) {
            if (cIG.getReservationId() == theGuest.getReservationId()) {
                cIG.setCheckOutDate(newCheckOutDate);
            }
        }
        return theList;
    }

    // Check if a bed for a given guest who is checking out today is free
    @Override
    public List<FreeBed> getGuestBedAvailableToday(List<CheckedInGuests> guestsCheckingOutToday, List<FreeBed> freeBedsToday) {
        // If a guest is checking out today and if their bed is free today
        // get their reservationId, todays date, the room, the bedNumber and update the appropriate roomGridTable
        List<FreeBed> freeBedsToReturn = new ArrayList<>();
        for (CheckedInGuests cIG : guestsCheckingOutToday) {
            for (FreeBed freeBed : freeBedsToday) {
                if (cIG.getRoomName().equalsIgnoreCase(freeBed.getRoomName())) {
                    if (cIG.getBedNumber() == freeBed.getBedNumber()) {
                        // Make the reservationId negative so it can be identified by javaScript
                        freeBed.setReservationId(0 - cIG.getReservationId());
                        freeBedsToReturn.add(freeBed);
                    }
                }
            }
        }
        return freeBedsToReturn;
    }

    @Override
    public void initialiseTheGridView() {
        // Time is represented by two tables - room_grid and room_grid_two
        // On month 1, room_grid represents thisMonth, room_grid_two is nextMonth
        // On the 1st of next month room_grid_two becomes the table for thisMonth
        // room_grid is reset and it becomes the table for nnextMonth
        // If a guest is checking out on the first of the month the table cell on the roomGrid views
        // will be set to allow the user to extend their stay from the grid
        // The guest isn't available on the grid as a checkedInGuest - if it is the first of the month
        // then the grid data used to display last month is no longer available
        List<FreeBed> freeBeds = getGuestBedAvailableToday(getGuestsCheckingOutToday(), getBedsEmptyToday());

        Date today = new Date();
        if ((DateUtility.getIntDate(today)) == -1) {
            for (FreeBed theBed : freeBeds) {
                Date checkInDate = new Date();
                Date checkOutDate = DateUtility.getTomorrowsDate();
                updateRoomGrid(theBed.getRoomName(), theBed.getBedNumber(), checkInDate, checkOutDate, theBed.getReservationId(), Boolean.FALSE);
            }
        }
    }

    // Check the db for number of occupied beds for today and tomorrow by room
    // Subtract that value from Rooms.getNumberOfBeds() to get availability
    @Override
    public List<RoomObject> getAvailability() {

        List<RoomObject> retVal = new ArrayList<>();
        Map<String, Long> availableToday = new LinkedHashMap<>();
        Map<String, Long> availableTomorrow = new LinkedHashMap<>();
        int totalBedsInHostel = 0;
        int totalOccupancyToday = 0;

        try {
            List<Rooms> roomNames = entityManager.createNamedQuery("getOpenRooms").getResultList();

            for (Rooms r : roomNames) {
                List<Long> someList = entityManager.createNamedQuery("getNumberOfGuestsByRoomToday")
                        .setParameter("param1", r.getRoomName())
                        .setParameter("param2", new Date())
                        .setParameter("param3", GuestStatus.getCheckedInStatus())
                        .setParameter("param4", GuestStatus.getMovedBedStatus())
                        .getResultList();
                long bedsTaken = someList.get(0);
                long bedsAvailableToday = r.getNumberOfBeds() - bedsTaken;
                availableToday.put(r.getRoomName(), bedsAvailableToday);
                totalBedsInHostel += r.getNumberOfBeds();
                totalOccupancyToday += bedsTaken;

                // Availability for tomorrow
                List<Long> someOtherList = entityManager.createNamedQuery("getNumberOfGuestsByRoomTomorrow")
                        .setParameter("param1", r.getRoomName())
                        .setParameter("param2", DateUtility.getTomorrowsDate())
                        .setParameter("param3", new Date())
                        .setParameter("param4", GuestStatus.getCheckedInStatus())
                        .setParameter("param5", GuestStatus.getMovedBedStatus()).getResultList();
                bedsTaken = someOtherList.get(0);
                long bedsAvailableTomorrow = r.getNumberOfBeds() - bedsTaken;
                availableTomorrow.put(r.getRoomName(), bedsAvailableTomorrow);

                retVal.add(new RoomObject(r.getRoomName(), bedsAvailableToday, bedsAvailableTomorrow));
            }
        } catch (Exception e) {
            Logger.safePrint(e.getMessage());
        }
        setTotalBeds(totalBedsInHostel);
        setTotalOccupancy(totalOccupancyToday);

        return retVal;
    }

    @Override
    public List<String> getHostelRoomNames() {

        return entityManager.createNamedQuery("getRoomNames").getResultList();
    }

    @Override
    public int deleteRoom(String roomName) {

        Logger.safePrint("Deleting room " + roomName);
        try {
            return entityManager.createNamedQuery("deleteRoomFromRoomGrid")
                    .setParameter("param1", roomName)
                    .executeUpdate()
                    + entityManager.createNamedQuery("deleteRoomFromRoomGridTwo")
                            .setParameter("param1", roomName)
                            .executeUpdate()
                    + entityManager.createNamedQuery("deleteOpenRoom")
                            .setParameter("param1", roomName)
                            .executeUpdate();
        } catch (javax.persistence.PersistenceException jPP) {
            // The exception will be thrown if there is a guest staying in the room
            // roomName is bound by a foreign key constraint
            // Foreign key in the checkedInGuests table
            Logger.safePrint(jPP.getMessage());
        }
        return -1;
    }
}
