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

package uk.ac.ebi.atlas.streams.differential;


import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile.DifferentialProfileBuilder;
import uk.ac.ebi.atlas.streams.TsvInputStream;
import uk.ac.ebi.atlas.streams.TsvRowBuffer;

public class DifferentialProfilesInputStream extends TsvInputStream<DifferentialProfile> {


    private DifferentialProfileBuilder differentialProfileBuilder;

    public DifferentialProfilesInputStream(CSVReader csvReader, String experimentAccession
            , DifferentialExpressionsBuffer.Builder expressionsBufferBuilder
            , DifferentialProfileBuilder differentialProfileBuilder) {

        super(csvReader, experimentAccession, expressionsBufferBuilder);
        this.differentialProfileBuilder = differentialProfileBuilder;
    }

    @Override
    protected DifferentialProfile buildObjectFromTsvValues(String[] values) {

        //we need to reload because the first line can only be used to extract the gene ID
        getTsvRowBuffer().reload(values);

        differentialProfileBuilder.forGeneId(values[TsvRowBuffer.GENE_ID_COLUMN]);

        DifferentialExpression expression;

        while ((expression = (DifferentialExpression)getTsvRowBuffer().poll()) != null) {

            differentialProfileBuilder.addExpression(expression);

        }

        return differentialProfileBuilder.create();

    }
}
