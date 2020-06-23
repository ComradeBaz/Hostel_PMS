/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tests;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ComradeBaz
 */
@Entity
@Table(name="testextras")
public class TestExtras implements Serializable {
    
    @Id
    @Column(name="item_name")
    private String itemName;
    
    @Column(name="item_amount")
    private float itemAmount;

    @Column(name="is_deposit")
    private boolean isDeposit;
    
    public TestExtras() {
        // No arg constructor
    }
    public TestExtras(String name, float amount) {
        this.itemName = name;
        this.itemAmount = amount;
    }
    public TestExtras(String name, float amount, boolean isDeposit) {
        this.itemName = name;
        this.itemAmount = amount;
        this.isDeposit = isDeposit;
    }
    public TestExtras(String itemName) {
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
    public boolean isIsDeposit() {
        return isDeposit;
    }
    public void setIsDeposit(boolean isDeposit) {
        this.isDeposit = isDeposit;
    }
    @Override
    public String toString() {
        return "Extras{" + "itemName=" + itemName + ", itemAmount=" + itemAmount + '}';
    }
    
}
