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

package uk.ac.ebi.atlas.experimentimport;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Named;
import java.util.Set;

@Named
@Scope("prototype")
public class ExperimentDTOBuilder {
    private String experimentAccession;
    private ExperimentType experimentType;
    private boolean isPrivate;

    private Set<String> species;
    private String title;
    private Set<String> pubmedIds;

    public ExperimentDTOBuilder forExperimentAccession(String experimentAccession){
        this.experimentAccession = experimentAccession;
        return this;
    }

    public ExperimentDTOBuilder withExperimentType(ExperimentType experimentType){
        this.experimentType = experimentType;
        return this;
    }

    public ExperimentDTOBuilder withPrivate(boolean isPrivate){
        this.isPrivate = isPrivate;
        return this;
    }

    public ExperimentDTOBuilder withSpecies(Set<String> species) {
        this.species = species;
        return this;
    }

    public ExperimentDTOBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ExperimentDTOBuilder withPubMedIds(Set<String> pubmedIds) {
        this.pubmedIds = pubmedIds;
        return this;
    }

    public ExperimentDTO build(){
        return new ExperimentDTO(experimentAccession, experimentType, species, pubmedIds, title, isPrivate);
    }
}
