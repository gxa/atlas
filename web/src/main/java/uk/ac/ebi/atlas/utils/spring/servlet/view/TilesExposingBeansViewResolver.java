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

package uk.ac.ebi.atlas.utils.spring.servlet.view;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Arrays;

//see: http://stackoverflow.com/questions/2848415/accessing-spring-beans-from-a-tiles-view-jsp
public class TilesExposingBeansViewResolver extends UrlBasedViewResolver {

    private Boolean exposeContextBeansAsAttributes;
    private String[] exposedContextBeanNames;

    public void setExposeContextBeansAsAttributes(boolean exposeContextBeansAsAttributes) {
        this.exposeContextBeansAsAttributes = exposeContextBeansAsAttributes;
    }

    public void setExposedContextBeanNames(String[] exposedContextBeanNames) {
        // this copy has been suggested by Sonar
        this.exposedContextBeanNames = Arrays.copyOf(exposedContextBeanNames, exposedContextBeanNames.length);
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) {
        try {
            AbstractUrlBasedView superView = super.buildView(viewName);
            if (superView instanceof TilesExposingBeansView) {
                TilesExposingBeansView view = (TilesExposingBeansView) superView;
                if (this.exposeContextBeansAsAttributes != null) {
                    view.setExposeContextBeansAsAttributes(this.exposeContextBeansAsAttributes);
                }
                if (this.exposedContextBeanNames != null) {
                    view.setExposedContextBeanNames(this.exposedContextBeanNames);
                }
            }
            return superView;
        } catch (Exception e) {
            throw new IllegalStateException("When building view with name " + viewName, e);
        }
    }

}