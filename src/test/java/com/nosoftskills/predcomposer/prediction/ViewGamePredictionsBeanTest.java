package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.alternatives.PredictionsServiceAlternative;
import com.nosoftskills.predcomposer.alternatives.UserContextAlternative;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.ContextController;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static com.nosoftskills.predcomposer.common.TestData.game2;
import static org.junit.Assert.assertEquals;


/**
 * @author Ivan St. Ivanov
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({
        PredictionsServiceAlternative.class, UserContextAlternative.class
})
public class ViewGamePredictionsBeanTest {

    @Inject
    private ContextController contextController;

    @Inject
    private ViewGamePredictionsBean bean;

    @Test
    public void shouldLoadGamePredictionsUponRequest() throws Exception {
        contextController.openRequest();
        bean.showGamePredictions(game2);
        assertEquals(game2, bean.getGame());
        assertEquals(2, bean.getPredictions().size());
        contextController.closeRequest();
        contextController.closeSession();
    }

}
