<%--
  Created by IntelliJ IDEA.
  User: erwin
  Date: 15/03/21
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<style>
    #rendered-form {
        max-width: 90%;
        margin: auto;
        border-style: outset;
        border-radius: 5px;
        padding: 10px;
    }
</style>
<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header">
                        <h4 class="card-title">Formularios</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="dataTableToCompleteList" class="display" style="width:100%">
                            <thead>
                            <th>ID</th>
                            <th class="text-center">Editar</th>
                            <th class="text-center">Borrar</th>
                            <th>Nombre</th>
                            <th class="text-center">Formulario</th>
                            </thead>

                            <tbody>

                            <c:forEach items="${formularios}" var="bo">

                            <tr>
                                <td><a href="/formulario/${bo.id}">${bo.id}</a></td>
                                <td class="text-center">
                                    <a href="<c:url value='/formulario/update?id=${bo.id}'/>"/>
                                    <img src="/resources/assets/img/icons/edit2.png" alt="">
                                </td>
                                <td class="text-center">
                                    <a href="<c:url value='/formulario/bajaLogica?id=${bo.id}'/>"/>
                                    <img src="/resources/assets/img/icons/delete.png" alt="">
                                </td>
                                <td>${bo.nombreFormulario}</td>
                                <td class="text-center">
                                    <div class="text-center cursorPointer" onclick="showForm(${bo.id})" title="Ver formulario">
                                        <input type="hidden" id="data-${bo.id}" value='${bo.formulario}'>
                                        <i class="fas fa-eye"></i>
                                    </div>
                                </td>
                            </tr>

                            </c:forEach>
                        </table>

                        <div class="row pt-4 px-2">
                            <div class="col-6">
                                <tags:paginador page="${mapPoiRequest.page}" formName="myWrapper"/>

                                <a href="create" class="btn btn-primary btn-fill"><i class="fas fa-plus"></i>&nbsp;Nuevo</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="modal-show-form" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content" style="height: 50%; overflow-y: scroll; margin-top: 0px;">
                <div class="modal-header">
                    <strong class="modal-title" id="exampleModalLabel">Items del formulario:</strong>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div id="rendered-form"></div>
                </div>
                <div class="modal-footer"></div>
            </div>
        </div>
    </div>

</div>

<script src="<c:url value='/resources/assets/js/plugins/FormBuilder/form-builder.min.js'/>"></script>
<script src="<c:url value='/resources/assets/js/plugins/FormBuilder/form-render.min.js'/>"></script>
<script src="<c:url value="/resources/assets/js/formBuilder.js"/>"></script>

<script>
    var element = document.getElementById("auditapp");
    element.classList.add("active");

    $(document).ready(function(){

        var valueOfSearchingDataTable = sessionStorage['formularioSearchDataTable'] || '';
        $('.dataTables_filter input[type="search"]').val(valueOfSearchingDataTable).keyup();

        $('.dataTables_filter input').unbind().keyup(function(e) {

            var valueSearchDataTable = $(this).val();
            if (valueSearchDataTable.length>=1) {
                dataTableToCompleteList.search(valueSearchDataTable).draw();
                sessionStorage['formularioSearchDataTable'] = valueSearchDataTable;

            } else {
                dataTableToCompleteList.search('').draw();
                sessionStorage['formularioSearchDataTable'] = "";
            }
        });

    });
</script>