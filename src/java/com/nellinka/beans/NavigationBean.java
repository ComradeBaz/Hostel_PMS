/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.beans;

import com.nellinka.tools.Logger;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author ComradeBaz
 */
@SessionScoped
//@ManagedBean
@Named
public class NavigationBean implements Serializable {

    private static final long serialVersionUID = 1520318172495977648L;

    public String redirectToLoginPage() {
        return "/login.xhtml?faces-redirect=true";
    }

    public String toLogin() {
        return "/login.xhtml?faces-redirect=true";
    }

    public String logOut() {
        return "/logout.xhtml?faces-redirect=true";
    }
    
    public String goToSignUp() {
        return "/signup.xhtml?faces-redirect=true";
    }

    public String redirectToIndex() {
        return "/secured/index.xhtml?faces-redirect=true";
    }
    
    public String toSignUpConfirmation() {
        return "signupconfirmation.xhtml?faces-redirect=true";
    }

    public String toSignUpFailureUserExists() {
        return "signupfailureuserexists.xhtml?faces-redirect=true";
    }
    public String toLoginFailureUserNoExist() {
        return "loginfailureusernoexist.xhtml?faces-redirect=true";
    }
    
    public String toIndex() {
        return "/secured/index.xhtml?faces-redirect=true";
    }
    
    public String toConfirmDelete() {
        return "/secured/xconfirmdelete.xhtml?faces-redirect=true";
    }
    
    public String toDeleteConfirmation() {
        return "/secured/xdeleteconfirmation.xhtml?faces-redirect=true";
    }

    public String cancelSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //login.setLoggedIn(Boolean.FALSE);
        Logger.safePrint("Invalidating Session");
        return "/logout.xhtml?faces-redirect=true?faces-redirect=true";
    }

    public String cancelSessionGoToSaveCheckin() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //login.setLoggedIn(Boolean.FALSE);
        Logger.safePrint("Invalidating Session");
        return "/secured/xsaveguest.xhtml?faces-redirect=true";
    }

    public String showCheckedInGuest() {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //login.setLoggedIn(Boolean.FALSE);
        //Logger.safePrint("Invalidating Session");
        return "/secured/yshowcheckedinguest?faces-redirect=true";
    }

    public String showCheckedGuestConfirmation() {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //login.setLoggedIn(Boolean.FALSE);
        Logger.safePrint("Invalidating Session");
        return "/secured/zconfirmation?faces-redirect=true";
    }

    public String goToSaveCheckIn() {
        return "/secured/xsavecheckin.xhtml?faces-redirect=true";
    }
    
    // Go to the screen that prompts for guest name, lastName, country, id
    public String goToCheckIn() {
        return "/secured/checkin.xhtml?faces-redirect=true";
    }
    
    public String redirectToCheckIn() {
        return "/secured/checkin.xhtml?faces-redirect=true";
    }

    public String endSessionFromCheckOutGuest() {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //login.setLoggedIn(Boolean.FALSE);
        Logger.safePrint("Invalidating Session");
        return "/secured/zcheckoutguest.xhtml?faces-redirect=true";
    }

    public String cancelSessionGoToPendingCheckins() {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //login.setLoggedIn(Boolean.FALSE);
        Logger.safePrint("Invalidating Session");
        return "/secured/ycheckinguest.xhtml?faces-redirect=true";
    }

    public String cancelSessionGoToDashboard() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //login.setLoggedIn(Boolean.FALSE);
        Logger.safePrint("Invalidating Session");
        return "/secured/wgridview.xhtml?faces-redirect=true";
    }

    public String goToDashboardCancelSession() {
        //.FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //login.setLoggedIn(Boolean.FALSE);
        Logger.safePrint("Invalidating Session");
        return "/secured/wgridview.xhtml?faces-redirect=true";
    }

    public String cancelCheckIn() {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //login.setLoggedIn(Boolean.FALSE);
        Logger.safePrint("Invalidating Session");
        return "/secured/xsaveguest.xhtml?faces-redirect=true";
    }
}
