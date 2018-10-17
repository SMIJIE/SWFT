<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<%--Shows rows of table for users--%>
<script type="text/javascript">
    function menuPages(messCurrentPage) {
        var showTrId;
        var hideTrId;
        var messArr;

        if (messCurrentPage == null) {
            for (var i = 1; i <= 4; i++) {
                showTrId = '#LUNCHEON' + i;
                $(showTrId).show();
                showTrId = '#SOUP' + i;
                $(showTrId).show();
                showTrId = '#HOT' + i;
                $(showTrId).show();
                showTrId = '#DESSERT' + i;
                $(showTrId).show();
            }
        } else {
            messArr = messCurrentPage.split('+');
            var end = parseInt(messArr[1]) * 4;
            var start = end - 3;
            hideTrId = '.' + messArr[0];
            $(hideTrId).hide();

            for (var i = start; i <= end; i++) {
                showTrId = '#' + messArr[0] + i;
                $(showTrId).show();
            }
        }
    }
</script>
<%--Shows rows of table for users--%>

<div id="content">

    <div id="accordion">

        <c:forEach items="${generalDishes}" var="generalDish">

            <div class="card bg-transparent">
                <div class="card-header">
                    <a class="card-link text-danger" data-toggle="collapse" href="#${generalDish.key}">
                        <spring:message code="category.${generalDish.key}"/>
                    </a>
                </div>

                <div id="${generalDish.key}" class="collapse" data-parent="#accordion">
                    <div class="card-body">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">
                                    <spring:message code="dish.name"/>
                                </th>
                                <th scope="col">
                                    <spring:message code="dish.weight"/>,
                                    <spring:message code="dish.gr"/>
                                </th>
                                <th scope="col">
                                    <spring:message code="dish.calories"/> /
                                    100 <spring:message code="dish.gr"/>
                                </th>
                                <th scope="col">
                                    <spring:message code="dish.proteins"/>
                                </th>
                                <th scope="col">
                                    <spring:message code="dish.fats"/>
                                </th>
                                <th scope="col">
                                    <spring:message code="dish.carbohydrates"/>
                                </th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:set var="counter" value="0" scope="page"/>
                            <c:forEach items="${generalDish.value}" var="dishGeneralValue">

                                <c:set var="foodCategory" value="${dishGeneralValue.foodCategory}" scope="page"/>
                                <c:set var="counter" value="${counter + 1}" scope="page"/>
                                <tr style="display: none" class="${foodCategory}" id="${foodCategory}${counter}">
                                    <th scope="row">${counter}</th>
                                    <td><spring:message code="${dishGeneralValue.name}"/></td>
                                    <td>${dishGeneralValue.weight/1000}</td>
                                    <td>${dishGeneralValue.calories/1000}</td>
                                    <td>${dishGeneralValue.proteins/1000}</td>
                                    <td>${dishGeneralValue.fats/1000}</td>
                                    <td>${dishGeneralValue.carbohydrates/1000}</td>
                                </tr>

                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <nav aria-label="Page navigation example" class="">
                        <ul class="pagination">
                            &nbsp;&nbsp;
                            <c:forEach begin="1" end="${0.99+counter/4}" var="loop">
                                <li class="page-item">
                                    <a class="page-link bg-transparent text-danger" id="${foodCategory}+${loop}"
                                       href="#" onclick="clickPage(this)">
                                            ${loop}
                                    </a>
                                </li>
                                &nbsp;
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%--Shows rows of table for users--%>
<script type="text/javascript">
    menuPages()
</script>
<script type="text/javascript">
    function clickPage(currentPage) {
        menuPages(currentPage.id)
    }
</script>
<%--Shows rows of table for users--%>
