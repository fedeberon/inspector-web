<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Localidad</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" style="width:100%">
                            <thead>
                            <th>ID</th>
                            <th>Descripcion</th>
                            <th>Provincia</th>
                            <th>Evalua</th>
                            <th>Baja Logica</th>
                            </thead>

                            <tbody>

                            <tr>
                                <td>${localidad.id}</td>
                                <td>${localidad.descripcion}</td>
                                <td>${localidad.mapProvincia.descripcion}</td>
                                <td>${localidad.evalua}</td>
                                <td>${localidad.bajaLogica}</td>
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
