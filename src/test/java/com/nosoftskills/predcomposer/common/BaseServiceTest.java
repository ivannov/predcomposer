package com.nosoftskills.predcomposer.common;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.nosoftskills.predcomposer.common.TestData.*;

/**
 * @author Ivan St. Ivanov
 */
public abstract class BaseServiceTest {

    protected static EntityManager entityManager;

    @BeforeClass
    public static void setupTestObjects() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("predcomposer-test");
        entityManager = emf.createEntityManager();
    }

    @Before
    public void setUp() throws Exception {
        setupClassUnderTest();
        entityManager.getTransaction().begin();
        initialize();
        insertTestData();
        entityManager.flush();
    }

    private void insertTestData() {
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(competition);
    }

    @After
    public void tearDown() throws Exception {
        entityManager.getTransaction().rollback();
    }

    @AfterClass
    public static void closeEntityManager() throws Exception {
        entityManager.close();
    }

    protected abstract void setupClassUnderTest();
}
