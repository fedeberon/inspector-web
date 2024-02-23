<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content">
<div class="col-12">
<div class="card">
<form:form action="editElemento" modelAttribute="updateElemento" method="post">
    <form:hidden path="id" value='${elemento.id}'/>

    <div class="form-group ml-3 mr-3">
            <div class="row">
                <div class="col-6">
                    <label for="street1_id" class="control-label pt-2"><strong>Descripcion</strong></label>
                    <form:input value='${elemento.descripcion}' path="descripcion" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                </div>
                <div class="col-6">
                    <label for="street1_id" class="control-label pt-2"><strong>Pano</strong></label>
                    <form:input  path="panos" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <label for="street2_id" class="control-label pt-2"><strong>Medio</strong></label>
                    <select class="form-control" id="street2_id" name="mapMedio.id">
                        <c:forEach items="${medios}" var="bo" varStatus="status">
                            <option ${bo.descripcion == updateElemento.mapMedio.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-6">
                    <label for="street1_id" class="control-label pt-2"><strong>Formato</strong></label>
                    <select class="form-control" id="street1_id" name="mapFormato.id">
                        <c:forEach items="${formatos}" var="bo" varStatus="status">
                            <option ${bo.descripcion == updateElemento.mapFormato.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <label for="street1_id" class="control-label pt-2"><strong>Acumula Datos</strong></label>
                    <form:input  path="acumulaDatos" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                </div>
                <div class="col-6">
                    <label for="street1_id" class="control-label pt-2"><strong>Evalua</strong></label>
                    <form:input  path="evalua" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
                </div>
            </div>
            <div class="row">
            <div class="col-6">
                <label for="street1_id" class="control-label pt-2"><strong>Metros de contacto</strong></label>
                <form:input  path="metrosContacto" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
            </div>
            <div class="col-6">
                <label for="street1_id" class="control-label pt-2"><strong>Coeficiente</strong></label>
                <form:input  path="coeficiente" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
            </div>
        </div>
             <form:input  type="hidden" path="fechaAlta" value='${elemento.fechaAlta}' cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
    </div>

    <a href="list" class="btn btn-light pull-left ml-3"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>


    <!-- Button trigger modal -->
    <button type="button" class="btn btn-secondary pull-right mr-3" data-toggle="modal" data-target="#exampleModal">
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
                    <button type="#" class="btn btn-light" data-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-secondary btn-fill">Guardar</button>
                </div>
            </div>
        </div>
    </div>




</form:form>
</div>
</div>
</div>