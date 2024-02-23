<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<script src="<c:url value='/resources/assets/js/plugins/FileSaver/FileSaver.js'/>"></script>
<script>
    var element = document.getElementById("users");
    element.classList.add("active");

    $(document).ready(function(){

        var valueOfSearchingDataTable = sessionStorage['usuarioSearchDataTable'] || '';
        $('.dataTables_filter input[type="search"]').val(valueOfSearchingDataTable).keyup();

        $('.dataTables_filter input').unbind().keyup(function(e) {

            var valueSearchDataTable = $(this).val();
            if (valueSearchDataTable.length>=1) {
                dataTableToCompleteList.search(valueSearchDataTable).draw();
                sessionStorage['usuarioSearchDataTable'] = valueSearchDataTable;

            } else {
                dataTableToCompleteList.search('').draw();
                sessionStorage['usuarioSearchDataTable'] = "";
            }
        });

    });
</script>

<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header">
                        <h4 class="card-title">Usuarios</h4>
                        <a class="" href="listComplete"><i class="fas fa-sort-amount-up"></i> Usuarios activos/inactivos</a>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="dataTableToCompleteList" class="display" style="width:100%">
                            <thead>
                                <th>ID</th>
                                <th>Fotos</th>
                                <th class="text-center">Editar</th>
                                <th class="text-center">Baja/Alta</th>
                                <th>Usuario</th>
                                <th>Contrase&ntilde;a</th>
                                <th>Nombre Completo</th>
                                <th>Email</th>
                                <th>Telefono</th>
                                <th>Estado</th>
                                <th>Tipo de usuario</th>
                                <th>Empresa</th>
                                <th>C.Inversion</th>
                                <th>C.Auditoria</th>
                                <th>C.Mapping</th>
                                <th>Fecha Vencimiento</th>
                            </thead>

                            <tbody>

                            <c:forEach items="${usuarios}" var="bo">

                                <tr>
                                    <td><a href="/usuario/${bo.id}">${bo.id}</a></td>
                                    <td>
                                        <div ${bo.tipoUsuario.id == 1 || bo.tipoUsuario.id == 5 ? 'class="text-center cursorPointer"' : 'class="d-none"'} onclick="initCarouselAuditapp('${bo.id}' )">
                                            <i class="fas fa-camera"></i>
                                        </div>
                                        <div ${bo.tipoUsuario.id == 1 || bo.tipoUsuario.id == 5 ? 'class="d-none"' : 'class="text-center"'}>
                                            <span> - </span>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <a href="<c:url value='/usuario/update?id=${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/edit2.png" alt="">
                                    </td>
                                    <td class="text-center">
                                        <a ${bo.estado == "B" ? 'class="d-none"' : ''} href="<c:url value='/usuario/dropState?id=${bo.id}'/>"/>
                                        <img ${bo.estado == "B" ? 'class="d-none"' : ''} src="/resources/assets/img/icons/delete.png" alt="">
                                        <a ${bo.estado == "A" || bo.estado == "M" ? 'class="d-none"' : ''} href="<c:url value='/usuario/upState?id=${bo.id}'/>"/>
                                        <img ${bo.estado == "A" || bo.estado == "M" ? 'class="d-none"' : ''} src="/resources/assets/img/icons/arrowUp2.png" alt="">
                                    </td>
                                    <td>${bo.username}</td>
                                    <td>${bo.password}</td>
                                    <td>${bo.nombreCompleto}</td>
                                    <td>${bo.eMail}</td>
                                    <td>${bo.telefono}</td>
                                    <td>${bo.estado}</td>
                                    <td>${bo.tipoUsuario.descripcion}</td>
                                    <td>${bo.cargo}</td>
                                    <td>${bo.contratoInv.descripcion}</td>
                                    <td>${bo.contratoAud.descripcion}</td>
                                    <td>${bo.contratoMap.descripcion}</td>
                                    <td>${bo.fechaVencimiento}</td>
                                </tr>

                            </c:forEach>

                            </tbody>
                        </table>
                        <div class="row pt-4 px-2">
                            <div class="col-8">
                                <a href="create" class="btn btn-primary btn-fill"><i class="fas fa-plus"></i>&nbsp;Nuevo</a>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

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
                <h4 style="margin-top: 0;" id="myModalLabel">&iquest;Esta seguro que desea eliminar esta imagen?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-fill btn-default" onclick="deleteFile()">Si</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="modal-folders" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content" style="height: 50%; overflow-y: scroll; margin-top: 0px;">
            <div class="modal-header">
                <strong class="modal-title" id="exampleModalLabel">Gestor de archivos</strong>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="col load mt-5" style="display: none; position:absolute; z-index: 1000; top: 123px;">
                    <div class="col-md-12">
                        <div class="loader">
                            <div class="loader-inner box1"></div>
                            <div class="loader-inner box2"></div>
                            <div class="loader-inner box3"></div>
                        </div>
                    </div>
                    <div class="col-md-12"><h5 id="info-loader" style="text-align: center"></h5></div>
                </div>
                <div class="data-auditapp d-none"></div>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Carpetas</th>
                                <th>Descargar</th>
                            </tr>
                        </thead>
                        <tbody id="folders">

                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
