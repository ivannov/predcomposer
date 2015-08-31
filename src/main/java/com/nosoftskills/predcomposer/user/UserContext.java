package com.nosoftskills.predcomposer.user;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.test.TestDataInserter;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * @author Ivan St. Ivanov
 */
@SessionScoped
public class UserContext implements Serializable {

    public Competition getSelectedCompetition() {
        return TestDataInserter.DEFAULT_COMPETITION;
    }
}
