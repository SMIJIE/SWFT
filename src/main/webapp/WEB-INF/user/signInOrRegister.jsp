<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<%--Shows error with email for users--%>
<script type="text/javascript">
    function showErrorWithEmail() {
        var mess = '${userErrorEmail}';
        if (mess === 'nonEmail') {
            $('#userErrorEmail').css({display: 'none'});
        }
    }
</script>
<%--Shows error with email for users--%>

<div id="content">

    <div id="accordion">

        <div class="card text-light bg-danger" id="userErrorEmail">
            <div class="card-header">
                <fmt:message key="${userErrorEmail}"/>
            </div>
        </div>

        <%--LogIn--%>
        <div class="card">
            <div class="card-header">
                <a class="card-link" data-toggle="collapse" href="#collapseOne">
                    <fmt:message key="page.signIn"/>
                </a>
            </div>

            <div id="collapseOne" class="collapse show" data-parent="#accordion">
                <div class="card-body">
                    <form method="POST" action="${pageContext.request.contextPath}/swft/logIn" id="logIn">
                        <div class="form-group">
                            <label for="emailIn">
                                <fmt:message key="page.register.email"/>:
                            </label>
                            <input type="email" class="form-control" id="emailIn" name="email"
                                   placeholder="ZakusyloP@gmail.com">
                        </div>
                        <div class="form-group">
                            <label for="passwordIn">
                                <fmt:message key="page.register.password"/>:
                            </label>
                            <input type="password" class="form-control" id="passwordIn" name="password">
                        </div>
                        <button type="submit" class="btn btn-primary">
                            <fmt:message key="page.signIn"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <%--LogIn--%>

        <%--Sign up--%>
        <div class="card">
            <div class="card-header">
                <a class="collapsed card-link" data-toggle="collapse" href="#collapseTwo">
                    <fmt:message key="page.signUp"/>
                </a>
            </div>

            <div id="collapseTwo" class="collapse" data-parent="#accordion">
                <div class="card-body">
                    <form method="POST" action="${pageContext.request.contextPath}/swft/registerNewUser"
                          id="registerNewUser">
                        <div class="form-group row">
                            <div class="col">
                                <label for="name">
                                    <fmt:message key="page.register.name"/>:
                                </label>
                                <input type="text" class="form-control" id="name" name="name"
                                       placeholder="<fmt:message key="page.example.name"/>">
                            </div>
                            <div class="col">
                                <label for="dob">
                                    <fmt:message key="page.register.dob"/>:
                                </label>
                                <input type="date" class="form-control" id="dob" name="dob">
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col">
                                <label for="emailReg">
                                    <fmt:message key="page.register.email"/>:
                                </label>
                                <input type="email" class="form-control" id="emailReg" name="email"
                                       placeholder="ZakusyloP@gmail.com">
                            </div>
                            <div class="col">
                                <label for="passwordReg">
                                    <fmt:message key="page.register.password"/>:
                                </label>
                                <input type="password" class="form-control" id="passwordReg" name="password">
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col">
                                <label for="lifestyle">
                                    <fmt:message key="page.register.lifestyle"/>:
                                </label>
                                <select class="form-control" id="lifestyle" name="lifestyle">
                                    <option selected value="1.2">
                                        <fmt:message key="page.register.lifestyleMin"/>
                                    </option>
                                    <option value="1.375">
                                        <fmt:message key="page.register.lifestyleWeak"/>
                                    </option>
                                    <option value="1.55">
                                        <fmt:message key="page.register.lifestyleAvr"/>
                                    </option>
                                    <option value="1.725">
                                        <fmt:message key="page.register.lifestyleHigh"/>
                                    </option>
                                    <option value="1.9">
                                        <fmt:message key="page.register.lifestyleExtra"/>
                                    </option>
                                </select>
                            </div>
                            <div class="col">
                                <label for="height">
                                    <fmt:message key="page.register.height"/>,&nbsp;
                                    <fmt:message key="page.register.cm"/>&nbsp;:
                                </label>
                                <input type="number" class="form-control" id="height" name="height" step="0.1"
                                       placeholder="183,5">
                            </div>
                            <div class="col">
                                <label for="weight">
                                    <fmt:message key="calories.currentWeight"/>,&nbsp;
                                    <fmt:message key="page.register.kg"/>&nbsp;:
                                </label>
                                <input type="number" class="form-control" id="weight" name="weight" step="0.1"
                                       placeholder="85,0">
                            </div>
                            <div class="col">
                                <label for="weightDesired">
                                    <fmt:message key="calories.desireWeight"/>,&nbsp;
                                    <fmt:message key="page.register.kg"/>&nbsp;:
                                </label>
                                <input type="number" class="form-control" id="weightDesired" name="weightDesired"
                                       step="0.1" placeholder="70,5">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-success">
                            <fmt:message key="page.signUp"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <%--Sign up--%>
    </div>
</div>

<%--Shows error with email for users--%>
<script type="text/javascript">
    showErrorWithEmail()
</script>
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
            alert('<fmt:message key="page.email.wrong"/>');
            return;
        } else if (passwordIn.value.length < 3) {
            log.preventDefault();
            alert('<fmt:message key="page.password.wrong"/>');
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
            alert('<fmt:message key="page.name.wrong"/>');
            return;
        } else if (isNaN(dob) ||
            ((currentDate.getFullYear() - dob.getFullYear()) < 15) ||
            ((currentDate.getFullYear() - dob.getFullYear()) > 99)) {
            reg.preventDefault();
            alert('<fmt:message key="page.dob.wrong"/>');
            return;
        } else if (!(emailReg.value.match(/^\w{2,}@[a-z]{3,}\.[a-z]{2,}$/g) ||
            emailReg.value.match(/^\w{2,}@[a-z]{3,}\.[a-z]{3,}\.[a-z]{2,}$/g) ||
            emailReg.value.match(/^\w{2,}@.{3,}\.[a-z]{3,}\.[a-z]{2,}$/g))) {
            reg.preventDefault();
            alert('<fmt:message key="page.email.wrong"/>');
            return;
        } else if (passwordReg.value.length < 3) {
            reg.preventDefault();
            alert('<fmt:message key="page.password.wrong"/>');
            return;
        } else if ((height.value < 50) || (height.value > 250)) {
            reg.preventDefault();
            alert('<fmt:message key="page.height.wrong"/>');
            return;
        } else if ((weight.value < 50) || (weight.value > 150)) {
            reg.preventDefault();
            alert('<fmt:message key="page.weight.wrong"/>');
            return;
        } else if (weightDesired.value != '') {
            if ((weightDesired.value < 50) || (weightDesired.value > 150)) {
                reg.preventDefault();
                alert('<fmt:message key="page.weight.wrong"/>');
            }
        }
    });
    <%--Regex for Sign Up--%>
</script>
<%--Regex for users form--%>

