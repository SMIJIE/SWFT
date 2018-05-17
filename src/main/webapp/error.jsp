<%@ include file="WEB-INF/components/allImports.jsp" %>

<jsp:include page="WEB-INF/components/header.jsp"/>
<jsp:include page="WEB-INF/components/sidePanel.jsp"/>
<jsp:include page="WEB-INF/components/footer.jsp"/>


<div id="content">
    <h1><fmt:message key="${requestScope['javax.servlet.error.message']}"/></h1>
    <img src="<c:url value="../../resources/img/Error.png"/>"/>
</div>


