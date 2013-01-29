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

package uk.ac.ebi.atlas.web.controllers.page;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.RankGeneProfilesCommand;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.GeneExpressionPrecondition;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.FilterParameters;
import uk.ac.ebi.atlas.streams.RankingParameters;
import uk.ac.ebi.atlas.utils.FilterByMenuBuilder;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.RequestPreferences;
import uk.ac.ebi.atlas.web.controllers.GeneProfilesController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

@Controller
@Scope("request")
public class GeneProfilesPageController extends GeneProfilesController {

    private static final String TSV_FILE_EXTENSION = ".tsv";

    private RankGeneProfilesCommand rankCommand;

    private RankingParameters rankingParameters;
    private ApplicationProperties applicationProperties;

    private ExperimentsCache experimentsCache;

    @Inject
    public GeneProfilesPageController(RankingParameters rankingParameters, RankGeneProfilesCommand rankCommand,
                                      ApplicationProperties applicationProperties,
                                      ExperimentsCache experimentsCache, FilterParameters.Builder filterParameterBuilder, GeneExpressionPrecondition geneExpressionPrecondition) {
        super(filterParameterBuilder, experimentsCache, geneExpressionPrecondition);
        this.rankingParameters = rankingParameters;
        this.applicationProperties = applicationProperties;
        this.rankCommand = rankCommand;
        this.experimentsCache = experimentsCache;
    }

    @RequestMapping("/experiments/{experimentAccession}")
    public String showGeneProfiles(@PathVariable String experimentAccession
            , @ModelAttribute("preferences") @Valid RequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        if (!result.hasErrors()) {

            Experiment experiment = experimentsCache.getExperiment(experimentAccession);

            FilterParameters filterParameters = createFilterParameters(experimentAccession, preferences);

            prepareGeneExpressionPrecondition(preferences, filterParameters);

            rankCommand.setFilteredParameters(filterParameters);

            rankingParameters.setSpecific(preferences.isSpecific());
            rankingParameters.setHeatmapMatrixSize(preferences.getHeatmapMatrixSize());

            GeneProfilesList geneProfiles = rankCommand.apply(experimentAccession);


            model.addAttribute("geneProfiles", geneProfiles);

            model.addAttribute("minExpressionLevel", geneProfiles.getMinExpressionLevel());

            model.addAttribute("maxExpressionLevel", geneProfiles.getMaxExpressionLevel());

            model.addAttribute("totalResultCount", geneProfiles.getTotalResultCount());

            model.addAttribute("requestURI", request.getRequestURI());

            model.addAttribute("experimentAccession", experimentAccession);

            model.addAttribute("formattedQueryFactorType", formatQueryFactorType(filterParameters.getQueryFactorType()));

            model.addAttribute("allFactorValues", experiment.getFactorValues(filterParameters.getQueryFactorType()));

            Set<FactorValue> filterByFactorValues = filterParameters.getFilterFactorValues();

            SortedSet<FactorValue> filteredFactorValues = experiment.getFilteredFactorValues(filterByFactorValues, filterParameters.getQueryFactorType());

            model.addAttribute("heatmapFactorValues", filteredFactorValues);

            // this is currently required for the request preferences filter drop-down multi-selection box
            model.addAttribute("heatmapFactorValueValues", FactorValue.getFactorValuesStrings(filteredFactorValues));

            String specie = experiment.getSpecie();

            model.addAttribute("maleAnatomogramFile", applicationProperties.getAnatomogramFileName(specie, true));

            model.addAttribute("femaleAnatomogramFile", applicationProperties.getAnatomogramFileName(specie, false));

            model.addAttribute("downloadUrl", buildDownloadURL(request));

            // all the following is required for filtering by two factor values chosen from drop down menu
            SortedMap<FactorValue, SortedSet<FactorValue>> validFactorValueCombinations = experiment.getValidFactorValueCombinations();

            SortedMap<String, SortedSet<FactorValue>> allFactorNames = FilterByMenuBuilder.indexFactorValuesByName(validFactorValueCombinations.keySet());

            model.addAttribute("defaultFilterFactorValuesSize", experiment.getDefaultFilterFactorValues().size());

            model.addAttribute("filterByMenu", FilterByMenuBuilder.buildFilterByMenu(allFactorNames, validFactorValueCombinations));

            model.addAttribute("selectedFactorValues", FilterByMenuBuilder.extractSelectedFactorValues(allFactorNames, filterParameters));
        }

        return "experiment";
    }

    String formatQueryFactorType(String queryFactorType) {
        // this formats the default factor type for display on web page
        String result = queryFactorType.replaceAll("_", " ").toLowerCase();
        result = result.substring(0, 1).toUpperCase() + result.substring(1);
        return result;
    }

    String buildDownloadURL(HttpServletRequest request) {
        return request.getRequestURI() + TSV_FILE_EXTENSION + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }
}
















