/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.beans;

import com.nellinka.customInterfaces.CachedDataLocal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ComradeBaz
 */
@Named
@RequestScoped
public class Settings {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @EJB
    private CachedDataLocal cachedData;
    
    private String name;
    private float conversionRate;
        
    public Settings() {
        // No arg constructor
    }

    public float getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(float conversionRate) {
        this.conversionRate = conversionRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
    public String addCurrency() {
        
        cachedData.addRate(name, conversionRate);
        
        return "addcurrencysuccess";
    }
    public String updateCurrency() {
        
        cachedData.updateCurrency(getName(), getConversionRate());
        
        return "updatecurrencysuccess";
    }
}
