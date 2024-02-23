<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<style>
    #mapApp {
        height: 400px;  /* The height is 400 pixels */
        width: 100%;  /* The width is the width of the web page */
    }
    .card form [class*="col-"]:first-child {
        padding-left: 6px;
    }
    .card form [class*="col-"]:last-child {
        padding-right: 6px;
    }
    .hidden{
        display: none;
    }
</style>

<div class="content">
    <div class="col-12">
        <div class="col load mt-5" style="display: none; position:absolute; z-index: 1000; top: 123px;">
            <div class="col-md-12">
                <div class="loader">
                    <div class="loader-inner box1"></div>
                    <div class="loader-inner box2"></div>
                    <div class="loader-inner box3"></div>
                </div>
            </div>
            <div class="col-md-12"><h5 style="text-align: center"></h5></div>
        </div>
        <div class="card">
            <form:form action="saveUpdate" modelAttribute="updateAppUbicacion" method="post">
            <form:hidden path="id" value='${appUbicacionRelevamiento.id}'/>

                <div class="row ml-3">
                <div class="col-md-11">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-6">
                                <label for="oneClaveUbi" class="control-label pt-2 pr-2">ONE ClaveUbi</label>
                                <form:input type="number" path="oneClaveUbi" cssClass="form-control" id="oneClaveUbi" placeholder=""/>
                            </div>
                            <div class="col-6">
                                <label class="control-label pt-2 pr-2 col-4">Direccion</label>
                                <label style="font-size: 11px;" class="pt-2 col-6 float-right text-right">(Formato: direccion, localidad , provincia, pa&iacute;s)</label>
                                <form:input path="direccion" cssClass="form-control" id="newAddress" placeholder="Ej: Av. Independencia 100, Buenos Aires, Argentina"/>
                            </div>
                            <div class="col-6">
                                <label for="localidad" class="control-label pt-2 pr-2 col-4">Barrio</label>
                                <form:input path="barrio" cssClass="form-control" id="localidad" placeholder=""/>
                            </div>
                            <div class="col-6">
                                <label for="localidad" class="control-label pt-2 pr-2 col-4">Localidad</label>
                                <form:input path="localidad" cssClass="form-control" id="localidad" placeholder=""/>
                            </div>
                            <div class="col-6">
                                <label for="provincia" class="control-label pt-2 pr-2 col-4">Provincia</label>
                                <form:input path="provincia" cssClass="form-control" id="provincia" placeholder=""/>
                            </div>
                            <div class="col-6">
                                <label for="localidad" class="control-label pt-2 pr-2 col-4">Zona</label>
                                <form:input path="zona" cssClass="form-control" id="localidad" placeholder=""/>
                            </div>
                            <div class="col-6">
                                <label class="control-label pt-2">Latitud</label>
                                <form:input path="latitud" cssClass="form-control" id="latitud" onchange="setLatApp(event)" placeholder=""/>
                            </div>
                            <div class="col-6">
                                <label class="control-label pt-2">Longitud</label>
                                <form:input path="longitud" cssClass="form-control" id="longitud"  onchange="setLngApp(event)" placeholder=""/>
                            </div>
                            <form:input type="hidden" path="bajaLogica" cssClass="form-control" value="false"/>

                        </form:form>
<%--                            <div id="appRespuestaTable" style="border-radius: 4px;" class="col-12 table-responsive">--%>
<%--                                <table class="table">--%>
<%--                                    <thead>--%>
<%--                                        <th>Relevamiento asignado/s:</th>--%>
<%--                                        <th>Quitar</th>--%>
<%--                                        <th>ONE Orden_S</th>--%>
<%--                                        <th>ONE Circuito</th>--%>
<%--                                    </thead>--%>
<%--                                    <tbody id="relevamientoList">--%>
<%--                                        <c:forEach items="${updateRespuestas}" var="bo" varStatus="status">--%>
<%--                                            <tr id="row-${bo.id}">--%>
<%--                                                <td>--%>
<%--                                                    ${bo.appRelevamiento.nombreRelevamiento}--%>
<%--                                                </td>--%>
<%--                                                <td>--%>
<%--                                                    <div class="text-center cursorPointer" onclick="deleteRelevamiento(${bo.id})" title="Quitar relevamiento">--%>
<%--                                                        <i style="color: darkred" class="fas fa-times-circle"></i>--%>
<%--                                                    </div>--%>
<%--                                                </td>--%>
<%--                                                <td><input type="number" id="oneOrdenS-${bo.id}" value="${bo.oneOrdenS}"/></td>--%>
<%--                                                <td><input type="number" id="oneCircuito-${bo.id}" value="${bo.oneCircuito}"/></td>--%>
<%--                                                <td style="align-items: center;">--%>
<%--                                                    <div class="col-6">--%>
<%--                                                        <i id="loadIcon-${bo.id}" class="fas fa-spinner fa-spin hidden"></i>--%>
<%--                                                        <i style="color:green" id="successIcon-${bo.id}" class='far fa-check-circle hidden'></i>--%>
<%--                                                    </div>--%>
<%--                                                    <button type="button" class="btn btn-sm btn-secondary btn-fill" onclick="updateRespuesta(${bo.id})">Guardar</button>--%>
<%--                                                </td>--%>
<%--                                            </tr>--%>
<%--                                        </c:forEach>--%>
<%--                                    </tbody>--%>
<%--                                </table>--%>
<%--                            </div>--%>

                        </div>
                    </div>
                    <a href="list" class="btn btn-light pull-left ml-3"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>


                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-secondary btn-fill pull-right mr-3" data-toggle="modal" data-target="#exampleModal">
                        Guardar
                    </button>

                    <!-- Modal -->
                    <div class="modal fade modal-confirm" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel"></h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <h5>&iquest;Desea confirmar los cambios?</h5>
                                </div>
                                <div class="modal-footer">
                                    <button type="#" class="btn btn-light" data-dismiss="modal">Cancelar</button>
                                    <button type="submit" class="btn btn-secondary btn-fill">Guardar</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <a href="#" class="btn btn-info btn-fill pull-right mr-1" onclick="showMap(lat, lng, 'mapApp', 'searchInputApp')">Mapa</a>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="mapModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div style="max-width: 650px;" class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Edici&oacute;n de coordenadas</h5>
                            <button type="button" onclick="reCreateInputSearch('mapApp','searchInputApp')" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div class="modal-body row">
                            <input id="searchInputApp" class="form-control col-sm-6" type="text" style="margin-top: 10px" placeholder="Ingrese una lugar ..">
                            <div id="mapApp">

                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<script>
    <c:choose>
    <c:when test="${updateAppUbicacion.latitud == null}">
    var lat = "";
    </c:when>
    <c:otherwise>
    var lat = ${updateAppUbicacion.latitud}
    </c:otherwise>
    </c:choose>
    <c:choose>
    <c:when test="${updateAppUbicacion.longitud == null}">
    var lng = "";
    </c:when>
    <c:otherwise>
    var lng = ${updateAppUbicacion.longitud};
    </c:otherwise>
    </c:choose>

    function setLatApp(event) {
        lat = event.target.value;
    }

    function setLngApp(event) {
        lng = event.target.value;
    }

    // function showRespuestaInputs(){
    //     let selectValue = document.getElementById("select-relevamientos").value;
    //     if(selectValue !== "-1"){
    //         $('#oneOrdenS-select').removeClass('hidden');
    //         $('#oneCircuito-select').removeClass('hidden');
    //
    //     }else{
    //         $('#oneOrdenS-select').addClass('hidden');
    //         $('#oneCircuito-select').addClass('hidden');
    //     }
    // }
    //
    // function deleteRelevamiento(idRespuesta){
    //     $('#appRespuestaTable').css('opacity', '0.4');
    //
    //     $.ajax( {
    //         url: "../appRespuesta/delete/" + idRespuesta,
    //         type: "DELETE",
    //         headers: {
    //             'Accept': 'application/json',
    //             'Content-Type': 'application/json'
    //         },
    //         success:  function (data) {
    //             $('#appRespuestaTable').css('opacity', '1');
    //
    //             document.getElementById('row-' + data.idRespuesta).remove();
    //             var e = document.getElementById("option-" + data.appRelevamiento.idRelevamiento);
    //             e.removeAttribute("disabled");
    //
    //             $.notify({
    //                 title: '<strong>Relevamiento quitado exitosamente!</strong>',
    //                 message: 'Se ha quitado correctamente la ubicacion del relevamiento!'
    //             }, {
    //                 type: 'info',
    //                 timer: 8000
    //             });
    //         },
    //         error: function (jqXHR, exception) {
    //             $('#appRespuestaTable').css('opacity', '1');
    //             console.log(jqXHR);
    //
    //             $.notify({
    //                 title: '<strong>Hubo un problema!</strong>',
    //                 message: 'Se produjo un error al intentar quitar el relevamiento.'
    //             }, {
    //                 type: 'danger'
    //             });
    //         }
    //     });
    // }
    //
    // function updateRespuesta(idRespuesta){
    //     $('#appRespuestaTable').css('opacity', '0.4');
    //
    //     let oneOrdenS = document.getElementById("oneOrdenS-" + idRespuesta).value;
    //     let oneCircuito = document.getElementById("oneCircuito-" + idRespuesta).value;
    //
    //     let dataToSend = { "idRespuesta": idRespuesta, "orden": oneOrdenS, "circuito": oneCircuito };
    //
    //     let dataJson = JSON.stringify(dataToSend);
    //
    //     $.ajax( {
    //         url: "../appRespuesta/updateRespuesta/",
    //         type: "POST",
    //         dataType: 'json',
    //         data: dataJson,
    //         beforeSend: function () {
    //             $("#successIcon-" + idRespuesta).addClass("hidden");
    //             $("#loadIcon-" + idRespuesta).removeClass("hidden");
    //         },
    //         headers: {
    //             'Accept': 'application/json',
    //             'Content-Type': 'application/json'
    //         },
    //         success:  function (data) {
    //             $("#loadIcon-" + idRespuesta).addClass("hidden");
    //             $("#successIcon-" + idRespuesta).removeClass("hidden");
    //             document.getElementById("appRespuestaTable").style.backgroundColor = "#0080004a";
    //             $('#appRespuestaTable').css('opacity', '1');
    //
    //             console.log(data);
    //             $.notify({
    //                 title: '<strong>Cambios guardados exitosamente!</strong>',
    //                 message: ''
    //             }, {
    //                 timer: 8000
    //             });
    //
    //         },
    //         error: function (jqXHR, exception) {
    //             console.log(jqXHR);
    //             $("#loadIcon-" + idRespuesta).addClass("hidden");
    //             $("#successIcon-" + idRespuesta).addClass("hidden");
    //             document.getElementById("appRespuestaTable").style.backgroundColor = "#dc354563";
    //             $('#appRespuestaTable').css('opacity', '1');
    //
    //             $.notify({
    //                 title: '<strong>Hubo un problema!</strong>',
    //                 message: 'Se produjo un error al intentar guardar los cambios.'
    //             }, {
    //                 type: 'danger'
    //             });
    //         }
    //     });
    //
    // }
    //
    // window.onload = function() {
    //     showRespuestaInputs();
    // };
</script>
