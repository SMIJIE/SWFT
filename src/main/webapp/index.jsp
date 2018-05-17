<%@ include file="WEB-INF/components/allImports.jsp" %>

<jsp:include page="WEB-INF/components/header.jsp"/>
<jsp:include page="WEB-INF/components/sidePanel.jsp"/>
<jsp:include page="WEB-INF/components/footer.jsp"/>

<div id="content">
    <script type="text/javascript">
        google.charts.load('current', {'packages': ['corechart']});
        google.charts.setOnLoadCallback(drawVisualization);

        function drawVisualization() {

            var data = google.visualization.arrayToDataTable([
                ['Month', '<fmt:message key="calories.currentWeight"/>', '<fmt:message key="calories.desireWeight"/>', '<fmt:message key="calories.daily"/>'],
                ['1', 2000, 938, 2137],
                ['2', 2000, 938, 2137],
                ['3', 2145, 1167, 2137],
                ['4', 2056, 1110, 2237],
                ['5', 2520, 1120, 2137],
                ['6', 2520, 1120, 2137],
                ['7', 2145, 1167, 2137],
                ['8', 2000, 938, 2137],
                ['9', 2056, 1110, 2237],
                ['10', 2000, 938, 2137],
                ['11', 2056, 1110, 2237],
                ['12', 2520, 1120, 2137],
                ['13', 2520, 1120, 2137],
                ['14', 2520, 1120, 2137],
                ['15', 0, 0, 0],
                ['16', 0, 0, 0],
                ['17', 0, 0, 0],
                ['18', 0, 0, 0],
                ['19', 0, 0, 0],
                ['20', 0, 0, 0],
                ['21', 0, 0, 0],
                ['22', 0, 0, 0],
                ['23', 0, 0, 0],
                ['24', 0, 0, 0],
                ['25', 0, 0, 0],
                ['26', 0, 0, 0],
                ['27', 0, 0, 0],
                ['28', 0, 0, 0]
            ]);

            var options = {
                title: '<fmt:message key="page.charts.caloriesPerMonth"/>',
                seriesType: 'line',
                series: {2: {type: 'bars'}},
                colors: ['#007bff', '#28a745', '#ffc107'],
                backgroundColor: "transparent"
            };

            var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }
    </script>
    <div id="chart_div" style="width: 900px; height: 500px;"></div>
</div>


