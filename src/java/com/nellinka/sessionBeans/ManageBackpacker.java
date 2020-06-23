/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.sessionBeans;

import com.nellinka.beans.Login;
import com.nellinka.beans.NavigationBean;
import com.nellinka.customInterfaces.CachedDataLocal;
import com.nellinka.customInterfaces.ManageGuestLocal;
import com.nellinka.customInterfaces.ManageHostelLocal;
import com.nellinka.entities.CheckedInGuests;
import java.util.Date;
import javax.ejb.EJB;
import com.nellinka.interfaces.ManageFacilitiesLocal;
import com.nellinka.tools.DateUtility;
import com.nellinka.tools.Logger;
import com.nellinka.tools.ShowMovedBedReservations;
import com.nellinka.utilities.GuestExtrasObject;
import com.nellinka.utilities.ManagePayBillObjects;
import com.nellinka.utilities.PayBillObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ComradeBaz
 */
@Named
@SessionScoped
public class ManageBackpacker implements Serializable {

    @EJB
    private ManageHostelLocal manageHostel;

    @EJB
    private ManageGuestLocal manageGuest;

    @EJB
    private CachedDataLocal cachedData;

    @EJB
    private ManageFacilitiesLocal manageFacilities;

    @Inject
    private NavigationBean navigationBean;

    private CheckedInGuests theGuest;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String country;
    private String roomName;
    private int bedNumber;
    private Date checkInDate;
    private Date checkOutDate;
    private float rate;
    private float totalAmount;
    private float discount;
    private float amountPaid;
    private float theBalance;
    private String currency;
    private String guestNote;
    private int lengthOfStay;
    private List<Integer> freeBeds;
    private List<PayBillObject> payBillObjects;
    private List<GuestExtrasObject> extrasObjectsToAddToCheckIn;
    private List<GuestExtrasObject> guestExtrasObjectToDisplay;
    private List<ShowMovedBedReservations> movedBedReservations;
    private List<GuestExtrasObject> guestExtrasObject_Deposit;
    private List<GuestExtrasObject> guestExtrasObject_NonDeposit;
    private List<GuestExtrasObject> guestExtrasObjectWithCountMoreThanZero;

    public ManageBackpacker() {
        // no arg constructor
    }

    @PostConstruct
    public void initialiseBean() {

        resetBean();
        manageFacilities.setHostelRooms(manageHostel.getHostelRooms());
        setGuestExtrasObjectToDisplay(manageHostel.getCheckInExtrasToDisplay());
        setExtrasObjectsToAddToCheckIn(new ArrayList<GuestExtrasObject>());
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getDiscount() {
        return discount * 100;
    }

    public void setDiscount(float discount) {
        this.discount = discount / 100;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public String getGuestNote() {
        return guestNote;
    }

    public void setGuestNote(String guestNote) {
        this.guestNote = guestNote;
    }

    public int getLengthOfStay() {
        return lengthOfStay;
    }

    public void setLengthOfStay(int lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }

    public List<Integer> getFreeBeds() {
        return freeBeds;
    }

    public void setFreeBeds(List<Integer> freeBeds) {
        this.freeBeds = freeBeds;
    }

    public List<PayBillObject> getPayBillObjects() {
        return payBillObjects;
    }

    public void setPayBillObjects(List<PayBillObject> payBillObjects) {
        this.payBillObjects = payBillObjects;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public float getTheBalance() {
        return theBalance;
    }

    public void setTheBalance(float theBalance) {
        this.theBalance = theBalance;
    }

    public CheckedInGuests getTheGuest() {
        return theGuest;
    }

    public void setTheGuest(CheckedInGuests theGuest) {
        this.theGuest = theGuest;
    }

    public List<GuestExtrasObject> getExtrasObjectsToAddToCheckIn() {
        return extrasObjectsToAddToCheckIn;
    }

    public void setExtrasObjectsToAddToCheckIn(List<GuestExtrasObject> extrasObjectsToAddToCheckIn) {
        this.extrasObjectsToAddToCheckIn = extrasObjectsToAddToCheckIn;
    }

    public List<GuestExtrasObject> getGuestExtrasObjectToDisplay() {
        return guestExtrasObjectToDisplay;
    }

    public void setGuestExtrasObjectToDisplay(List<GuestExtrasObject> guestExtrasObjectToDisplay) {

        if (guestExtrasObjectToDisplay != null) {
            this.guestExtrasObjectToDisplay = guestExtrasObjectToDisplay;

            List<GuestExtrasObject> temp = new ArrayList<>();
            List<GuestExtrasObject> tempWithDeposit = new ArrayList<>();
            List<GuestExtrasObject> tempWithoutDeposit = new ArrayList<>();

            for (GuestExtrasObject theObject : guestExtrasObjectToDisplay) {
                if (theObject.getItemCount() == 0) {
                    // do nothing
                } else if (theObject.getItemCount() > 0) {
                    if (theObject.isIsDeposit()) {
                        temp.add(theObject);
                        tempWithDeposit.add(theObject);
                    } else {
                        temp.add(theObject);
                        tempWithoutDeposit.add(theObject);
                    }
                }
            }
            setGuestExtrasObjectWithCountMoreThanZero(temp);
            setGuestExtrasObject_Deposit(tempWithDeposit);
            setGuestExtrasObject_NonDeposit(tempWithoutDeposit);
        }
    }

    public List<ShowMovedBedReservations> getMovedBedReservations() {
        return movedBedReservations;
    }

    public void setMovedBedReservations(List<ShowMovedBedReservations> movedBedReservations) {
        this.movedBedReservations = movedBedReservations;
    }

    public List<GuestExtrasObject> getGuestExtrasObject_Deposit() {
        return guestExtrasObject_Deposit;
    }

    public void setGuestExtrasObject_Deposit(List<GuestExtrasObject> guestExtrasObject_Deposit) {
        this.guestExtrasObject_Deposit = guestExtrasObject_Deposit;
    }

    public List<GuestExtrasObject> getGuestExtrasObject_NonDeposit() {
        return guestExtrasObject_NonDeposit;
    }

    public void setGuestExtrasObject_NonDeposit(List<GuestExtrasObject> guestExtrasObject_NonDeposit) {
        this.guestExtrasObject_NonDeposit = guestExtrasObject_NonDeposit;
    }

    public List<GuestExtrasObject> getGuestExtrasObjectWithCountMoreThanZero() {
        return guestExtrasObjectWithCountMoreThanZero;
    }

    public void setGuestExtrasObjectWithCountMoreThanZero(List<GuestExtrasObject> guestExtrasObjectWithCountMoreThanZero) {
        this.guestExtrasObjectWithCountMoreThanZero = guestExtrasObjectWithCountMoreThanZero;
    }

    // Method called when navigating from one page to another to perform 
    // an action against a chosen guest
    public void setTheGuest(ActionEvent ae) {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        //String guestIdString = params.get("currentGuest");
        String reservationIdString = params.get("reservationId");
        String theSource = ae.getComponent().getId();

        setTheGuest(manageHostel.getOneCheckedInGuestByReservationId(Integer.parseInt(reservationIdString)));
        setTheGuestForBean(getTheGuest(), Boolean.FALSE, Boolean.FALSE, theSource);

        Logger.safePrint("Setting the guest: " + getTheGuest().getFirstName() + " " + getTheGuest().getLastName());

        //return navigationBean.goToSaveCheckIn();
    }

    // This method saves a guest to the db without completing a checkIn 
    // for that guest. Called from the "Save" button on the screen where guest
    // details are entered
    public String saveGuest() {

        // User must enter all four fields, or just firstName and lastName
        int newGuestId = -1;
        if ((getCountry() != null) && (getIdNumber() != null)) {
            newGuestId = manageGuest.saveGuest(getCountry(), getIdNumber(), getFirstName(), getLastName());
            manageGuest.saveGuestToCheckInGuestsTable(newGuestId, getFirstName(),
                    getLastName(), getIdNumber(), getCountry(), cachedData.getDefaultCurrency());

        } else {
            newGuestId = manageGuest.saveGuestFirstLastName(getFirstName(), getLastName());
            manageGuest.saveGuestToCheckInGuestsTableFirstLastName(newGuestId, getFirstName(), getLastName());
        }
        Logger.safePrint("Saving guestId: " + newGuestId);
        setTheGuest(manageHostel.getCheckedInGuestByGuestId(newGuestId));

        //return cancelSession();
        return navigationBean.goToCheckIn();
    }

    public void deleteTheGuest(AjaxBehaviorEvent event) {
        //return "xconfirmdelete";
    }

    public String confirmDeleteGuest() {
        // If there's a roomGrid entry, delete it
        if ((getRoomName() != null) && (getBedNumber() != 0) && (getCheckInDate() != null) && (getCheckOutDate() != null)) {
            manageHostel.updateRoomGrid(getRoomName(), getBedNumber(),
                    getCheckInDate(), getCheckOutDate(), getTheGuest().getReservationId(), Boolean.TRUE);
        }

        manageGuest.removeGuestByReservationId(getTheGuest().getReservationId());

        return navigationBean.toDeleteConfirmation();
    }

    // doCheckIn called to navigate to the checkIn page, it doesn't do the checkin
    // doCheckIn saves the guest to guest_list table
    // and sets the guestId for the current checkIn
    // This method saves the guest details and assigns the guestID to 
    // the currently scoped newGuest
    public String doCheckIn() {

        String returnVal = "xsavecheckin";

        // Save basic information for the guest
        int newGuestId = manageGuest.saveGuest(getCountry(), getIdNumber(), getFirstName(), getLastName());
        // Guest choice of currency is unknown at this point, set it to default
        // Add an entry to the checkedInGuests table - room/bed/dates will be added below
        manageGuest.saveGuestToCheckInGuestsTable(newGuestId, getFirstName(), getLastName(), getIdNumber(), getCountry(), cachedData.getDefaultCurrency());
        setTheGuest(manageHostel.getCheckedInGuestByGuestId(newGuestId));

        Logger.safePrint("Navigating to checkIn page to complete checkIn for " + getFirstName() + " " + getLastName());

        //return returnVal;
        return navigationBean.goToSaveCheckIn();
    }

    // Will update the bed numbers available or the rate applied to the room chosen
    public void changeListener(javax.faces.event.AjaxBehaviorEvent event) throws javax.faces.event.AbortProcessingException {

        if ((getRoomName() != null) && !(getRoomName().equalsIgnoreCase(""))) {
            setRate(cachedData.getRateByRoomName(getRoomName()));
            Logger.safePrint("Setting rate for room " + getRoomName() + " - rate is " + Float.toString(cachedData.getRateByRoomName(getRoomName())));
        }
        if ((getRoomName() != null) && (getCheckInDate() != null) && (getCheckOutDate() != null) && !(getRoomName().equalsIgnoreCase(""))) {
            setFreeBeds(manageHostel.addFreeBedsToList(getRoomName(), getCheckInDate(),
                    getCheckOutDate(), manageHostel.getCheckedInGuestByGuestId(getTheGuest().getGuestId()), Boolean.FALSE));
            setRate(cachedData.getRateByRoomName(getRoomName()));
            setTotalAmount(manageGuest.calculateTotal(manageHostel.getCheckedInGuestByGuestId(getTheGuest().getGuestId()), Boolean.FALSE,
                    DateUtility.getLengthOfStay(getCheckInDate(), getCheckOutDate()), getRate()));
            // Set the object to display bill information to the user
            setPayBillObjects(ManagePayBillObjects.makePayBillObject(getGuestExtrasObjectWithCountMoreThanZero(),
                    getAmountPaid(), getTotalAmount()));
            Logger.safePrint("Setting freeBeds, rate and totalAmount for room " + getRoomName());
        }
    }

    public String checkInGuest() {

        // The retVal is the next page in the flow if there are no errors
        // and the user continues through the wizard
        String retVal = "yshowcheckedinguest";

        // Save the reservation details and log the result
        Logger.safePrint("CheckedIn " + (manageGuest.saveGuestCheckIn(getTheGuest().getGuestId(), getFirstName(), getLastName(), getIdNumber(), getCountry(), getRoomName(),
                getBedNumber(), getCheckInDate(), getCheckOutDate(), getRate(), getCurrency(), cachedData.getConversionRate(getCurrency()), getDiscount()).toString()));
        // Create the object that is used to display extras to the user
        manageGuest.buildGuestExtrasObject(getTheGuest().getGuestId(), manageHostel.getAllExtrasFromDb(),
                false, cachedData.getConversionRate(getCurrency()), getTheGuest().getReservationId());
        // Organise data so it can be displayed          
        setGuestExtrasObjectToDisplay(manageGuest.buildGuestExtrasObject(getTheGuest().getGuestId(),
                manageHostel.getAllExtrasFromDb(), false, cachedData.getConversionRate(getCurrency()), getTheGuest().getReservationId()));
        // Update the table holding reservation data that is queried when checking availability
        // and displaying the grid views of the rooms
        manageHostel.updateRoomGrid(getRoomName(), getBedNumber(), getCheckInDate(), getCheckOutDate(),
                manageHostel.getCheckedInGuestByGuestId(getTheGuest().getGuestId()).getReservationId(), Boolean.FALSE);

        Logger.safePrint("Checked In: " + getTheGuest().toString());

        //return retVal;
        return navigationBean.showCheckedInGuest();
    }

    // Called when the user clicks the +/- buttons on the extras table checkinguest2.xhtml
    public void updateExtrasItemCount(ActionEvent ae) {
        String button = ae.getComponent().getId();

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();

        String itemNameString = params.get("itemName");
        String itemAmountString = params.get("itemAmount");
        String entryIdString = params.get("entryId");

        // Update extras count to add to the checkIn for the guest
        // Update the object temporarily storing information about the guest choice on extras to add
        // Update the database
        setGuestExtrasObjectToDisplay(manageGuest.updateExtrasItemCount(getGuestExtrasObjectToDisplay(), button, getTheGuest().getGuestId(),
                itemNameString, Integer.parseInt(entryIdString)));

        // Update the totalAmount
        setTotalAmount(manageGuest.calculateTotal(getTheGuest(), Boolean.FALSE, DateUtility.getLengthOfStay(getCheckInDate(), getCheckOutDate()), getRate()));
        // Update the object displaying the bill information
        setPayBillObjects(ManagePayBillObjects.makePayBillObject(getGuestExtrasObjectWithCountMoreThanZero(),
                getAmountPaid(), getTotalAmount()));

        Logger.safePrint("Updated itemCount for guest " + getTheGuest().getFirstName() + " " + getTheGuest().getLastName() + " " + itemNameString);
    }

    // Save a payment made by the guest
    // Any payments made are displayed on checkout
    public void makePayment(AjaxBehaviorEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();

        String amountString = params.get("payBillForm:amountPaid");
        setAmountPaid(manageGuest.payTheBill(getTheGuest(), Float.parseFloat(amountString)));
        Logger.safePrint("The Guest has paid an amount towards their bill: " + amountString);
    }

    // Save a note against the guest reservation
    public void saveNote() {
        manageGuest.saveNote(getTheGuest().getReservationId(), getTheGuest().getGuestId(), getGuestNote());
        Logger.safePrint("Saving note: " + getGuestNote());
    }

    // Listener called if the user changes the currency for the guest
    public void updateCurrency(AjaxBehaviorEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();

        String newCurrency = params.get("currency");
        setCurrency(newCurrency);
        // If other fields have been set, update values with new currency
        if ((getRoomName() != null) && (getCheckInDate() != null) && (getCheckOutDate() != null)) {
            setRate(cachedData.getRateByRoomName(getRoomName()) * cachedData.getConversionRate(newCurrency));
            setTotalAmount((manageGuest.calculateTotal(getTheGuest(), Boolean.FALSE, DateUtility.getLengthOfStay(getCheckInDate(), getCheckOutDate()), getRate())));
            setPayBillObjects(ManagePayBillObjects.makePayBillObject(getGuestExtrasObjectWithCountMoreThanZero(),
                    getAmountPaid(), getTotalAmount()));
            setGuestExtrasObjectToDisplay(manageHostel.getCheckInExtrasToDisplayByCurrency(newCurrency));
        }

        Logger.safePrint("The user has chosen currency " + newCurrency + " for Guest " + getFirstName() + " " + getLastName());
    }

    // Save a change to the booking but do not check the guest in
    public String updateReservation() {

        manageGuest.saveUpdateToReservation(manageHostel.getCheckedInGuestByGuestId(getTheGuest().getGuestId()), getRoomName(), getBedNumber(),
                getCheckInDate(), getCheckOutDate(), getRate(), getDiscount());

        Logger.safePrint("Reservation for Guest " + getFirstName() + " " + getLastName() + " has been updated");
        resetBean();

        return navigationBean.cancelSessionGoToPendingCheckins();
    }

    public void setTheGuestForBean(CheckedInGuests tempGuest, boolean isThisAnEditTransaction, boolean ignoreZero, String theSource) {

        Logger.safePrint("Setting the guest for bean " + tempGuest.toString());

        setFirstName(tempGuest.getFirstName());
        setLastName(tempGuest.getLastName());
        setIdNumber(tempGuest.getIdNumber());
        setCountry(tempGuest.getCountry());
        setRoomName(tempGuest.getRoomName());
        setBedNumber(tempGuest.getBedNumber());
        setCheckInDate(tempGuest.getCheckInDate());
        setCheckOutDate(tempGuest.getCheckOutDate());
        setRate(tempGuest.getRate());
        setCurrency(tempGuest.getCurrency());
        setAmountPaid(tempGuest.getAmountPaid());
        setGuestNote(tempGuest.getNote());
        setDiscount(tempGuest.getDiscount());
        // During checkIn all deposit object are displayed
        // If the guest is checking out an object consisting of the guest's extras is displayed
        // When editing a guest all extras are displayed
        if (theSource.equalsIgnoreCase("cInButton")) {
            setGuestExtrasObjectToDisplay(manageGuest.buildGuestExtrasObject(getTheGuest().getGuestId(),
                    manageHostel.getAllCheckInExtrasFromDb(), Boolean.TRUE, cachedData.getConversionRate(getCurrency()), getTheGuest().getReservationId()));
            // Add bedNumber to freeBeds List so the freeBeds list is populated when the edit page loads, if the bed is not null
            if (getBedNumber() != 0) {
                setFreeBeds(manageHostel.addFreeBedsToList(getRoomName(), getCheckInDate(), getCheckOutDate(), tempGuest, isThisAnEditTransaction));
            }
            if (getRate() != 0) {

            }
        } else if (theSource.equalsIgnoreCase("myEditButton")) {
            setGuestExtrasObjectToDisplay(manageGuest.buildGuestExtrasObject(getTheGuest().getGuestId(), manageHostel.getAllExtrasFromDb(),
                    Boolean.TRUE, cachedData.getConversionRate(tempGuest.getCurrency()), tempGuest.getReservationId()));
        } else {
            setGuestExtrasObjectToDisplay(manageGuest.buildGuestExtrasObject(getTheGuest().getGuestId(), manageHostel.getAllExtrasFromDb(),
                    ignoreZero, cachedData.getConversionRate(tempGuest.getCurrency()), getTheGuest().getReservationId()));
        }

        if (!(theSource.equalsIgnoreCase("cInButton"))) {
            // Add bedNumber to freeBeds List so the freeBeds list is populated when the edit page loads
            setFreeBeds(manageHostel.addFreeBedsToList(getRoomName(), getCheckInDate(), getCheckOutDate(), tempGuest, isThisAnEditTransaction));
            // Calculate the length so it can be displayed
            setLengthOfStay(DateUtility.getLengthOfStay(getCheckInDate(), getCheckOutDate()));
            // Set totals so they're available if a user opens the form showing the bill 
            setTotalAmount(manageGuest.calculateTotal(getTheGuest(), Boolean.FALSE, getLengthOfStay(), getRate()));
            // Check if the guest has any other reservations (eg moved bed during the stay)           
            setMovedBedReservations(manageGuest.getMovedBedReservationsObject(manageGuest.getMovedBedReservations(getTheGuest())));
            //setOtherReservations(manageGuest.getOtherReservationsObject(manageGuest.getMovedBedReservations(getTheGuest())));
            // Get the total associated with those reservations and add to the total amount           
            if (getMovedBedReservations().size() > 0) {
                setTotalAmount(manageGuest.addAmountForOtherReservationsToTotal(manageGuest.getMovedBedReservations(getTheGuest()), getTotalAmount()));
            }

            // Calculate the balance, guest may have paid an amount during their stay
            setTheBalance(getTotalAmount() - getAmountPaid());

            Logger.safePrint("Setting the guest - guest_id: " + tempGuest.getGuestId());
        }
        // Build/Set the object that displays the bill to the user
        setPayBillObjects(ManagePayBillObjects.makePayBillObject(getGuestExtrasObjectWithCountMoreThanZero(),
                getAmountPaid(), getTotalAmount()));
    }

    // The guest is using the amount paid as a deposit to pay their bill
    public void retainDeposit(ActionEvent ae) {

        Logger.safePrint("Guest is using their deposit to pay the final bill");
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        String reservationIdString = params.get("reservationId");
        String amountPaidString = params.get("amountPaid");
        float amountPaidFloat = Float.parseFloat(amountPaidString);

        setAmountPaid(manageGuest.payTheBill(manageHostel.getOneCheckedInGuestByReservationId(Integer.parseInt(reservationIdString)), amountPaidFloat));
        setTheBalance(getTotalAmount() - getAmountPaid());
    }

    // Method to check out a guest, change status and unassign the room/bed
    public String checkOutGuest() {

        // Note - delete the extras from the GuestExtras table
        String retVal = "zconfirmation";
        manageGuest.saveGuestCheckOut(getTheGuest());
        // Delete entries in the roomGrid if the guest is checking out early
        //manageHostel.updateRoomGrid(getTheGuest().getRoomName(), getTheGuest().getBedNumber(), new Date(), getTheGuest().getCheckOutDate(), 
        //getTheGuest().getReservationId(), Boolean.TRUE);
        Logger.safePrint("Checked out Guest: " + getTheGuest().toString());

        return retVal;
    }

    // actionListener to update amount_paid field of CheckedInGuests table
    public void updateAmountPaid() {
        setAmountPaid(manageGuest.payTheBill(getTheGuest(), getAmountPaid()));
    }

    @PreDestroy
    public void beforeDestroy() {
        Logger.safePrint("Destroying Bean");
    }

    public void resetBean() {

        setFirstName("");
        setLastName("");
        setIdNumber("");
        setCountry("");
        setRoomName("");
        setBedNumber(0);
        setCheckInDate(null);
        setCheckOutDate(null);
        setRate(0);
        setTotalAmount(0);
        setFreeBeds(null);
        setPayBillObjects(null);
        setGuestExtrasObjectToDisplay(null);
        setExtrasObjectsToAddToCheckIn(null);
        setMovedBedReservations(null);
        setGuestExtrasObject_Deposit(null);
        setGuestExtrasObject_NonDeposit(null);
        setGuestExtrasObjectWithCountMoreThanZero(null);
    }
}
