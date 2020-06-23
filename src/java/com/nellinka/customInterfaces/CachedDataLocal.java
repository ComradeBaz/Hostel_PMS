/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.customInterfaces;

import com.nellinka.entities.GlobalVariables;
import com.nellinka.entities.Rooms;
import com.nellinka.utilities.GuestExtrasObject;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ComradeBaz
 */
@Local
public interface CachedDataLocal {
 
    public void setRatesForRooms();
    
    public String getDefaultCurrency();
    
    public List<String> getCurrencies();
    
    public List<Rooms> getHostelRooms();
    
    public String getTableForThisMonth();
    
    public String getTableForNextMonth();
    
    public List<String> getAllCurrencies();
    
    public String getNextMonthDayAndMonthHeading();
    
    public String getThisMonthDayAndMonthHeading();
    
    public void setDefaultCurrency(String currency);
    
    public float getConversionRate(String currency);
    
    public float getRateByRoomName(String roomName);

    public boolean switchTableForThisMonthNextMonth();
    
    public void setHostelRooms(List<Rooms> hostelRooms);
    
    public void addRate(String currencyName, float rate);
    
    public boolean getIsRoomGridTablesHaveBeenSwitched();
    
    public List<GlobalVariables> getRoomGridTableNames();
    
    public void updateCurrency(String currency, float rate);
    
    public GlobalVariables getGlobalVariableEntryByName(String name);
}
