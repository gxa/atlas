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

package uk.ac.ebi.atlas.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentPageRequestPreferencesTest {

    private ExperimentPageRequestPreferences subject;

    @Mock
    private BaselineExpressionLevelRounder baselineExpressionLevelRounderMock;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineRequestPreferences();
    }

    @Test
    public void cutoffShouldBeRoundedToNoFractionalDigitForValuesLargerThanOne() {
        //given
        subject.setCutoff(2.1211);
        //then
        assertThat(subject.getCutoff(), is(2d));
    }

    @Test
    public void cutoffShouldBeRoundedTo1FractionalDigitForValuesSmallerThanOne() {
        //given
        subject.setCutoff(0.1211);
        //then
        assertThat(subject.getCutoff(), is(0.1d));
    }

    @Test
    public void heatmapMatrixSizeIsSetToTheDefaultRankingSizeIfRequestDoesntSpecifyAnyValue() {
        //given
        subject.setHeatmapMatrixSize(null);
        //then
        assertThat(subject.getHeatmapMatrixSize(), is(ExperimentPageRequestPreferences.DEFAULT_NUMBER_OF_RANKED_GENES));
        //and given
        subject.setHeatmapMatrixSize(33);
        //then
        assertThat(subject.getHeatmapMatrixSize(), is(33));
    }

    @Test
    public void testMatchingDoubleQuotes(){
        assertThat(subject.areQuotesMatching("hello \" boy"), is(false));
        assertThat(subject.areQuotesMatching("hello \" boy \""), is(true));
    }

}
