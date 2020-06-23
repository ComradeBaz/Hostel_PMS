/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.beans;

import com.nellinka.customInterfaces.ManageHostelLocal;
import com.nellinka.tools.TestLogger;
import com.nellinka.tools.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author ComradeBaz
 *
 * Extras include deposits, laundry or any other extra service the hostel can
 * offer
 */
@Named
@RequestScoped
public class AddExtra {

    @EJB
    private ManageHostelLocal manageHostel;

    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext
    private EntityManager entityManager;
    private String extraName;
    private float extraAmount;
    private boolean isDeposit;
    private int isDepositValue;
    private final TestLogger testLogger;

    public AddExtra() {
        testLogger = new TestLogger();
    }

    public String getExtraName() {
        return extraName;
    }

    public void setExtraName(String extraName) {
        this.extraName = extraName;
    }

    public float getExtraAmount() {
        return extraAmount;
    }

    public void setExtraAmount(float extraAmount) {
        this.extraAmount = extraAmount;
    }

    public boolean getIsDeposit() {
        return isDeposit;
    }

    public void setIsDeposit(boolean isDeposit) {
        // Use the value from the boolean checkbox to set an int value in the 
        // datebase. 
        this.isDeposit = isDeposit;
        if (isDeposit) {
            setIsDepositValue(1);
        } else {
            setIsDepositValue(0);
        }
    }

    public int getIsDepositValue() {
        return isDepositValue;
    }

    public void setIsDepositValue(int isDepositValue) {
        this.isDepositValue = isDepositValue;
    }

    public String addExtraItem() {

        String retVal = "addextrasuccess";

        manageHostel.saveExtraItem(getExtraName(), getExtraAmount(), getIsDepositValue());
        
        Logger.safePrint("Added new extra: " + getExtraName());

        return retVal;
    }
}
