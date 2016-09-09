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
package com.nosoftskills.predcomposer.browser.scenarios;

import com.nosoftskills.predcomposer.browser.fixtures.CompletedMatchesInserter;
import com.nosoftskills.predcomposer.browser.pages.CompletedGamesPage;
import com.nosoftskills.predcomposer.browser.pages.HomePage;
import com.nosoftskills.predcomposer.browser.pages.LoginPage;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
@RunAsClient
public class CompletedMatchesScenarioTest {

    @Drone
    private WebDriver browser;

    @Page
    private CompletedGamesPage completedGamesPage;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Deployments.createDeployment().addClass(CompletedMatchesInserter.class);
    }

    @Before
    public void login(@InitialPage LoginPage loginPage) {
        loginPage.login("ivan", "ivan");
    }

    @Test
    public void shouldLoadCompletedMatchesPageFromHomePage(@InitialPage HomePage homePage) throws Exception {
        homePage.showCompletedMatches();
        completedGamesPage.assertIsLoaded();
    }
}
