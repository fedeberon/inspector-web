<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="modal fade" id="searchModalUbiAct" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content" style="margin-top: 0px">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Filtros</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form:form action="search" modelAttribute="myWrapper" id="searchModalFilterUbiAct" name="searchModalFilterUbiAct">

                <div class="modal-body row">

                    <div class="form-group col-6" style="padding-left: 6px">
                        <label>Id Ubicacion</label>
                        <input id="idSearchUbiAct" name="ubicacionActualizacionRequest.idsSearch" autocomplete="off"  class="form-control filterInput" placeholder="Ingrese IDs separados por comas"/>
                    </div>

                    <div class="form-group col-6">
                        <label for="maxResultsUbiAct">Cantidad resultados</label>
                        <select id="maxResultsUbiAct" class="form-control" name="ubicacionActualizacionRequest.maxResults">
                            <option value="-1">Todos</option>
                            <option value="10" selected>10</option>
                            <option value="20">20</option>
                            <option value="50">50</option>
                        </select>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>

                    <button name="paginate" onclick="onSubmit('searchModalFilterUbiAct')" class="btn btn-primary">Buscar</button>
                </div>

            </form:form>
        </div>
    </div>
</div>