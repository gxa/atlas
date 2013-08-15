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

package uk.ac.ebi.atlas.web.controllers.page;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.web.controllers.rest.admin.LoadExperimentsController;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml"})
public class DifferentialGeneProfileServiceIT {

    private static final String E_GEOD_22351 = "E-GEOD-22351";
    private static final String E_MTAB_698 = "E-MTAB-698";
    private static final String E_GEOD_38400 = "E-GEOD-38400";

    private static final String FIRST_IDENTIFIER = "ENSMUSG00000029816";
    private static final String SECOND_IDENTIFIER = "ENSMUSG00000036887";
    private static final String THIRD_IDENTIFIER = "ENSMUSG00000026628";
    private static final String FOURTH_IDENTIFIER = "AT3G29644";

    private static final double FDR_CUTOFF = 0.5;
    private static final String MUS_MUSCULUS = "Mus musculus";
    private static final String ARABIDOPSIS_THALIANA = "Arabidopsis thaliana";

    @Inject
    private ExperimentTrader experimentTrader;

    @Inject
    private DifferentialGeneProfileService subject;

    @Inject
    private LoadExperimentsController loadExperimentsController;

    @Before
    public void initDatabase() throws IOException {
        loadExperimentsController.loadExperiment(E_GEOD_38400, ExperimentType.DIFFERENTIAL, false);
        loadExperimentsController.loadExperiment(E_GEOD_22351, ExperimentType.DIFFERENTIAL, false);
        loadExperimentsController.loadExperiment(E_MTAB_698, ExperimentType.DIFFERENTIAL, false);
    }

    @After
    public void tearDown() throws IOException {
        loadExperimentsController.deleteExperiment(E_GEOD_38400);
        loadExperimentsController.deleteExperiment(E_GEOD_22351);
        loadExperimentsController.deleteExperiment(E_MTAB_698);
    }


    @Test
    public void testGetDifferentialProfilesListForIdentifierFirst() throws Exception {
        DifferentialGeneProfileProperties differentialGeneProfileProperties = subject.initDifferentialProfilesListForIdentifier(FIRST_IDENTIFIER, FDR_CUTOFF);
        assertThat(differentialGeneProfileProperties, is(not(nullValue())));
        assertThat(differentialGeneProfileProperties.getAllExperimentAccessions(), contains(E_GEOD_22351));
        DifferentialProfilesList differentialProfilesListForExperiment = differentialGeneProfileProperties.getDifferentialProfilesListForExperiment(E_GEOD_22351);
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesListForExperiment.get(0);
        assertThat(differentialProfile.getId(), is(FIRST_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(1));
    }

    @Test
    public void testGetDifferentialProfilesListForIdentifierSecond() throws Exception {
        DifferentialGeneProfileProperties differentialGeneProfileProperties = subject.initDifferentialProfilesListForIdentifier(SECOND_IDENTIFIER, FDR_CUTOFF);
        assertThat(differentialGeneProfileProperties, is(not(nullValue())));
        assertThat(differentialGeneProfileProperties.getAllExperimentAccessions(), containsInAnyOrder(E_GEOD_22351));
        DifferentialProfilesList differentialProfilesListForExperiment = differentialGeneProfileProperties.getDifferentialProfilesListForExperiment(E_GEOD_22351);
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesListForExperiment.get(0);
        assertThat(differentialProfile.getId(), is(SECOND_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(1));
    }

    @Test
    public void testGetDifferentialProfilesListForIdentifierThird() throws Exception {
        DifferentialGeneProfileProperties differentialGeneProfileProperties = subject.initDifferentialProfilesListForIdentifier(THIRD_IDENTIFIER, FDR_CUTOFF);
        assertThat(differentialGeneProfileProperties, is(not(nullValue())));
        assertThat(differentialGeneProfileProperties.getAllExperimentAccessions(), containsInAnyOrder(E_MTAB_698, E_GEOD_22351));
        DifferentialProfilesList differentialProfilesListForExperiment = differentialGeneProfileProperties.getDifferentialProfilesListForExperiment(E_MTAB_698);
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesListForExperiment.get(0);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(1));
        differentialProfilesListForExperiment = differentialGeneProfileProperties.getDifferentialProfilesListForExperiment(E_GEOD_22351);
        differentialProfile = (DifferentialProfile) differentialProfilesListForExperiment.get(0);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(1));
    }

    @Test
    public void testGetDifferentialProfilesListForIdentifierFourth() throws Exception {
        DifferentialGeneProfileProperties differentialGeneProfileProperties = subject.initDifferentialProfilesListForIdentifier(FOURTH_IDENTIFIER, FDR_CUTOFF);
        assertThat(differentialGeneProfileProperties, is(not(nullValue())));
        assertThat(differentialGeneProfileProperties.getAllExperimentAccessions(), contains(E_GEOD_38400));
        DifferentialProfilesList differentialProfilesListForExperiment = differentialGeneProfileProperties.getDifferentialProfilesListForExperiment(E_GEOD_38400);
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesListForExperiment.get(0);
        assertThat(differentialProfile.getId(), is(FOURTH_IDENTIFIER));
        assertThat(differentialProfile.getConditions().size(), is(3));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD22351First() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_GEOD_22351, FIRST_IDENTIFIER, FDR_CUTOFF, MUS_MUSCULUS);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(FIRST_IDENTIFIER));
    }


    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD22351Second() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_GEOD_22351, SECOND_IDENTIFIER, FDR_CUTOFF, MUS_MUSCULUS);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(SECOND_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEMTAB698Third() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_MTAB_698, THIRD_IDENTIFIER, FDR_CUTOFF, MUS_MUSCULUS);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD22351Third() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_GEOD_22351, THIRD_IDENTIFIER, FDR_CUTOFF, MUS_MUSCULUS);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(THIRD_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD38400Fourth() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_GEOD_38400, FOURTH_IDENTIFIER, FDR_CUTOFF, ARABIDOPSIS_THALIANA);
        assertThat(differentialProfilesList, is(not(nullValue())));
        assertThat(differentialProfilesList.size(), is(1));
        DifferentialProfile differentialProfile = (DifferentialProfile) differentialProfilesList.get(0);
        assertThat(differentialProfile.getId(), is(FOURTH_IDENTIFIER));
    }

    @Test
    public void testRetrieveDifferentialProfileForExperimentEGEOD22351WrongSpecie() throws Exception {
        DifferentialProfilesList differentialProfilesList = subject.retrieveDifferentialProfilesForRnaSeqExperiment(E_GEOD_22351, FIRST_IDENTIFIER, FDR_CUTOFF, "BLA");
        assertThat(differentialProfilesList.isEmpty(), is(true));
    }
}