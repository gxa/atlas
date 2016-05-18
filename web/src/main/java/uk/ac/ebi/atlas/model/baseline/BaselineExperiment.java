
package uk.ac.ebi.atlas.model.baseline;

import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.Regulation;

import java.util.*;

public class BaselineExperiment extends Experiment {

    private ExperimentalFactors experimentalFactors;
    private AssayGroups assayGroups;

    private List<String> dataProviderURL;
    private List<String> dataProviderDescription;

    BaselineExperiment(String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description, String displayName, Set<String> organisms, String kingdom, String ensemblDB,
                       Map<String, String> speciesMapping, boolean hasExtraInfoFile, boolean hasRData, Set<String> pubMedIds,
                       ExperimentDesign experimentDesign, AssayGroups assayGroups) {
        this(
                accession, lastUpdate, experimentalFactors, description, displayName, organisms, kingdom, ensemblDB, speciesMapping,
                hasExtraInfoFile, hasRData, pubMedIds, experimentDesign,  assayGroups, Collections.<String>emptyList(), Collections.<String>emptyList());
    }

    BaselineExperiment(String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description, String displayName, Set<String> organisms, String kingdom, String ensemblDB,
                       Map<String, String> speciesMapping, boolean hasExtraInfoFile, boolean hasRData, Set<String> pubMedIds,
                       ExperimentDesign experimentDesign, AssayGroups assayGroups, List<String> dataProviderURL, List<String> dataProviderDescription) {
        this(
                ExperimentType.RNASEQ_MRNA_BASELINE, accession, lastUpdate, experimentalFactors,
                description, displayName, organisms, kingdom, ensemblDB, speciesMapping,
                hasExtraInfoFile, hasRData, pubMedIds, experimentDesign, assayGroups, dataProviderURL, dataProviderDescription);
    }

    BaselineExperiment(ExperimentType experimentType, String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description, String displayName, Set<String> organisms, String kingdom, String ensemblDB,
                       Map<String, String> speciesMapping, boolean hasExtraInfoFile, boolean hasRData, Set<String> pubMedIds,
                       ExperimentDesign experimentDesign, AssayGroups assayGroups) {
        this(
                experimentType, accession, lastUpdate, experimentalFactors, description, displayName, organisms, kingdom,
                ensemblDB, speciesMapping, hasExtraInfoFile, hasRData, pubMedIds, experimentDesign, assayGroups, Collections.<String>emptyList(),
                Collections.<String>emptyList());
    }

    BaselineExperiment(ExperimentType experimentType, String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description, String displayName, Set <String> organisms, String kingdom, String ensemblDB, Map <String, String> speciesMapping,
                       boolean hasExtraInfoFile, boolean hasRData, Set<String> pubMedIds, ExperimentDesign experimentDesign, AssayGroups assayGroups, List<String> dataProviderURL, List<String> dataProviderDescription) {
        super(experimentType, accession, lastUpdate, displayName, description, hasExtraInfoFile, hasRData, organisms, kingdom, ensemblDB, speciesMapping, pubMedIds, experimentDesign);
        this.experimentalFactors = experimentalFactors;
        this.assayGroups = assayGroups;
        this.dataProviderURL = dataProviderURL;
        this.dataProviderDescription = dataProviderDescription;
    }

    public Set<String> getExperimentRunAccessions() {
        return assayGroups.getAssayAccessions();
    }

    public ExperimentalFactors getExperimentalFactors() {
        return experimentalFactors;
    }

    public AssayGroups getAssayGroups() {
        return assayGroups;
    }

    public List<String> getDataProviderURL() {
        return dataProviderURL;
    }

    public List<String> getDataProviderDescription() {
        return dataProviderDescription;
    }

    public SortedSet<Factor> getAssayGroupFactors(Collection<String> assayGroupIds, String factorType) {
        return getExperimentalFactors().getFactors(assayGroupIds, factorType);
    }

    public boolean isTissueExperiment() {
        return getExperimentalFactors().getDefaultQueryFactorType().equals("ORGANISM_PART");
    }

    public Map<String, ?> getBaselineAttributes(){
        Map<String, Object> result = new HashMap<>();
        result.putAll(super.getAttributes());

        // required to show link to one or more data providers on baseline page (if they were provided in <expAcc>-factors.xml file)
        result.put("dataProviderURL", getDataProviderURL());
        result.put("dataProviderDescription", getDataProviderDescription());

        return result;
    }
}
