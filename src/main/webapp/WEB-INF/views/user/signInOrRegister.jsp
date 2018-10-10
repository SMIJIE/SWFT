<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>
<script type="text/javascript">
    <%--Shows error for users--%>

    function showErrorWithEmail() {
        var mess = '${userError}';
        if (mess === "") {
            $('#userErrorEmail').css({display: 'none'});
        }
    }

    <%--Shows error for users--%>

    <%--Dynamic Check every input by regex separatly--%>

    function doAjax(parseAttribute) {
        $.ajax({
            url: 'checkUserParamFromHttpForm',
            data: ({param: parseAttribute + '=' + $('#' + parseAttribute).val()}),
            success: function (data) {
                $('#' + parseAttribute + 'Error').html(data);
            }
        });
    }

    <%--Dynamic Check every input by regex separatly--%>
</script>


<div id="content">

    <div id="accordion">

        <div class="card text-light bg-danger" id="userErrorEmail">
            <div class="card-header">
                <spring:message code="${userError}"/>
            </div>
        </div>

        <%--LogIn--%>
        <div class="card">
            <div class="card-header">
                <a class="card-link" data-toggle="collapse" href="#collapseOne">
                    <spring:message code="page.signIn"/>
                </a>
            </div>

            <div id="collapseOne" class="collapse ${showCollapseSignIn}" data-parent="#accordion">
                <div class="card-body">
                    <form:form method="POST" action="logIn" id="logIn" modelAttribute="formUser">
                        <div class="form-group">
                            <form:label path="email">
                                <spring:message code="register.email"/>:
                            </form:label>
                            <form:input path="email" type="email" class="form-control" id="emailIn"
                                        placeholder="ZakusyloP@gmail.com" value="${valueEmailLogIn}"
                                        onkeyup="doAjax('emailIn')"/>
                            <p style="color:red" id="emailInError"></p>
                        </div>

                        <div class="form-group">
                            <form:label path="password">
                                <spring:message code="register.password"/>:
                            </form:label>
                            <form:input path="password" type="password" class="form-control" id="passwordIn"
                                        placeholder="" value="${valuePasswordLogIn}" onkeyup="doAjax('passwordIn')"/>
                            <p style="color:red" id="passwordInError"></p>
                        </div>

                        <button type="submit" class="btn btn-primary">
                            <spring:message code="page.signIn"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
        <%--LogIn--%>

        <%--Sign up--%>
        <div class="card">
            <div class="card-header">
                <a class="collapsed card-link" data-toggle="collapse" href="#collapseTwo">
                    <spring:message code="page.signUp"/>
                </a>
            </div>

            <div id="collapseTwo" class="collapse ${showCollapseSignUp}" data-parent="#accordion">
                <div class="card-body">
                    <form:form method="POST" action="registerNewUser" id="registerNewUser" modelAttribute="formUser">
                        <div class="form-group row">
                            <div class="col">
                                <spring:bind path="name">
                                    <form:label path="name">
                                        <spring:message code="register.name"/>:
                                    </form:label>
                                    <spring:message code="example.name" var="page.example.name"/>
                                    <form:input path="name" type="text" class="form-control" id="name"
                                                placeholder="${page.example.name}" value="${formUser.name}"
                                                onkeyup="doAjax('name')"/>
                                    <p style="color:red" id="nameError">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>

                            <div class="col">
                                <spring:bind path="dob">
                                    <form:label path="dob">
                                        <spring:message code="register.dob"/>:
                                    </form:label>
                                    <form:input path="dob" type="date" class="form-control" id="dob"
                                                value="${formUser.dob}" onkeyup="doAjax('dob')"/>
                                    <p style="color:red" id="dobError">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col">
                                <spring:bind path="email">
                                    <form:label path="email">
                                        <spring:message code="register.email"/>:
                                    </form:label>
                                    <form:input path="email" type="email" class="form-control" id="emailReg"
                                                placeholder="ZakusyloP@gmail.com" value="${formUser.email}"
                                                onkeyup="doAjax('emailReg')"/>
                                    <p style="color:red" id="emailRegError">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>

                            <div class="col">
                                <spring:bind path="password">
                                    <form:label path="password">
                                        <spring:message code="register.password"/>:
                                    </form:label>
                                    <form:input path="password" type="password" class="form-control" id="passwordReg"
                                                placeholder="" value="${formUser.password}"
                                                onkeyup="doAjax('passwordReg')"/>
                                    <p style="color:red" id="passwordRegError">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col">
                                <spring:bind path="lifeStyleCoefficient">
                                    <form:label path="lifeStyleCoefficient">
                                        <spring:message code="register.lifestyle"/>:
                                    </form:label>
                                    <form:select path="lifeStyleCoefficient" class="form-control" id="lifestyle"
                                                 value="${formUser.lifeStyleCoefficient}">
                                        <form:option selected="true" value="1.2">
                                            <spring:message code="register.lifestyleMin"/>
                                        </form:option>
                                        <form:option value="1.375">
                                            <spring:message code="register.lifestyleWeak"/>
                                        </form:option>
                                        <form:option value="1.55">
                                            <spring:message code="register.lifestyleAvr"/>
                                        </form:option>
                                        <form:option value="1.725">
                                            <spring:message code="register.lifestyleHigh"/>
                                        </form:option>
                                        <form:option value="1.9">
                                            <spring:message code="register.lifestyleExtra"/>
                                        </form:option>
                                    </form:select>
                                    <p style="color:red">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>

                            <div class="col">
                                <spring:bind path="height">
                                    <form:label path="height">
                                        <spring:message code="register.height"/>,&nbsp;
                                        <spring:message code="register.cm"/>&nbsp;:
                                    </form:label>
                                    <form:input path="height" type="number" class="form-control" id="height" step="0.1"
                                                placeholder="183,5" value="${formUser.height}"
                                                onkeyup="doAjax('height')"/>
                                    <p style="color:red" id="heightError">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>

                            <div class="col">
                                <spring:bind path="weight">
                                    <form:label path="weight">
                                        <spring:message code="calories.currentWeight"/>,&nbsp;
                                        <spring:message code="register.kg"/>&nbsp;:
                                    </form:label>
                                    <form:input path="weight" type="number" class="form-control" id="weight" step="0.1"
                                                placeholder="85,0" value="${formUser.weight}"
                                                onkeyup="doAjax('weight')"/>
                                    <p style="color:red" id="weightError">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>

                            <div class="col">
                                <spring:bind path="weightDesired">
                                    <form:label path="weightDesired">
                                        <spring:message code="calories.desireWeight"/>,&nbsp;
                                        <spring:message code="register.kg"/>&nbsp;:
                                    </form:label>
                                    <form:input path="weightDesired" type="number" class="form-control"
                                                id="weightDesired" step="0.1"
                                                placeholder="70,5" value="${formUser.weightDesired}"
                                                onkeyup="doAjax('weightDesired')"/>
                                    <p style="color:red" id="weightDesiredError">
                                        <spring:message code="${status.errorMessage}"/>
                                    </p>
                                </spring:bind>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-success">
                            <spring:message code="page.signUp"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
        <%--Sign up--%>
    </div>
</div>

<%--Shows error for users--%>
<script type="text/javascript">
    showErrorWithEmail()
</script>
<%--Shows error for users--%>