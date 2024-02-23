<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags" %>

<style>
    #mapCreate {
        height: 400px;  /* The height is 400 pixels */
        width: 100%;  /* The width is the width of the web page */
    }
    .card #mapUbicacion [class*="col-"]{
        padding: 6px;
    }
</style>

<script>
    var lat;
    var lng;

    function setLat(event) {
        console.log(event.target.value);
        lat = event.target.value;
    }

    function setLong(event) {
        console.log(event.target.value);
        lng = event.target.value;
    }

    $(document).ready(function() {
        $(window).keydown(function(event){
            if(event.keyCode == 13) {
                event.preventDefault();
                return false;
            }
        });
    });
</script>

<div class="content">
    <div class="col-12">
        <div class="card">
            <%--@elvariable id="mapUbicacion" type="com.ideaas.services.domain.MapUbicacion"--%>
            <form:form autocomplete="off" action="save" modelAttribute="mapUbicacion" method="post">
                <%--<form:input path="latitud" value='${ubicacion.latitud}'/>--%>
                <%--<form:input path="longitud" value='${ubicacion.longitud}'/>--%>
                <div class="row m-0 p-3">
                    <div class="col-6">
                        <label class="control-label pt-2">Empresa</label>
                        <select id="select-empresas" name="mapEmpresa.id" class="form-control selectStyle" data-live-search="true" title="Seleccione una empresa">
                            <c:forEach items="${empresas}"  var="bo" varStatus="status">
                                <option ${bo.descripcion == mapUbicacion.mapEmpresa.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                        <form:errors path="mapEmpresa" cssStyle="color: red;"/>
                    </div>
                    <div class="col-6">
                        <label class="control-label pt-2">Elemento</label>
                        <select class="form-control selectStyle" id="select-elementos" name="mapElemento.id" data-live-search="true" title="Seleccione un elemento">
                            <c:forEach items="${elementos}" var="bo" varStatus="status">
                                <option ${bo.descripcion == mapUbicacion.mapElemento.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                        <form:errors path="mapElemento" cssStyle="color: red;"/>
                    </div>
                    <div class="col-6">
                        <label class="control-label pt-2">Formato</label>
                        <select class="form-control selectStyle" id="select-formatos" name="mapFormato.id" title="Seleccione un formato">
                            <c:forEach items="${formatos}" var="bo" varStatus="status">
                                <option ${bo.descripcion == mapUbicacion.mapFormato.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                        <form:errors path="mapFormato" cssStyle="color: red;"/>
                    </div>
                        <div class="col-6">
                        <label class="control-label pt-2">Medio</label>
                        <select class="form-control selectStyle" id="select-medios" name="mapMedio.id" title="Seleccione un medio">
                            <c:forEach items="${medios}" var="bo" varStatus="status">
                                <option ${bo.descripcion == mapUbicacion.mapMedio.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                        <form:errors path="mapMedio" cssStyle="color: red;"/>
                    </div>
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Direccion</label>
                        <form:input  path="direccion" cssClass="form-control" id="newAddress" name="street1" placeholder=""/>
                    </div>
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Numero Agip</label>
                        <form:input  path="nroAgip" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                    </div>
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Referencia</label>
                        <form:input  path="referencia" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                    </div>
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Cantidad</label>
                        <form:input  path="cantidad" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                    </div>
                    <div class="col-6">
                        <label class="control-label pt-2">Provincia</label>
                        <select class="form-control selectStyle" data-live-search="true" id="select-provincias" name="mapProvincia.id" title="Seleccione una provincia">
                            <c:forEach items="${provincias}" var="bo" varStatus="status">
                                <option ${bo.descripcion == mapUbicacion.mapProvincia.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion == "" ? "-Dejar vacio-" : bo.descripcion}</option>
                            </c:forEach>
                        </select>
                        <form:errors path="mapProvincia" cssStyle="color: red;"/>
                    </div>
                    <div class="col-6">
                        <label class="control-label pt-2">Localidad</label>
                        <select class="form-control selectStyle" data-live-search="true" id="select-localidades" name="mapLocalidad.id" title="Seleccione una localidad">
                            <c:forEach items="${localidades}" var="bo" varStatus="status">
                                <option ${bo.descripcion == mapUbicacion.mapLocalidad.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                        <form:errors path="mapLocalidad" cssStyle="color: red;"/>
                    </div>
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Transito</label>
                        <form:input  path="transito" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                    </div>
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Iluminacion</label>
                        <form:input  path="iluminacion" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                    </div>
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Medida</label>
                        <form:input  path="medidas" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                    </div>
                    <sec:authorize access="hasAnyRole(
                                   T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                ">
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Metros Contacto</label>
                        <form:input  value="0" type="number" path="metrosContacto" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                        <form:errors path="metrosContacto" cssStyle="color: red;"/>
                    </div>
                    </sec:authorize>
                    <div class="col-6">
                        <label class="control-label pt-2">Latitud</label>
                        <form:input path="latitud" value='${mapUbicacion.latitud}' cssClass="form-control" onchange="setLat(event)"/>
                    </div>
                    <div class="col-6">
                        <label class="control-label pt-2">Longitud</label>
                        <form:input path="longitud" value='${mapUbicacion.longitud}' cssClass="form-control" onchange="setLong(event)"/>
                    </div>
                    <sec:authorize access="hasAnyRole(
                                   T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                ">
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Coeficiente</label>
                        <form:input value="0.00" type="number" step="any" path="coeficiente" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                        <form:errors path="coeficiente" cssStyle="color: red;"/>
                    </div>
                    </sec:authorize>
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Referencia</label>
                        <form:input  path="idReferencia" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                    </div>
                    <sec:authorize access="hasAnyRole(
                                   T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                ">
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Buses</label>
                        <select class="form-control" id="street1_id" name="mapBuses.id">
                            <c:forEach items="${buses}" var="bo" varStatus="status">
                                <option ${bo.id == mapUbicacion.mapBuses.id ? 'selected' : ''} value="${bo.id}">${bo.id}</option>
                            </c:forEach>
                        </select>
                    </div>
                    </sec:authorize>
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Fecha Transf</label>
                        <form:input  path="fechaTransf" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                    </div>
                    <sec:authorize access="hasAnyRole(
                                   T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                ">
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Ubicacion Altura</label>
                        <select class="form-control" id="street1_id" name="mapUbicacionAltura.id">
                            <c:forEach items="${alturas}" var="bo" varStatus="status">
                                <option ${bo.descripcion == mapUbicacion.mapUbicacionAltura.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-6">
                        <label for="street1_id" class="control-label pt-2">Ubicacion visibilidad</label>
                        <select class="form-control" id="street1_id" name="mapUbicacionVisibilidad.id">
                            <c:forEach items="${visibilidades}" var="bo" varStatus="status">
                                <option ${bo.descripcion == mapUbicacion.mapUbicacionVisibilidad.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>
                    </sec:authorize>
                    <c:choose>
                    <c:when test="${empresas !=null && empresas[0] !=null}">
                    <c:forEach items="${empresas[0].parametros}" var="bo" varStatus="status">
                        <div class="col-6">
                            <label for="mapUbicaionParametro.id" class="control-label pt-2">${bo.nombre}</label>
                            <input type="hidden" name="parametros[${status.index}].id.idUbicaion" value='${id}'>
                            <input type="hidden" name="parametros[${status.index}].id.idParametro" value='${bo.idParametro}' >
                            <input  type="text" name="parametros[${status.index}].descripcion" class="form-control" id="street1_id" placeholder="Ingrese un valor para el par&aacute;metro ${bo.nombre}">
                        </div>
                    </c:forEach>
                    </c:when>
                    </c:choose>
                    <form:input type="hidden" path="bajaLogica" cssClass="form-control" id="street1_id" name="street1" value="false"/>
                </div>
                <div class="row m-0 p-3">
                    <div class="col-md-12">
                        <a href="list" class="btn btn-light pull-left"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>


                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-secondary btn-fill pull-right mr-3" data-toggle="modal" data-target="#exampleModal">
                            Guardar
                        </button>

                        <!-- Modal -->
                        <div class="modal fade modal-confirm" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel1"></h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <h5>&iquest;Desea guardar una nueva ubicacion?</h5>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="#" class="btn btn-light" data-dismiss="modal">Cancelar</button>
                                        <button type="submit" class="btn btn-secondary btn-fill">Guardar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <a href="#" class="btn btn-info btn-fill pull-right mr-1" onclick="showMap(lat, lng , 'mapCreate','searchInputCreate')"><i class=""></i>Mapa</a>

                    </div>
                <div class="modal fade" id="mapModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div style="max-width: 650px;" class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Selecci&oacute;n de la geolocalizaci&oacute;n</h5>
                                <button type="button" onclick="reCreateInputSearch('mapCreate','searchInputCreate')" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>

                            <div class="modal-body row">
                                <input id="searchInputCreate" class="form-control col-sm-6" type="text" style="margin-top: 10px" placeholder="Ingrese una lugar ..">
                                <div id="mapCreate">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            </form:form>

            <%--Modal error stock --%>
            <jsp:include page="../modals/stockError.jsp"/>
        </div>
    </div>
</div>

