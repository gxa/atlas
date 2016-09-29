<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>The European Bioinformatics Institute &lt; EMBL-EBI</title>
    <meta name="description" content="EMBL-EBI"><!-- Describe what this page is about -->
    <meta name="keywords" content="bioinformatics, europe, institute"><!-- A few keywords that relate to the content of THIS PAGE (not the whol project) -->
    <meta name="author" content="EMBL-EBI"><!-- Your [project-name] here -->
    <meta name="HandheldFriendly" content="true" />
    <meta name="MobileOptimized" content="width" />
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="theme-color" content="#70BDBD"> <!-- Android Chrome mobile browser tab color -->

    <!-- Add information on the life cycle of this page -->
    <meta name="ebi:owner" content="John Doe"> <!-- Who should be contacted about changes -->
    <meta name="ebi:review-cycle" content="30"> <!-- In days, how often should the content be reviewed -->
    <meta name="ebi:last-review" content="2015-12-20"> <!-- The last time the content was reviewed -->
    <meta name="ebi:expiry" content="2016-01-20"> <!-- When this content is no longer relevant -->

    <!-- If you link to any other sites frequently, consider optimising performance with a DNS prefetch -->
    <link rel="dns-prefetch" href="https://embl.de/">

    <!-- favicons generated at realfavicongenerator.net, and merged with https://github.com/ebiwd/EBI-Pattern-library/blob/gh-pages/sample-site/boilerplate/blank.html -->
    <meta name="theme-color" content="#ffffff">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicons/favicon.ico" />
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/favicons/favicon.ico" />
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/favicons/favicon-16x16.png" sizes="16x16" />
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/favicons/favicon-32x32.png" sizes="32x32" />

    <link rel="icon" type="image/png" sizes="192×192" href="${pageContext.request.contextPath}/resources/favicons/android-chrome-192x192.png" /> <!-- Android (192px) -->
    <link rel="manifest" href="${pageContext.request.contextPath}/resources/favicons/manifest.json" />

    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/resources/favicons/apple-icon-114x114.png" /> <!-- For iPhone 4 Retina display (114px) -->
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/resources/favicons/apple-icon-72x72.png" /> <!-- For iPad (72px) -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/resources/favicons/apple-icon-144x144.png" /> <!-- For iPad retina (144px) -->
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/resources/favicons/apple-icon-57x57.png" /> <!-- For iPhone (57px) -->
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/resources/favicons/apple-touch-icon.png" />
    <link rel="mask-icon" href="${pageContext.request.contextPath}/resources/favicons/safari-pinned-tab.svg" color="#5bbad5" /> <!-- Safari icon for pinned tab -->

    <meta name="msapplication-TileColor" content="#da532c" /> <!-- MS Icons -->
    <meta name="msapplication-TileImage" content="${pageContext.request.contextPath}/resources/favicons/mstile-144x144.png" />
    <meta name="msapplication-config" content="${pageContext.request.contextPath}/resources/favicons/browserconfig.xml" />

    <!-- CSS: implied media=all -->
    <!-- CSS concatenated and minified via ant build script-->
    <link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/libraries/foundation-6/css/foundation.css" type="text/css" media="all">
    <link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/css/ebi-global.css" type="text/css" media="all">
    <link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.1/fonts.css" type="text/css" media="all">

    <!-- Use this CSS file for any custom styling -->
    <!-- <link rel="stylesheet" href="css/custom.css" type="text/css" media="all"> -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/foundation/atlas.css" type="text/css" media="all">

    <!-- If you have a custom header image or colour -->
    <!-- <meta name="ebi:localmasthead-color" content="#000"> -->
    <!-- <meta name="ebi:localmasthead-image" content="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/images/backgrounds/embl-ebi-background.jpg"> -->

    <!-- you can replace this with theme-[projectname].css. See http://www.ebi.ac.uk/web/style/colour for details of how to do this -->
    <!-- also inform ES so we can host your colour palette file -->
    <!-- <link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/css/theme-embl-petrol.css" type="text/css" media="all"> -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/foundation/theme-atlas.css" type="text/css" media="all">

    <!-- for production the above can be replaced with -->
    <!--
    <link rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/css/compliance/mini/ebi-fluid-embl.css">
    -->

    <!-- end CSS-->


    <!-- All JavaScript at the bottom, except for Modernizr -->
    <script src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/libraries/modernizr/modernizr.custom.49274.js"></script>

</head>

<body class="level2"><!-- add any of your classes or IDs -->
<div id="skip-to">
    <ul>
        <li><a href="#content">Skip to main content</a></li>
        <li><a href="#local-nav">Skip to local navigation</a></li>
        <li><a href="#global-nav">Skip to EBI global navigation menu</a></li>
        <li><a href="#global-nav-expanded">Skip to expanded EBI global navigation menu (includes all sub-sections)</a></li>
    </ul>
</div>

<div data-sticky-container>
    <%--<div id="local-masthead" data-sticky data-sticky-on="large" data-top-anchor="content">--%>
    <div id="local-masthead" data-sticky data-sticky-on="large" data-top-anchor="235">
        <header>

            <tiles:insertAttribute name="top-menu" />

            <tiles:insertAttribute name="header" />

        </header>
    </div>
</div>


<div id="content" role="main">
    <tiles:insertAttribute name="body" />
</div>

<footer id="footer">
    <tiles:insertAttribute name="footer" />
</footer>
<!--! end of #wrapper -->


<!-- JavaScript -->

<!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline -->
<!--
<script>window.jQuery || document.write('<script src="../js/libs/jquery-1.10.2.min.js"><\/script>')</script>
-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<!-- Your custom JavaScript file scan go here... change names accordingly -->
<!--
<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/plugins.js"></script>
<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/script.js"></script>
-->
<script defer="defer" src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/cookiebanner.js"></script>
<script defer="defer" src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/foot.js"></script>
<script defer="defer" src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/script.js"></script>

<!-- The Foundation theme JavaScript -->
<script src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/libraries/foundation-6/js/foundation.js"></script>
<script src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/foundationExtendEBI.js"></script>
<script type="text/JavaScript">$(document).foundation();</script>
<script type="text/JavaScript">$(document).foundationExtendEBI();</script>
<!--
   -->

<!-- Google Analytics details... -->
<!-- Change UA-XXXXX-X to be your site's ID -->
<!--
<script>
  window._gaq = [['_setAccount','UAXXXXXXXX1'],['_trackPageview'],['_trackPageLoadTime']];
  Modernizr.load({
    load: ('https:' == location.protocol ? '//ssl' : '//www') + '.google-analytics.com/ga.js'
  });
</script>
-->



</body></html>