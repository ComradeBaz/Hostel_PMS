/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tests;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author David O'Brien
 */

@Entity
@Table(name="test_guest_list")
public class TestGuest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="guest_id")
    private int guestId;
    
    @Column(name="first_name")
    private String firstName;
    
    @Column(name="last_name")
    private String lastName;
    
    @Column(name="testextras")
    private String extras;
    
    public TestGuest() {
        //no arg constructor
    }
    // Constructor for guest with minimum details
    public TestGuest(String fName, String lName) {
        this.firstName = fName;
        this.lastName = lName;
    } 
    public void setGuestId(int id) {
        this.guestId = id;
    }
    public int getGuestId() {
        return guestId;
    }
    public void setFirstName(String fname) {
        this.firstName = fname;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setLastName(String lname) {
        this.lastName = lname;
    }
    public String getLastName() {
        return lastName;
    }
}
