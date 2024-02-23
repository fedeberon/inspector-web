<%--
Created by IntelliJ IDEA.
User: guerr
Date: 19/02/2022
Time: 19:18
To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<style>
    #rendered-form {
        max-width: 90%;
        margin: auto;
        border-style: outset;
        border-radius: 5px;
        padding: 10px;
    }
    #errorsTable{
        width: 100%;
        overflow-x: scroll;
    }
</style>--%>
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
                        <h4 class="card-title">Clientes para ruta: ${idRelevamiento}</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <%--<form:form modelAttribute="myWrapper" name="myWrapper" id="myWrapper" method="post">--%>
                            <table id="dataTableToCompleteList" class="display" style="width:100%">
                                <thead>
                                <th>
                                    <div class="form-check">
                                        <label class="form-check-label">
                                            <input type="checkbox" value=""  id="checkbox-all" onclick="selectAll()">
                                            <span class="form-check-sign"></span>
                                        </label>
                                    </div>
                                </th>
                                <th>ID</th>
                                <th class="text-center">Revisar</th>
                                <%--<th>ID Relevamiento</th>--%>
                                <th>ID Ubicacion</th>
                                <th>Direccion</th>
                                <th>Localidad</th>
                                <th>Provincia</th>
                                <th>Cantidad</th>
                                <th>EVP</th>
                                <th>Elemento</th>
                                <th>Anunciante</th>
                                <th>Producto</th>
                                <th>Referencias</th>
                                <th>One Circuito</th>
                                <th>One OrdenS</th>
                                </thead>

                                <tbody>

                                <c:forEach items="${ubicaciones}" var="bo" varStatus="status">
                                <%--<c:choose>
                                <c:when test="${bo.appUbicacion.latitud < 0 && bo.appUbicacion.longitud < 0}">--%>
                                <tr>
                                    <td>
                                        <div class="form-check">
                                            <label class="form-check-label">
                                                <input class="form-check-input" type="checkbox" id="form-check-input-${bo.id}" name="list[${status.index}].checked">
                                                <span class="form-check-sign" id="${bo.id}"></span>
                                            </label>
                                        </div>
                                    </td>
                                    <td>
                                        <a href="/appUbicacionRelevamiento/${bo.id}">${bo.id}</a>
                                        <input type="hidden" value="${bo.id}" name="list[${status.index}].id"/>
                                    </td>
                                    <td class="text-center">
                                        <a href="<c:url value='/appUbicacionRevisar/show/${idRelevamiento}/${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/edit2.png" alt="">
                                    </td>
                                    <%--<td>
                                        <a href="/relevamiento/${bo.appRelevamiento.id}">${bo.appRelevamiento.id}</a>
                                        <input type="hidden" value="${bo.appRelevamiento.id}" name="list[${status.index}].appRelevamiento"/>
                                    </td>--%>
                                    <td>
                                        <a href="/appUbicacion/${bo.appUbicacion.id}">${bo.appUbicacion.id}</a>
                                        <input type="hidden" value="${bo.appUbicacion.id}" name="list[${status.index}].appUbicacion"/>
                                    </td>

                                    <td>
                                            ${bo.appUbicacion.direccion}
                                        <input type="hidden" value="${bo.appUbicacion.direccion}" name="list[${status.index}].address"/>
                                        <input type="hidden" value="${bo.appUbicacion.latitud}" name="list[${status.index}].latitud"/>
                                        <input type="hidden" value="${bo.appUbicacion.longitud}" name="list[${status.index}].longitud"/>
                                    </td>
                                    <td>
                                            ${bo.appUbicacion.localidad}
                                    </td>
                                    <td>
                                            ${bo.appUbicacion.provincia}
                                    </td>

                                    <td>
                                            ${bo.cantidad}
                                        <input type="hidden" value="${bo.cantidad}" name="list[${status.index}].cantidad"/>
                                    </td>

                                    <td>
                                            ${bo.evp}
                                        <input type="hidden" value="${bo.evp}" name="list[${status.index}].evp"/>
                                    </td>

                                    <td>
                                            ${bo.elemento}
                                        <input type="hidden" value="${bo.elemento}" name="list[${status.index}].elemento"/>
                                    </td>

                                    <td>
                                            ${bo.anunciante}
                                        <input type="hidden" value="${bo.anunciante}" name="list[${status.index}].anunciante"/>
                                    </td>

                                    <td>
                                            ${bo.producto}
                                        <input type="hidden" value="${bo.producto}" name="list[${status.index}].producto"/>
                                    </td>

                                    <td>
                                            ${bo.referencias}
                                        <input type="hidden" value="${bo.referencias}" name="list[${status.index}].referencias"/>
                                    </td>

                                    <td>
                                            ${bo.oneOrdenS}
                                        <input type="hidden" value="${bo.oneOrdenS}" name="list[${status.index}].oneOrdenS"/>
                                    </td>

                                    <td>
                                            ${bo.oneCircuito}
                                        <input type="hidden" value="${bo.oneCircuito}" name="list[${status.index}].oneCircuito"/>
                                    </td>
                                </tr>
                                <%--</c:when>
                                <c:otherwise>
                                <tr>
                                    <td style="background-color: #fc727a !important;" class="text-white">
                                        N/A
                                    </td>
                                    <td class="alert-danger">
                                        <a href="/appUbicacionRelevamiento/${bo.id}">${bo.id}</a>
                                    </td>
                                    <td class="alert-danger text-white text-center">
                                        <a href="<c:url value='/appUbicacionRelevamiento/update?id=${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/edit2.png" alt="">
                                    </td>
                                    <td class="alert-danger text-white">
                                        <a href="/relevamiento/${bo.appRelevamiento.id}">${bo.appRelevamiento.id}</a>
                                    </td>
                                    <td class="alert-danger text-white">
                                        <a href="/appUbicacion/${bo.appUbicacion.id}">${bo.appUbicacion.id}</a>
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.appUbicacion.direccion}
                                    </td>
                                    <td class="alert-danger text-white">
                                            ${bo.appUbicacion.localidad}
                                    </td>
                                    <td class="alert-danger text-white">
                                            ${bo.appUbicacion.provincia}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.cantidad}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.evp}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.elemento}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.anunciante}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.producto}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.referencias}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.oneOrdenS}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.oneCircuito}
                                    </td>
                                </tr>
                                <%--
                                </c:otherwise>
                                </c:choose>
                                --%>
                                </c:forEach>
                            </table>
                            <div class="col-8">
                                <a class="btn btn-secondary btn-fill" href="<c:url value='/relevamiento/list'/>" role="button">Volver a la lista</a>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
