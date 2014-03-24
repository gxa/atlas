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

package uk.ac.ebi.atlas.commands.context;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSetMultimap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Named;
import java.util.Collections;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Scope("request")
public class BaselineRequestContext extends RequestContext<Factor, BaselineRequestPreferences> implements BaselineProfileStreamOptions {

    private BaselineExperiment experiment;

    private Set<Factor> selectedFilterFactors;

    public BaselineRequestContext() {
    }

    public Set<Factor> getSelectedFilterFactors() {
        return selectedFilterFactors;
    }

    @Override
    public ImmutableSetMultimap<String, String> getGeneSetIdsToGeneIds() {
        checkNotNull(geneQueryResponse, "geneQueryResponse not set");
        return geneQueryResponse.getQueryTermsToIds();
    }

    @Override
    public String getExperimentAccession() {
        return experiment.getAccession();
    }

    public String getQueryFactorType() {
        return getRequestPreferences().getQueryFactorType();
    }

    void setSelectedFilterFactors(Set<Factor> selectedFilterFactors) {
        this.selectedFilterFactors = selectedFilterFactors;
    }

    void setExperiment(BaselineExperiment experiment) {
        this.experiment = experiment;
    }

    public BaselineExperiment getExperiment() {
        return experiment;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .addValue(super.toString())
                .add("selectedFilterFactors", selectedFilterFactors)
                .add("experiment", experiment).toString();
    }


}