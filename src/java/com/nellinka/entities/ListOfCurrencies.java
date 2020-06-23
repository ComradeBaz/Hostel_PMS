/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ComradeBaz
 */
@Entity
@Table(name = "list_of_currencies")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListOfCurrencies.findAll", query = "SELECT l FROM ListOfCurrencies l")
    , @NamedQuery(name = "ListOfCurrencies.findByCurrencyName", query = "SELECT l FROM ListOfCurrencies l WHERE l.currencyName = :currencyName")
    , @NamedQuery(name = "findConversaionRateByCurrency", query = "SELECT l.conversionRate FROM ListOfCurrencies l WHERE l.currencyName = :currencyName")
    , @NamedQuery(name = "ListOfCurrencies.findByConversionRate", query = "SELECT l FROM ListOfCurrencies l WHERE l.conversionRate = :conversionRate")
    , @NamedQuery(name = "getDefaultCurrency", query = "SELECT l.currencyName FROM ListOfCurrencies l WHERE l.isDefault = 1")
    , @NamedQuery(name = "setDefaultCurrency", query = "Update ListOfCurrencies l SET l.isDefault = 1 WHERE l.currencyName =:param1")
    , @NamedQuery(name = "reSetDefaultCurrency", query = "Update ListOfCurrencies l SET l.isDefault = 0 WHERE l.currencyName =:param1")
    , @NamedQuery(name = "getAllCurrencies", query = "SELECT l.currencyName FROM ListOfCurrencies l")})
public class ListOfCurrencies implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "currency_name")

    private String currencyName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "conversion_rate")
    private float conversionRate;

    @Column(name = "is_default")
    private int isDefault;

    public ListOfCurrencies() {
    }

    public ListOfCurrencies(String currencyName) {
        this.currencyName = currencyName;
    }

    public ListOfCurrencies(String currencyName, float conversionRate) {
        this.currencyName = currencyName;
        this.conversionRate = conversionRate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public float getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(float conversionRate) {
        this.conversionRate = conversionRate;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currencyName != null ? currencyName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListOfCurrencies)) {
            return false;
        }
        ListOfCurrencies other = (ListOfCurrencies) object;
        if ((this.currencyName == null && other.currencyName != null) || (this.currencyName != null && !this.currencyName.equals(other.currencyName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nellinka.beans.ListOfCurrencies[ currencyName=" + currencyName + " ]";
    }

}
