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

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import uk.ac.ebi.atlas.model.Expression;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Set;

public class BaselineExpression implements Expression {
    private final double level;
    private final String levelString;
    private FactorGroup factorGroup;
    private final boolean known;
    private final Optional<Quartiles> quartiles;

    public BaselineExpression(double level, FactorGroup factorGroup) {
        this(level, factorGroup, Optional.<Quartiles>absent());
    }

    public BaselineExpression(Quartiles quartiles, FactorGroup factorGroup) {
        this(quartiles.median(), factorGroup, Optional.of(quartiles));
    }

    private BaselineExpression(double level, FactorGroup factorGroup, Optional<Quartiles> quartiles) {
        this.level = level;
        this.factorGroup = factorGroup;
        this.levelString = removeTrailingZero(level);
        this.known = true;
        this.quartiles = quartiles;
    }

    public BaselineExpression(String expressionLevelString, FactorGroup factorGroup) {
        this.levelString = expressionLevelString;

        switch (expressionLevelString) {
            //TODO: remove FAIL, LOWDATA - no longer present in the input data
            case "FAIL":
            case "LOWDATA":
            case "NT":  //Non-Tissue
                level = 0;
                known = false;
                break;
            case "NA":
                // treat as if zero
                level = 0;
                known = true;
                break;
            default:
                level = Double.parseDouble(expressionLevelString);
                known = true;
                break;
        }
        this.factorGroup = factorGroup;
        this.quartiles = Optional.absent();
    }

    public Optional<Quartiles> getQuartiles() {
        return quartiles;
    }

    //ToDo: this method is only required by BarChartTraderBuilder and is just exposing internal data structure, maybe it should not be here.
    public FactorGroup getFactorGroup() {
        return factorGroup;
    }

    public double getLevel() {
        if (!isKnown()) {
            throw new UnsupportedOperationException("BaselineExpression level is " + levelString + ". Call isKnown() first to check.");
        }
        return level;
    }

    public boolean isKnown() {
        return known;
    }

    public String getLevelAsString() {
        return levelString;
    }

    public boolean isGreaterThan(double level) {
        return Double.compare(getLevel(), level) > 0;
    }

    public Factor getFactor(String type) {
        for (Factor factor : factorGroup) {
            if (factor.getType().equals(type)) {
                return factor;
            }
        }
        throw new IllegalStateException("BaselineExpression doesn't contain factor for a given type");
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        BaselineExpression other = (BaselineExpression) object;

        return Objects.equal(level, other.level) &&
                Objects.equal(factorGroup, other.factorGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(level, factorGroup);
    }

    public boolean containsAll(Set<Factor> factors) {
        return factorGroup.containsAll(factors);
    }

    static String removeTrailingZero(double value) {
        NumberFormat format = new DecimalFormat("0.####");
        return format.format(value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("levelString", levelString)
                .add("factorGroup", factorGroup)
                .toString();
    }


}