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
import javax.persistence.Table;

/**
 *
 * @author David O'Brien
 */

@Entity
@Table(name="guest_status")
public class GuestStatus implements Serializable{
    @Id
    @Column(name="status")
    private int status;
    
    @Column(name="status_description")
    private String statusDescription;
    
    public void setStatus(int sts) {
        this.status = sts;
    }
    public int getStatus() {
        return status;
    }
    public void setStatusDescription(String desc) {
        this.statusDescription = desc;
    }
    public String getStatusDescription() {
        return statusDescription;
    }
}
