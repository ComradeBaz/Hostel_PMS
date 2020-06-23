/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.entities;

import java.io.Serializable;
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
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
    , @NamedQuery(name = "Users.findByEmailAddress", query = "SELECT u FROM Users u WHERE u.emailAddress = :emailAddress")
        , @NamedQuery(name = "Users.findPasswordByEmailAddress", query = "SELECT u.password FROM Users u WHERE u.emailAddress = :emailAddress")
    , @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email_address")
    private String emailAddress;
    
    @NotNull
    @Size(min = 1, max = 127)
    @Column(name = "password")
    private String password;

    public Users() {
    }

    public Users(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Users(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emailAddress != null ? emailAddress.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.emailAddress == null && other.emailAddress != null) || (this.emailAddress != null && !this.emailAddress.equals(other.emailAddress))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nellinka.entities.Users[ emailAddress=" + emailAddress + " ]";
    }
    
}
