/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.entities;

import java.io.Serializable;
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
    @NamedQuery(name="getIsExtraDeposit",
    query="SELECT ex.isDeposit FROM Extras ex WHERE ex.itemName"
                        + "=:itemName")
        ,
    @NamedQuery(name="getItemAmount",
            query="SELECT ex.itemAmount FROM Extras ex WHERE ex.itemName"
                        + "=:itemName")
        ,
    @NamedQuery(name = "getExtras",
            query = "SELECT ex FROM Extras ex")
        ,
    @NamedQuery(name = "getCheckInExtras",
            query = "SELECT ex FROM Extras ex WHERE ex.isDeposit = 1")
})
@Entity
@Table(name="extras")
public class Extras implements Serializable {
    
    @Id
    @Column(name="item_name")
    private String itemName;
    
    @Column(name="item_amount")
    private float itemAmount;

    @Column(name="is_deposit")
    private int isDeposit;
    
    public Extras() {
        // No arg constructor
    }
    public Extras(String name, float amount) {
        this.itemName = name;
        this.itemAmount = amount;
    }
    public Extras(String name, float amount, int isDeposit) {
        this.itemName = name;
        this.itemAmount = amount;
        this.isDeposit = isDeposit;
    }
    public Extras(String itemName) {
        this.itemName = itemName;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public float getItemAmount() {
        return itemAmount;
    }
    public void setItemAmount(float itemAmount) {
        this.itemAmount = itemAmount;
    } 
    public int isIsDeposit() {
        return isDeposit;
    }
    public void setIsDeposit(int isDeposit) {
        this.isDeposit = isDeposit;
    }
    @Override
    public String toString() {
        return "Extras{" + "itemName=" + itemName + ", itemAmount=" + itemAmount + '}';
    }
    
}
