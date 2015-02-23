package uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Joiner;
import org.junit.Test;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class RnaSeqDifferentialAnalyticsInputStreamTest {

    private static CsvReaderFactory csvReaderFactory = new CsvReaderFactory();

    public static final String CONTRAST_ID_1 = "g2_g3";
    public static final String CONTRAST_ID_2 = "g2_g1";
    public static final String GENE_ID_1 = "GENE_ID_1";
    public static final String GENE_ID_2 = "GENE_ID_2";
    private static final String GENE_NAME_1 = "GENE_NAME_1";
    private static final String GENE_NAME_2 = "GENE_NAME_2";
    private static final String NA = "NA";
    private static final String INF = "inf";
    private static final String NEGATIVE_INF = "-inf";
    private static final double P_VALUE_1 = 0.974206814501029;
    private static final double FOLD_CHANGE_1 = 0.0761328916666661;
    private static final double P_VALUE_2 = 0.979446913114856;
    private static final double FOLD_CHANGE_2 = -0.0282126666666667;

    private static final String TSV_HEADER_1 = Joiner.on("\t").join(new String[]{"Gene ID", "Gene Name",
            CONTRAST_ID_1 + ".p-value", CONTRAST_ID_1 + ".log2foldchange"});
    private static final String TSV_LINE_1 = Joiner.on("\t").join(new String[]{GENE_ID_1, GENE_NAME_1,
            ""+ P_VALUE_1,  ""+ FOLD_CHANGE_1});
    private static final String TSV_LINE_2 = Joiner.on("\t").join(new String[]{GENE_ID_2, GENE_NAME_2,
            ""+ P_VALUE_2,  ""+ FOLD_CHANGE_2});

    private static final String TSV_HEADER_2_CONTRASTS = Joiner.on("\t").join(new String[]{"Gene ID", "Gene Name", "Design Element",
            CONTRAST_ID_1 + ".p-value", CONTRAST_ID_1 + ".t-statistic", CONTRAST_ID_1 + ".log2foldchange",
            CONTRAST_ID_2 + ".p-value", CONTRAST_ID_2 + ".t-statistic", CONTRAST_ID_2 + ".log2foldchange"});
    private static final String TSV_LINE_2_CONTRASTS = Joiner.on("\t").join(new String[]{GENE_ID_1, GENE_NAME_1,
            ""+ P_VALUE_1, ""+ FOLD_CHANGE_1,
            ""+ P_VALUE_2, ""+ FOLD_CHANGE_2,});

    private static final String TSV_LINE_1_NA = Joiner.on("\t").join(new String[]{GENE_ID_1, GENE_NAME_1,
            NA, ""+ FOLD_CHANGE_1});

    private static final String TSV_LINE_1_INF = Joiner.on("\t").join(new String[]{GENE_ID_1, GENE_NAME_1,
            INF, ""+ FOLD_CHANGE_1});

    private static final String TSV_LINE_1_ZERO = Joiner.on("\t").join(new String[]{GENE_ID_1, GENE_NAME_1,
            ""+ P_VALUE_1,  ""+ 0});

    private static final String TSV_LINE_2_NEGATIVE_INF = Joiner.on("\t").join(new String[]{GENE_ID_2, GENE_NAME_2,
            NEGATIVE_INF, ""+ FOLD_CHANGE_2});

    private static final String TSV_MANY_NA_LINES = Joiner.on("\n").join(Collections.nCopies(5000, TSV_LINE_1_NA));

    private static String tsvContents1Contrast = Joiner.on("\n").join(new String[]{TSV_HEADER_1, TSV_LINE_1, TSV_LINE_2});
    private static String tsvContents2Contrasts = Joiner.on("\n").join(new String[]{TSV_HEADER_2_CONTRASTS, TSV_LINE_2_CONTRASTS});
    private static String tsvContents1ContrastNA = Joiner.on("\n").join(new String[]{TSV_HEADER_1, TSV_LINE_1_NA, TSV_LINE_2});
    private static String tsvContents1ContrastZero = Joiner.on("\n").join(new String[]{TSV_HEADER_1, TSV_LINE_1_ZERO, TSV_LINE_2});;
    private static String tsvContents1ContrastINF = Joiner.on("\n").join(new String[]{TSV_HEADER_1, TSV_LINE_1_INF, TSV_LINE_2_NEGATIVE_INF});

    private static final String TSV_CONTENTS_MANY_NAS = Joiner.on("\n").join(new String[]{TSV_HEADER_1, TSV_MANY_NA_LINES, TSV_LINE_2});



    @Test
    public void readOneContrastTsv() throws IOException {
        Reader tsvSource = new StringReader(tsvContents1Contrast);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);
        RnaSeqDifferentialAnalyticsInputStream subject = new RnaSeqDifferentialAnalyticsInputStream(csvReader, "Test");

        RnaSeqDifferentialAnalytics dto1 = new RnaSeqDifferentialAnalytics(GENE_ID_1, CONTRAST_ID_1, P_VALUE_1, FOLD_CHANGE_1);
        RnaSeqDifferentialAnalytics dto2 = new RnaSeqDifferentialAnalytics(GENE_ID_2, CONTRAST_ID_1, P_VALUE_2, FOLD_CHANGE_2);

        assertThat(subject.readNext(), is(dto1));
        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void readTwoContrastTsv() throws IOException {
        Reader tsvSource = new StringReader(tsvContents2Contrasts);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);
        RnaSeqDifferentialAnalyticsInputStream subject = new RnaSeqDifferentialAnalyticsInputStream(csvReader, "Test");

        RnaSeqDifferentialAnalytics dto1 = new RnaSeqDifferentialAnalytics(GENE_ID_1, CONTRAST_ID_1, P_VALUE_1, FOLD_CHANGE_1);
        RnaSeqDifferentialAnalytics dto2 = new RnaSeqDifferentialAnalytics(GENE_ID_1, CONTRAST_ID_2, P_VALUE_2, FOLD_CHANGE_2);

        assertThat(subject.readNext(), is(dto1));
        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void readContrastContainingNA() throws IOException {
        Reader tsvSource = new StringReader(tsvContents1ContrastNA);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);
        RnaSeqDifferentialAnalyticsInputStream subject = new RnaSeqDifferentialAnalyticsInputStream(csvReader, "Test");

        RnaSeqDifferentialAnalytics dto2 = new RnaSeqDifferentialAnalytics(GENE_ID_2, CONTRAST_ID_1, P_VALUE_2, FOLD_CHANGE_2);

        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void readContrastWithZeroFoldChange() throws IOException {
        Reader tsvSource = new StringReader(tsvContents1ContrastZero);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);
        RnaSeqDifferentialAnalyticsInputStream subject = new RnaSeqDifferentialAnalyticsInputStream(csvReader, "Test");

        RnaSeqDifferentialAnalytics dto2 = new RnaSeqDifferentialAnalytics(GENE_ID_2, CONTRAST_ID_1, P_VALUE_2, FOLD_CHANGE_2);

        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void readContrastContainingManyNAsWithoutStackOverflow() throws IOException {
        Reader tsvSource = new StringReader(TSV_CONTENTS_MANY_NAS);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);
        RnaSeqDifferentialAnalyticsInputStream subject = new RnaSeqDifferentialAnalyticsInputStream(csvReader, "Test");

        RnaSeqDifferentialAnalytics dto2 = new RnaSeqDifferentialAnalytics(GENE_ID_2, CONTRAST_ID_1, P_VALUE_2, FOLD_CHANGE_2);

        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }

    @Test
    public void readContrastWithInf() throws IOException {
        Reader tsvSource = new StringReader(tsvContents1ContrastINF);
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);
        RnaSeqDifferentialAnalyticsInputStream subject = new RnaSeqDifferentialAnalyticsInputStream(csvReader, "Test");

        RnaSeqDifferentialAnalytics dto1 = new RnaSeqDifferentialAnalytics(GENE_ID_1, CONTRAST_ID_1, Double.POSITIVE_INFINITY, FOLD_CHANGE_1);
        RnaSeqDifferentialAnalytics dto2 = new RnaSeqDifferentialAnalytics(GENE_ID_2, CONTRAST_ID_1, Double.NEGATIVE_INFINITY, FOLD_CHANGE_2);

        assertThat(subject.readNext(), is(dto1));
        assertThat(subject.readNext(), is(dto2));
        assertThat(subject.readNext(), is(nullValue()));
    }


    @Test
    public void tryResourcesClosesUnderlyingReaderWhenFinished() throws IOException {
        Reader tsvSource = spy(new StringReader(tsvContents1Contrast));
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);

        try (RnaSeqDifferentialAnalyticsInputStream subject = new RnaSeqDifferentialAnalyticsInputStream(csvReader, "Test")) {
            subject.readNext();
        }

        verify(tsvSource).close();
    }

    @Test
    public void tryResourcesAutoClosesUnderlyingReaderOnException() throws IOException {
        Reader tsvSource = spy(new StringReader(tsvContents1Contrast));
        CSVReader csvReader = csvReaderFactory.createTsvReader(tsvSource);

        try (RnaSeqDifferentialAnalyticsInputStream subject = new RnaSeqDifferentialAnalyticsInputStream(csvReader, "Test")) {
            subject.readNext();
            throw new RuntimeException("foobar");
        } catch (RuntimeException e) {
            // ignore
        }

        verify(tsvSource).close();
    }

}
