<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<script type="text/javascript">
    <%--Shows error with data--%>

    function showErrorWithData() {
        var mess = '${userErrorData}';
        if (mess === 'nonErrorData') {
            $('#userErrorData').css({display: 'none'});
        }
        <%--Shows error with data--%>

        <%--Selected users lifestyle coefficient--%>
        $('#lifestyle').val(${user.lifeStyleCoefficient/1000}).change();
        <%--Selected users lifestyle coefficient--%>
    }
</script>

<div id="content">
    <div class="card text-light bg-danger" id="userErrorData">
        <div class="card-header">
            <fmt:message key="${userErrorData}"/>
        </div>
    </div>

    <form method="POST" action="${pageContext.request.contextPath}/swft/updateUserParameters" id="updateUser">
        <div class="form-group row">
            <div class="col">
                <label for="name">
                    <fmt:message key="page.register.name"/>:
                </label>
                <input type="text" class="form-control" id="name" name="name" value="${user.name}">
            </div>
            <div class="col">
                <label for="dob">
                    <fmt:message key="page.register.dob"/>:
                </label>
                <input type="date" class="form-control" id="dob" name="dob" value="${user.dob}">
            </div>
        </div>

        <div class="form-group row">
            <div class="col">
                <label for="email">
                    <fmt:message key="page.register.email"/>:
                </label>
                <input type="email" class="form-control" id="email" name="email" value="${user.email}">
            </div>
            <div class="col">
                <label for="password">
                    <fmt:message key="page.register.password"/>:
                </label>
                <input type="password" class="form-control" id="password" name="password"
                       placeholder="<fmt:message key="page.register.newPassword"/>">
                <br/>
                <input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm"
                       placeholder="<fmt:message key="page.register.confirmPassword"/>">
            </div>
        </div>

        <div class="form-group row">
            <div class="col">
                <label for="lifestyle">
                    <fmt:message key="page.register.lifestyle"/>:
                </label>
                <select class="form-control" id="lifestyle" name="lifestyle">
                    <option value="1.2">
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
                    <fmt:message key="page.register.height"/>,&nbsp;<fmt:message key="page.register.cm"/>&nbsp;:
                </label>
                <input type="number" class="form-control" id="height" name="height" step="0.1"
                       value="${user.height/100}">
            </div>
            <div class="col">
                <label for="weight">
                    <fmt:message key="calories.currentWeight"/>,&nbsp;<fmt:message key="page.register.kg"/>&nbsp;:
                </label>
                <input type="number" class="form-control" id="weight" name="weight" step="0.1"
                       value="${user.weight/1000}">
            </div>
            <div class="col">
                <label for="weightDesired">
                    <fmt:message key="calories.desireWeight"/>,&nbsp;<fmt:message key="page.register.kg"/>&nbsp;:
                </label>
                <input type="number" class="form-control" id="weightDesired" name="weightDesired"
                       step="0.1" value="${user.weightDesired/1000}">
            </div>
        </div>

        <button type="submit" class="btn btn-success">
            <fmt:message key="page.update"/>
        </button>
    </form>
</div>

<%--Shows error with data and selected users lifestyle coefficient--%>
<script type="text/javascript">
    showErrorWithData()
</script>
<%--Shows error with data and selected users lifestyle coefficient--%>
<%--Regex for Update user parameters--%>
<script>
    document.getElementById('updateUser').addEventListener('submit', function (reg) {
        var name = document.getElementById('name');
        var currentDate = new Date();
        var dob = new Date();
        dob.setTime(Date.parse(document.getElementById('dob').value));
        var email = document.getElementById('email');
        var password = document.getElementById('password');
        var passwordConfirm = document.getElementById('passwordConfirm');
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
        } else if (!(email.value.match(/^\w{2,}@[a-z]{3,}\.[a-z]{2,}$/g) ||
            email.value.match(/^\w{2,}@[a-z]{3,}\.[a-z]{3,}\.[a-z]{2,}$/g) ||
            email.value.match(/^\w{2,}@.{3,}\.[a-z]{3,}\.[a-z]{2,}$/g))) {
            reg.preventDefault();
            alert('<fmt:message key="page.email.wrong"/>');
            return;
        } else if ((password.value.length != 0 && passwordConfirm.value.length != 0) &&
            (password.value.length < 3 ||
                passwordConfirm.value.length < 3 ||
                password.value !== passwordConfirm.value)) {
            reg.preventDefault();
            alert('<fmt:message key="page.password.wrong"/>');
            return;
        } else if ((password.value.length == 0 && passwordConfirm.value.length != 0) ||
            (password.value.length != 0 && passwordConfirm.value.length == 0)) {
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
    })
    ;
</script>
<%--Regex for Update user parameters--%>