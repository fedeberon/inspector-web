<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ideaas.services.enums.ReservationState" %>

<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header">
                        <h4 class="card-title">Circuito</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <%--@elvariable id="circuitDTO" type="com.ideaas.services.bean.MapCircuitDTO"--%>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" >
                            <tbody>
                                <tr>
                                    <th>Cliente</th>
                                    <td>${circuitDTO.client}</td>
                                </tr>
                                <tr>
                                    <th>Campa&ntilde;a</th>
                                    <td>${circuitDTO.campaign}</td>
                                </tr>
                                <tr>
                                    <th>Fecha de inicio</th>
                                    <td>${circuitDTO.startDate}</td>
                                </tr>
                                <tr>
                                    <th>Fecha de finalizaci&oacute</th>
                                    <td>${circuitDTO.finishDate}</td>
                                </tr>
                                <tr>
                                    <th>Empresa</th>
                                    <td>${circuitDTO.company}</td>
                                </tr>
                                <tr>
                                    <th>Elemento</th>
                                    <td>${circuitDTO.element}</td>
                                </tr>
                                <tr>
                                    <th>Provincia</th>
                                    <td>${circuitDTO.province}</td>
                                </tr>
                                <tr>
                                    <th>Localidad</th>
                                    <td>${circuitDTO.city}</td>
                                </tr>
                                <tr>
                                    <th>Cantidad</th>
                                    <td>${circuitDTO.reservationAmount}</td>
                                </tr>
                                <tr>
                                    <th>Estado</th>
                                    <td>${circuitDTO.reservationState.description}</td>
                                </tr>
                            </tbody>
                        </table>
                        <a href="/campanas/${campaignId}/circuitos/list" class="btn btn-light"><i class="fas fa-angle-double-left pr-2"></i>Volver a la lista</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
