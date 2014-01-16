package uk.ac.ebi.atlas.model;

import org.junit.Test;
import uk.ac.ebi.atlas.model.baseline.Factor;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: barrera
 */
public class ExperimentDesignTest {

    private static final String ASSAY1 = "ASSAY1";
    private static final String FACTOR_HEADER = "FACTOR_HEADER";
    private static final String FACTOR_VALUE = "FACTOR_VALUE";
    private static final String FACTOR_ONTOLOGYTERM = "FACTOR_ONTOLOGYTERM";

    private static final String FACTOR_HEADER2 = "FACTOR_HEADER2";
    private static final String FACTOR_VALUE2 = "FACTOR_VALUE2";
    private static final String FACTOR_ONTOLOGYTERM2 = "FACTOR_ONTOLOGYTERM2";

    private static final String ASSAY2 = "ASSAY2";

    private static final String FACTOR_HEADER3 = "FACTOR_HEADER3";
    private static final String FACTOR_VALUE3 = "FACTOR_VALUE3";
    private static final String FACTOR_ONTOLOGYTERM3 = "FACTOR_ONTOLOGYTERM3";

    public ExperimentDesign subject;

    @Test
    public void loadMultipleFactorSet(){
        subject = new ExperimentDesign();
        subject.putFactor(ASSAY1, FACTOR_HEADER2, FACTOR_VALUE2, FACTOR_ONTOLOGYTERM2);
        subject.putFactor(ASSAY1, FACTOR_HEADER, FACTOR_VALUE, FACTOR_ONTOLOGYTERM);

        Factor factor1 = new Factor(FACTOR_HEADER, FACTOR_VALUE, FACTOR_ONTOLOGYTERM);
        Factor factor2 = new Factor(FACTOR_HEADER2, FACTOR_VALUE2, FACTOR_ONTOLOGYTERM2);
        Factor factor3 = new Factor(FACTOR_HEADER3, FACTOR_VALUE3, FACTOR_ONTOLOGYTERM3);

        subject.putFactor(ASSAY2, FACTOR_HEADER, FACTOR_VALUE, FACTOR_ONTOLOGYTERM);
        subject.putFactor(ASSAY2, FACTOR_HEADER2, FACTOR_VALUE2, FACTOR_ONTOLOGYTERM2);
        subject.putFactor(ASSAY2, FACTOR_HEADER3, FACTOR_VALUE3, FACTOR_ONTOLOGYTERM3);

//        System.out.println(subject.getFactors(ASSAY1));
//        System.out.println(subject.getFactors(ASSAY2));

        assertThat(subject.getFactors(ASSAY1), contains(factor2, factor1));
        assertThat(subject.getFactors(ASSAY2), contains(factor3, factor2, factor1));

    }

    @Test
    public void loadFactorValueFromAssayAndHeader(){
        subject = new ExperimentDesign();
        subject.putFactor(ASSAY1, FACTOR_HEADER2, FACTOR_VALUE2, FACTOR_ONTOLOGYTERM2);
        subject.putFactor(ASSAY1, FACTOR_HEADER, FACTOR_VALUE, FACTOR_ONTOLOGYTERM);

        subject.putFactor(ASSAY2, FACTOR_HEADER, FACTOR_VALUE, FACTOR_ONTOLOGYTERM);
        subject.putFactor(ASSAY2, FACTOR_HEADER2, FACTOR_VALUE2, FACTOR_ONTOLOGYTERM2);

        assertEquals(subject.getFactorValue(ASSAY1, FACTOR_HEADER), FACTOR_VALUE);

    }

}
