package com.nosoftskills.predcomposer.session;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.test.TestDataInserter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Ivan St. Ivanov
 */
@SessionScoped
@Named("userContext")
public class UserContext implements Serializable {

    public Competition getSelectedCompetition() {
        return TestDataInserter.DEFAULT_COMPETITION;
    }

    private User loggedUser;

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
