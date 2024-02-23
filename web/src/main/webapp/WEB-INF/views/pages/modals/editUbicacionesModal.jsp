<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Modal -->
<div class="modal fade" id="optionModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Datos a Cambiar</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
                <input type="hidden" name="page" value="${ubicacionRequest.page}"/>
                <div class="modal-body row">

                        <div class="form-group col-6">
                            <label for="idEmpresa">Empresas</label>
                            <select name="request.idEmpresa" class="form-control" title="Seleccione una empresa">
                                <option  value="-1">Seleccione una Empresa</option>
                                <c:forEach items="${empresas}" var="bo">
                                    <option value="${bo.id}">${bo.descripcion}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group col-6">
                            <label for="idElemento">Elementos</label>
                            <select name="request.idElemento" class="form-control" title="Seleccione un elemento">
                                <option  value="-1">Seleccione un Elemento</option>
                                <c:forEach items="${elementos}" var="bo">
                                    <option value="${bo.id}">${bo.descripcion}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group col-6">
                            <label for="idFormato">Formatos</label>
                            <select name="request.idFormato" items="${formatos}" class="form-control" title="Seleccione un formato">
                                <option  value="-1">Seleccione un Formato</option>
                                <c:forEach items="${formatos}" var="bo">
                                    <option value="${bo.id}">${bo.descripcion}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group col-6">
                            <label for="idMedio">Medios</label>
                            <select name="request.idMedio" class="form-control" title="Seleccione un medio">
                                <option  value="-1">Seleccione una Medio</option>
                                <c:forEach items="${medios}" var="bo">
                                    <option value="${bo.id}">${bo.descripcion}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group col-6">
                            <label for="idLocalidad">Localidadades</label>
                            <select name="request.idLocalidad" items="${localidades}" class="form-control" title="Seleccione una localidad">
                                <option  value="-1">Seleccione una Localidad</option>
                                <c:forEach items="${localidades}" var="bo">
                                    <option value="${bo.id}">${bo.descripcion}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group col-6">
                            <label for="idProvincia">Provincias</label>
                            <select name="request.idProvincia" items="${provincias}" class="form-control" title="Seleccione una provincia">
                                <option  value="-1">Seleccione una Provincia</option>
                                <c:forEach items="${provincias}" var="bo">
                                    <option value="${bo.id}">${bo.descripcion}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <sec:authorize access="hasAnyRole(
                           T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                        ">
                        <div class="form-group col-6">
                            <label for="idAltura">Alturas</label>
                            <select name="request.idAltura" items="${alturas}" class="form-control" title="Seleccione una altura">
                                <option  value="-1">Seleccione una altura</option>
                                <c:forEach items="${alturas}" var="bo">
                                    <option value="${bo.id}">${bo.descripcion}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group col-6">
                            <label for="idVisibilidad">Visibilidades</label>
                            <select name="request.idVisibilidad" items="${visibilidades}" class="form-control" title="Seleccione una visibilidad">
                                <option  value="-1">Seleccione una visibilidad</option>
                                <c:forEach items="${visibilidades}" var="bo">
                                    <option value="${bo.id}">${bo.descripcion}</option>
                                </c:forEach>
                            </select>
                        </div>
                        </sec:authorize>

                        <div class="form-group col-6">
                            <label for="bajaLogicaRequest">Estado</label>
                            <select name="request.bajaLogicaRequest" class="form-control" title="Seleccione un estado">
                                <option value="">Seleccione un estado</option>
                                <option value="false">Activo</option>
                                <option value="true">Inactivo</option>
                            </select>
                        </div>

                        <sec:authorize access="hasAnyRole(
                           T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                        ">
                        <div class="form-group col-6">
                            <label for="metrosContactoRequest">Metros Contacto</label>
                            <input type="number" name="request.metrosContactoRequest" class="form-control" title="Metros Contacto" placeholder="Ingrese metros de contacto">
                        </div>
                        <div class="form-group col-6">
                            <label for="coeficienteRequest">Coeficiente</label>
                            <input  type="number" step="any" name="request.coeficienteRequest" class="form-control" title="Coeficiente" placeholder="Ingrese coeficiente">
                        </div>
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole(
                           T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
                        ">
                            <c:forEach items="${parametros}" var="bo" varStatus="status">
                                <div class="form-group col-6">
                                    <label for="request.parametros[${bo.idParametro}].id" class="control-label">${bo.nombre}</label>
                                    <input type="text" name="request.parametros[${bo.idParametro}]" class="form-control filterInput" placeholder="Ingrese un valor para el par&aacute;metro ${bo.nombre}">
                                </div>
                            </c:forEach>
                        </sec:authorize>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>

                    <button name="saveList" type="submit"  class="btn btn-primary"><i class="nc-icon nc-map-big"></i>&nbsp;Guardar resultados</button>
                </div>
        </div>
    </div>
</div>