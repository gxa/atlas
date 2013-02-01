/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExperimentTest {

    private static final String RUN_ACCESSION_1 = "ENS0";
    private static final String RUN_ACCESSION_2 = "ENS1";
    private static final String RUN_ACCESSION_3 = "ENS2";

    private ExperimentRun experimentRun1;
    private ExperimentRun experimentRun2;
    private ExperimentRun experimentRun3;

    private Factor factor1 = new Factor("ORGANISM_PART", "ORGANISM_PART", "heart");
    private Factor factor2 = new Factor("ORGANISM_PART", "ORGANISM_PART", "liver");
    private Factor factor3 = new Factor("ORGANISM_PART", "ORGANISM_PART", "lung");
    private Factor factor4 = new Factor("MATERIAL_TYPE", "RNAtype", "total rna");

    private static final String MOCK_EXPERIMENT_ACCESSION = "MOCK_EXPERIMENT_ACCESSION";

    private Experiment subject;
    private final String specie = "homo sapiens";

    @Before
    public void initializeSubject() {
        experimentRun1 = new ExperimentRun(RUN_ACCESSION_1).addFactor(factor1);
        experimentRun2 = new ExperimentRun(RUN_ACCESSION_2).addFactor(factor2);
        experimentRun3 = new ExperimentRun(RUN_ACCESSION_3).addFactor(factor3);
        experimentRun3.addFactor(factor4);

        subject = new Experiment(MOCK_EXPERIMENT_ACCESSION, null, factor1.getType(), Collections.EMPTY_SET, specie)
                .add(experimentRun1).add(experimentRun2).add(experimentRun3);
    }

    @Test
    public void testExperimentRunAccessions() {
        assertThat(subject.getExperimentRunAccessions(), hasItems(RUN_ACCESSION_1, RUN_ACCESSION_2, RUN_ACCESSION_3));
    }

    @Test
    public void testExperimentAccession() {
        assertThat(subject.getExperimentAccession(), is(MOCK_EXPERIMENT_ACCESSION));
    }

    @Test
    public void testDescription() {
        assertThat(subject.getDescription(), nullValue(String.class));
    }

    @Test
    public void testNumberOfRuns() {
        assertThat(subject.getNumberOfRuns(), is(3));
    }

    @Test
    public void testSpecies() {
        assertThat(subject.getSpecie(), is(specie));
    }

    @Test
    public void testExperimentRun() {
        assertThat(subject.getExperimentRun(RUN_ACCESSION_1), is(experimentRun1));
        assertThat(subject.getExperimentRun(RUN_ACCESSION_2), is(experimentRun2));
        assertThat(subject.getExperimentRun(RUN_ACCESSION_3), is(experimentRun3));
        assertThat(subject.getExperimentRun("non existing run"), nullValue(ExperimentRun.class));
    }

    @Test
    public void testDefaultFactorType() {
        assertThat(subject.getDefaultQueryFactorType(), is(factor1.getType()));
    }

    @Test
    public void testFactorValueByType() {
        assertThat(subject.getFactor(RUN_ACCESSION_1, factor1.getType()).getValue(), is(factor1.getValue()));
        assertThat(subject.getFactor(RUN_ACCESSION_1, factor4.getType()), nullValue(Factor.class));
        assertThat(subject.getFactor(RUN_ACCESSION_2, factor2.getType()).getValue(), is(factor2.getValue()));
        assertThat(subject.getFactor(RUN_ACCESSION_3, factor3.getType()).getValue(), is(factor3.getValue()));
        assertThat(subject.getFactor(RUN_ACCESSION_3, factor4.getType()).getValue(), is(factor4.getValue()));
    }

    @Test
    public void testAllFactorValues() {
        assertThat(subject.getAllFactors(RUN_ACCESSION_1), hasItems(factor1));
        assertThat(subject.getAllFactors(RUN_ACCESSION_2), hasItems(factor2));
        assertThat(subject.getAllFactors(RUN_ACCESSION_3), hasItems(factor3, factor4));
    }

    @Test
    public void testFilteredFactorValues() {
        assertThat(subject.getFilteredFactors(Sets.newHashSet(factor1), factor1.getType()).size(), is(1));
        assertThat(subject.getFilteredFactors(Sets.newHashSet(factor1), factor1.getType()), hasItems(factor1));
        // there is no such combination as factor1 and factor2 on an experimentRun
        assertThat(subject.getFilteredFactors(Sets.newHashSet(factor1, factor2), factor1.getType()).size(), is(0));
        assertThat(subject.getFilteredFactors(Sets.newHashSet(factor3, factor4), factor1.getType()).size(), is(1));
        assertThat(subject.getFilteredFactors(Sets.newHashSet(factor3, factor4), factor1.getType()), hasItems(factor3));
        assertThat(subject.getFilteredFactors(Sets.newHashSet(factor2), factor1.getType()).size(), is(1));
        assertThat(subject.getFilteredFactors(Sets.newHashSet(factor2), factor1.getType()), hasItems(factor2));
        assertThat(subject.getFilteredFactors(Sets.newHashSet(factor3), factor4.getType()).size(), is(1));
        assertThat(subject.getFilteredFactors(Sets.newHashSet(factor3), factor4.getType()), hasItems(factor4));
    }

}