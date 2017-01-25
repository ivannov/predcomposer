package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.alternatives.PredictionsServiceAlternative;
import com.nosoftskills.predcomposer.alternatives.UserContextAlternative;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
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
    private ViewGamePredictionsBean bean;

    @Test
    @InRequestScope
    public void shouldLoadGamePredictionsUponRequest() throws Exception {
        bean.showGamePredictions(game2);
        assertEquals(game2, bean.getGame());
        assertEquals(2, bean.getPredictions().size());
    }

}
