<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title" style="text-align: center">Relevamiento Ubicacion ID: ${appUbicacionRelevamiento.id}</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" >

                            <tbody>
                                <tr>
                                    <th>ONE_ClaveCliente:</th>
                                    <td>${appUbicacionRelevamiento.appUbicacion.oneClaveUbi}</td>
                                </tr>
                                <tr>
                                    <th>Direccion:</th>
                                    <td>${appUbicacionRelevamiento.appUbicacion.direccion}</td>
                                </tr>
                                <tr>
                                    <th>Cantidad:</th>
                                    <td>${appUbicacionRelevamiento.cantidad}</td>
                                </tr>
                                <tr>
                                    <th>EVP:</th>
                                    <td>${appUbicacionRelevamiento.evp}</td>
                                </tr>
                                <tr>
                                    <th>Elemento:</th>
                                    <td>${appUbicacionRelevamiento.elemento}</td>
                                </tr>
                                <tr>
                                    <th>Anunciante:</th>
                                    <td>${appUbicacionRelevamiento.anunciante}</td>
                                </tr>
                                <tr>
                                    <th>Producto:</th>
                                    <td>${appUbicacionRelevamiento.producto}</td>
                                </tr>
                                <tr>
                                    <th>Referencias:</th>
                                    <td>${appUbicacionRelevamiento.referencias}</td>
                                </tr>
                                <tr>
                                    <th>Fecha de finalizaci&oacute;n:</th>
                                    <td>${appUbicacionRelevamiento.fechaFinalizacion}</td>
                                </tr>
                                <tr>
                                    <th>Localidad:</th>
                                    <td>${appUbicacionRelevamiento.appUbicacion.localidad}</td>
                                </tr>
                                <tr>
                                    <th>Provincia:</th>
                                    <td>${appUbicacionRelevamiento.appUbicacion.provincia}</td>
                                </tr>
                                <tr>
                                    <th>Latitud:</th>
                                    <td>${appUbicacionRelevamiento.appUbicacion.latitud}</td>
                                </tr>
                                <tr>
                                    <th>Longitud:</th>
                                    <td>${appUbicacionRelevamiento.appUbicacion.longitud}</td>
                                </tr>
                            </tbody>
                        </table>
                        <table class="table table-hover table-striped">
                            <thead>
                                <th>Ruta asignada</th>
                                <th>ONE Orden_S</th>
                                <th>ONE Circuito</th>
                            </thead>
                            <tbody>
                                <c:forEach items="${relevamientosShow}" var="bo" varStatus="status">
                                    <tr>
                                        <td>${bo.appRelevamiento.nombreRelevamiento}</td>
                                        <td>${bo.oneOrdenS}</td>
                                        <td>${bo.oneCircuito}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="py-2" style="text-align: center;">
                        <a class="btn btn-light" href="list"><i class="fas fa-angle-double-left pr-2"></i>Volver a la lista</a>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>
<script>
    var element = document.getElementById("auditapp");
    element.classList.add("active");
</script>