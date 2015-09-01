package com.nosoftskills.predcomposer.user;

import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author Ivan St. Ivanov
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UserManagementBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserContext userContext;

    public boolean validateUser(String userName, String password) {
        TypedQuery<User> validateUserQuery = entityManager.createNamedQuery(
                "findUserByNameAndPassword", User.class);
        validateUserQuery.setParameter("userName", userName);
        validateUserQuery.setParameter("password", password);
        try {
            User loggedUser = validateUserQuery.getSingleResult();
            userContext.setLoggedUser(loggedUser);
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }

    public void logout() {
        userContext.setLoggedUser(null);
    }
}
