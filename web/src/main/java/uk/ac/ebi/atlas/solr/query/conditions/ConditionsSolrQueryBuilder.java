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
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.sun.istack.internal.Nullable;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Scope("singleton")
public class ConditionsSolrQueryBuilder {

    public static final String CONDITIONS_SEARCH_FIELD = "conditions_search";

    BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer;

    @Inject
    public ConditionsSolrQueryBuilder(BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer) {
        this.bioentityPropertyValueTokenizer = bioentityPropertyValueTokenizer;
    }

    public SolrQuery build(String queryString) {
        SolrQuery solrQuery = new SolrQuery(buildQueryString(queryString));
        solrQuery.setRows(1000);
        return solrQuery;
    }

    String buildQueryString(String queryString){
        List<String> terms = bioentityPropertyValueTokenizer.split(queryString);

        Iterable<String> searchTerms = Iterables.transform(terms, new Function<String, String>() {

            @Override
            public String apply(@Nullable java.lang.String s) {
                return CONDITIONS_SEARCH_FIELD + ":" + s;
            }
        });

        return Joiner.on(" OR ").join(searchTerms);
    }
}
