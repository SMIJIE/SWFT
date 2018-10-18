<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<script type="text/javascript">
    function menuItemForPageAndErrorData(messCurrentPage) {
        <%--Shows row of menu--%>
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
        var mess = '${userError}';
        if (mess === "") {
            $('#userErrorData').css({display: 'none'});
        }
        <%--Shows error with data--%>
    }
</script>

<div id="content">

    <div id="accordion">
        <div class="card text-light bg-danger" id="userErrorData">
            <div class="card-header">
                <fmt:message key="${userError}"/>
            </div>
        </div>

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
                                <th scope="col"></th>
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
                                <th scope="col">
                                    <button type="button" id="delete_luncheon_dish" class="btn btn-danger navbar-btn">
                                        <fmt:message key="page.delete"/>
                                    </button>
                                </th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:set var="counter" value="0" scope="page"/>
                            <c:forEach items="${generalDish.value}" var="dishGeneralValue">

                                <c:set var="foodCategory" value="${dishGeneralValue.foodCategory}" scope="page"/>
                                <c:set var="counter" value="${counter + 1}" scope="page"/>
                                <tr style="display: none" class="${foodCategory}" id="${foodCategory}${counter}">
                                    <th scope="row">
                                            ${counter}
                                    </th>
                                    <td>
                                        <input type="checkbox" name="toDelete[]"
                                               value="${dishGeneralValue.id}" id="checkbox_${dishGeneralValue.id}"/>
                                    </td>
                                    <td>
                                        <spring:message code="${dishGeneralValue.name}"/>
                                    </td>
                                    <form:form method="POST" id="updateDish" modelAttribute="formDish"
                                               action="updateGeneralDish?numDish=${dishGeneralValue.id}">
                                        <form:hidden path="foodCategory" value="${foodCategory}"/>
                                        <form:hidden path="name" value="${dishGeneralValue.name}"/>
                                        <td>
                                            <form:input path="weight" type="number" class="form-control" step="0.1"
                                                        id="weightUpdate" value="${dishGeneralValue.weight/1000}"/>
                                        </td>
                                        <td>
                                            <form:input path="calories" type="number" class="form-control" step="0.1"
                                                        id="caloriesUpdate" value="${dishGeneralValue.calories/1000}"/>
                                        </td>
                                        <td>
                                            <form:input path="proteins" type="number" class="form-control" step="0.1"
                                                        id="proteinsUpdate" value="${dishGeneralValue.proteins/1000}"/>
                                        </td>
                                        <td>
                                            <form:input path="fats" type="number" class="form-control" step="0.1"
                                                        id="fatsUpdate" value="${dishGeneralValue.fats/1000}"/>
                                        </td>
                                        <td>
                                            <form:input path="carbohydrates" type="number" class="form-control"
                                                        step="0.1"
                                                        id="carbohydratesUpdate"
                                                        value="${dishGeneralValue.carbohydrates/1000}"/>
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">
                                                <spring:message code="page.update"/>
                                            </button>
                                        </td>
                                    </form:form>
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

<%--Shows row of menu and error with data--%>
<script type="text/javascript">
    menuItemForPageAndErrorData()
</script>
<script type="text/javascript">
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
            window.location.href = '${pageContext.request.contextPath}/swft/admin/deleteGeneralDishItem?arrDish=' + arrDish['toDelete[]'];
        }
    });
</script>
<%--Delete arraay of menu item--%>
