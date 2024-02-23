<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
    .form-check {
        margin-top: 10px;
    }

    .dt-buttons {
        display: none;
    }

</style>

<div class="content">
    <div class="container-fluid">

        <div class="row">

            <div class="col-12">

                <div class="card strpied-tabled-with-hover">

                    <div class="card-header">
                        <h4 class="card-title">Clientes</h4>
                    </div>

                    <div class="card-body table-full-width table-responsive">

                        <form:form action="map" modelAttribute="wrapper" name="ubicaciones">

                            <table id="dataTableToEdit" class="display" style="width:100%">

                                <thead>
                                    <th>ID</th>
                                    <th>Referencia</th>
                                    <th>Empresa</th>
                                    <th>Elemento</th>
                                    <th>Formato</th>
                                    <th>Medio</th>
                                    <th>Direccion</th>
                                    <sec:authorize access="hasAnyRole(
                                       T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
                                    ">
                                    <c:forEach items="${parametros}" var="bo" varStatus="status">
                                    <th>${bo.nombre}</th>
                                    </c:forEach>
                                    </sec:authorize>
                                    <th>Nro.Agip</th>
                                    <th>Localidad</th>
                                    <th>Provincia</th>
                                    <th>Cantidad</th>
                                    <th>Transito</th>
                                    <th>Iluminacion</th>
                                    <th>Medidas</th>
                                    <th>Latitud</th>
                                    <th>Longitud</th>
                                    <th>Metros Contacto</th>
                                    <th>Coeficiente</th>
                                    <th>ID Referencia</th>
                                    <th>Buses</th>
                                    <th>Baja Logica</th>
                                    <th>Fecha de Tranferencia</th>
                                    <th>Fecha de Alta</th>
                                    <th>Altura</th>
                                    <th>Visibilidad</th>
                                </thead>

                                <tbody>

                                <c:forEach items="${ubicaciones}" var="bo" varStatus="status">

                                    <tr>
                                        <td>${bo.id}</td>
                                        <td>${bo.idReferencia}</td>
                                        <td>${bo.mapEmpresa.descripcion}</td>
                                        <td>${bo.mapElemento.descripcion}</td>
                                        <td>${bo.mapFormato.descripcion}</td>
                                        <td>${bo.mapMedio.descripcion}</td>
                                        <td>${bo.direccion}</td>
                                        <sec:authorize access="hasAnyRole(
                                           T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
                                        ">
                                        <c:forEach items="${bo.mapEmpresa.parametros}" var="boI" varStatus="statusI">
                                        <td>
                                        <c:if test="${bo.parametros!=null}">
                                        <c:forEach items="${bo.parametros}" var="boJ" varStatus="statusJ">
                                        <c:if test="${boJ.parametro.idParametro.equals(boI.idParametro)}">
                                            ${bo.parametros[statusJ.index].descripcion}
                                        </c:if>
                                        </c:forEach>
                                        </c:if>
                                        </td>
                                        </c:forEach>
                                        </sec:authorize>
                                        <td>${bo.nroAgip}</td>
                                        <td>${bo.referencia}</td>
                                        <td>${bo.nroAnuncio}</td>
                                        <td>${bo.mapLocalidad.descripcion}</td>
                                        <td>${bo.mapProvincia.descripcion}</td>
                                        <td>${bo.cantidad}</td>
                                        <td>${bo.transito}</td>
                                        <td>${bo.iluminacion}</td>
                                        <td>${bo.medidas}</td>
                                        <td>${bo.latitud}</td>
                                        <td>${bo.longitud}</td>
                                        <td>${bo.metrosContacto}</td>
                                        <td>${bo.coeficiente}</td>
                                        <td>${bo.mapBuses.linea}</td>
                                        <td>${bo.bajaLogica}</td>
                                        <td>${bo.fechaTransf}</td>
                                        <td>${bo.fechaAlta}</td>
                                        <td>${bo.mapUbicacionAltura.descripcion}</td>
                                        <td>${bo.mapUbicacionVisibilidad.descripcion}</td>
                                    </tr>

                                </c:forEach>

                                </tbody>

                            </table>


                        </form:form>

                        <div class="col-8">
                            <a href="#" onclick="onSubmit('ubicaciones')" class="btn btn-primary btn-fill"><i class="nc-icon nc-map-big"></i>&nbsp;Guardar</a>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<li class="nav-item more-options" style="display: none">
    <a href="#" class="nav-link">
        <i id="icon-options" class="nc-icon nc-simple-add"></i>
        <span id="span-more-options" class="d-lg-block" data-toggle="modal" data-target="#optionModal">&nbsp;Datos a Editar</span>
    </a>
</li>



<!-- Modal -->
<div class="modal fade" id="optionModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Datos a modificar</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form:form action="saveList"  modelAttribute="mapUbicacionRequest" name="searchModal">
                <input type="hidden" name="page" value="${ubicacionRequest.page}"/>
                <div class="modal-body row">

                    <div class="form-group col-6">
                        <label for="empresa">Empresas</label>
                        <select name="mapEmpresa" data-done-button="true" class="form-control" title="Seleccione una empresa">
                            <c:forEach items="${empresas}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="elemento">Elementos</label>
                        <select name="mapElemento" class="form-control" title="Seleccione un elemento">
                            <c:forEach items="${elementos}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="formato">Formatos</label>
                        <select name="mapFormato" items="${formatos}" class="form-control" title="Seleccione un formato">
                            <c:forEach items="${formatos}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="medio">Medios</label>
                        <select name="mapMedio" class="form-control" title="Seleccione un medio   ">
                            <c:forEach items="${medios}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="localidad">Localidadades</label>
                        <select name="mapLocalidad" items="${localidades}" class="form-control" title="Seleccione una localidad">
                            <c:forEach items="${localidades}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="provincia">Provincias</label>
                        <select name="mapProvincia" items="${provincias}" class="form-control" title="Seleccione una provincia">
                            <c:forEach items="${provincias}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                </div>

            </form:form>
        </div>
    </div>
</div>