<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<div id="content">

    <div id="accordion">
        <div class="card bg-transparent">
            <div class="card-header">
                <a class="card-link text-success" data-toggle="collapse" href="#collapseOne">
                    <fmt:message key="menu.users.list"/>
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
                                <fmt:message key="register.email"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="register.newPassword"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="user.role"/>
                            </th>
                            <th scope="col">
                                <button type="button" id="delete_users" class="btn btn-danger navbar-btn">
                                    <fmt:message key="page.delete"/>
                                </button>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <form action="${pageContext.request.contextPath}/swft/searchUsersByEmail" method="POST">
                                <td colspan="5">
                                    <input type="email" class="form-control" name="email"
                                           placeholder="ZakusyloP@gmail.com">
                                </td>
                                <td>
                                    <button type="submit" class="btn btn-primary">
                                        <fmt:message key="page.search"/>
                                    </button>
                                </td>
                            </form>
                        </tr>

                        <c:set var="count" value="${((numPage-1)*6)}" scope="page"/>
                        <c:forEach items="${listUsers}" var="user">
                            <c:set var="count" value="${count+ 1}" scope="page"/>
                            <tr>
                                <th scope="row">
                                        ${count}
                                </th>
                                <td>
                                    <input type="checkbox" name="toDelete[]" value="${user.email}"
                                           id="checkbox_${user.id}"/>
                                </td>
                                <td>
                                        ${user.email}
                                </td>
                                <form action="${pageContext.request.contextPath}/swft/updateUsers?emailUsers=${user.email}"
                                      method="POST">
                                    <td>
                                        <input type="password" class="form-control" id="password" name="password"
                                               placeholder="<fmt:message key="register.newPassword"/>">
                                    </td>
                                    <td>
                                        <select class="form-control" id="role" name="role">
                                            <option
                                                    <c:if test="${user.role eq 'ADMIN'}">
                                                        selected
                                                    </c:if>
                                                    value="ADMIN">
                                                <fmt:message key="user.role.admin"/>
                                            </option>
                                            <option
                                                    <c:if test="${user.role eq 'USER'}">
                                                        selected
                                                    </c:if>
                                                    value="USER">
                                                <fmt:message key="user.role.user"/>
                                            </option>
                                        </select>
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
            window.location.href = '${pageContext.request.contextPath}/swft/deleteUsers?arrEmailUsers=' + arrDish['toDelete[]'];
        }
    });
</script>
<%--Delete users dishes--%>

