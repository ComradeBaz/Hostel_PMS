/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.beans;

import com.nellinka.interfaces.ManageApplicationLocal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.nellinka.utilities.PBKDF2Hasher;

/**
 *
 * @author ComradeBaz
 */
@Named
@RequestScoped
public class SignUp {
    
    @Inject
    private NavigationBean navigationBean;
    
    @EJB
    private ManageApplicationLocal manageApplication;
    
    private String emailAddress;
    private String password;
    
    public SignUp () {
        // no arg constructor
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
    
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }
    
    public String doSignUp() {
        PBKDF2Hasher hasher = new PBKDF2Hasher();
        setPassword(hasher.hash(getPassword().toCharArray()));
        
        return manageApplication.doSignUp(getEmailAddress(), getPassword());
    }
}
