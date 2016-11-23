
package uk.ac.ebi.atlas.experimentpage.differential.microarray.geod3307;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class MicroArray2ArrayDesignsHeatmapTableWithDifferentRegulationsButDefaultQueryParamsSIT extends SeleniumFixture {

    private static final String E_GEOD_3307_ACCESSION = "E-GEOD-3307";
    protected HeatmapTablePage subject;

    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, E_GEOD_3307_ACCESSION, "regulation=UP&displayLevels=true&cutoff=1&foldChangeCutOff=0");
        subject.get();
        assertThat(subject.getGeneCount(), is("Showing 50 of 183 genes found:"));

        assertThat(subject.getGeneNames().size(), is(50));
        assertThat(subject.getGeneNames().subList(0, 3), contains("HS3ST1", "ICA1", "SLC7A2"));

        assertThat(subject.getGeneProfile(1).size(), is(24));
        assertThat(subject.getGeneProfile(1).get(0), is(""));
        assertThat(subject.getGeneProfile(1).get(6), is("0.2"));

        assertThat(subject.getLastGeneProfile().size(), is(24));
        assertThat(subject.getLastGeneProfile().get(16), is("0.1"));
        assertThat(subject.getLastGeneProfile().get(13), is("0"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, E_GEOD_3307_ACCESSION, "regulation=DOWN&displayLevels=true&cutoff=1&foldChangeCutOff=0");
        subject.get();
        assertThat(subject.getGeneCount(), is("Showing 50 of 188 genes found:"));

        assertThat(subject.getGeneNames().size(), is(50));
        assertThat(subject.getGeneNames().subList(0, 3), contains("PDK4", "LAP3", "CFH"));

        assertThat(subject.getGeneProfile(1).size(), is(24));
        assertThat(subject.getGeneProfile(1).get(18), is("-1"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(24));
        assertThat(subject.getLastGeneProfile().get(17), is("-0.1"));
        assertThat(subject.getLastGeneProfile().get(1), is(""));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_GEOD_3307_ACCESSION, "regulation=UP_DOWN&displayLevels=true&cutoff=1&foldChangeCutOff=0");
        subject.get();
        assertThat(subject.getGeneCount(), is("Showing 50 of 200 genes found:"));

        assertThat(subject.getGeneNames().size(), is(50));
        assertThat(subject.getGeneNames().subList(0, 3), contains("MTMR7", "CFTR", "KMT2E"));

        assertThat(subject.getGeneProfile(1).size(), is(24));
        assertThat(subject.getGeneProfile(1).get(7), is("-0"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(24));
        assertThat(subject.getLastGeneProfile().get(12), is("-0.1"));
        assertThat(subject.getLastGeneProfile().get(19), is("-0.3"));
    }

    @Test
    public void heatmapCellTooltipTest() {
        subject = new HeatmapTablePage(driver, E_GEOD_3307_ACCESSION, "regulation=UP_DOWN&displayLevels=true&cutoff=1&foldChangeCutOff=0");
        subject.get();

        // dismiss cookie notice otherwise we get a Element cannot be scrolled into view
        subject.dismissCookieNotice();

        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 7, 0, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("Adjusted p-value"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 7, 1, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("t-statistic"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 7, 2, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), startsWith("Log2-fold"));

        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 0, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("0.181"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 1, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("-1.48"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 2, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("-0"));
    }

}
