<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .card form [class*="col-"]:first-child {
        padding-left: 6px;
    }
    .card form [class*="col-"]:last-child {
        padding-right: 6px;
    }

    .field-icon {
        float: right;
        margin-right: 12px;
        margin-top: -28px;
        position: relative;
        z-index: 2;
    }
</style>

<div class="content">
    <div class="col-12">
        <div class="card">
            <form:form autocomplete="off" action="save" modelAttribute="usuarioWrapper" method="post">
                <div class="row ml-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Usuario</label>
                                    <form:input path="usuario.username" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese un nuevo usuario"/>
                                    <form:errors path="usuario.username" cssStyle="color: red;"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Password</label>
                                    <form:input path="usuario.password" cssClass="form-control" id="inputPassword" type="password" autocomplete="new-password" name="street1" placeholder="Password"/>
                                    <span id="showPassword" class="fas fa-eye field-icon" onclick="showPassword()"></span>
                                    <form:errors path="usuario.password" cssStyle="color: red;"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Nombre completo</label>
                                    <form:input  path="usuario.nombreCompleto" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese el nombre completo"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">E-mail</label>
                                    <form:input  path="usuario.eMail" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese un E-mail"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Telefono</label>
                                    <form:input  path="usuario.telefono" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese un telefono"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Estado</label>
                                    <select class="form-control" id="street1_id" name="usuario.estado">
                                            <option value="A" selected="selected">Alta</option>
                                            <option value="B">Baja</option>
                                            <option value="M">Modificado</option>
                                    </select>
                                </div>
                                <div class="col-6">
                                    <label for="select-tipo-usuairo" class="control-label pt-2">Tipo de Usuario</label>
                                    <select class="form-control" id="select-tipo-usuairo" name="usuario.tipoUsuario.id">
                                        <c:forEach items="${tiposUsuarios}" var="bo" varStatus="status">
                                            <option value="${bo.id}">${bo.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Empresa</label>
                                    <form:input  path="usuario.cargo" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese un cargo"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label pt-2">Contrato Inversion</label>
                                    <select class="form-control" id="street3_id" name="usuario.contratoInv.id">
                                        <option value="0">SIN CONTRATO</option>
                                        <c:forEach items="${contratosInversion}" var="bo" varStatus="status">
                                            <option value="${bo.id}">${bo.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label pt-2">Contrato Auditoria</label>
                                    <select class="form-control" id="street4_id" name="usuario.contratoAud.id">
                                        <option value="0">SIN CONTRATO</option>
                                        <c:forEach items="${contratosAuditoria}" var="bo" varStatus="status">
                                            <option value="${bo.id}">${bo.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label pt-2">Contrato Mapping</label>
                                    <select class="form-control" id="street5_id" name="usuario.contratoMap.id">
                                        <option value="0">SIN CONTRATO</option>
                                        <c:forEach items="${contratosMapping}" var="bo" varStatus="status">
                                            <option value="${bo.id}">${bo.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-6" id="div-empresas-select-container">
                                    <label for="select-usuario-empresas" class="control-label pt-2">Empresas</label>
                                    <select id="select-usuario-empresas" name="empresasIDs" data-done-button="true" class="form-control" multiple data-live-search="true" data-actions-box="true" title="Seleccione una empresa">
                                        <c:forEach items="${empresas}" var="bo">
                                            <option value="${bo.id}">${bo.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-12" style="text-align: center">
                                    <label class="col-12" style="color: black;" class="control-label pt-2">Modulos</label>
                                    <div>

                                        <c:forEach items="${modulos}" var="bo" varStatus="status">
                                            <form:checkbox path="usuario.UsuarioMenuRequest.modulos" cssClass="mx-3" value="${bo.id}"/>${bo.descripcion}
                                        </c:forEach>

<%--                                        <form:checkboxes path="UsuarioMenuRequest.modulos" cssClass="mx-3" items="${modulos}" itemLabel="descripcion" itemValue="id"/>--%>
                                    </div>
                                    <div>
                                        <form:errors path="usuario.UsuarioMenuRequest.modulos" cssStyle="color: red;"/>
                                    </div>
                                </div>
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
                                        <h5>&iquest;Desea guardar un nuevo usuario?</h5>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="#" class="btn btn-light" data-dismiss="modal">Cancelar</button>
                                        <button type="submit" class="btn btn-secondary btn-fill">Guardar</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="clearfix"></div>
            </form:form>
        </div>
    </div>
</div>