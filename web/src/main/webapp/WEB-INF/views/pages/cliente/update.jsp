<%@ page   contentType="text/html; charset=ISO-8859-15" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style>
    .card #updateCliente [class*="col-"]{
        padding: 6px;
    }
</style>
<div class="content">
    <div class="col-12">
        <div class="card">
            <div class="row m-0 mt-3">
                <form:form action="save" modelAttribute="updateCliente" method="post"  cssClass="w-100 m-0">
                <form:hidden path="id" value='${updateCliente.id}'/>
                <div class="col-md-12 m-0">
                    <div class="form-group m-0">
                        <div class="row m-0">
                            <div class="col-6">
                                <label for="nombre" class="control-label">Nombre</label>
                                <form:input  path="nombre" cssClass="form-control" id="nombre" name="nombre" placeholder="Ingrese un nuevo nombre"/>
                                <form:errors path="nombre" cssStyle="color: red;"/>
                            </div>
                            <div class="col-6">
                                <label for="agencia" class="control-label">Agencia</label>
                                <form:input  path="agencia" cssClass="form-control" id="agencia" name="agencia" placeholder="Ingrese una nueva agencia"/>
                            </div>
                        </div>
                        <div class="row m-0">
                            <div class="col-6">
                                <label for="direccion" class="control-label">Direcci&oacute;n</label>
                                <form:input  path="direccion" cssClass="form-control" id="direccion" name="direccion" placeholder="Ingrese una nueva dirección"/>
                            </div>
                            <div class="col-6">
                                <label for="email" class="control-label">Email</label>
                                <form:input  path="email" cssClass="form-control" id="email" name="email" placeholder="Ingrese un nuevo email"/>
                            </div>
                        </div>
                        <div class="row m-0">
                            <div class="col-6">
                                <label for="telefono" class="control-label">Tel&eacute;fono</label>
                                <form:input  path="telefono" cssClass="form-control" id="telefono" name="telefono" placeholder="Ingrese un nuevo teléfono"/>
                            </div>
                            <div class="col-6">
                                <label for="ejecutivoDeCuenta" class="control-label">Ejecutivo de cuenta</label>
                                <form:input  path="ejecutivoDeCuenta" cssClass="form-control" id="ejecutivoDeCuenta" name="ejecutivoDeCuenta" placeholder="Ingrese un nuevo ejecutivo de cuenta"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
                </form:form>
                <div class="col-md-12 p-0 m-0 px-3 mb-3">
                    <a href="list" class="btn btn-light pull-left m-0"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>
                    <button type="button" class="btn btn-secondary btn-fill pull-right m-0" data-toggle="modal" data-target="#exampleModal">
                        Guardar
                    </button>
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
                                    <button type="submit" form="updateCliente" class="btn btn-secondary btn-fill">Guardar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>