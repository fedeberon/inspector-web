<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page   import="com.ideaas.services.enums.ReservationState" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    /* Missing classes */
    .input-group > .custom-file:not(:first-child) .custom-file-label, .input-group > .custom-file:not(:first-child) .custom-file-label::before {
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
    }

    .custom-file-label {
        position: absolute;
        top: 0;
        right: 0;
        left: 0;
        z-index: 1;
        height: calc(2.25rem + 2px);
        padding: .375rem .75rem;
        line-height: 1.5;
        color: #495057;
        background-color: #fff;
        border: 1px solid #ced4da;
        border-top-color: rgb(206, 212, 218);
        border-right-color: rgb(206, 212, 218);
        border-bottom-color: rgb(206, 212, 218);
        border-left-color: rgb(206, 212, 218);
        border-radius: .25rem;
        border-top-left-radius: 0.25rem;
        border-bottom-left-radius: 0.25rem;
    }

    .custom-file-input:lang(en) ~ .custom-file-label::after {
        content: "Buscar";
    }

    .custom-file-label::after {
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        z-index: 3;
        display: block;
        height: calc(calc(2.25rem + 2px) - 1px * 2);
        padding: .375rem .75rem;
        line-height: 1.5;
        color: #495057;
        content: "Browse";
        background-color: #e9ecef;
        border-left: 1px solid #ced4da;
        border-radius: 0 .25rem .25rem 0;
    }

    .input-group > .custom-file {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
    }
    .input-group > .custom-file, .input-group > .custom-select, .input-group > .form-control {
        position: relative;
        -webkit-box-flex: 1;
        -ms-flex: 1 1 auto;
        flex: 1 1 auto;
        width: 1%;
        margin-bottom: 0;
    }

    .custom-file {
        position: relative;
        display: inline-block;
        width: 100%;
        height: calc(2.25rem + 2px);
        margin-bottom: 0;
    }

    .input-group > .input-group-append:last-child > .btn:not(:last-child):not(.dropdown-toggle), .input-group > .input-group-append:last-child > .input-group-text:not(:last-child), .input-group > .input-group-append:not(:last-child) > .btn, .input-group > .input-group-append:not(:last-child) > .input-group-text, .input-group > .input-group-prepend > .btn, .input-group > .input-group-prepend > .input-group-text {
        border-top-right-radius: 0;
        border-bottom-right-radius: 0;
    }

    .input-group-text {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        padding: .375rem .75rem;
        margin-bottom: 0;
        font-size: 1rem;
        font-weight: 400;
        line-height: 1.5;
        color: #495057;
        text-align: center;
        white-space: nowrap;
        background-color: #e9ecef;
        border: 1px solid #ced4da;
        border-radius: .25rem;
        border-top-right-radius: 0.25rem;
        border-bottom-right-radius: 0.25rem;
    }

    .input-group-prepend {
        margin-right: -1px;
    }

    .input-group-append, .input-group-prepend {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
    }
</style>
<div class="content">
    <div class="container-fluid">
        <jsp:include page="../component/loader.jsp"/>
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Circuitos</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="dataTableToCompleteList" class="display" style="width:100%">
                            <thead>
                                <tr>
                                    <th>
                                        <div class="form-check">
                                            <label class="form-check-label">
                                                <input type="checkbox" value=""  id="checkbox-all" onclick="selectAll()">
                                                <span class="form-check-sign"></span>
                                            </label>
                                        </div>
                                    </th>
                                    <th>N&uacute;mero</th>
                                    <th class="text-center">Editar</th>
                                    <th class="text-center">Borrar</th>
                                    <th class="text-center">Editar estado</th>
                                    <th class="text-center">Marcar exhibido</th>
                                    <th class="bg-transparent text-dark">
                                        Exhibido
                                    </th>
                                    <th>Cliente</th>
                                    <th>Campa&ntilde;a</th>
                                    <th>Fecha de inicio</th>
                                    <th>Fecha de finalizaci&oacute;n</th>
                                    <th>Empresa</th>
                                    <th>Elemento</th>
                                    <th>Provincia</th>
                                    <th>Localidad</th>
                                    <th>Cantidad</th>
                                    <th>Estado</th>
                                    <th>Ingresar</th>
                                </tr>
                            </thead>
                            <tbody>
                             <%--@elvariable id="bo" type="com.ideaas.services.bean.MapCircuitDTO"--%> 
                                <c:forEach items="${circuits}" var="bo" varStatus="status">
                                <tr
                                    <c:if test="${bo.hasCanceledReservations}">
                                        style="background: #FFCF85!important"
                                    </c:if>
                                >
                                    <td>
                                        <div class="form-check">
                                            <label class="form-check-label mb-n3">
                                                <input class="form-check-input checkbox" data-form="create-reservacion-form load-map-form" type="checkbox" id="form-check-input-${status.index}" name="idAppUbicacionesList" onclick="saveCheck($,{status,index})" value="${status.index}">
                                                <span class="form-check-sign" id="${status.index}"></span>
                                            </label>
                                        </div>
                                    </td>
                                    <td class="bg-transparent text-dark">
                                        <form action="./show" method="post" class="m-0">
                                            <input type="hidden" name="campaignId"           value="${bo.campaignId}"/>
                                            <input type="hidden" name="client"               value="${bo.client}"/>
                                            <input type="hidden" name="clientId"             value="${bo.clientId}"/>
                                            <input type="hidden" name="campaign"             value="${bo.campaign}"/>
                                            <input type="hidden" name="startDate"            value="${bo.startDate}"/>
                                            <input type="hidden" name="finishDate"           value="${bo.finishDate}"/>
                                            <input type="hidden" name="company"              value="${bo.company}"/>
                                            <input type="hidden" name="element"              value="${bo.element}"/>
                                            <input type="hidden" name="province"             value="${bo.province}"/>
                                            <input type="hidden" name="city"                 value="${bo.city}"/>
                                            <input type="hidden" name="companyId"            value="${bo.companyId}"/>
                                            <input type="hidden" name="elementId"            value="${bo.elementId}"/>
                                            <input type="hidden" name="provinceId"           value="${bo.provinceId}"/>
                                            <input type="hidden" name="cityId"               value="${bo.cityId}"/>
                                            <input type="hidden" name="reservationAmount"    value="${bo.reservationAmount}"/>
                                           
                                         
                                            <input type="hidden" name="reservationStateCode" value="${bo.reservationState.code}"/>
                                            <button type="submit" class="border-0 bg-transparent text-info cursorPointer">${status.index + 1}</button>
                                        </form>
                                    </td>
                                    <td class="bg-transparent text-dark">
                                        <form action="./edit" method="post" class="m-0">
                                            <input type="hidden" name="campaignId"           value="${bo.campaignId}"/>
                                            <input type="hidden" name="client"               value="${bo.client}"/>
                                            <input type="hidden" name="clientId"             value="${bo.clientId}"/>
                                            <input type="hidden" name="campaign"             value="${bo.campaign}"/>
                                            <input type="hidden" name="startDate"            value="${bo.startDate}"/>
                                            <input type="hidden" name="finishDate"           value="${bo.finishDate}"/>
                                            <input type="hidden" name="company"              value="${bo.company}"/>
                                            <input type="hidden" name="element"              value="${bo.element}"/>
                                            <input type="hidden" name="province"             value="${bo.province}"/>
                                            <input type="hidden" name="city"                 value="${bo.city}"/>
                                            <input type="hidden" name="companyId"            value="${bo.companyId}"/>
                                            <input type="hidden" name="elementId"            value="${bo.elementId}"/>
                                            <input type="hidden" name="provinceId"           value="${bo.provinceId}"/>
                                            <input type="hidden" name="cityId"               value="${bo.cityId}"/>
                                            <input type="hidden" name="reservationAmount"    value="${bo.reservationAmount}"/>
                                            
                                           
                                            <input type="hidden" name="reservationStateCode" value="${bo.reservationState.code}"/>
                                            <button type="submit" class="border-0 bg-transparent text-info cursorPointer"><img src="/resources/assets/img/icons/edit2.png" alt="edit icon"></button>
                                        </form>
                                    </td>
                                    <td class="text-center bg-transparent text-dark">
                                        <form action="./delete" method="post" class="m-0" id="form${status.index + 1}">
                                            <input type="hidden" name="campaignId"           value="${bo.campaignId}"/>
                                            <input type="hidden" name="client"               value="${bo.client}"/>
                                            <input type="hidden" name="clientId"             value="${bo.clientId}"/>
                                            <input type="hidden" name="campaign"             value="${bo.campaign}"/>
                                            <input type="hidden" name="startDate"            value="${bo.startDate}"/>
                                            <input type="hidden" name="finishDate"           value="${bo.finishDate}"/>
                                            <input type="hidden" name="company"              value="${bo.company}"/>
                                            <input type="hidden" name="element"              value="${bo.element}"/>
                                            <input type="hidden" name="province"             value="${bo.province}"/>
                                            <input type="hidden" name="city"                 value="${bo.city}"/>
                                            <input type="hidden" name="companyId"            value="${bo.companyId}"/>
                                            <input type="hidden" name="elementId"            value="${bo.elementId}"/>
                                            <input type="hidden" name="provinceId"           value="${bo.provinceId}"/>
                                            <input type="hidden" name="cityId"               value="${bo.cityId}"/>
                                            <input type="hidden" name="reservationAmount"    value="${bo.reservationAmount}"/>
                                            
                                          
                                            <input type="hidden" name="reservationStateCode" value="${bo.reservationState.code}"/>
                                            <a href=# data-toggle="modal" data-target="#modal-delete-circuit" data-form="form${status.index + 1}"/>
                                            <img src="/resources/assets/img/icons/delete.png" alt="">
                                        </form>
                                    </td>
                                    <td class="justify-content-center bg-transparent text-dark text-center">
                                        <fmt:parseDate  value="${bo.startDate}"  pattern="yyyy-MM-dd" var="startDate" type="date" />
                                        <fmt:parseDate  value="${bo.finishDate}" pattern="yyyy-MM-dd" var="finishDate" type="date" />
                                        <fmt:formatDate value="${startDate}"  var="formattedStartDate"  pattern="dd-MM-yyyy" />
                                        <fmt:formatDate value="${finishDate}" var="formattedFinishDate" pattern="dd-MM-yyyy" />
                                        <form action="./edited" method="post" id="circuit-confirm-form-${status.index}" class="m-0">
                                            <input type="hidden" name="campaignId" value="${bo.campaignId}"/>
                                            <input type="hidden" name="client"     value="${bo.client}"/>
                                            <input type="hidden" name="clientId"   value="${bo.clientId}"/>
                                            <input type="hidden" name="campaign"   value="${bo.campaign}"/>
                                            <input type="hidden" name="startDate"  value="${bo.startDate}"/>
                                            <input type="hidden" name="finishDate" value="${bo.finishDate}"/>
                                            <input type="hidden" name="company"    value="${bo.company}"/>
                                            <input type="hidden" name="element"    value="${bo.element}"/>
                                            <input type="hidden" name="province"   value="${bo.province}"/>
                                            <input type="hidden" name="city"       value="${bo.city}"/>
                                            <input type="hidden" name="companyId"  value="${bo.companyId}"/>
                                            <input type="hidden" name="elementId"  value="${bo.elementId}"/>

                                            <input type="hidden" name="provinceId" value="${bo.provinceId}"/>
                                            <input type="hidden" name="cityId"     value="${bo.cityId}"/>

                                            <input type="hidden" name="reservationAmount"    value="${bo.reservationAmount}"/>
                                            <input type="hidden" name="updatedStartDate"     value="${formattedStartDate}"/>
                                            <input type="hidden" name="updatedFinishDate"    value="${formattedFinishDate}"/>
                                            <input type="hidden" name="reservationStateCode" value="${ReservationState.CONFIRMED.code}"/>
                                        </form>

                                        <form action="./edited" method="post" id="circuit-not-confirm-form-${status.index}" class="m-0">
                                            <input type="hidden" name="campaignId"   value="${bo.campaignId}"/>
                                            <input type="hidden" name="client"       value="${bo.client}"/>
                                            <input type="hidden" name="clientId"     value="${bo.clientId}"/>
                                            <input type="hidden" name="campaign"     value="${bo.campaign}"/>
                                            <input type="hidden" name="startDate"    value="${bo.startDate}"/>
                                            <input type="hidden" name="finishDate"   value="${bo.finishDate}"/>
                                            <input type="hidden" name="company"      value="${bo.company}"/>
                                            <input type="hidden" name="element"      value="${bo.element}"/>
                                            <input type="hidden" name="province"     value="${bo.province}"/>
                                            <input type="hidden" name="city"         value="${bo.city}"/>
                                            <input type="hidden" name="companyId"    value="${bo.companyId}"/>
                                            <input type="hidden" name="elementId"    value="${bo.elementId}"/>
                                            <input type="hidden" name="provinceId"   value="${bo.provinceId}"/>
                                            <input type="hidden" name="cityId"       value="${bo.cityId}"/>

                                            <input type="hidden" name="reservationAmount"    value="${bo.reservationAmount}"/>
                                            <input type="hidden" name="updatedStartDate"     value="${formattedStartDate}"/>
                                            <input type="hidden" name="updatedFinishDate"    value="${formattedFinishDate}"/>
                                            <input type="hidden" name="reservationStateCode" value="${ReservationState.NOT_CONFIRMED.code}"/>
                                        </form>

                                        <button data-target-modal-interceptor="#circuit-confirm-modal-${status.index}" class="border-0 bg-transparent text-info cursorPointer">
                                            <img src="/resources/assets/img/icons/arrowUp2.png"   alt="arrow up">
                                        </button>

                                        <button data-target-modal-interceptor="#circuit-not-confirm-modal-${status.index}" class="border-0 bg-transparent text-info cursorPointer">
                                            <img src="/resources/assets/img/icons/arrowDown2.png" alt="arrow down">
                                        </button>

                                        <jsp:include page="../modals/updateReservationsModal.jsp">
                                            <jsp:param name="html-modal-id" value="circuit-confirm-modal-${status.index}"/>
                                            <jsp:param name="html-form-id"  value="circuit-confirm-form-${status.index}"/>
                                        </jsp:include>

                                        <jsp:include page="../modals/updateReservationsModal.jsp">
                                            <jsp:param name="html-modal-id" value="circuit-not-confirm-modal-${status.index}"/>
                                            <jsp:param name="html-form-id"  value="circuit-not-confirm-form-${status.index}"/>
                                        </jsp:include>
                                    </td>
                                    <td class="justify-content-center bg-transparent row align-items-start">
                                        <form action="./set-exhibido?exhibido=true" method="post" class="m-0" id="form${status.index + 1}">
                                            <input type="hidden" name="campaignId"           value="${bo.campaignId}"/>
                                            <input type="hidden" name="client"               value="${bo.client}"/>
                                            <input type="hidden" name="clientId"             value="${bo.clientId}"/>
                                            <input type="hidden" name="campaign"             value="${bo.campaign}"/>
                                            <input type="hidden" name="startDate"            value="${bo.startDate}"/>
                                            <input type="hidden" name="finishDate"           value="${bo.finishDate}"/>
                                            <input type="hidden" name="company"              value="${bo.company}"/>
                                            <input type="hidden" name="element"              value="${bo.element}"/>
                                            <input type="hidden" name="province"             value="${bo.province}"/>
                                            <input type="hidden" name="city"                 value="${bo.city}"/>
                                            <input type="hidden" name="companyId"            value="${bo.companyId}"/>
                                            <input type="hidden" name="elementId"            value="${bo.elementId}"/>
                                            <input type="hidden" name="provinceId"           value="${bo.provinceId}"/>
                                            <input type="hidden" name="cityId"               value="${bo.cityId}"/>
                                            <input type="hidden" name="reservationAmount"    value="${bo.reservationAmount}"/>


                                            <input type="hidden" name="reservationStateCode" value="${bo.reservationState.code}"/>

                                            <button class="border-0 bg-transparent text-info cursorPointer">
                                                <i class="fa fa-check-circle" aria-hidden="true"></i>
                                            </button>
                                        </form>
                                        <form action="./set-exhibido?exhibido=false" method="post" class="m-0" id="form${status.index + 1}">
                                            <input type="hidden" name="campaignId"           value="${bo.campaignId}"/>
                                            <input type="hidden" name="client"               value="${bo.client}"/>
                                            <input type="hidden" name="clientId"             value="${bo.clientId}"/>
                                            <input type="hidden" name="campaign"             value="${bo.campaign}"/>
                                            <input type="hidden" name="startDate"            value="${bo.startDate}"/>
                                            <input type="hidden" name="finishDate"           value="${bo.finishDate}"/>
                                            <input type="hidden" name="company"              value="${bo.company}"/>
                                            <input type="hidden" name="element"              value="${bo.element}"/>
                                            <input type="hidden" name="province"             value="${bo.province}"/>
                                            <input type="hidden" name="city"                 value="${bo.city}"/>
                                            <input type="hidden" name="companyId"            value="${bo.companyId}"/>
                                            <input type="hidden" name="elementId"            value="${bo.elementId}"/>
                                            <input type="hidden" name="provinceId"           value="${bo.provinceId}"/>
                                            <input type="hidden" name="cityId"               value="${bo.cityId}"/>
                                            <input type="hidden" name="reservationAmount"    value="${bo.reservationAmount}"/>


                                            <input type="hidden" name="reservationStateCode" value="${bo.reservationState.code}"/>

                                            <button class="border-0 bg-transparent text-info cursorPointer">
                                                <i class="fa fa-times" aria-hidden="true"></i>
                                            </button>
                                        </form>
                                    </td>
                                    <td class="bg-transparent text-dark text-center">
                                        <c:if test="${bo.exhibir}">
                                            <i class="fa fa-check-square-o" aria-hidden="true"></
                                        </c:if>

                                        <c:if test="${bo.exhibir == false}">
                                            <i class="fa fa-square-o" aria-hidden="true"></i>
                                    </c:if>
                                       
                                    </td>
                                    <td class="bg-transparent text-dark" data-client="${bo.client}"       data-clientId="${bo.clientId}"       >${bo.client}</td>
                                    <td class="bg-transparent text-dark" data-campaign="${bo.campaign}"   data-campaignId="${bo.campaignId}"   >${bo.campaign}</td>
                                    <td class="bg-transparent text-dark" data-startDate="${bo.startDate}"                                      >${bo.startDate}</td>
                                    <td class="bg-transparent text-dark" data-finishDate="${bo.finishDate}"                                    >${bo.finishDate}</td>
                                    <td class="bg-transparent text-dark" data-company="${bo.company}"     data-companyId="${bo.companyId}"     >${bo.company}</td>
                                    <td class="bg-transparent text-dark" data-element="${bo.element}"     data-elementId="${bo.elementId}"     >${bo.element}</td>
                                    <td class="bg-transparent text-dark" data-province="${bo.province}"   data-provinceId="${bo.provinceId}"   >${bo.province}</td>
                                    <td class="bg-transparent text-dark" data-city="${bo.city}"           data-cityId="${bo.cityId}"           >${bo.city}</td>
                                    <td class="bg-transparent text-dark" data-reservationAmount="${bo.reservationAmount}">${bo.reservationAmount}</td>
                                    <td class="bg-transparent text-dark" data-reservationStateCode="${bo.reservationState.code}">${bo.reservationState.description}</td>
                                    <td class="bg-transparent text-dark">
                                        <div class="text-center cursorPointer" title="Ingresar">
                                            <form action="./reservaciones/list" method="post" class="m-0">
                                                <input type="hidden" name="campaignId"           value="${bo.campaignId}"/>
                                                <input type="hidden" name="client"               value="${bo.client}"/>
                                                <input type="hidden" name="clientId"             value="${bo.clientId}"/>
                                                <input type="hidden" name="campaign"             value="${bo.campaign}"/>
                                                <input type="hidden" name="startDate"            value="${bo.startDate}"/>
                                                <input type="hidden" name="finishDate"           value="${bo.finishDate}"/>
                                                <input type="hidden" name="company"              value="${bo.company}"/>
                                                <input type="hidden" name="element"              value="${bo.element}"/>
                                                <input type="hidden" name="province"             value="${bo.province}"/>
                                                <input type="hidden" name="city"                 value="${bo.city}"/>
                                                <input type="hidden" name="companyId"            value="${bo.companyId}"/>
                                                <input type="hidden" name="elementId"            value="${bo.elementId}"/>
                                                <input type="hidden" name="provinceId"           value="${bo.provinceId}"/>
                                                <input type="hidden" name="cityId"               value="${bo.cityId}"/>
                                                <input type="hidden" name="reservationAmount"    value="${bo.reservationAmount}"/>
                                                
                                                <input type="hidden" name="reservationStateCode" value="${bo.reservationState.code}"/>
                                                <button type="submit" class="border-0 bg-transparent text-info cursorPointer"><i class="fa fas fa-info-circle"></i></button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="row pt-4 px-4 mx-4">
                            <a href="../../list" class="btn btn-light"><i class="fas fa-angle-double-left pr-2"></i> Campa&ntilde;as</a>
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
                            <button class="btn btn-primary btn-fill" id="btn-export-circuit" onclick="openModal('export-excel-modal')">Exportar circuitos</button>
                            &nbsp;
                        </div>
                        <div id="target-append-form" class="hidden">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

const getCircuitsId = () => {
        const circuitsProps = [
            'campaignId',
            'campaign',
            'clientId',
            'client',
            'startDate',
            'finishDate',
            'company',
            'element',
            'province',
            'city',
            'companyId',
            'elementId',
            'provinceId',
            'cityId',
            'reservationAmount',
            'reservationStateCode'
        ];
const circuitDTOS = [];
circuitDTOS.push(...Array.from($('tr').filter( (_, tr) => $(tr).find('input[type="checkbox"]:checked').length > 0)).map(tr =>
                    circuitsProps.reduce( (circuit, prop) =>  ({...circuit, [prop]: $(tr).find('[data-'+prop+']').attr('data-'+prop)}), {})
                ).flat());
return circuitDTOS;
};

document.querySelector("#exhibir-true").addEventListener("click", () => {
  const circuits = getCircuitsId();
        console.log(circuits)
        if(circuits.length < 0) return;
        document.querySelector("#target-append-form").innerHTML = 
        `<form id="set-multiple-exhibido" action="./set-multiple-exhibido" method="post">
            <input type="hidden" name="circuitsDTOs" value='`+JSON.stringify(circuits)+`'/>
            <input type="hidden" name="exhibir" value="true"/>
        </form>`;
        document.querySelector("#set-multiple-exhibido").submit()
});
document.querySelector("#exhibir-false").addEventListener("click", () => {
    
  const circuits = getCircuitsId();
        console.log(circuits)
        if(circuits.length < 0) return;
        document.querySelector("#target-append-form").innerHTML = 
        `<form id="set-multiple-exhibido" action="./set-multiple-exhibido" method="post">
            <input type="hidden" name="circuitsDTOs" value='`+JSON.stringify(circuits)+`'/>
            <input type="hidden" name="exhibir" value="false"/>
        </form>`;
        document.querySelector("#set-multiple-exhibido").submit()
});
 
</script>



<!-- <%-- Export circuits  --%> -->
<div class="modal fade" id="export-excel-modal" tabindex="-1" role="dialog" aria-labelledby="create-resercaion" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div id="modal-error" class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">No hay circuitos seleccionados</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body row">
                <div class="col">
                    <p>No hay seleccionado ning&uacute;n circuito debe seleccionar al menos un circuito para poder exportarlo.</p>
                </div>
            </div>
        </div>
        <div id="modal-success" class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="exampleModalLabel">Exportar circuitos</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="save" id="form" class="modal-body row"  autocomplete="off" method="post">
                <input type="hidden" id="companyId" value="${circuits.get(0).companyId}" name="companyId">
                <div class="form-group col-6">
                    <div>
                        <p>Camapa&ntilde;a: <span data-export-campaign>${circuits.get(0).campaign}</span></p>
                    </div>
                </div>
                <div class="form-group col-6">
                    <div>
                        <p>Fecha desde: <span data-export-startDate>xxxx-xx-xx</span></p>
                    </div>
                </div>
                <div class="form-group col-6">
                    <div>
                        <p>Cliente: <span data-export-client>${circuits.get(0).client}</span></p>
                    </div>
                </div>
                <div class="form-group col-6">
                    <div>
                        <p>Fecha hasta: <span data-export-endDate>xxxx-xx-xx</span></p>
                    </div>
                </div>
                <div class="form-group col-12">
                    <div class="form-group">
                        <label for="observations">Agregar texto:</label>
                        <textarea class="form-control" name="observations" id="observations" rows="3"></textarea>
                    </div>
                </div>
                <%--
                <!-- No longer needed, the company logo is now fetched from the file server.
                This code is left alone in case it becomes necessary or useful again.-->
                <div class="form-group col-12">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Subir</span>
                        </div>
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" id="company-logo">
                            <label class="custom-file-label" for="company-logo">Elegir logo</label>
                        </div>
                    </div>
                </div> --%>
                <div class="form-group col-6">
                    <label for="listType">Listado</label>
                    <select name="listType" id="listType" class="form-control" title="Seleccione una estado de reservaci&oacute;n">
                        <option value="1">Listado tentativo</option>
                        <option value="2">Listado definitivo</option>
                    </select>
                </div>
                <div class="form-group col-6">
                    <div>
                        <label class="form-check-label" for="subjectAvailability">
                            <input class="form-check-input" type="checkbox" value="true" id="subjectAvailability" value="true" name="isSubjectToAvailability">
                            Sujeto a disponibilidad
                        </label>
                    </div>
                </div>
                <fieldset class="form-group w-100">
                    <legend class="col-form-label col pt-0">Seleccionar columnas a  exportar:</legend>
                    <div class="row m-0">
                        <div class="col-6">
                            <div>
                                <label class="form-check-label" for="referenceId">
                                    <input class="form-check-input" type="checkbox" id="referenceId" value="true" name="includeReferenceId">
                                    Id Referencia
                                </label>
                            </div>
                        </div>
                        <div class="col-6">
                            <div>
                                <label class="form-check-label" for="address">
                                    <input class="form-check-input" type="checkbox" id="address" value="true" name="includeAddress">
                                    Direcci&oacute;n
                                </label>
                            </div>
                        </div>
                        <div class="col-6">
                            <div>
                                <label class="form-check-label" for="city">
                                    <input class="form-check-input" type="checkbox" id="city" value="true" name="includeCity">
                                    Localidad
                                </label>
                            </div>
                        </div>
                        <div class="col-6">
                            <div>
                                <label class="form-check-label" for="element">
                                    <input class="form-check-input" type="checkbox" id="element" value="true" name="includeElement">
                                    Elemento
                                </label>
                            </div>
                        </div>
                        <div class="col-6">
                            <div>
                                <label class="form-check-label" for="coordinates">
                                    <input class="form-check-input" type="checkbox" id="coordinates" value="true" name="includeCoordinates">
                                    Coordenadas
                                </label>
                            </div>
                        </div>
                        <c:forEach items="${parametros}" var="bo">
                        <div class="col-6">
                            <div>
                                <label class="form-check-label" for="${bo.nombre}">
                                    <input class="form-check-input" type="checkbox" id="${bo.nombre}" value='{"${bo.idParametro}": "${bo.nombre}"}' name="parameters[${bo.idParametro}]">
                                    ${bo.nombre}
                                </label>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </fieldset>
            </form>
            <div class="modal-footer d-flex">
                <button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Cerrar</button>

                <button class="btn btn-danger pull-right" id="btn-download-pdf">Exportar a PDF</button>
                &nbsp;
                <button class="btn btn-success pull-right" id="btn-download-excel">Exportar a Excel</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade confirm-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" id="modal-delete-circuit">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <%--<input type="hidden" id="fileToDelete"/>--%>
            <div class="modal-header">
                <h5></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="files-auditapp d-none"></div>
                <h4 style="margin-top: 0;">&iquest;Esta seguro que desea eliminar esta circuito?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-dismiss="modal">No</button>
                <a class="btn btn-fill btn-default">Si</a>
            </div>
        </div>
    </div>
</div>
<script>
    $('#modal-delete-circuit').on('show.bs.modal', function(e) {
        let formId = $(e.relatedTarget).attr('data-form')
        $(".btn-default").click(function(){
            $('#' + formId).submit();
        });
    });
</script>
<script>
    /**
     *
     * jquery.binarytransport.js
     *
     * @description. jQuery ajax transport for making binary data type requests.
     * @version 1.0
     * @author Henry Algus <henryalgus@gmail.com>
     *
     */

    // use this transport for "binary" data type
    $.ajaxTransport("+binary", function(options, originalOptions, jqXHR){
        // check for conditions and support for blob / arraybuffer response type
        if (window.FormData && ((options.dataType && (options.dataType == 'binary')) || (options.data && ((window.ArrayBuffer && options.data instanceof ArrayBuffer) || (window.Blob && options.data instanceof Blob)))))
        {
            return {
                // create new XMLHttpRequest
                send: function(headers, callback){
                    // setup all variables
                    var xhr = new XMLHttpRequest(),
                        url = options.url,
                        type = options.type,
                        async = options.async || true,
                        // blob or arraybuffer. Default is blob
                        dataType = options.responseType || "blob",
                        data = options.data || null,
                        username = options.username || null,
                        password = options.password || null;

                    xhr.addEventListener('load', function(){
                        var data = {};
                        data[options.dataType] = xhr.response;
                        // make callback and send data
                        callback(xhr.status, xhr.statusText, data, xhr.getAllResponseHeaders());
                    });

                    xhr.open(type, url, async, username, password);

                    // setup custom headers
                    for (var i in headers ) {
                        xhr.setRequestHeader(i, headers[i] );
                    }

                    xhr.responseType = dataType;
                    xhr.send(data);
                },
                abort: function(){
                    jqXHR.abort();
                }
            };
        }
    });
</script>
<script>
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
    $(document).ready(() => {
        const circuitsProps = [
            'campaignId',
            'campaign',
            'clientId',
            'client',
            'startDate',
            'finishDate',
            'company',
            'element',
            'province',
            'city',
            'companyId',
            'elementId',
            'provinceId',
            'cityId',
            'reservationAmount',
            'reservationStateCode'
        ]
        const modal = $('#export-excel-modal');
        const [ endDateModal, startDateModal, campaignModal, clientModal ] =
            ['[data-export-endDate]', '[data-export-startDate]', '[data-export-campaign]', '[data-export-client]'].map(selector => $(selector));
        let circuitDTOS = [];

        $('#btn-export-circuit').on('click', function() {
            if($('tr input[type="checkbox"]:checked').length == 0) {
                $('#modal-success').hide();
                $('#modal-error').show();
                return;
            } else {
                $('#modal-success').show();
                $('#modal-error').hide();

                circuitDTOS = [];
                circuitDTOS.push(...Array.from($('tr').filter( (_, tr) => $(tr).find('input[type="checkbox"]:checked').length > 0)).map(tr =>
                    circuitsProps.reduce( (circuit, prop) =>  ({...circuit, [prop]: $(tr).find('[data-'+prop+']').attr('data-'+prop)}), {})
                ).flat());

                const dates = circuitDTOS.reduce( (dates, circuit) => {
                    dates.startDate  = (dates.startDate  && dates.startDate  < circuit.startDate)  ? dates.startDate  : circuit.startDate;
                    dates.finishDate = (dates.finishDate && dates.finishDate > circuit.finishDate) ? dates.finishDate : circuit.finishDate;
                    return dates;
                }, {});

                startDateModal.html(dates.startDate  || 'xxxx-xx-xx');
                endDateModal  .html(dates.finishDate || 'xxxx-xx-xx');
            }
        });

        <%--
        <!-- No longer needed, the company logo is now fetched from the file server.
        This code is left alone in case it becomes necessary or useful again.-->
        async function getImage(inputFile) {
        const toBase64 = file => new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result);
            reader.onerror = error => reject(error);
        });--%>

        $('#btn-download-excel').on("click", async function() {
            if(circuitDTOS.length==0) return;

            $('.content').css('opacity', '0.7');
            $('.load').css('opacity', '1').show();

            const exportCircuitsRequest = {
                endDate: endDateModal.html(),
                startDate: startDateModal.html(),
                campaign: campaignModal.html(),
                client: clientModal.html(),
                ...Object.fromEntries(new FormData(document.getElementById('form')).entries()),
                circuitDTOS
            };

            <%--
            <!-- No longer needed, the company logo is now fetched from the file server.
            This code is left alone in case it becomes necessary or useful again.-->
            async function getImage(inputFile) {
                if (!inputFile.files || !inputFile.files[0]) return;
                return await toBase64(inputFile.files[0])
            }

            exportCircuitsRequest.image = await getImage(document.getElementById('company-logo')) --%>

            Object.keys(exportCircuitsRequest).forEach( key => {
                if(key.startsWith('parameters')) {
                    if(!exportCircuitsRequest.parameters) exportCircuitsRequest.parameters = {};
                    let temp = JSON.parse(exportCircuitsRequest[key]);
                    exportCircuitsRequest.parameters = { ...exportCircuitsRequest.parameters, ...temp};
                }
            });

            $.ajax({
                url: '/campanas/excel-circuits-export',
                type: 'POST',
                dataType: 'html',
                contentType: 'application/json',
                mimeType: 'application/json',
                dataType: "binary",
                processData: false,
                data: JSON.stringify(exportCircuitsRequest),
                async: false,
                success: function (data) {
                    $('.content').css('opacity', '1');
                    $('.load').hide();
                    let blob = data;
                    let a = window.document.createElement('a');
                    a.href = window.URL.createObjectURL(blob);
                    a.download = "Circuitos Exportados.xlsx";
                    document.body.appendChild(a);
                    a.click();
                    document.body.removeChild(a);
                },
                error: function () {
                    $('.content').css('opacity', '1');
                    $('.load').hide();
                    $.notify({
                        title: '<strong>Hubo un problema!</strong>',
                        message: 'Se produjo un error al exportar los circuitos.'
                    }, {
                        type: 'danger'
                    });
                }
            });
        });

        $('#btn-download-pdf').on("click", async function() {
            if(circuitDTOS.length==0) return;

            $('.content').css('opacity', '0.7');
            $('.load').css('opacity', '1').show();

            const exportCircuitsRequest = {
                endDate: endDateModal.html(),
                startDate: startDateModal.html(),
                campaign: campaignModal.html(),
                client: clientModal.html(),
                ...Object.fromEntries(new FormData(document.getElementById('form')).entries()),
                circuitDTOS
            };

            <%--
            <!-- No longer needed, the company logo is now fetched from the file server.
            This code is left alone in case it becomes necessary or useful again.-->
            async function getImage(inputFile) {
                if (!inputFile.files || !inputFile.files[0]) return;
                return await toBase64(inputFile.files[0])
            }

            exportCircuitsRequest.image = await getImage(document.getElementById('company-logo')) --%>

            Object.keys(exportCircuitsRequest).forEach( key => {
                if(key.startsWith('parameters')) {
                    if(!exportCircuitsRequest.parameters) exportCircuitsRequest.parameters = {};
                    let temp = JSON.parse(exportCircuitsRequest[key]);
                    exportCircuitsRequest.parameters = { ...exportCircuitsRequest.parameters, ...temp};
                }
            });

            $.ajax({
                url: '/campanas/pdf-circuits-export',
                type: 'POST',
                dataType: 'html',
                contentType: 'application/json',
                mimeType: 'application/json',
                dataType: "binary",
                processData: false,
                data: JSON.stringify(exportCircuitsRequest),
                async: false,
                success: function (data) {
                    $('.content').css('opacity', '1');
                    $('.load').hide();
                    let blob = data;
                    let a = window.document.createElement('a');
                    a.href = window.URL.createObjectURL(blob);
                    a.download = "Circuitos Exportados.pdf";
                    document.body.appendChild(a);
                    a.click();
                    document.body.removeChild(a);
                },
                error: function () {
                    $('.content').css('opacity', '1');
                    $('.load').hide();
                    $.notify({
                        title: '<strong>Hubo un problema!</strong>',
                        message: 'Se produjo un error al exportar los circuitos.'
                    }, {
                        type: 'danger'
                    });
                }
            });
        });
    });
</script>