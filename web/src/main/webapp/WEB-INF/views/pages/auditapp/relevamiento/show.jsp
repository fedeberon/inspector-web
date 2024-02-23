<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title" style="text-align: center">Ruta ID: ${relevamiento.id}</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" >

                            <tbody>
                                <tr>
                                    <th>Nombre de la ruta:</th>
                                    <td>${relevamiento.nombreRelevamiento}</td>
                                </tr>
                                <tr>
                                    <th>Inspector a cargo:</th>
                                    <td>${relevamiento.usuario.username}</td>
                                </tr>
                                <tr>
                                    <th>Formulario:</th>
                                    <td>${relevamiento.formulario.nombreFormulario}</td>
                                </tr>
                                <tr>
                                    <th>Estado:</th>
                                    <td>${relevamiento.estado.descripcion}</td>
                                </tr>
                                <tr>
                                    <th>Con clientes:</th>
                                    <td>${relevamiento.conUbicacionesPreAsignadas}</td>
                                </tr>
                                <tr>
                                    <th>Fecha de asignacion:</th>
                                    <td>${relevamiento.fechaAsignacion}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="py-2" style="text-align: center;">
                    <c:choose>
                        <c:when test="${not empty projectId}">

                                <a class="btn btn-light" href="/relevamiento/list/${projectId}"><i class="fas fa-angle-double-left pr-2"></i>Volver a la lista</a>
                        </c:when>
                        <c:otherwise>
                                <a class="btn btn-light" href="list"><i class="fas fa-angle-double-left pr-2"></i>Volver a la lista</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

        </div>

    </div>
</div>
<script>
    var element = document.getElementById("relevamiento");
    element.classList.add("active");
</script>