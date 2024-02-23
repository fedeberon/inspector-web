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
            <%--@elvariable id="updateStock" type="com.ideaas.services.domain.Stock"--%>
            <form:form action="save" modelAttribute="updateStock" method="post">
                <form:hidden path="idStock" value='${updateStock.idStock}'/>
                <form:hidden path="mapElemento.id" value='${updateStock.mapElemento.id}'/>
                <form:hidden path="mapEmpresa.id" value='${updateStock.mapEmpresa.id}'/>
                <div class="row ml-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <%-- <div class="row">
                                <div class="col-3">
                                    <h4>Suma minima de ubicaciones: ${minimumAmountOfLocations}</h4>
                                </div>
                            </div> --%>
                            <div class="row">
                                <div class="form-group">
                                        <div class="col-6">
                                            <label class="control-label pt-2">Cantidad de dispositivos en dep&oacute;sito</label>
                                            <form:input  path="cantDispositivosDeposito" cssClass="form-control" id="cant_dispositivo_deposito" name="cant_dispositivo_deposito" placeholder="Ingrese la cantidad de dispositivos que est&aacute;n en dep&oacute;sito"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-6">
                                        <label class="control-label pt-2">Cantidad de dispositivos en reparaci&oacute;n</label>
                                        <form:input  path="cantDispositivosReparacion" cssClass="form-control" id="cant_dispositivo_reparacion" name="cant_dispositivo_reparacion" placeholder="Ingrese la cantidad de dispositivos que est&aacute;n en reparacion"/>
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
                                        <h5>&iquest;Desea confirmar los cambios?</h5>
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
            </form:form>
        </div>
    </div>
</div>
    <script>
        $(document).ready(function() {
            $('.minus').click(function () {
                var $input = $(this).parent().find('input');
                var count = parseInt($input.val()) - 1;
                count = count < 1 ? 1 : count;
                $input.val(count);
                $input.change();
                return false;
            });
            $('.plus').click(function () {
                var $input = $(this).parent().find('input');
                $input.val(parseInt($input.val()) + 1);
                $input.change();
                return false;
            });
        });
    </script>


<%--Modal error stock --%>
<jsp:include page="../modals/stockError.jsp"/>