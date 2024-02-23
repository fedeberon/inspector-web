<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content">
<div class="col-12">
<div class="card">
        <form:form action="save" modelAttribute="updatePoiEntidad" method="post">
            <form:hidden path="id" value='${updatePoiEntidad.id}'/>
            <div class="row ml-3">
                <div class="col-md-11">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-6">
                                <label class="control-label pt-2"><strong>POI Sector</strong></label>
                                <select class="form-control" id="street1_id" name="mapPoiSector.id">
                                    <c:forEach items="${poiSectores}" var="bo" varStatus="status">
                                        <option ${bo.descripcion == updatePoiEntidad.mapPoiSector.descripcion ? 'selected' : ''} value="${bo.id}">${bo.descripcion}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-6">
                                <label class="control-label pt-2"><strong>Descripcion</strong></label>
                                <form:input path="descripcion" cssClass="form-control" id="street1_id" name="street1" placeholder=""/>
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
                                    <button type="#" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
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
