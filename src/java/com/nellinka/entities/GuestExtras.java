/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.entities;

import java.io.Serializable;
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
    @NamedQuery(name = "getGuestExtrasByGuestId",
            query = "SELECT e.itemName FROM GuestExtras e WHERE e.guestId"
            + "=:param1")
    ,
    @NamedQuery(name = "getGuestExtraItemCountByGuestId",
            query = "SELECT e.itemCount FROM GuestExtras e WHERE e.guestId"
            + "=:param1 AND e.itemName=:param2")
    ,
    @NamedQuery(name = "deleteFromExtras",
            query = "DELETE FROM GuestExtras ge WHERE ge.itemName"
            + "=:param1 AND ge.guestId =:param2")
        ,
    @NamedQuery(name = "getItemAmountByItemName",
            query = "Select ext.itemAmount from Extras ext where ext.itemName"
                        + "=:param1")
        ,
    @NamedQuery(name = "getIsDeposit",
            query = "Select ext.isDeposit from Extras ext where ext.itemName"
                        + "=:param1")
    ,
    @NamedQuery(name = "getItemCountByGuestId",
            query = "SELECT xe.itemCount, xe.entryId FROM GuestExtras xe WHERE xe.itemName"
            + "=:param1 AND xe.guestId=:param2")
    ,
    @NamedQuery(name = "addToGuestExtrasItemCountByGuestId",
            query = "UPDATE GuestExtras gex SET gex.itemCount=gex.itemCount+1 WHERE gex.guestId"
            + "=:param1 AND gex.itemName =:param2")
    ,
    @NamedQuery(name = "getGuestExtraItemCountByEntryId",
            query = "SELECT e.itemCount FROM GuestExtras e WHERE e.entryId"
            + "=:param1")
    ,
    @NamedQuery(name = "addToGuestExtrasItemCountByEntryId",
            query = "UPDATE GuestExtras gex SET gex.itemCount=gex.itemCount+1 WHERE gex.entryId"
            + "=:param1")
    ,
    @NamedQuery(name = "subtractFromGuestExtrasItemCountByEntryId",
            query = "UPDATE GuestExtras gex SET gex.itemCount=gex.itemCount-1 WHERE gex.entryId"
            + "=:param1")
    ,
    @NamedQuery(name = "deleteGuestExtrasOnCheckOut",
            query = "DELETE FROM GuestExtras ge WHERE ge.guestId =:param1")
})
@Entity
@Table(name = "guest_extras")
public class GuestExtras implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "entry_id")
    private int entryId;

    @Column(name = "guest_id")
    private int guestId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_count")
    private int itemCount;

    public GuestExtras() {
        // No arg constructor
    }

    public GuestExtras(int guestId, String itemName) {
        this.guestId = guestId;
        this.itemName = itemName;
    }

    public GuestExtras(int guestId, String itemName, int itemCount) {
        this.guestId = guestId;
        this.itemName = itemName;
        this.itemCount = itemCount;
    }

    @Override
    public String toString() {
        return "GuestID: " + guestId + " ItemName: " + itemName;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
