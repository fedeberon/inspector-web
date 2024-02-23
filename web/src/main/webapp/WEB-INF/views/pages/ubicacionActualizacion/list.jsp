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
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.ideaas.services.bean.DateTimeUtil" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Clientes Actualizaciones</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <form:form action="search" modelAttribute="myWrapper" name="myWrapper" method="post">
                            <input type="hidden" name="page" value="${ubicacionActualizacionRequest.page}"/>

                            <table id="dataTableOrderDesc" class="display" style="width:100%">
                                <thead>
                                    <th>ID Ubicacion</th>
                                    <th class="text-center">Editar</th>
                                    <th class="text-center">Baja/Alta</th>
                                    <th>Operacion</th>
                                    <th>Fecha Operacion</th>
                                    <th>Procesado</th>
                                    <th>Evalua</th>
                                    <th>Fecha Alta</th>
                                </thead>

                                <tbody>

                                <c:forEach items="${ubicacionActualizaciones}" var="bo">

                                    <tr>
                                        <td><a href="/ubicacionActualizacion/show?id=${bo.id.mapUbicacionId}&fechaOperacion=${bo.id.fechaOperacion}">${bo.id.mapUbicacionId}</a></td>
                                        <td class="text-center">
                                            <a href="<c:url value='/ubicacionActualizacion/update?id=${bo.id.mapUbicacionId}&fechaOperacion=${DateTimeUtil.formatLocalDateTime(bo.id.fechaOperacion)}'/>"/>
                                            <img src="/resources/assets/img/icons/edit2.png" alt="">
                                        </td>
                                        <td class="text-center">
                                            <a ${bo.operacion == "B" ? 'class="d-none"' : ''} href="<c:url value='/ubicacionActualizacion/dropState?id=${bo.id.mapUbicacionId}&fechaOperacion=${DateTimeUtil.formatLocalDateTime(bo.id.fechaOperacion)}'/>"/>
                                            <img ${bo.operacion == "B" ? 'class="d-none"' : ''} src="/resources/assets/img/icons/delete.png" title="Dar de baja" alt="">
                                            <a ${bo.operacion == "A" || bo.operacion == "M" ? 'class="d-none"' : ''} href="<c:url value='/ubicacionActualizacion/upState?id=${bo.id.mapUbicacionId}&fechaOperacion=${DateTimeUtil.formatLocalDateTime(bo.id.fechaOperacion)}'/>"/>
                                            <img ${bo.operacion == "A" || bo.operacion == "M" ? 'class="d-none"' : ''} src="/resources/assets/img/icons/arrowUp2.png" title="Dar de alta" alt="">
                                        </td>
                                        <td>${bo.operacion}</td>
                                        <td>${DateTimeUtil.formatLocalDateTime(bo.id.fechaOperacion)}</td>
                                        <td>${bo.procesado}</td>
                                        <td>${bo.evalua}</td>
                                        <td>${DateTimeUtil.formatLocalDateTime(bo.fechaAlta)}</td>
                                    </tr>

                                </c:forEach>

                                </tbody>
                            </table>
                            <div class="row pt-4 px-2">
                                <div class="col-6">
<%--                                        <tags:paginador page="${page}" formName="myWrapper" noMorePages="${Integer.valueOf(ubicacionActualizaciones.size() / 10) + Integer.valueOf(ubicacionActualizaciones.size() % 10 >= 1 ? 1 : 0)}"/>--%>
                                        <tags:paginador page="${ubicacionActualizacionRequest.page}" formName="myWrapper"/>

                                        <a href="../tablas" class="btn btn-secondary btn-fill pull-left mr-3"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>

                                        <a href="create" class="btn btn-primary btn-fill"><i class="fas fa-plus"></i>&nbsp;Nuevo</a>
                                </div>
                            </div>

                            <%--Modal filtrar campos--%>
                            <jsp:include page="../modals/filterUbicacionAct.jsp"/>

                        </form:form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<li class="nav-item more-options" style="display: none">
    <a href="#" class="nav-link" onclick="openModal('searchModalUbiAct')">
        <i class="nc-icon nc-zoom-split"></i>
        <span class="d-lg-block">&nbsp;Buscar</span>
    </a>
</li>
<script>
    var element = document.getElementById("geoplanning");
    element.classList.add("active");

    $(document).ready(function(){

        var valueOfSearchingDataTable = sessionStorage['ubiActSearchDataTable'] || '';
        $('.dataTables_filter input[type="search"]').val(valueOfSearchingDataTable).keyup();

        $('.dataTables_filter input').unbind().keyup(function(e) {

            var valueSearchDataTable = $(this).val();
            if (valueSearchDataTable.length>=1) {
                table.search(valueSearchDataTable).draw();
                sessionStorage['ubiActSearchDataTable'] = valueSearchDataTable;

            } else {
                table.search('').draw();
                sessionStorage['ubiActSearchDataTable'] = "";
            }
        });

    });
</script>
