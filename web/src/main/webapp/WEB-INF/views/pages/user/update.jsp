<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .card form [class*="col-"]:first-child {
        padding-left: 6px;
    }
    .card form [class*="col-"]:last-child {
        padding-right: 6px;
    }
</style>
<div class="content">
    <div class="col-12">
        <div class="card">
            <%--@elvariable id="usuarioWrapper" type="com.ideaas.services.bean.UsuarioWrapper"--%>
            <form:form action="update" modelAttribute="usuarioWrapper"  method="post">
                <form:hidden path="usuario.id" value='${usuario.id}'/>
                <div class="row ml-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Usuario</label>
                                    <form:input  path="usuario.username" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Password</label>
                                    <form:input  path="usuario.password" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Nombre completo</label>
                                    <form:input  path="usuario.nombreCompleto" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">E-mail</label>
                                    <form:input  path="usuario.eMail" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Telefono</label>
                                    <form:input  path="usuario.telefono" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                                </div>

                                <div class="col-6">
                                    <label for="select-tipo-usuairo" class="control-label pt-2">Tipo de Usuario</label>
                                    <select class="form-control" id="select-tipo-usuairo" name="usuario.tipoUsuario.id">
                                        <c:forEach items="${tiposUsuarios}" var="bo" varStatus="status">
                                            <option ${bo.descripcion == usuarioWrapper.usuario.tipoUsuario.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Empresa</label>
                                    <form:input  path="usuario.cargo" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese un cargo"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label pt-2">Contrato Inversion</label>
                                    <select class="form-control" id="street1_id" name="usuario.contratoInv.id">
                                        <c:forEach items="${contratosInversion}" var="bo" varStatus="status">
                                            <option ${bo.descripcion == usuarioWrapper.usuario.contratoInv.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label pt-2">Contrato Auditoria</label>
                                    <select class="form-control" id="street1_id" name="usuario.contratoAud.id">
                                        <c:forEach items="${contratosAuditoria}" var="bo" varStatus="status">
                                            <option ${bo.descripcion == usuarioWrapper.usuario.contratoAud.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label pt-2">Contrato Mapping</label>
                                    <select class="form-control" id="street1_id" name="usuario.contratoMap.id">
                                        <c:forEach items="${contratosMapping}" var="bo" varStatus="status">
                                            <option ${bo.descripcion == usuarioWrapper.usuario.contratoMap.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-6">
                                    <label for="expirationDate" class="control-label">Fecha Vencimiento</label>
                                    <form:input  path="usuario.fechaVencimiento" cssClass="form-control" autocomplete="off"  id="expirationDate" name="street1" placeholder="Seleccione una fecha"/>

                                </div>
                                <div class="col-6" id="div-empresas-select-container">
                                    <label for="select-usuario-empresas" class="control-label pt-2">Empresas</label>
                                    <select id="select-usuario-empresas"  name="empresasIDs" data-done-button="true" class="form-control" multiple data-live-search="true" data-actions-box="true" title="Seleccione una empresa">
                                        <c:forEach items="${empresas}" var="boI">
                                            <option
                                                <c:forEach items="${usuarioWrapper.usuario.empresas}" var="boJ">
                                                ${boJ.id.equals(boI.id) ? 'selected' : ''}
                                                </c:forEach>
                                                value="${boI.id}">${boI.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-12" style="text-align: center">
                                    <label class="col-12" style="color: black;" class="control-label pt-2">Modulos</label>
                                    <div>
                                        <c:forEach items="${modulos}" var="bo" varStatus="status">

                                            <c:set var="contains" value="false" />
                                            <c:forEach var="item" items="${modulosUsuario}">
                                                <c:if test="${item.usuarioMenuId.idMenu eq bo.id}">
                                                    <c:set var="contains" value="true" />
                                                    <form:checkbox path="usuario.usuarioMenuRequest.modulos" cssClass="mx-3" value="${bo.id}" checked="${contains}"/>${bo.descripcion}
                                                </c:if>
                                            </c:forEach>

                                            <c:if test="${!contains}">
                                                <form:checkbox path="usuario.usuarioMenuRequest.modulos" cssClass="mx-3" value="${bo.id}"/>${bo.descripcion}
                                            </c:if>

                                        </c:forEach>
                                    </div>
                                </div>

                                <input type="hidden" name="usuario.estado" value="M"/>
                                <input type="hidden" name="usuario.mapAnunciante.id" value="0">
                                <input type="hidden" name="usuario.producto.id" value="0">
                            </div>
                        </div>
                        <a href="list" class="btn btn-light pull-left ml-3"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>


                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-secondary btn-fill pull-right mr-3" data-toggle="modal" data-target="#exampleModal">
                            Guardar
                        </button>

                        <!-- Modal -->
                        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" style="top:180px; text-align: center;">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel"></h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <h5>&iquest;Desea guardar los cambios?</h5>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="#" class="btn btn-light " data-dismiss="modal">Cancelar</button>
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
<script>
    var element = document.getElementById("users");
    element.classList.add("active");

    $(document).ready(function(){

        $("#expirationDate").datepicker({
            dateFormat: 'yy-mm-dd',
            changeYear: true
        });
    });
</script>