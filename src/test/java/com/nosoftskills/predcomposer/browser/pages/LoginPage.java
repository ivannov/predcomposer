package com.nosoftskills.predcomposer.browser.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivan St. Ivanov
 */
@Location("login.jsf")
public class LoginPage {

    @FindBy(id = "loginForm:userName")
    private WebElement userName;

    @FindBy(id = "loginForm:password")
    private WebElement password;

    @FindBy(id = "loginForm:login")
    private WebElement loginButton;

    @FindBy
    private WebElement loginErrorMessage;

    public void login(String userName, String password) {
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        guardHttp(loginButton).click();
    }

    public void assertWrongCredentialsMessage() {
        assertTrue(loginErrorMessage.isDisplayed());
    }

    public void assertIsLoaded() {
        assertTrue(this.userName.isDisplayed());
    }
}
