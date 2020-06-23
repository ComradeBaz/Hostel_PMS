/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.beans;

import com.nellinka.customInterfaces.CachedDataLocal;
import com.nellinka.customInterfaces.ManageGuestLocal;
import com.nellinka.customInterfaces.ManageHostelLocal;
import com.nellinka.entities.CheckedInGuests;
import com.nellinka.interfaces.ManageFacilitiesLocal;
import com.nellinka.tools.ShowMovedBedReservations;
import com.nellinka.utilities.GuestExtrasObject;
import com.nellinka.utilities.RoomObject;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author ComradeBaz
 */
@Named
@RequestScoped
public class DynamicData {

    @EJB
    private ManageHostelLocal manageHostel;

    @EJB
    private ManageGuestLocal manageGuest;

    @EJB
    private ManageFacilitiesLocal manageFacilities;

    @EJB
    private CachedDataLocal cachedData;

    public DynamicData() {
        // no arg constructor
    }

    public List<CheckedInGuests> getSavedGuestsNotCheckedIn() {

        return manageHostel.getSavedGuestsNotCheckedIn();
    }

    public List<CheckedInGuests> getSavedGuestsCheckedIn() {

        return manageHostel.getSavedGuestsCheckedIn();
    }

    public List<ShowMovedBedReservations> getMovedBedReservations(CheckedInGuests theGuest) {

        return manageGuest.getMovedBedReservationsObject(manageGuest.getMovedBedReservations(theGuest));
    }

    public List<RoomObject> getAvailability() {

        return manageHostel.getAvailability();
    }

    public int getTotalOccupancy() {
        
        return manageHostel.getTotalOccupancy();
    }
    
    public List<CheckedInGuests> getGuestsCheckingOutToday() {
        
        return manageHostel.getGuestsCheckingOutToday();
    }
}
