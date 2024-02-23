<%--
  Created by IntelliJ IDEA.
  User: erwin
  Date: 01/09/20
  Time: 09:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Clientes Actualizaciones Especiales</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <form:form action="search" modelAttribute="myWrapper" name="myWrapper" method="post">
                            <input type="hidden" name="page" value="${mapUbiActEspecialRequest.page}"/>

                            <table id="dataTableOrderDesc" class="display" style="width:100%">
                                <thead>
                                <th>ID Cliente</th>
                                <th class="text-center">Editar</th>
                                <th class="text-center">Baja/Alta</th>
                                <th>Operacion</th>
                                <th>Procesado</th>
                                <th>Fecha Alta</th>
                                </thead>

                                <tbody>

                                <c:forEach items="${ubicacionActEspeciales}" var="bo">

                                    <tr>
                                        <td><a href="/ubicacionActEspecial/show?id=${bo.id.mapUbicacionId}&fechaAlta=${bo.id.fechaAlta}">${bo.id.mapUbicacionId}</a></td>
                                        <td class="text-center">
                                            <a href="<c:url value='/ubicacionActEspecial/update?id=${bo.id.mapUbicacionId}&fechaAlta=${bo.id.fechaAlta}'/>"/>
                                            <img src="/resources/assets/img/icons/edit2.png" alt="">
                                        </td>
                                        <td class="text-center">
                                            <a ${bo.operacion == "B" ? 'class="d-none"' : ''} href="<c:url value='/ubicacionActEspecial/dropState?id=${bo.id.mapUbicacionId}&fechaAlta=${bo.id.fechaAlta}'/>"/>
                                            <img ${bo.operacion == "B" ? 'class="d-none"' : ''} src="/resources/assets/img/icons/delete.png" title="Dar de baja" alt="">
                                            <a ${bo.operacion == "A" || bo.operacion == "M" ? 'class="d-none"' : ''} href="<c:url value='/ubicacionActEspecial/upState?id=${bo.id.mapUbicacionId}&fechaAlta=${bo.id.fechaAlta}'/>"/>
                                            <img ${bo.operacion == "A" || bo.operacion == "M" ? 'class="d-none"' : ''} src="/resources/assets/img/icons/arrowUp2.png" title="Dar de alta" alt="">
                                        </td>
                                        <td>${bo.operacion}</td>
                                        <td>${bo.procesado}</td>
                                        <td>${bo.id.fechaAlta}</td>
                                    </tr>

                                </c:forEach>

                                </tbody>
                            </table>
                            <div class="row pt-4 px-2">
                                <div class="col-6">

                                    <%--<tags:paginador page="${page}" formName="myWrapper" noMorePages="${Integer.valueOf(ubicacionActEspeciales.size() / 10) + Integer.valueOf(ubicacionActEspeciales.size() % 10 >= 1 ? 1 : 0)}"/>--%>
                                    <tags:paginador page="${mapUbiActEspecialRequest.page}" formName="myWrapper"/>

                                    <a href="../tablas" class="btn btn-secondary btn-fill pull-left mr-3"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>

                                    <a href="create" class="btn btn-primary btn-fill"><i class="fas fa-plus"></i>&nbsp;Nuevo</a>

                                </div>
                            </div>

                            <%--Modal filtrar campos--%>
                            <jsp:include page="../modals/filterUbicacionActEspecial.jsp"/>

                        </form:form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<li class="nav-item more-options" style="display: none">
    <a href="#" class="nav-link" onclick="openModal('filterUbiActEspecial')">
        <i class="nc-icon nc-zoom-split"></i>
        <span class="d-lg-block">&nbsp;Buscar</span>
    </a>
</li>
<script>
    var element = document.getElementById("geoplanning");
    element.classList.add("active");

    $(document).ready(function(){

        var valueOfSearchingDataTable = sessionStorage['ubiActEspecialSearchDt'] || '';
        $('.dataTables_filter input[type="search"]').val(valueOfSearchingDataTable).keyup();

        $('.dataTables_filter input').unbind().keyup(function(e) {

            var valueSearchDataTable = $(this).val();
            if (valueSearchDataTable.length>=1) {
                table.search(valueSearchDataTable).draw();
                sessionStorage['ubiActEspecialSearchDt'] = valueSearchDataTable;

            } else {
                table.search('').draw();
                sessionStorage['ubiActEspecialSearchDt'] = "";
            }
        });

    });
</script>
