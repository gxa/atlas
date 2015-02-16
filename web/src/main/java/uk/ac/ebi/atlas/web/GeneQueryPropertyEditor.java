package uk.ac.ebi.atlas.web;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;
import uk.ac.ebi.atlas.utils.StringUtil;

import java.beans.PropertyEditorSupport;
import java.util.List;

public class GeneQueryPropertyEditor extends PropertyEditorSupport {


    @Override
    public void setAsText(String text) {
        String geneQuery = TagEditorConverter.tagsToQueryString(text);

        List<String> terms = BioentityPropertyValueTokenizer.splitBySpacePreservingQuotes(geneQuery);

        setValue(GeneQuery.create(removeSurroundingQuotes(terms)));
    }

    static ImmutableList<String> removeSurroundingQuotes(List<String> strings) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for (String s : strings) {
            builder.add(StringUtil.removeSurroundingQuotes(s));

        }

        return builder.build();
    }

    @Override
    public String getAsText() {
        return ((GeneQuery) this.getValue()).asUrlQueryParameter();
    }

}
