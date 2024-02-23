<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header">
                        <h4 class="card-title">Campa&ntilde;a</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <%--@elvariable id="campaign" type="com.ideaas.services.bean.MapCampaignDTO"--%>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" >
                            <tbody>
                            <tr>
                                <th>Cliente</th>
                                <td>${campaign.client}</td>
                            </tr>
                            <tr>
                                <th>Nombre</th>
                                <td>${campaign.campaign}</td>
                            </tr>
                            <tr>
                                <th>Fecha de inicio</th>
                                <td>${campaign.startDate}</td>
                            </tr>
                            <tr>
                                <th>Fecha de final</th>
                                <td>${campaign.finishDate}</td>
                            </tr>
                            <tr>
                                <th>Cantidad</th>
                                <td>${campaign.reservationAmount}</td>
                            </tr>
                            <tr>
                                <th>Estado</th>
                                <td>${campaign.reservationState .description}</td>
                            </tr>
                            </tbody>
                        </table>
                        <a href="../list" class="btn btn-light"><i class="fas fa-angle-double-left pr-2"></i>Volver a la lista</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
