<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<%--Shows error for users--%>
<script type="text/javascript">
    function showErrorWithEmail() {
        var mess = '${userError}';
        if (mess === "") {
            $('#userErrorEmail').css({display: 'none'});
        }
    }
</script>
<%--Shows error for users--%>

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

            <div id="collapseOne" class="collapse show" data-parent="#accordion">
                <div class="card-body">
                    <form:form method="POST" action="logIn" id="logIn" modelAttribute="formUser">
                        <div class="form-group">
                            <form:label path="email">
                                <spring:message code="register.email"/>:
                            </form:label>
                            <form:input path="email" type="email" class="form-control" id="emailIn"
                                        placeholder="ZakusyloP@gmail.com" value="${valueEmailLogIn}"/>
                        </div>
                        <div class="form-group">
                            <form:label path="password">
                                <spring:message code="register.password"/>:
                            </form:label>
                            <form:input path="password" type="password" class="form-control" id="passwordIn"
                                        placeholder="" value="${valuePasswordLogIn}"/>
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

            <div id="collapseTwo" class="collapse" data-parent="#accordion">
                <div class="card-body">
                    <form:form method="POST" action="registerNewUser" id="registerNewUser" modelAttribute="formUser">
                    <div class="form-group row">
                        <div class="col">
                            <form:label path="name">
                                <spring:message code="register.name"/>:
                            </form:label>
                            <spring:message code="example.name" var="page.example.name"/>
                            <form:input path="name" type="text" class="form-control" id="name"
                                        placeholder="${page.example.name}" value="${valueNameReg}"/>
                        </div>
                        <div class="col">
                            <form:label path="dob">
                                <spring:message code="register.dob"/>:
                            </form:label>
                            <form:input path="dob" type="date" class="form-control" id="dob"
                                        value="${valueDobReg}"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col">
                            <form:label path="email">
                                <spring:message code="register.email"/>:
                            </form:label>
                            <form:input path="email" type="email" class="form-control" id="emailReg"
                                        placeholder="ZakusyloP@gmail.com" value="${valueEmailReg}"/>
                        </div>
                        <div class="col">
                            <form:label path="password">
                                <spring:message code="register.password"/>:
                            </form:label>
                            <form:input path="password" type="password" class="form-control" id="passwordReg"
                                        value="${valuePasswordReg}"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col">
                            <form:label path="lifeStyleCoefficient">
                                <spring:message code="register.lifestyle"/>:
                            </form:label>
                            <form:select path="lifeStyleCoefficient" class="form-control" id="lifestyle">
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
                        </div>
                        <div class="col">
                            <form:label path="height">
                                <spring:message code="register.height"/>,&nbsp;
                                <spring:message code="register.cm"/>&nbsp;:
                            </form:label>
                            <form:input path="height" type="number" class="form-control" id="height" step="0.1"
                                        placeholder="183,5" value="${valueHeightReg}"/>
                        </div>
                        <div class="col">
                            <form:label path="weight">
                                <spring:message code="calories.currentWeight"/>,&nbsp;
                                <spring:message code="register.kg"/>&nbsp;:
                            </form:label>
                            <form:input path="weight" type="number" class="form-control" id="weight" step="0.1"
                                        placeholder="85,0" value="${valueWeightReg}"/>
                        </div>
                        <div class="col">
                            <form:label path="weightDesired">
                                <spring:message code="calories.desireWeight"/>,&nbsp;
                                <spring:message code="register.kg"/>&nbsp;:
                            </form:label>
                            <form:input path="weightDesired" type="number" class="form-control" id="weightDesired"
                                        step="0.1" placeholder="70,5" value="${valueWeightDesiredReg}"/>
                        </div>
                            <%--</div>--%>

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

    <%--Regex for users form--%>
    <script>
        <%--Regex for Log In--%>
        document.getElementById('logIn').addEventListener('submit', function (log) {
            var emailIn = document.getElementById('emailIn');
            var passwordIn = document.getElementById('passwordIn');

            if (!(emailIn.value.match(/^\w{2,}@[a-z]{3,}\.[a-z]{2,}$/g) ||
                emailIn.value.match(/^\w{2,}@[a-z]{3,}\.[a-z]{3,}\.[a-z]{2,}$/g) ||
                emailIn.value.match(/^\w{2,}@.{3,}\.[a-z]{3,}\.[a-z]{2,}$/g))) {
                log.preventDefault();
                alert('<spring:message code="wrong.user.email"/>');
                return;
            } else if (passwordIn.value.length < 3) {
                log.preventDefault();
                alert('<spring:message code="wrong.user.password"/>');
            }
        });
        <%--Regex for Log In--%>

        <%--Regex for Sign Up--%>
        document.getElementById('registerNewUser').addEventListener('submit', function (reg) {
            var name = document.getElementById('name');
            var currentDate = new Date();
            var dob = new Date();
            dob.setTime(Date.parse(document.getElementById('dob').value));
            var emailReg = document.getElementById('emailReg');
            var passwordReg = document.getElementById('passwordReg');
            var height = document.getElementById('height');
            var weight = document.getElementById('weight');
            var weightDesired = document.getElementById('weightDesired');

            if (!(name.value.match(/^[A-Z][a-z]+$/g) ||
                name.value.match(/^[A-Z][a-z]+-[A-Z][a-z]+$/g) ||
                name.value.match(/^[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+$/g) ||
                name.value.match(/^[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+-[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490][`´''ʼ’ʼ’]?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+$/g))) {
                reg.preventDefault();
                alert('<spring:message code="wrong.user.name"/>');
                return;
            } else if (isNaN(dob) ||
                ((currentDate.getFullYear() - dob.getFullYear()) < 15) ||
                ((currentDate.getFullYear() - dob.getFullYear()) > 99)) {
                reg.preventDefault();
                alert('<spring:message code="wrong.user.dob"/>');
                return;
            } else if (!(emailReg.value.match(/^\w{2,}@[a-z]{3,}\.[a-z]{2,}$/g) ||
                emailReg.value.match(/^\w{2,}@[a-z]{3,}\.[a-z]{3,}\.[a-z]{2,}$/g) ||
                emailReg.value.match(/^\w{2,}@.{3,}\.[a-z]{3,}\.[a-z]{2,}$/g))) {
                reg.preventDefault();
                alert('<spring:message code="wrong.user.email"/>');
                return;
            } else if (passwordReg.value.length < 3) {
                reg.preventDefault();
                alert('<spring:message code="wrong.user.password"/>');
                return;
            } else if ((height.value < 50) || (height.value > 250)) {
                reg.preventDefault();
                alert('<spring:message code="wrong.user.height"/>');
                return;
            } else if ((weight.value < 50) || (weight.value > 150)) {
                reg.preventDefault();
                alert('<spring:message code="wrong.user.weight"/>');
                return;
            } else if (weightDesired.value != '') {
                if ((weightDesired.value < 50) || (weightDesired.value > 150)) {
                    reg.preventDefault();
                    alert('<spring:message code="wrong.user.weight"/>');
                }
            }
        });
        <%--Regex for Sign Up--%>
    </script>
<%--Regex for users form--%>