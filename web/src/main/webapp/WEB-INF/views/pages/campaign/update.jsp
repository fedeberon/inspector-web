<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ page   contentType="text/html; charset=ISO-8859-15" pageEncoding="UTF-8"%>
<%@ page    import="com.ideaas.services.enums.ReservationState" %>

<style>
    .popover p, .popover h3{
        margin:0;
    }

    /* Overriding properties that cause bootstrap modals to not render correctly  */
    .modal-dialog {
        -webkit-transform: translate(0, 0)!important;
        -o-transform:      translate(0, 0)!important;
        transform:         translate(0, 0)!important;
    }
    .modal-content {
        margin-top: 0;
    }

    .card #update-form [class*="col-"]{
        padding: 6px;
    }
</style>
<div class="content">
    <div class="col-12">
        <div class="card">
            <div class="row m-0 mt-3">
                <%--@elvariable id="updateCampaign" type="com.ideaas.services.bean.MapCampaignDTO"--%>
                <form:form action="edit" id="update-form" modelAttribute="updateCampaign" cssClass="w-100 m-0">
                    <input:hidden path="campaignId"/>
                    <input  type="hidden" name="isCloning" value="false"/>
                    <div class="col-md-12 m-0">
                        <div class="form-group m-0">
                            <div class="row m-0">
                                    <%--
                                    <!-- It is not a requirement that the dates of a campaign can be edited, but in the context of
                                    changing requirements, I am leaving this just in case. -->
                                    <div class="col-6">
                                        <label for="startDate" class="control-label">Fecha de inicio</label>
                                        <form:input  path="startDate" cssClass="form-control datepicker" id="startDate" name="startDate" required="required" placeholder="Ingrese una nueva fecha de inicio"/>
                                        <form:errors path="startDate" cssStyle="color: red;"/>
                                    </div>
                                    <div class="col-6">
                                        <label for="finishDate" class="control-label">Fecha de finalizaci&oacute;n</label>
                                        <form:input  path="finishDate" cssClass="form-control datepicker" id="finishDate" name="finishDate" required="required" required="required"placeholder="Ingrese una nueva fecha de finalización"/>
                                        <form:errors path="finishDate" cssStyle="color: red;"/>
                                    </div> --%>
                                <div class="form-group col-6">
                                    <label for="name" class="control-label">Nombre</label>
                                    <form:input  path="campaign" cssClass="form-control" id="name" name="name" required="required" placeholder="Ingrese una nueva nombre"/>
                                    <form:errors path="campaign" cssStyle="color: red;"/>
                                </div>
                                <div class="form-group col-6">
                                    <label>Seleccione un cliente</label>
                                    <select class="form-control" id="clientId" name="clientId" required title="Seleccione un cliente">
                                        <c:forEach items="${clientes}" var="bo">
                                            <option ${updateCampaign.clientId == bo.id ? 'selected' : ''} value="${bo.id}">${bo.nombre}</option>
                                        </c:forEach>
                                        <form:errors path="client" cssStyle="color: red;"/>
                                    </select>
                                    <form:errors path="client" cssStyle="color: red;"/>
                                </div>
                                <div class="form-group col-6">
                                    <label>Tipo de reservaci&oacute;n</label>
                                    <select  class="form-control selectpicker-default" id="reservationStateCode" name="reservationStateCode" title="Seleccione nuevo una estado de reservaci&oacute;n">
                                        <option value="${ReservationState.NOT_CHANGE.code}" selected>${ReservationState.NOT_CHANGE.description}</option>
                                        <option ${ReservationState.NOT_CONFIRMED.code == updateCampaign.reservationState.code ? 'selected': '' } value="${ReservationState.NOT_CONFIRMED.code}">${ReservationState.NOT_CONFIRMED.description}</option>
                                        <option ${ReservationState.CONFIRMED.code     == updateCampaign.reservationState.code ? 'selected': '' } value="${ReservationState.CONFIRMED.code}">${ReservationState.CONFIRMED.description}</option>
                                    </select>
                                    <form:errors path="reservationStateCode" cssStyle="color: red;"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </form:form>
                <div class="col-md-12 p-0 m-0 px-3 mb-3">
                    <a href="./list" class="btn btn-light pull-left m-0"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>
                    <button type="button" class="btn btn-secondary btn-fill pull-right m-0" data-toggle="modal" data-target-modal-interceptor="#update-modal">
                        Guardar
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- Modal update reservations --%>
<jsp:include page="../modals/updateReservationsModal.jsp"/>

<%-- Loader --%>
<jsp:include page="../component/loader.jsp"/>