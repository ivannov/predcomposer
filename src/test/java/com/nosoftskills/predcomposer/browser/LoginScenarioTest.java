package com.nosoftskills.predcomposer.browser;

import com.nosoftskills.predcomposer.browser.pages.HomePage;
import com.nosoftskills.predcomposer.browser.pages.LoginPage;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

/**
 * @author Ivan St. Ivanov
 */
@RunWith(Arquillian.class)
@RunAsClient
public class LoginScenarioTest {

    @Drone
    private WebDriver browser;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Deployments.createDeployment();
    }

    @Page
    private HomePage homePage;

    @Test
    public void successfulLoginWithAdminUser(@InitialPage LoginPage loginPage) {
        loginPage.login("ivan", "ivan");
        homePage.assertGreetingMessage("Ivan");
        homePage.assertGameFormVisible(true);
    }

    @Test
    public void successfulLoginWithNonAdminUser(@InitialPage LoginPage loginPage) {
        loginPage.login("koko", "koko");
        System.out.println(browser.getPageSource());
        homePage.assertGreetingMessage("Koko");
        homePage.assertGameFormVisible(false);
    }

    @Test
    public void unsuccessfulLogin(@InitialPage LoginPage loginPage) {
        loginPage.login("ivan", "");
        loginPage.assertWrongCredentialsMessage();
    }
}
