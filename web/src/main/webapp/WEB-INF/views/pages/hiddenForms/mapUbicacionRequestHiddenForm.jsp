<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ideaas.services.bean.DateTimeUtil" %>


<%-- This inputs are use to store the value of the Wrapper class after a post. --%>
<input type="hidden" id="request.id"                    name="request.id"                    value="${ubicacionRequest.id}"/>
<input type="hidden" id="request.mapEmpresa"            name="request.mapEmpresa"            value="${ubicacionRequest.mapEmpresa}"/>
<input type="hidden" id="request.mapElemento"           name="request.mapElemento"           value="${ubicacionRequest.mapElemento}"/>
<input type="hidden" id="request.mapProvincia"          name="request.mapProvincia"          value="${ubicacionRequest.mapProvincia}"/>
<input type="hidden" id="request.mapFormato"            name="request.mapFormato"            value="${ubicacionRequest.mapFormato}"/>
<input type="hidden" id="request.mapLocalidad"          name="request.mapLocalidad"          value="${ubicacionRequest.mapLocalidad}"/>
<input type="hidden" id="request.mapMedio"               name="request.mapMedio"              value="${ubicacionRequest.mapMedio}"/>
<input type="hidden" id="request.bajaLogica"            name="request.bajaLogica"            value="${ubicacionRequest.bajaLogica}"/>
<input type="hidden" id="request.fechaAlta"             name="request.fechaAlta"             value="${fechaAltaFormated}"/>
<input type="hidden" id="request.latLngEmpty"           name="request.latLngEmpty"           value="${ubicacionRequest.latLngEmpty}"/>
<input type="hidden" id="request.polygonLatLong"        name="request.polygonLatLong"        value="${ubicacionRequest.polygonLatLong}"/>
<input type="hidden" id="request.latitud"               name="request.latitud"               value="${ubicacionRequest.latitud}"/>
<input type="hidden" id="request.idsSearch"             name="request.idsSearch"             value="${ubicacionRequest.idsSearch}"/>
<input type="hidden" id="request.searchValue"           name="request.searchValue"           value="${ubicacionRequest.searchValue}"/>
<input type="hidden" id="request.reservacionFechaHasta" name="request.reservacionFechaHasta" value="${DateTimeUtil.formatLocalDate(ubicacionRequest.reservacionFechaHasta)}"/>
<input type="hidden" id="request.reservacionFechaDesde" name="request.reservacionFechaDesde" value="${DateTimeUtil.formatLocalDate(ubicacionRequest.reservacionFechaDesde)}"/>
<input type="hidden" id="request.mapClientes"           name="request.mapClientes"           value="${ubicacionRequest.mapClientes}"/>
<input type="hidden" id="request.campana"               name="request.campana"               value="${ubicacionRequest.campana}"/>
<input type="hidden" id="request.estadoReservacion"     name="request.estadoReservacion"     value="${ubicacionRequest.estadoReservacion}"/>
<c:forEach items="${ubicacionRequest.getFilteredParametros().entrySet()}" var="entry">
<input type="hidden" id="request.parametros[${entry.getKey()}]"    name="request.parametros[${entry.getKey()}]"     value="${entry.getValue()}">
</c:forEach>