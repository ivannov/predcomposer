package com.nosoftskills.predcomposer.browser.scenarios;

import com.nosoftskills.predcomposer.competition.CompetitionsService;
import com.nosoftskills.predcomposer.game.GamesService;
import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.prediction.PredictionsService;
import com.nosoftskills.predcomposer.rankings.Score;
import com.nosoftskills.predcomposer.session.UserContext;
import com.nosoftskills.predcomposer.test.TestDataInserter;
import com.nosoftskills.predcomposer.user.UsersService;
import com.nosoftskills.predcomposer.web.LoggedUserFilter;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.io.File;

/**
 * @author Ivan St. Ivanov
 */
public class Deployments {

    public static final String WEBAPP_DIR = "src/main/webapp";
    public static final String WEB_INF_DIR = WEBAPP_DIR + "/WEB-INF";

    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "predcomposer-test.war")
                .addPackages(true, CompetitionsService.class.getPackage(),
                        GamesService.class.getPackage(), Competition.class.getPackage(),
                        PredictionsService.class.getPackage(), Score.class.getPackage(),
                        UserContext.class.getPackage(), TestDataInserter.class.getPackage(),
                        UsersService.class.getPackage(), LoggedUserFilter.class.getPackage())
                .addAsResource(new File("src/test/resources/META-INF/persistence-scenarios.xml"),
                        "META-INF/persistence.xml")
                .merge(getWebPagesArchive(), "/", Filters.include(".*\\.xhtml$"))
                .addAsWebResource(new File(WEBAPP_DIR, "login.xhtml"))
                .addAsWebResource(new File(WEBAPP_DIR, "home.xhtml"))
                .addAsWebInfResource(new File(WEB_INF_DIR, "faces-config.xml"))
                .setWebXML(new File(WEB_INF_DIR, "web.xml"));
        System.out.println(webArchive.toString());
        return webArchive;
    }

    private static GenericArchive getWebPagesArchive() {
        return ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                .importDirectory(WEBAPP_DIR).as(GenericArchive.class);
    }
}
