<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>

    .modal-content {
        margin-top: 0px;
    }

    .marker-touched{
        background-color: #6dfea9;
        border: 0.1px solid #cabdbd;
        text-align: center;
    }


    .tabla-ubicaciones{
        position: absolute;
        margin-top: -150px;
        background-color: white;
        opacity: 0.8;
        min-width: 98%;
        width: 98%;
        height: 62%;
        overflow: scroll;
        border-radius: 3px;
        margin-left: 1vh;
    }

    .hidden{
        display: none;
    }


    .fancybox-button--fb {
        padding: 10px;
        margin-right: 5px;
    }

    .fancybox-button--fb svg path{
        stroke-width: 0;
    }

    .dropdown-toggle::after{
        display: none !important;
    }
</style>

<div class="">

    <div class="map-container">
        <%--<input id="pac-input"/>--%>
        <input id="searchInput" class="form-control col-sm-6" type="text" style="margin-top: 10px" placeholder="Ingrese una lugar ..">

        <div id="map"></div>

        <div class="col load mt-5" style="display: none; position:absolute; top: 123px;">
            <div class="col-md-12">
                <div class="loader">
                    <div class="loader-inner box1"></div>
                    <div class="loader-inner box2"></div>
                    <div class="loader-inner box3"></div>
                </div>
            </div>
            <div class="col-md-12"><h5 id="info-loader" style="text-align: center"></h5></div>
        </div>

    </div>


    <div class="tabla-ubicaciones">
        <img id="arrowUp" src="/resources/assets/img/icons/arrowUp.png" style="width:3%; z-index: 500;">
        <img id="arrowDown" src="/resources/assets/img/icons/arrowDown.png" style="width:4%; display: none">
        <table class="table" id="table-markers">
            <thead>
                <th>opciones</th>
                <th>Resaltar pin</th>
                <th>empresa</th>
                <th>direccion</th>
                <th>localidad</th>
                <th>provincia</th>
                <th>description</th>
            </thead>
            <tbody>
                <c:forEach items="${registros}" var="bo">
                <input type="hidden" id="${bo.id}-idEmpresa" value="${bo.idEmpresa}"/>
                <tr>
                    <td>
                        <a class="nav-link dropdown-toggle cursorPointer" id="dropdownMenuOffset" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-bars" id="icon-${bo.id}"></i>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuOffset">
                            <div class="dropdown-item cursorPointer" id="icon-view-marker-${bo.id}" onclick="displayMarkers(this, '${bo.id}')">
                                <i class="far fa-eye-slash"></i><a>&nbsp;&nbsp;Ocultar pin</a>
                            </div>
                            <div class="dropdown-divider"></div>
                            <div class="dropdown-item cursorPointer" onclick="centerFromMarker('${bo.id}')">
                                <i class="fas fa-location-arrow"></i><a>&nbsp;&nbsp;Ir</a>
                            </div>
                            <div class="dropdown-divider"></div>
                            <div class="dropdown-item cursorPointer" onclick="createCarousel('${bo.id}', '${bo.idEmpresa}' , '${bo.description}')">
                                <i class="fas fa-camera"></i><a>&nbsp;&nbsp;Fotos</a>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="cursorPointer" style="text-align: center;" id="marker-touch-${bo.id}" onclick="toggleBounce('${bo.id}')">
                            <i class="nc-icon nc-tap-01 pr-0" style="font-size: 24px"></i>
                        </div>
                    </td>
                    <td data-ubicacion-id class="d-none"         >${bo.id}</td>
                    <td data-empresa                             >${bo.name}</td>
                    <td data-direccion      id="${bo.id}-address">${bo.address}</td>
                    <td data-localidad                           >${bo.localidad}</td>
                    <td data-provincia                           >${bo.provincia}</td>
                    <td data-elemento                            >${bo.description}</td>
                    <td data-lat class="d-none" id="${bo.id}-lat">${bo.latitud}</td>
                    <td data-lon class="d-none" id="${bo.id}-lon">${bo.longitud}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <div style="text-align: center">
            <button type="submit" form="form-back-to-table" formaction="/planificacion/search" class="btn btn-secondary btn-fill"><i class="fas fa-angle-double-left pr-2"></i> Volver</button>
        </div>
    </div>

</div>

<%-- Hidden form to save search filter values, Search the button with the form attribute equals to "form-back-to-table"--%>
<form id="form-back-to-table" action="/planificacion/search" method="post">
    <jsp:include page="../hiddenForms/mapUbicacionRequestHiddenForm.jsp"/>
</form>


<!-- Modal -->
<div class="modal fade" id="modal-info-files" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content" style="margin-top: 45%;">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Gestor de imagenes</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="data-ubicacion"></div>
                <button onclick="showImages('fotos_map')" class="btn btn-sm btn-fill btn-secondary"><i class="far fa-images"></i> Ver Imagenes</button>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>

<table class="table table-bordered table-data-ubicaciones hidden">
    <thead>
    <tr>
        <th>Empresa</th>
        <th>Direcci&oacute;n</th>
        <th>Elemento</th>
        <th>Formato</th>
    </tr>
    </thead>
</table>


<div class="modal" id="myModal2">
    <div class="modal-dialog  modal-md">
        <div class="modal-content" style="margin-top: 15%; max-height: 55%">
            <div id="loader-block" class="loader-block"></div>
            <div id="modal-loader" class="col load mt-5" style="display: none; position:absolute; z-index: 2000;">
                <div class="col-md-12">
                    <div class="loader">
                        <div class="loader-inner box1"></div>
                        <div class="loader-inner box2"></div>
                        <div class="loader-inner box3"></div>
                    </div>
                </div>
            </div>
            <div style="text-align: center;">
                <button type="button" class="close" data-dismiss="modal" onclick="clearResponseLabel()" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3>Selecci&oacute;n de imagenes</h3>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value='/resources/assets/js/filemanager.js'/>"></script>
<script>
    var element = document.getElementById("geoplanning");
    element.classList.add("active");
</script>