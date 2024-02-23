<script src="https://code.highcharts.com/highcharts.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">

<div class="content">
    <h3>Reportes</h3>
    <hr/>
    <div class="container-fluid mt-2">
        <div class="row">
            <div class="col-md-3">
                <input type="text" id="start-date" placeholder="Desde" class="form-control">
            </div>
            <div class="col-md-3">
                <input type="text" id="end-date" placeholder="Hasta" class="form-control">
            </div>
            <div class="col-md-3">
                <button type="button" onclick="createTable()" class="btn btn-primary">Reporte Por Auditor</button>
            </div>
            <div class="col-md-3">
                <button type="button" onclick="createTableRelevamiento()" class="btn btn-primary">Reporte Por Elemento</button>

            </div>
            <div class="col-md-12">
                <div id="pieChartContainer"  style="display: none"></div>
            </div>
            <div class="col-md-6">
                <div id="pieChartContainerPorElementos"  style="display: none"></div>
            </div>
            <div class="col-md-12">
                <div id="divDataTable" style="display: none">
                    <table id="myDataTable" class="mt-2"  style="width:100%;">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Descripci&oacute;n</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>

    <jsp:include page="../../views/pages/component/loader.jsp"/>

<script>
    let token
    $(document).ready(function () {
        token = '${sessionScope.token}';
    });


    let dataTable = $('#myDataTable').DataTable({
        "language": {
            "url": "/resources/DataTables/dataTable-ES.json"
        },
    });

    let users = []
    function createTable() {
        showLoader();
        let startDate = $('#start-date').val();
        let endDate = $('#end-date').val();

        $.ajax({
            url: '/relevamiento/findAllByDates/' + startDate + '/' + endDate,
            method: 'GET',
            dataType: 'json',
            success: function (response) {
                dataTable.clear();
                $.each(response, function (index, item) {
                    let rowData = [
                        item.usuario.id,
                        item.usuario.username,
                        item.nombreRelevamiento
                    ];
                    users.push({
                        id: item.usuario.id,
                        username: item.usuario.username,
                        nombreRelevamiento: item.nombreRelevamiento
                    })
                    dataTable.row.add(rowData);
                });
                dataTable.draw();
                generatePieChart(dataTable);
            },
            error: function (xhr, status, error) {
                console.log('Error en la petición AJAX:', error);
            }
        });
    }

    function createTableRelevamiento() {
        showLoader();
        let startDate = $('#start-date').val();
        let endDate = $('#end-date').val();

        $.ajax({
            url: '/api/relevamiento/ubicacionRelevamiento/findAllByDates/' + startDate + '/' + endDate,
            method: 'GET',
            dataType: 'json',
            success: function (response) {
                dataTable.clear();
                $.each(response, function (index, item) {
                    let rowData = [
                        item.appRelevamiento.usuario.id,
                        item.appRelevamiento.usuario.username,
                        item.elemento
                    ];
                    users.push({
                        id: item.appRelevamiento.usuario.id,
                        username:  item.appRelevamiento.usuario.username,
                        nombreRelevamiento: item.elemento
                    })
                    dataTable.row.add(rowData);
                });
                dataTable.draw();
                generatePieChartRelevamiento(dataTable);
            },
            error: function (xhr, status, error) {
                console.log('Error en la petición AJAX:', error);
            }
        });
    }


    function generatePieChart(dataTable) {
        let tableData = dataTable.rows().data();
        let userCounts = {};
        tableData.each(function (value, index) {
            var userId = value[0]; // Obtener el ID del usuario
            if (userCounts.hasOwnProperty(userId)) {
                userCounts[userId]++; // Incrementar el contador del usuario existente
            } else {
                userCounts[userId] = 1; // Inicializar el contador del nuevo usuario
            }
        });

        let pieData = [];
        for (let userId in userCounts) {
            if (userCounts.hasOwnProperty(userId)) {
                let username = getUsernameById(userId); // Obtener el nombre de usuario por el ID (debe ser implementada)
                pieData.push({
                    name: username,
                    y: userCounts[userId]
                });
            }
        }

        Highcharts.chart('pieChartContainer', {
            chart: {
                type: 'pie'
            },
            title: {
                text: 'Auditorias por Usuario'
            },
            series: [{
                name: 'Auditorias:',
                colorByPoint: true,
                data: pieData
            }]
        });
        $("#pieChartContainer").show();
        $('#divDataTable').show();
        hideLoader()
    }

    function generatePieChartRelevamiento(dataTable) {
        let tableData = dataTable.rows().data();
        let elementCounts = {};

        tableData.each(function (value, index) {
            var elemento = value[2]; // Obtener el nombre del elemento
            if (elementCounts.hasOwnProperty(elemento)) {
                elementCounts[elemento]++; // Incrementar el contador del elemento existente
            } else {
                elementCounts[elemento] = 1; // Inicializar el contador del nuevo elemento
            }
        });

        let pieData = [];
        for (let elemento in elementCounts) {
            if (elementCounts.hasOwnProperty(elemento)) {
                pieData.push({
                    name: elemento,
                    y: elementCounts[elemento]
                });
            }
        }

        Highcharts.chart('pieChartContainer', {
            chart: {
                type: 'pie'
            },
            title: {
                text: 'Gr&aacute;fico de Torta por Elementos'
            },
            series: [{
                name: 'Cantidad:',
                colorByPoint: true,
                data: pieData
            }]
        });
        $("#pieChartContainer").show();
        $('#divDataTable').show();
        hideLoader()
    }

    function getUsernameById(userId) {
        for (var i = 0; i < users.length; i++) {
            if (users[i].id == userId) {
                return users[i].username;
            }
        }
        return 'Sin Usuario';
    }

</script>

<script>
    $(document).ready(function () {
        $('#start-date').datepicker({
            dateFormat: 'dd-mm-yy',
            autoclose: true,
            todayHighlight: true,
            language: 'es'
        });

        $('#end-date').datepicker({
            dateFormat: 'dd-mm-yy',
            autoclose: true,
            todayHighlight: true,
            language: 'es'
        });
    });


</script>
    <script src="/resources/assets/js/datepicker.es.js" charset="UTF-8"></script>
