package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Scope("request")
public class ProteomicsBaselineExperimentDownloadController extends BaselineExperimentDownloadController {

    @Inject
    public ProteomicsBaselineExperimentDownloadController(BaselineRequestContextBuilder requestContextBuilder,
                                                          FilterFactorsConverter filterFactorsConverter,
                                                          ProteomicsBaselineProfilesWriter proteomicsBaselineProfilesWriter) {
        super(requestContextBuilder, filterFactorsConverter, proteomicsBaselineProfilesWriter);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = "type=PROTEOMICS_BASELINE")
    public void downloadGeneProfiles(HttpServletRequest request
            , @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        geneProfilesHandler(request, preferences, response);

    }
}
