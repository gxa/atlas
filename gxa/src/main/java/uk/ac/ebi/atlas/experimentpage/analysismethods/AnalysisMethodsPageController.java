package uk.ac.ebi.atlas.experimentpage.analysismethods;

import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.controllers.DownloadURLBuilder;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;

@Controller
@Scope("request")
public class AnalysisMethodsPageController {

    private String pdfFileTemplate;
    private final DataFileHub dataFileHub;
    private final ExperimentTrader experimentTrader;

    @Inject
    public AnalysisMethodsPageController(DataFileHub dataFileHub,
                                         ExperimentTrader experimentTrader,
                                         @Value("#{configuration['analysis-methods.pdf.path.template']}") String pdfFileTemplate) {

        this.dataFileHub = dataFileHub;
        this.experimentTrader = experimentTrader;
        this.pdfFileTemplate = pdfFileTemplate;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods", params = {"type=RNASEQ_MRNA_BASELINE"})
    public String baselineAnalysisMethods(@PathVariable String experimentAccession,
                                          @RequestParam(value = "accessKey", required = false) String accessKey,
                                          Model model, HttpServletRequest request) throws IOException {
        return analysisMethods(experimentAccession, model, request.getRequestURI(), accessKey);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods", params = {"type=RNASEQ_MRNA_DIFFERENTIAL"})
    public String rnaSeqAnalysisMethods(@PathVariable String experimentAccession, @RequestParam(value = "accessKey", required = false) String accessKey,Model model, HttpServletRequest request) throws IOException {
        return analysisMethods(experimentAccession, model, request.getRequestURI(), accessKey);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods", params = {"type=MICROARRAY_ANY"})
    public String microArrayAnalysisMethods(@PathVariable String experimentAccession,
                                            @RequestParam(value = "accessKey",required = false) String accessKey,
                                            Model model, HttpServletRequest request) throws IOException {

        return analysisMethods(experimentAccession, model, request.getRequestURI(), accessKey);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods", params = {"type=PROTEOMICS_BASELINE"})
    public String proteomicsAnalysisMethods(@PathVariable String experimentAccession,@RequestParam(value = "accessKey", required = false) String accessKey, Model model, HttpServletRequest request) throws IOException {
        return analysisMethods(experimentAccession, model, request.getRequestURI(), accessKey);
    }

    private String analysisMethods(String experimentAccession, Model model, String requestURI, String accessKey) throws
            IOException {
        Experiment experiment = experimentTrader.getExperiment(experimentAccession,accessKey);

        TsvReader tsvReader = dataFileHub.getExperimentFiles(experimentAccession).analysisMethods.get();

        model.addAttribute("csvLines", tsvReader.readAll());

        model.addAllAttributes(experiment.getAttributes());

        model.addAllAttributes(new DownloadURLBuilder(experimentAccession).dataDownloadUrls(requestURI));

        return "experiment-analysis-methods";
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods/{resource}.pdf", method = RequestMethod.GET)
    public String getAnalysisMethodsPdf(@PathVariable String experimentAccession,@PathVariable String resource) throws IOException {
        if(!hasPDF(experimentAccession, resource)) {
            throw new ResourceNotFoundException("No pdf for " + resource);
        }

        String path = generatePDFPath(experimentAccession, resource);
        return "forward:" + path;
    }

    /***** analysis-methods pdf path ****/
    private boolean hasPDF(String experimentAccession, String resource) throws IOException {
        String path = buildPDFPath(experimentAccession, resource);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

    private String buildPDFPath(String experimentAccession, String resource)  {
        return MessageFormat.format(pdfFileTemplate, experimentAccession, resource);
    }

    public String generatePDFPath(String experimentAccession, String resource) {
        return MessageFormat.format("/expdata/{0}/{1}.pdf", experimentAccession, resource);
    }

}
















