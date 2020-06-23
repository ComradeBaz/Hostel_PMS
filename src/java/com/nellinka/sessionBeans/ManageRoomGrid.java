/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.sessionBeans;

import com.nellinka.customInterfaces.CachedDataLocal;
import com.nellinka.customInterfaces.ManageGuestLocal;
import com.nellinka.customInterfaces.ManageHostelLocal;
import com.nellinka.entities.CheckedInGuests;
import com.nellinka.entities.GlobalVariables;
import com.nellinka.entities.RoomGrid;
import com.nellinka.entities.RoomGridTwo;
import com.nellinka.staticdata.GuestStatus;
import com.nellinka.tools.DateUtility;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author ComradeBaz
 */
@Named
@SessionScoped
public class ManageRoomGrid implements Serializable {

    @EJB
    private ManageHostelLocal manageHostel;

    @EJB
    private ManageGuestLocal manageGuest;

    @EJB
    private CachedDataLocal cachedData;

    private String whatMonth;
    private String dateToMove;
    private String currentRoomName;
    private String dateForHeading;
    private List<Integer> daysOfMonth;
    private List<RoomGrid> roomGrid;
    private List<RoomGridTwo> roomGridTwo;
    private List<Integer> occupants;
    private List<CheckedInGuests> guestToDisplay;
    private List<CheckedInGuests> anUpdatedGuestToDisplay;
    private List<CheckedInGuests> guestsCheckingOutToday;

    public ManageRoomGrid() {
        // no arg constructor
    }

    @PostConstruct
    public void initialiseBean() {
        setGuestsCheckingOutToday(null);
        setGuestsCheckingOutToday(manageHostel.getGuestsCheckingOutToday());
    }

    public String getWhatMonth() {
        return whatMonth;
    }

    public void setWhatMonth(String whatMonth) {
        this.whatMonth = whatMonth;
    }

    public String getDateToMove() {
        return dateToMove;
    }

    public void setDateToMove(String dateToMove) {
        this.dateToMove = dateToMove;
    }

    public String getCurrentRoomName() {
        return currentRoomName;
    }

    public void setCurrentRoomName(String currentRoomName) {
        this.currentRoomName = currentRoomName;
    }

    public String getDateForHeading() {
        return dateForHeading;
    }

    public void setDateForHeading(String dateForHeading) {
        this.dateForHeading = dateForHeading;
    }

    public List<Integer> getDaysOfMonth() {
        return daysOfMonth;
    }

    public void setDaysOfMonth(List<Integer> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    public List<RoomGrid> getRoomGrid() {
        return roomGrid;
    }

    public void setRoomGrid(List<RoomGrid> roomGrid) {
        this.roomGrid = roomGrid;
    }

    public List<RoomGridTwo> getRoomGridTwo() {
        return roomGridTwo;
    }

    public void setRoomGridTwo(List<RoomGridTwo> roomGridTwo) {
        this.roomGridTwo = roomGridTwo;
    }

    public List<Integer> getOccupants() {
        return occupants;
    }

    public void setOccupants(List<Integer> occupants) {
        this.occupants = occupants;
    }

    public List<CheckedInGuests> getGuestToDisplay() {
        return guestToDisplay;
    }

    public void setGuestToDisplay(List<CheckedInGuests> guestToDisplay) {
        this.guestToDisplay = guestToDisplay;
    }

    public List<CheckedInGuests> getAnUpdatedGuestToDisplay() {
        return anUpdatedGuestToDisplay;
    }

    public void setAnUpdatedGuestToDisplay(List<CheckedInGuests> anUpdatedGuestToDisplay) {
        this.anUpdatedGuestToDisplay = anUpdatedGuestToDisplay;
    }

    public List<CheckedInGuests> getGuestsCheckingOutToday() {
        return guestsCheckingOutToday;
    }

    public void setGuestsCheckingOutToday(List<CheckedInGuests> guestsCheckingOutToday) {
        this.guestsCheckingOutToday = guestsCheckingOutToday;
    }

    // EventListener
    public void setTheRoom(ActionEvent theEvent) {

        String roomNameString = theEvent.getComponent().getId();
        String whatTable = "";
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();

        roomNameString = params.get("roomName");
        String whichMonth = params.get("forPeriod");
        // Set variable for the bean
        setWhatMonth(whichMonth);
        if (roomNameString == null) {
            roomNameString = params.get("gridForm:roomName");
        }
        setCurrentRoomName(roomNameString);

        if (whichMonth.equalsIgnoreCase("this_month")) {
            whatTable = cachedData.getTableForThisMonth();
            setDateForHeading(cachedData.getThisMonthDayAndMonthHeading());
            if (whatTable.equalsIgnoreCase("room_grid")) {
                setRoomGrid(manageHostel.getRoomGrid());
                setOccupants(manageHostel.getGridOccupants("RoomGrid", getCurrentRoomName()));
            } else if (whatTable.equalsIgnoreCase("room_grid_two")) {
                setRoomGridTwo(manageHostel.getRoomGridTwo());
                setOccupants(manageHostel.getGridOccupants("RoomGridTwo", getCurrentRoomName()));
            }
            setDaysOfMonth(DateUtility.getDaysOfMonthForTableHeading("this_month"));

        } else if (whichMonth.equalsIgnoreCase("next_month")) {
            whatTable = cachedData.getTableForNextMonth();
            setDateForHeading(cachedData.getNextMonthDayAndMonthHeading());
            if (whatTable.equalsIgnoreCase("room_grid_two")) {
                setRoomGridTwo(manageHostel.getRoomGridTwo());
                setOccupants(manageHostel.getGridOccupants("RoomGridTwo", getCurrentRoomName()));
            } else if (whatTable.equalsIgnoreCase("room_grid")) {
                setRoomGrid(manageHostel.getRoomGrid());
                setOccupants(manageHostel.getGridOccupants("RoomGrid", getCurrentRoomName()));
            }
            setDaysOfMonth(DateUtility.getDaysOfMonthForTableHeading("next_month"));
        }
    }

    // Clear the occupants from all rooms
    public void emptyRooms() {
        manageHostel.emptyRooms();
    }

    public String extendGuestByOneNight() {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        String reservationIdString = params.get("reservationId");
        CheckedInGuests aGuest = manageHostel.getOneCheckedInGuestByReservationId(Integer.parseInt(reservationIdString));

        String retValue = extendTheGuestByOneNight(aGuest);

        return retValue;
    }

    public String extendTheGuestByOneNight(CheckedInGuests aGuest) {

        if (aGuest.getStatus() == GuestStatus.getCheckedInStatus()) {
            boolean isGuestBedEmptyToday = manageHostel.getIsBedEmptyByDay(aGuest.getRoomName(),
                    aGuest.getBedNumber(), aGuest.getCheckOutDate());
            if (!(isGuestBedEmptyToday)) {
                return "/secured/zerrorguestbednotavailable.xhtml?faces-redirect=true";
            } else if (isGuestBedEmptyToday) {
                // Update the list 
                setGuestsCheckingOutToday(manageHostel.updateGuestsCheckingOutToday(manageHostel.getGuestsCheckingOutToday(), aGuest, DateUtility.getDatePlusOneDay(aGuest.getCheckOutDate())));
                // Save the edited guest data and update the grid tables
                manageGuest.saveEditGuestReservation(aGuest, aGuest.getRoomName(), aGuest.getBedNumber(), aGuest.getCheckInDate(),
                        DateUtility.getDatePlusOneDay(aGuest.getCheckOutDate()), aGuest.getRate());
                List<CheckedInGuests> newList = new ArrayList<>();
                newList.add(manageHostel.getCheckedInGuestByGuestId(aGuest.getGuestId()));
                setAnUpdatedGuestToDisplay(newList);
                setGuestToDisplay(newList);

                return "";
            }
        }
        return "/secured/zerrorguestcheckedout.xhtml?faces-redirect=true";
    }

    // ActionListener 
    public String setGuestToDisplay() {

        // Paramaters are submitted via Ajax request 
        // Cell value is hidden in the cell on the gridView table
        // It is the reservationId of the guest assigned to the bed
        // or negative reservationId if they are checking out today and it is 
        // the first day of the month
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();

        // Get the day associated with the editButton that was pressed
        // If the next click is a moveBed request dateToMove is referenced
        String source = params.get("javax.faces.source");
        //j_idt28:2:j_idt30:13:myEditButton
        String[] items = source.split(":");
        setDateToMove(items[3]);
        int tempBedNumber = Integer.parseInt(items[1]);
        // Add one - index for bedNumber starts at zero
        tempBedNumber += 1;
        int tempDateToMove = Integer.parseInt(items[3]);

        String tempReservationId = params.get("cellValue");
        int forQuery = Integer.parseInt(tempReservationId);

        // Get the guest associated with the reservationId 
        if (forQuery < 0) {
            forQuery = (-(forQuery));
        }
        //setReservationId(forQuery);
        setGuestToDisplay(manageHostel.getCheckedInGuestByReservationId(forQuery));

        if (forQuery >= 0) {
            //Date guestCheckOutDate = DateUtility.getDateObject(getWhatMonth(), getDateToMove());
            GlobalVariables theTable = cachedData.getGlobalVariableEntryByName(getWhatMonth());
            setGuestToDisplay(manageHostel.getCheckedInGuestByReservationId(manageHostel.getReservationIdByRoomAndBedNumberAndDate(getCurrentRoomName(),
                    tempBedNumber, tempDateToMove, theTable.getItemValue())));
            getGuestToDisplay().get(0).setBedNumber(tempBedNumber);
        }

        return "";
    }
    // ActionListener 

    public String moveBedInSameRoom(ActionEvent ae) {

        String button = ae.getComponent().getId();

        String dateToMoveString = getDateToMove();
        CheckedInGuests theGuest = getGuestToDisplay().get(0);
        // Get all free beds in the guests rooms
        List<Integer> results = manageHostel.addFreeBedsToList(theGuest.getRoomName(),
                DateUtility.getDateObject(getWhatMonth(), dateToMoveString), theGuest.getCheckOutDate(), theGuest, Boolean.FALSE);
        // Get all possible choices in the direction of the move (up or down)
        List<Integer> possibleBedNumbers = new ArrayList<>();
        if (button.equals("upOneBedNumber")) {
            possibleBedNumbers = manageGuest.getPossibleNewBedNumbersMore(theGuest.getBedNumber(), theGuest.getRoomName());
        } else if (button.equals("downOneBedNumber")) {
            possibleBedNumbers = manageGuest.getPossibleNewBedNumbersLess(theGuest.getBedNumber(), theGuest.getRoomName());
        }
        // Compare the freeBeds with the possible options and pick the closest free bed
        int theNewBedNumber = manageGuest.getClosestAvailableBedNumber(results, possibleBedNumbers);
        // If a new bed number was found delete the old roomGrid entry and save the new one
        if (theNewBedNumber > 0) {
            manageHostel.updateRoomGrid(theGuest.getRoomName(), theGuest.getBedNumber(), DateUtility.getDateObject(getWhatMonth(), dateToMoveString),
                    theGuest.getCheckOutDate(), theGuest.getReservationId(), Boolean.TRUE);
            manageGuest.saveEditGuestReservation(theGuest, theGuest.getRoomName(), theNewBedNumber, DateUtility.getDateObject(getWhatMonth(), dateToMoveString),
                    theGuest.getCheckOutDate(), theGuest.getRate());

            // Set the updated guest in the bean
            List<CheckedInGuests> newList = new ArrayList<>();
            newList.add(manageHostel.getCheckedInGuestByGuestId(theGuest.getGuestId()));
            setAnUpdatedGuestToDisplay(newList);
            setGuestToDisplay(newList);
        }
        return "";
    }
}
