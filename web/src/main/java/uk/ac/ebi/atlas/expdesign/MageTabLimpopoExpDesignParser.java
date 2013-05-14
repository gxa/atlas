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

package uk.ac.ebi.atlas.expdesign;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.graph.utils.GraphUtils;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.CharacteristicsAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public class MageTabLimpopoExpDesignParser {

    protected String experimentAccession;

    protected Collection<SourceNode> sourceNodes;

    protected Collection<ScanNode> scanNodes;

    protected Collection<HybridizationNode> hybridizationNodes;

    protected MAGETABInvestigation investigation;

    private String idfUrlTemplate;

    private String idfPathTemplate;

    @Inject
    public void setIdfUrlTemplate(@Value("#{configuration['experiment.magetab.idf.url.template']}") String idfUrlTemplate) {
        this.idfUrlTemplate = idfUrlTemplate;
    }

    @Inject
    public void setIdfPathTemplate(@Value("#{configuration['experiment.magetab.idf.path.template']}") String idfPathTemplate) {
        this.idfPathTemplate = idfPathTemplate;
    }

    public MageTabLimpopoExpDesignParser forExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    public MageTabLimpopoExpDesignParser build() throws IOException, ParseException {
        checkState(experimentAccession != null, "Please invoke forExperimentAccession method to initialize the builder !");

        investigation = MageTabLimpopoUtils.parseInvestigation(experimentAccession, idfPathTemplate, idfUrlTemplate);

        sourceNodes = investigation.SDRF.getNodes(SourceNode.class);

        scanNodes = investigation.SDRF.getNodes(ScanNode.class);

        hybridizationNodes = investigation.SDRF.getNodes(HybridizationNode.class);

        return this;
    }

    public Set<String> extractCharacteristics() {

        Set<String> characteristics = Sets.newHashSet();

        for (SourceNode sourceNode : sourceNodes) {
            for (CharacteristicsAttribute characteristicsAttribute : sourceNode.characteristics) {
                characteristics.add(characteristicsAttribute.type);
            }
        }

        return characteristics;
    }

    public String[] findCharacteristicValueForScanNode(ScanNode scanNode, String characteristic) {

        Collection<SourceNode> upstreamNodes = GraphUtils.findUpstreamNodes(scanNode, SourceNode.class);
        if (upstreamNodes.size() != 1) {
            throw new IllegalStateException("There is no one to one mapping between scanNode and sourceNode for scanNode: " + scanNode);
        }

        SourceNode sourceNode = upstreamNodes.iterator().next();
        for (CharacteristicsAttribute characteristicsAttribute : sourceNode.characteristics) {
            if (characteristicsAttribute.type.equals(characteristic)) {
                return characteristicsAttribute.values();
            }
        }

        return null;
    }

}