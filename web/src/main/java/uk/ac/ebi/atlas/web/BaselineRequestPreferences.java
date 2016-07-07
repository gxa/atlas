package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

public class BaselineRequestPreferences extends ExperimentPageRequestPreferences{

    public static final double DEFAULT_CUTOFF = 0.5d;
    private static final String DEFAULT_GENE_QUERY = "protein_coding";

    private static final String DEFAULT_GENE_QUERY_VALUE = "protein_coding";
    private static final String DEFAULT_GENE_QUERY_CATEGORY = "gene_biotype";

    @Override
    protected OldGeneQuery getDefaultGeneQuery() {
        return OldGeneQuery.create(DEFAULT_GENE_QUERY);
    }

    @Override
    protected SemanticQuery getDefaultSemanticQuery() {
        return SemanticQuery.create(SemanticQueryTerm.create(DEFAULT_GENE_QUERY_VALUE, DEFAULT_GENE_QUERY_CATEGORY));
    }

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

    @Override
    public void setCutoff(Double cutoff) {
        if (cutoff != null) {
            super.setCutoff(BaselineExpressionLevelRounder.round(cutoff));
        } else {
            super.setCutoff(null);
        }
    }

}
