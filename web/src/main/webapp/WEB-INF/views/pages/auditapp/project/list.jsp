<%--
  Created by IntelliJ IDEA.
  User: guerr
  Date: 15/06/2022
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<div class="content">
    <div class="container-fluid">
        <div class="col load mt-5" style="display: none; position:absolute; z-index: 1000; top: 123px;">
            <div class="col-md-12">
                <div class="loader">
                    <div class="loader-inner box1"></div>
                    <div class="loader-inner box2"></div>
                    <div class="loader-inner box3"></div>
                </div>
            </div>
            <div class="col-md-12"><h5 style="text-align: center"></h5></div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header">
                        <h4 class="card-title">Proyectos</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="dataTableToCompleteList" class="display" style="width:100%">
                            <!--Columns go here-->
                            <thead>
                            <th>ID</th>
                            <th class="text-center">Editar</th>
                            <th class="text-center">Borrar</th>
                            <th>Nombre</th>
                            <th>Ingresar</th>
                            </thead>

                            <!--Rows go here-->
                            <tbody>
                            <%--@elvariable id="bo" type="com.ideaas.services.bean.MapReservationDTO"--%>
                            <c:forEach items="${projects}" var="bo" varStatus="status">
                                <tr>
                                    <td><a href="./show/${bo.id}">${bo.id}</a></td>
                                    <td class="text-center">
                                        <a href="<c:url value='/proyecto/update?id=${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/edit2.png" alt="">
                                    </td>
                                    <td class="text-center">
                                        <a href=# data-toggle="modal" data-target="#modal-delete-proyecto"data-href="<c:url value='/proyecto/delete?id=${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/delete.png" alt="">
                                    </td>
                                    <td>${bo.name}</td>
                                    <td>
                                        <a href="<c:url value='/relevamiento/list/${bo.id}'/>"><i class="fas fa-running"></i></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="row pt-4 px-2">
                            <div class="col-6">
                                <a href="create" class="btn btn-primary btn-fill"><i class="fas fa-plus"></i>&nbsp;Nuevo</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--Modal-->
<div class="modal fade confirm-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" id="modal-delete-proyecto">
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
                <h4 style="margin-top: 0;">&iquest;Esta seguro que desea eliminar este proyecto?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-dismiss="modal">No</button>
                <a class="btn btn-fill btn-default">Si</a>
            </div>
        </div>
    </div>
</div>
<script>
    $('#modal-delete-proyecto').on('show.bs.modal', function(e) {
        $(this).find('.btn-default').attr('href', $(e.relatedTarget).data('href'));
        <%--
        $('.debug-url').html('Delete URL: <strong>' + $(this).find('.btn-ok').attr('href') + '</strong>');--%>
    });
</script>
