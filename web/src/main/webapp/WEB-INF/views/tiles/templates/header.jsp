<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <meta charset="utf-8" />
    <%-- <link rel="apple-touch-icon" sizes="76x76" href="<c:url value='/resources/assets/img/apple-icon.png'/>"> --%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Manager</title>
    <%-- <link rel="shortcut icon" type="image/png" href="/resources/assets/img/icons/faviconScopesi.png"/> --%>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
    <!--     Fonts and icons     -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200" rel="stylesheet" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />
    <script src="https://kit.fontawesome.com/d2e2e9031b.js" crossorigin="anonymous"></script> <!-- v5.0 -->
    <!-- CSS Files -->
    <link href="<c:url value='/resources/assets/css/bootstrap.min.css'/>" rel="stylesheet" /> <!-- v4.0.0-beta -->
    <link href="<c:url value='/resources/assets/css/light-bootstrap-dashboard.css?v=2.0.0'/>" rel="stylesheet" />
    <!-- CSS Just for demo purpose, don't include it in your project -->
    <link href="<c:url value='/resources/assets/css/main.css'/>" rel="stylesheet" />
    <link href="<c:url value='/resources/assets/css/loading.css'/>" rel="stylesheet" />
    <link rel="stylesheet" href="<c:url value='/resources/DataTables/DataTables-1.10.20/css/jquery.dataTables.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/DataTables/FixedColumns-3.3.0/css/fixedColumns.dataTables.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/DataTables/Buttons-1.6.1/css/buttons.dataTables.min.css'/>">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/css/bootstrap-select.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.5.7/jquery.fancybox.css">

    <!--   Core JS Files   -->
    <script src="<c:url value='/resources/assets/js/core/jquery.3.2.1.min.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/resources/assets/js/core/popper.min.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/resources/assets/js/core/bootstrap.min.js'/>" type="text/javascript"></script>
    <!--  Plugin for Switches, full documentation here: http://www.jque.re/plugins/version3/bootstrap.switch/ -->
    <script src="<c:url value='/resources/assets/js/plugins/bootstrap-switch.js'/>"></script>
    <!--  Google Maps Plugin    -->
    <spring:eval expression="@environment.getProperty('spring.google.map.key')" var="apiKey" />
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=${apiKey}&libraries=drawing,places"></script>

    <!--  Chartist Plugin  -->
    <script src="<c:url value='/resources/assets/js/plugins/chartist.min.js'/>"></script>
    <!--  Notifications Plugin    -->
    <script src="<c:url value='/resources/assets/js/plugins/bootstrap-notify.js'/>"></script>
    <!-- Control Center for Light Bootstrap Dashboard: scripts for the example pages etc -->
    <script src="<c:url value='/resources/DataTables/DataTables-1.10.20/js/jquery.dataTables.min.js'/>"></script>
    <script src="<c:url value='/resources/assets/js/customDataTable.js'/>"></script>
    <script src="<c:url value='/resources/assets/js/light-bootstrap-dashboard.js?v=2.0.0'/>" type="text/javascript"></script>
    <script src="<c:url value='/resources/assets/js/demo.js'/>"></script>
    <script src="<c:url value='/resources/assets/js/paginador.js'/>"></script>
    <script src="<c:url value='/resources/assets/js/scripts.js'/>"></script>
    <script src="<c:url value='/resources/DataTables/JSZip-2.5.0/jszip.min.js'/>"></script>
    <script src="<c:url value='/resources/DataTables/pdfmake-0.1.36/pdfmake.min.js'/>"></script>
    <script src="<c:url value='/resources/DataTables/pdfmake-0.1.36/vfs_fonts.js'/>"></script>
    <script src="<c:url value='/resources/DataTables/Buttons-1.6.1/js/dataTables.buttons.min.js'/>"></script>
    <script src="<c:url value='/resources/DataTables/Buttons-1.6.1/js/buttons.html5.min.js'/>"></script>
    <script src="<c:url value='/resources/DataTables/Buttons-1.6.1/js/buttons.colVis.min.js'/>"></script>
    <script src="<c:url value='/resources/DataTables/Buttons-1.6.1/js/buttons.print.min.js'/>"></script>
    <script src="<c:url value='/resources/DataTables/FixedColumns-3.3.0/js/dataTables.fixedColumns.min.js'/>"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>

    <!-- Add fancyBox -->
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.5.7/jquery.fancybox.js"></script>

    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css" />
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            // Javascript method's body can be found in assets/js/demos.js
            demo.initGoogleMaps();

        });

        $(function () {
            $(".datepicker").datepicker({
                dateFormat: 'dd-mm-yy'
            });
        });

        $(function () {
            $("#reservacionFechaDesdeCreate").datepicker({
                dateFormat: 'dd-mm-yy'
            });
        });

        $(function () {
            $("#reservacionFechaHastaCreate").datepicker({
                dateFormat: 'dd-mm-yy',
                endDate: '+1d'
            });
        });

        // //event to display the map search box in full screen
        // document.onfullscreenchange = function ( event ) {
        //     var target = event.target;
        //     var pacContainerElements = document.getElementsByClassName("pac-container");
        //     if (pacContainerElements.length > 0) {
        //         var pacContainer = document.getElementsByClassName("pac-container")[0];
        //         if (pacContainer.parentElement === target) {
        //             console.log("Exiting FULL SCREEN - moving pacContainer to body");
        //             document.getElementsByTagName("body")[0].appendChild(pacContainer);
        //         } else {
        //             console.log("Entering FULL SCREEN - moving pacContainer to target element");
        //             target.appendChild(pacContainer);
        //         }
        //     } else {
        //         console.log("FULL SCREEN change - no pacContainer found");
        //
        //     }};
    </script>

    <style>

        th, td { white-space: nowrap; }
        div.dataTables_wrapper {
            width: 98%;
            margin: 0 auto;
        }
        div.ColVis {
            float: left;
        }
        .modal-content {
            margin-top: -20%;
        }
        .pac-container {
            z-index: 1050 !important;
        }

    </style>
</head>