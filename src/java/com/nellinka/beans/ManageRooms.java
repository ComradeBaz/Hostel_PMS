/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.beans;

import com.nellinka.customInterfaces.CachedDataLocal;
import com.nellinka.customInterfaces.ManageHostelLocal;
import com.nellinka.tools.Logger;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author ComradeBaz
 */
@Named
@RequestScoped
public class ManageRooms implements Serializable {

    @EJB
    private ManageHostelLocal manageHostel;
    
    @EJB
    private CachedDataLocal cachedData;
    
    private String roomName;
    private int numberOfBeds;
    private float rate;

    public ManageRooms() {
        // no arg constructor
    }
    
    /*    @PostConstruct
    public void getRooms() {
    setHostelRooms(manageHostel.getHostelRoomNames());
    }*/
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfRooms) {
        this.numberOfBeds = numberOfRooms;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
        
    public String addRoom() {

        String retVal = "/secured/roomaddedsuccess";
        
        manageHostel.saveRoom(getRoomName(), getNumberOfBeds(), getRate());
        cachedData.setHostelRooms(manageHostel.getHostelRooms());
        cachedData.setRatesForRooms();
        
        Logger.safePrint("Added a new room, roomName: " + getRoomName());

        return retVal;
    }
    
    public String updateRoom() {
        
        String retVal = "/secured/roomupdatedsuccess";
        
        manageHostel.updateRoom(getRoomName(),  getNumberOfBeds(), getRate());
        // Update Singleton
        cachedData.setRatesForRooms();
        
        return retVal;
    }
    public String deleteRoom() {
        
        if((manageHostel.deleteRoom(getRoomName())) > 0) {
            cachedData.setHostelRooms(manageHostel.getHostelRooms());
            return "/secured/roomdeletedsuccess";
        }
        return "/secured/roomdeletederror";
    }
    
    // Display the current rate for the chosen room
    public void roomChangeListener(javax.faces.event.AjaxBehaviorEvent event) throws javax.faces.event.AbortProcessingException {
        setRate(cachedData.getRateByRoomName(getRoomName()));
        setNumberOfBeds(manageHostel.getNumberOfBedsByRoomName(getRoomName()));
    }
}
