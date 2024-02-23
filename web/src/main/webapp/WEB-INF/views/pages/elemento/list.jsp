<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Elemento</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="dataTableToCompleteList" class="display" style="width:100%">
                            <thead>
                                <th>ID</th>
                                <sec:authorize access="hasAnyRole(
                                            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                        ">
                                <th class="text-center">Editar</th>
                                <th class="text-center">Baja/Alta Logica</th>
                                </sec:authorize>
                                <th>Descripcion</th>
                                <th>Panos</th>
                                <th>Medio</th>
                                <th>Formato</th>
                                <sec:authorize access="hasAnyRole(
                                            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                        ">
                                <th>Acumula Datos</th>
                                <th>Evalua</th>
                                <th>MetrosContacto</th>
                                <th>Coeficiente</th>
                                </sec:authorize>
                            </thead>

                            <tbody>

                            <c:forEach items="${elementos}" var="bo">

                                <tr>
                                    <td><a href="/elemento/${bo.id}">${bo.id}</a></td>
                                    <sec:authorize access="hasAnyRole(
                                            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                        ">
                                    <td class="text-center">
                                        <a href="<c:url value='/elemento/update?id=${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/edit2.png" alt="">
                                    </td>
                                    <td class="text-center">
                                        <a ${bo.bajaLogica == true ? 'class="d-none"' : ''} href="<c:url value='/elemento/dropBajaLogica?id=${bo.id}'/>"/>
                                        <img ${bo.bajaLogica == true ? 'class="d-none"' : ''} src="/resources/assets/img/icons/delete.png" alt="">
                                        <a ${bo.bajaLogica == false ? 'class="d-none"' : ''} href="<c:url value='/elemento/upBajaLogica?id=${bo.id}'/>"/>
                                        <img ${bo.bajaLogica == false ? 'class="d-none"' : ''} src="/resources/assets/img/icons/arrowUp2.png" alt="">
                                    </td>
                                    </sec:authorize>
                                    <td>${bo.descripcion}</td>
                                    <td>${bo.panos}</td>
                                    <td>${bo.mapMedio.descripcion}</td>
                                    <td>${bo.mapFormato.descripcion}</td>
                                    <sec:authorize access="hasAnyRole(
                                            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                        ">
                                    <td>${bo.acumulaDatos}</td>
                                    <td>${bo.evalua}</td>
                                    <td>${bo.metrosContacto}</td>
                                    <td>${bo.coeficiente}</td>
                                    </sec:authorize>
                                </tr>

                            </c:forEach>

                            </tbody>
                        </table>
                        <div class="row pt-4 px-2">
                            <div class="col-6">

                                <form name="search" action="list" method="get">

                                    <a href="../tablas" class="btn btn-light pull-left mr-3"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>
                                    <sec:authorize access="hasAnyRole(
                                            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                        ">
                                    <a href="create" class="btn btn-primary btn-fill"><i class="fas fa-plus"></i>&nbsp;Nuevo</a>
                                    </sec:authorize>

                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var element = document.getElementById("geoplanning");
    element.classList.add("active");

    $(document).ready(function(){

        var valueOfSearchStoraged = sessionStorage['elementoSearchDataTable'] || '';
        $('.dataTables_filter input[type="search"]').val(valueOfSearchStoraged).keyup();

        $('.dataTables_filter input').unbind().keyup(function(e) {

            var valueSearchDataTable = $(this).val();
            if (valueSearchDataTable.length>=1) {
                dataTableToCompleteList.search(valueSearchDataTable).draw();
                sessionStorage['elementoSearchDataTable'] = valueSearchDataTable;

            } else {
                dataTableToCompleteList.search('').draw();
                sessionStorage['elementoSearchDataTable'] = "";
            }
        });

    });
</script>
