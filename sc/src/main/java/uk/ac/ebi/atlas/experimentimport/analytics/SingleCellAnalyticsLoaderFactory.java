package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.SingleCellAnalyticsLoader;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellAnalyticsLoaderFactory implements AnalyticsLoaderFactory {

    private final SingleCellAnalyticsLoader singleCellAnalyticsLoader;

    @Inject
    public SingleCellAnalyticsLoaderFactory(SingleCellAnalyticsLoader singleCellAnalyticsLoader) {
        this.singleCellAnalyticsLoader = singleCellAnalyticsLoader;
    }

    @Override
    public AnalyticsLoader getLoader(ExperimentType experimentType) {
        return singleCellAnalyticsLoader;
    }
}
