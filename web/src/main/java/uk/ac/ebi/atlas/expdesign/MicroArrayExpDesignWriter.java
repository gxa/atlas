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
/*
package uk.ac.ebi.atlas.expdesign;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.HybridizationNode;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

//ToDo: (N) to be removed

@Named
@Scope("prototype")
public class MicroArrayExpDesignWriter implements ExpDesignWriter {

    private MicroArrayExpDesignMageTabParser mageTabLimpopoExpDesignParser;

    private PropertyMergeService propertyMergeService;

    @Inject
    public MicroArrayExpDesignWriter(MicroArrayExpDesignMageTabParser mageTabLimpopoExpDesignParser, PropertyMergeService propertyMergeService) {
        this.mageTabLimpopoExpDesignParser = mageTabLimpopoExpDesignParser;
        this.propertyMergeService = propertyMergeService;
    }

    @Override
    public void forExperimentAccession(String experimentAccession, CSVWriter csvWriter) throws IOException, ParseException {
        mageTabLimpopoExpDesignParser.init(experimentAccession);

        List<String> characteristics = Lists.newArrayList(mageTabLimpopoExpDesignParser.extractCharacteristics());
        Collections.sort(characteristics);

        List<String> factors = Lists.newArrayList(mageTabLimpopoExpDesignParser.extractFactors());
        Collections.sort(factors);

        List<String> assays = Lists.newArrayList(mageTabLimpopoExpDesignParser.extractAssays());
        Collections.sort(assays);

        csvWriter.writeNext(composeHeader(characteristics, factors));
        for (String assay : assays) {
            csvWriter.writeNext(composeExperimentAssay(assay, characteristics, factors));
        }
    }

    String[] composeExperimentAssay(String assay, List<String> characteristics, List<String> factors) {
        List<String> result = Lists.newArrayList(assay);
        HybridizationNode hybridizationNode = mageTabLimpopoExpDesignParser.getHybridizationNodeForAssay(assay);
        String array = mageTabLimpopoExpDesignParser.findArrayForHybridizationNode(hybridizationNode);
        result.add(array);
        for (String characteristic : characteristics) {
            List<String> characteristicValues = mageTabLimpopoExpDesignParser.findCharacteristicValueForSDRFNode(hybridizationNode, characteristic);
            addNodeValue(result, characteristicValues);
        }
        for (String factor : factors) {
            List<String> factorValues = mageTabLimpopoExpDesignParser.findFactorValueForHybridizationNode(hybridizationNode, factor);
            addNodeValue(result, factorValues);
        }
        return result.toArray(new String[result.size()]);
    }

    private void addNodeValue(List<String> result, List<String> values) {
        //Limpopo parser returns 2 values in a case there is a unit present
        if (values.size() == 2) {
            result.add(propertyMergeService.mergeValueAndUnit(values.get(0), values.get(1)));
        } else if (values.size() == 1) {
            result.add(values.get(0));
        } else {
            result.add("");
        }
    }

    String[] composeHeader(List<String> characteristics, List<String> factors) {
        List<String> result = Lists.newArrayList("Assay", "Array");
        for (String characteristic : characteristics) {
            result.add("Sample Characteristics[" + characteristic + "]");
        }
        for (String factor : factors) {
            result.add("Factor Values[" + factor + "]");
        }
        return result.toArray(new String[result.size()]);
    }
}
*/