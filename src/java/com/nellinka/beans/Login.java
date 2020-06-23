/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.beans;

import com.nellinka.interfaces.ManageApplicationLocal;
import com.nellinka.tools.Logger;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author ComradeBaz
 */
@SessionScoped
@ManagedBean
public class Login implements Serializable {

    private String userName;
    private String password;
    private boolean loggedIn;

    @EJB
    private ManageApplicationLocal manageApplication;

    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    public Login() {
        // no arg constructor
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public String doLogin() {

        if (manageApplication.isUserExists(getUserName())) {
            if (isCredentialsValid(userName, password)) {
                setLoggedIn(Boolean.TRUE);
                Logger.safePrint(getUserName() + " has logged in.");
                
                return navigationBean.redirectToIndex();
                
            } else {
                setLoggedIn(Boolean.FALSE);
                FacesMessage msg = new FacesMessage("ERROR", "Login error - check your username and password");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage("password", msg);
                Logger.safePrint("Login failed for " + getUserName());

                return navigationBean.toLogin();
            }
        }
        return navigationBean.toLoginFailureUserNoExist();
    }

    public boolean isCredentialsValid(String userName, String password) {
        return manageApplication.isCredentialsValid(userName, password);
    }
}
