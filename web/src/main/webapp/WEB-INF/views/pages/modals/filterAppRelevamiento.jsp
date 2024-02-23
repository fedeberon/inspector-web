<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="modal fade" id="filterAppRelevamiento" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content" style="margin-top: 0px">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Filtros</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form:form action="search" modelAttribute="relevamientoRequest" id="filterFormAppRelevamiento" name="filterFormAppRelevamiento">

                <div class="modal-body row">

                    <div class="form-group col-6">
                        <label for="select-estado">Estado</label>
                        <select id="select-estado" name="idEstado" data-done-button="true" class="form-control" data-actions-box="true" title="Seleccione un estado">
                            <c:forEach items="${estados}" var="bo">
                                <option value="${bo.id}">${bo.descripcion}</option>
                            </c:forEach>
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

                    <button name="paginate" onclick="onSubmit('filterFormAppRelevamiento')" class="btn btn-primary">Buscar</button>
                </div>

            </form:form>
        </div>
    </div>
</div>