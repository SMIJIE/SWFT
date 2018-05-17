<%@ include file="../components/allImports.jsp" %>

<jsp:include page="../components/header.jsp"/>
<jsp:include page="../components/sidePanel.jsp"/>
<jsp:include page="../components/footer.jsp"/>

<div id="content">
    <script type="text/javascript">
        google.charts.load('current', {'packages': ['corechart']});
        google.charts.setOnLoadCallback(drawVisualization);

        function drawVisualization() {
            var data = google.visualization.arrayToDataTable([
                ['Month', '<fmt:message key="calories.currentWeight"/>', '<fmt:message key="calories.desireWeight"/>', '<fmt:message key="calories.daily"/>'],
                <c:if test="${not empty monthlyDR}">
                <c:forEach items="${monthlyDR}" var="monthlyDR">
                ['${monthlyDR.key.date.getDayOfMonth()}', ${(monthlyDR.key.userCalories)/1000},
                    ${(monthlyDR.key.userCaloriesDesired)/1000}, ${(monthlyDR.value)/1000}],
                </c:forEach>
                </c:if>

                <c:if test="${empty monthlyDR}">
                ['1', 0, 0, 0],
                </c:if>
            ]);

            var options = {
                title: '<fmt:message key="page.charts.caloriesPerMonth"/>: <fmt:message key="month.${numMonth}"/>',
                seriesType: 'line',
                series: {2: {type: 'bars'}},
                colors: ['#007bff', '#28a745', '#ffc107'],
                backgroundColor: "transparent"
            };

            var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }
    </script>
    <nav aria-label="Page navigation example" class="">
        <ul class="pagination">
            &nbsp;&nbsp;

            <li class="page-item">
                <a class="page-link bg-warning text-danger" id="numPage=${numPage-1}"
                   href="#" aria-label="Previous" onclick="clickPage(this)">
                    <span aria-hidden="true">&laquo;</span>
                    <span class="sr-only">Previous</span>
                </a>
            </li>

            <div id="chart_div" style="width: 1200px; height: 500px;"></div>

            <li class="page-item">
                <a class="page-link bg-warning text-danger" id="numPage=${numPage+1}"
                   href="#" aria-label="Next" onclick="clickPage(this)">
                    <span aria-hidden="true">&raquo;</span>
                    <span class="sr-only">Next</span>
                </a>
            </li>
        </ul>
    </nav>

</div>

<%--Sent number of page--%>
<script>
    function clickPage(currentPage) {
        window.location.href = '${pageContext.request.contextPath}/swft/listHomePage?' + currentPage.id;
    }
</script>
<%--Sent number of page--%>