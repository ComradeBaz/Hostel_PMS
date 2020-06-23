/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tests;

import com.nellinka.entities.Rooms;
import com.nellinka.tools.Logger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author ComradeBaz
 */
@Named
@RequestScoped
public class TestExtrasBean {
    
    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext
    private EntityManager entityManager;
    
    private List<TestExtras> testExtras;
    private ArrayList<String> guestExtras;

    public List<TestExtras> getTestExtras() {
        return testExtras;
    }
    public void setTestExtras(List<TestExtras> testExtras) {
        this.testExtras = testExtras;
    }    
    public ArrayList<String> getGuestExtras() {
        return guestExtras;
    }
    public void setGuestExtras(ArrayList<String> guestExtras) {
        this.guestExtras = guestExtras;
        //Logger.safePrint(guestExtras.toString());
        for(String s: guestExtras)
            Logger.safePrint(s);
        // For each string write the string to a new extras table along with the 
        // guest_id
        // something like newExtrasTable myNewExtrasTable = newExtrasTable(s, guest_Id);
    }
    
    public List<TestExtras> getExtras() {
        
        List<TestExtras> tempList = new ArrayList<>();
        
        try {
            Query query = entityManager.createQuery(
                    "Select t from TestExtras t");
            if (query.getResultList() != null) {
                setTestExtras(query.getResultList());
            }
            tempList = query.getResultList();

            //setRoomName("White");
        } catch (Exception e) {

            // System.out.println(e.getStackTrace());
            Logger.safePrint("getRooms() exception: " + e.getMessage());
        }
        
        return tempList;
    }
}
