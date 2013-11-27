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

package uk.ac.ebi.atlas.web.controllers.page.crossexperiment;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.commands.BaselineBioentityCountsService;
import uk.ac.ebi.atlas.commands.GeneQueryDifferentialService;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.dao.BaselineExperimentResult;
import uk.ac.ebi.atlas.integration.EBIGlobalSearchQueryBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpressions;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.BioentityType;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Controller
@Scope("prototype")
public class BioentitiesQueryController {

    private GeneQueryDifferentialService geneQueryDifferentialService;
    private BaselineBioentityCountsService baselineBioentityCountsService;

    private EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder;

    private SolrQueryService solrQueryService;

    @Inject
    public BioentitiesQueryController(GeneQueryDifferentialService geneQueryDifferentialService, BaselineBioentityCountsService baselineBioentityCountsService, EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder, SolrQueryService solrQueryService) {
        this.geneQueryDifferentialService = geneQueryDifferentialService;
        this.baselineBioentityCountsService = baselineBioentityCountsService;
        this.ebiGlobalSearchQueryBuilder = ebiGlobalSearchQueryBuilder;
        this.solrQueryService = solrQueryService;
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("bioEntities");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @RequestMapping(value = "/query")
    public String showGeneQueryResultPage(@Valid GeneQuerySearchRequestParameters requestParameters, Model model, BindingResult result) {

        checkState(requestParameters.hasGeneQuery() || requestParameters.hasCondition(), "Please specify gene query or condition!");

        String geneQuery = requestParameters.getGeneQuery();

        if (requestParameters.hasGeneQuery() && !requestParameters.hasCondition()) {
            //If Query just for a single bioentityID
            String geneIdRedirectString = getGeneIdRedirectString(requestParameters.getGeneQuery());
            if (StringUtils.isNotBlank(geneIdRedirectString)) {
                return geneIdRedirectString;
            }
        }

        try {

            model.addAttribute("entityIdentifier", requestParameters.getDescription());

            Set<BaselineExperimentResult> baselineCounts = baselineBioentityCountsService.query(requestParameters);
            model.addAttribute("baselineCounts", baselineCounts);

            // used to populate diff-heatmap-table
            DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

            model.addAttribute("bioentities", bioentityExpressions);

            model.addAttribute("preferences", new DifferentialRequestPreferences());

            String globalSearchTerm = ebiGlobalSearchQueryBuilder.buildGlobalSearchTerm(geneQuery, requestParameters.getCondition());

            model.addAttribute("globalSearchTerm", globalSearchTerm);


        } catch (GenesNotFoundException e) {
            result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + geneQuery + "'"));
        }

        return "bioEntities";
    }


    private String getGeneIdRedirectString(String geneId) {

        if (geneId.toUpperCase().startsWith("REACT_")) {
            return "redirect:/genesets/" + geneId;
        }

        BioentityProperty bioentityProperty = solrQueryService.findBioentityIdentifierProperty(geneId);

        if (bioentityProperty == null) {
            return null;
        }

        String bioentityPageName = BioentityType.get(bioentityProperty.getBioentityType()).getBioentityPageName();

        return "redirect:/" + bioentityPageName + "/" + geneId;


    }

}
