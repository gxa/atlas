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

package uk.ac.ebi.atlas.search;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerConditionAndOrganismSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "organism=Homo+sapiens&condition=adult");
        subject.get();
    }

    @Test
    public void checkBaselineExperimentCounts() {
        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(2));
        assertThat(baselineCounts.get(0).getExperimentAccession(), is("E-PROT-1"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("Human Proteome Map - adult"));
        assertThat(baselineCounts.get(0).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(0).getHref(), endsWith("E-PROT-1?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=B%20cell,CD4-positive%20T%20cell,CD8-positive%20T%20cell,adrenal%20gland,colon,esophagus,frontal%20cortex,gallbladder,kidney,liver,lung,monocyte,natural%20killer%20cell,ovary,pancreas,platelet,prostate,rectum,retina,spinal%20cord,testis,urinary%20bladder&geneQuery=&exactMatch=true&serializedFilterFactors=DEVELOPMENTAL_STAGE:adult"));

        assertThat(baselineCounts.get(1).getExperimentAccession(), is("E-MTAB-1733"));
        assertThat(baselineCounts.get(1).getExperimentName(), is("Twenty seven tissues"));
        assertThat(baselineCounts.get(1).getSpecies(), is("Homo sapiens"));
        assertThat(baselineCounts.get(1).getHref(), endsWith("E-MTAB-1733?_specific=on&queryFactorType=ORGANISM_PART&queryFactorValues=&geneQuery=&exactMatch=true"));
    }

    @Test
    public void checkDifferentialProfiles() {
        subject.clickDifferentialPane();
        assertThat(subject.diffExpressionResultCount(), is("Showing 50 of 9859 results"));
        assertThat(subject.getDiffHeatmapTableOrganismColumn(), contains(Collections.nCopies(50, "Homo sapiens").toArray()));

    }

}
