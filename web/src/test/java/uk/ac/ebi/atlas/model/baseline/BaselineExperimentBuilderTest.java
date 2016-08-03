
package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentBuilderTest {

    private static final String FACTOR_TYPE = "type";
    private static final String SPECIES = "homo sapiens";
    private static final String SPECIES_NAME = "Homo sapiens";
    private static final String FACTOR_NAME = "name";
    private static final String EXPERIMENT_ACCESSION = "accession";
    private static final String DESCRIPTION = "description";
    private static final String DISPLAY_NAME = "displayName";
    private static final String RUN_ACCESSION1 = "run1";
    private static final String RUN_ACCESSION2 = "run2";
    private static final String PUBMEDID = "PUBMEDID";
    private static final List<String> PROVIDER_URL = Arrays.asList("http://www.provider.com","http://www.provider1.com");
    private static final List<String> PROVIDER_DESCRIPTION = Arrays.asList("Baseline experiment data provider","Another baseline experiment data provider");

    private BaselineExperimentBuilder subject;

    @Mock
    private ExperimentRun runMock1;

    @Mock
    private ExperimentRun runMock2;

    @Mock
    private FactorGroup factorGroupMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Mock
    private ExperimentalFactors experimentalFactors;

    private Map<String, String> nameMap = Maps.newHashMap();

    @Mock
    private AssayGroups assayGroupsMock;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineExperimentBuilder();

        nameMap.put(FACTOR_TYPE, FACTOR_NAME);

        when(runMock1.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock1.getAccession()).thenReturn(RUN_ACCESSION1);
        when(runMock2.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock2.getAccession()).thenReturn(RUN_ACCESSION2);

        when(assayGroupsMock.iterator()).thenReturn(Sets.newHashSet(new AssayGroup("g1", RUN_ACCESSION1), new AssayGroup("g2", RUN_ACCESSION2)).iterator());
        when(assayGroupsMock.getAssayAccessions()).thenReturn(Sets.newHashSet(RUN_ACCESSION1, RUN_ACCESSION2));
        when(assayGroupsMock.getAssayGroupIds()).thenReturn(Sets.newHashSet("g1", "g2"));
    }

    @Test
    public void testCreate() throws Exception {

        BaselineExperiment experiment = subject
                .forSpecies(new Species(SPECIES_NAME,SPECIES,"kingdom","ensembl_db"))
                .ofType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .withAccession(EXPERIMENT_ACCESSION)
                .withDescription(DESCRIPTION)
                .withDisplayName(DISPLAY_NAME)
                .withExtraInfo(false)
                .withPubMedIds(Sets.newHashSet(PUBMEDID))
                .withExperimentDesign(experimentDesignMock)
                .withExperimentalFactors(experimentalFactors)
                .withAssayGroups(assayGroupsMock)
                .withDataProviderURL(PROVIDER_URL)
                .withDataProviderDescription(PROVIDER_DESCRIPTION)
                .create();

        Map<String, ?> attributes = experiment.getAttributes();

        assertEquals(EXPERIMENT_ACCESSION,attributes.get("experimentAccession"));
        assertEquals(DESCRIPTION,attributes.get("pageDescription"));
        assertEquals(PROVIDER_URL,attributes.get("dataProviderURL"));
        assertEquals(PROVIDER_DESCRIPTION,attributes.get("dataProviderDescription"));
        assertThat((Collection<String>) attributes.get("pubMedIds"), Matchers.contains(PUBMEDID));

    }
}