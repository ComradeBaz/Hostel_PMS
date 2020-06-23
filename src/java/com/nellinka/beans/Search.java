/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.beans;

import com.nellinka.customInterfaces.ManageHostelLocal;
import com.nellinka.entities.CheckedInGuests;
import com.nellinka.staticdata.GuestStatus;
import com.nellinka.tools.DateUtility;
import com.nellinka.tools.Logger;
import com.nellinka.tools.SearchStrings;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author ComradeBaz
 */
@Named 
@RequestScoped
public class Search implements Serializable{
    
    @EJB
    private ManageHostelLocal manageHostel;
    private Date startDate;
    private Date endDate;
    private String guestStatus;
    private String roomName;
    private String country;
    private List<String> allGuestStatus;
    private List<String> allHostelRoomNames;
    private List<String> allCountries;
    private List<CheckedInGuests> results;
    
    public Search() {
        // no arg constructor
    }
    
    @PostConstruct
    public void initBean() {
        setAllGuestStatus(GuestStatus.getAllGuestStatus());
        setAllHostelRoomNames(manageHostel.getHostelRoomNames());
        setAllCountries(manageHostel.getAllCountries());
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

    public String getGuestStatus() {
        return guestStatus;
    }

    public void setGuestStatus(String guestStatus) {
        this.guestStatus = guestStatus;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public String search() {
        return "";
    }

    public List<String> getAllGuestStatus() {
        return allGuestStatus;
    }

    public void setAllGuestStatus(List<String> allGuestStatus) {
        this.allGuestStatus = allGuestStatus;
    } 

    public List<String> getAllHostelRoomNames() {
        return allHostelRoomNames;
    }

    public void setAllHostelRoomNames(List<String> allHostelRoomNames) {
        this.allHostelRoomNames = allHostelRoomNames;
    }

    public List<String> getAllCountries() {
        return allCountries;
    }

    public void setAllCountries(List<String> allCountries) {
        this.allCountries = allCountries;
    }

    public List<CheckedInGuests> getResults() {
        return results;
    }

    public void setResults(List<CheckedInGuests> results) {
        this.results = results;
    }
    
    public String doSearch() {

        setResults(manageHostel.getSavedGuestsCheckedInToday());
        
        String searchStatus = SearchStrings.getSearchStringPart(getGuestStatus(), "guestStatus");
        String searchRoomName = SearchStrings.getSearchStringPart(getRoomName(), "roomName");
        String searchCountry = SearchStrings.getSearchStringPart(getCountry(), "country");
        String searchString = "";
        
        String cInDateString = "";
        String cOutDateString = "";
        
        if(getStartDate() == null) {
            // do nothing
        } else {
            cInDateString = DateUtility.getADateInMySqlFormat(getStartDate());
        }
        if(getEndDate() == null) {
            // do nothing
        } else {
            cOutDateString = DateUtility.getADateInMySqlFormat(getEndDate());
        }
        

        searchString = SearchStrings.buildSearchString(searchStatus, searchRoomName, searchCountry, cInDateString, cOutDateString);

        setResults(manageHostel.searchCheckedInGuests(searchString));
        
        Logger.safePrint(searchString);
        
        return "";
    }
}
