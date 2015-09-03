<%--
  ~ Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  ~
  ~
  ~ For further details of the Gene Expression Atlas project, including source code,
  ~ downloads and documentation, please see:
  ~
  ~ http://gxa.github.com/gxa
  --%>
<%--@elvariable id="bioEntityPropertyService" type="uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/faceted-search.css"/>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div id="search_result_pg">

<div id="help-placeholder" style="display: none"></div>

<!-- Search box -->
<%--<section class="grid_24" style="margin-bottom:18px;">--%>
    <%--<%@ include file="includes/search-form.jsp" %>--%>
<%--</section>--%>
<!-- /Search box -->

<c:if test="${not empty searchDescription}" >
    <c:if test="${hasGeneInformation}" >
        <!-- Simple page header -->
        <div id="headerBody" class="gxaBioEntityCardHeader" style="margin-bottom: 20px;margin-top: 10px;">
                  <span class="gxaBioEntityCardBioentityName">
                      <c:forEach var="entityName" varStatus="loopStatus"
                                 items="${bioEntityPropertyService.entityNames}">
                          ${entityName}<c:if test="${not loopStatus.last}">, </c:if>
                          <c:set var="entityNamesList" value="${entityNamesList} ${entityName}"/>
                      </c:forEach>
                  </span>
            <c:set var="species" value="${bioEntityPropertyService.getSpecies()}"/>
            <span class="gxaBioEntityCardSpecies">${fn:toUpperCase(fn:substring(species, 0, 1))}${fn:substring(species, 1,fn:length(species))}</span>
            <span class="gxaBioEntityCardDescription">${bioEntityPropertyService.getBioEntityDescription()}</span>
        </div>
    </c:if>
    <c:if test="${!hasGeneInformation}" >
            <div class="container">
                <div class="page-header">
                    <h2 class="strapline">Search result for <span class="searchterm">${searchDescription}</span></h2>
                </div>
                    <%--<h:ebiGlobalSearch ebiSearchTerm="${applicationProperties.urlParamEncode(globalSearchTerm)}"/>--%>
            </div>
    </c:if>

    <!-- /Simple page header -->
</c:if>


<section class="grid_24">

    <ul class="nav nav-tabs">
        <c:if test="${hasBaselineResults}"><li title="Baseline experiments" class="active"><a data-toggle="tab" href="${pageContext.request.contextPath}/search?geneQuery=${geneQuery.asUrlQueryParameter()}" >Baseline experiments</a></li></c:if>
        <c:if test="${!hasBaselineResults}"><li title="Baseline experiments" class="disabled" >Baseline experiments</li></c:if>
        <c:if test="${hasDifferentialResults}"><li title="Differential experiments"><a href="${pageContext.request.contextPath}/search/differential?geneQuery=${geneQuery.asUrlQueryParameter()}">Differential experiments</a></li></c:if>
        <c:if test="${!hasDifferentialResults}"> <li title="Differential experiments" class="disabled">Differential experiments</li></c:if>
        <c:if test="${hasGeneInformation}"><li title="Bioentity information"><a href="${pageContext.request.contextPath}/search/bioentity?geneQuery=${geneQuery.asUrlQueryParameter()}">Bioentity information</a></li> </c:if>
        <c:if test="${!hasGeneInformation}"><li title="Bioentity information" class="disabled">Bioentity information</li> </c:if>
    </ul>

    <div id="baseline" class="tab-pane fade in active">
        <div class="grid_6 alpha" id="atlasBaselineFacetedSearchFacetsContainer"></div>

        <div class="grid_18 omega">
            <c:if test="${!hasBaselineResults}"><p>No baseline results<p></c:if>
            <c:if test="${hasBaselineResults}">
            <h3>List of baseline experiments</h3>
            <div id="atlasBaselineFacetedSearchResultsContainer"></div>
            </c:if>
        </div>
    </div>

</section><!-- /search_facet -->
</div>

<script src="${pageContext.request.contextPath}/resources/js-bundles/vendor.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/faceted-search.bundle.js"></script>

<script>
    var facetedSearcher = window.exposed;

    <c:if test="${hasBaselineResults}">
    var facetsData = ${empty jsonFacets ? 'null' : jsonFacets};
        facetedSearcher.baseline("atlasBaselineFacetedSearchFacetsContainer", "atlasBaselineFacetedSearchResultsContainer", facetsData);
    </c:if>

</script>


<!-- absolute Link to bootstrap script - temp - make it relative-->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<!-- Script to solve conflict between Bootstrap and Jquery https://github.com/twbs/bootstrap/issues/6094 -->
<script>
    var btn = $.fn.button.noConflict() // reverts $.fn.button to jqueryui btn
    $.fn.btn = btn // assigns bootstrap button functionality to $.fn.btn
</script>

