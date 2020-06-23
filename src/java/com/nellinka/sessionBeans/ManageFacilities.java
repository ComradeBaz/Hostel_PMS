/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.sessionBeans;

import com.nellinka.customInterfaces.ManageHostelLocal;
import com.nellinka.entities.Rooms;
import com.nellinka.interfaces.ManageFacilitiesLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author ComradeBaz
 */
@Singleton
@Startup
public class ManageFacilities implements ManageFacilitiesLocal {

    @EJB
    private ManageHostelLocal manageHostel;
    
    //private List<Integer> freeBeds;
    private List<Rooms> hostelRooms;
    
    public ManageFacilities() {
        // no arg constructor
    }

    @PostConstruct
    public void initialiseBean() {
        setHostelRooms(manageHostel.getHostelRooms());
    }
    /*    @Override
    public List<Integer> getFreeBeds() {
    return freeBeds;
    }
    
    @Override
    public void setFreeBeds(List<Integer> freeBeds) {
    this.freeBeds = freeBeds;
    }*/
    public List<Rooms> getHostelRooms() {
        return hostelRooms;
    }
    @Override
    public void setHostelRooms(List<Rooms> hostelRooms) {
        this.hostelRooms = hostelRooms;
    }   
}
