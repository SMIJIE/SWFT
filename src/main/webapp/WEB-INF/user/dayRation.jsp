<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>


<script type="text/javascript">
    function showRowAndErrorWithData(messCurrentPage) {
        <%--Shows error with data--%>
        var mess = '${userErrorData}';
        if (mess === 'nonErrorData') {
            $('#userErrorData').css({display: 'none'});
        }
        <%--Shows error with data--%>

        <%--Shows row of ration composition--%>
        var showTrId;
        var hideTrId;
        var messArr;
        if (messCurrentPage == null) {
            for (var i = 1; i <= 6; i++) {
                showTrId = '#FoodIntake' + i;
                $(showTrId).show();
            }
        } else {
            messArr = messCurrentPage.split('+');
            var end = parseInt(messArr[1]) * 6;
            var start = end - 5;
            hideTrId = '.' + messArr[0];
            $(hideTrId).hide();

            for (var i = start; i <= end; i++) {
                showTrId = '#' + messArr[0] + i;
                $(showTrId).show();
            }
        }
        <%--Shows row of ration composition--%>
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
                <a class="card-link" data-toggle="collapse" href="#collapseOne">
                    <fmt:message key="page.menu.edit"/>
                    <mytags:formatDate langCount="${localeLang}" localDate="${localeDate}"/>
                </a>
            </div>

            <div id="collapseOne" class="collapse show" data-parent="#accordion">
                <div class="card-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col" style="width: 15px">
                                <a class="page-link bg-primary text-light" id="numPage=${numPage-1}"
                                   href="#" aria-label="Previous" onclick="serverPage(this)">
                                    <span aria-hidden="true">&laquo;</span>
                                    <span class="sr-only">Previous</span>
                                </a>
                            </th>
                            <th></th>
                            <th scope="col">
                                <fmt:message key="page.ration"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.name"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.amount"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="page.dish.calories"/>
                            </th>
                            <th scope="col">
                                <button type="button" id="delete_composition" class="btn btn-danger navbar-btn">
                                    <fmt:message key="page.delete"/>
                                </button>
                            </th>
                            <th scope="col" style="width: 15px">
                                <a class="page-link bg-primary text-light" id="numPage=${numPage+1}"
                                   href="#" aria-label="Next" onclick="serverPage(this)">
                                    <span aria-hidden="true">&raquo;</span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:set var="countCalories" value="0" scope="page"/>
                        <c:set var="count" value="0" scope="page"/>
                        <c:forEach items="${usersComposition}" var="userCom">
                            <c:if test="${userCom.foodIntake eq 'BREAKFAST'}">
                                <c:set var="count" value="${count + 1}" scope="page"/>
                                <tr class="FoodIntake" id="FoodIntake${count}" style="display: none">
                                    <td>
                                            ${count}
                                    </td>
                                    <td>
                                        <input type="checkbox" name="toDelete[]"
                                               value="${userCom.id}" id="checkbox_${userCom.id}"/>
                                    </td>
                                    <td>
                                        <fmt:message key="page.ration.breakfast"/>
                                    </td>
                                    <td>
                                        <c:if test="${userCom.dish.generalFood eq false}">
                                            ${userCom.dish.name}
                                        </c:if>
                                        <c:if test="${userCom.dish.generalFood eq true}">
                                            <fmt:message key="${userCom.dish.name}"/>
                                        </c:if>
                                    </td>
                                    <form action="${pageContext.request.contextPath}/swft/updateUsersComposition?numComposition=${userCom.id}"
                                          method="POST">
                                        <td>
                                            <input type="number" class="form-control" name="amount"
                                                   value="${userCom.numberOfDish}" min="1" max="5"/>
                                        </td>
                                        <td>
                                            <c:set var="countCalories"
                                                   value="${countCalories+ (userCom.numberOfDish * userCom.dish.calories)/1000}"
                                                   scope="page"/>
                                                ${(userCom.numberOfDish * userCom.dish.calories)/1000}
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">
                                                <fmt:message key="page.update"/>
                                            </button>
                                        </td>
                                    </form>
                                    <td></td>
                                </tr>
                            </c:if>
                        </c:forEach>

                        <c:forEach items="${usersComposition}" var="userCom">
                            <c:if test="${userCom.foodIntake eq 'DINNER'}">
                                <c:set var="count" value="${count + 1}" scope="page"/>
                                <tr class="FoodIntake" id="FoodIntake${count}" style="display: none">
                                    <td>
                                            ${count}
                                    </td>
                                    <td>
                                        <input type="checkbox" name="toDelete[]" value="${userCom.id}"
                                               id="checkbox_${userCom.id}"/>
                                    </td>
                                    <td>
                                        <fmt:message key="page.ration.dinner"/>
                                    </td>
                                    <td>
                                        <c:if test="${userCom.dish.generalFood eq false}">
                                            ${userCom.dish.name}
                                        </c:if>
                                        <c:if test="${userCom.dish.generalFood eq true}">
                                            <fmt:message key="${userCom.dish.name}"/>
                                        </c:if>
                                    </td>
                                    <form action="${pageContext.request.contextPath}/swft/updateUsersComposition?numComposition=${userCom.id}"
                                          method="POST">
                                        <td>
                                            <input type="number" class="form-control" name="amount"
                                                   value="${userCom.numberOfDish}" min="1" max="5"/>
                                        </td>
                                        <td>
                                            <c:set var="countCalories"
                                                   value="${countCalories+ (userCom.numberOfDish * userCom.dish.calories)/1000}"
                                                   scope="page"/>
                                                ${(userCom.numberOfDish * userCom.dish.calories)/1000}
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">
                                                <fmt:message key="page.update"/>
                                            </button>
                                        </td>
                                    </form>
                                    <td></td>
                                </tr>
                            </c:if>
                        </c:forEach>

                        <c:forEach items="${usersComposition}" var="userCom">
                            <c:if test="${userCom.foodIntake eq 'SUPPER'}">
                                <c:set var="count" value="${count + 1}" scope="page"/>
                                <tr class="FoodIntake" id="FoodIntake${count}" style="display: none">
                                    <td>
                                            ${count}
                                    </td>
                                    <td>
                                        <input type="checkbox" name="toDelete[]" value="${userCom.id}"
                                               id="checkbox_${userCom.id}"/>
                                    </td>
                                    <td>
                                        <fmt:message key="page.ration.supper"/>
                                    </td>
                                    <td>
                                        <c:if test="${userCom.dish.generalFood eq false}">
                                            ${userCom.dish.name}
                                        </c:if>
                                        <c:if test="${userCom.dish.generalFood eq true}">
                                            <fmt:message key="${userCom.dish.name}"/>
                                        </c:if>
                                    </td>
                                    <form action="${pageContext.request.contextPath}/swft/updateUsersComposition?numComposition=${userCom.id}"
                                          method="POST">
                                        <td>
                                            <input type="number" class="form-control" name="amount"
                                                   value="${userCom.numberOfDish}" min="1" max="5"/>
                                        </td>
                                        <td>
                                            <c:set var="countCalories"
                                                   value="${countCalories+ (userCom.numberOfDish * userCom.dish.calories)/1000}"
                                                   scope="page"/>
                                                ${(userCom.numberOfDish * userCom.dish.calories)/1000}
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">
                                                <fmt:message key="page.update"/>
                                            </button>
                                        </td>
                                    </form>
                                    <td></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        <tr>
                            <td colspan="5" style="text-align: center">
                                <fmt:message key="calories.total.number"/>
                            </td>
                            <td>
                                ${countCalories}
                            </td>
                            <td colspan="2"></td>
                        </tr>
                        </tbody>
                    </table>

                    <nav aria-label="Page navigation example" class="">
                        <ul class="pagination">
                            &nbsp;&nbsp;
                            <c:forEach begin="1" end="${0.99+usersComposition.size()/6}" var="loop">
                                <li class="page-item">
                                    <a class="page-link bg-transparent text-danger" id="FoodIntake+${loop}"
                                       href="#" onclick="jsPage(this)">
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

        <div class="card bg-transparent">

            <div class="card-header">
                <a class="collapsed card-link" data-toggle="collapse" href="#collapseTwo">
                    <fmt:message key="page.menu.add"/>
                </a>
            </div>

            <div id="collapseTwo" class="collapse" data-parent="#accordion">
                <div class="card-body">
                    <div class="form-group row">
                        <div class="col">
                            <label for="selectMenu">
                                <fmt:message key="page.menu"/>:
                            </label>

                            <select class="form-control" multiple="multiple" id="selectMenu" size="18">
                                <optgroup label="<fmt:message key="page.category.luncheon"/>">
                                    <c:forEach items="${usersDishes}" var="dishUsers">
                                        <c:if test="${dishUsers.foodCategory eq 'LUNCHEON'}">
                                            <option value="${dishUsers.id}" id="${dishUsers.calories}">
                                                <c:if test="${dishUsers.generalFood eq false}">
                                                    ${dishUsers.name}
                                                </c:if>
                                                <c:if test="${dishUsers.generalFood eq true}">
                                                    <fmt:message key="${dishUsers.name}"/>
                                                </c:if>
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </optgroup>

                                <optgroup label="<fmt:message key="page.category.soup"/>">
                                    <c:forEach items="${usersDishes}" var="dishUsers">
                                        <c:if test="${dishUsers.foodCategory eq 'SOUP'}">
                                            <option value="${dishUsers.id}" id="${dishUsers.calories}">
                                                <c:if test="${dishUsers.generalFood eq false}">
                                                    ${dishUsers.name}
                                                </c:if>
                                                <c:if test="${dishUsers.generalFood eq true}">
                                                    <fmt:message key="${dishUsers.name}"/>
                                                </c:if>
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </optgroup>

                                <optgroup label="<fmt:message key="page.category.hot"/>">
                                    <c:forEach items="${usersDishes}" var="dishUsers">
                                        <c:if test="${dishUsers.foodCategory eq 'HOT'}">
                                            <option value="${dishUsers.id}" id="${dishUsers.calories}">
                                                <c:if test="${dishUsers.generalFood eq false}">
                                                    ${dishUsers.name}
                                                </c:if>
                                                <c:if test="${dishUsers.generalFood eq true}">
                                                    <fmt:message key="${dishUsers.name}"/>
                                                </c:if>
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </optgroup>

                                <optgroup label="<fmt:message key="page.category.dessert"/>">
                                    <c:forEach items="${usersDishes}" var="dishUsers">
                                        <c:if test="${dishUsers.foodCategory eq 'DESSERT'}">
                                            <option value="${dishUsers.id}" id="${dishUsers.calories}">
                                                <c:if test="${dishUsers.generalFood eq false}">
                                                    ${dishUsers.name}
                                                </c:if>
                                                <c:if test="${dishUsers.generalFood eq true}">
                                                    <fmt:message key="${dishUsers.name}"/>
                                                </c:if>
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </optgroup>
                            </select>
                        </div>

                        <div class="col">
                            <form method="POST" action="${pageContext.request.contextPath}/swft/createNewRation"
                                  id="dayRation">

                                <label style="text-align: right;display: block" for="breakfast">
                                    <fmt:message key="page.ration.breakfast"/>:
                                </label>
                                <div class="row" id="breakfast">
                                    <div class="btn-group-vertical" role="group">
                                        <button type="button" id="addToBreakfast" class="btn btn-success btn-sm">
                                            &raquo;&nbsp;<fmt:message key="page.menu.add"/>&nbsp;&raquo;
                                        </button>
                                        &nbsp;
                                        <button type="button" id="removeFromBreakfast"
                                                class="btn btn-danger btn-sm">
                                            &laquo;&nbsp;<fmt:message key="page.delete"/>&nbsp;&laquo;
                                        </button>
                                    </div>

                                    <div class="col">
                                        <select class="form-control" multiple="multiple" id="selectBreakfast"
                                                name="selectBreakfast" size="4"></select>
                                    </div>
                                </div>

                                <label style="text-align: right;display: block" for="dinner">
                                    <fmt:message key="page.ration.dinner"/>:
                                </label>
                                <div class="row" id="dinner">
                                    <div class="btn-group-vertical" role="group">
                                        <button type="button" id="addToDinner" class="btn btn-success btn-sm">
                                            &raquo;&nbsp;<fmt:message key="page.menu.add"/>&nbsp;&raquo;
                                        </button>
                                        &nbsp;
                                        <button type="button" id="removeFromDinner" class="btn btn-danger btn-sm">
                                            &laquo;&nbsp;<fmt:message key="page.delete"/>&nbsp;&laquo;
                                        </button>
                                    </div>
                                    <div class="col">
                                        <select class="form-control" multiple="multiple" id="selectDinner"
                                                name="selectDinner" size="4">
                                        </select>
                                    </div>
                                </div>

                                <label style="text-align: right;display: block" for="supper">
                                    <fmt:message key="page.ration.supper"/>:
                                </label>
                                <div class="row" id="supper">
                                    <div class="btn-group-vertical" role="group">
                                        <button type="button" id="addToSupper" class="btn btn-success btn-sm">
                                            &raquo;&nbsp;<fmt:message key="page.menu.add"/>&nbsp;&raquo;
                                        </button>
                                        &nbsp;
                                        <button type="button" id="removeFromSupper" class="btn btn-danger btn-sm">
                                            &laquo;&nbsp;<fmt:message key="page.delete"/>&nbsp;&laquo;
                                        </button>
                                    </div>
                                    <div class="col">
                                        <select class="form-control" multiple="multiple" id="selectSupper"
                                                name="selectSupper" size="4">
                                        </select>
                                    </div>
                                </div>

                                <div class="row" style="margin-top: 10px">
                                    <div class="btn-group" role="group">
                                        <input type="date" class="form-control" id="date" name="date">
                                        &nbsp;
                                        <button type="submit" class="btn btn-primary">
                                            <fmt:message key="page.write"/>
                                        </button>
                                        &nbsp;
                                        <button type="button" class="btn btn-primary" id="calculateCalories">
                                            <fmt:message key="page.calculate"/>
                                        </button>
                                        &nbsp;
                                        <button type="button" class="btn btn-sm bg-transparent">
                                            <fmt:message key="page.chosenCalories"/> =
                                            <span class="badge badge-dark" id='resultChosenCalories'>0</span>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--Shows row for users and  error with data--%>
<script type="text/javascript">
    showRowAndErrorWithData()
</script>
<%--Shows row for users and  error with data--%>
<%--Sent number of page--%>
<script>
    function serverPage(currentPage) {
        window.location.href = '${pageContext.request.contextPath}/swft/listUserDayRation?' + currentPage.id;
    }

    function jsPage(currentPage) {
        showRowAndErrorWithData(currentPage.id)
    }
</script>
<%--Sent number of page--%>
<%--Delete array of composition item--%>
<script>
    $('#delete_composition').click(function () {
        var arrDish = {'toDelete[]': []};
        $(":input:checked").each(function () {
            arrDish['toDelete[]'].push($(this).val());
        });

        if (arrDish['toDelete[]'].length > 0) {
            window.location.href = '${pageContext.request.contextPath}/swft/deleteUsersComposition?arrComposition=' + arrDish['toDelete[]'];
        }
    });
</script>
<%--Delete array of composition item--%>
<%--For selecting menu item for food intake--%>
<script type="text/javascript">
    $(document).ready(function () {
        // Breakfast
        $('#addToBreakfast').click(function () {
            $('#selectMenu option:selected').each(function () {
                var tempDish = $(this).clone();
                $(tempDish).appendTo('#selectBreakfast');
            });
        });
        $('#removeFromBreakfast').click(function () {
            $('#selectBreakfast option:selected').each(function () {
                $(this).remove();
            });
        });
        // Breakfast

        // Dinner
        $('#addToDinner').click(function () {
            $('#selectMenu option:selected').each(function () {
                var tempDish = $(this).clone();
                $(tempDish).appendTo('#selectDinner');
            });
        });
        $('#removeFromDinner').click(function () {
            $('#selectDinner option:selected').each(function () {
                $(this).remove();
            });
        });
        // Dinner

        // Supper
        $('#addToSupper').click(function () {
            $('#selectMenu option:selected').each(function () {
                var tempDish = $(this).clone();
                $(tempDish).appendTo('#selectSupper');
            });
        });
        $('#removeFromSupper').click(function () {
            $('#selectSupper option:selected').each(function () {
                $(this).remove();
            });
        });
        // Supper

        /*Selected all dishes and sent to server*/
        $('#dayRation').submit(function (event) {
            var myDate = new Date($('#date').val());
            var currentDate = new Date();
            if (!isNaN(myDate)) {
                if ((currentDate.getDate() - myDate.getDate()) > 14
                    || (currentDate.getDate() - myDate.getDate()) < -14) {
                    event.preventDefault();
                    alert('<fmt:message key="page.date.wrong"/>');
                    return;
                }
            }

            if ($('#selectBreakfast option').length == 0
                && $('#selectDinner option').length == 0
                && $('#selectSupper option').length == 0) {
                event.preventDefault();
                return;
            }

            $('#selectBreakfast option').prop('selected', true);
            $('#selectDinner option').prop('selected', true);
            $('#selectSupper option').prop('selected', true);
        });
        /*Selected all dishes and sent to server*/

        /*Calculate chosen calories*/
        $('#calculateCalories').click(function () {
            var sumCalories = 0;
            $('#selectBreakfast option').each(function () {
                sumCalories += parseInt($(this).attr("id"));
            });
            $('#selectDinner option').each(function () {
                sumCalories += parseInt($(this).attr("id"));
            });
            $('#selectSupper option').each(function () {
                sumCalories += parseInt($(this).attr("id"));
            });
            $('span#resultChosenCalories').text(sumCalories / 1000);
        });
        /*Calculate chosen calories*/
    });
</script>
<%--For selecting menu item for food intake--%>
