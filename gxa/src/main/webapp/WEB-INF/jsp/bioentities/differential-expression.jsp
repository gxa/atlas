<%--@elvariable id="hasDifferentialResults" type="boolean"--%>
<%--@elvariable id="identifier" type="java.lang.String"--%>
<%--@elvariable id="geneQuery" type="java.lang.String"--%>
<%--@elvariable id="conditionQuery" type="java.lang.String"--%>
<%--@elvariable id="species" type="java.lang.String"--%>
<%--@elvariable id="query" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div id="gxaDifferentialTab"></div>

<script>
    <c:if test="${hasDifferentialResults}">
    $(function() {
        expressionAtlasDifferentialExpression({
            target: "gxaDifferentialTab",
            atlasUrl: "${pageContext.request.contextPath}/",
            identifier: "${identifier}",
            query: "${query}",
            geneQuery: "${geneQuery}",
            conditionQuery: "${conditionQuery}",
            species: "${species}"
        });
    });
    </c:if>
</script>


