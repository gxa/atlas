package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class HeatmapTablePageOrganismOriented extends HeatmapTablePage {

    public HeatmapTablePageOrganismOriented(WebDriver driver, String parameters) {
        super(driver, parameters.concat(StringUtils.isBlank(parameters)?"?":"&").concat("organismOriented"));
    }

    @Override
    public List<String> getOrganismParts() {
        return getFirstColumnValues(getHeatmapTable());
    }

    @Override
    public List<String> getSelectedGenes() {
        List<String> geneNames = getTableHeaders(getHeatmapTable());
        //and we need to remove the last header value, because is related to the organism part column
        return geneNames.subList(1, geneNames.size());
    }

    @Override
    public List<String> getFirstGeneProfile() {
        return getSecondColumnValues(getHeatmapTable());
    }

    @Override
    public List<String> getLastGeneProfile() {
        return getLastColumnValues(getHeatmapTable());
    }

}
