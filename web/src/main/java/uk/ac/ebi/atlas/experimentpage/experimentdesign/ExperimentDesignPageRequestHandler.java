
package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import com.google.gson.Gson;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public abstract class ExperimentDesignPageRequestHandler<T extends Experiment> {

    private DownloadURLBuilder downloadURLBuilder;

    protected ArrayDesignTrader arrayDesignTrader;

    protected static final String ALL_ARRAY_DESIGNS_ATTRIBUTE = "allArrayDesigns";

    @Inject
    void setDownloadURLBuilder(DownloadURLBuilder downloadURLBuilder) {
        this.downloadURLBuilder = downloadURLBuilder;
    }

    @Inject
    public void setArrayDesignTrader(ArrayDesignTrader arrayDesignTrader) {
        this.arrayDesignTrader = arrayDesignTrader;
    }

    public String handleRequest(Model model, HttpServletRequest request) {
        T experiment = (T) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        String experimentAccession = experiment.getAccession();

        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        // does the serialisation to JSON
        Gson gson = new Gson();
        // add table data to model
        List<String> assayHeaders = experimentDesign.getAssayHeaders();
        model.addAttribute("assayHeaders", gson.toJson(assayHeaders));
        model.addAttribute("sampleHeaders", gson.toJson(experimentDesign.getSampleHeaders()));
        model.addAttribute("factorHeaders", gson.toJson(experimentDesign.getFactorHeaders()));
        model.addAttribute("tableData", gson.toJson(experimentDesign.asTableData()));

        //analysed row accessions are added to the model separately,
        //because design table rows can be hidden or shown depending if they are or not part of the analysed subset
        String runAccessions = gson.toJson(getAnalysedRowsAccessions(experiment));
        model.addAttribute("runAccessions", runAccessions);

        // add general experiment attributes to model
        model.addAttribute("experimentAccession", experimentAccession);

        model.addAllAttributes(downloadURLBuilder.dataDownloadUrls(request));

        extendModel(model, experiment, experimentAccession);

        model.addAllAttributes(experiment.getAttributes());

        model.addAttribute("preferences", new ExperimentDesignPageRequestPreferences());

        return "experiment-experiment-design";
    }

    protected abstract void extendModel(Model model, T experiment, String experimentAccession);

    protected abstract Set<String> getAnalysedRowsAccessions(T experiment);

    static class ExperimentDesignPageRequestPreferences extends BaselineRequestPreferences {

        /*Fixes the experiment-design magic Spring form element*/
        public String getSelectedContrast(){
            return "";
        }
    }

}
















