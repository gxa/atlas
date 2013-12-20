package uk.ac.ebi.atlas.experimentloader.analytics.differential.microarray;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

@Named
public class MicroarrayDifferentialAnalyticsInputStreamFactory {

    private final CsvReaderFactory csvReaderFactory;
    private final String fileTemplate;

    @Inject
    public MicroarrayDifferentialAnalyticsInputStreamFactory(@Value("#{configuration['microarray.experiment.data.path.template']}")
                                                             String fileTemplate,
                                                             CsvReaderFactory csvReaderFactory) {
        this.fileTemplate = fileTemplate;
        this.csvReaderFactory = csvReaderFactory;
    }

    public MicroarrayDifferentialAnalyticsInputStream create(String experimentAccession, String arrayDesignAccession) {
        String tsvFilePath = MessageFormat.format(fileTemplate, experimentAccession, arrayDesignAccession);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvFilePath);
        return new MicroarrayDifferentialAnalyticsInputStream(csvReader, tsvFilePath);
    }
}
