<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    .confirm-modal{
        margin-top: 124px;
        z-index: 100000;
    }
</style>

<div>
    <div class="map-container">
        <%--<input id="pac-input"/>--%>
        <input id="searchInput" class="form-control col-sm-6" type="text" style="margin-top: 10px" placeholder="Ingrese un lugar ...">

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

        <table class="table" id="table-markers-appMap">
                <thead>
                    <th>opciones</th>
                    <th>Resaltar pin</th>
                    <th>GeoReferenciar</th>
                    <th>id</th>
                    <th>direccion</th>
                    <th>lat</th>
                    <th>long</th>
                </thead>
                <tbody>
                    <c:forEach items="${registros}" var="bo">
                        <tr>
                            <td>
                                <a class="nav-link dropdown-toggle cursorPointer" id="dropdownMenuOffset" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="fas fa-bars" id="icon-${bo.id}"></i>
                                </a>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuOffset">

                                    <div class="dropdown-item cursorPointer" onclick="modificarCoordenadas('${bo.id}')">
                                        <i class="fas fa-map-marker-alt"></i><a>&nbsp;&nbsp;Ubicar punto</a>
                                    </div>

                                    <div class="dropdown-divider"></div>

                                    <div class="dropdown-item cursorPointer" id="icon-view-marker-${bo.id}" onclick="displayMarkers(this, '${bo.id}')">
                                        <i class="far fa-eye-slash"></i><a>&nbsp;&nbsp;Ocultar pin</a>
                                    </div>

                                    <div class="dropdown-divider"></div>

                                    <div class="dropdown-item cursorPointer" onclick="centerFromMarker('${bo.id}')">
                                        <i class="fas fa-location-arrow"></i><a>&nbsp;&nbsp;Ir</a>
                                    </div>

                                </div>

                            </td>
                            <td>
                                <div class="cursorPointer" style="text-align: center;" id="marker-touch-${bo.id}" onclick="toggleBounce('${bo.id}')">
                                    <i class="nc-icon nc-tap-01 pr-0" style="font-size: 24px"></i>
                                </div>
                            </td>
                            <td>
                                <i class="fas fa-sync dropdown-item cursorPointer" style="text-align: center; font-weight: 900;" id="${bo.id}-update" onclick="actualizarCoordenadas('${bo.id}','${bo.address}')"></i>
                                <button id="${bo.id}-save" onclick="guardarCoordenadas('${bo.id}');" class="btn btn-danger hidden btn-fill">Guardar</button>
                            </td>
                            <td>${bo.id}</td>
                            <td id="${bo.id}-address">${bo.address}</td>
                            <td id="${bo.id}-lat">${bo.latitud}</td>
                            <td id="${bo.id}-lon">${bo.longitud}</td>
                        </tr>

                    </c:forEach>
                </tbody>
        </table>

            <div style="text-align: center">
                <style>
                    a{
                        text-decoration: none;
                        color:white;
                    }
                </style>
                <button type="submit" name="paginate" class="btn btn-secondary btn-fill"><i class="fas fa-angle-double-left pr-2"></i> <a href="/appUbicacionRelevamiento/list" >Volver</a></button>
            </div>

    </div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="mi-modal">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">&iquest;Desea modificar la geolocalizaci&oacute;n?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="modal-btn-si">Si</button>
                <button type="button" class="btn btn-primary" id="modal-btn-no">No</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade confirm-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" id="modal-confirmacion">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" style="margin-top: 0;">&iquest;Desea eliminar esta imagen?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="modal-btn2-si">Si</button>
                <button type="button" class="btn btn-primary" id="modal-btn2-no">No</button>
            </div>
        </div>
    </div>
</div>

<script>
    var element = document.getElementById("auditapp");
    element.classList.add("active");
</script>

