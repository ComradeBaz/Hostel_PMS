/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.customInterfaces;

import com.nellinka.entities.ListOfCurrencies;
import com.nellinka.entities.GlobalVariables;
import com.nellinka.entities.Rooms;
import com.nellinka.tools.DateUtility;
import com.nellinka.tools.Logger;
import com.nellinka.utilities.GuestExtrasObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ComradeBaz
 */
@Singleton
@Startup
public class CachedData implements CachedDataLocal {

    @EJB
    private ManageHostelLocal manageHostel;

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> currencies;
    private List<String> allCurrencies;
    private LinkedHashMap<String, Float> roomRates;
    private List<Rooms> hostelRooms;
    private String defaultCurrency;

    @Override
    public List<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<String> currencies) {

        this.currencies = currencies;
    }

    public LinkedHashMap<String, Float> getRoomRates() {
        return roomRates;
    }

    @Override
    public List<String> getAllCurrencies() {
        return allCurrencies;
    }

    public void setAllCurrencies(List<String> allCurrencies) {
        this.allCurrencies = allCurrencies;
    }

    public void setRoomRates(LinkedHashMap<String, Float> roomRates) {
        this.roomRates = roomRates;
    }

    @Override
    public List<Rooms> getHostelRooms() {
        return hostelRooms;
    }

    @Override
    public void setHostelRooms(List<Rooms> hostelRooms) {
        this.hostelRooms = hostelRooms;
    }

    @PostConstruct
    public void init() {

        // On the first of each month the gridView tables are swapped
        if ((isSwitchRoomGridTablesToday()) && !(getIsRoomGridTablesHaveBeenSwitched())) {
            if (switchTableForThisMonthNextMonth()) {
                Logger.safePrint("Switched RoomGrid tables");
            }
        }
        if (isUpdateGridViewBooleanValueToday()) {
            if (updateGridViewBooleanValueToday()) {
                Logger.safePrint("Updated GridView boolean to FALSE");
            }
        }
        // Set currencies to display to user when checking in a guest
        setCurrencies(entityManager.createNamedQuery("getAllCurrencies").getResultList());
        getDefaultCurrencyAtStartUp();
        setRatesForRooms();
        setHostelRooms(manageHostel.getHostelRooms());
        // Set currencies available to user when adding new currencies from administration page
        setAllCurrencies(entityManager.createNamedQuery("getAllCurrencyCodes").getResultList());
    }

    @Override
    public String getTableForThisMonth() {

        String retValue = "";
        try {
            GlobalVariables thisMonth = entityManager.find(GlobalVariables.class, "this_month");

            retValue = thisMonth.getItemValue();
            Logger.safePrint(retValue + " is the table for this month");

        } catch (IllegalStateException
                | SecurityException e) {

            Logger.safePrint("CachedData error getting month from GlobalVariables " + e.getMessage());
        }

        return retValue;
    }

    @Override
    public String getTableForNextMonth() {

        String retValue = "";
        try {
            GlobalVariables nextMonth = entityManager.find(GlobalVariables.class, "next_month");

            retValue = nextMonth.getItemValue();
            Logger.safePrint(retValue + " is the table for next month");

        } catch (IllegalStateException
                | SecurityException e) {

            Logger.safePrint("CachedData error getting month from GlobalVariables " + e.getMessage());
        }
        return retValue;
    }

    @Override
    public boolean switchTableForThisMonthNextMonth() {
        Logger.safePrint("Changing tables for this month next month");
        // Get the table for this month before the tables are switched
        // Clear the gridView of occupants so it can be used 
        // to represent "nextMonth" data
        String tableForLastMonth = getTableForThisMonth();

        List<GlobalVariables> tableNames = getRoomGridTableNames();
        for (GlobalVariables gV : tableNames) {
            if (gV.getItemValue().equalsIgnoreCase("room_grid")) {
                gV.setItemValue("room_grid_two");
            } else if (gV.getItemValue().equalsIgnoreCase("room_grid_two")) {
                gV.setItemValue("room_grid");
            }
        }
        // Clear occupant data from the room grid table
        clearLastMonthGridTable(tableForLastMonth);
        // Reset the occupantLists for the nextMonth table
        // Change the value in the database so the table is not switched again
        getGlobalVariableEntryByName("tables_have_been_switched").setItemValue("TRUE");

        return Boolean.TRUE;
    }

    @Override
    public boolean getIsRoomGridTablesHaveBeenSwitched() {

        List<String> result = entityManager.createNamedQuery("getIsTablesHaveBeenSwitched")
                .setParameter("param1", "tables_have_been_switched")
                .getResultList();

        if (result.get(0).equalsIgnoreCase("TRUE")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public boolean isSwitchRoomGridTablesToday() {
        // If it is the first of the month change the tables
        Date date = new Date();
        int todayAsInt = DateUtility.getIntDate(date);

        return todayAsInt == 2;
    }

    public boolean isUpdateGridViewBooleanValueToday() {
        // If it is the first of the month change the tables
        Date date = new Date();
        int todayAsInt = DateUtility.getIntDate(date);

        return todayAsInt == 3;
    }

    public boolean updateGridViewBooleanValueToday() {

        getGlobalVariableEntryByName("tables_have_been_switched").setItemValue("FALSE");

        return Boolean.TRUE;
    }

    @Override
    public List<GlobalVariables> getRoomGridTableNames() {

        return entityManager.createNamedQuery("getRoomGridTableNames")
                .setParameter("param1", "this_month")
                .setParameter("param2", "next_month")
                .getResultList();
    }

    @Override
    public GlobalVariables getGlobalVariableEntryByName(String name) {

        List<GlobalVariables> newList = entityManager.createNamedQuery("getGlobalVariableEntryByName")
                .setParameter("param1", name)
                .getResultList();

        return newList.get(0);
    }

    public void clearLastMonthGridTable(String lastMonth) {
        // Clear the table that was use during the previous month
        // Check the number of days in the month and update occupants List
        // If last month had 30 days but nextMonth is 31 set the length +1

        Logger.safePrint("Clearing last months gridView data");

        List<RoomGridInterface> theRoomGrid;

        if (lastMonth.equalsIgnoreCase("room_grid")) {
            theRoomGrid = entityManager.createNamedQuery("getAllEntries").getResultList();
        } else {
            theRoomGrid = entityManager.createNamedQuery("getAllEntries_roomGridTwo").getResultList();
        }

        List<Integer> occupantsRoomGrid = new ArrayList<>();

        for (RoomGridInterface bed : theRoomGrid) {
            occupantsRoomGrid = bed.getBedOccupantsList();
            for (int i = 1; i < occupantsRoomGrid.size(); i++) {
                occupantsRoomGrid.remove(i);
                occupantsRoomGrid.add(i, 0);
            }
            if ((occupantsRoomGrid.size()) < (DateUtility.getNoOfDaysNextMonth(DateUtility.getNextMonth()) + 1)) {
                while ((occupantsRoomGrid.size()) < (DateUtility.getNoOfDaysNextMonth(DateUtility.getNextMonth()) + 1)) {
                    occupantsRoomGrid.add(0);
                }
            } else if ((occupantsRoomGrid.size()) > (DateUtility.getNoOfDaysNextMonth(DateUtility.getNextMonth()) + 1)) {
                while ((occupantsRoomGrid.size()) > (DateUtility.getNoOfDaysNextMonth(DateUtility.getNextMonth()) + 1)) {
                    occupantsRoomGrid.remove((DateUtility.getNoOfDaysNextMonth(DateUtility.getNextMonth()) + 1));
                }
            }
        }
    }

    @Override
    public String getThisMonthDayAndMonthHeading() {
        Date date = new Date();
        String pattern = "MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(date);
    }

    @Override
    public String getNextMonthDayAndMonthHeading() {
        Date date = new Date();
        Date nextMonth = DateUtility.getNextMonth();
        String pattern = "MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(nextMonth);
    }

    @Override
    public void addRate(String currencyName, float rate) {

        Logger.safePrint("Adding rate for " + currencyName + " rate: " + rate);
        ListOfCurrencies newCurrency = new ListOfCurrencies(currencyName, rate);
        entityManager.persist(newCurrency);
        setCurrencies(entityManager.createNamedQuery("getAllCurrencies").getResultList());
        setRatesForRooms();
    }
    
    @Override
    public void updateCurrency(String currencyName, float rate) {

        ListOfCurrencies theList = entityManager.find(ListOfCurrencies.class, currencyName);
        theList.setConversionRate(rate);
    }

    @Override
    public float getConversionRate(String currency) {

        List<Float> myList = entityManager.createNamedQuery("findConversaionRateByCurrency")
                .setParameter("currencyName", currency)
                .getResultList();

        return myList.get(0);
    }

    @Override
    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    @Override
    public void setDefaultCurrency(String currency) {
        // Update the datebase
        // reSet the current default currency then set a new default
        entityManager.createNamedQuery("reSetDefaultCurrency")
                .setParameter("param1", getDefaultCurrency())
                .executeUpdate();
        
        entityManager.createNamedQuery("setDefaultCurrency")
                .setParameter("param1", currency)
                .executeUpdate();
        
        // Update the bean
        this.defaultCurrency = currency;
    }

    public void getDefaultCurrencyAtStartUp() {
        List<String> newList = entityManager.createNamedQuery("getDefaultCurrency")
                .getResultList();

        this.defaultCurrency = newList.get(0);
    }

    // Populate rates array
    @Override
    public void setRatesForRooms() {

        LinkedHashMap<String, Float> results = new LinkedHashMap<>();

        List<Rooms> tempList = entityManager.createNamedQuery("getRooms").getResultList();
        for (Rooms r : tempList) {
            String roomName = r.getRoomName();
            float roomRate = r.getRate();
            results.put(roomName, roomRate);
        }
        setRoomRates(results);
    }

    @Override
    public float getRateByRoomName(String roomName) {
        return roomRates.get(roomName);
    }
}
