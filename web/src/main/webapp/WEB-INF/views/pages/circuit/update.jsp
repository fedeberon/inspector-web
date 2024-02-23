<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page   contentType="text/html; charset=ISO-8859-15" pageEncoding="UTF-8"%>
<%@ page   import="com.ideaas.services.enums.ReservationState" %>

<style>
    .popover p, .popover h3{
        margin:0;
    }

    /* Overriding properties that cause circuitDTOotstrap modals to not render correctly  */
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
                <%--@elvariable id="circuitDTO" type="com.ideaas.services.bean.MapCircuitDTO"--%>
                <form:form action="edited" id="update-form" modelAttribute="circuitDTO" method="POST"  cssClass="w-100 m-0">
                    <input type="hidden" name="campaignId"           value="${circuitDTO.campaignId}"/>
                    <input type="hidden" name="client"               value="${circuitDTO.client}"/>
                    <input type="hidden" name="clientId"             value="${circuitDTO.clientId}"/>
                    <input type="hidden" name="campaign"             value="${circuitDTO.campaign}"/>
                    <input type="hidden" name="startDate"            value="${circuitDTO.startDate}"/>
                    <input type="hidden" name="finishDate"           value="${circuitDTO.finishDate}"/>
                    <input type="hidden" name="company"              value="${circuitDTO.company}"/>
                    <input type="hidden" name="element"              value="${circuitDTO.element}"/>
                    <input type="hidden" name="province"             value="${circuitDTO.province}"/>
                    <input type="hidden" name="city"                 value="${circuitDTO.city}"/>
                    <input type="hidden" name="companyId"            value="${circuitDTO.companyId}"/>
                    <input type="hidden" name="elementId"            value="${circuitDTO.elementId}"/>
                    <input type="hidden" name="provinceId"           value="${circuitDTO.provinceId}"/>
                    <input type="hidden" name="cityId"               value="${circuitDTO.cityId}"/>
                    <div class="col-md-12 m-0">
                        <div class="form-group m-0">
                            <div class="row m-0">
                                <div class="col-6">
                                    <label for="startDate" class="control-label">Fecha de inicio</label>
                                    <form:input  path="updatedStartDate" cssClass="form-control datepicker" id="startDate" name="startDate" required="required" placeholder="Ingrese una nueva fecha de inicio"/>
                                    <form:errors path="updatedStartDate" cssStyle="color: red;"/>
                                </div>
                                <div class="col-6">
                                    <label for="finishDate" class="control-label">Fecha de finalizaci&oacute;n</label>
                                    <form:input  path="updatedFinishDate" cssClass="form-control datepicker" id="finishDate" name="finishDate" required="required" placeholder="Ingrese una nueva fecha de finalización"/>
                                    <form:errors path="updatedFinishDate" cssStyle="color: red;"/>
                                </div>
                                <div class="form-group col-6">
                                    <label>Tipo de reservaci&oacute;n</label>
                                    <select class="form-control selectpicker-default" id="reservationStateCode" name="reservationStateCode" required title="Seleccione nuevo una estado de reservaci&oacute;n">
                                        <option ${ReservationState.NOT_CONFIRMED.code == circuitDTO.reservationState.code } value="${ReservationState.NOT_CONFIRMED.code}">${ReservationState.NOT_CONFIRMED.description}</option>
                                        <option ${ReservationState.CONFIRMED.code     == circuitDTO.reservationState.code } value="${ReservationState.CONFIRMED.code}">${ReservationState.CONFIRMED.description}</option>
                                    </select>
                                    <form:errors path="reservationStateCode" cssStyle="color: red;"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </form:form>
                <div class="col-md-12 p-0 m-0 px-3 mb-3">
                    <a href="/campanas/${campaignId}/circuitos/list" class="btn btn-light pull-left m-0"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>
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