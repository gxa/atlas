
package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.SortedSetMultimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.dao.ArrayDesignDAO;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.utils.UniProtClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named("bioEntityPropertyService")
@Scope("request")
public class BioEntityPropertyService {

    public static final String PROPERTY_TYPE_DESCRIPTION = "description";

    private BioEntityPropertyDao bioEntityPropertyDao;
    private UniProtClient uniProtClient;
    private ArrayDesignDAO arrayDesignDAO;
    private final BioEntityPropertyLinkBuilder linkBuilder;

    private SortedSetMultimap<String, String> propertyValuesByType;

    private Multimap<Integer, OntologyTerm> depthToGoTerms;
    private Multimap<Integer, OntologyTerm> depthToPoTerms;

    private String species;

    private SortedSet<String> entityNames;
    private String entityName;

    private String identifier;


    @Inject
    public BioEntityPropertyService(BioEntityPropertyDao bioEntityPropertyDao, UniProtClient uniProtClient, BioEntityPropertyLinkBuilder linkBuilder, ArrayDesignDAO arrayDesignDAO) {
        this.bioEntityPropertyDao = bioEntityPropertyDao;
        this.uniProtClient = uniProtClient;
        this.arrayDesignDAO = arrayDesignDAO;
        this.linkBuilder = linkBuilder;
    }

    public void init(String species, SortedSetMultimap<String, String> propertyValuesByType, Multimap<Integer, OntologyTerm> goTerms, Multimap<Integer, OntologyTerm> poTerms, SortedSet<String> entityNames, String identifier) {
        this.species = species;
        this.propertyValuesByType = propertyValuesByType;
        this.entityNames = entityNames;
        this.identifier = identifier;
        this.depthToGoTerms = goTerms;
        this.depthToPoTerms = poTerms;

        // this is to add mirbase sequence for ENSEMBL mirnas
        if (propertyValuesByType.containsKey("mirbase_id") && !propertyValuesByType.containsKey("mirbase_sequence")) {
            addMirBaseSequence();
        }
        this.init(species, propertyValuesByType, entityNames.first(),identifier);
    }

    public void init(String species, SortedSetMultimap<String, String> propertyValuesByType, String entityName, String identifier) {
        this.species = species;
        this.propertyValuesByType = propertyValuesByType;
        this.entityName = entityName;
        this.identifier = identifier;
    }

    public String getSpecies() {
        return species;
    }

    public List<PropertyLink> fetchPropertyLinks(String propertyType) {
        if ("reactome".equals(propertyType) && !propertyValuesByType.containsKey(propertyType)) {
            addReactomePropertyValues();
        } else if ("design_element".equals(propertyType) && !propertyValuesByType.containsKey(propertyType)) {
            addDesignElements();
        }

        List<PropertyLink> propertyLinks = Lists.newArrayList();
        for (String propertyValue : propertyValuesByType.get(propertyType)) {
            Optional<PropertyLink> link = linkBuilder.createLink(identifier, propertyType, propertyValue, species);
            if (link.isPresent()) {
                propertyLinks.add(link.get());
            }
        }
        return propertyLinks;
    }

    public List<PropertyLink> fetchRelevantGoPoLinks(String ontology, int includeAtLeast) {
        switch (ontology) {
            case "go":
                return fetchRelevantGoLinks(includeAtLeast);
            case "po":
                return fetchRelevantPoLinks(includeAtLeast);
            default:
                return new ImmutableList.Builder<PropertyLink>().build();
        }
    }

    public List<PropertyLink> fetchGoPoLinksOrderedByDepth(String ontology) {
        switch (ontology) {
            case "go":
                return fetchGoLinksOrderedByDepth();
            case "po":
                return fetchPoLinksOrderedByDepth();
            default:
                return new ImmutableList.Builder<PropertyLink>().build();
        }
    }

    private List<PropertyLink> fetchRelevantGoLinks(int includeAtLeast) {
        List<PropertyLink> propertyLinks = Lists.newArrayList();

        if (!depthToGoTerms.isEmpty()) {
            for (int i = Collections.max(depthToGoTerms.keySet()) ; i >= 1 && propertyLinks.size() < includeAtLeast; i--) {
                for (OntologyTerm goPoTerm : depthToGoTerms.get(i)) {
                    Optional<PropertyLink> link = linkBuilder.createLink(identifier, "go", goPoTerm.accession(), species);
                    if (link.isPresent()) {
                        propertyLinks.add(link.get());
                    }
                }
            }
        }

        return propertyLinks;
    }

    private List<PropertyLink> fetchGoLinksOrderedByDepth() {
        List<PropertyLink> propertyLinks = Lists.newArrayList();

        if (!depthToGoTerms.isEmpty()) {
            for (int i = Collections.max(depthToGoTerms.keySet()) ; i >= 1 ; i--) {
                for (OntologyTerm goPoTerm : depthToGoTerms.get(i)) {
                    Optional<PropertyLink> link = linkBuilder.createLink(identifier, "go", goPoTerm.accession(), species);
                    if (link.isPresent()) {
                        propertyLinks.add(link.get());
                    }
                }
            }
        }

        return propertyLinks;
    }

    // We don’t have depth information so far for PO. Once we do, remove this comment and apply the same logic as in GO
    private List<PropertyLink> fetchRelevantPoLinks(int maxLinkCount) {
        List<PropertyLink> propertyLinks = Lists.newArrayList();

        if (!depthToPoTerms.isEmpty()) {
            for (OntologyTerm goPoTerm : depthToPoTerms.values()) {
                Optional<PropertyLink> link = linkBuilder.createLink(identifier, "po", goPoTerm.accession(), species);
                if (link.isPresent()) {
                    propertyLinks.add(link.get());
                }
                if (propertyLinks.size() >= maxLinkCount) {
                    break;
                }
            }
        }

        return propertyLinks;
    }

    private List<PropertyLink> fetchPoLinksOrderedByDepth() {
        List<PropertyLink> propertyLinks = Lists.newArrayList();

            if (!depthToPoTerms.isEmpty()) {
            for (int i = Collections.max(depthToPoTerms.keySet()) ; i >= 1 ; i--) {
                for (OntologyTerm goPoTerm : depthToPoTerms.get(i)) {
                    Optional<PropertyLink> link = linkBuilder.createLink(identifier, "po", goPoTerm.accession(), species);
                    if (link.isPresent()) {
                        propertyLinks.add(link.get());
                    }
                }
            }
        }

        return propertyLinks;
    }

    private void addMirBaseSequence() {
        String mirbase_id = propertyValuesByType.get("mirbase_id").first();
        Set<String> mirbase_sequence = bioEntityPropertyDao.fetchPropertyValuesForGeneId(mirbase_id, "mirbase_sequence");
        propertyValuesByType.putAll("mirbase_sequence", mirbase_sequence);
    }

    private void addDesignElements() {
        List<String> designElements = arrayDesignDAO.getDesignElements(identifier);
        if (!designElements.isEmpty()) {
            propertyValuesByType.putAll("design_element", designElements);
        }
    }

    public String getBioEntityDescription() {
        String description = getFirstValueOfProperty(PROPERTY_TYPE_DESCRIPTION);
        return StringUtils.substringBefore(description, "[");
    }

    public SortedSet<String> getEntityNames() {
        return entityNames;
    }

    public String getEntityName() {
        return entityName;
    }

    String getFirstValueOfProperty(String propertyType) {
        Collection<String> properties = propertyValuesByType.get(propertyType);
        return CollectionUtils.isNotEmpty(properties) ? properties.iterator().next() : "";
    }

    void addReactomePropertyValues() {
        Collection<String> uniprotIds = propertyValuesByType.get("uniprot");
        if (CollectionUtils.isNotEmpty(uniprotIds)) {
            for (String uniprotId : uniprotIds) {
                Collection<String> reactomeIds = uniProtClient.fetchReactomeIds(uniprotId);
                propertyValuesByType.putAll("reactome", reactomeIds);
            }
        }
    }
}

