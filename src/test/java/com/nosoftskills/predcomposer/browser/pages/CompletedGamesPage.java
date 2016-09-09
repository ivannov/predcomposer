/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nosoftskills.predcomposer.browser.pages;

import com.nosoftskills.predcomposer.model.Game;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Location("completedGames.jsf")
public class CompletedGamesPage {

    @FindBy
    private WebElement matchesTable;

    public int getDisplayedMatchesNumber() {
        return matchesTable.findElements(By.tagName("tr")).size() - 1; // the first tr is header
    }

    public void assertIsLoaded() {
        assertTrue(this.matchesTable.isDisplayed());
    }
}
