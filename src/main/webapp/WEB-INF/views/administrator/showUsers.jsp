<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<%--Shows error for users--%>
<script type="text/javascript">
    function showErrorWithData() {
        var mess = '${userError}';
        if (mess === "") {
            $('#userErrorData').css({display: 'none'});
        }
    }
</script>
<%--Shows error for users--%>

<div id="content">

    <div id="accordion">

        <div class="card text-light bg-danger" id="userErrorData">
            <div class="card-header">
                <spring:message code="${userError}"/>
            </div>
        </div>

        <div class="card bg-transparent">
            <div class="card-header">
                <a class="card-link text-success" data-toggle="collapse" href="#collapseOne">
                    <spring:message code="menu.users.list"/>
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
                                <spring:message code="register.email"/>
                            </th>
                            <th scope="col">
                                <spring:message code="register.newPassword"/>
                            </th>
                            <th scope="col">
                                <spring:message code="user.role"/>
                            </th>
                            <th scope="col">
                                <button type="button" id="delete_users" class="btn btn-danger navbar-btn">
                                    <spring:message code="page.delete"/>
                                </button>
                            </th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <%--Search users by email--%>
                            <form:form method="POST" action="searchUsersByEmail" id="searchUsersByEmail"
                                       modelAttribute="formUser">
                                <td colspan="5">
                                    <form:input path="email" type="email" class="form-control" name="email"
                                                placeholder="ZakusyloP@gmail.com"/>
                                </td>
                                <td>
                                    <button type="submit" class="btn btn-primary">
                                        <spring:message code="page.search"/>
                                    </button>
                                </td>
                            </form:form>
                            <%--Search users by email--%>
                        </tr>

                        <c:set var="count" value="${((numPage-1)*6)}" scope="page"/>
                        <c:forEach items="${listUsers}" var="user">
                            <c:set var="count" value="${count+ 1}" scope="page"/>
                            <tr>
                                <th scope="row">
                                        ${count}
                                </th>
                                <td>
                                    <input type="checkbox" name="toDelete[]" value="${user.email}"/>
                                </td>
                                <td>
                                        ${user.email}
                                </td>
                                <form:form method="POST" id="updateUsersByEmail" modelAttribute="formUser"
                                           action="updateUsersByEmail?email=${user.email}&numPage=${numPage}">
                                    <td>
                                        <spring:message code="register.newPassword" var="newPassword"/>
                                        <form:input path="password" type="password" class="form-control"
                                                    id="password" name="password" placeholder="${newPassword}"/>
                                    </td>
                                    <td>
                                        <form:select path="role" class="form-control" id="role" name="role">
                                            <form:option value="ADMIN"
                                                         selected="${user.role eq 'ADMIN' ? 'selected' : ''}">
                                                <spring:message code="user.role.admin"/>
                                            </form:option>
                                            <form:option value="USER"
                                                         selected="${user.role eq 'USER' ? 'selected' : ''}">
                                                <spring:message code="user.role.user"/>
                                            </form:option>
                                        </form:select>
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
                            <a class="page-link bg-transparent text-success" id="numPage=${numPage-1}"
                               href="#" aria-label="Previous" onclick="clickPage(this)">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>

                        &nbsp;
                        <c:forEach begin="1" end="${0.99+numUsers/6}" var="loop">
                            <c:if test="${loop eq numPage}">
                                <li class="page-item active">
                            </c:if>
                            <c:if test="${loop ne numPage}">
                                <li class="page-item">
                            </c:if>
                            <a class="page-link bg-transparent text-success"
                               id="numPage=${loop}"
                               href="#" onclick="clickPage(this)">
                                    ${loop}
                            </a>
                            </li>
                            &nbsp;
                        </c:forEach>

                        <li class="page-item">
                            <a class="page-link bg-transparent text-success" id="numPage=${numPage+1}"
                               href="#" aria-label="Next" onclick="clickPage(this)">
                                <span aria-hidden="true">&raquo;</span>
                                <span class="sr-only">Next</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>


<%--Shows error for users--%>
<script type="text/javascript">
    showErrorWithData()
</script>
<%--Shows error for users--%>
<%--Sent number of page--%>
<script>
    function clickPage(currentPage) {
        window.location.href = '${pageContext.request.contextPath}/swft/admin/showUsers?' + currentPage.id;
    }
</script>
<%--Sent number of page--%>
<%--Delete users dishes--%>
<script>
    $('#delete_users').click(function () {
        var arrDish = {'toDelete[]': []};
        $(":input:checked").each(function () {
            arrDish['toDelete[]'].push($(this).val());
        });

        if (arrDish['toDelete[]'].length > 0) {
            window.location.href = '${pageContext.request.contextPath}/swft/admin/deleteUsersByEmail?arrEmailUsers=' + arrDish['toDelete[]'] + '&numPage=' +${numPage};
        }
    });
</script>
<%--Delete users dishes--%>

