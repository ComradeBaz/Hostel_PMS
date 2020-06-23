/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.sessionBeans;

import com.nellinka.beans.NavigationBean;
import com.nellinka.entities.Users;
import com.nellinka.interfaces.ManageApplicationLocal;
import com.nellinka.tools.Logger;
import com.nellinka.utilities.PBKDF2Hasher;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ComradeBaz
 */
@Stateless
public class ManageApplication implements ManageApplicationLocal {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private NavigationBean navigationBean;

    @Override
    public String doSignUp(String emailAddress, String password) {
        
        // Does the user exist?
        if(isUserExists(emailAddress))
            return navigationBean.toSignUpFailureUserExists();

        Users user = new Users(emailAddress, password);
        entityManager.persist(user);

        return navigationBean.toSignUpConfirmation();
    }
    
    @Override
    public boolean isUserExists(String emailAddress) {
        
        return (entityManager.createNamedQuery("Users.findByEmailAddress")
                .setParameter("emailAddress", emailAddress)
                .getResultList()
                .size() > 0);
    }
    
    @Override
    public boolean isCredentialsValid(String emailAddress, String password) {
        
        List<String> aList = entityManager.createNamedQuery("Users.findPasswordByEmailAddress")
                .setParameter("emailAddress", emailAddress)
                .getResultList();
        
        String token = aList.get(0);
        PBKDF2Hasher hasher = new PBKDF2Hasher();
        return hasher.checkPassword(password.toCharArray(), token);
    }
}
