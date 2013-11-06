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

package uk.ac.ebi.atlas.dao;

import com.google.common.collect.Lists;

import java.util.List;

public class AssayGroupQuery {

    private List<String> params = Lists.newArrayList();
    private String query;
    private StringBuilder queryBuilder = new StringBuilder();

    void addParameter(String value) {
        params.add(value);
    }

    void addParameters(List<String> values) {
        params.addAll(values);
    }

    String[] getParameters() {
        return params.toArray(new String[params.size()]);
    }

    String getQuery() {
        return queryBuilder.toString();
    }

    void setQueryString(String query) {
        this.query = query;
    }

    AssayGroupQuery appendToQueryString(String text) {
        queryBuilder.append(text);
        return this;
    }

    @Override
    public String toString(){
        return "query: " + getQuery() + ", params: " + params;
    }
}
