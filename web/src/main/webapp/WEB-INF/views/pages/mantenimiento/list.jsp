<%--
  Created by IntelliJ IDEA.
  User: erwin
  Date: 30/09/20
  Time: 09:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script>
    function changeStatus(id) {
        $('.load').show();
        $('.map-container').css('opacity', '0.3');
        $('.content').css('opacity', '0.3');

        var checkbox = document.getElementById("changeStatus-" + id);

        if (checkbox.checked) {
            document.getElementById("upStatus-" + id).click();

        } else {
            document.getElementById("dropStatus-" + id).click();
        }
    }
</script>

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
            <div class="col-md-12"><h5 id="info-loader" style="text-align: center"></h5></div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Mantenimiento</h4>
                        <p class="card-category">Lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="dataTable" class="display" style="width:100%">
                            <thead>
                                <th>ID</th>
                                <th>Enable/Disable</th>
                                <th>Module</th>
                                <th>Status</th>
                            </thead>

                            <tbody>

                            <c:forEach items="${mantenimientos}" var="bo">

                                <tr>
                                    <td>${bo.id}</td>
                                    <td>
                                        <label id="maintenanceStatus" class="switch">
                                            <input id="changeStatus-${bo.id}" onclick="changeStatus(${bo.id})" ${bo.status == true ? 'checked' : ''} type="checkbox">
                                            <span class="slider round"></span>
                                        </label>
                                        <a class="d-none" id="dropStatus-${bo.id}" href="<c:url value='/mantenimiento/dropStatus?id=${bo.id}'/>"></a>
                                        <a class="d-none" id="upStatus-${bo.id}" href="<c:url value='/mantenimiento/upStatus?id=${bo.id}'/>"></a>
                                    </td>
                                    <td>${bo.module}</td>
                                    <td>
                                        ${bo.status}
                                    </td>
                                </tr>

                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
