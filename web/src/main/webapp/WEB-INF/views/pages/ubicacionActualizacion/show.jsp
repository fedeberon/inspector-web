<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ideaas.services.bean.DateTimeUtil" %>

<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Ubicacion Actualizacion</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example"class="table table-hover table-striped" style="width:100%">
                            <thead>
                                <th>Id Ubicacion</th>
                                <th>Operacion</th>
                                <th>Fecha Operacion</th>
                                <th>Procesado</th>
                                <th>Evalua</th>
                                <th>Fecha Alta</th>
                            </thead>

                            <tbody>
                                <tr>
                                    <td>${mapUbicacionActualizacion.id.mapUbicacionId}</td>
                                    <td>${mapUbicacionActualizacion.operacion}</td>
                                    <td>${DateTimeUtil.formatLocalDateTime(mapUbicacionActualizacion.id.fechaOperacion)}</td>
                                    <td>${mapUbicacionActualizacion.procesado}</td>
                                    <td>${mapUbicacionActualizacion.evalua}</td>
                                    <td>${DateTimeUtil.formatLocalDateTime(mapUbicacionActualizacion.fechaAlta)}</td>
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
