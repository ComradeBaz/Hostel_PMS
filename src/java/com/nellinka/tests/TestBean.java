/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tests;

import com.nellinka.entities.Guest;
import java.util.Date;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author David O'Brien
 */

@Named
@ApplicationScoped
public class TestBean {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;
    
    private Guest newGuest;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String country;
    private int status;
    private int room;
    private int bedNumber;
    private Date checkInDate;
    private Date checkOutDate;
    private float rate;
    
    public TestBean() {
        // Default Constructor
    }
    
    public Guest getNewGuest() {
        return newGuest;
    }
    public void setNewGuest(Guest newGuest) {
        this.newGuest = newGuest;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getIdNumber() {
        return idNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public int getRoom() {
        return room;
    }
    public void setRoom(int room) {
        this.room = room;
    }
    public int getBedNumber() {
        return bedNumber;
    }
    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }   
    
    public String updateDatabase() {
        String returnVal = "confirmation";
        
        //newGuest = new Guest(firstName, lastName, idNumber, country, 0);
        newGuest = new Guest(); 
        try {
            userTransaction.begin();
            entityManager.persist(newGuest);
            userTransaction.commit();
        } catch (HeuristicMixedException |
                HeuristicRollbackException |
                IllegalStateException |
                NotSupportedException |
                RollbackException |
                SecurityException |
                SystemException e) {
            
            returnVal = "Error";
            //e.printStackTrace();
        }
        
        return returnVal;
    }
    public String checkIn() {
        return "checkInForm";
    }
}
