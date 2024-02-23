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
            <form:form action="saveUpdate" modelAttribute="updateFormulario"  method="post">
                <form:hidden path="id" value='${relevamiento.id}'/>
                <div class="row ml-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-6">
                                    <label class="control-label pt-2">Nombre del formulario</label>
                                    <form:input path="nombreFormulario" cssClass="form-control" required="true"/>
                                    <form:errors path="nombreFormulario" cssStyle="color: red;"/>
                                </div>
                                <div class="col-12 text-center">
                                    <form:input path="formulario" type="hidden" cssClass="form-control" id="items" placeholder="*Obligatorio"/>
                                    <form:errors path="formulario" cssStyle="color: red;"/>
                                </div>
                            </div>
                        </div>
                        <a href="list" class="btn btn-light pull-left ml-3"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>

                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-secondary btn-fill pull-right mr-3" data-toggle="modal" data-target="#exampleModal">
                            Guardar
                        </button>

                        <button type="button" id="createFormItems" class="btn btn-secondary btn-fill pull-right mr-3" data-toggle="modal" data-target="#modal-create-form">
                            Ver formulario
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

                        <!-- Modal -->
                        <div class="modal fade" id="modal-create-form" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg" role="document">
                                <div class="modal-content" style="height: 50%; overflow-y: scroll; margin-top: 0px;">
                                    <div class="modal-header">
                                        <strong class="modal-title" id="exampleModalLabel">Items del formulario:</strong>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div id="fb-editor"></div>
                                        <div id="rendered-form" class="d-none"></div>
                                    </div>
                                    <div class="modal-footer" style="justify-content: flex-end;">
                                        <div id="fb-buttons" class="d-none">
                                            <button type="button" class="btn btn-fill btn-danger" onclick="goBackToEdit()">Editar</button>
                                            <button type="button" class="btn btn-fill btn-success" onclick="loadForm()">Guardar</button>
                                            <button type="button" class="btn btn-fill btn-secundary" onclick="closeBuilder()" aria-label="Cerrar ventana">Cerrar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="clearfix"></div>
                <form:input type="hidden" path="bajaLogicaFormulario" cssClass="form-control" value="false"/>
            </form:form>
        </div>
    </div>
</div>

<script src="<c:url value='/resources/assets/js/plugins/FormBuilder/form-builder.min.js'/>"></script>
<script src="<c:url value='/resources/assets/js/plugins/FormBuilder/form-render.min.js'/>"></script>
<script src="<c:url value='/resources/assets/js/formBuilder.js'/>"></script>

<script>
    var element = document.getElementById("auditapp");
    element.classList.add("active");

    $(document).ready(function() {
        initFormBuilder();
    });
</script>