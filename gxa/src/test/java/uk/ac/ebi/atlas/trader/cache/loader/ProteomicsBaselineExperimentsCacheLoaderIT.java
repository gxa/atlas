package uk.ac.ebi.atlas.trader.cache.loader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.experimentimport.GxaExperimentDao;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;

import javax.inject.Inject;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
public class ProteomicsBaselineExperimentsCacheLoaderIT {
    private static final String E_PROT_1 = "E-PROT-1";
    private static final String DEVELOPMENTAL_STAGE = "developmental stage";
    private static final String ORGANISM_PART = "organism part";
    private static final String ORGANISM = "organism";

    @Inject
    private ProteomicsBaselineExperimentFactory proteomicsBaselineExperimentFactory;

    @Mock
    private GxaExperimentDao expressionAtlasExperimentDao;

    @Inject
    private ExperimentDesignParser experimentDesignParser;

    @Inject
    private IdfParser idfParser;

    private ExperimentsCacheLoader<BaselineExperiment> subject;

    @Before
    public void mockOutDatabaseAndArrayExpress() {
        MockitoAnnotations.initMocks(this);

        ExperimentDTO experimentDTO = new ExperimentDTO(
                E_PROT_1,
                ExperimentType.PROTEOMICS_BASELINE,
                "Homo sapiens",
                Collections.emptySet(),
                Collections.emptySet(),
                "title",
                new Date(),
                false,
                UUID.randomUUID().toString());
        when(expressionAtlasExperimentDao.getExperimentAsAdmin(E_PROT_1)).thenReturn(experimentDTO);

        subject =
                new ExperimentsCacheLoader<>(
                        experimentDesignParser, expressionAtlasExperimentDao, proteomicsBaselineExperimentFactory, idfParser);
    }

    @Test
    public void correctSpeciesReadFromDatabase() {
        //given
        BaselineExperiment experiment = subject.load(E_PROT_1);
        //then
        String species = experiment.getSpecies().getName();
        assertThat(species, is("Homo sapiens"));

    }

    @Test
    public void experimentShouldOnlyContainRunsFromDataFile() {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        assertThat(experiment.getAnalysedAssays(), containsInAnyOrder(
                "Adult_Adrenalgland", "Adult_Bcells", "Adult_CD4Tcells", "Adult_CD8Tcells",
                "Adult_Colon", "Adult_Esophagus", "Adult_Frontalcortex", "Adult_Gallbladder",
                "Adult_Heart", "Adult_Kidney", "Adult_Liver", "Adult_Lung",
                "Adult_Monocytes", "Adult_NKcells", "Adult_Ovary", "Adult_Pancreas",
                "Adult_Platelets", "Adult_Prostate", "Adult_Rectum", "Adult_Retina",
                "Adult_Spinalcord", "Adult_Testis", "Adult_Urinarybladder", "Fetal_Brain",
                "Fetal_Gut", "Fetal_Heart", "Fetal_Liver", "Fetal_Ovary",
                "Fetal_Placenta", "Fetal_Testis"
        ));

    }

    @Test
    public void experimentShouldContainAssayGroups() {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        Set<String> allAssayGroupIds = new HashSet<>();
        for (AssayGroup assayGroup : experiment.getDataColumnDescriptors()) {
            allAssayGroupIds.add(assayGroup.getId());
        }

        assertThat(allAssayGroupIds, containsInAnyOrder("g1", "g2", "g3", "g4", "g5", "g6",
                "g7", "g8", "g9", "g10", "g11", "g12", "g13", "g14", "g15", "g16", "g17", "g18", "g19", "g20", "g21",
                "g22", "g23", "g24", "g25", "g26", "g27", "g28", "g29", "g30"));
    }

    @Test
    public void experimentalFactors() {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        FactorGroup adultAdrenal =
                new FactorSet()
                        .add(new Factor(DEVELOPMENTAL_STAGE, "adult")).add(new Factor(ORGANISM_PART, "adrenal gland"));
        FactorGroup fetusTestis =
                new FactorSet()
                        .add(new Factor(DEVELOPMENTAL_STAGE, "fetus")).add(new Factor(ORGANISM_PART, "testis"));

        assertThat(experiment.getFactors(experiment.getDataColumnDescriptor("g1")), is(adultAdrenal));
        assertThat(experiment.getFactors(experiment.getDataColumnDescriptor("g30")), is(fetusTestis));
    }

    @Test
    public void experimentDesign() {
        BaselineExperiment experiment = subject.load(E_PROT_1);

        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        assertThat(experimentDesign.getFactorHeaders(), contains(DEVELOPMENTAL_STAGE, ORGANISM_PART));
        assertThat(experimentDesign.getSampleHeaders(), contains(DEVELOPMENTAL_STAGE, ORGANISM, ORGANISM_PART));

        Iterator<SampleCharacteristic> sampleCharacteristicIterator =
                experimentDesign.getSampleCharacteristics("Adult_Ovary").iterator();

        SampleCharacteristic sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), is(ORGANISM));
        assertThat(sampleCharacteristic.value(), is("Homo sapiens"));
        assertThat(
                sampleCharacteristic.valueOntologyTerms().iterator().next().uri(),
                is("http://purl.obolibrary.org/obo/NCBITaxon_9606"));

        sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), is(ORGANISM_PART));
        assertThat(sampleCharacteristic.value(), is("ovary"));
        assertThat(
                sampleCharacteristic.valueOntologyTerms().iterator().next().uri(),
                is("http://www.ebi.ac.uk/efo/EFO_0000973"));

        sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), is(DEVELOPMENTAL_STAGE));
        assertThat(sampleCharacteristic.value(), is("adult"));
        assertThat(
                sampleCharacteristic.valueOntologyTerms().iterator().next().uri(),
                is("http://www.ebi.ac.uk/efo/EFO_0001272"));
    }
}
