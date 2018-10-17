package uk.ac.ebi.atlas.metadata;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CellMetadataServiceIT {
    // Ideally we would retrieve a random experiment accession, but not all experiments have the inferred cell
    // type characteristic
    private static final String EXPERIMENT_ACCESSION = "E-MTAB-5061";
    private static final String EXPERIMENT_WITHOUT_METADATA_ACCESSION = "E-GEOD-99058";

    @Inject
    private DataSource dataSource;
    @Inject
    private IdfParser idfParser;
    @Inject
    private CellMetadataDao cellMetadataDao;
    @Inject
    private JdbcUtils jdbcUtils;

    private CellMetadataService subject;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-fixture.sql"),
                new ClassPathResource("fixtures/scxa_analytics-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-delete.sql"),
                new ClassPathResource("fixtures/scxa_analytics-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.subject = new CellMetadataService(idfParser, cellMetadataDao);
    }

    @Test
    void existingInferredCellType() {
        String cellId = jdbcUtils.fetchRandomCellFromExperiment(EXPERIMENT_ACCESSION);
        assertThat(
                subject.getInferredCellType(
                        EXPERIMENT_ACCESSION,
                        cellId))
                .isPresent();
    }

    @Test
    void inferredCellTypeForInvalidExperimentId() {
        assertThat(subject.getInferredCellType("FOO", "FOO")).isNotPresent();
    }

    @Test
    void inferredCellTypeForInvalidCellId() {
        assertThat(subject.getInferredCellType(EXPERIMENT_ACCESSION, "FOO")).isNotPresent();
    }

    @ParameterizedTest
    @MethodSource("experimentsWithFactorsProvider")
    void factorsForValidExperimentAccession(String experimentAccession) {
        String cellId = jdbcUtils.fetchRandomCellFromExperiment(experimentAccession);

        assertThat(subject.getFactors(experimentAccession, cellId)).isNotEmpty();
    }

    @Test
    void factorsForInvalidExperiment() {
        assertThat(subject.getFactors("FOO", "FOO")).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("experimentsWithMetadataProvider")
    void metadataForValidExperimentAccession(String experimentAccession) {
        String cellId = jdbcUtils.fetchRandomCellFromExperiment(experimentAccession);

        assertThat(subject.getMetadata(experimentAccession, cellId)).isNotEmpty();
    }

    @Test
    void metadataForInvalidExperiment() {
        assertThat(subject.getMetadata("FOO", "FOO")).isEmpty();
    }

    @Test
    void experimentWithMetadataFieldsInIdf() {
        // Ideally we would retrieve a random experiment accession, but not all experiments have curated metadata
        // files in the IDF file
        assertThat(
                subject.getIdfFileAttributes(
                        EXPERIMENT_ACCESSION,
                        jdbcUtils.fetchRandomCellFromExperiment(EXPERIMENT_ACCESSION)))
                .isNotEmpty()
                .containsOnlyKeys("characteristic_individual");
    }

    @Test
    void experimentWithoutMetadataFieldsInIdf() {
        String experimentAccession = EXPERIMENT_WITHOUT_METADATA_ACCESSION;    // Empty Comment[EAAdditionalAttributes] in IDF file

        assertThat(
                subject.getIdfFileAttributes(
                        experimentAccession,
                        jdbcUtils.fetchRandomCellFromExperiment(experimentAccession)))
                .isEmpty();

        assertThat(
                subject.getFactors(
                        experimentAccession,
                        jdbcUtils.fetchRandomCellFromExperiment(experimentAccession)))
                .isEmpty();
    }

    private Iterable<String> experimentsWithMetadataProvider() {
        // E-GEOD-99058 does not have any metadata (factors or inferred cell types)
        return jdbcUtils.getPublicSingleCellExperimentAccessions()
                .stream()
                .filter(accession -> !accession.equalsIgnoreCase("E-GEOD-99058"))
                .collect(Collectors.toSet());
    }

    private Iterable<String> experimentsWithFactorsProvider() {
        // E-GEOD-99058 and E-ENAD-13 do not have any factors
        return jdbcUtils.getPublicSingleCellExperimentAccessions()
                .stream()
                .filter(accession ->
                        !accession.equalsIgnoreCase("E-GEOD-99058") && !accession.equalsIgnoreCase("E-ENAD-13"))
                .collect(Collectors.toSet());
    }
}
