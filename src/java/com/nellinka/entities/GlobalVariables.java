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
    @NamedQuery(name = "getIsTablesHaveBeenSwitched",
            query = "SELECT gV.itemValue FROM GlobalVariables gV WHERE gV.itemName"
            + " = :param1")
        ,
    @NamedQuery(name = "getRoomGridTableNames",
            query = "SELECT gV FROM GlobalVariables gV WHERE gV.itemName"
            + " = :param1 OR gV.itemName = :param2")
        ,
    @NamedQuery(name = "getGlobalVariableEntryByName",
            query = "SELECT gV FROM GlobalVariables gV WHERE gV.itemName"
            + " = :param1")
})
@Entity
@Table(name="global_variables")
public class GlobalVariables implements Serializable {
    
    @Id
    @Column(name="item_name")
    private String itemName;
    
    @Column(name="item_value")
    private String itemValue;
    
    public GlobalVariables () {
        // no arg constructor
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
