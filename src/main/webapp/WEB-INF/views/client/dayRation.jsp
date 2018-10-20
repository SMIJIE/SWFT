<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>


<script type="text/javascript">
    function showRowAndErrorWithData(messCurrentPage) {
        <%--Shows error with data--%>
        var mess = '${userError}';
        if (mess === "") {
            $('#userErrorData').css({display: 'none'});
        }
        <%--Shows error with data--%>

        <%--Shows row of ration composition--%>
        var showTrId;
        var hideTrId;
        var messArr;
        if (messCurrentPage == null) {
            for (var i = 1; i <= 5; i++) {
                showTrId = '#FoodIntake' + i;
                $(showTrId).show();
            }
        } else {
            messArr = messCurrentPage.split('+');
            var end = parseInt(messArr[1]) * 5;
            var start = end - 4;
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
                <spring:message code="${userError}"/>
            </div>
        </div>

        <div class="card bg-transparent">
            <div class="card-header">
                <a class="card-link" data-toggle="collapse" href="#collapseOne">
                    <spring:message code="menu.edit"/>
                    <spring:message code="page.langLocale" var="langLocale"/>
                    <mytags:formatDate langCount="${langLocale}" localDate="${localeDate}"/>
                </a>
            </div>

            <div id="collapseOne" class="collapse ${showCollapseDayRationComposition}" data-parent="#accordion">
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
                                <spring:message code="page.ration"/>
                            </th>
                            <th scope="col">
                                <spring:message code="dish.name"/>
                            </th>
                            <th scope="col">
                                <spring:message code="dish.amount"/>
                            </th>
                            <th scope="col">
                                <spring:message code="dish.calories"/>
                            </th>
                            <th scope="col">
                                <button type="button" id="delete_composition" class="btn btn-danger navbar-btn">
                                    <spring:message code="page.delete"/>
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
                        <c:set var="countPages" value="0" scope="page"/>

                        <c:forEach items="${usersComposition}" var="userCom">

                            <c:set var="countPages" value="${countPages+userCom.value.size()}" scope="page"/>
                            <c:forEach items="${userCom.value}" var="userComValue">

                                <c:set var="count" value="${count + 1}" scope="page"/>
                                <tr class="FoodIntake" id="FoodIntake${count}" style="display: none">
                                    <td>
                                            ${count}
                                    </td>
                                    <td>
                                        <input type="checkbox" name="toDelete[]" value="${userComValue.id}"/>
                                    </td>
                                    <form:form method="POST" id="userUpdateComposition" modelAttribute="formRC"
                                               action="userUpdateComposition?numComposition=${userComValue.id}&numPage=${numPage}">
                                        <td>
                                            <form:select path="foodIntake" class="form-control" id="foodIntake"
                                                         name="foodIntake">
                                                <form:option value="BREAKFAST"
                                                             selected="${userComValue.foodIntake eq 'BREAKFAST' ? 'selected' : ''}">
                                                    <spring:message code="ration.breakfast"/>
                                                </form:option>
                                                <form:option value="DINNER"
                                                             selected="${userComValue.foodIntake eq 'DINNER' ? 'selected' : ''}">
                                                    <spring:message code="ration.dinner"/>
                                                </form:option>
                                                <form:option value="SUPPER"
                                                             selected="${userComValue.foodIntake eq 'SUPPER' ? 'selected' : ''}">
                                                    <spring:message code="ration.supper"/>
                                                </form:option>
                                            </form:select>
                                        </td>
                                        <td>
                                            <c:if test="${userComValue.dish.generalFood eq false}">
                                                ${userComValue.dish.name}
                                            </c:if>
                                            <c:if test="${userComValue.dish.generalFood eq true}">
                                                <spring:message code="${userComValue.dish.name}"/>
                                            </c:if>
                                        </td>
                                        <td>
                                            <form:input path="numberOfDish" type="number" class="form-control"
                                                        name="numberOfDish" min="1"
                                                        value="${userComValue.numberOfDish}"/>
                                        </td>
                                        <td>
                                            <c:set var="countCalories" scope="page"
                                                   value="${countCalories+ (userComValue.numberOfDish * userComValue.dish.calories)/1000}"/>
                                                ${(userComValue.numberOfDish * userComValue.dish.calories)/1000}
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-success">
                                                <spring:message code="page.update"/>
                                            </button>
                                        </td>
                                    </form:form>
                                    <td></td>
                                </tr>
                            </c:forEach>
                        </c:forEach>

                        <tr>
                            <td colspan="5" style="text-align: center">
                                <spring:message code="calories.totalNumber"/>
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
                            <c:forEach begin="1" end="${0.99+countPages/5}" var="loop">
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
                    <spring:message code="menu.add"/>&nbsp;&nbsp;&nbsp;
                    <span style="color:#D2691E; visibility: hidden" id="dateError">
                        <spring:message code="valid.dayRation.date"/>
                    </span>
                </a>
            </div>

            <div id="collapseTwo" class="collapse ${showCollapseDayRationAddComposition}" data-parent="#accordion">
                <div class="card-body">
                    <div class="form-group row">
                        <div class="col">
                            <label for="selectMenu">
                                <spring:message code="page.menu"/>:
                            </label>

                            <select class="form-control" multiple="multiple" id="selectMenu" size="18">
                                <c:forEach items="${usersDishes}" var="dishUsers">
                                    <optgroup label="<spring:message code="category.${dishUsers.key}"/>">
                                        <c:forEach items="${dishUsers.value}" var="dishUsersValue">
                                            <option value="${dishUsersValue.id}" id="${dishUsersValue.calories}">
                                                <c:if test="${dishUsersValue.generalFood eq false}">
                                                    ${dishUsersValue.name}
                                                </c:if>
                                                <c:if test="${dishUsersValue.generalFood eq true}">
                                                    <spring:message code="${dishUsersValue.name}"/>
                                                </c:if>
                                            </option>
                                        </c:forEach>
                                    </optgroup>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col">
                            <form:form method="POST" id="dayRation" modelAttribute="formRC"
                                       action="createNewRation?numPage=${numPage}">

                                <form:label path="breakfast" style="text-align: right;display: block">
                                    <spring:message code="ration.breakfast"/>:
                                </form:label>
                                <div class="row" id="breakfast">
                                    <div class="btn-group-vertical" role="group">
                                        <button type="button" id="addToBreakfast" class="btn btn-success btn-sm">
                                            &raquo;&nbsp;<spring:message code="menu.add"/>&nbsp;&raquo;
                                        </button>
                                        &nbsp;
                                        <button type="button" id="removeFromBreakfast" class="btn btn-danger btn-sm">
                                            &laquo;&nbsp;<spring:message code="page.delete"/>&nbsp;&laquo;
                                        </button>
                                    </div>
                                    <div class="col">
                                        <form:select path="breakfast" class="form-control" multiple="multiple"
                                                     id="selectBreakfast" name="selectBreakfast" size="4">
                                        </form:select>
                                    </div>
                                </div>

                                <form:label path="dinner" style="text-align: right;display: block">
                                    <spring:message code="ration.dinner"/>:
                                </form:label>
                                <div class="row" id="dinner">
                                    <div class="btn-group-vertical" role="group">
                                        <button type="button" id="addToDinner" class="btn btn-success btn-sm">
                                            &raquo;&nbsp;<spring:message code="menu.add"/>&nbsp;&raquo;
                                        </button>
                                        &nbsp;
                                        <button type="button" id="removeFromDinner" class="btn btn-danger btn-sm">
                                            &laquo;&nbsp;<spring:message code="page.delete"/>&nbsp;&laquo;
                                        </button>
                                    </div>
                                    <div class="col">
                                        <form:select path="dinner" class="form-control" multiple="multiple"
                                                     id="selectDinner" name="selectDinner" size="4">
                                        </form:select>
                                    </div>
                                </div>

                                <form:label path="supper" style="text-align: right;display: block">
                                    <spring:message code="ration.supper"/>:
                                </form:label>
                                <div class="row" id="supper">
                                    <div class="btn-group-vertical" role="group">
                                        <button type="button" id="addToSupper" class="btn btn-success btn-sm">
                                            &raquo;&nbsp;<spring:message code="menu.add"/>&nbsp;&raquo;
                                        </button>
                                        &nbsp;
                                        <button type="button" id="removeFromSupper" class="btn btn-danger btn-sm">
                                            &laquo;&nbsp;<spring:message code="page.delete"/>&nbsp;&laquo;
                                        </button>
                                    </div>
                                    <div class="col">
                                        <form:select path="supper" class="form-control" multiple="multiple"
                                                     id="selectSupper" name="selectSupper" size="4">
                                        </form:select>
                                    </div>
                                </div>

                                <div class="row" style="margin-top: 10px">
                                    <div class="btn-group" role="group">
                                        <form:input path="date" type="date" class="form-control" id="date" name="date"/>
                                        &nbsp;
                                        <button type="submit" class="btn btn-primary">
                                            <spring:message code="page.write"/>
                                        </button>
                                        &nbsp;
                                        <button type="button" class="btn btn-primary" id="calculateCalories">
                                            <spring:message code="page.calculate"/>
                                        </button>
                                        &nbsp;
                                        <button type="button" class="btn btn-sm bg-transparent">
                                            <spring:message code="page.chosenCalories"/> =
                                            <span class="badge badge-secondary" id='resultChosenCalories'>0</span>
                                        </button>
                                    </div>
                                </div>
                            </form:form>
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
<%--Shows row for`x` users and  error with data--%>
<%--Sent number of page--%>
<script type="text/javascript">
    function serverPage(currentPage) {
        window.location.href = '${pageContext.request.contextPath}/swft/user/dayRation?' + currentPage.id;
    }

    function jsPage(currentPage) {
        showRowAndErrorWithData(currentPage.id)
    }
</script>
<%--Sent number of page--%>
<%--Delete array of composition item--%>
<script type="text/javascript">
    $('#delete_composition').click(function () {
        var arrDish = {'toDelete[]': []};
        $(":input:checked").each(function () {
            arrDish['toDelete[]'].push($(this).val());
        });

        if (arrDish['toDelete[]'].length > 0) {
            window.location.href = '${pageContext.request.contextPath}/swft//user/userDeleteComposition?arrComposition=' + arrDish['toDelete[]'] + '&numPage=' +${numPage};
        }
    });
</script>
<%--Delete array of composition item--%>
<%--For selecting menu item for food intake--%>
<script type="text/javascript">
    $(document).ready(function () {
        <%--Breakfast--%>
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
        <%--Breakfast--%>

        <%--Dinner--%>
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
        <%--Dinner--%>

        <%--Supper--%>
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
        <%--Supper--%>

        <%--Selected all dishes and sent to server--%>
        $('#dayRation').submit(function (event) {
            var myDate = new Date($('#date').val());
            var currentDate = new Date();
            var timeDiff = Math.abs(currentDate.getTime() - myDate.getTime());
            var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
            if (!isNaN(myDate)) {
                if (diffDays > 14 || diffDays < -14) {
                    event.preventDefault();
                    $('#dateError').css({visibility: 'visible'});
                    return;
                } else {
                    $('#dateError').css({visibility: 'hidden'});
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
        <%--Selected all dishes and sent to server--%>

        <%--Calculate chosen calories--%>
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
        <%--Calculate chosen calories--%>
    });
</script>
<%--For selecting menu item for food intake--%>
