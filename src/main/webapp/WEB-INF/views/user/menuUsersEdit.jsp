<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<%--Shows error with data--%>
<script type="text/javascript">
    function showErrorWithData() {
        var mess = '${userErrorData}';
        if (mess === 'nonErrorData') {
            $('#userErrorData').css({display: 'none'});
        }
    }
</script>
<%--Shows error with data--%>
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
                    <fmt:message key="page.menu.own"/>
                </a>
            </div>

            <div id="collapseOne" class="collapse show" data-parent="#accordion">
                <div class="card-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"></th>
                            <th scope="col">
                                <fmt:message key="page.category"/>
                            </th>
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
                                <button type="button" id="delete_users_dish" class="btn btn-danger navbar-btn">
                                    <fmt:message key="page.delete"/>
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
                                <td>
                                    <fmt:message key="page.category.${fn:toLowerCase(dishGeneral.foodCategory)}"/>
                                </td>
                                <td>
                                        ${dishGeneral.name}
                                </td>
                                <form action="${pageContext.request.contextPath}/swft/updateUsersDish?numDish=${dishGeneral.id}"
                                      method="POST">
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
                    <fmt:message key="page.menu.add"/>
                </a>
            </div>

            <div id="collapseTwo" class="collapse" data-parent="#accordion">
                <div class="card-body">
                    <form method="POST" action="${pageContext.request.contextPath}/swft/addNewDish"
                          id="addNewDish">
                        <div class="form-group row">
                            <div class="col">
                                <label for="category">
                                    <fmt:message key="page.category"/>:
                                </label>
                                <select class="form-control" id="category" name="category">
                                    <option selected value="LUNCHEON">
                                        <fmt:message key="page.category.luncheon"/>
                                    </option>
                                    <option value="HOT">
                                        <fmt:message key="page.category.hot"/>
                                    </option>
                                    <option value="SOUP">
                                        <fmt:message key="page.category.soup"/>
                                    </option>
                                    <option value="DESSERT">
                                        <fmt:message key="page.category.dessert"/>
                                    </option>
                                </select>
                            </div>
                            <div class="col">
                                <label for="name">
                                    <fmt:message key="page.dish.name"/>:
                                </label>
                                <input type="text" class="form-control" id="name" name="name"
                                       placeholder="<fmt:message key="page.example.dish"/>">
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col">
                                <label for="weight">
                                    <fmt:message key="page.dish.weight"/>,&nbsp;
                                    <fmt:message key="page.dish.gr"/>&nbsp;:
                                </label>
                                <input type="number" class="form-control" id="weight" name="weight" step="0.1"
                                       placeholder="150,0">
                            </div>
                            <div class="col">
                                <label for="calories">
                                    <fmt:message key="page.dish.calories"/>:
                                </label>
                                <input type="number" class="form-control" id="calories" name="calories" step="0.1"
                                       placeholder="300,0">
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col">
                                <label for="proteins">
                                    <fmt:message key="page.dish.proteins"/>:
                                </label>
                                <input type="number" class="form-control" id="proteins" name="proteins" step="0.1"
                                       placeholder="1,5">
                            </div>
                            <div class="col">
                                <label for="fats">
                                    <fmt:message key="page.dish.fats"/>:
                                </label>
                                <input type="number" class="form-control" id="fats" name="fats" step="0.1"
                                       placeholder="0,5">
                            </div>
                            <div class="col">
                                <label for="carbohydrates">
                                    <fmt:message key="page.dish.carbohydrates"/>:
                                </label>
                                <input type="number" class="form-control" id="carbohydrates" name="carbohydrates"
                                       step="0.1" placeholder="4,5">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-success">
                            <fmt:message key="page.menu.add"/>
                        </button>
                    </form>
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
<%--Regex for add new dish--%>
<script>
    document.getElementById('addNewDish').addEventListener('submit', function (reg) {
        var name = document.getElementById('name');
        var weight = document.getElementById('weight');
        var calories = document.getElementById('calories');
        var proteins = document.getElementById('proteins');
        var fats = document.getElementById('fats');
        var carbohydrates = document.getElementById('carbohydrates');

        if (!(name.value.match(/^[A-Z][a-z]+(\s[A-Za-z]+)?(\s[A-Za-z]+)?$/g) ||
            name.value.match(/^[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+(\s[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+)?(\s[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+)?/g))) {
            reg.preventDefault();
            alert('<fmt:message key="page.dish.name.wrong"/>');
            return;
        } else if ((weight.value < 50) || (weight.value > 999)) {
            reg.preventDefault();
            alert('<fmt:message key="page.dish.weight.wrong"/>');
            return;
        } else if ((calories.value < 0.001) || (calories.value > 1000)) {
            reg.preventDefault();
            alert('<fmt:message key="page.dish.calories.wrong"/>');
            return;
        } else if ((proteins.value < 0.001) || (proteins.value > 1000)) {
            reg.preventDefault();
            alert('<fmt:message key="page.dish.proteins.wrong"/>');
            return;
        } else if ((fats.value < 0.001) || (fats.value > 1000)) {
            reg.preventDefault();
            alert('<fmt:message key="page.dish.fats.wrong"/>');
            return;
        } else if ((carbohydrates.value < 0.001) || (carbohydrates.value > 1000)) {
            reg.preventDefault();
            alert('<fmt:message key="page.dish.carbohydrates.wrong"/>');
            return;
        }
    });
</script>
<%--Regex for add new dish--%>
<%--Sent number of page--%>
<script>
    function clickPage(currentPage) {
        window.location.href = '${pageContext.request.contextPath}/swft/listDishPage?' + currentPage.id;
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
            window.location.href = '${pageContext.request.contextPath}/swft/deleteUsersMenuItem?arrDish=' + arrDish['toDelete[]'];
        }
    });
</script>
<%--Delete users dishes--%>