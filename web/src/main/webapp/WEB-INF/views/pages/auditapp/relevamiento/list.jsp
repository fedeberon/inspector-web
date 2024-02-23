<%--
Created by IntelliJ IDEA.
User: erwin
Date: 15/03/21
Time: 11:20
To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<div class="content">
    <div class="container-fluid">

        <div class="col load mt-5" style="display: none; position:absolute; top: 123px; z-index: 2000;">
            <div class="col-md-12">
                <div class="loader">
                    <div class="loader-inner box1"></div>
                    <div class="loader-inner box2"></div>
                    <div class="loader-inner box3"></div>
                </div>
            </div>
            <div class="col-md-12"><h5 id="info-loader" style="text-align: center"></h5></div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header">
                        <h4 class="card-title">Rutas</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="dataTableToCompleteList" class="display" style="width:100%">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th class="text-center">Editar</th>
                                    <th class="text-center">Borrar</th>
                                    <th style="width: 50%;">Fotos</th>
                                    <th>Asignar</th>
                                    <c:if test="${empty projectId}">
                                        <th>Proyecto</th>
                                    </c:if>
                                    <th>Ruta</th>
                                    <th>Inspector</th>
                                    <th>Formulario</th>
                                    <th>Estado</th>
                                    <th>Clientes pre-asignados</th>
                                    <th>Fecha de asignacion</th>
                                    <th>Clientes</th>
                                    <th>Clientes asignados</th>
                                    <th>Reporte</th>
                                </tr>
                            </thead>

                            <tbody>
                               <c:forEach items="${relevamientos}" var="bo">
                                    <tr>
                                        <td><a href="/relevamiento/${bo.id}">${bo.id}</a></td>
                                        <td class="text-center">
                                            <a href="<c:url value='/relevamiento/update?id=${bo.id}'/>"/>
                                            <img src="/resources/assets/img/icons/edit2.png" alt="">
                                        </td>
                                        <td class="text-center">
                                            <a href=# data-toggle="modal" data-target="#modal-delete-relevamiento"data-href="<c:url value='/relevamiento/delete?id=${bo.id}'/>"/>
                                            <img src="/resources/assets/img/icons/delete.png" alt="">
                                        </td>
                                        <td class="row" style="justify-content: space-around">
                                            <div class="text-center cursorPointer" onclick="initCarouselOfRelevamiento('${bo.id}')">
                                                <i class="fas fa-camera"></i>
                                            </div>
                                            -
                                            <div class="text-center cursorPointer" onclick="downloadFilesOfRelevamiento('${bo.id}' , '${bo.nombreRelevamiento}')">
                                                <i class="fas fa-file-download"></i>
                                            </div>
                                        </td>
                                        <td class="text-center">
                                            <a ${bo.estado.id == 1 ? 'class="d-none"' : ''} href="<c:url value='/relevamiento/dropState?id=${bo.id}'/>" title="Cambiar a no asignado"/>
                                            <img ${bo.estado.id == 1 ? 'class="d-none"' : ''} src="/resources/assets/img/icons/arrowDown2.png" alt="">
                                            <a ${bo.estado.id == 2 ? 'class="d-none"' : ''} href="<c:url value='/relevamiento/upState?id=${bo.id}'/>" title="Cambiar a asignado"/>
                                            <img ${bo.estado.id == 2 ? 'class="d-none"' : ''} src="/resources/assets/img/icons/arrowUp2.png" alt="">
                                        </td>
                                        <c:if test="${empty projectId}">
                                            <td>${bo.project.name}</td>
                                        </c:if>
                                        <td>${bo.nombreRelevamiento}</td>
                                        <td>${bo.usuario.username}</td>
                                        <td>${bo.formulario.nombreFormulario}</td>
                                        <td>${bo.estado.descripcion}</td>
                                        <td><div class="text-center">${bo.conUbicacionesPreAsignadas ? 'Si' : 'No'}</div></td>
                                        <td class="text-center">${bo.fechaAsignacion}</td>
                                        <td>
                                            <div class="text-center cursorPointer"  title="Ver ubicaciones asignadas">
                                                <a href="<c:url value='/appUbicacionRelevamiento/list/${bo.id}'/>"><i class="fas fa-map-marker-alt"></i></a>
                                            </div>
                                        </td>
                                        <td class="row" style="justify-content: space-around">
                                            <div class="text-center">${bo.cantidadDeUbicacionesByRelevamiento}</div>
                                            <div class="text-right cursorPointer" title="Revisar ubicaciones">
                                                <a href="<c:url value='/appUbicacionRevisar/list/${bo.id}'/>"><i class="fas fa-info-circle"></i></a>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="text-center cursorPointer" onclick="createReport(${bo.id})" title="Descargar datos">
                                                <i class="fas fa-file-download"></i>
                                            </div>
                                        </td>    
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <c:if test="${not empty projectId}">
                            <div class="row pt-4 px-2">
                                <div class="col-6">
                                    <a class="btn btn-light" href="/proyecto/list"><i class="fas fa-angle-double-left pr-2"></i>Volver a proyectos</a>

                                    <a href="../create/${projectId}" class="btn btn-primary btn-fill"><i class="fas fa-plus"></i>&nbsp;Nuevo</a>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<%--Modal filtrar campos--%>
<jsp:include page="../../modals/filterAppRelevamiento.jsp"/>

<li class="nav-item more-options" style="display: none">
    <a href="#" class="nav-link" onclick="openModal('filterAppRelevamiento')">
        <i class="nc-icon nc-zoom-split"></i>
        <span class="d-lg-block">&nbsp;Buscar</span>
    </a>
</li>

<!-- Modal -->
<div class="modal fade confirm-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" id="modal-delete-file">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <input type="hidden" id="fileToDelete"/>
            <div class="modal-header">
                <h5></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="files-auditapp d-none"></div>
                <h4 style="margin-top: 0;" id="myModalLabel">&iquest;Esta seguro que desea eliminar esta imagen?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-fill btn-default" onclick="deleteFile()">Si</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade confirm-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" id="modal-delete-relevamiento">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <%--<input type="hidden" id="fileToDelete"/>--%>
            <div class="modal-header">
                <h5></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="files-auditapp d-none"></div>
                <h4 style="margin-top: 0;">&iquest;Esta seguro que desea eliminar este relevamiento?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-dismiss="modal">No</button>
                <a class="btn btn-fill btn-default">Si</a>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value='/resources/assets/js/plugins/xlsx.full.min.js'/>"></script>
<script src="<c:url value='/resources/assets/js/plugins/FileSaver/FileSaver.js'/>"></script>

<script>
    var element = document.getElementById("auditapp");
    element.classList.add("active");

    $('#modal-delete-relevamiento').on('show.bs.modal', function(e) {
        $(this).find('.btn-default').attr('href', $(e.relatedTarget).data('href'));

        <%--
        $('.debug-url').html('Delete URL: <strong>' + $(this).find('.btn-ok').attr('href') + '</strong>');--%>
    });

    $(document).ready(function(){

        var valueOfSearchingDataTable = sessionStorage['relevamientoSearchDataTable'] || '';
        $('.dataTables_filter input[type="search"]').val(valueOfSearchingDataTable).keyup();

        $('.dataTables_filter input').unbind().keyup(function(e) {

            var valueSearchDataTable = $(this).val();
            if (valueSearchDataTable.length>=1) {
                dataTableToCompleteList.search(valueSearchDataTable).draw();
                sessionStorage['relevamientoSearchDataTable'] = valueSearchDataTable;

            } else {
                dataTableToCompleteList.search('').draw();
                sessionStorage['relevamientoSearchDataTable'] = "";
            }
        });

    });
</script>