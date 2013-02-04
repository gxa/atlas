package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import javax.inject.Inject;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ExperimentIT {

    @Inject
    private ExperimentsCache experimentsCache;

    private Experiment subject;

    @Before
    public void initSubject(){

        subject = experimentsCache.getExperiment("E-GEOD-26284");

    }

    @Test
    public void getFactorsByTypeTest(){
        assertThat(subject.getFactorsByType("MATERIAL_TYPE").size(),is(3));
        assertThat(subject.getFactorsByType("CELL_LINE").size(),is(23));
        assertThat(subject.getFactorsByType("CELLULAR_COMPONENT").size(),is(6));
    }

    @Test
    public void getCellLineFilteredFactorsTest(){
        Factor filterFactor1 = new Factor("MATERIAL_TYPE","total rna");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT","whole cell");

        SortedSet<Factor> filteredFactors = subject.getFilteredFactors(Sets.newHashSet(filterFactor1,  filterFactor2), "CELL_LINE");

        assertThat(filteredFactors.size(),is(5));
        assertThat(filteredFactors.first().getValue(), is("cd34-positive mobilized cell cell line"));
        assertThat(filteredFactors.last().getValue(), is("imr-90"));
    }

    @Test
    public void getMaterialTypeFilteredFactorsTest(){
        Factor filterFactor1 = new Factor("CELL_LINE","imr-90");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT","whole cell");

        SortedSet<Factor> filteredFactors = subject.getFilteredFactors(Sets.newHashSet(filterFactor1,  filterFactor2), "MATERIAL_TYPE");

        assertThat(filteredFactors.size(),is(2));
        assertThat(filteredFactors.first().getValue(), is("long polya rna"));
        assertThat(filteredFactors.last().getValue(), is("total rna"));
    }

    @Test
    public void getValidFactorCombinationsTest(){

        assertThat(subject.getValidFactorCombinations().keySet().size(),is(32));
        assertThat(subject.getValidFactorCombinations().keys().size(),is(198));
        assertThat(subject.getValidFactorCombinations().size(),is(198));

        Factor factor = new Factor("MATERIAL_TYPE","total rna");
        assertThat(subject.getValidFactorCombinations().get(factor).size(),is(10));
        factor = new Factor("MATERIAL_TYPE","long polya rna");
        assertThat(subject.getValidFactorCombinations().get(factor).size(),is(21));
        factor = new Factor("CELLULAR_COMPONENT","whole cell");
        assertThat(subject.getValidFactorCombinations().get(factor).size(),is(26));
        factor = new Factor("CELL_LINE","imr-90");
        assertThat(subject.getValidFactorCombinations().get(factor).size(),is(5));
        factor = new Factor("CELL_LINE","cd34-positive mobilized cell cell line");
        assertThat(subject.getValidFactorCombinations().get(factor).size(),is(2));


        int objectCount = 32;
        for (Factor keyFactor: subject.getValidFactorCombinations().keySet()){
            objectCount += subject.getValidFactorCombinations().get(keyFactor).size();
        }

        assertThat(objectCount, is(230));

        objectCount = 198 + subject.getValidFactorCombinations().values().size();

    }

    @Test(expected = IllegalArgumentException.class)
    public void geFilteredFactorsShouldFailIfQueryFactorTypeIsEqualToTheTypeOfOneOfTheFilterFactors(){
        Factor filterFactor1 = new Factor("CELL_LINE","imr-90");
        Factor filterFactor2 = new Factor("CELLULAR_COMPONENT","whole cell");

        SortedSet<Factor> filteredFactors = subject.getFilteredFactors(Sets.newHashSet(filterFactor1,  filterFactor2), "CELL_LINE");
    }


    @Test
    public void getCellularComponentFilteredFactorsTest(){
        Factor filterFactor1 = new Factor("CELL_LINE","imr-90");
        Factor filterFactor2 = new Factor("MATERIAL_TYPE","total rna");

        SortedSet<Factor> filteredFactors = subject.getFilteredFactors(Sets.newHashSet(filterFactor1,  filterFactor2), "CELLULAR_COMPONENT");

        assertThat(filteredFactors.size(),is(1));
        assertThat(filteredFactors.first().getValue(), is("whole cell"));
    }

}
