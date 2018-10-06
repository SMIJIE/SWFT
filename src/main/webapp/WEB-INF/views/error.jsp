<%@ include file="components/allImports.jsp" %>

<jsp:include page="components/header.jsp"/>
<jsp:include page="components/sidePanel.jsp"/>
<jsp:include page="components/footer.jsp"/>


<div id="content">
    <h1><fmt:message key="${requestScope['javax.servlet.error.message']}"/></h1>
    <img src="<c:url value="../../resources/img/Error.png"/>"/>
</div>


