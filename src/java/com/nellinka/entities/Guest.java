/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.entities;

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
@Table(name = "guest_list")
public class Guest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "guest_id")
    private int guestId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "country")
    private String country;

    @Column(name = "status")
    private int status;

    @OneToOne(mappedBy = "guest")
    private CheckIn checkIn;

    public Guest() {
        //no arg constructor - guest is saved to generate a unique guestId
        // guest data is saved to checkedInGuests table
    }

    // Constructor for guest with minimum details
    public Guest(String fName, String lName) {
        this.firstName = fName;
        this.lastName = lName;
    }

    // If idNumber is known country is known
    public Guest(String fName, String lName, String idNumber, String country) {
        this.firstName = fName;
        this.lastName = lName;
        this.idNumber = idNumber;
        this.country = country;
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

    public void setIdNumber(String id) {
        this.idNumber = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setStatus(int sts) {
        this.status = sts;
    }

    public int getStatus() {
        return status;
    }

    public CheckIn getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        this.checkIn = checkIn;
    }

    @Override
    public String toString() {
        if (lastName != null) {
            return "GuestID: " + guestId + " First Name: " + firstName + " Last Name: "
                    + lastName + " Country: " + country;
        } else {
            return firstName;
        }
    }
}
