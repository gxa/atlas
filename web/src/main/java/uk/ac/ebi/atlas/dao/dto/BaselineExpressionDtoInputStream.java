package uk.ac.ebi.atlas.dao.dto;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;


public class BaselineExpressionDtoInputStream implements ObjectInputStream<BaselineExpressionDto> {

    private static final Logger LOGGER = Logger.getLogger(BaselineExpressionDtoInputStream.class);

    private static final int GENE_ID_COLUMN_INDEX = 0;
    private static final int FIRST_EXPRESSION_LEVEL_INDEX = 2;

    private final CSVReader csvReader;
    private final Queue<BaselineExpressionDto> queue = new LinkedList<>();
    private final String[] assayGroupIds;

    public BaselineExpressionDtoInputStream(CSVReader csvReader) {
        this.csvReader = csvReader;
        String[] headers = readCsvLine();
        this.assayGroupIds = (String[]) ArrayUtils.subarray(headers, FIRST_EXPRESSION_LEVEL_INDEX, headers.length);
    }

    @Override
    public void close() throws IOException {
        csvReader.close();
    }

    private String[] readCsvLine() {
        try {
            return csvReader.readNext();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Exception thrown while reading next csv line.", e);
        }
    }

    @Override
    public BaselineExpressionDto readNext() {
        if (queue.isEmpty()) {
            String[] line = readCsvLine();
            if (line == null) {
                // EOF
                return null;
            }

            String geneId = line[GENE_ID_COLUMN_INDEX];
            String[] expressionLevels = (String[]) ArrayUtils.subarray(line, FIRST_EXPRESSION_LEVEL_INDEX, line.length);
            ImmutableList<? extends BaselineExpressionDto> baselineExpressionDtos = createList(geneId, assayGroupIds, expressionLevels);

            queue.addAll(baselineExpressionDtos);
        }

        return queue.remove();
    }

    /*
     * turns tsv header and line of:
     *
     * Gene ID, Gene Name, g1, g2, g3, g4, g5
     * mus1, musName, 1, 2, 3, 4, 5
     *
     * into BaselineExpressionDTOs of:
     *
     * mus1, g1, 1
     * mus1, g2, 2
     * mus1, g3, 3
     * mus1, g4, 4
     * mus1, g5, 5
     */
    private ImmutableList<? extends BaselineExpressionDto> createList(String geneId, String[] assayGroupIds, String[] expressionLevels) {
        checkArgument(assayGroupIds.length == expressionLevels.length);

        ImmutableList.Builder<BaselineExpressionDto> builder = ImmutableList.builder();

        for (int i = 0; i < expressionLevels.length; i++) {
            String assayGroupId = assayGroupIds[i];
            Double expressionLevel = Double.parseDouble(expressionLevels[i]);
            builder.add(new BaselineExpressionDto(geneId, assayGroupId, expressionLevel));
        }

        return builder.build();
    }
}
