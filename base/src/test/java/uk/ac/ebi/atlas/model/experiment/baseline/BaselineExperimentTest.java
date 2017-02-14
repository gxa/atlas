package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentTest {

    private static final String RUN_ACCESSION1 = "run1";
    private static final String RUN_ACCESSION2 = "run2";
    private static final String PUBMEDID = "PUBMEDID";

    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    @Mock
    private ExperimentDesign experimentDesignMock;


    static List<AssayGroup> assayGroups = ImmutableList.of(new AssayGroup("g1", RUN_ACCESSION1), new AssayGroup
            ("g2",
            RUN_ACCESSION2));


    private BaselineExperiment subject;

    @Before
    public void setUp() throws Exception {



        subject = mockExperiment(experimentalFactorsMock, experimentDesignMock, assayGroups);

    }

    public static BaselineExperiment mockExperiment(ExperimentalFactors experimentalFactors, ExperimentDesign
            experimentDesign, List<AssayGroup> assayGroups){
            return new BaselineExperiment(ExperimentType.RNASEQ_MRNA_BASELINE,"accession", new Date(),
                    experimentalFactors, "description", "displayName", "",
                    new Species("species",
                            SpeciesProperties.create(
                                    "ensemblName", "defaulQueryFactorType",
                                    "kingdom", ImmutableSortedMap.<String, List<String>>of())),
                    true, Sets.newHashSet(PUBMEDID), experimentDesign, assayGroups, Collections.<String>emptyList(),
                    Collections.<String>emptyList(), Collections.<String>emptyList(), new ArrayList<String>(), ExperimentDisplayDefaults.create());

    }

    public static BaselineExperiment mockExperiment(){
        return mockExperiment(mock(ExperimentalFactors.class), mock(ExperimentDesign.class),
                assayGroups);
    }


    @Test
    public void testGetExperimentRunAccessions() throws Exception {
        assertThat(subject.getAnalysedRowsAccessions(), hasItems(RUN_ACCESSION1, RUN_ACCESSION2));
    }

    @Test
    public void testGetExperimentalFactors() throws Exception {
        assertThat(subject.getExperimentalFactors(), is(experimentalFactorsMock));
    }

    @Test
    public void testGetPubMedIds() throws Exception {
        assertThat((Iterable<String>) subject.getAttributes().get("pubMedIds"), contains(PUBMEDID));
    }

    @Test
    public void testGetExperimentDesign() throws Exception {
        assertThat(subject.getExperimentDesign(), is(experimentDesignMock));
    }

    @Test
    public void orderOfAssayGroupsIsPreserved(){
        int num = (int) Math.round(Math.random()*10000);
        List<AssayGroup> assayGroups = new ArrayList<>(num);
        for(int i = 0 ; i< num; i++){
            assayGroups.add(new AssayGroup("id_"+i, "assay_"+i));
        }
        assertThat(mockExperiment(mock(ExperimentalFactors.class), mock(ExperimentDesign.class),
                assayGroups).getDataColumnDescriptors(), is(assayGroups));
    }
}