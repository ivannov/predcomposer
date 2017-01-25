package com.nosoftskills.predcomposer.prediction;

import com.nosoftskills.predcomposer.alternatives.PredictionsServiceAlternative;
import com.nosoftskills.predcomposer.alternatives.UserContextAlternative;
import com.nosoftskills.predcomposer.model.Prediction;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * @author Ivan St. Ivanov
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({
        PredictionsServiceAlternative.class, UserContextAlternative.class
})
public class ViewUserPredictionsBeanTest {

    @Inject
    private ViewUserPredictionsBean bean;

    @Test
    @InRequestScope
    public void shouldLoadUserPredictionsUponStartup() throws Exception {
        Map<Long, Prediction> userPredictions = bean.getUserPredictions();
        assertNotNull(userPredictions);
        assertEquals(2, userPredictions.size());
    }
}
