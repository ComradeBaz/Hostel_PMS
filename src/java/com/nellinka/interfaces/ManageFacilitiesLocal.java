/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.interfaces;

import com.nellinka.entities.Rooms;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ComradeBaz
 */
@Local
public interface ManageFacilitiesLocal {
    
    public void setHostelRooms(List<Rooms> hostelRooms);

}
