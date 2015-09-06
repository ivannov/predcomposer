package com.nosoftskills.predcomposer.alternatives;

import com.nosoftskills.predcomposer.model.Competition;
import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;

import static com.nosoftskills.predcomposer.common.TestData.user1;
/**
 * @author Ivan St. Ivanov
 */
@Alternative
@SessionScoped
public class UserContextAlternative extends UserContext {

    private Competition defaultCompetition = new Competition("Champions League", "Ils sont les meilleurs");

    @Override
    public User getLoggedUser() {
        return user1;
    }

    @Override public Competition getSelectedCompetition() {
        return defaultCompetition;
    }
}
