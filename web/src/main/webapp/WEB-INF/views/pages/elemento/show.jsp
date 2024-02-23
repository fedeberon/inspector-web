<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Elemento</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" style="width:100%">
                            <thead>
                            <th>ID</th>
                            <th>Descripcion</th>
                            <th>Panos</th>
                            <th>Medio</th>
                            <th>Formato</th>
                            <th>AcumulaDatos</th>
                            <th>Evalua</th>
                            <th>MetrosContacto</th>
                            <th>Coeficiente</th>
                            <th>Baja Logica</th>

                            </thead>

                            <tbody>


                            <tr>
                                <td>${elemento.id}</td>
                                <td>${elemento.descripcion}</td>
                                <td>${elemento.panos}</td>
                                <td>${elemento.mapMedio.descripcion}</td>
                                <td>${elemento.mapFormato.descripcion}</td>
                                <td>${elemento.acumulaDatos}</td>
                                <td>${elemento.evalua}</td>
                                <td>${elemento.metrosContacto}</td>
                                <td>${elemento.coeficiente}</td>
                                <td>${elemento.bajaLogica}</td>
                            </tr>


                            </tbody>



                        </table>
                        <a href="list" class="btn btn-primary"><i class="fas fa-angle-double-left pr-2"></i>Volver a la lista</a>

                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
