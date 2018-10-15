<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<%--Shows error with data--%>
<script type="text/javascript">
    function showErrorWithData() {
        var mess = '${userError}';
        if (mess === '') {
            $('#userErrorData').css({display: 'none'});
        }
    }
</script>
<%--Shows error with data--%>

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
                    <spring:message code="menu.own"/>
                </a>
            </div>

            <div id="collapseOne" class="collapse ${showCollapseMenuUsersPage}" data-parent="#accordion">
                <div class="card-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"></th>
                            <th scope="col">
                                <spring:message code="page.foodCategory"/>
                            </th>
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
                                <button type="button" id="delete_users_dish" class="btn btn-danger navbar-btn">
                                    <spring:message code="page.delete"/>
                                </button>
                            </th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:set var="count" value="${((numPage-1)*5)}" scope="page"/>
                        <c:forEach items="${usersDishes}" var="dishGeneral">
                            <c:set var="count" value="${count+ 1}" scope="page"/>

                            <tr>
                                <th scope="row">
                                        ${count}
                                </th>
                                <td>
                                    <input type="checkbox" name="toDelete[]" value="${dishGeneral.id}"
                                           id="checkbox_${dishGeneral.id}"/>
                                </td>

                                <form:form method="POST" action="userUpdateDish?numDish=${dishGeneral.id}"
                                           modelAttribute="formDish">
                                    <td>
                                        <form:select path="foodCategory" class="form-control"
                                                     id="foodCategory" name="foodCategory">
                                            <form:option disabled="true" selected="true"
                                                         value="${dishGeneral.foodCategory}">
                                                <spring:message
                                                        code="category.${fn:toLowerCase(dishGeneral.foodCategory)}"/>
                                            </form:option>
                                            <form:option value="LUNCHEON">
                                                <spring:message code="category.luncheon"/>
                                            </form:option>
                                            <form:option value="HOT">
                                                <spring:message code="category.hot"/>
                                            </form:option>
                                            <form:option value="SOUP">
                                                <spring:message code="category.soup"/>
                                            </form:option>
                                            <form:option value="DESSERT">
                                                <spring:message code="category.dessert"/>
                                            </form:option>
                                        </form:select>
                                    </td>
                                    <td>
                                        <form:input path="name" type="text" class="form-control"
                                                    id="name" value="${dishGeneral.name}"/>
                                    </td>
                                    <td>
                                        <form:input path="weight" type="number" class="form-control"
                                                    step="0.1" value="${dishGeneral.weight/1000}"/>
                                    </td>
                                    <td>
                                        <form:input path="calories" type="number" class="form-control"
                                                    step="0.1" value="${dishGeneral.calories/1000}"/>
                                    </td>
                                    <td>
                                        <form:input path="proteins" type="number" class="form-control"
                                                    step="0.1" value="${dishGeneral.proteins/1000}"/>
                                    </td>
                                    <td>
                                        <form:input path="fats" type="number" class="form-control"
                                                    step="0.1" value="${dishGeneral.fats/1000}"/>
                                    </td>
                                    <td>
                                        <form:input path="carbohydrates" type="number" class="form-control"
                                                    step="0.1" value="${dishGeneral.carbohydrates/1000}"/>
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

                        <li class="page-item">
                            <a class="page-link bg-transparent text-primary" id="numPage=${numPage-1}"
                               href="#" aria-label="Previous" onclick="clickPage(this)">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>

                        &nbsp;
                        <c:forEach begin="1" end="${0.99+numUserDish/5}" var="loop">
                            <c:if test="${loop eq numPage}">
                                <li class="page-item active">
                            </c:if>
                            <c:if test="${loop ne numPage}">
                                <li class="page-item">
                            </c:if>
                            <a class="page-link bg-transparent text-primary"
                               id="numPage=${loop}"
                               href="#" onclick="clickPage(this)">
                                    ${loop}
                            </a>
                            </li>
                            &nbsp;
                        </c:forEach>

                        <li class="page-item">
                            <a class="page-link bg-transparent text-primary" id="numPage=${numPage+1}"
                               href="#" aria-label="Next" onclick="clickPage(this)">
                                <span aria-hidden="true">&raquo;</span>
                                <span class="sr-only">Next</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

        <div class="card bg-transparent">
            <div class="card-header">
                <a class="collapsed card-link" data-toggle="collapse" href="#collapseTwo">
                    <spring:message code="menu.add"/>
                </a>
            </div>

            <div id="collapseTwo" class="collapse ${showCollapseMenuAddDish}" data-parent="#accordion">
                <div class="card-body">
                    <form:form method="POST" action="addNewDish" id="addNewDish" modelAttribute="formDish">
                        <div class="form-group row">
                            <div class="col">
                                <spring:bind path="foodCategory">
                                    <form:label path="foodCategory">
                                        <spring:message code="page.foodCategory"/>:&nbsp;
                                        <span style="color:#D2691E; visibility: hidden" id="foodCategoryError">
                                            <spring:message code="valid.foodCategory.notNull"/>
                                        </span>
                                    </form:label>
                                    <form:select path="foodCategory" class="form-control"
                                                 id="foodCategory" name="foodCategory">
                                        <form:option selected="true" value="LUNCHEON">
                                            <spring:message code="category.luncheon"/>
                                        </form:option>
                                        <form:option value="HOT">
                                            <spring:message code="category.hot"/>
                                        </form:option>
                                        <form:option value="SOUP">
                                            <spring:message code="category.soup"/>
                                        </form:option>
                                        <form:option value="DESSERT">
                                            <spring:message code="category.dessert"/>
                                        </form:option>
                                    </form:select>
                                    <p style="color:red" id="foodCategoryErrorAfterPost">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>

                            <div class="col">
                                <spring:bind path="name">
                                    <form:label path="name">
                                        <spring:message code="dish.name"/>:&nbsp;
                                        <span style="color:#D2691E; visibility: hidden" id="nameError">
                                            <spring:message code="wrong.dish.name"/>
                                        </span>
                                    </form:label>
                                    <spring:message code="example.dish" var="example"/>
                                    <form:input path="name" type="text" class="form-control" id="name"
                                                placeholder="${example}" value="${formDish.name}"
                                                onkeyup="checkInputs('name')"/>
                                    <p style="color:red" id="nameErrorAfterPost">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col">
                                <spring:bind path="weight">
                                    <form:label path="weight">
                                        <spring:message code="dish.weight"/>,&nbsp;
                                        <spring:message code="dish.gr"/>&nbsp;:&nbsp;
                                        <span style="color:#D2691E; visibility: hidden" id="weightError">
                                            <spring:message code="valid.dish.weight.size"/>
                                        </span>
                                    </form:label>
                                    <form:input path="weight" type="number" class="form-control" id="weight"
                                                step="0.1" placeholder="150,0" value="${formDish.weight}"
                                                onkeyup="checkInputs('weight')"/>
                                    <p style="color:red" id="weightErrorAfterPost">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>

                            <div class="col">
                                <spring:bind path="calories">
                                    <form:label path="calories">
                                        <spring:message code="dish.calories"/>:&nbsp;
                                        <span style="color:#D2691E; visibility: hidden" id="caloriesError">
                                            <spring:message code="valid.dish.calories.size"/>
                                        </span>
                                    </form:label>
                                    <form:input path="calories" type="number" class="form-control" id="calories"
                                                step="0.1" placeholder="300,0" value="${formDish.calories}"
                                                onkeyup="checkInputs('calories')"/>
                                    <p style="color:red" id="caloriesErrorAfterPost">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col">
                                <spring:bind path="proteins">
                                    <form:label path="proteins">
                                        <spring:message code="dish.proteins"/>:&nbsp;<br/>
                                        <span style="color:#D2691E; visibility: hidden" id="proteinsError">
                                            <spring:message code="valid.dish.proteins.size"/>
                                        </span>
                                    </form:label>
                                    <form:input path="proteins" type="number" class="form-control" id="proteins"
                                                step="0.1" placeholder="1,5" value="${formDish.proteins}"
                                                onkeyup="checkInputs('proteins')"/>
                                    <p style="color:red" id="proteinsErrorAfterPost">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>

                            <div class="col">
                                <spring:bind path="fats">
                                    <form:label path="fats">
                                        <spring:message code="dish.fats"/>:&nbsp;<br/>
                                        <span style="color:#D2691E; visibility: hidden" id="fatsError">
                                            <spring:message code="valid.dish.fats.size"/>
                                        </span>
                                    </form:label>
                                    <form:input path="fats" type="number" class="form-control" id="fats"
                                                step="0.1" placeholder="0,5" value="${formDish.fats}"
                                                onkeyup="checkInputs('fats')"/>
                                    <p style="color:red" id="fatsErrorAfterPost">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>

                            <div class="col">
                                <spring:bind path="carbohydrates">
                                    <form:label path="carbohydrates">
                                        <spring:message code="dish.carbohydrates"/>:&nbsp;<br/>
                                        <span style="color:#D2691E; visibility: hidden" id="carbohydratesError">
                                            <spring:message code="valid.dish.carbohydrates.size"/>
                                        </span>
                                    </form:label>
                                    <form:input path="carbohydrates" type="number" class="form-control"
                                                id="carbohydrates"
                                                step="0.1" placeholder="4,5" value="${formDish.carbohydrates}"
                                                onkeyup="checkInputs('carbohydrates')"/>
                                    <p style="color:red" id="carbohydratesErrorAfterPost">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-success">
                            <spring:message code="menu.add"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>


<%--Shows error with data--%>
<script type="text/javascript">
    showErrorWithData()
</script>
<%--Shows error with data--%>
<%--Regex for dish parameters check dynamically--%>
<script type="text/javascript">
    function checkInputs(inputAttribute) {
        var checkAttribute = $('#' + inputAttribute).val();
        $('#' + inputAttribute + 'Error').css({visibility: 'visible'});
        $('#' + inputAttribute + 'ErrorAfterPost').css({visibility: 'hidden'});

        var REGEX_DISH_NAME_US = new RegExp(/^[A-Z][a-z]+(\s[A-Za-z]+)?(\s[A-Za-z]+)?$/g);
        var REGEX_DISH_NAME_UA = new RegExp(/^[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+(\s[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+)?(\s[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+)?/g);

        if (inputAttribute === "name") {
            if (REGEX_DISH_NAME_US.test(checkAttribute) ||
                REGEX_DISH_NAME_UA.test(checkAttribute)) {
                $('#' + inputAttribute + 'Error').css({visibility: 'hidden'});
                return;
            }
        } else if (inputAttribute === "weight") {
            if ((checkAttribute >= 50) && (checkAttribute <= 999)) {
                $('#' + inputAttribute + 'Error').css({visibility: 'hidden'});
                return;
            }
        } else if (inputAttribute === "calories" || inputAttribute === "proteins" ||
            inputAttribute === "fats" || inputAttribute === "carbohydrates") {
            if ((checkAttribute >= 0.001) && (checkAttribute <= 1000)) {
                $('#' + inputAttribute + 'Error').css({visibility: 'hidden'});
                return;
            }
        }
    }
</script>
<%--Regex for dish parameters check dynamically--%>
<%--Sent number of page--%>
<script>
    function clickPage(currentPage) {
        window.location.href = '${pageContext.request.contextPath}/swft/menuUsersEdit?' + currentPage.id;
    }
</script>
<%--Sent number of page--%>
<%--Delete users dishes--%>
<script>
    $('#delete_users_dish').click(function () {
        var arrDish = {'toDelete[]': []};
        $(":input:checked").each(function () {
            arrDish['toDelete[]'].push($(this).val());
        });

        if (arrDish['toDelete[]'].length > 0) {
            window.location.href = '${pageContext.request.contextPath}/swft/userDeleteDish?arrDish=' + arrDish['toDelete[]'];
        }
    });
</script>
<%--Delete users dishes--%>