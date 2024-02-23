<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page   import="com.ideaas.services.enums.ReservationState" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="content">
    <div class="container-fluid">
        <div class="col load mt-5" style="display: none; position:absolute; z-index: 1000; top: 123px;">
            <div class="col-md-12">
                <div class="loader">
                    <div class="loader-inner box1"></div>
                    <div class="loader-inner box2"></div>
                    <div class="loader-inner box3"></div>
                </div>
            </div>
            <div class="col-md-12"><h5 style="text-align: center"></h5></div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header">
                        <h4 class="card-title">Clientes</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <form:form modelAttribute="myWrapper" name="myWrapper" id="myWrapper" method="post">
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
                            <input type="hidden" name="reservationAmount"    value="${circuitDTO.reservationAmount}"/>
                            <input type="hidden" name="reservationStateCode" value="${circuitDTO.reservationState.code}"/>
                            <table id="dataTableToCompleteList" class="display" style="width:100%">
                                <!--Columns go here-->
                                <thead>
                                <th>
                                    <div class="form-check">
                                        <label class="form-check-label">
                                            <input type="checkbox" value=""  id="checkbox-all" onclick="selectAll()">
                                            <span class="form-check-sign"></span>
                                        </label>
                                    </div>
                                </th>
                                <th class="text-center">Editar</th>
                                <th class="text-center">Borrar</th>
                                <th>ID</th>
                                <th>Exhibido</th>
                                <th>ID Referencia</th>
                                <th>Empresa</th>
                                <th>Direccion</th>
                                <th>Provincia</th>
                                <th>Localidad</th>
                                <th>Elemento</th>
                                <th>Estado</th>
                                <th>Orden Numero</th>
                                <th>Latidud</th>
                                <th>Longitud</th>

                                </thead>

                                <!--Rows go here-->
                                <tbody>
                                <%--@elvariable id="bo" type="com.ideaas.services.bean.MapReservationDTO"--%>
                                <c:forEach items="${reservations}" var="bo" varStatus="status">
                                    <tr>
                                        <td>
                                            <div class="form-check">
                                                <label class="form-check-label">
                                                    <input class="form-check-input checkbox" data-form="create-reservacion-form exhibir-form load-map-form" type="checkbox" id="form-check-input-${bo.reservationId}" name="idAppUbicacionesList" onclick="saveCheck(${bo.reservationId})" value="${bo.reservationId}">
                                                    <span class="form-check-sign" id="${bo.reservationId}"></span>
                                                </label>
                                            </div>
                                        </td>
                                        <td>
                                            <a href="./edit?reservationId=${bo.reservationId}"/>
                                            <img src="/resources/assets/img/icons/edit2.png" alt="edit icon">
                                        </td>
                                        <td class="text-center">
                                            <form action="./delete/${bo.reservationId}" method="post" class="m-0" id="form${bo.reservationId}">
                                                <input type="hidden" name="id" value="${bo.id}"/>
                                                <input type="hidden" name="reservationId" value="${bo.reservationId}"/>
                                                <input type="hidden" name="startDate" value="${bo.startDate}"/>
                                                <input type="hidden" name="finishDate" value="${bo.finishDate}"/>
                                                <a href=# data-toggle="modal" data-target="#modal-delete-reservation" data-form="form${bo.reservationId}"/>
                                                <img src="/resources/assets/img/icons/delete.png" alt="">
                                            </form>
                                        </td>
                                        <td>
                                            <%-- WIP <a href="./show/${bo.id}">${bo.id}</a>  WIP --%>
                                            <a href="#">${bo.id}</a>
                                        </td>
                                        <td class="text-center">
                                            <c:if test="${bo.exhibir}">
                                                <i class="fa fa-check-square-o" aria-hidden="true"></
                                            </c:if>

                                            <c:if test="${bo.exhibir == false}">
                                                <i class="fa fa-square-o" aria-hidden="true"></i>
                                        </c:if>
                                           
                                        </td>
                                        <td>${bo.referenceId}</td>
                                        <td>${bo.company}</td>
                                        <td>${bo.direction}</td>
                                        <td>${bo.province}</td>
                                        <td>${bo.city}</td>
                                        <td>${bo.element}</td>
                                        <td>${bo.reservationState.description}</td>
                                        <td>${bo.ordenNumber}</td>
                                        <td>${bo.latitude}</td>
                                        <td>${bo.longitude}</td>
                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <div class="row pt-4 px-2 mx-0">
                                <a href="../list" class="btn btn-light"><i class="fas fa-angle-double-left pr-2"></i> Circuitos</a>
                                &nbsp;
                                <button form="form" formaction="/planificacion/search" formmethod="post" class="btn btn-primary btn-fill"> Agregar ubicaciones</button>
                                <!--

                                <button form="exhibir-form" formaction="./set-exhibido" formmethod="post"  class="btn btn-primary btn-fill">Exhibir</button>
                                -->
                                &nbsp;
                                <div class="dropdown px-2">
                                    <button class="btn btn-primary dropdown-toggle btn-fill" type="button" data-toggle="dropdown" aria-expanded="false">
                                        Exhibir multiples
                                    </button>
                                    <div class="dropdown-menu">
                                        <a class="dropdown-item" id="exhibir-true" href="#">Exhibir</a>
                                        <a class="dropdown-item" id="exhibir-false" href="#">Ocultar</a>
                                    </div>
                                </div>
                                &nbsp;
                                <button type="submit" name="delete" onclick= "form.action = 'deleteAll';" class="btn btn-secondary btn-fill"><i class="fas fa-trash-alt"></i>&nbsp;Borrar</button>
                            </div>
                        </form:form >
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="target-append-form" class="hidden"></div>



<fmt:parseDate  value="${circuitDTO.startDate}"  pattern="yyyy-MM-dd" var="startDate" type="date" />
<fmt:parseDate  value="${circuitDTO.finishDate}" pattern="yyyy-MM-dd" var="finishDate" type="date" />
<fmt:formatDate value="${startDate}"  var="formattedStartDate"  pattern="dd-MM-yyyy" />
<fmt:formatDate value="${finishDate}" var="formattedFinishDate" pattern="dd-MM-yyyy" />

<%--@elvariable id="circuitDTO" type="com.ideaas.services.bean.MapCircuitDTO"--%>
<form id="form">
    <input type="hidden" id="request.mapEmpresa"            name="request.mapEmpresa"            value="${circuitDTO.company}"/>
    <input type="hidden" id="request.mapElemento"           name="request.mapElemento"           value="${circuitDTO.element}"/>
    <input type="hidden" id="request.mapProvincia"          name="request.mapProvincia"          value="${circuitDTO.province}"/>
    <input type="hidden" id="request.mapLocalidad"          name="request.mapLocalidad"          value="${circuitDTO.city}"/>
    <input type="hidden" id="request.reservacionFechaDesde" name="request.reservacionFechaDesde" value="${formattedStartDate}"/>
    <input type="hidden" id="request.reservacionFechaHasta" name="request.reservacionFechaHasta" value="${formattedFinishDate}"/>
    <input type="hidden" id="request.estadoReservacion"     name="request.estadoReservacion"     value="${ReservationState.NOT_CONFIRMED.code},${ReservationState.AVAILABLE.code}"/>
</form>

<form id="exhibir-form">
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
    <input type="hidden" name="reservationAmount"    value="${circuitDTO.reservationAmount}"/>
    <input type="hidden" name="reservationStateCode" value="${circuitDTO.reservationState.code}"/>
</form>

<div class="modal fade confirm-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" id="modal-delete-reservation">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h5></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="files-auditapp d-none"></div>
                <h4 style="margin-top: 0;">&iquest;Est&aacute; seguro que desea eliminar esta reservaci&oacute;n?</h4>
            </div>
                <div class="modal-footer">
                <button type="button" class="btn btn-light" data-dismiss="modal">No</button>
                <a class="btn btn-fill btn-default">Si</a>
            </div>
        </div>
    </div>
</div>
<script>
    $('#modal-delete-reservation').on('show.bs.modal', function(e) {
        let formId = $(e.relatedTarget).attr('data-form')
        $(".btn-default").click(function(){
            $('#' + formId).submit();
        });
    });

    function saveCheck(id,selectAllCheckbox){
        selectAllCheckbox = selectAllCheckbox || false;

        let ids=JSON.parse(sessionStorage.getItem('listaChekboxUbicacionAuditapp'))
        if(ids==null){ids=[]}
        let identificadorInput= "#form-check-input-"+id
        let input=document.querySelector(identificadorInput)

        if(selectAllCheckbox.arriba==true) {
            if (!ids.includes(id)) {
                ids.push(id)
                sessionStorage.setItem('listaChekboxUbicacionAuditapp', JSON.stringify(ids))
            }
        }
        if(selectAllCheckbox.checkbox==false){
            sessionStorage.setItem('listaChekboxUbicacionAuditapp',JSON.stringify([]))
        }else{
            if(!ids.includes(id)){
                ids.push(id)
                sessionStorage.setItem('listaChekboxUbicacionAuditapp',JSON.stringify(ids))
            }else if(ids.includes(id)&&selectAllCheckbox.agregarTodas!=undefined&&(selectAllCheckbox.arriba==false)||selectAllCheckbox==false){

                const index = ids.indexOf(id);
                if (index > -1) {
                    ids.splice(index, 1);
                    sessionStorage.setItem('listaChekboxUbicacionAuditapp',JSON.stringify(ids))
                }
            }
        }
    }

    function cargarCheckbox(){
        let lista=JSON.parse(sessionStorage.getItem('listaChekboxUbicacionAuditapp'))
        if(lista){
            for(let i=0;i<lista.length;i++){
                let identificador="#form-check-input-"+lista[i];
                let checkbox=  document.querySelector(identificador)
                checkbox.checked=true
            }
        }
    }
    window.onload = function() {
        cargarCheckbox()
    }
</script>
<script>
    const geReservationsIds = () => {
        return Array.from($('tbody tr').filter( (_, tr) => $(tr).find('input[type="checkbox"]:checked').length > 0)).map(tr =>
            $(tr).find('input[type="checkbox"]:checked').attr('value')
        ).flat()
    };

    document.querySelector("#exhibir-true").addEventListener("click", () => {
        const reservationsIds = geReservationsIds();
        console.log(reservationsIds)
        if(reservationsIds.length < 0) return;
        document.querySelector("#target-append-form").innerHTML +=
            `<form id="set-multiple-exhibido" action="./set-multiple-exhibido?exhibir=true" method="post">
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
            <input type="hidden" name="reservationAmount"    value="${circuitDTO.reservationAmount}"/>
            <input type="hidden" name="reservationStateCode" value="${circuitDTO.reservationState.code}"/>
            <input type="hidden" name="reservationIds" value='`+reservationsIds.join()+`'/>
        </form>`;

        document.querySelector("#set-multiple-exhibido").submit()
    });
    document.querySelector("#exhibir-false").addEventListener("click", () => {
        const reservationsIds = geReservationsIds();
        console.log(reservationsIds)
        if(reservationsIds.length < 0) return;
        document.querySelector("#target-append-form").innerHTML =
        `<form id="set-multiple-exhibido" action="./set-multiple-exhibido?exhibir=false" method="post">
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
            <input type="hidden" name="reservationAmount"    value="${circuitDTO.reservationAmount}"/>
            <input type="hidden" name="reservationStateCode" value="${circuitDTO.reservationState.code}"/>
            <input type="hidden" name="reservationIds" value='`+reservationsIds.join()+`'/>
        </form>`;

        document.querySelector("#set-multiple-exhibido").submit()
    });
</script>