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
            <form:form autocomplete="off" action="../save" modelAttribute="appRelevamiento" method="post">
                <div class="row ml-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-6">
                                    <label for="nombre" class="control-label">Nombre Ruta:</label>
                                    <form:input path="nombreRelevamiento" cssClass="form-control" id="nombre" placeholder="*Obligatorio"/>
                                    <form:errors path="nombreRelevamiento" cssStyle="color: red;"/>
                                </div>

                                <div class="col-6">
                                    <label for="select-inspector" class="control-label pt-2">Inspector:</label>
                                    <select id="select-inspector" name="usuario.id" class="form-control selectStyle" data-live-search="true" title="Seleccione un inspector">
                                        <c:forEach items="${inspectores}" var="bo" varStatus="status">
                                            <option value="${bo.id}">${bo.username}</option>
                                        </c:forEach>
                                    </select>
                                    <form:errors path="usuario" cssStyle="color: red;"/>
                                </div>

                                <div class="col-6">
                                    <label for="select-formulario" class="control-label pt-2">formulario:</label>
                                    <select id="select-formulario" name="formulario.id" class="form-control selectStyle" data-live-search="true" title="Seleccione un formulario">
                                        <c:forEach items="${formularios}" var="bo" varStatus="status">
                                            <option value="${bo.id}">${bo.nombreFormulario}</option>
                                        </c:forEach>
                                    </select>
                                    <form:errors path="formulario" cssStyle="color: red;"/>
                                </div>
                                <div class="col-6 pt-3">
                                    <label for="conUbicacionesPreAsignadas" class="control-label pt-4">CON CLIENTES:</label>
                                    <form:checkbox path="conUbicacionesPreAsignadas" id="conUbicacionesPreAsignadas" cssClass="mx-3" value="true" checked="true"/>
                                </div>

                                <div class="col-6">
                                    <label for="select-relevamiento" class="control-label pt-2">Ruta a copiar clientes:</label>
                                    <select id="select-relevamiento" name="idRelevamientoToCopy" class="form-control selectStyle" data-live-search="true">
                                        <option value="-1"> No copiar</option>
                                        <c:forEach items="${relevamientosToCopy}" var="bo" varStatus="status">
                                            <option value="${bo.id}">${bo.nombreRelevamiento}</option>
                                        </c:forEach>
                                    </select>
                                    <form:errors path="idRelevamientoToCopy" cssStyle="color: red;"/>
                                </div>
                                <c:if test="${not empty project}">
                                    <form:input  type="hidden" path="project" value="${project.id}" cssClass="form-control"/>
                                </c:if>
                                <form:input  type="hidden" path="bajaLogica" value="0" cssClass="form-control"/>
                                <form:input  type="hidden" path="estado" value="1" cssClass="form-control"/>
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
                                        <h5>&iquest;Desea guardar una nueva ruta?</h5>
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
<script>
    var element = document.getElementById("auditapp");
    element.classList.add("active");

</script>