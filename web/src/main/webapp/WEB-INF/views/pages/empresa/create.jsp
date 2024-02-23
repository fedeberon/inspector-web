<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
        <form:form action="save" autocomplete="off" modelAttribute="mapEmpresa" method="post">
                <div class="row ml-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Descripcion</label>
                                    <form:input  path="descripcion" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese una nueva empresa"/>
                                    <form:errors path="descripcion" cssStyle="color: red;"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Telefono</label>
                                    <form:input  path="telefono" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese un nuevo telefono"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">E-mail</label>
                                    <form:input  path="mail" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese un nuevo e-mail"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Logo</label>
                                    <form:input  path="logo" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese un nuevo logo"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Orden</label>
                                    <form:input  path="orden" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese un nuevo orden"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">Html</label>
                                    <form:input  path="html" cssClass="form-control" id="street1_id" name="street1" placeholder="Ingrese un nuevo html"/>
                                </div>
                                <div class="col-6">
                                    <label for="street1_id" class="control-label">GP+</label>
                                    <form:input  path="GPMas" cssClass="form-control" id="street1_id" name="street1" placeholder="Nuevo cliente: true / false"/>
                                </div>
                                <form:input type="hidden" path="bajaLogica" cssClass="form-control" id="street1_id" name="street1" value="false"/>
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
                                        <h5>&iquest;Desea guardar una nueva empresa?</h5>
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

