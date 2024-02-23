<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-15" pageEncoding="UTF-8"%>

<%-- Modal for udpate Campaign, Circuits and Reservations. --%>
<%-- It's modal has a button that refers to a form that needs to be validated by JS before submitting --%>
<%-- @param html-modal-id the modal id, with default value html-modal-id --%>
<%-- @param html-form-id the form id, with default value update-form     --%>
<c:set var="htmFormId"   value="${param['html-form-id']}" />
<c:set var="htmlModalId" value="${param['html-modal-id']}"/>
<c:if test="${htmFormId  == null || htmFormId  == ''}">
    <c:set var="htmFormId"   value="update-form" />
</c:if>
<c:if test="${htmlModalId == null || htmlModalId == ''}">
    <c:set var="htmlModalId" value="update-modal"/>
</c:if>
<div class="modal fade modal-confirm" id="${htmlModalId}" tabindex="-1" role="dialog" aria-labelledby="confirm-changes-modal-label" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="white-space: normal;">
            <div class="modal-header">
                <h4 class="modal-title mt-0" id="confirm-changes-modal-label">&iquest;Desea confirmar los cambios?</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body text-left"><%-- The content. --%></div>
            <div class="modal-footer">
                <button type="#" class="btn btn-light" data-dismiss="modal">Cancelar</button>
                <button type="submit" form="${htmFormId}" class="btn btn-secondary btn-fill">Guardar</button>
            </div>
        </div>
    </div>
</div>