/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
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
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.web.DifferentialDesignRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DifferentialDesignPageControllerIT {

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String experimentDesignTemplate;

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-25185";

    @Inject
    private DifferentialDesignPageController subject;

    @Inject
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    private HttpServletRequest requestMock;
    private DifferentialDesignRequestPreferences preferencesMock;

    Model model = new BindingAwareModelMap();


    @Before
    public void initSubject() throws Exception {

        requestMock = mock(HttpServletRequest.class);
        preferencesMock = mock(DifferentialDesignRequestPreferences.class);
        DifferentialExperiment differentialExperiment = rnaSeqDiffExperimentsCache.getExperiment(EXPERIMENT_ACCESSION);
        when(requestMock.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE)).thenReturn(differentialExperiment);
        when(requestMock.getRequestURI()).thenReturn("/gxa/experiments/" + EXPERIMENT_ACCESSION + "/experiment-design");

        assertNotNull(differentialExperiment);
    }

    @Test
    public void testExtractExperimentDesign() throws IOException {

        // given
        subject.showRnaSeqExperimentDesign(preferencesMock, model, requestMock);

        Gson gson = new Gson();

        // then
        Map<String, Object> map = model.asMap();

        assertNotNull(map.get("assayHeaders"));

        // and
        String[] samples = gson.fromJson((String) map.get("sampleHeaders"), String[].class);
        assertThat(samples.length, greaterThan(0));
        String[] factors = gson.fromJson((String) map.get("factorHeaders"), String[].class);
        assertThat(factors.length, greaterThan(0));

        // and
        Type listStringArrayType = new TypeToken<List<String[]>>() {
        }.getType();
        List<String[]> data = gson.fromJson((String) map.get("tableData"), listStringArrayType);
        assertThat(data.size(), greaterThan(0));


        // and
        assertNotNull(map.get("runAccessions"));

        // and
        assertThat((String) map.get("experimentAccession"), is(EXPERIMENT_ACCESSION));
    }
}