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
                <%--@elvariable id="newCampaign" type="com.ideaas.services.bean.MapCampaignDTO"--%>
                <form:form action="clone" id="update-form" modelAttribute="newCampaign" cssClass="w-100 m-0">
                    <form:hidden path="campaignId"/>
                    <input  type="hidden" name="isCloning" value="true"/>
                    <div class="col-md-12 m-0">
                        <div class="form-group m-0">
                            <div class="row m-0">
                                <div class="col-6">
                                    <label for="startDate" class="control-label">Nueva fecha de inicio</label>
                                    <form:input  path="startDate" cssClass="form-control datepicker" id="startDate" name="startDate" required="required" placeholder="Ingrese una nueva fecha de inicio"/>
                                    <form:errors path="startDate" cssStyle="color: red;"/>
                                </div>
                                <div class="form-group col-6">
                                    <label for="name" class="control-label">Nombre</label>
                                    <form:input  path="campaign" cssClass="form-control" id="name" name="name" required="required" placeholder="Ingrese una nueva nombre"/>
                                    <form:errors path="campaign" cssStyle="color: red;"/>
                                </div>
                                <div class="form-group col-6">
                                    <label>Seleccione un cliente</label>
                                    <select class="form-control" id="clientId" name="clientId" required title="Seleccione un cliente">
                                        <option value="-1">Seleccione</option>
                                        <c:forEach items="${clientes}" var="bo">
                                        <option ${newCampaign.clientId == bo.id ? 'selected' : ''} value="${bo.id}">${bo.nombre}</option>
                                        </c:forEach>
                                    </select>
                                    <form:errors path="client" cssStyle="color: red;"/>
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