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

package uk.ac.ebi.atlas.search.diffanalytics;

import oracle.sql.ARRAY;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.search.DatabaseQuery;

import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class DiffAnalyticsQueryBuilderTest {

    private DiffAnalyticsQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new DiffAnalyticsQueryBuilder();

    }

    @Test
    public void selectWhereContrasts() throws Exception {
        ARRAY indexedContrasts = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withExperimentContrasts(indexedContrasts).buildSelect();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT IDENTIFIER, NAME, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID order by abs(LOG2FOLD) desc"));
    }

    @Test
    public void countWhereContrasts() throws Exception {
        ARRAY indexedContrasts = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withExperimentContrasts(indexedContrasts).buildCount();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT count(1) FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID order by abs(LOG2FOLD) desc"));
    }


    @Test
    public void selectWhereGeneIds() throws Exception {
        ARRAY geneIds = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withGeneIds(geneIds).buildSelect();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT IDENTIFIER, NAME, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT " +
                "FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value order by abs(LOG2FOLD) desc"));
        MatcherAssert.assertThat(databaseQuery.getParameters(), IsIterableContainingInOrder.contains((Object) geneIds));

    }

    @Test
    public void selectWhereContrastsAndGeneIds() throws Exception {
        ARRAY geneIds = Mockito.mock(ARRAY.class);
        ARRAY indexedContrasts = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withExperimentContrasts(indexedContrasts)
                .withGeneIds(geneIds)
                .buildSelect();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT IDENTIFIER, NAME, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value JOIN TABLE(?) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID order by abs(LOG2FOLD) desc"));
        MatcherAssert.assertThat(databaseQuery.getParameters().size(), is(2));

    }


    @Test
    public void countWhereContrastsAndGeneIds() throws Exception {
        ARRAY geneIds = Mockito.mock(ARRAY.class);
        ARRAY indexedContrasts = Mockito.mock(ARRAY.class);

        DatabaseQuery<Object> databaseQuery = subject.withExperimentContrasts(indexedContrasts)
                .withGeneIds(geneIds)
                .buildCount();

        MatcherAssert.assertThat(databaseQuery.getQuery(), is("SELECT count(1) FROM VW_DIFFANALYTICS JOIN EXPERIMENT on VW_DIFFANALYTICS.EXPERIMENT = EXPERIMENT.ACCESSION AND PRIVATE = 'F' JOIN TABLE(?) identifiersTable ON IDENTIFIER = identifiersTable.column_value JOIN TABLE(?) exprContrast ON VW_DIFFANALYTICS.EXPERIMENT = exprContrast.EXPERIMENT AND VW_DIFFANALYTICS.CONTRASTID = exprContrast.CONTRASTID order by abs(LOG2FOLD) desc"));

    }

}
