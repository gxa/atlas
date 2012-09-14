package uk.ac.ebi.atlas.streams;

import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import utils.ExperimentRunsBuilder;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ExpressionLevelBufferTest {

    private static final String RUN_ACCESSION_1 = "ERR030872";
    private static final String RUN_ACCESSION_2 = "ERR030873";
    private static final String RUN_ACCESSION_3 = "ERR030874";
    private static final String TRANSCRIPT_ID = "ENST00000000233";
    private static final String[] THREE_RPKM_VALUES = new String[]{TRANSCRIPT_ID, "0", "42.9134", "0.0001"};

    private ExpressionLevelsBuffer subject;
    private static List<ExperimentRun> experimentRuns;

    @Before
    public void initializeSubject() {
        ExperimentRun experimentRun1 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_1).create();
        ExperimentRun experimentRun2 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_2).create();
        ExperimentRun experimentRun3 = ExperimentRunsBuilder.forRunAccession(RUN_ACCESSION_3).create();

        experimentRuns = Lists.newArrayList(experimentRun1, experimentRun2, experimentRun3);

        subject = ExpressionLevelsBuffer.forExperimentRuns(experimentRuns)
            .withHeaders("", RUN_ACCESSION_1, RUN_ACCESSION_2, RUN_ACCESSION_3)
            .create();
    }

    @Test(expected = IllegalStateException.class)
    public void builderShouldThrowIllegalStateExceptionWhenOrderSpecificationIsNotSet(){
        ExpressionLevelsBuffer.forExperimentRuns(experimentRuns).create();
    }

    @Test
    public void pollShouldReturnExpressionLevelsInTheRightOrder() throws Exception {
        subject.reload(THREE_RPKM_VALUES);
        //given the object was just initialized
        ExpressionLevel expressionLevel = subject.poll();
        //then we expect first expressionLevel
        assertThat(expressionLevel.getRpkm(), is(0d));
        assertThat(expressionLevel.getFactorValues(), is(experimentRuns.get(0).getFactorValues()));

        //given we poll again
        expressionLevel = subject.poll();
        //then we expect secondExpressionLevel
        assertThat(expressionLevel.getRpkm(), is(42.9134d));
        assertThat(expressionLevel.getFactorValues(), is(experimentRuns.get(1).getFactorValues()));

    }

    @Test
    public void bufferShouldBeExhaustedAfterThreePolls() throws Exception {
        //given we do first reload
        subject.reload(THREE_RPKM_VALUES);
        //and we poll three times
        subject.poll();
        subject.poll();
        subject.poll();
        //then we expect next poll to return null
        assertThat(subject.poll(), Matchers.is(nullValue()));
    }

    @Test
    public void reloadWhenBufferIsExhaustedShouldFillTheBufferAgain() throws Exception {
        //given we do first reload
        subject.reload(THREE_RPKM_VALUES);
        //and we poll until exhaustion
        ExpressionLevel run;
        do {
            run = subject.poll();
        } while (run != null);
        //when we reload again with new values
        subject.reload("T1", "1", "2", "3");
        //and we poll
        ExpressionLevel transcriptExpressionLevel = subject.poll();
        //then we expect to find the new values
        assertThat(transcriptExpressionLevel.getRpkm(), is(1d));
        assertThat(transcriptExpressionLevel.getRunAccession(), is(experimentRuns.get(0).getRunAccession()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void reloadShouldThrowExceptionIfMoreValuesThanRuns() throws Exception {
        //given that we initialized subject with three runs
        //when
        subject.reload(TRANSCRIPT_ID, "0", "42.9134", "0.0001", "666");

    }

    @Test(expected = IllegalArgumentException.class)
    public void reloadShouldThrowExceptionIfLessValuesThanRuns() throws Exception {
        //given that we initialized subject with three runs
        //when
        subject.reload(TRANSCRIPT_ID, "0", "42.9134");

    }

    @Test(expected = IllegalStateException.class)
    public void reloadShouldThrowExceptionIfBufferIsNotEmpty() throws Exception {
        //given
        subject.reload(THREE_RPKM_VALUES);
        //and
        subject.poll();
        //when we reload again
        subject.reload(THREE_RPKM_VALUES);

    }


}
