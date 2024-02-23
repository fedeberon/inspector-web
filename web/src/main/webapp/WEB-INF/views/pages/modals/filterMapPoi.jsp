<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="modal fade" id="filterMapPoi" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content" style="margin-top: 0px">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Filtros</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form:form action="search" modelAttribute="myWrapper" id="filterFormMapPoi" name="filterFormMapPoi">

                <div class="modal-body row">

                    <div class="form-group col-6">
                        <label for="select-poiSectores">Pois Sectores</label>
                        <select id="select-poiSectores" data-done-button="true" class="form-control" multiple="multiple" data-live-search="true" data-actions-box="true" title="Seleccione un sector">
                            <c:forEach items="${poiSectores}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="select-poiEntidades">Pois Entidad</label><button type="button" style="float:right" class="btn btn-sm btn-primary" onclick="actualizarEntidades()" title="Actualizar entidades"><i class="fas fa-redo-alt"></i></button>
                        <select id="select-poiEntidades" name="mapPoiRequest.mapPoiEntidad" data-done-button="true" class="form-control" multiple data-live-search="true" data-actions-box="true" title="Seleccione una entidad">
                            <c:forEach items="${poiEntidades}" var="bo">
                                <option value="${bo.descripcion}">${bo.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-6">
                        <label for="maxResultsPoi">Cantidad resultados</label>
                        <select id="maxResultsPoi" class="form-control" name="mapPoiRequest.maxResults">
                            <option value="-1">Todos</option>
                            <option value="10" selected>10</option>
                            <option value="20">20</option>
                            <option value="50">50</option>
                        </select>
                    </div>

                    <div class="col load mt-5" style="display: none; position:absolute; top: 20%;">
                        <div class="col-md-12">
                            <div class="loader">
                                <div class="loader-inner box1"></div>
                                <div class="loader-inner box2"></div>
                                <div class="loader-inner box3"></div>
                            </div>
                        </div>
                        <div class="col-md-12"><h5 id="info-loader" style="text-align: center"></h5></div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>

                    <button name="paginate" onclick="onSubmit('filterFormMapPoi')" class="btn btn-primary">Buscar</button>
                </div>

            </form:form>
        </div>
    </div>
</div>