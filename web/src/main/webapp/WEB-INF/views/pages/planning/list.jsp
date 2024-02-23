<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.ideaas.services.enums.ReservationState" %>
<script src="<c:url value='/resources/assets/js/search.js'/>"></script>
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
</style>


<div class="row">
    <div class="col-md-12">
        <div class="card strpied-tabled-with-hover">
            <div class="card-header ">
                <h4 class="card-title">Planificaci&oacute;n</h4>
                <p class="card-category">Lista</p>
            </div>
            <div class="card-body table-full-width table-responsive">
                <h5 id="check-counter" class="card-text pl-3"></h5>
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
                            <th>ID</th>
                            <th>ID Referencia</th>
                            <th>Empresa</th>
                            <th>Direccion</th>
                            <sec:authorize access="hasAnyRole(
                                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
                            ">
                            <c:forEach items="${parametros}" var="bo">
                            <th>${bo.nombre}</th>
                            </c:forEach>
                            </sec:authorize>
                            <th>Provincia</th>
                            <th>Localidad</th>
                            <th>Elemento</th>
                            <c:forEach items="${rangoFechasReservaciones}" var="fecha">
                            <td>${fecha}</td>
                            </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                    <%--@elvariable id="reservaciones" type="java.util.List"--%>
                    <%--@elvariable id="bo" type="com.ideaas.services.bean.MapPlanningDTO"--%>
                    <c:forEach items="${ubicaciones}" var="bo">
                        <tr>
                            <td>
                                <div class="form-check">
                                    <label class="form-check-label">
                                        <input class="form-check-input checkbox" data-form="create-reservacion-form load-map-form" type="checkbox" id="form-check-input-${bo.id}" name="idAppUbicacionesList" onclick="saveCheck(${bo.id})" value="${bo.id}">
                                        <span class="form-check-sign" id="${bo.id}"></span>
                                    </label>
                                </div>
                            </td>
                            <td>
                                <%-- WIP  <a href="/reservacion/${bo.id}">${bo.id}</a></td>  WIP --%>
                                <a href="#">${bo.id}</a>
                            </td>
                            <td>${bo.referenceId}</td>
                            <td>${bo.company}</td>
                            <td>${bo.address}</td>
                            <sec:authorize access="hasAnyRole(
                                    T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
                                ">
                            <c:forEach items="${parametros}" var="boI" varStatus="statusI">
                            <td>
                            <c:if test="${bo.parameters!=null}">
                            <c:forEach items="${bo.parameters}" var="boJ" varStatus="statusJ">
                            <c:if test="${boJ.parametro.idParametro.equals(boI.idParametro)}">
                                ${bo.parameters[statusJ.index].descripcion}
                            </c:if>
                            </c:forEach>
                            </c:if>
                            </td>
                            </c:forEach>
                            </sec:authorize>
                            <td>${bo.province}</td>
                            <td>${bo.city}</td>
                            <td>${bo.element}</td>
                            <c:forEach items="${rangoFechasReservaciones}" var="fecha">
                            <td
                                <c:choose>
                                <c:when test="${bo.reservationsPerDay.containsKey(fecha)}">
                                <c:choose>
                                <c:when test="${bo.reservationsPerDay.get(fecha).get(0).reservationState == ReservationState.CONFIRMED}">
                                    style="background-color: #FF8585"
                                    data-estado-reservacion="4"
                                </c:when>
                                <c:otherwise>
                                    style="background-color: #85C4FF"
                                    data-estado-reservacion="2"
                                </c:otherwise>
                                </c:choose>
                                </c:when>
                                <c:otherwise>
                                    data-estado-reservacion="1"
                                </c:otherwise>
                                </c:choose>
                            >
                                <c:choose>
                                <c:when test="${bo.reservationsPerDay.containsKey(fecha)}">
                                <span class="w-100 h-100 d-block"
                                        href="#"
                                        data-boundary="window"
                                        data-trigger="hover"
                                        data-html="true"
                                        data-toggle="popover"
                                        title="Lista de clientes"
                                        data-content="
                                        <c:forEach items="${bo.reservationsPerDay.get(fecha)}" var="reservationPerDay">
                                        <p>${reservationPerDay.client}: ${reservationPerDay.campaign}</p>
                                        </c:forEach>"
                                >
                                    ${bo.reservationsPerDay.get(fecha).size()}
                                </span>
                                </c:when>
                                <c:otherwise>
                                    0
                                </c:otherwise>
                                </c:choose>
                            </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="row pt-4 px-2">
                    <div class="col-6">
                        <button type="submit" form="load-map-form" formaction="maps" formmethod="post" class="btn btn-info btn-fill"><i class="fas fa-map-marker-alt"></i>&nbsp;Mapa</button>

                        <sec:authorize access="hasAnyRole(
                            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
                        ">
                            <button type="button" class="btn btn-primary btn-fill" onclick="openModal('create-resercaion')"><i class="fas fa-plus"></i>&nbsp;Confirmar reservas</button>
                        </sec:authorize>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



<%-- Display Crate reservacion modal if it hash errors  --%>
<c:choose>
<c:when test="${mapReservacionCreateModalHashError}">
<script> $(document).ready( () => $('#create-resercaion').modal('show') );</script>
</c:when>
</c:choose>

<%-- Create reservacion modal  --%>
<div class="modal fade" id="create-resercaion" tabindex="-1" role="dialog" aria-labelledby="create-resercaion" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Crear reservaci&oacute;n</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%--@elvariable id="myWrapper" type="com.ideaas.services.bean.Wrapper"--%>
            <form:form action="save" id="form" class="modal-body row"  autocomplete="off" modelAttribute="myWrapper" method="post">
                <jsp:include page="../hiddenForms/mapUbicacionRequestHiddenForm.jsp"/>
                <div class="form-group col-6">
                    <label for="createMapReservacionRequest.mapClientId">Clientes</label>
                    <select data-select-independent data-select-dependent-target="#campaigns-select" name="createMapReservacionRequest.mapClientId" class="form-control" title="Seleccione un cliente">
                        <option value="-1">Seleciones un cliente</option>
                        <c:forEach items="${clientes}" var="bo">
                        <option value="${bo.id}">${bo.nombre}</option>
                        </c:forEach>
                    </select>
                    <form:errors path="createMapReservacionRequest.mapClientId" cssStyle="color: red;"/>
                </div>
                <div class="form-group col-6">
                    <label class="form-check-label">
                        <form:checkbox path="createMapReservacionRequest.isNewCampaignLong" id="createMapReservacionRequest.isNewCampaign" cssClass="mx-3" value="1"/> Crear nueva campa&ntilde;a
                        <%--
                        <input type="checkbox" value=""  checked>
                        <span class="form-check-sign"for="createMapReservacionRequest.isNewCampaign"></span>--%>
                    </label>
                    <div data-is-new-campaign="false">
                        <select data-select-dependent id="campaigns-select" name="createMapReservacionRequest.campaignId" class="form-control" title="Seleciones una campa&ntilde;a">
                            <option data-dependent-from="-1" value="-1">Seleciones una campa&ntilde;a</option>
                            <c:forEach items="${campaigns}" var="bo">
                            <option data-dependent-from="${bo.clientId}" value="${bo.campaignId}">${bo.campaign}</option>
                            </c:forEach>
                        </select>
                        <form:errors path="createMapReservacionRequest.campaignId" cssStyle="color: red;"/>
                    </div>
                    <div data-is-new-campaign="true">
                        <input name="createMapReservacionRequest.campaignName" autocomplete="off" id="createMapReservacionRequest.campaignName"  class="form-control filterInput" placeholder="Ingrese el nombe de la camapa&ntilde;a"/>
                        <form:errors path="createMapReservacionRequest.campaignName" cssStyle="color: red;"/>
                    </div>
                </div>
                <div class="form-group col-6">
                    <label for="createMapReservacionRequest.startDate">Fecha de inicio de reservaci&oacute;n</label>
                    <input name="createMapReservacionRequest.startDate" id="createMapReservacionRequest.startDate" autocomplete="off"  class="form-control datepicker filterInput" placeholder="Seleccione una fecha de inicio de reservaci&oacute;n"/>
                    <form:errors path="createMapReservacionRequest.startDate" cssStyle="color: red;"/>
                </div>
                <div class="form-group col-6">
                    <label for="createMapReservacionRequest.finishDate">Fecha de finalizaci&oacute;n de reservaci&oacute;n</label>
                    <input name="createMapReservacionRequest.finishDate" id="createMapReservacionRequest.finishDate" autocomplete="off"  class="form-control datepicker filterInput" placeholder="Seleccione una fecha de finalizaci&oacute;n de reservaci&oacute;n"/>
                    <form:errors path="createMapReservacionRequest.finishDate" cssStyle="color: red;"/>
                </div>
                <div class="form-group col-6">
                    <label for="createMapReservacionRequest.reservationStateCode">Tipo de reservaci&oacute;n</label>
                    <select name="createMapReservacionRequest.reservationStateCode" id="createMapReservacionRequest.reservationStateCode" class="form-control" title="Seleccione una estado de reservaci&oacute;n">
                        <option value="${ReservationState.NOT_CONFIRMED.code}">${ReservationState.NOT_CONFIRMED.description}</option>
                        <option value="${ReservationState.CONFIRMED.code}">${ReservationState.CONFIRMED.description}</option>
                    </select>
                    <form:errors path="createMapReservacionRequest.reservationStateCode" cssStyle="color: red;"/>
                </div>
                <div class="form-group col-6">
                    <label for="createMapReservacionRequest.ordenNumber">Orden numero</label>
                    <input name="createMapReservacionRequest.ordenNumber" autocomplete="off" id="createMapReservacionRequest.ordenNumber"  class="form-control filterInput" placeholder="Ingrese el numero de orden"/>
                    <form:errors path="createMapReservacionRequest.reservationStateCode" cssStyle="color: red;"/>
                </div>
                <c:choose>
                <c:when test="${mapReservacionSelectedLocationHashError}">
                <div class="form-group col-12">
                    <p><strong>Error con las ubicaiones seleccionadas:</strong></p>
                    <form:errors path="idAppUbicacionesList"/>
                </div>
                </c:when>
                </c:choose>
            </form:form>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary pointer" data-dismiss="modal">Cerrar</button>

                <button form="form" formaction="save" class="btn btn-primary pointer">Confirmar</button>
            </div>
        </div>
    </div>
</div>

<%-- Select random reservacion modal  --%>
<div class="modal fade" id="select-random-resercaion" tabindex="-1" role="dialog" aria-labelledby="create-resercaion" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Crear reservaci&oacute;n</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div action="save" id="form" class="modal-body row"  autocomplete="off" modelAttribute="myWrapper" method="post">
                <div class="form-group col-6">
                    <label>Cantidad de ubicaciones</label>
                    <input id="select-random-cant-reservacion" type="number" min="0" max="${ubicaciones.size()}" value="0" utocomplete="off"  class="form-control filterInput" placeholder="Seleccione una cantidad de ubicaciones" onkeypress="return validateNumber(event)">
                </div>
                <div class="form-group col-6">
                    <label>Tipo de reservaci&oacute;n</label>
                    <select id="select-random-reservacion-estado" multiple class="form-control selectpicker-default" title="Seleccione una estado de reservaci&oacute;n">
                        <option value="${ReservationState.AVAILABLE.code}">${ReservationState.AVAILABLE.description}</option>
                        <option value="${ReservationState.NOT_CONFIRMED.code}">${ReservationState.NOT_CONFIRMED.description}</option>
                        <option value="${ReservationState.CONFIRMED.code}">${ReservationState.CONFIRMED.description}</option>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>

                <button type="button"  data-dismiss="modal" id="buton-select-random-reservaciones" class="btn btn-primary">Aceptar</button>
            </div>
        </div>
    </div>
</div>

<%-- Navbar item for mapUbicacion search modal --%>
<li class="nav-item more-options" style="display: none">
    <a href="#" class="nav-link" onclick="openModal('searchModal')">
        <i class="nc-icon nc-zoom-split"></i>
        <span class="d-lg-block">&nbsp;Buscar</span>
    </a>
</li>

<c:if test="${displaySelectRandomLocations}">
<%-- Navbar item for select random reservacion modal --%>
<li class="nav-item more-options" style="display: none">
    <a href="#" class="nav-link" onclick="openModal('select-random-resercaion')">
        <i class="fa fa-random"></i>
        <span class="d-lg-block">&nbsp;Seleccionar al azar</span>
    </a>
</li>
</c:if>

<%-- The search modal for mapUbicacion --%>
<jsp:include page="../modals/filterUbicacion.jsp"/>

<%-- Hidden form to save search filter values, Search the button with the form attribute equals to "load-map-form"--%>
<form id="load-map-form" action="/reservacion/maps" method="post">
    <jsp:include page="../hiddenForms/mapUbicacionRequestHiddenForm.jsp"/>
</form>

<%-- Select item from sidebar && add search filter --%>
<script>
    var element = document.getElementById("geoplanning");
    element.classList.add("active");
    const updateNumberSelectedRows =
        () => $('#check-counter').text('Seleccionados '+getNumberSelectedRows()+' de '+getNumberTotalRows()+'');
        const getNumberSelectedRows = () => $('#dataTableToCompleteList tbody tr input.checkbox:checked').length;
        const getNumberTotalRows    = () => $('#dataTableToCompleteList').DataTable().rows()[0].length;
    $(document).ready(function(){
        var valueOfSearchingDataTable = sessionStorage['ubicacionSearchDataTable'] || '';
        $('.dataTables_filter input[type="search"]').val(valueOfSearchingDataTable).keyup();

        $('.dataTables_filter input').unbind().keyup(function(e) {

            var valueSearchDataTable = $(this).val();
            if (valueSearchDataTable.length>=1) {
                dataTableToCompleteList.search(valueSearchDataTable).draw();
                sessionStorage['ubicacionSearchDataTable'] = valueSearchDataTable;

            } else {
                dataTableToCompleteList.search('').draw();
                sessionStorage['ubicacionSearchDataTable'] = "";
            }
        });

        // // 0. Events to update the counter of selected records
        // const getNumberSelectedRows = () => $('#dataTableToCompleteList tbody tr input.checkbox:checked').length;
        // const getNumberTotalRows    = () => $('#dataTableToCompleteList').DataTable().rows()[0].length;
        // 1. Adding event to detect when a checkbox is checked
        // const updateNumberSelectedRows =
        //     () => $('#check-counter').text('Seleccionados '+getNumberSelectedRows()+' de '+getNumberTotalRows()+'');
        // // 2. Adding event to detect rows change
        $(`#dataTableToCompleteList tbody tr input.checkbox, #checkbox-all` ).on('change', updateNumberSelectedRows);
        new MutationObserver(updateNumberSelectedRows).observe(document.querySelector('#dataTableToCompleteList'), {
            childList: true, // observe direct children
        });
        // 3. Calling the function to update the counter, in case there are already selected locations
        updateNumberSelectedRows();
    });
    $('#buton-select-random-reservaciones').click(function() {
        const inputNumberReservacion = $('#select-random-cant-reservacion');
        const selectedCheckbox = [];
        const isReservacionesfiltered = $('td[data-estado-reservacion]').length > 0;
        Array.from($(`#dataTableToCompleteList tbody tr input.checkbox`)).forEach( check => check.checked = false);
        const inputCheckboxes = Array.from($(`#dataTableToCompleteList tbody tr`))
            .filter( tr => !isReservacionesfiltered ||
                $('#select-random-reservacion-estado').val().length == 0 ||
                tr.querySelectorAll(
                    $('#select-random-reservacion-estado').val()
                        .map(value => 'td[data-estado-reservacion="'+value+'"]').join()
                ).length>0
            ).map(tr => $(tr).find('input')[0] );

        let cantRandomCheckbox= Number(inputNumberReservacion.val());
        if(cantRandomCheckbox > inputCheckboxes.length) cantRandomCheckbox = inputCheckboxes.length;
        console.log(cantRandomCheckbox, inputCheckboxes.length);
        while(cantRandomCheckbox > 0) {
            selectedCheckbox.push(
                ...inputCheckboxes
                    .splice(inputCheckboxes
                        .indexOf(inputCheckboxes[Math.floor(inputCheckboxes.length * Math.random())]), 1));
            cantRandomCheckbox--;
        };
        selectedCheckbox.forEach( check => check.checked = true);
        updateNumberSelectedRows ();
    });
    function validateNumber(e) {
        const pattern = /^[0-9]$/;
        const inputNumberReservacion = $('#select-random-cant-reservacion');
        const inputCheckboxes = Array.from(document.querySelectorAll('#dataTableToCompleteList tbody tr input.checkbox'));
        const maxValue = inputCheckboxes.length;
        return pattern.test(e.key ) && Number(inputNumberReservacion.val()) >= 0 && (Number(inputNumberReservacion.val()+e.key) <= maxValue);
    }



</script>

<%-- Store selected checkboxs in local storage --%>
<script>
    function saveCheck(id,selectAllCheckbox){
        selectAllCheckbox = selectAllCheckbox || false;

        let ids=JSON.parse(sessionStorage.getItem('listaChekboxUbicacionGeoplaning'))
        if(ids==null){ids=[]}
        let identificadorInput= "#form-check-input-"+id
        let input=document.querySelector(identificadorInput)

        if(selectAllCheckbox.arriba==true   ) {
            if (!ids.includes(id)) {
                ids.push(id)
                sessionStorage.setItem('listaChekboxUbicacionGeoplaning', JSON.stringify(ids))
            }
        }
        if(selectAllCheckbox.checkbox==false){
            sessionStorage.setItem('listaChekboxUbicacionGeoplaning',JSON.stringify([]))
        }else{
            if(!ids.includes(id)){
                ids.push(id)
                sessionStorage.setItem('listaChekboxUbicacionGeoplaning',JSON.stringify(ids))
            }else if(ids.includes(id)&&selectAllCheckbox.agregarTodas!=undefined&&(selectAllCheckbox.arriba==false)||selectAllCheckbox==false){

                const index = ids.indexOf(id);
                if (index > -1) {
                    ids.splice(index, 1);
                    sessionStorage.setItem('listaChekboxUbicacionGeoplaning',JSON.stringify(ids))
                }
            }
        }
    }

    function cargarCheckbox(){
        let lista=JSON.parse(sessionStorage.getItem('listaChekboxUbicacionGeoplaning'))
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

<%-- Script to add a inputs to multiple forms --%>
<script>
    document.querySelectorAll("form").forEach( form => {
        $(form).submit(function() {
            document.querySelectorAll('[data-form*="'+form.id+'"]')
                .forEach( input => input.setAttribute("form", form.id) );
        });
    });
</script>

<script>
    $(document).ready(() => {
        // Add dependent select events
        // $('[data-select-independent]').each((_, independentSelect) => {
        //     const dependentSelect = $(independentSelect.getAttribute('data-select-dependent-target'));
        //     $(independentSelect).on('change', () => {
        //         (dependentSelect.find('option')).each( (_, option) => {
        //             $(option).prop('data-dependent-from') == $(independentSelect).val() ?
        //                 $(option).show() : $(option).hide();
        //             $(dependentSelect).val([-1]);
        //         });
        //     });
        // });

        $('[data-select-independent]').each((_, independentSelect) => {
            independentSelect = $(independentSelect);
            const dependentSelect = $(independentSelect.attr('data-select-dependent-target'));
            const options = dependentSelect.find('option').remove();
            const addSelectChangeEvent = () => {
                dependentSelect.empty();
                (options).each( (_, option) => {
                    // val() returns a single value when a single element is selected, and an array with more than one
                    // wrapping the values with [] and using flat() fixes this difference and allows you to use the some() method
                    if([independentSelect.val()].flat().some( independentId => independentId == $(option).attr('data-dependent-from'))) {
                        dependentSelect.append(option)
                    }
                    dependentSelect.val([-1]);
                });
                dependentSelect.selectpicker('refresh');
            };
            addSelectChangeEvent();
            $(independentSelect).on('change', addSelectChangeEvent);
        });

        // Add conditional campaign name

        const checkboxIsNewCampaign = $('[id="createMapReservacionRequest.isNewCampaign"]');
        const toggleCampaignInput = function() {
            const isChecked = checkboxIsNewCampaign.prop('checked');
            $('[data-is-new-campaign="'+( isChecked)+'"]').show();
            $('[data-is-new-campaign="'+(!isChecked)+'"]').hide();
        }
        toggleCampaignInput();
        checkboxIsNewCampaign.on('change', toggleCampaignInput);

        const updateTooltip = () => {
            $('[data-toggle = "popover"]').popover({})
            $('[data-toggle="tooltip"]').tooltip();
        }

        new MutationObserver(updateTooltip).observe(document.querySelector('#dataTableToCompleteList'), {
            childList: true, // observe direct children
        });
    });
</script>

<style>

    select:not(:valid) {
        border: 1px solid red;
    }

    select:not(:valid) + .followup {
        display: none;
    }
</style>