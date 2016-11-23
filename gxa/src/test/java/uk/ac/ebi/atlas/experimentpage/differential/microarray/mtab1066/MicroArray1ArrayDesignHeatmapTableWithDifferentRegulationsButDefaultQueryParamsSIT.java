
package uk.ac.ebi.atlas.experimentpage.differential.microarray.mtab1066;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class MicroArray1ArrayDesignHeatmapTableWithDifferentRegulationsButDefaultQueryParamsSIT extends SeleniumFixture {

    private static final String E_MTAB_1066_ACCESSION = "E-MTAB-1066";
    protected HeatmapTablePage subject;

    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 21"));

        assertThat(subject.getGeneNames().size(), is(21));
        assertThat(subject.getGeneNames().subList(0, 3), contains("Cda5", "CG33459", "CG11147"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is("1.4"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is("1.3"));
        assertThat(subject.getLastGeneProfile().get(1), is("1.1"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 33"));

        assertThat(subject.getGeneNames().size(), is(33));
        assertThat(subject.getGeneNames().subList(0, 3), contains("CG31624", "CG4669", "Mst84Db"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is("-3.4"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is("-1.2"));
        assertThat(subject.getLastGeneProfile().get(1), is("-1.1"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 54"));

        assertThat(subject.getGeneNames().size(), is(50));
        assertThat(subject.getGeneNames().subList(0, 3), contains("CG31624", "CG4669", "Mst84Db"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is("-3.4"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is("1.5"));
        assertThat(subject.getLastGeneProfile().get(1), is("1.4"));
    }

    //This will fail with PhantomJS
    @Test
    public void heatmapCellTooltipTest() {
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();

        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 0, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("Adjusted p-value"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 1, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("t-statistic"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 2, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), startsWith("Log2-fold"));

        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 0, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("3.24 × 10-4"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 1, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("-8.79"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 2, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("-3.4"));
    }

}
