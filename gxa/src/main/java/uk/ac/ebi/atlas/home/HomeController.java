package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experiments.ExperimentInfoListService;
import uk.ac.ebi.atlas.species.AtlasInformationDao;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.Map;
import java.util.Random;

import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.PROTEOMICS_BASELINE;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.RNASEQ_MRNA_BASELINE;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.RNASEQ_MRNA_DIFFERENTIAL;
import static uk.ac.ebi.atlas.species.AtlasInformationDataType.EFO;
import static uk.ac.ebi.atlas.species.AtlasInformationDataType.ENSEMBL;
import static uk.ac.ebi.atlas.species.AtlasInformationDataType.GENOMES;
import static uk.ac.ebi.atlas.species.AtlasInformationDataType.PARASITE;

@Controller
public class HomeController {
    @Autowired
    protected Environment env;

    private static final String NORMAL_SEPARATOR = "━━━━━━━━━━━━━━━━━";
    private static final String BEST_SEPARATOR = "(╯°□°）╯︵ ┻━┻";
    private static final double EASTER_EGG_PROBABILITY = 0.001;
    private static final Random RANDOM = new Random();

    private final SpeciesPropertiesTrader speciesPropertiesTrader;
    private final AtlasInformationDao atlasInformationDao;
    private final ExperimentInfoListService experimentInfoListService;

    public HomeController(SpeciesPropertiesTrader speciesPropertiesTrader,
                          ExpressionAtlasExperimentTrader experimentTrader,
                          AtlasInformationDao atlasInformationDao) {
        this.speciesPropertiesTrader = speciesPropertiesTrader;
        this.atlasInformationDao = atlasInformationDao;

        this.experimentInfoListService =
                new ExperimentInfoListService(
                        experimentTrader,
                        ImmutableList.of(
                                RNASEQ_MRNA_BASELINE,
                                PROTEOMICS_BASELINE,
                                RNASEQ_MRNA_DIFFERENTIAL,
                                MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                                MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL,
                                MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL));
    }

    @RequestMapping(value = "/home", produces = "text/html;charset=UTF-8")
    public String getHome(Model model) {
        model.addAttribute(
                "separator", RANDOM.nextDouble() < EASTER_EGG_PROBABILITY ? BEST_SEPARATOR : NORMAL_SEPARATOR);

        ImmutableMap.Builder<String, String> speciesSelectBuilder = ImmutableMap.builder();
        for (SpeciesProperties speciesProperties : speciesPropertiesTrader.getAll()) {
            speciesSelectBuilder.put(
                    speciesProperties.referenceName(), StringUtils.capitalize(speciesProperties.referenceName()));
        }

        model.addAttribute("species", speciesSelectBuilder.build());
        model.addAttribute("speciesPath", ""); // Required by Spring form tag
        model.addAttribute("numberOfStudies", experimentInfoListService.listPublicExperiments().size());

        long numberOfSpecies =
                experimentInfoListService.listPublicExperiments().stream()
                        .map(ExperimentInfo::getSpecies)
                        .distinct()
                        .count();
        model.addAttribute("numberOfSpecies", numberOfSpecies);

        int numberOfAssays =
                experimentInfoListService.listPublicExperiments().stream()
                        .mapToInt(ExperimentInfo::getNumberOfAssays)
                        .sum();
        model.addAttribute("numberOfAssays", numberOfAssays);

        Map<String, String> atlasInformation = atlasInformationDao.fetchAll();
        model.addAttribute("info", atlasInformation);
        model.addAttribute("ensembl", ENSEMBL.getId());
        model.addAttribute("genomes", GENOMES.getId());
        model.addAttribute("paraSite", PARASITE.getId());
        model.addAttribute("efo", EFO.getId());
        
        return "home";
    }
}
