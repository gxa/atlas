package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class BaselineAnalyticsLoader implements AnalyticsLoader {

    private final BaselineAnalyticsDao baselineAnalyticsDao;
    private final BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;

    @Inject
    public BaselineAnalyticsLoader(BaselineAnalyticsDao baselineAnalyticsDao, BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory) {
        this.baselineAnalyticsDao = baselineAnalyticsDao;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
    }

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
        loadBaselineExpressions(accession);
    }

    private void loadBaselineExpressions(String accession) {
        BaselineAnalyticsInputStream baselineAnalyticsInputStream = baselineAnalyticsInputStreamFactory.create(accession);
        baselineAnalyticsDao.loadAnalytics(accession, baselineAnalyticsInputStream);
    }

    @Override
    @Transactional
    public void deleteAnalytics(String accession) {
        baselineAnalyticsDao.deleteAnalytics(accession);
    }

}
