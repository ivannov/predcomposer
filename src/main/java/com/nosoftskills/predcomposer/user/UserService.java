package com.nosoftskills.predcomposer.user;

import com.nosoftskills.predcomposer.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAllUsers() {
        return entityManager.createNamedQuery("getAllUsers", User.class).getResultList();
    }
}
