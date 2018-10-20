<%@ include file="allImports.jsp" %>

<html>
<head>
    <title>
        SWFT
    </title>

    <meta charset="UTF-8">
    <link href="<c:url value="../../../resources/img/Logo.png"/>" rel="shortcut icon">
    <link href="<c:url value="../../../resources/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="../../../resources/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <script src="<c:url value="../../../resources/js/jquery-3.3.1.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="../../../resources/js/bootstrap.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="../../../resources/js/popper.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="../../../resources/js/GoogleChartsLoader.js"/>" type="text/javascript"></script>

    <spring:message code="page.langLocale" var="langLocale"/>

    <%--Calculate users calories--%>
    <script type="text/javascript">
        function calcCalories() {
            <c:if test="${user eq null}">
            resultCalories.innerHTML = 3333;
            resultCaloriesDesired.innerHTML = 2222;
            $('#registeredUser').css({display: 'none'});
            </c:if>
            <c:if test="${user ne null}">
            var currentDate = new Date();
            var dob = new Date("${user.dob}");
            var age = currentDate.getFullYear() - dob.getFullYear();
            resultCalories.innerHTML = Math.round(((${user.lifeStyleCoefficient}) / 1000) * (10 * ((${user.weight}) / 1000) + 6.25 * ((${user.height}) / 100) - (5 * age)));
            resultCaloriesDesired.innerHTML = Math.round(((${user.lifeStyleCoefficient}) / 1000) * (10 * ((${user.weightDesired}) / 1000) + 6.25 * ((${user.height}) / 100) - (5 * age)));
            $('#signInOrRegister').css({display: 'none'});
            </c:if>

            <%--Selected users language--%>
            $('#selectId').val('${pageContext.request.contextPath}' + '/currPage?lang=' + '${langLocale}').change();
            <%--Selected users language--%>
        }
    </script>
    <%--Calculate users calories--%>
</head>
<body>

<div id="header">

    <div class="headerLeft">
        <img height="60px" width="65px" src="<c:url value="../../../resources/img/Logo.png"/>"/>
    </div>

    <div class="headerRight">
        <div class="btn-group" role="group">
            <div class="btn-group-vertical" role="group">
                <button type="button" class="btn btn-primary btn-sm">
                    <spring:message code="calories.currentWeight"/> =
                    <span class="badge badge-light" id='resultCalories'></span>
                </button>
                <button type="button" class="btn btn-success btn-sm">
                    <spring:message code="calories.desireWeight"/> =
                    <span class="badge badge-light" id='resultCaloriesDesired'></span>
                </button>
            </div>
            <button type="button" class="btn btn-warning">
                <spring:message code="calories.daily"/><br/>
                <mytags:formatDate langCount="${langLocale}"/>
            </button>
        </div>

        <h5 class="text-light">
            <spring:message code="${pageName}"/>
        </h5>

        <div class="form-group">
            <select class="form-control bg-white" id="selectId" onchange="knowCurrentlink()">
                <option value="${pageContext.request.contextPath}/currPage?lang=uk_UA">
                    <spring:message code="page.lang.uk_UA"/>
                </option>
                <option value="${pageContext.request.contextPath}/currPage?lang=en_US">
                    <spring:message code="page.lang.en_US"/>
                </option>
            </select>
        </div>

        <div class="form-group" id="registeredUser">
            <select class="form-control text-white bg-secondary"
                    onchange="window.location.href=this.options[this.selectedIndex].value">
                <option disabled selected>
                    ${user.name}
                </option>
                <option value="${pageContext.request.contextPath}/swft/user/logOut">
                    <spring:message code="page.logout"/>
                </option>
                <option value="${pageContext.request.contextPath}/swft/user/userSettings">
                    <spring:message code="page.settings"/>
                </option>
            </select>
        </div>

        <button type="button" class="btn btn-secondary btn-lg" id="signInOrRegister">
            <spring:message code="page.signIn"/>
        </button>
    </div>
</div>

<%--To calculate calories--%>
<script type="text/javascript">
    calcCalories()
</script>
<%--To calculate calories--%>
<%--To know current address for change language--%>
<script type="text/javascript">
    function knowCurrentlink() {
        var select, value, page;

        page = window.location.pathname;
        select = document.getElementById("selectId");
        value = select.options[select.selectedIndex].value;
        window.location.href = value.replace("/currPage", page);
    }
</script>
<%--To know current address for change language--%>
<%--For button Sign in--%>
<script type="text/javascript">
    $('#signInOrRegister').click(function () {
        window.location.href = '${pageContext.request.contextPath}/swft/signInOrRegister';
    });
</script>
<%--For button Sign in--%>