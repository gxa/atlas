<%--@elvariable id="resourcesVersion" type="String"--%>
<%--@elvariable id="geneId" type="String"--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="row">
    <div class="small-12 columns">
        <h3>Search results</h3>
    </div>

    <div class="small-12 columns">
        <tiles:insertTemplate template="./gene-search-results/search.jsp"/>
    </div>

    <div class="small-12 columns">
        <div id="search-results-list"></div>
    </div>
</div>

<script defer src="${pageContext.request.contextPath}/resources/js-bundles/geneSearch.bundle.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function(event) {
        geneSearch.render({
            atlasUrl: '${pageContext.request.contextPath}/',
            resource: 'json/search/${geneId}'
        }, 'search-results-list')
    });

</script>
