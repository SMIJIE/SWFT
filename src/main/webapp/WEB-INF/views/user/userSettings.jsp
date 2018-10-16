<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<script type="text/javascript">
    function showErrorWithData() {
        <%--Shows error with data--%>
        var mess = '${userError}';
        if (mess === "") {
            $('#userErrorData').css({display: 'none'});
        }
        <%--Shows error with data--%>

        <%--Selected users lifestyle coefficient--%>
        $('#lifestyle').val(${empty formUser.lifeStyleCoefficient ?
        user.lifeStyleCoefficient/1000 : formUser.lifeStyleCoefficient}).change();
        <%--Selected users lifestyle coefficient--%>
    }
</script>

<div id="content">
    <div class="card text-light bg-danger" id="userErrorData">
        <div class="card-header">
            <fmt:message key="${userError}"/>
        </div>
    </div>

    <form:form method="POST" action="userUpdateParameters" id="updateUser" modelAttribute="formUser">
        <div class="form-group row">
            <div class="col">
                <spring:bind path="name">
                    <form:label path="name">
                        <spring:message code="register.name"/>:&nbsp;
                        <span style="color:#D2691E; visibility: hidden" id="nameError">
                            <spring:message code="wrong.user.name"/>
                        </span>
                    </form:label>
                    <form:input path="name" type="text" class="form-control" id="name"
                                value="${empty formUser.name ? user.name : formUser.name}"
                                onkeyup="checkInputs('name')"/>
                    <p style="color:red" id="nameErrorAfterPost">
                        <spring:message code="${status.errorMessage}"/>
                    </p>
                </spring:bind>
            </div>

            <div class="col">
                <spring:bind path="dob">
                    <form:label path="dob">
                        <spring:message code="register.dob"/>:&nbsp;
                        <span style="color:#D2691E; visibility: hidden" id="dobError">
                            <spring:message code="valid.dob.age.between"/>
                        </span>
                    </form:label>
                    <form:input path="dob" type="date" class="form-control" id="dob"
                                value="${empty formUser.dob ? user.dob : formUser.dob}"
                                onkeyup="checkInputs('dob')"/>
                    <p style="color:red" id="dobErrorAfterPost">
                        <spring:message code="${status.errorMessage}"/>
                    </p>
                </spring:bind>
            </div>
        </div>

        <div class="form-group row">
            <div class="col">
                <spring:bind path="email">
                    <form:label path="email">
                        <spring:message code="register.email"/>:&nbsp;
                        <span style="color:#D2691E; visibility: hidden" id="emailError">
                            <spring:message code="wrong.user.email"/>
                        </span>
                    </form:label>
                    <form:input path="email" type="email" class="form-control" id="email"
                                value="${empty formUser.email ? user.email : formUser.email}"
                                onkeyup="checkInputs('email')"/>
                    <p style="color:red" id="emailErrorAfterPost">
                        <spring:message code="${status.errorMessage}"/>
                    </p>
                </spring:bind>
            </div>

            <div class="col">
                <spring:bind path="password">
                    <form:label path="password">
                        <spring:message code="register.password"/>:&nbsp;
                        <span style="color:#D2691E; visibility: hidden" id="passwordError">
                              <spring:message code="valid.password.size"/>
                        </span>
                    </form:label>
                    <spring:message code="register.newPassword" var="newPassword"/>
                    <form:input path="password" type="password" class="form-control" id="password"
                                placeholder="${newPassword}"
                                value="${formUser.password}" onkeyup="checkInputs('password')"/>

                    <form:label path="passwordConfirm">
                        <span style="color:#D2691E; visibility: hidden" id="passwordConfirmError">
                              <spring:message code="valid.password.size"/>
                        </span>
                    </form:label>
                    <spring:message code="register.confirmPassword" var="newPasswordConfirm"/>
                    <form:input path="passwordConfirm" type="password" class="form-control" id="passwordConfirm"
                                placeholder="${newPasswordConfirm}"
                                value="${formUser.passwordConfirm}" onkeyup="checkInputs('passwordConfirm')"/>
                    <p style="color:red" id="passwordErrorAfterPost">
                        <span id="passwordConfirmErrorAfterPost">
                            <spring:message code="${status.errorMessage}"/>
                        </span>
                    </p>
                </spring:bind>
            </div>
        </div>

        <div class="form-group row">
            <div class="col">
                <spring:bind path="lifeStyleCoefficient">
                    <form:label path="lifeStyleCoefficient">
                        <spring:message code="register.lifestyle"/>:&nbsp;&nbsp;&nbsp;
                        <span style="color:#D2691E; visibility: hidden" id="lifeStyleCoefficientError">
                            <spring:message code="valid.lifeStyleCoefficient"/>
                        </span>
                    </form:label>
                    <form:select path="lifeStyleCoefficient" class="form-control" id="lifestyle">
                        <form:option value="1.2">
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
                        <spring:message code="register.cm"/>&nbsp;:&nbsp;
                        <span style="color:#D2691E; visibility: hidden" id="heightError">
                              <spring:message code="valid.height.size"/>
                        </span>
                    </form:label>
                    <form:input path="height" type="number" class="form-control" id="height" step="0.1"
                                value="${empty formUser.height ? user.height/100 : formUser.height}"
                                onkeyup="checkInputs('height')"/>
                    <p style="color:red" id="heightErrorAfterPost">
                        <spring:message code="${status.errorMessage}"/>
                    </p>
                </spring:bind>
            </div>

            <div class="col">
                <spring:bind path="weight">
                    <form:label path="weight">
                        <spring:message code="calories.currentWeight"/>,&nbsp;
                        <spring:message code="register.kg"/>&nbsp;:&nbsp;
                        <span style="color:#D2691E; visibility: hidden" id="weightError">
                              <spring:message code="valid.weight.size"/>
                        </span>
                    </form:label>
                    <form:input path="weight" type="number" class="form-control" id="weight" step="0.1"
                                value="${empty formUser.weight ? user.weight/1000 : formUser.weight}"
                                onkeyup="checkInputs('weight')"/>
                    <p style="color:red" id="weightErrorAfterPost">
                        <spring:message code="${status.errorMessage}"/>
                    </p>
                </spring:bind>
            </div>

            <div class="col">
                <spring:bind path="weightDesired">
                    <form:label path="weightDesired">
                        <spring:message code="calories.desireWeight"/>,&nbsp;
                        <spring:message code="register.kg"/>&nbsp;:&nbsp;
                        <span style="color:#D2691E; visibility: hidden" id="weightDesiredError">
                            <spring:message code="valid.weight.size"/>
                        </span>
                    </form:label>
                    <form:input path="weightDesired" type="number" class="form-control"
                                id="weightDesired" step="0.1"
                                value="${empty formUser.weightDesired ? user.weightDesired/1000 : formUser.weightDesired}"
                                onkeyup="checkInputs('weightDesired')"/>
                    <p style="color:red" id="weightDesiredErrorAfterPost">
                        <spring:message code="${status.errorMessage}"/>
                    </p>
                </spring:bind>
            </div>
        </div>

        <button type="submit" class="btn btn-success">
            <spring:message code="page.update"/>
        </button>
    </form:form>
</div>

<%--Shows error with data and selected users lifestyle coefficient--%>
<script type="text/javascript">
    showErrorWithData()
</script>
<%--Shows error with data and selected users lifestyle coefficient--%>
<%--Regex for user parameters check dynamically--%>
<script type="text/javascript">
    function checkInputs(inputAttribute) {
        var checkAttribute = $('#' + inputAttribute).val();
        $('#' + inputAttribute + 'Error').css({visibility: 'visible'});
        $('#' + inputAttribute + 'ErrorAfterPost').css({visibility: 'hidden'});

        var REGEX_USER_NAME_US = new RegExp(/^[A-Z][a-z]+$/g);
        var REGEX_USER_NAME_US2 = new RegExp(/^[A-Z][a-z]+-[A-Z][a-z]+$/g);
        var REGEX_USER_NAME_UA = new RegExp(/^[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+$/g);
        var REGEX_USER_NAME_UA2 = new RegExp(/^[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+-[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+$/g);

        var REGEX_USER_EMAIL = new RegExp(/^\w{2,}@[a-z]{3,}\.[a-z]{2,}$/g);
        var REGEX_USER_EMAIL2 = new RegExp(/^\w{2,}@[a-z]{3,}\.[a-z]{3,}\.[a-z]{2,}$/g);
        var REGEX_USER_EMAIL3 = new RegExp(/^\w{2,}@.{3,}\.[a-z]{3,}\.[a-z]{2,}$/g);

        if (inputAttribute === "name") {
            if (REGEX_USER_NAME_US.test(checkAttribute) ||
                REGEX_USER_NAME_US2.test(checkAttribute) ||
                REGEX_USER_NAME_UA.test(checkAttribute) ||
                REGEX_USER_NAME_UA2.test(checkAttribute)) {
                $('#' + inputAttribute + 'Error').css({visibility: 'hidden'});
                return;
            }
        } else if (inputAttribute === "dob") {
            var currentDate = new Date();
            var dob = new Date();
            dob.setTime(Date.parse(checkAttribute));
            dob = isNaN(dob) ? currentDate : dob;
            var years = currentDate.getFullYear() - dob.getFullYear();

            if ((years >= 15) && (years <= 99)) {
                $('#' + inputAttribute + 'Error').css({visibility: 'hidden'});
                return;
            }
        } else if (inputAttribute === "email") {
            if (REGEX_USER_EMAIL.test(checkAttribute ||
                REGEX_USER_EMAIL2.test(checkAttribute) ||
                REGEX_USER_EMAIL3.test(checkAttribute))) {
                $('#' + inputAttribute + 'Error').css({visibility: 'hidden'});
                return;
            }
        } else if (inputAttribute === "password" || inputAttribute === "passwordConfirm") {
            if (checkAttribute.length >= 3) {
                $('#' + inputAttribute + 'Error').css({visibility: 'hidden'});
                return;
            }
        } else if (inputAttribute === "height") {
            if ((checkAttribute >= 50) && (checkAttribute <= 250)) {
                $('#' + inputAttribute + 'Error').css({visibility: 'hidden'});
                return;
            }
        } else if (inputAttribute === "weight" || inputAttribute === "weightDesired") {
            if ((checkAttribute >= 50) && (checkAttribute <= 150)) {
                $('#' + inputAttribute + 'Error').css({visibility: 'hidden'});
                return;
            }
        }
    }
</script>
<%--Regex for user parameters check dynamically--%>