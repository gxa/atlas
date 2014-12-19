package uk.ac.ebi.atlas.search.EFO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class EFOIdToTermMapperIT {

    @Inject
    EFOIdToTermMapper subject;

    @Test
    public void getIdsFromTerm() {
        assertThat(subject.getIdsFromTerm("cancer").size(), is(greaterThanOrEqualTo(44)));
    }
}