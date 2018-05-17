<%@ include file="allImports.jsp" %>
<%--Shows link for users--%>
<script type="text/javascript">
    function showUsersLinks() {
        <c:if test="${user eq null}">
        $('#menuCollapse').css({display: 'none'});
        $('#linkMenuCollapse').css({display: 'none'});
        $('#menuUsers').css({display: 'none'});
        $('#rationUsers').css({display: 'none'});
        </c:if>

        <c:if test="${user.role ne 'ADMIN'}">
        $('#menuCollapse').css({display: 'none'});
        $('#linkMenuCollapse').css({display: 'none'});
        $('#showUsers').css({display: 'none'});
        </c:if>

    }
</script>
<%--Shows link for users--%>

<div id="sidePanel">
    <ul class="text-danger">
        <li>
            <a class="text-light" href="${pageContext.request.contextPath}/swft/homePage">
                <fmt:message key="page.general"/>
            </a>
        </li>
        <li id="showUsers">
            <a class="text-light" href="${pageContext.request.contextPath}/swft/showUsers">
                <fmt:message key="page.users"/>
            </a>
        </li>
        <li>
            <a class="text-light" href="${pageContext.request.contextPath}/swft/menu">
                <fmt:message key="page.menu"/>
            </a>

            <a style="font-size: 20px" class="text-danger" href="#" data-toggle="collapse"
               data-target="#menuCollapse" id="linkMenuCollapse">
                <strong>
                    &#926;
                </strong>
            </a>

            <div class="collapse navbar-collapse" id="menuCollapse">
                <ul class="nav navbar-nav" style="list-style: none">
                    <li>&#8211;
                        <a class="text-light" href="${pageContext.request.contextPath}/swft/menuGeneralEdit">
                            <fmt:message key="page.menu.edit"/>
                        </a>
                    </li>
                </ul>
            </div>
        </li>
        <li id="menuUsers">
            <a class="text-light" href="${pageContext.request.contextPath}/swft/menuUsersEdit">
                <fmt:message key="page.menu.own"/>
            </a>
        </li>
        <li id="rationUsers">
            <a class="text-light" href="${pageContext.request.contextPath}/swft/dayRation">
                <fmt:message key="page.ration"/>
            </a>
        </li>
    </ul>
</div>
<%--Shows link for users--%>
<script type="text/javascript">
    showUsersLinks()
</script>
<%--Shows link for users--%>
