/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.transcript;

import com.google.common.base.Joiner;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.baseline.TranscriptProfile;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Named
@Scope("prototype")
public class TranscriptProfileDao {
    private static final Logger LOGGER = Logger.getLogger(TranscriptProfileDao.class);

    private static final String TRANSCRIPT_PROFILE_QUERY = "SELECT GENE_IDENTIFIER, TRANSCRIPT_IDENTIFIER, TRANSCRIPT_EXPRESSIONS " +
            "FROM RNASEQ_BSLN_TRANSCRIPTS WHERE EXPERIMENT = ? AND GENE_IDENTIFIER = ? AND ISACTIVE='T'";

    private static final String TRANSCRIPT_PROFILE_INSERT = "INSERT INTO RNASEQ_BSLN_TRANSCRIPTS " +
            "(EXPERIMENT, GENE_IDENTIFIER, TRANSCRIPT_IDENTIFIER, TRANSCRIPT_EXPRESSIONS, TRANSCRIPT_ISACTIVE) VALUES (?, ?, ?, ?, ?)";
    private static final int FIRST_INDEX = 1;
    private static final int SECOND_INDEX = 2;
    private static final int THIRD_INDEX = 3;
    private static final int FOURTH_INDEX = 4;
    private static final int IS_ACTIVE = 5;

    private JdbcTemplate jdbcTemplate;

    @Inject
    public TranscriptProfileDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<TranscriptProfile> findTranscriptProfiles(String experimentAccession, String geneId) {
        LOGGER.debug("<findTranscriptProfiles> experimentAccession = " + experimentAccession + ", geneId = " + geneId);

        return jdbcTemplate.query(TRANSCRIPT_PROFILE_QUERY,
                new String[]{experimentAccession, geneId},
                new TranscriptProfileRowMapper());
    }

    public void loadTranscriptProfiles(final String experimentAccession, final List<TranscriptProfile> profiles) {
        LOGGER.debug("<loadTranscriptProfiles> experimentAccession = " + experimentAccession + ", profiles.size() = " + profiles.size());

        jdbcTemplate.batchUpdate(TRANSCRIPT_PROFILE_INSERT, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TranscriptProfile profile = profiles.get(i);
                ps.setString(FIRST_INDEX, experimentAccession);
                ps.setString(SECOND_INDEX, profile.getGeneId());
                ps.setString(THIRD_INDEX, profile.getTranscriptId());
                List<Double> expressions = profile.getExpressions();
                String expressionsSerialized = Joiner.on(",").join(expressions);
                ps.setString(FOURTH_INDEX, expressionsSerialized);
                ps.setString(IS_ACTIVE, "T");
            }

            @Override
            public int getBatchSize() {
                return profiles.size();
            }
        });
    }

    public void deleteTranscriptProfilesForExperiment(String experimentAccession) {
        LOGGER.debug("<deleteTranscriptProfilesForExperiment> experimentAccession = " + experimentAccession);
        jdbcTemplate.update("UPDATE RNASEQ_BSLN_TRANSCRIPTS SET ISACTIVE = 'F' WHERE EXPERIMENT = ?",
                experimentAccession);
    }


    public void deleteInactiveTranscriptProfiles() {
        LOGGER.debug("<deleteInactiveTranscriptProfiles>");
        jdbcTemplate.update("DELETE FROM RNASEQ_BSLN_TRANSCRIPTS WHERE ISACTIVE = 'F'");
    }

}
