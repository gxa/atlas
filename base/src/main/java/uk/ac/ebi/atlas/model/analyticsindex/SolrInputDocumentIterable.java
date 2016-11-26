package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.apache.solr.common.SolrInputDocument;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SolrInputDocumentIterable implements Iterable<SolrInputDocument> {

    private final Iterator<? extends ExperimentDataPoint> experimentDataPointIterator;
    private final Map<String,Map<BioentityPropertyName, Set<String>>> propertiesPerBioentityIdentifier;

    public SolrInputDocumentIterable(Iterator<? extends ExperimentDataPoint> experimentDataPointIterator,
                                     Map<String, Map<BioentityPropertyName, Set<String>>> propertiesPerBioentityIdentifier){
        this.experimentDataPointIterator = experimentDataPointIterator;
        this.propertiesPerBioentityIdentifier = propertiesPerBioentityIdentifier;
    }

    private SolrInputDocument createNext(ExperimentDataPoint experimentDataPoint){

        Map<BioentityPropertyName, Set<String>> m = propertiesPerBioentityIdentifier.get(experimentDataPoint
                .bioentityIdentifier);

        return create(experimentDataPoint,m!=null? m : ImmutableMap.<BioentityPropertyName, Set<String>>of());
    }


    private static SolrInputDocument create(ExperimentDataPoint experimentDataPoint,Map<BioentityPropertyName, Set<String>>
            bioentityProperties){

        SolrInputDocument solrInputDocument = new SolrInputDocument();

        for(Map.Entry<String, Object> e: experimentDataPoint.getProperties().entrySet()){
            solrInputDocument.addField(e.getKey(), e.getValue());
        }

        List<String> nonKeywordProperties = new LinkedList<>();

        for(BioentityPropertyName bioentityPropertyName : experimentDataPoint.getRelevantBioentityPropertyNames()){
            Collection<String> values = bioentityProperties.get(bioentityPropertyName);
            if(values!= null){
                for(String value: values){
                    if(bioentityPropertyName.isId){
                        solrInputDocument.addField(bioentityPropertyName.asAnalyticsIndexKeyword(), value);
                    } else {
                        nonKeywordProperties.add(value);
                    }
                }
            }
        }

        /*
        Improvement:
        add experiment.getDisplayName() to identifierSearch too?
         */

        solrInputDocument.addField(BioentityPropertyName.IDENTIFIER_SEARCH.name, Joiner.on(" ").join(nonKeywordProperties));

        return solrInputDocument;
    }

    @Override
    public Iterator<SolrInputDocument> iterator() {
        return new Iterator<SolrInputDocument>() {
            @Override
            public boolean hasNext() {
                return experimentDataPointIterator.hasNext();
            }

            @Override
            public SolrInputDocument next() {
                return createNext(experimentDataPointIterator.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
