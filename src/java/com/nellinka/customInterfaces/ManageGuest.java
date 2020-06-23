/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.customInterfaces;

import com.nellinka.entities.CheckedInGuests;
import com.nellinka.entities.Extras;
import com.nellinka.entities.Guest;
import com.nellinka.entities.GuestExtras;
import com.nellinka.staticdata.GuestStatus;
import com.nellinka.tools.DateUtility;
import com.nellinka.tools.Logger;
import com.nellinka.tools.ShowMovedBedReservations;
import com.nellinka.utilities.GuestExtrasObject;
import com.nellinka.utilities.OtherReservationsObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;

/**
 *
 * @author ComradeBaz
 */
@Stateless
public class ManageGuest implements ManageGuestLocal {

    @EJB
    private ManageHostelLocal manageHostel;

    @EJB
    private CachedDataLocal cachedData;

    @PersistenceContext
    private EntityManager entityManager;

    // Persist the extras chosen for the guest (eg "Key Deposit") to the database
    @Override
    public void saveExtras(List<GuestExtrasObject> someExtras, int guestId) {

        Logger.safePrint("Saving guest extras to the database");
        try {
            for (GuestExtrasObject gEO : someExtras) {
                String eName = gEO.getItemName();
                int eCount = gEO.getItemCount();
                GuestExtras gtExtras = new GuestExtras(guestId, eName, eCount);
                entityManager.persist(gtExtras);
            }

        } catch (IllegalStateException
                | SecurityException e) {

            Logger.safePrint("ManageGuest.saveExtras() exception " + e.getMessage());

        }
    }

    // This creates the objects that are added to a List to be iterated over
    // when listing the available guestExtras on the checkinguest2.xhtml page
    // Only deposit items are shown at checkIn
    @Override                                                           // add a rate field, default to 1 and modified with a dropdown list
    public List<GuestExtrasObject> buildGuestExtrasObject(int guestId, List<Extras> theExtras, boolean ignoreZero, float conversionRate, int reservationId) {

        GuestExtrasObject gEObject;
        List<GuestExtrasObject> guestExtrasObject = new ArrayList<>();

        for (Extras e : theExtras) {
            String extraName = e.getItemName();

            try {
                // GuestExtras saves the itemName and the guestId if they have the item
                //Query query = entityManager.createQuery("Select xe.itemCount, xe.entryId from GuestExtras xe where xe.itemName"
                //+ "=:param1 and xe.guestId=:param2");
                List<Object[]> tempList = entityManager.createNamedQuery("getItemCountByGuestId")
                        .setParameter("param1", extraName)
                        .setParameter("param2", guestId)
                        .getResultList();

                // The results list consists of the itemCount and entryId
                // itemCount is first in the Object[], itemCount is second
                // Check to see if there are results and extract the values
                int itemCountIndex = 0;
                int entryIdIndex = 1;
                //List<Object[]> tempList = query.getResultList();
                List<Integer> intResults;

                int theCount = 0;
                int theEntryId = 0;
                // Initialise array for query.getResultsList()
                int tempListLength = tempList.size();
                // Initialise array for int values
                intResults = new ArrayList<>(tempListLength);
                int index;
                // If the guest had not gotten the current "extraName" the result list will be 0 elements long
                // Check to avoid an Array index out of range error
                if (tempListLength > 0) {
                    //theCount = tempList.get(0);
                    for (Object[] o : tempList) {
                        for (index = 0; index < o.length; index++) {
                            intResults.add((Integer) o[index]);
                        }
                    }
                    theCount = intResults.get(itemCountIndex);
                    theEntryId = intResults.get(entryIdIndex);
                }
                //query = entityManager.createQuery("Select ext.itemAmount from Extras ext where ext.itemName"
                        //+ "=:param3");
                //query.setParameter("param3", extraName);
                List<Float> tempFloatList = entityManager.createNamedQuery("getItemAmountByItemName")
                        .setParameter("param1", extraName)
                        .getResultList();

                //List<Float> tempFloatList = query.getResultList();

                //float theAmount = tempFloatList.get(0);
                float theAmount = (tempFloatList.get(0) * conversionRate);

                //query = entityManager.createQuery("Select ext.isDeposit from Extras ext where ext.itemName"
                        //+ "=:param3");
                //query.setParameter("param3", extraName);
                List<Integer> tempBooleanList = entityManager.createNamedQuery("getIsDeposit")
                        .setParameter("param1", extraName)
                        .getResultList();
                //List<Integer> tempBooleanList = query.getResultList();
                int intValue = tempBooleanList.get(0);
                boolean isItADeposit = false;
                if (intValue == 1) {
                    isItADeposit = Boolean.TRUE;
                }

                gEObject = new GuestExtrasObject(theCount, extraName, theAmount, guestId, theEntryId, isItADeposit, reservationId);

                if (theCount > 0) {
                    guestExtrasObject.add(gEObject);
                }
                if ((theCount == 0) && (ignoreZero)) {
                    if (fixItemCount(extraName, guestExtrasObject)) {
                        gEObject = new GuestExtrasObject(theCount, extraName, theAmount, guestId, theEntryId);
                        guestExtrasObject.add(gEObject);
                        //Logger.safePrint("List: " + guestExtrasObject.size() + " " + gEObject.toString());
                    } else {
                        //Logger.safePrint("Updated item count for " + extraName);
                    }
                }

            } catch (IllegalStateException
                    | SecurityException ex) {

                Logger.safePrint("ManageGuest.buildGuestExtrasObject() error: " + ex.getMessage());
            }
            Logger.safePrint("Built guestExtrasObject");
        }
        return guestExtrasObject;
    }

    @Override
    public int saveGuest(String country, String idNumber, String firstName, String lastName) {

        Guest newGuest = new Guest();

        if ((country != null) && (idNumber != null)) {
            newGuest = new Guest(firstName, lastName, idNumber, country);
        }
        // persist the guest to generate a unique guestId
        entityManager.persist(newGuest);
        entityManager.flush();

        Logger.safePrint("Saved guest: " + firstName + " " + lastName + " from " + country);

        return newGuest.getGuestId();
    }

    @Override
    public int saveGuestFirstLastName(String firstName, String lastName) {

        //Guest newGuest = new Guest(firstName, lastName);
        Guest newGuest = new Guest();
        entityManager.persist(newGuest);
        entityManager.flush();

        Logger.safePrint("Saved guest: " + firstName + " " + lastName + ", no country");

        return newGuest.getGuestId();
    }

    @Override
    public int saveGuestToCheckInGuestsTable(int guestId, String firstName, String lastName, String idNumber, String country, String currency) {

        CheckedInGuests checkedInGuest = new CheckedInGuests(guestId, firstName, lastName, idNumber, country, currency);
        entityManager.persist(checkedInGuest);

        Logger.safePrint("New guest " + firstName + " " + lastName + ", guestID: " + guestId);

        return checkedInGuest.getReservationId();
    }

    @Override
    public int saveGuestToCheckInGuestsTableFirstLastName(int guestId, String firstName, String lastName) {
        return -1;
    }

    // Change guest Status from Saved to CheckedIn, assign room and bed number
    @Override
    public CheckedInGuests saveGuestCheckIn(int guestId, String firstName, String lastName, String idNumber, String country, String roomName,
            int bedNumber, Date checkInDate, Date checkOutDate, float rate, String currency, float conversionRate, float discount) {

        Logger.safePrint("About to check in guestId " + guestId);

        CheckedInGuests aGuest = getCheckedInGuest(guestId);

        // Get the user from the db and change the status
        CheckedInGuests tempGuest = entityManager.find(CheckedInGuests.class, aGuest.getReservationId());
        tempGuest.setStatus(GuestStatus.getCheckedInStatus());

        // Save the data
        // Save first/lastName with ID and Country to deal with edits
        tempGuest.setFirstName(firstName);
        tempGuest.setLastName(lastName);
        tempGuest.setIdNumber(idNumber);
        tempGuest.setCountry(country);
        tempGuest.setRoomName(roomName);
        tempGuest.setBedNumber(bedNumber);
        tempGuest.setCheckInDate(checkInDate);
        tempGuest.setCheckOutDate(checkOutDate);
        tempGuest.setRate(rate);
        tempGuest.setStatus(GuestStatus.getCheckedInStatus());
        //tempGuest.setExtras(guestChoice);
        tempGuest.setCurrency(currency);
        tempGuest.setDiscount(discount);

        Logger.safePrint("Checked in guest " + tempGuest);

        return tempGuest;
    }

    // If the user makes a change to the guest's reservation from editguest.xhtml
    // this method is called to update their reservation
    @Override
    public int saveEditGuestReservation(CheckedInGuests theGuest, String roomName, int bedNumber, Date checkInDate, Date checkOutDate, float rate) {

        Logger.safePrint("Saving an edit to the guest reservation. Guest is " + theGuest.getFirstName() + " " + theGuest.getLastName());
        int retVal = -1;

        //EditGuestTypes editGuestType = EditGuestType.getEditGuestType(theGuest, checkInDate, checkOutDate, roomName, bedNumber);
        if (!(theGuest.getRoomName().equalsIgnoreCase(roomName))) {

            CheckedInGuests guestMovedBed = new CheckedInGuests(theGuest.getGuestId(), theGuest.getFirstName(), theGuest.getLastName(),
                    theGuest.getIdNumber(), theGuest.getCountry(), theGuest.getCurrency());
            guestMovedBed.setStatus(GuestStatus.getMovedBedStatus());

            // Set data and add it to the database
            guestMovedBed.setRoomName(roomName);
            guestMovedBed.setBedNumber(bedNumber);
            guestMovedBed.setCheckInDate(checkInDate);
            guestMovedBed.setCheckOutDate(checkOutDate);
            guestMovedBed.setRate(rate);
            guestMovedBed.setExtras(theGuest.getExtras());

            entityManager.persist(guestMovedBed);
            entityManager.flush();

            // Get the new checkIn from the db so I can get the reservationId assigned
            CheckedInGuests theNewCheckIn = manageHostel.getCheckedInGuestByGuestIdRoomNameBedNumber(guestMovedBed.getGuestId(),
                    guestMovedBed.getRoomName(), guestMovedBed.getBedNumber());

            // Add the new data to the gridView tables
            // final arguement is true when this is a deleteRequest to the roomGrid
            manageHostel.updateRoomGrid(theNewCheckIn.getRoomName(), theNewCheckIn.getBedNumber(),
                    theNewCheckIn.getCheckInDate(), theNewCheckIn.getCheckOutDate(), theNewCheckIn.getReservationId(), Boolean.FALSE);

            Logger.safePrint("Edited: " + theGuest.toString());

            return guestMovedBed.getReservationId();

        } else {
            // else update the other fields for the existing reservationId
            CheckedInGuests aGuest = manageHostel.getCheckedInGuestByGuestId(theGuest.getGuestId());
            aGuest.setBedNumber(bedNumber);
            aGuest.setCheckInDate(checkInDate);
            aGuest.setCheckOutDate(checkOutDate);
            // Add the new data to the gridView tables
            manageHostel.updateRoomGrid(aGuest.getRoomName(), aGuest.getBedNumber(),
                    aGuest.getCheckInDate(), aGuest.getCheckOutDate(), aGuest.getReservationId(), Boolean.FALSE);
        }
        return theGuest.getReservationId();
    }

    @Override
    public void saveUpdateToReservation(CheckedInGuests theGuest, String roomName, int bedNumber, Date checkInDate, Date checkOutDate, float rate, float discount) {

        Logger.safePrint("Saving an update to the guest reservation, check in is not complete. Guest is " + theGuest.getFirstName() + " " + theGuest.getLastName());

        CheckedInGuests aGuest = manageHostel.getCheckedInGuestByGuestId(theGuest.getGuestId());
        aGuest.setRoomName(roomName);
        aGuest.setBedNumber(bedNumber);
        aGuest.setCheckInDate(checkInDate);
        aGuest.setCheckOutDate(checkOutDate);
        aGuest.setRate(rate);
        aGuest.setDiscount(discount);

        manageHostel.updateRoomGrid(aGuest.getRoomName(), aGuest.getBedNumber(),
                aGuest.getCheckInDate(), aGuest.getCheckOutDate(), aGuest.getReservationId(), Boolean.FALSE);
    }
// Change the guest status from CheckedIn to CheckedOut
// Also update any movedBed status reservations in the db for this guest

    @Override
    public void saveGuestCheckOut(CheckedInGuests aGuest) {

        entityManager.find(CheckedInGuests.class,
                aGuest.getReservationId()).setStatus(GuestStatus.getCheckedOutStatus());

        // If the guest has moved bed during their stay find the other reservations and set status to GuestStatus.getCheckedOutStatus()
        entityManager.createNamedQuery("updateMovedBedStatusOnCheckOut")
                .setParameter("param1", aGuest.getGuestId())
                .setParameter("param2", GuestStatus.getMovedBedStatus())
                .executeUpdate();

        Logger.safePrint("Checked out guest: " + aGuest);

        // Update the grid tables if the guest is checking out early
        Date today = new Date();
        if (today.compareTo(aGuest.getCheckOutDate()) < 0) {
            Logger.safePrint("Guest cheking out early - deleting roomGrid entry for Room " + aGuest.getRoomName());
            manageHostel.updateRoomGrid(aGuest.getRoomName(), aGuest.getBedNumber(), new Date(), aGuest.getCheckOutDate(),
                    aGuest.getReservationId(), Boolean.TRUE);
        }

        // Delete guestExtras
        deleteGuestExtrasObjectsDbEntries(aGuest.getGuestId());
    }

    public void deleteGuestExtrasObjectsDbEntries(int guestId) {

        entityManager.createNamedQuery("deleteGuestExtrasOnCheckOut")
                .setParameter("param1", guestId)
                .executeUpdate();
    }

    // Delete a guest from CheckedInGuests
    @Override
    public void removeGuestByReservationId(int reservationId) {

        CheckedInGuests theGuest = manageHostel.getOneCheckedInGuestByReservationId(reservationId);

        entityManager.remove(theGuest);
    }

    // Get a guest from the checked_in_guests table by guestId 
    // assigned when the guest is first entered in the system
    @Override
    public CheckedInGuests getCheckedInGuest(int guestId) {

        List<CheckedInGuests> gList = entityManager.createNamedQuery("getGuestByGuestId")
                .setParameter("param1", guestId)
                .getResultList();

        CheckedInGuests retValue = gList.get(0);

        return retValue;

    }

    // Method to update the itemCount for extras items assigned to the guest
    // Called when the user clicks the +/- buttons on the extras table checkinguest2.xhtml
    // Modify the extrasObjectsToAddToCheckIn and return it
    @Override
    public List<GuestExtrasObject> updateExtrasCountForCheckIn(int guestId, List<GuestExtrasObject> extrasObjectsToAddToCheckIn,
            String button, String itemNameString, String itemAmountString) {

        Logger.safePrint("Updating extras count for guestId " + guestId);

        List<GuestExtrasObject> retValue = new ArrayList<>();
        GuestExtrasObject gExtrasObject;
        boolean addItemToList = true;
        GuestExtrasObject geObject;

        // Check the source of the request, plus or minus
        // If it's a plus add the object
        // It it's a minus get the list of objects and minus 1 for the appropriate item
        // Iterate through the list to see if the item is present
        // If it is set the addItemToList boolean to false so it is not added when the for loop finishes
        if (button.equalsIgnoreCase("plus")) {
            if (extrasObjectsToAddToCheckIn.size() > 0) {
                for (ListIterator<GuestExtrasObject> theListIterator = extrasObjectsToAddToCheckIn.listIterator(); theListIterator.hasNext();) {
                    geObject = theListIterator.next();
                    String itemNameValue = geObject.getItemName();
                    if (itemNameValue.equalsIgnoreCase(itemNameString)) {
                        geObject.setItemCount(geObject.getItemCount() + 1);
                        addItemToList = false;
                        // Continue to iterate over all entries in the list
                        // If an existing entry is not found addedItem will resolve to false and the if(!addedItem) statement will return true
                        // The item will be added to the end of the list
                    }
                }
                // Called if the item was not found in the list of objects assigned to the guest
                if (addItemToList) {
                    ListIterator<GuestExtrasObject> theListIterator = extrasObjectsToAddToCheckIn.listIterator();
                    gExtrasObject = new GuestExtrasObject(1, itemNameString, Float.parseFloat(itemAmountString), guestId);
                    theListIterator.add(gExtrasObject);
                }
            } else {
                // If the list.size() == 0 add the item
                ListIterator<GuestExtrasObject> theListIterator = extrasObjectsToAddToCheckIn.listIterator();
                gExtrasObject = new GuestExtrasObject(1, itemNameString, Float.parseFloat(itemAmountString), guestId);
                theListIterator.add(gExtrasObject);
            }
        }
        if (button.equalsIgnoreCase("minus")) {
            // If size() !> 0 then do nothing
            if (extrasObjectsToAddToCheckIn.size() > 0) {
                for (ListIterator<GuestExtrasObject> theListIterator = extrasObjectsToAddToCheckIn.listIterator(); theListIterator.hasNext();) {
                    geObject = theListIterator.next();
                    if (geObject.getItemName().equalsIgnoreCase(itemNameString)) {
                        geObject.setItemCount(geObject.getItemCount() - 1);
                    }
                }
            }
        }

        return extrasObjectsToAddToCheckIn;
    }

    // Update the temp object so the itemCount value on the checkinguest2.xhtml page is updated via Ajax request
    // Modify the guestExtrasObjectToDisplay and return the object
    @Override
    public List<GuestExtrasObject> updateExtrasObjectToDisplay(List<GuestExtrasObject> guestExtrasObjectToDisplay, String itemNameString, String button) {

        GuestExtrasObject theObject;
        for (GuestExtrasObject g : guestExtrasObjectToDisplay) {
            // Find the correct entry
            if (g.getItemName().equalsIgnoreCase(itemNameString)) {
                theObject = g;
                int count = theObject.getItemCount();
                if (button.equalsIgnoreCase("plus")) {
                    theObject.setItemCount(count + 1);
                } else {
                    theObject.setItemCount(count - 1);
                }
            }
        }
        return guestExtrasObjectToDisplay;
    }

    // Update count of items in guestExtrasObject
    @Override
    public boolean fixItemCount(String extraName, List<GuestExtrasObject> guestExtrasObject) {

        for (GuestExtrasObject gEO : guestExtrasObject) {
            String currentExtra = extraName;
            if (gEO.getItemName().equalsIgnoreCase(currentExtra)
                    && (gEO.getItemCount() == 0)) {
                //gEO.setItemCount(gEO.getItemCount() + 1);
                // This is the first time the user has added the extra for the guest
                // Add a row to the database
                return true;
            }
            if (gEO.getItemName().equalsIgnoreCase(currentExtra)
                    && (gEO.getItemCount() > 0)) {
                //gEO.setItemCount(gEO.getItemCount() + 1);
                // This is an additional count, update the existing GuestExtra 
                // record in the dB
                return false;
            }
        }
        return true;
    }

    // Update the itemCount, boolean determines if it is incremented or decremented
    @Override
    public void updateItemCount(int guestId, int entryId, String itemName, boolean addToItemCount) {

        Logger.safePrint("Updating extras itemCount for " + itemName + ", guestId" + guestId);

        List<GuestExtras> results = new ArrayList<>();
        int result = 0;

        if (entryId == 0) {
            entityManager.createNamedQuery("addToGuestExtrasItemCountByGuestId")
                    .setParameter("param1", guestId)
                    .setParameter("param2", itemName)
                    .executeUpdate();
        } else {
            entityManager.createNamedQuery("getGuestExtraItemCountByEntryId")
                    .setParameter("param1", entryId);
            if (addToItemCount) {
                entityManager.createNamedQuery("addToGuestExtrasItemCountByEntryId")
                        .setParameter("param1", entryId)
                        .executeUpdate();
            }
            if (!addToItemCount) {
                entityManager.createNamedQuery("subtractFromGuestExtrasItemCountByEntryId")
                        .setParameter("param1", entryId)
                        .executeUpdate();
            }

            Logger.safePrint(result + " rows updated in guest_extras");
        }
    }

    // Calculate what the guest should pay, including bed plus extras
    // not including deposit items. Deposit items are displayed on the 
    // check out confirmation page but the price isn't added to the final amount
    @Override
    public float calculateTotal(CheckedInGuests cInGuest, Boolean deleteExtras, int lengthOfStay, float rate) {

        // Get the basic total
        float retValue = lengthOfStay * rate;

        // Calculate the discount if there is one
        if (cInGuest.getDiscount() > 0) {
            retValue = applyDiscount(cInGuest, retValue);
        }

        // Get the extras from the guest_extras table
        // Check the extras against the extras table to see if they are deposits
        // If they are not deposits add the amount to the total
        List<String> extrasList = new ArrayList<>();
        try {
            extrasList = entityManager.createNamedQuery("getGuestExtrasByGuestId")
                    .setParameter("param1", cInGuest.getGuestId())
                    .getResultList();
            // Check is the item a deposit item
            for (String s : extrasList) {
                String searchString = s.trim();

                List<Integer> isDeposit = entityManager.createNamedQuery("getIsExtraDeposit")
                        .setParameter("itemName", searchString)
                        .getResultList();

                // If the result is false
                if (isDeposit.get(0) == 0) {
                    // Get the amount for the item
                    List<Float> itemAmount = entityManager.createNamedQuery("getItemAmount")
                            .setParameter("itemName", s)
                            .getResultList();
                    //float theAmount = itemAmount.get(0);
                    float theAmount = (itemAmount.get(0) * cachedData.getConversionRate(cInGuest.getCurrency()));

                    // Get the count for the item
                    List<Integer> theCount = entityManager.createNamedQuery("getGuestExtraItemCountByGuestId")
                            .setParameter("param1", cInGuest.getGuestId())
                            .setParameter("param2", searchString)
                            .getResultList();
                    int theExtraCount = theCount.get(0);

                    // Add this to the base amount
                    retValue += (theAmount * theExtraCount);
                }
                // Delete the entry from the guest_extras table if boolean is set
                if (deleteExtras) {
                    try {
                        int result = entityManager.createNamedQuery("deleteFromExtras")
                                .setParameter("paramOne", searchString)
                                .setParameter("paramTwo", cInGuest.getGuestId())
                                .executeUpdate();

                        Logger.safePrint(result + " row(s) deleted for guestId: " + cInGuest.getGuestId()
                                + " - deleted: " + searchString);

                    } catch (IllegalStateException
                            | QueryTimeoutException exc) {

                        Logger.safePrint("Error deleting record from guest_extras"
                                + " table" + exc.getMessage());
                    }
                }
            }
        } catch (SecurityException e) {

            Logger.safePrint("CheckOutFlow.calculateTotal() exception "
                    + e.getMessage());
        }

        Logger.safePrint("Calculating total for guest " + cInGuest.getFirstName() + " " + cInGuest.getLastName() + ". Total is " + retValue);

        return retValue;
    }

    private float applyDiscount(CheckedInGuests cInGuest, float theAmount) {

        float theDiscountRate = cInGuest.getDiscount();
        float theDiscount = theAmount * theDiscountRate;

        return theAmount - theDiscount;
    }

    // Find other reservations for the current guest
    // Status == 3 means they moved bed
    @Override
    public List<CheckedInGuests> getMovedBedReservations(CheckedInGuests checkedInGuest) {

        List<CheckedInGuests> reservations = entityManager.createNamedQuery("getMovedBedByGuestId")
                .setParameter("param1", checkedInGuest.getGuestId())
                .setParameter("param2", GuestStatus.getMovedBedStatus())
                .getResultList();

        return reservations;
    }

    // Add amount for other reservations to final amount (moved bed status reservations)
    @Override
    public float addAmountForOtherReservationsToTotal(List<CheckedInGuests> reservations, float theAmount) {

        float retValue = 0;

        for (CheckedInGuests cInG : reservations) {
            String oldRoom = cInG.getRoomName();
            int oldNoOfDays = DateUtility.getLengthOfStay(cInG.getCheckInDate(), cInG.getCheckOutDate());
            DecimalFormat df = new DecimalFormat("#.00");
            float oldRate = cInG.getRate();
            float formattedRate = Float.parseFloat(df.format(oldRate));

            ShowMovedBedReservations showThem = new ShowMovedBedReservations(oldRoom, oldNoOfDays, formattedRate);

            retValue += (showThem.getOldTotal() * cachedData.getConversionRate(cInG.getCurrency()));
        }
        // Add the totals for other reservations to the total for the current reservation passed in method call
        retValue += theAmount;

        return retValue;
    }

    @Override
    public List<ShowMovedBedReservations> getMovedBedReservationsObject(List<CheckedInGuests> reservations) {

        List<ShowMovedBedReservations> retValue = new ArrayList<>();

        for (CheckedInGuests cInG : reservations) {
            String oldRoom = cInG.getRoomName();
            int oldNoOfDays = DateUtility.getLengthOfStay(cInG.getCheckInDate(), cInG.getCheckOutDate());
            DecimalFormat df = new DecimalFormat("#.00");
            float oldRate = cInG.getRate();
            float formattedRate = Float.parseFloat(df.format(oldRate));

            ShowMovedBedReservations showThem = new ShowMovedBedReservations(oldRoom, oldNoOfDays, formattedRate);

            retValue.add(showThem);
        }

        return retValue;
    }

    @Override
    public List<OtherReservationsObject> getOtherReservationsObject(List<CheckedInGuests> reservations) {

        List<OtherReservationsObject> retVal = new ArrayList<>();

        for (CheckedInGuests cInG : reservations) {
            String oldRoom = cInG.getRoomName();
            int oldNoOfDays = DateUtility.getLengthOfStay(cInG.getCheckInDate(), cInG.getCheckOutDate());
            DecimalFormat df = new DecimalFormat("#.00");
            float oldRate = cInG.getRate();
            float formattedRate = Float.parseFloat(df.format(oldRate));

            ShowMovedBedReservations showThem = new ShowMovedBedReservations(oldRoom, oldNoOfDays, formattedRate);
            OtherReservationsObject room = new OtherReservationsObject("Room", cInG.getRoomName());
            OtherReservationsObject numberOfDays = new OtherReservationsObject("No. of Days", Integer.toString(oldNoOfDays));
            OtherReservationsObject rate = new OtherReservationsObject("Rate", Float.toString(formattedRate));
            retVal.add(room);
            retVal.add(numberOfDays);
            retVal.add(rate);
        }

        return retVal;
    }

    // Update the itemCount for extras items assigned to the guest
    @Override
    public List<GuestExtrasObject> updateExtrasItemCount(List<GuestExtrasObject> guestExtrasObject, String button, int guestId, String itemName, int entryId) {

        if (entryId != 0) {
            Logger.safePrint("About to update itemCount for guestId " + guestId + " itemName: " + itemName);

            // Check the source of the request, plus or minus 
            if (button.equalsIgnoreCase("plus")) {
                updateItemCount(guestId, entryId, itemName, true);
            }
            if (button.equalsIgnoreCase("minus")) {
                updateItemCount(guestId, entryId, itemName, false);
            }
        }
        // If entryId is 0 add a new row to the dB
        // Unless the user is adding an extra for the second time to the guest from the editguest.xhtml page
        // The first time an extra is added it is saved in an Object, not persisted to the db
        //boolean updateObject = true;
        if (entryId == 0) {
            if (fixItemCount(itemName, guestExtrasObject)) {
                // The object used to store information to be displayed on the extras section
                // of the edtiguest page has been updated in fixItemCount(itemNameString)
                // so it's not necessary to update it again
                //updateObject = false;
                GuestExtras aGuestExtra = new GuestExtras(guestId, itemName, 1);

                entityManager.persist(aGuestExtra);
                entityManager.flush();

            } else {
                if (button.equalsIgnoreCase("plus")) {
                    // The entryIdString isn't known, I need to go back to the dB to get it
                    updateItemCount(guestId, entryId, itemName, true);
                }
            }
        }
        // Update the object so the itemCount value on the editGuest page is updated via Ajax request
        GuestExtrasObject theObject;
        //if (updateObject) {
        for (GuestExtrasObject g : guestExtrasObject) {
            // Find the correct entry
            if ((g.getExtrasEntryId() == entryId)
                    && (g.getItemName().equalsIgnoreCase(itemName))) {
                theObject = g;
                int count = theObject.getItemCount();
                if (button.equalsIgnoreCase("plus")) {
                    theObject.setItemCount(count + 1);
                } else {
                    theObject.setItemCount(count - 1);
                }
            }
        }
        return guestExtrasObject;
    }

    // Pay an amount 
    // If a guest wishes to pay for part/all of their stay, a user can enter the 
    // amount and it will be saved then displayed when the guest checks out
    @Override
    public float payTheBill(CheckedInGuests aGuest, float theAmount) {

        CheckedInGuests theGuest = manageHostel.getCheckedInGuestByGuestId(aGuest.getGuestId());
        float amount = theGuest.getAmountPaid();
        amount += theAmount;
        theGuest.setAmountPaid(amount);

        Logger.safePrint("Guest " + theGuest.getFirstName() + " " + theGuest.getLastName() + " has paid " + theGuest.getAmountPaid() + " towards their bill");

        return amount;
    }

    @Override
    public List<Integer> getPossibleNewBedNumbersMore(int bedNumber, String roomName) {
        List<Integer> retList = new ArrayList<>();
        for (int i = bedNumber + 1; i <= manageHostel.getNumberOfBedsByRoomName(roomName); i++) {
            retList.add(i);
        }
        return retList;
    }

    @Override
    public List<Integer> getPossibleNewBedNumbersLess(int bedNumber, String roomName) {
        List<Integer> retList = new ArrayList<>();
        for (int i = bedNumber - 1; i > 0; i--) {
            retList.add(i);
        }
        return retList;
    }

    @Override
    public int getClosestAvailableBedNumber(List<Integer> availableBeds, List<Integer> possibleBeds) {

        for (Integer possibleBed : possibleBeds) {
            for (Integer availableBed : availableBeds) {
                if (possibleBed.equals(availableBed)) {
                    return possibleBed;
                }
            }
        }
        return -1;
    }

    @Override
    public void saveNote(int reservationId, int guestId, String theText) {
        CheckedInGuests theGuest = manageHostel.getCheckedInGuestByReservationAndGuestId(reservationId, guestId);
        theGuest.setNote(theText);

        Logger.safePrint("Saving note for reservationId " + reservationId + ". Note: " + theText);
    }
}
