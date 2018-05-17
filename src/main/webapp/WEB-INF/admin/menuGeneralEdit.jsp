<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<script type="text/javascript">
    <%--Shows row of menu--%>

    function menuItemForPageAndErrorData(messCurrentPage) {
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
        <%--Shows row of menu--%>

        <%--Shows error with data--%>
        var mess = '${userErrorData}';
        if (mess === 'nonErrorData') {
            $('#userErrorData').css({display: 'none'});
        }
        <%--Shows error with data--%>
    }
</script>

<div id="content">

    <div id="accordion">
        <div class="card text-light bg-danger" id="userErrorData">
            <div class="card-header">
                <fmt:message key="${userErrorData}"/>
            </div>
        </div>

        <div class="card bg-transparent">
            <div class="card-header">
                <a class="card-link text-danger" data-toggle="collapse" href="#luncheon">
                    <fmt:message key="page.category.luncheon"/>
                </a>
            </div>

            <div id="luncheon" class="collapse" data-parent="#accordion">
                <div class="card-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"></th>
                            <th scope="col">
                                <fmt:message key="page.dish.name"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.weight"/>,
                                <fmt:message key="page.dish.gr"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.calories"/> /
                                100 <fmt:message key="page.dish.gr"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.proteins"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.fats"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.carbohydrates"/>
                            </th>
                            <th scope="col">
                                <button type="button" id="delete_luncheon_dish" class="btn btn-danger navbar-btn">
                                    <fmt:message key="page.delete"/>
                                </button>
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
                                    <th scope="row">
                                            ${countLuncheon}
                                    </th>
                                    <td>
                                        <input type="checkbox" name="toDelete[]"
                                               value="${dishGeneral.id}" id="checkbox_${dishGeneral.id}"/>
                                    </td>
                                    <form action="${pageContext.request.contextPath}/swft/updateGeneralDish?numDish=${dishGeneral.id}"
                                          method="POST">
                                        <td>
                                            <fmt:message key="${dishGeneral.name}"/>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="weight"
                                                   step="0.1" value="${dishGeneral.weight/1000}"/>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="calories"
                                                   step="0.1" value="${dishGeneral.calories/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="proteins"
                                                   step="0.1" value="${dishGeneral.proteins/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="fats"
                                                   step="0.1" value="${dishGeneral.fats/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="carbohydrates"
                                                   step="0.1" value="${dishGeneral.carbohydrates/1000}">
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">
                                                <fmt:message key="page.update"/>
                                            </button>
                                        </td>
                                    </form>
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

        <div class="card bg-transparent">
            <div class="card-header">
                <a class="card-link text-danger" data-toggle="collapse" href="#soup">
                    <fmt:message key="page.category.soup"/>
                </a>
            </div>

            <div id="soup" class="collapse" data-parent="#accordion">
                <div class="card-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"></th>
                            <th scope="col">
                                <fmt:message key="page.dish.name"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.weight"/>,
                                <fmt:message key="page.dish.gr"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.calories"/> /
                                100 <fmt:message key="page.dish.gr"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.proteins"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.fats"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.carbohydrates"/>
                            </th>
                            <th scope="col">
                                <button type="button" id="delete_soup_dish" class="btn btn-danger navbar-btn">
                                    <fmt:message key="page.delete"/>
                                </button>
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
                                    <th scope="row">
                                            ${countSoup}
                                    </th>
                                    <td>
                                        <input type="checkbox" name="toDelete[]" value="${dishGeneral.id}"
                                               id="checkbox_${dishGeneral.id}"/>
                                    </td>
                                    <form action="${pageContext.request.contextPath}/swft/updateGeneralDish?numDish=${dishGeneral.id}"
                                          method="POST">
                                        <td>
                                            <fmt:message key="${dishGeneral.name}"/>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="weight"
                                                   step="0.1" value="${dishGeneral.weight/1000}"/>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="calories"
                                                   step="0.1" value="${dishGeneral.calories/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="proteins"
                                                   step="0.1" value="${dishGeneral.proteins/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="fats"
                                                   step="0.1" value="${dishGeneral.fats/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="carbohydrates"
                                                   step="0.1" value="${dishGeneral.carbohydrates/1000}">
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">
                                                <fmt:message key="page.update"/>
                                            </button>
                                        </td>
                                    </form>
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
                            <li class="page-item">
                                <a class="page-link bg-transparent text-danger"
                                   id="${foodCategory}+${loop}"
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

        <div class="card bg-transparent">
            <div class="card-header">
                <a class="card-link text-danger" data-toggle="collapse" href="#hot">
                    <fmt:message key="page.category.hot"/>
                </a>
            </div>

            <div id="hot" class="collapse" data-parent="#accordion">
                <div class="card-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"></th>
                            <th scope="col">
                                <fmt:message key="page.dish.name"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.weight"/>,
                                <fmt:message key="page.dish.gr"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.calories"/> /
                                100 <fmt:message key="page.dish.gr"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.proteins"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.fats"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.carbohydrates"/>
                            </th>
                            <th scope="col">
                                <button type="button" id="delete_hot_dish" class="btn btn-danger navbar-btn">
                                    <fmt:message key="page.delete"/>
                                </button>
                            </th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:set var="countHot" value="0" scope="page"/>
                        <c:forEach items="${generalDishes}" var="dishGeneral">
                            <c:if test="${dishGeneral.foodCategory eq 'HOT'}">
                                <c:set var="foodCategory" value="${dishGeneral.foodCategory}" scope="page"/>
                                <c:set var="countHot" value="${countHot + 1}" scope="page"/>

                                <tr class="${dishGeneral.foodCategory}"
                                    id="${dishGeneral.foodCategory}${countHot}" style="display: none">
                                    <th scope="row">
                                            ${countHot}
                                    </th>
                                    <td>
                                        <input type="checkbox" name="toDelete[]" value="${dishGeneral.id}"
                                               id="checkbox_${dishGeneral.id}"/>
                                    </td>
                                    <form action="${pageContext.request.contextPath}/swft/updateGeneralDish?numDish=${dishGeneral.id}"
                                          method="POST">
                                        <td>
                                            <fmt:message key="${dishGeneral.name}"/>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="weight"
                                                   step="0.1" value="${dishGeneral.weight/1000}"/>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="calories"
                                                   step="0.1" value="${dishGeneral.calories/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="proteins"
                                                   step="0.1" value="${dishGeneral.proteins/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="fats"
                                                   step="0.1" value="${dishGeneral.fats/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="carbohydrates"
                                                   step="0.1" value="${dishGeneral.carbohydrates/1000}">
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">
                                                <fmt:message key="page.update"/>
                                            </button>
                                        </td>
                                    </form>
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
                            <li class="page-item">
                                <a class="page-link bg-transparent text-danger"
                                   id="${foodCategory}+${loop}"
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

        <div class="card bg-transparent">
            <div class="card-header">
                <a class="card-link text-danger" data-toggle="collapse" href="#dessert">
                    <fmt:message key="page.category.dessert"/>
                </a>
            </div>

            <div id="dessert" class="collapse" data-parent="#accordion">
                <div class="card-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"></th>
                            <th scope="col">
                                <fmt:message key="page.dish.name"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.weight"/>,
                                <fmt:message key="page.dish.gr"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.calories"/> /
                                100 <fmt:message key="page.dish.gr"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.proteins"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.fats"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.carbohydrates"/>
                            </th>
                            <th scope="col">
                                <button type="button" id="delete_dessert_dish" class="btn btn-danger navbar-btn">
                                    <fmt:message key="page.delete"/>
                                </button>
                            </th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:set var="countDessert" value="0" scope="page"/>
                        <c:forEach items="${generalDishes}" var="dishGeneral">
                            <c:if test="${dishGeneral.foodCategory eq 'DESSERT'}">
                                <c:set var="foodCategory" value="${dishGeneral.foodCategory}" scope="page"/>
                                <c:set var="countDessert" value="${countDessert + 1}" scope="page"/>

                                <tr class="${dishGeneral.foodCategory}"
                                    id="${dishGeneral.foodCategory}${countDessert}" style="display: none">
                                    <th scope="row">
                                            ${countDessert}
                                    </th>
                                    <td>
                                        <input type="checkbox" name="toDelete[]" value="${dishGeneral.id}"
                                               id="checkbox_${dishGeneral.id}"/>
                                    </td>
                                    <form action="${pageContext.request.contextPath}/swft/updateGeneralDish?numDish=${dishGeneral.id}"
                                          method="POST">
                                        <td>
                                            <fmt:message key="${dishGeneral.name}"/>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="weight"
                                                   step="0.1" value="${dishGeneral.weight/1000}"/>
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="calories"
                                                   step="0.1" value="${dishGeneral.calories/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="proteins"
                                                   step="0.1" value="${dishGeneral.proteins/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="fats"
                                                   step="0.1" value="${dishGeneral.fats/1000}">
                                        </td>
                                        <td>
                                            <input type="number" class="form-control" name="carbohydrates"
                                                   step="0.1" value="${dishGeneral.carbohydrates/1000}">
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">
                                                <fmt:message key="page.update"/>
                                            </button>
                                        </td>
                                    </form>
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
                            <li class="page-item">
                                <a class="page-link bg-transparent text-danger"
                                   id="${foodCategory}+${loop}"
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
    </div>
</div>

<%--Shows row of menu and error with data--%>
<script type="text/javascript">
    menuItemForPageAndErrorData()
</script>
<script>
    function clickPage(currentPage) {
        menuItemForPageAndErrorData(currentPage.id)
    }
</script>
<%--Shows row of menu and error with data--%>
<%--Delete arraay of menu item--%>
<script>
    $('#delete_luncheon_dish,#delete_soup_dish,#delete_hot_dish,#delete_dessert_dish').click(function () {
        var arrDish = {'toDelete[]': []};
        $(":input:checked").each(function () {
            arrDish['toDelete[]'].push($(this).val());
        });

        if (arrDish['toDelete[]'].length > 0) {
            window.location.href = '${pageContext.request.contextPath}/swft/deleteGeneralMenuItem?arrDish=' + arrDish['toDelete[]'];
        }
    });
</script>
<%--Delete arraay of menu item--%>
