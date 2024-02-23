<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">POI</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" style="width:100%">
                            <thead>
                            <th>ID</th>
                            <th>Descripcion</th>
                            <th>Entidad</th>
                            <th>Latitud</th>
                            <th>Longitud</th>
                            <th>Icono</th>
                            <th>Baja Logica</th>
                            </thead>

                            <tbody>


                            <tr>
                                <td>${poi.id}</td>
                                <td>${poi.descripcion}</td>
                                <td>${poi.mapPoiEntidad.descripcion}</td>
                                <td>${poi.geoLatitud}</td>
                                <td>${poi.geoLongitud}</td>
                                <td>${poi.icono}</td>
                                <td>${poi.bajaLogica}</td>
                            </tr>


                            </tbody>
                        </table>
                        <a href="list" class="btn btn-light"><i class="fas fa-angle-double-left pr-2"></i>Volver a la lista</a>

                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
