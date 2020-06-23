/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.beans;

import com.nellinka.customInterfaces.CachedDataLocal;
import com.nellinka.entities.Rooms;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author ComradeBaz
 */
@Named
@RequestScoped
public class StaticData {
    
    @EJB
    private CachedDataLocal cachedData;

    public List<String> getCurrencies() {
        
        return cachedData.getCurrencies();
    }
    
    public List<String> getAllCurrencies() {
        
        return cachedData.getAllCurrencies();
    }
    
    public String getDefaultCurrency() {
        
        return cachedData.getDefaultCurrency();
    }
    
    public void setDefaultCurrency(String currency) {
        cachedData.setDefaultCurrency(currency);
    }
    
    public float getRateByRoomName(String roomName) {
        
        return cachedData.getRateByRoomName(roomName);
    }
    
    public List<Rooms> getHostelRooms() {
        
        return cachedData.getHostelRooms();
    }
}
