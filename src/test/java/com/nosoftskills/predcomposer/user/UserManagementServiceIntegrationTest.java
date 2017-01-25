package com.nosoftskills.predcomposer.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nosoftskills.predcomposer.alternatives.UserContextAlternative;
import com.nosoftskills.predcomposer.common.TestData;
import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.session.UserContext;

/**
 * @author Irina Marudina
 */
@RunWith(Arquillian.class)
public class UserManagementServiceIntegrationTest {


    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "predcomposer-test.war")
                .addClass(UserManagementService.class)
                .addClass(UsersService.class)
                .addClasses(UserContextAlternative.class, UserContext.class, TestData.class,
                        PasswordHashUtil.class)
                .addPackage(User.class.getPackage())
                .addAsResource(new File("src/test/resources/META-INF/persistence-scenarios.xml"),
                        "META-INF/persistence.xml")
                .addAsWebInfResource("test-beans.xml", "beans.xml");
        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    @Inject
    public UserManagementService userManagementService;

    @Inject
    public UsersService usersService;
    
    @Before
    public void setUp() {
    	User user1 = new User("imarudina", "irina-password", "public@marudina.net");
    	usersService.storeUser(user1);
    }

    @Test
    public void shouldValidateUserWithCorrectPassword() throws Exception {
    	User user = userManagementService.validateUser("imarudina", "irina-password");
        assertNotNull(user);
        assertEquals("imarudina", user.getUserName());
    }

    @Test
    public void shouldFailOnWrongPassword() throws Exception {
    	User user = userManagementService.validateUser("imarudina", "blah");
    	assertNull(user);
    }

    @Test
    public void shouldFailOnUnknownUser() throws Exception {
    	User user = userManagementService.validateUser("unknown_user", "blah");
    	assertNull(user);
    }
}
