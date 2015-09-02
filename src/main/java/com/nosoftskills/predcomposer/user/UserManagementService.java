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
public class UserManagementService {

    @PersistenceContext
    private EntityManager entityManager;

    public User validateUser(String userName, String password) {
        TypedQuery<User> validateUserQuery = entityManager.createNamedQuery(
                "findUserByNameAndPassword", User.class);
        validateUserQuery.setParameter("userName", userName);
        validateUserQuery.setParameter("password", password);
        try {
            return validateUserQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
