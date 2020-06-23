/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.entities;

import com.nellinka.customInterfaces.RoomGridInterface;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ComradeBaz
 */
@NamedQueries({
    @NamedQuery(name="getABedWithOccupants",
            query="SELECT gv FROM RoomGrid gv WHERE gv.roomName"
                    + "=:param1 AND gv.bedNumber=:param2")
        ,
    @NamedQuery(name="getAllEntries",
            query="SELECT rg FROM RoomGrid rg")
        ,
    @NamedQuery(name="getRoomGridEntryByRoomNameAndBedNumber",
            query="Select gv from RoomGrid gv where gv.roomName"
                        + "=:param1 and gv.bedNumber=:param2")
        ,
    @NamedQuery(name="getAllRoomGridRooms",
            query="SELECT rg.roomName FROM RoomGrid rg")
        ,
    @NamedQuery(name="deleteRoomFromRoomGrid",
            query="DELETE FROM RoomGrid rm WHERE rm.roomName=:param1")
})
@Entity
@Table(name="room_grid")
public class RoomGrid implements Serializable, RoomGridInterface {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="entry_id")
    private int entryId;
    
    @Column(name="room_name")
    private String roomName;
    
    @Column(name="bed_number")
    private int bedNumber;
    
    @Column(name="bed_occupants")
    //private int[] bedOccupants;
    private List<Integer> bedOccupantsList;
    
    //private List<Integer> bedOccupantsList;
    
    public RoomGrid() {
        // no arg constructor
    }
    public RoomGrid(String roomName, int bedNumber, List<Integer> bed) {
        this.roomName = roomName;
        this.bedNumber = bedNumber;
        this.bedOccupantsList = bed;
    }

    @Override
    public int getEntryId() {
        return entryId;
    }
    @Override
    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }
    @Override
    public String getRoomName() {
        return roomName;
    }
    @Override
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    @Override
    public int getBedNumber() {
        return bedNumber;
    }
    @Override
    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }
/*    
    public int[] getBedOccupants() {
        return bedOccupants;
    }

    public void setBedOccupants(int[] bed) {
        this.bedOccupants = bed;
    }    
*/  @Override 
    public List<Integer> getBedOccupantsList() {
        return bedOccupantsList;
    }
    @Override
    public void setBedOccupantsList(List<Integer> bedOccupantsList) {
        this.bedOccupantsList = bedOccupantsList;
    }
}
