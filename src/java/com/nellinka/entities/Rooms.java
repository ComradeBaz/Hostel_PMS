/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.entities;

import com.nellinka.tools.Logger;
import java.io.Serializable;
import java.util.LinkedHashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ComradeBaz
 */
@NamedQueries({
    @NamedQuery(name="getRoomsOccupants",
            query="SELECT rm.occupants FROM Rooms rm WHERE rm.roomName=:param1")
        ,
    @NamedQuery(name="getRoomNames",
            query="SELECT rm.roomName FROM Rooms rm WHERE rm.isOpen = 1")
        ,
    @NamedQuery(name="getAllRoomNames",
            query="SELECT rm.roomName FROM Rooms rm")
        ,
    @NamedQuery(name="getRooms",
            query="SELECT rm FROM Rooms rm")
        ,
    @NamedQuery(name="getOpenRooms",
            query="SELECT rm FROM Rooms rm WHERE rm.isOpen = 1")
        ,
    @NamedQuery(name="deleteRoom",
            query="DELETE FROM Rooms rm WHERE rm.roomName=:param1")
        ,
    @NamedQuery(name="deleteOpenRoom",
            query="UPDATE Rooms rm SET rm.isOpen = 0 WHERE rm.roomName=:param1")
        ,
    @NamedQuery(name="getRateByRoomName",
            query="SELECT rm.rate FROM Rooms rm WHERE rm.roomName =:param1")
        ,
    @NamedQuery(name="getNumberOfBedsByRoomName",
            query="SELECT rm.numberOfBeds FROM Rooms rm WHERE rm.roomName =:param1")
})
@Entity
@Table(name = "rooms")
public class Rooms implements Serializable {

    @Id
    @Column(name = "room_name")
    private String roomName;

    @Column(name = "number_of_beds")
    private int numberOfBeds;

    @Column(name = "occupants")
    private LinkedHashMap<Integer, Integer> occupants;
    
    @Column(name = "is_open")
    private int isOpen;
    
    @Column (name = "rate")
    private float rate;

    public Rooms() {
        // Default no arg constructor
    }

    public Rooms(String roomName, int numberOfBeds, float rate) {
        this.roomName = roomName;
        this.numberOfBeds = numberOfBeds;
        // New rooms are open by default
        this.rate = rate;
        this.isOpen = 1;
    }

    public Rooms(String roomName, int numberOfBeds, LinkedHashMap<Integer, Integer> occupants) {
        this.roomName = roomName;
        this.numberOfBeds = numberOfBeds;
        this.occupants = occupants;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        Logger.safePrint("setRoomName" + roomName);
        this.roomName = roomName;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        Logger.safePrint("setNumberOfBeds" + numberOfBeds);
        this.numberOfBeds = numberOfBeds;
    }

    public LinkedHashMap<Integer, Integer> getOccupants() {
        return occupants;
    }

    public void setOccupants(LinkedHashMap occupants) {
        this.occupants = occupants;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
        
    @Override
    public String toString() {
        return "Room Name: " + roomName + " Number of Beds: " + numberOfBeds;
    }

    @Override
    public boolean equals(Object o) {

        
        if (!(o instanceof Rooms)) {
            return false;
        }
        if(o instanceof Rooms) {
            Rooms tempRoom = (Rooms)o;
            if(tempRoom.roomName.equals(this.roomName))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + roomName.hashCode();

        return result;
    }
}
