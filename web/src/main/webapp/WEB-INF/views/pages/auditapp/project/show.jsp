<%--
  Created by IntelliJ IDEA.
  User: guerr
  Date: 16/06/2022
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title" style="text-align: center">Proyecto ID: ${project.id}</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" >
                            <tbody>
                            <tr>
                                <th>Nombre:</th>
                                <td>${project.name}</td>
                            </tr>
                        </table>
                    </div>

                    <div class="py-2" style="text-align: center;">
                        <a class="btn btn-light" href="../list"><i class="fas fa-angle-double-left pr-2"></i>Volver a la lista</a>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>
