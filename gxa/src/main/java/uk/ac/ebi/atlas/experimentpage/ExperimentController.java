package uk.ac.ebi.atlas.experimentpage;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.gson.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.controllers.rest.experimentdesign.ExperimentDesignFile;
import uk.ac.ebi.atlas.experimentpage.baseline.genedistribution.BaselineBarChartController;
import uk.ac.ebi.atlas.experimentpage.qc.MicroarrayQCFiles;
import uk.ac.ebi.atlas.experimentpage.qc.QCReportController;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesignTable;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ExperimentController extends HtmlExceptionHandlingController {

    private final ExperimentTrader experimentTrader;
    private final ApplicationProperties applicationProperties;
    private final DataFileHub dataFileHub;
    private static final Gson gson = new Gson();
    
    @Inject
    public ExperimentController(ExperimentTrader experimentTrader,
                                ApplicationProperties applicationProperties,
                                DataFileHub dataFileHub){
        this.experimentTrader = experimentTrader;
        this.applicationProperties = applicationProperties;
        this.dataFileHub = dataFileHub;
    }

    @RequestMapping(value = {"/experiments/{experimentAccession}", "/experiments/{experimentAccession}/**"})
    public String showExperimentPage(Model model,
                                     HttpServletRequest request,
                                     @PathVariable String experimentAccession,
                                     @RequestParam(required = false) String accessKey) {
        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);
        model.addAllAttributes(experiment.getAttributes());

        model.addAttribute("content", gson.toJson(experimentPageContentForExperiment(experiment, request, accessKey)));

        return "foundation-experiment-page";
    }

    private JsonObject experimentPageContentForExperiment(final Experiment experiment, final HttpServletRequest request,
                                                  final String accessKey){
        JsonObject result = new JsonObject();

        // the client can't know that otherwise and it needs that!
        result.addProperty("experimentAccession", experiment.getAccession());
        result.addProperty("experimentType", experiment.getType().name());
        result.addProperty("accessKey", accessKey);
        result.addProperty("species", experiment.getSpecies().getReferenceName());

        JsonArray availableTabs = new JsonArray();
        // everything wants to have a heatmap
        availableTabs.add(heatmapTab(
                experiment.groupingsForHeatmap(),
                BaselineBarChartController.geneDistributionUrl(request, experiment.getAccession(), accessKey))
        );

        if(experiment.getType().isDifferential()){
            availableTabs.add(
                    customContentTab("resources", "Plots", "url",
                            new JsonPrimitive(ExternallyAvailableContentController.listResourcesUrl(
                                    experiment.getAccession(), accessKey, ExternallyAvailableContent.ContentType.PLOTS)))
            );
        }

        if(dataFileHub.getExperimentFiles(experiment.getAccession()).experimentDesign.exists()){
            availableTabs.add(
                    experimentDesignTab(new ExperimentDesignTable(experiment).asJson(),
                            ExperimentDesignFile.makeUrl(experiment.getAccession(), accessKey))
            );
        }

        availableTabs.add(
                customContentTab("multipart", "Supplementary Information", "sections", supplementaryInformationTabs(experiment, request, accessKey))
        );

        availableTabs.add(
                customContentTab("resources", "Download", "url",
                        new JsonPrimitive(ExternallyAvailableContentController.listResourcesUrl(
                                experiment.getAccession(), accessKey, ExternallyAvailableContent.ContentType.DATA)))
        );

        result.add("tabs", availableTabs);

        return result;
    }

    private JsonArray supplementaryInformationTabs(final Experiment experiment, final HttpServletRequest request, final String accessKey) {
        JsonArray supplementaryInformationTabs = new JsonArray();
        if(dataFileHub.getExperimentFiles(experiment.getAccession()).analysisMethods.exists()){
            supplementaryInformationTabs.add(customContentTab("static-table", "Analysis Methods", "data",
                    formatTable(dataFileHub.getExperimentFiles(experiment.getAccession()).analysisMethods.get().readAll()
                            )
            ));
        }
        supplementaryInformationTabs.add(
                customContentTab("resources", "Resources", "url",
                        new JsonPrimitive(ExternallyAvailableContentController.listResourcesUrl(
                                experiment.getAccession(), accessKey, ExternallyAvailableContent.ContentType.SUPPLEMENTARY_INFORMATION)))
        );

        if(experiment.getType().isMicroarray() &&
                dataFileHub.getExperimentFiles(experiment.getAccession()).qcFolder.existsAndIsNonEmpty()){
            supplementaryInformationTabs.add(customContentTab("qc-report", "QC Report",
                    "reports",
                    pairsToArrayOfObjects("name", "url",
                            FluentIterable.from(new MicroarrayQCFiles(dataFileHub.getExperimentFiles(experiment.getAccession()).qcFolder)
                                    .getArrayDesignsThatHaveQcReports())
                            .transform(new Function<String, Pair<String, String>>() {
                                @Override
                                public Pair<String, String> apply(String arrayDesign) {
                                    return Pair.of(
                                            "QC for array design " +arrayDesign,
                                            QCReportController.getQcReportUrl(
                                                    request,experiment.getAccession(), arrayDesign, accessKey
                                            )
                                    );
                                }
                            }).toList()
                    )
            ));
        }

        return supplementaryInformationTabs;
    }


    private JsonArray formatTable(List<String []> rows){

        JsonArray result = new JsonArray();
        for(int i = 0 ; i< rows.size() ; i ++){
            //skip empty rows and other unexpected input
            if(rows.get(i).length == 2) {
                result.add(twoElementArray(rows.get(i)[0], rows.get(i)[1]));
            }
        }

        return result;
    }

    private JsonArray pairsToArrayOfObjects(String leftName, String rightName, List<Pair<String, String>> pairs){
        JsonArray result = new JsonArray();
        for(Pair<String, String> p : pairs){
            JsonObject o = new JsonObject();
            o.addProperty(leftName, p.getLeft());
            o.addProperty(rightName, p.getRight());
            result.add(o);
        }
        return result;
    }

    private JsonArray twoElementArray(String x, String y){
        JsonArray result = new JsonArray();
        result.add(new JsonPrimitive(x));
        result.add(new JsonPrimitive(y));
        return result;
    }

    private JsonObject customContentTab(String tabType, String name, String onlyPropName, JsonElement value){
        JsonObject props =  new JsonObject();
        props.add(onlyPropName, value);
        return customContentTab(tabType, name, props);
    }

    private JsonObject customContentTab(String tabType, String name, JsonObject props){
        JsonObject result = new JsonObject();
        result.addProperty("type", tabType);
        result.addProperty("name", name);
        result.add("props", props);
        return result;
    }

    private JsonObject heatmapTab(JsonArray groups, String geneDistributionUrl){
        JsonObject props = new JsonObject();
        props.add("groups", groups);
        props.addProperty("genesDistributedByCutoffUrl", geneDistributionUrl);
        return customContentTab("heatmap", "Results", props);
    }

    private JsonObject experimentDesignTab(JsonObject table, String downloadUrl){
        JsonObject props = new JsonObject();
        props.add("table", table);
        props.addProperty("downloadUrl", downloadUrl);
        return customContentTab("experiment-design", "Experiment Design", props);
    }

}
