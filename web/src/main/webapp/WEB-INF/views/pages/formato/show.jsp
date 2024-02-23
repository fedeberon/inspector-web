<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Formato</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" style="width:100%">
                            <thead>
                            <th>ID</th>
                            <th>Elemento</th>
                            <th>Evalua</th>
                            </thead>

                            <tbody>


                            <tr>
                                <td>${formato.id}</td>
                                <td>${formato.descripcion}</td>
                                <td>${formato.evalua}</td>
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

