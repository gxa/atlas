package uk.ac.ebi.atlas.controllers.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.solr.bioentities.query.SolrBioentitiesSuggesterService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;

@RestController
@Scope("request")
public class AutoCompleteController extends JsonExceptionHandlingController {
    private final SolrBioentitiesSuggesterService suggesterService;
    private final SpeciesFactory speciesFactory;

    @Inject
    public AutoCompleteController(SolrBioentitiesSuggesterService suggesterService, SpeciesFactory speciesFactory) {
        this.suggesterService = suggesterService;
        this.speciesFactory = speciesFactory;
    }

    @RequestMapping(value = "/json/suggestions",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public String fetchTopSuggestions(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "species", required = false, defaultValue = "") String species,
            @RequestParam(value = "suggestCount", required = false, defaultValue = "15") int suggestCount) {
        return gson.toJson(
                suggesterService.fetchPropertySuggestions(query, speciesFactory.create(species), suggestCount));
    }
}
