/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model.differential;


import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.Map;

public class DifferentialProfile implements Iterable<DifferentialExpression>{

    private String geneId;

    private Map<Contrast, DifferentialExpression> differentialExpressions = Maps.newHashMap();

    public DifferentialProfile(String geneId) {
        this.geneId = geneId;
    }

    public double getDifferentialExpression(Contrast contrast){
        return differentialExpressions.get(contrast).getPValue();
    }

    public DifferentialProfile addExpression(DifferentialExpression expression){
        this.differentialExpressions.put(expression.getContrast(), expression);
        return this;
    }

    public String getGeneId() {
        return geneId;
    }

    @Override
    public Iterator<DifferentialExpression> iterator() {
        return differentialExpressions.values().iterator();
    }
}
