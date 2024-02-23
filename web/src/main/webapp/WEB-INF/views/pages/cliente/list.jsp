<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Clientes</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="dataTableToCompleteList" class="display" style="width:100%">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th class="text-center">Borrar</th>
                                    <th class="text-center">Editar</th>
                                    <th>Nombre</th>
                                    <th>Agencia</th>
                                    <th>Direcci&oacute;n</th>
                                    <th>Email</th>
                                    <th>Tel&eacute;fono</th>
                                    <th>Ejecutivo de cuenta</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${clientes}" var="bo">
                                <tr>
                                    <td><a href="/cliente/${bo.id}">${bo.id}</a></td>
                                    <td class="text-center">
                                        <a href="<c:url value='/cliente/delete?id=${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/delete.png" alt="">
                                    </td>
                                    <td class="text-center">
                                        <a href="<c:url value='/cliente/update?id=${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/edit2.png" alt="">
                                    </td>
                                    <td>${bo.nombre}</td>
                                    <td>${bo.agencia}</td>
                                    <td>${bo.direccion}</td>
                                    <td>${bo.email}</td>
                                    <td>${bo.telefono}</td>
                                    <td>${bo.ejecutivoDeCuenta}</td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="row pt-4 px-2">
                            <div class="col-6">
                                <form name="search" action="list" method="get">
                                    <a href="../tablas" class="btn btn-light pull-left mr-3"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>
                                    <sec:authorize access="hasAnyRole(
                                        T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
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
