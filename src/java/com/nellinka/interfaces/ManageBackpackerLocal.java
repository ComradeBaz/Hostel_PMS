/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.interfaces;

import javax.ejb.Local;

/**
 *
 * @author ComradeBaz
 */
@Local
public interface ManageBackpackerLocal {
    
    public String saveGuest();
    
    public String doCheckIn();
    
    public String checkInGuest();
}
