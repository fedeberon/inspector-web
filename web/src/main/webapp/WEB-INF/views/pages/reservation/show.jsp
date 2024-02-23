<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header">
                        <h4 class="card-title">Reservaciones</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <%--@elvariable id="reservationDTO" type="com.ideaas.services.bean.MapReservationDTO"--%>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" >
                            <tbody>
                                <tr>
                                    <th>ID</th>
                                    <td>${reservationDTO.id}</td>
                                </tr>
                                <tr>
                                    <th>Fecha de inicio</th>
                                    <td>${reservationDTO.finishDate}</td>
                                </tr>
                                <tr>
                                    <th>Fecha de finalizaci&oacute;n</th>
                                    <td>${reservationDTO.startDate}</td>
                                </tr>
                                <tr>
                                    <th>Empresa</th>
                                    <td>${reservationDTO.company}</td>
                                </tr>
                                <tr>
                                    <th>Elemento</th>
                                    <td>${reservationDTO.element}</td>
                                </tr>
                                <tr>
                                    <th>Provincia</th>
                                    <td>${reservationDTO.province}</td>
                                </tr>
                                <tr>
                                    <th>Localidad</th>
                                    <td>${reservationDTO.city}</td>
                                </tr>
                                <tr>
                                    <th>Direcci&oacute;n</th>
                                    <td>${reservationDTO.direction}</td>
                                </tr>
                                <tr>
                                    <th>Estado</th>
                                    <td>${reservationDTO.reservationState.description}</td>
                                </tr>
                            </tbody>
                        </table>
                        <form action="../list" method="post" class="m-0">
                            <input type="hidden" name="client"               value="${reservationDTO.client}">
                            <input type="hidden" name="campaign"             value="${reservationDTO.campaign}">
                            <input type="hidden" name="startDate"            value="${reservationDTO.startDate}">
                            <input type="hidden" name="finishDate"           value="${reservationDTO.finishDate}">
                            <input type="hidden" name="company"              value="${reservationDTO.company}">
                            <input type="hidden" name="element"              value="${reservationDTO.element}">
                            <input type="hidden" name="province"             value="${reservationDTO.province}">
                            <input type="hidden" name="city"                 value="${reservationDTO.city}">
                            <input type="hidden" name="reservationStateCode" value="${reservationDTO.reservationState.code}">
                            <input type="hidden" name="reservationId"        value="${reservationDTO.reservationId}">
                            <button type="submit" class="btn btn-light"><i class="fas fa-angle-double-left pr-2" aria-hidden="true"></i>Volver a la lista</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
