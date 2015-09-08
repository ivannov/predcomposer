package com.nosoftskills.predcomposer.score;

import com.nosoftskills.predcomposer.alternatives.PredictionsServiceAlternative;
import com.nosoftskills.predcomposer.alternatives.UserContextAlternative;
import com.nosoftskills.predcomposer.alternatives.UsersServiceAlternative;
import com.nosoftskills.predcomposer.common.TestData;
import com.nosoftskills.predcomposer.rankings.ViewRankingsBean;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.ContextController;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * @author Ivan St. Ivanov
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({
        PredictionsServiceAlternative.class, UserContextAlternative.class, UsersServiceAlternative.class
})
public class ViewRankingsBeanTest {

    @Inject
    private ContextController contextController;

    @Inject
    private ViewRankingsBean bean;

    @Test
    public void shouldLoadRankings() throws Exception {
        bean.loadUserScores();
        assertEquals(2, bean.getUserScores().size());
        assertEquals(3, bean.getUserScores().get(0).getPoints().intValue());
        assertEquals(TestData.user2, bean.getUserScores().get(0).getUser());
        assertEquals(0, bean.getUserScores().get(1).getPoints().intValue());
        assertEquals(TestData.user1, bean.getUserScores().get(1).getUser());
    }
}
