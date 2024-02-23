<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Stock</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">

                        <table id="dataTableToCompleteList" class="display" style="width:100%">
                            <thead>
                            <th class="text-center">Editar</th>
                            <th class="text-center">Elemento</th>
                            <th class="text-center">Empresa</th>
                            <th class="text-center">Cantidad de dispositivos en dep&oacute;sito</th>
                            <th class="text-center">Cantidad de dispositivos en reparaci&oacute;n</th>
                            <th class="text-center">Cantidad de dispositivos en calle</th>
                            </thead>
                            <tbody>
                            <%--@elvariable id="stocks" type="java.util.List"--%>
                            <%--@elvariable id="bo" type="com.ideaas.services.domain.Stock"--%>
                                <c:forEach items="${stocks}" var="bo">
                                    <tr>
                                        <td class="text-center">
                                            <a href="/stock/update?id=${bo.idStock}"/>
                                            <img src="/resources/assets/img/icons/edit2.png" alt="">
                                        </td>
                                        <td class="text-center">
                                            ${bo.mapElemento.descripcion}
                                        </td>
                                        <td class="text-center">
                                            ${bo.mapEmpresa.descripcion}
                                        </td>
                                        <td class="text-center">
                                            ${bo.cantDispositivosDeposito}
                                        </td>
                                        <td class="text-center">
                                            ${bo.cantDispositivosReparacion}
                                        </td>
                                        <td class="text-center">
                                            ${bo.cantDispositivosCalle}
                                        </td>
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
                                        <a href="updateUserStock" class="btn btn-primary btn-fill"><i class="fas fa-sync"></i>&nbsp;Actualizar Stocks</a>
                                        <!--<c:choose>
                                            <c:when test="${stocks.size()==0}">
                                            <a href="create" class="btn btn-primary btn-fill"><i class="fas fa-plus"></i>&nbsp;Nuevo</a>

                                            </c:when>
                                        </c:choose>-->
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
