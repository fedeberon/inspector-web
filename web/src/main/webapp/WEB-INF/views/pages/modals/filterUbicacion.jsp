<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.ideaas.services.enums.ReservationState" %>

<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Filtros</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form:form action="search"  modelAttribute="myWrapper" id="searchModalFilter" name="searchModalFilter">
                <div class="modal-body row">

                    <c:choose>
                    <c:when test="${displayPlanificacionFilters}">
                    <div class="form-group col-6">
                        <label for="reservacionFechaDesde">Fecha de inicio de reservaci&oacute;n</label>
                        <input name="request.reservacionFechaDesde" id="reservacionFechaDesde" autocomplete="off"  class="form-control datepicker filterInput" placeholder="Seleccione una fecha de inicio de reservaci&oacute;n"/>
                    </div>

                    <div class="form-group col-6">
                        <label for="reservacionFechaHasta">Fecha de finalizacion</label>
                        <input name="request.reservacionFechaHasta" id="reservacionFechaHasta" autocomplete="off"  class="form-control datepicker filterInput" placeholder="Seleccione una fecha de finalizaci&oacute;n de reservaci&oacute;n"/>
                    </div>

                    <div class="form-group col-6">
                        <label for="request.mapClientes">Clientes</label>
                        <select data-select-independent data-select-dependent-target="#campaigns-select-filter" id="select-clientes" name="request.mapClientes" data-done-button="true" class="form-control" multiple data-live-search="true" data-actions-box="true" title="Seleccione un cliente">
                            <c:forEach items="${clientes}" var="bo">
                                <option value="${bo.id}">${bo.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label>Camapa&ntilde;a</label>
                        <select data-select-dependent id="campaigns-select-filter" name="request.campana"  data-done-button="true" class="form-control selectpicker-default" multiple data-live-search="true" data-actions-box="true" title="Seleciones una campa&ntilde;a">
                            <c:forEach items="${campaigns}" var="bo">
                                <option data-dependent-from="${bo.clientId}" value="${bo.campaignId}">${bo.campaign}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="request.estadoReservacion">Estado de reservaci&oacute;n</label>
                        <select id="select-estado-reservacion" name="request.estadoReservacion" multiple class="form-control" title="Seleccione una estado de reservaci&oacute;n">
                            <option value="${ReservationState.AVAILABLE.code}">${ReservationState.AVAILABLE.description}</option>
                            <option value="${ReservationState.NOT_CONFIRMED.code}">${ReservationState.NOT_CONFIRMED.description}</option>
                            <option value="${ReservationState.CONFIRMED.code}">${ReservationState.CONFIRMED.description}</option>
                        </select>
                    </div>
                    </c:when>
                    </c:choose>

                    <div class="form-group col-6">
                        <label>Id Ubicacion</label>
                        <input id="input-ids" name="request.idsSearch" autocomplete="off"  class="form-control filterInput" placeholder="Ingrese IDs separados por comas"/>
                    </div>

                    <div class="form-group col-6">
                        <label for="empresa">Empresas</label>
                        <select id="select-empresas" name="request.mapEmpresa" data-done-button="true" class="form-control" multiple data-live-search="true" data-actions-box="true" title="Seleccione una empresa">
                            <c:forEach items="${empresas}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="elemento">Elementos</label>
                        <select id="select-elementos" name="request.mapElemento" data-done-button="true" class="form-control" multiple data-live-search="true" data-actions-box="true" title="Seleccione un Elemento">
                            <c:forEach items="${elementos}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="formato">Formatos</label>
                        <select id="select-formatos" name="request.mapFormato" data-done-button="true" class="form-control" multiple data-live-search="true" data-actions-box="true" title="Seleccione un formato">
                            <c:forEach items="${formatos}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="medio">Medios</label>
                        <select id="select-medios" name="request.mapMedio" data-done-button="true" class="form-control" multiple data-live-search="true" data-actions-box="true" title="Seleccione un Medio   ">
                            <c:forEach items="${medios}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="localidad">Localidadades</label>
                        <select id="select-localidades" name="request.mapLocalidad" data-done-button="true" class="form-control" multiple data-live-search="true" data-actions-box="true" title="Seleccione una Localidad">
                            <c:forEach items="${localidades}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="provincia">Provincias</label>
                        <select id="select-provincias" name="request.mapProvincia" data-done-button="true" class="form-control" multiple data-live-search="true" data-actions-box="true" title="Seleccione una Provincia">
                            <c:forEach items="${provincias}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="bajaLogica">Estado</label>
                        <select id="select-estados" name="request.bajaLogica" class="form-control" multiple title="Seleccione un estado">
                            <!--Solo la pantalla de planning/list necesita a "activo" como el default-->
                            <option
                            <c:if test="${displayPlanificacionFilters}">
                                selected
                            </c:if>
                            value="false">Activo</option>
                            <option value="true">Inactivo</option>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="latLngEmpty">GeoLocalizacion</label>
                        <select id="select-geolocalizacion" name="request.latLngEmpty" multiple class="form-control" title="Seleccione una opci&oacute;n">
                            <option value="false">Con coordenadas</option>
                            <option value="true">Sin coordenadas</option>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="fechaAlta">Fecha de alta</label>
                        <input name="request.fechaAlta" id="fechaAlta" autocomplete="off"  class="form-control datepicker filterInput" placeholder="Seleccione una fecha"/>
                    </div>

                    <div class="form-group col-6">
                        <label>Buscar</label>
                        <input id="input-searching" name="request.searchValue" autocomplete="off"  class="form-control filterInput" placeholder="direccion, id referencia, altura, visibilidad "/>
                    </div>

                    <%-- The client deprecated this filter
                    <div class="form-group col-6">
                        <label for="maxResults">Cantidad resultados</label>
                        <select id="select-maxResults" class="form-control" name="request.maxResults" data-live-search="true">
                            <option value="10" selected>10</option>
                            <option value="20">20</option>
                            <option value="50">50</option>
                            <option value="-1">Todos</option>
                        </select>
                    </div> --%>
                    <sec:authorize access="hasAnyRole(
                       T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
                    ">
                        <c:forEach items="${parametros}" var="bo" varStatus="status">
                            <div class="form-group col-6">
                                <label for="request.parametros[${bo.idParametro}].id" class="control-label">${bo.nombre}</label>
                                <input type="text" name="request.parametros[${bo.idParametro}]" class="form-control filterInput" placeholder="Ingrese un valor para el par&aacute;metro ${bo.nombre}" value=
                                <c:choose>
                                    <c:when test="${ubicacionRequest.parametros.get(bo.idParametro) != null}">
                                        "${ubicacionRequest.getFilteredParametros().get(bo.idParametro)}"
                                    </c:when>
                                </c:choose>
                                >
                            </div>
                        </c:forEach>
                    </sec:authorize>

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
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>

                    <button type="button" class="btn btn-secondary" id="btn-check-result" onclick="disabledOptionsNotFounds()">Chequear resultados</button>

                    <button name="paginate" onclick="onSubmit('searchModalFilter')" class="btn btn-primary">Buscar</button>
                </div>

            </form:form>
        </div>
    </div>
</div>