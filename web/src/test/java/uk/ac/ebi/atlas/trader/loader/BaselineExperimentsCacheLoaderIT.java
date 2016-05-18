
//ToDo ... this is impossible to test without bootstrapping spring, as most other IT tests


package uk.ac.ebi.atlas.trader.loader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExperimentsCacheLoaderIT {

    @Inject
    private BaselineExperimentsCacheLoader subject;


    @Test
    public void correctSpeciesReadFromDatabase() throws Exception {
        //given
        BaselineExperiment experiment = subject.load("E-MTAB-513");
        //then
        Set<String> species = experiment.getOrganisms();
        assertThat(species, hasItems("Homo sapiens"));
        assertThat(species, not(hasItem("Ornithorhynchus anatinus")));
    }


    @Test
    public void experimentShouldOnlyContainRunsFromDataFile() throws IOException {
        BaselineExperiment experiment = subject.load("E-MTAB-513");

        assertThat(experiment.getExperimentRunAccessions(), hasItems(
            "ERR030872", "ERR030873", "ERR030874", "ERR030875",
            "ERR030876", "ERR030877", "ERR030878", "ERR030879",
            "ERR030880", "ERR030881", "ERR030882", "ERR030883",
            "ERR030884", "ERR030885", "ERR030886", "ERR030887"
        ));

    }

    @Test
    public void experimentShouldContainAssayGroups() throws IOException {
        BaselineExperiment experiment = subject.load("E-MTAB-513");

        assertThat(experiment.getAssayGroups().getAssayGroupIds(), hasSize(16));
    }

    @Test(expected = IllegalStateException.class)
    public void loadNonExistentExperimentThrowsIllegalStateException() throws IOException {
        subject.load("FOOBAR");
    }

}
