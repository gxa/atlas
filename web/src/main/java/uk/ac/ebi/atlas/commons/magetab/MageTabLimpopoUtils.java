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

package uk.ac.ebi.atlas.commons.magetab;

import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.SourceNode;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.attribute.CharacteristicsAttribute;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.arrayexpress2.magetab.parser.MAGETABParser;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Set;

public class MageTabLimpopoUtils {

    private static final Logger LOGGER = Logger.getLogger(MageTabLimpopoUtils.class);

    protected String idfUrlTemplate;

    protected String idfPathTemplate;

    protected MAGETABInvestigation investigation;

    @Inject
    public void setIdfUrlTemplate(@Value("#{configuration['experiment.magetab.idf.url.template']}") String idfUrlTemplate) {
        this.idfUrlTemplate = idfUrlTemplate;
    }

    @Inject
    public void setIdfPathTemplate(@Value("#{configuration['experiment.magetab.idf.path.template']}") String idfPathTemplate) {
        this.idfPathTemplate = idfPathTemplate;
    }

    protected MAGETABInvestigation getInvestigation() {
        return investigation;
    }

    protected Collection<ScanNode> extractScanNodes(String experimentAccession) throws IOException, ParseException {
        investigation = parseInvestigation(experimentAccession);
        return investigation.SDRF.getNodes(ScanNode.class);
    }

    protected Collection<SourceNode> extractSourceNodes(String experimentAccession) throws IOException, ParseException {
        investigation = parseInvestigation(experimentAccession);
        return investigation.SDRF.getNodes(SourceNode.class);
    }

    protected Collection<HybridizationNode> extractHybridizationNode(String experimentAccession) throws IOException, ParseException {
        investigation = parseInvestigation(experimentAccession);
        return investigation.SDRF.getNodes(HybridizationNode.class);
    }

    protected Set<String> extractSpeciesFromSDRF(Collection<ScanNode> scanNodes) {
        Set<String> species = Sets.newHashSet();
        Collection<SourceNode> sourceNodes = investigation.SDRF.getNodes(SourceNode.class);
        for (SourceNode sourceNode : sourceNodes) {
            for (CharacteristicsAttribute characteristic : sourceNode.characteristics) {
                if (characteristic.type.equalsIgnoreCase("ORGANISM")) {
                    species.add(characteristic.getAttributeValue());
                }
            }
        }

        return species;
    }

    MAGETABInvestigation parseInvestigation(String experimentAccession) throws ParseException, MalformedURLException {

        String idfFileLocation = MessageFormat.format(idfPathTemplate, experimentAccession);
        LOGGER.info("<parseInvestigation> idfFileLocation = " + idfFileLocation);

        MAGETABParser<MAGETABInvestigation> mageTabParser = new MAGETABParser<>();
        File idfFile = new File(idfFileLocation);
        if (idfFile.exists()) {
            LOGGER.info("<parseInvestigation> investigation file exists on the filesystem, going to use it");
            return mageTabParser.parse(idfFile);
        } else {
            LOGGER.info("<parseInvestigation> investigation file not found on the filesystem, going to use online file");
            URL idfFileURL = new URL(MessageFormat.format(idfUrlTemplate, experimentAccession));
            return mageTabParser.parse(idfFileURL);
        }

    }
}