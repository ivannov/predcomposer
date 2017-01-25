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
public class UsersService {

    @PersistenceContext
    private EntityManager entityManager;
    
    public UsersService() {
    	// no init needed
    }
    
    public UsersService(EntityManager entityManager) {
    	this.entityManager = entityManager;
    }

    public List<User> getAllUsers() {
        return entityManager.createNamedQuery("getAllUsers", User.class).getResultList();
    }

    public User storeUser(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }
}
