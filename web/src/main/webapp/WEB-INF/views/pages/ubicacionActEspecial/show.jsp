<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Ubicacion Actualizacion Especial</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example"class="table table-hover table-striped" style="width:100%">
                            <thead>
                            <th>Id Ubicacion</th>
                            <th>Operacion</th>
                            <th>Procesado</th>
                            <th>Fecha Alta</th>
                            </thead>

                            <tbody>
                            <tr>
                                <td>${mapUbicacionActEspecial.id.mapUbicacionId}</td>
                                <td>${mapUbicacionActEspecial.operacion}</td>
                                <td>${mapUbicacionActEspecial.procesado}</td>
                                <td>${mapUbicacionActEspecial.id.fechaAlta}</td>
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
