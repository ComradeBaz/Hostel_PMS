/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ComradeBaz
 */
@ManagedBean(name = "checkedInGuests", eager = true)
@NamedQueries({
    @NamedQuery(name = "getNumberOfGuestsByRoomToday",
            query = "SELECT count(cIG) FROM CheckedInGuests cIG WHERE cIG.roomName =:param1 "
            + "AND cIG.checkOutDate > :param2 AND cIG.checkInDate <= :param2 "
            + "AND cIG.status = :param3 OR cIG.status = :param4")
    ,
        @NamedQuery(name = "getNumberOfGuestsByRoomTomorrow",
            query = "SELECT count(cIG) FROM CheckedInGuests cIG WHERE cIG.roomName =:param1 "
            + "AND cIG.checkOutDate > :param2 AND cIG.checkInDate <= :param3 "
            + "AND cIG.status = :param4 OR cIG.status = :param5")
    ,
        @NamedQuery(name = "getSavedGuestsCheckedIn",
            query = "SELECT cIG FROM CheckedInGuests cIG WHERE cIG.status"
            + "=:param1")
    ,
        @NamedQuery(name = "getSavedGuestsCheckedInToday",
            query = "SELECT cIG FROM CheckedInGuests cIG WHERE "
            + "cIG.checkOutDate > :param1 AND cIG.checkInDate <= :param2 "
            + "AND cIG.status = :param3 OR cIG.status = :param4")
    ,
        @NamedQuery(name = "getSavedGuestsNotCheckedIn",
            query = "SELECT cIG FROM CheckedInGuests cIG WHERE cIG.status"
            + "=:param1")
    ,       
        @NamedQuery(name = "updateMovedBedStatusOnCheckOut",
            query = "UPDATE CheckedInGuests cIG SET cIG.status=2 WHERE cIG.guestId=:param1 AND cIG.status=:param2")
    ,
        @NamedQuery(name = "getSavedGuestsMovedBed",
            query = "SELECT cIG FROM CheckedInGuests cIG WHERE cIG.status"
            + "=:param1")
    ,
        @NamedQuery(name = "getGuestByReservationId",
            query = "SELECT g FROM CheckedInGuests g WHERE g.reservationId"
            + "=:param1")
    ,
        @NamedQuery(name = "getGuestByGuestId",
            query = "SELECT g FROM CheckedInGuests g WHERE g.guestId"
            + "=:param1")
    ,
        @NamedQuery(name = "getMovedBedByGuestId",
            query = "SELECT c FROM CheckedInGuests c WHERE c.guestId"
            + "=:param1 AND c.status" + "=:param2")
    ,
        @NamedQuery(name = "getGuestByReservationAndGuestId",
            query = "SELECT g FROM CheckedInGuests g WHERE g.reservationId"
            + "=:param1 AND g.guestId=:param2")
    ,
        @NamedQuery(name = "updateStatusForMovedBedReservation",
            query = "UPDATE CheckedInGuests cIG SET cIG.status=3 WHERE cIG.guestId=:param1 "
            + "AND cIG.reservationId =:param2")
    ,
        @NamedQuery(name = "getReservationIdByGuestIdRoomNameBedNumber",
            query = "SELECT g.reservationId FROM CheckedInGuests g WHERE g.guestId"
            + "=:param1 AND g.roomName=:param2 AND g.bedNumber=:param3")
    ,
        @NamedQuery(name = "getCheckedInGuestByGuestIdRoomNameBedNumber",
            query = "SELECT g FROM CheckedInGuests g WHERE g.guestId"
            + "=:param1 AND g.roomName=:param2 AND g.bedNumber=:param3")
    ,
        @NamedQuery(name = "getGuestsCheckingOutToday",
            query = "SELECT g FROM CheckedInGuests g WHERE g.checkOutDate"
            + "=:param1 AND g.status=:param2")
    ,
        @NamedQuery(name = "getAllCountries",
            query = "SELECT DISTINCT g.country FROM CheckedInGuests g")
})
@Entity
@Table(name = "checked_in_guests")
public class CheckedInGuests implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "reservation_id")
    private int reservationId;

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

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "bed_number")
    private int bedNumber;

    @Column(name = "check_in_date")
    @Temporal(TemporalType.DATE)
    private Date checkInDate;

    @Column(name = "check_out_date")
    @Temporal(TemporalType.DATE)
    private Date checkOutDate;

    @Column(name = "status")
    private int status;

    @Column(name = "extras")
    ArrayList<String> extras;

    @Column(name = "rate")
    private float rate;

    @Column(name = "amount_paid")
    private float amountPaid;

    @Column(name = "currency")
    private String currency;
    
    @Column(name = "discount")
    private float discount;

    @Column(name = "note")
    private String note;

    public CheckedInGuests() {
        // No arg constructor
    }

    public CheckedInGuests(int guestId, String firstName,
            String lastName, String idNumber, String country, String roomName,
            int bedNumber, Date checkInDate, Date checkOutDate, int status, ArrayList<String> extras, float rate) {
        this.guestId = guestId;

        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.country = country;
        this.roomName = roomName;
        this.bedNumber = bedNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.extras = extras;
        this.rate = rate;
    }

    public CheckedInGuests(int guestId, String firstName,
            String lastName, String idNumber, String country, String roomName, int bedNumber, Date checkInDate,
            Date checkOutDate, int status, ArrayList<String> extras, float rate, String currency) {

        this.guestId = guestId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.country = country;
        this.roomName = roomName;
        this.bedNumber = bedNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.extras = extras;
        this.rate = rate;
        this.currency = currency;
    }

    public CheckedInGuests(int guestId, String firstName,
            String lastName, String idNumber, String country, String roomName,
            int bedNumber, Date checkInDate, Date checkOutDate, int status, float rate) {
        this.guestId = guestId;

        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.country = country;
        this.roomName = roomName;
        this.bedNumber = bedNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.extras = null;
        this.rate = rate;
    }

    // Constructor to save a guest from saveguest.xhtml saveguest-flow
    public CheckedInGuests(int guestId, String firstName, String lastName, String idNumber, String country, String currency) {
        this.guestId = guestId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.country = country;
        this.currency = currency;
    }

    // Constructor to save a guest from saveguest.xhtml saveguest-flow
    // Add empty string as placeholders for id and country
    // No need to require entry of info from web page
    // Info will be updated when the check-in is completed
    public CheckedInGuests(int guestId, String firstName, String lastName) {
        this.guestId = guestId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = " ";
        this.country = " ";
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<String> getExtras() {
        return extras;
    }

    public void setExtras(ArrayList<String> extras) {
        this.extras = extras;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "CheckedInGuests{" + "guestId=" + guestId + ", firstName=" + firstName
                + ", lastName=" + lastName + ", idNumber=" + idNumber + ", country="
                + country + ", roomName=" + roomName + ", bedNumber=" + bedNumber
                + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate
                + ", status=" + status + ", rate=" + rate + ",extras=" + extras + "}";
    }
}
