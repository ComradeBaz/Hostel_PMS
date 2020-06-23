/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.customInterfaces;

import java.util.List;

/**
 *
 * @author ComradeBaz
 */
public interface RoomGridInterface {

    public int getEntryId();

    public void setEntryId(int entryId);

    public String getRoomName();

    public void setRoomName(String roomName);

    public int getBedNumber();

    public void setBedNumber(int bedNumber);

    public List<Integer> getBedOccupantsList();

    public void setBedOccupantsList(List<Integer> bedOccupantsList);
}
