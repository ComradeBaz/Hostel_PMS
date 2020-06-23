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

/**
 *
 * @author ComradeBaz
 */
@Entity
@Table(name = "all_currencies")
@NamedQueries({
    @NamedQuery(name = "AllCurrencies.findAll", query = "SELECT a FROM AllCurrencies a")
    , @NamedQuery(name = "AllCurrencies.findByCountry", query = "SELECT a FROM AllCurrencies a WHERE a.country = :country")
    , @NamedQuery(name = "AllCurrencies.findByCurrency", query = "SELECT a FROM AllCurrencies a WHERE a.currency = :currency")
    , @NamedQuery(name = "getAllCurrencyCodes", query = "SELECT a.code FROM AllCurrencies a")
    , @NamedQuery(name = "AllCurrencies.findByCode", query = "SELECT a FROM AllCurrencies a WHERE a.code = :code")
    , @NamedQuery(name = "AllCurrencies.findBySymbol", query = "SELECT a FROM AllCurrencies a WHERE a.symbol = :symbol")})
public class AllCurrencies implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "country")
    private String country;

    @Size(max = 100)
    @Column(name = "currency")
    private String currency;

    @Size(max = 100)
    @Column(name = "code")
    private String code;

    @Size(max = 100)
    @Column(name = "symbol")
    private String symbol;

    public AllCurrencies() {
    }

    public AllCurrencies(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (country != null ? country.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AllCurrencies)) {
            return false;
        }
        AllCurrencies other = (AllCurrencies) object;
        if ((this.country == null && other.country != null) || (this.country != null && !this.country.equals(other.country))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nellinka.entities.AllCurrencies[ country=" + country + " ]";
    }

}
