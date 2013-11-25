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

package uk.ac.ebi.atlas.solr.query.conditions;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

@Named
@Scope("singleton")
public class DifferentialConditionsSearchService {

    private SolrServer differentialConditionsSolrServer;

    private ConditionsSolrQueryBuilder queryBuilder;

    @Inject
    public DifferentialConditionsSearchService(SolrServer differentialConditionsSolrServer, ConditionsSolrQueryBuilder queryBuilder) {
        this.differentialConditionsSolrServer = differentialConditionsSolrServer;
        this.queryBuilder = queryBuilder;
    }

    public Collection<IndexedAssayGroup> findContrasts(String queryString) {

        try {
            QueryResponse queryResponse = differentialConditionsSolrServer.query(queryBuilder.build(queryString));
            List<DifferentialCondition> beans = queryResponse.getBeans(DifferentialCondition.class);

            return Collections2.transform(beans, new Function<DifferentialCondition, IndexedAssayGroup>() {
                @Override
                public IndexedAssayGroup apply(DifferentialCondition conditionProperty) {
                    return new IndexedAssayGroup(conditionProperty.getExperimentAccession(), conditionProperty.getContrastId());
                }
            });
        } catch (SolrServerException e) {
            throw new IllegalStateException("Conditions index query failed!", e);
        }
    }

}
