<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header">
                        <h4 class="card-title">Cliente</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" >
                            <tbody>
                                <tr>
                                    <th>ID</th>
                                    <td>${cliente.id}</td>
                                </tr>
                                <tr>
                                    <th>Nombre</th>
                                    <td>${cliente.nombre}</td>
                                </tr>
                                <tr>
                                    <th>Agencia</th>
                                    <td>${cliente.agencia}</td>
                                </tr>
                                <tr>
                                    <th>Direcci&oacute;n</th>
                                    <td>${cliente.direccion}</td>
                                </tr>
                                <tr>
                                    <th>Email</th>
                                    <td>${cliente.email}</td>
                                </tr>
                                <tr>
                                    <th>Tel&eacute;fono</th>
                                    <td>${cliente.telefono}</td>
                                </tr>
                                <tr>
                                    <th>Ejecutivo de cuenta</th>
                                    <td>${cliente.ejecutivoDeCuenta}</td>
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
