package uk.ac.ebi.atlas.solr.analytics.query;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.BIOENTITY_IDENTIFIER;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.SYMBOL;
import static uk.ac.ebi.atlas.solr.analytics.query.AnalyticsQueryClient.Field.IDENTIFIER_SEARCH;
import static uk.ac.ebi.atlas.solr.analytics.query.AnalyticsSolrQueryTree.Operator.OR;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.asAnalyticsSchemaKeyword;

public class AnalyticsSolrQueryTree {
    private static final String UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE = "__identifierSearch";

    private static final Pattern ENSEMBL_ID_REGEX_FROM_THE_INTERNET =
            Pattern.compile(
                    "ENS[A-Z]+[0-9]{11}|" +
                            "[A-Z]{3}[0-9]{3}[A-Za-z]" + "(-[A-Za-z])?|" +
                            "CG[0-9]+|" +
                            "[A-Z0-9]+\\.[0-9]+|" +
                            "YM[A-Z][0-9]{3}[a-z][0-9]",
                    Pattern.CASE_INSENSITIVE);

    public enum Operator {
        AND(" AND "),
        OR(" OR ");

        private final String opString;

        Operator(String opString) {
            this.opString = opString;
        }
    }

    private static abstract class TreeNode {
        //f can't return a Null or you'll mess up the tree
        abstract TreeNode map(final Function<Leaf, TreeNode> f);
        abstract TreeNode filter(final Predicate<Leaf> f);
    }

    private static class EmptyTree extends TreeNode {
        private final static TreeNode INSTANCE = new EmptyTree();

        private EmptyTree() {
        }

        @Override
        TreeNode map(final Function<Leaf, TreeNode> f) {
            return this;
        }

        @Override
        TreeNode filter(Predicate<Leaf> f) {
            return this;
        }
    }

    private static class Leaf extends TreeNode {
        final String searchField;
        final String searchValue;

        private Leaf(String searchField, String searchValue) {
            this.searchField = searchField;
            this.searchValue = searchValue;
        }

        @Override
        public String toString() {
            return  String.format("%s:\"%s\"", searchField, searchValue);
        }

        @Override
        TreeNode map(final Function<Leaf, TreeNode> f) {
            return f.apply(this);
        }

        @Override
        TreeNode filter(final Predicate<Leaf> f) {
            return f.test(this) ? this : EmptyTree.INSTANCE;
        }
    }

    private static class Parent extends TreeNode {
        private Operator operator;
        private Collection<TreeNode> children;

        private Parent(Operator operator, Collection<TreeNode> children) {
            this.operator = operator;
            this.children = children;
        }

        @Override
        public String toString() {
            Function<TreeNode, String> wrapParentsInParenthesis = new Function<TreeNode, String>() {
                @Nullable
                @Override
                public String apply(TreeNode treeNode) {
                    if (treeNode instanceof Parent) {
                        return "(" + treeNode.toString() + ")";
                    } else {
                        return treeNode.toString();
                    }
                }
            };

            return children.stream().map(wrapParentsInParenthesis).collect(joining(this.operator.opString));
        }

        @Override
        TreeNode map(final Function<Leaf, TreeNode> f) {
            return new Parent(operator, children.stream().map(child -> child.map(f)).collect(toList()));
        }

        @Override
        TreeNode filter(final Predicate<Leaf> f) {

            Collection<TreeNode> newChildren =
                    children.stream()
                            .map(child -> child.filter(f))
                            .filter(treeNode -> !treeNode.equals(EmptyTree.INSTANCE))
                            .collect(toList());

            return newChildren.size() == 0
                    ? EmptyTree.INSTANCE
                    : newChildren.size() == 1
                    ? newChildren.iterator().next()
                    : new Parent(operator, newChildren);
        }
    }

    private final TreeNode root;

    public String toString() {
        return root.toString();
    }

    // We want this search field to match at least one of these values
    AnalyticsSolrQueryTree(String searchField, String... searchValues) {
        if (searchValues.length == 1) {
            root = new Leaf(searchField, searchValues[0]);
        } else {
            ImmutableList.Builder<TreeNode> childrenBuilder = new ImmutableList.Builder<>();
            for (String searchValue : searchValues) {
                childrenBuilder.add(new Leaf(searchField, searchValue));
            }
            root = new Parent(OR, childrenBuilder.build());
        }
    }

    AnalyticsSolrQueryTree(Operator operator, AnalyticsSolrQueryTree... queries) {
        ImmutableList.Builder<TreeNode> childrenBuilder = new ImmutableList.Builder<>();
        for (AnalyticsSolrQueryTree query : queries) {
            childrenBuilder.add(query.root);
        }

        root = new Parent(operator, childrenBuilder.build());
    }

    private static String decideOnKeywordField(SemanticQueryTerm term) {
        if (term.hasNoCategory()) {
            if (ENSEMBL_ID_REGEX_FROM_THE_INTERNET.matcher(term.value()).matches()) {
                return BIOENTITY_IDENTIFIER.name;
            }
            // A multiword string cannot be a keyword
            if (term.value().trim().contains(" ")) {
                return IDENTIFIER_SEARCH.name;
            } else {
                return UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE;
            }
        } else {
            return BIOENTITY_IDENTIFIER.name.equals(term.category())
                    ? BIOENTITY_IDENTIFIER.name
                    : "keyword_" + term.category();
        }
    }

    //package
    public static AnalyticsSolrQueryTree createForIdentifierSearch(SemanticQuery geneQuery) {
        Multimap<String, String> m = HashMultimap.create();

        geneQuery.terms().stream()
                .filter(SemanticQueryTerm::hasValue)
                .forEach(term -> m.put(decideOnKeywordField(term), term.value()));

        List<AnalyticsSolrQueryTree> possibleIdentifiers =
                m.asMap().entrySet().stream()
                        .map(e -> new AnalyticsSolrQueryTree(e.getKey(), e.getValue().toArray(new String[0])))
                        .collect(toList());

        if (possibleIdentifiers.size() == 1) {
            return possibleIdentifiers.get(0);
        } else {
            return new AnalyticsSolrQueryTree(OR, possibleIdentifiers.toArray(new AnalyticsSolrQueryTree[0]));
        }
    }

    List<String> toQueryPlan() {
        TreeNode n = root.filter(leaf -> leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE));
        if (n.equals(EmptyTree.INSTANCE)) {
            return ImmutableList.of(toString());
        } else {
            /*
            If there were somehow two identifier search queries, this would be wrong, because we would search for both
            as keywords and then for both as text, missing the case when one matches as keyword and the other as text.
            */
            Function<Leaf, TreeNode> makeTreeForBioentityIdentifier = leaf -> {
                if (leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE)) {
                    return new Leaf(BIOENTITY_IDENTIFIER.name, leaf.searchValue);
                } else {
                    return leaf;
                }
            };

            Function<Leaf, TreeNode> makeTreeForSymbol = leaf -> {
                if (leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE)) {
                    return new Leaf(asAnalyticsSchemaKeyword(SYMBOL), leaf.searchValue);
                } else {
                    return leaf;
                }
            };

            // Queries for keyword_*, keyoword_symbol is left, the extra complexity isn’t worth it
            Function<Leaf, TreeNode> makeTreeForKeywords = leaf -> {
                if (leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE)) {
                    return new Parent(
                            OR,
                            identifierKeywords().stream()
                                    .map(possibleIdentifierSearch ->
                                            new Leaf(possibleIdentifierSearch, leaf.searchValue))
                                    .collect(toList()));
                } else {
                    return leaf;
                }
            };

            // Query for identifier_search
            Function<Leaf, TreeNode> makeTreeForFreeTextSearch = leaf -> {
                if (leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE)) {
                    return new Leaf(IDENTIFIER_SEARCH.name, leaf.searchValue);
                } else {
                    return leaf;
                }
            };

            return ImmutableList.of(
                    root.map(makeTreeForBioentityIdentifier).toString(),
                    root.map(makeTreeForSymbol).toString(),
                    root.map(makeTreeForKeywords).toString(),
                    root.map(makeTreeForFreeTextSearch).toString());
        }

    }

    private static ImmutableList<String> identifierKeywords() {
        return ImmutableList.copyOf(
                ExperimentDataPoint.bioentityPropertyNames.stream()
                        .filter(bioentityPropertyName -> bioentityPropertyName.isId)
                        .map(AnalyticsCollectionProxy::asAnalyticsSchemaKeyword)
                        .collect(toList()));
    }
}
