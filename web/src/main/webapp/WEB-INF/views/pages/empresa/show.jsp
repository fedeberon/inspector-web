<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Empresa</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" style="width:100%">
                            <thead>
                            <th>ID</th>
                            <th>Empresa</th>
                            <th>Telefono</th>
                            <th>E-Mail</th>
                            <th>Logo</th>
                            <th>Orden</th>
                            <th>Html</th>
                            <th>GP+</th>
                            <th>Baja Logica</th>
                            </thead>

                            <tbody>


                                <tr>
                                    <td>${empresa.id}</td>
                                    <td>${empresa.descripcion}</td>
                                    <td>${empresa.telefono}</td>
                                    <td>${empresa.mail}</td>
                                    <td>${empresa.logo}</td>
                                    <td>${empresa.orden}</td>
                                    <td>${empresa.html}</td>
                                    <td>${empresa.GPMas}</td>
                                    <td>${empresa.bajaLogica}</td>
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
