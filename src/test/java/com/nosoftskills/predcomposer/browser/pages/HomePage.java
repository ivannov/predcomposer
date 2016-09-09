package com.nosoftskills.predcomposer.browser.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Ivan St. Ivanov
 */
@Location("home.jsf")
public class HomePage {

    @FindBy
    private WebElement greeting;

    @FindBy
    private WebElement addNewGameForm;

    @FindBy
    private WebElement completedGamesLink;

    public void showCompletedMatches() {
        guardHttp(completedGamesLink).click();
    }

    public void assertGreetingMessage(String expectedUserName) {
        assertEquals("Hello, " + expectedUserName, greeting.getText());
    }

    public void assertGameFormVisible(boolean shouldBeVisible) {
        try {
            assertEquals(shouldBeVisible, addNewGameForm.isDisplayed());
        } catch (NoSuchElementException nsee) {
            if (shouldBeVisible) {
                fail();
            }
        }

    }
}
