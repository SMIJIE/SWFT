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

        <div class="card bg-transparent">
            <div class="card-header">
                <a class="card-link text-danger" data-toggle="collapse" href="#luncheon">
                    <spring:message code="category.luncheon"/>
                </a>
            </div>

            <div id="luncheon" class="collapse" data-parent="#accordion">
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

                        <c:set var="countLuncheon" value="0" scope="page"/>
                        <c:forEach items="${generalDishes}" var="dishGeneral">
                            <c:if test="${dishGeneral.foodCategory eq 'LUNCHEON'}">
                                <c:set var="foodCategory" value="${dishGeneral.foodCategory}" scope="page"/>
                                <c:set var="countLuncheon" value="${countLuncheon + 1}" scope="page"/>
                                <tr class="${dishGeneral.foodCategory}" id="${dishGeneral.foodCategory}${countLuncheon}"
                                    style="display: none">
                                    <th scope="row">${countLuncheon}</th>
                                    <td><spring:message code="${dishGeneral.name}"/></td>
                                    <td>${dishGeneral.weight/1000}</td>
                                    <td>${dishGeneral.calories/1000}</td>
                                    <td>${dishGeneral.proteins/1000}</td>
                                    <td>${dishGeneral.fats/1000}</td>
                                    <td>${dishGeneral.carbohydrates/1000}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <nav aria-label="Page navigation example" class="">
                    <ul class="pagination">
                        &nbsp;&nbsp;
                        <c:forEach begin="1" end="${0.99+countLuncheon/4}" var="loop">
                            <li class="page-item"><a class="page-link bg-transparent text-danger"
                                                     id="${foodCategory}+${loop}"
                                                     href="#" onclick="clickPage(this)">${loop}</a></li>
                            &nbsp;
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>

        <div class="card bg-transparent">
            <div class="card-header">
                <a class="card-link text-danger" data-toggle="collapse" href="#soup">
                    <spring:message code="category.soup"/>
                </a>
            </div>

            <div id="soup" class="collapse" data-parent="#accordion">
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

                        <c:set var="countSoup" value="0" scope="page"/>
                        <c:forEach items="${generalDishes}" var="dishGeneral">
                            <c:if test="${dishGeneral.foodCategory eq 'SOUP'}">
                                <c:set var="foodCategory" value="${dishGeneral.foodCategory}" scope="page"/>
                                <c:set var="countSoup" value="${countSoup + 1}" scope="page"/>
                                <tr class="${dishGeneral.foodCategory}" id="${dishGeneral.foodCategory}${countSoup}"
                                    style="display: none">
                                    <th scope="row">${countSoup}</th>
                                    <td><spring:message code="${dishGeneral.name}"/></td>
                                    <td>${dishGeneral.weight/1000}</td>
                                    <td>${dishGeneral.calories/1000}</td>
                                    <td>${dishGeneral.proteins/1000}</td>
                                    <td>${dishGeneral.fats/1000}</td>
                                    <td>${dishGeneral.carbohydrates/1000}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <nav aria-label="Page navigation example" class="">
                    <ul class="pagination">
                        &nbsp;&nbsp;
                        <c:forEach begin="1" end="${0.99+countSoup/4}" var="loop">
                            <li class="page-item"><a class="page-link bg-transparent text-danger"
                                                     id="${foodCategory}+${loop}"
                                                     href="#" onclick="clickPage(this)">${loop}</a></li>
                            &nbsp;
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>

        <div class="card bg-transparent">
            <div class="card-header">
                <a class="card-link text-danger" data-toggle="collapse" href="#hot">
                    <spring:message code="category.hot"/>
                </a>
            </div>

            <div id="hot" class="collapse" data-parent="#accordion">
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

                        <c:set var="countHot" value="0" scope="page"/>
                        <c:forEach items="${generalDishes}" var="dishGeneral">
                            <c:if test="${dishGeneral.foodCategory eq 'HOT'}">
                                <c:set var="foodCategory" value="${dishGeneral.foodCategory}" scope="page"/>
                                <c:set var="countHot" value="${countHot + 1}" scope="page"/>
                                <tr class="${dishGeneral.foodCategory}" id="${dishGeneral.foodCategory}${countHot}"
                                    style="display: none">
                                    <th scope="row">${countHot}</th>
                                    <td><spring:message code="${dishGeneral.name}"/></td>
                                    <td>${dishGeneral.weight/1000}</td>
                                    <td>${dishGeneral.calories/1000}</td>
                                    <td>${dishGeneral.proteins/1000}</td>
                                    <td>${dishGeneral.fats/1000}</td>
                                    <td>${dishGeneral.carbohydrates/1000}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <nav aria-label="Page navigation example" class="">
                    <ul class="pagination">
                        &nbsp;&nbsp;
                        <c:forEach begin="1" end="${0.99+countHot/4}" var="loop">
                            <li class="page-item"><a class="page-link bg-transparent text-danger"
                                                     id="${foodCategory}+${loop}"
                                                     href="#" onclick="clickPage(this)">${loop}</a></li>
                            &nbsp;
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>

        <div class="card bg-transparent">
            <div class="card-header">
                <a class="card-link text-danger" data-toggle="collapse" href="#dessert">
                    <spring:message code="category.dessert"/>
                </a>
            </div>

            <div id="dessert" class="collapse" data-parent="#accordion">
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

                        <c:set var="countDessert" value="0" scope="page"/>
                        <c:forEach items="${generalDishes}" var="dishGeneral">
                            <c:if test="${dishGeneral.foodCategory eq 'DESSERT'}">
                                <c:set var="foodCategory" value="${dishGeneral.foodCategory}" scope="page"/>
                                <c:set var="countDessert" value="${countDessert + 1}" scope="page"/>
                                <tr class="${dishGeneral.foodCategory}" id="${dishGeneral.foodCategory}${countDessert}"
                                    style="display: none">
                                    <th scope="row">${countDessert}</th>
                                    <td><spring:message code="${dishGeneral.name}"/></td>
                                    <td>${dishGeneral.weight/1000}</td>
                                    <td>${dishGeneral.calories/1000}</td>
                                    <td>${dishGeneral.proteins/1000}</td>
                                    <td>${dishGeneral.fats/1000}</td>
                                    <td>${dishGeneral.carbohydrates/1000}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <nav aria-label="Page navigation example" class="">
                    <ul class="pagination">
                        &nbsp;&nbsp;
                        <c:forEach begin="1" end="${0.99+countDessert/4}" var="loop">
                            <li class="page-item"><a class="page-link bg-transparent text-danger"
                                                     id="${foodCategory}+${loop}"
                                                     href="#" onclick="clickPage(this)">${loop}</a></li>
                            &nbsp;
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>

<%--Shows rows of table for users--%>
<script type="text/javascript">
    menuPages()
</script>
<script>
    function clickPage(currentPage) {
        menuPages(currentPage.id)
    }
</script>
<%--Shows rows of table for users--%>
