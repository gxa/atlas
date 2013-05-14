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

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.Lists;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MicroArrayExpDesignWriter implements ExpDesignWriter {

    private MicroArrayMageTabLimpopoExpDesignParser mageTabLimpopoExpDesignParser;

    private CSVWriter csvWriter;

    public MicroArrayExpDesignWriter(MicroArrayMageTabLimpopoExpDesignParser mageTabLimpopoExpDesignParser, CSVWriter csvWriter) {
        this.mageTabLimpopoExpDesignParser = mageTabLimpopoExpDesignParser;
        this.csvWriter = csvWriter;
    }

    @Override
    public void forExperimentAccession(String experimentAccession) throws IOException, ParseException {
        mageTabLimpopoExpDesignParser.forExperimentAccession(experimentAccession).build();

        List<String> characteristics = Lists.newArrayList(mageTabLimpopoExpDesignParser.extractCharacteristics());
        Collections.sort(characteristics);

        List<String> factors = Lists.newArrayList(mageTabLimpopoExpDesignParser.extractFactors());
        Collections.sort(factors);

        ArrayList<String> assays = Lists.newArrayList(mageTabLimpopoExpDesignParser.extractAssays());
        Collections.sort(assays);

        csvWriter.writeNext(composeHeader(characteristics, factors));
        for (String assay : assays) {
            csvWriter.writeNext(composeExperimentAssay(assay, characteristics, factors));
        }
    }

    String[] composeExperimentAssay(String assay, List<String> characteristics, List<String> factors) {
        ArrayList<String> result = Lists.newArrayList(assay);
        ScanNode scanNode = mageTabLimpopoExpDesignParser.getScanNodeForAssay(assay);
        String array = mageTabLimpopoExpDesignParser.findArrayForScanNode(scanNode);
        result.add(array);
        for (String characteristic : characteristics) {
            String[] characteristicValueForScanNode = mageTabLimpopoExpDesignParser.findCharacteristicValueForScanNode(scanNode, characteristic);
            if (characteristicValueForScanNode != null) {
                result.add(characteristicValueForScanNode[0]);
            } else {
                result.add("");
            }
        }
        for (String factor : factors) {
            String[] factorValueForScanNode = mageTabLimpopoExpDesignParser.findFactorValueForScanNode(scanNode, factor);
            if (factorValueForScanNode != null) {
                result.add(factorValueForScanNode[0]);
            } else {
                result.add("");
            }
        }
        return result.toArray(new String[result.size()]);
    }

    String[] composeHeader(List<String> characteristics, List<String> factors) {
        ArrayList<String> result = Lists.newArrayList("Assay", "Array");
        for (String characteristic : characteristics) {
            result.add("Sample Characteristics[" + characteristic + "]");
        }
        for (String factor : factors) {
            result.add("Factor Values[" + factor + "]");
        }
        return result.toArray(new String[result.size()]);
    }
}