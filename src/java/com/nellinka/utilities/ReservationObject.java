/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.utilities;

import java.util.Date;

/**
 *
 * @author ComradeBaz
 * Class to temporarily store reservation data for cases where the
 * guest is checking in this month and checking out next month
 * There are two tables to represent time for the application
 * If a reservation starts this month and ends next month 
 * it is broken into two reservations, one saved to this month and one saved to next month
 * When the month changes next month becomes this month 
 */
public class ReservationObject {
    
    private Date startDate;
    private Date endDate;
    private String whatTable;
    private boolean goToEndDate;
    private String whatMonth;
    
    public ReservationObject(Date startDate, Date endDate, String table, boolean goToEndDate, String whatMonth) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.whatTable = table;
        this.goToEndDate = goToEndDate;
        this.whatMonth = whatMonth;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getWhatTable() {
        return whatTable;
    }

    public void setWhatTable(String whatTable) {
        this.whatTable = whatTable;
    }

    public boolean isGoToEndDate() {
        return goToEndDate;
    }

    public void setGoToEndDate(boolean goToEndDate) {
        this.goToEndDate = goToEndDate;
    }

    public String getWhatMonth() {
        return whatMonth;
    }

    public void setWhatMonth(String whatMonth) {
        this.whatMonth = whatMonth;
    }
}
