<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page   import="com.ideaas.services.enums.ReservationState" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="<c:url value='/resources/assets/js/campanias.js'/>"></script>

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
    <div class="container-fluid">
        <jsp:include page="../component/loader.jsp"/>
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header">
                        <h4 class="card-title">Campa&ntilde;as</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="dataTableToCompleteList" class="display" style="width:100%">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th class="text-center">Editar</th>
                                    <th class="text-center">Clonar</th>
                                    <th class="text-center">Borrar</th>
                                    <th class="text-center">Editar estado</th>
                                    <th class="text-center">Editar exhibido</th>
                                    <th>Exhibido</th>
                                    <th>Cliente</th>
                                    <th>Nombre</th>
                                    <th id="startDateColumnTop">Fecha de inicio</th>
                                    <th>Fecha de finalizaci&oacute;n</th>
                                    <th>Cantidad</th>
                                    <th>Estado</th>
                                    <th>Ingresar</th>
                                    <th>Nro Orden</th>
                                    <th style="width: 75%;">Descargar</th>
                                </tr>
                            </thead>

                            <!--Rows go here-->
                            <tbody>
                            <%--@elvariable id="bo" type="com.ideaas.services.bean.MapCampaignDTO"--%>
                                <c:forEach items="${campaigns}" var="bo" varStatus="status">
                                <tr  data-row-id="${bo.campaignId}"
                                    <c:if test="${bo.hasCanceledReservations}">
                                        style="background: #FFCF85!important"
                                    </c:if>
                                >
                                    <td class="bg-transparent">
                                        <a href="./show/${bo.campaignId}">${bo.campaignId}</a>
                                    </td>
                                    <td class="bg-transparent">
                                        <a href="./edit?campaignId=${bo.campaignId}"><img src="/resources/assets/img/icons/edit2.png" alt="edit icon"></a>
                                    </td>
                                    <td class="bg-transparent">
                                        <a href="./clone?campaignId=${bo.campaignId}"><i class="fa fa-clone text-dark" aria-hidden="true"></i></a>
                                    </td>
                                    <td class="bg-transparent">
                                        <form action="./delete/${bo.campaignId}" method="post" class="m-0" id="form${bo.campaignId}">
                                            <input type="hidden" name="id" value="${bo.campaignId}"/>
                                            <a href=# data-toggle="modal" data-target="#modal-delete-campaign" data-form="form${bo.campaignId}"/>
                                            <img src="/resources/assets/img/icons/delete.png" alt="">
                                        </form>
                                    </td>
                                    <td class="justify-content-center bg-transparent text-dark text-center">
                                        <form action="./edit" method="post" id="campaign-confirm-form-${bo.campaignId}" class="m-0">
                                            <input type="hidden" name="campaignId"           value="${bo.campaignId}"/>
                                            <input type="hidden" name="campaign"             value="${bo.campaign}"/>
                                            <input type="hidden" name="clientId"             value="${bo.clientId}"/>
                                            <input  type="hidden" name="isCloning"           value="false"/>
                                            <input type="hidden" name="reservationStateCode" value="${ReservationState.CONFIRMED.code}"/>
                                        </form>
                                        <form action="./edit" method="post" id="campaign-not-confirm-form-${bo.campaignId}" class="m-0">
                                            <input type="hidden" name="campaignId"           value="${bo.campaignId}"/>
                                            <input type="hidden" name="campaign"             value="${bo.campaign}"/>
                                            <input type="hidden" name="clientId"             value="${bo.clientId}"/>
                                            <input  type="hidden" name="isCloning"           value="false"/>
                                            <input type="hidden" name="reservationStateCode" value="${ReservationState.NOT_CONFIRMED.code}"/>
                                        </form>

                                        <button data-target-modal-interceptor="#campaign-confirm-modal-${bo.campaignId}" class="border-0 bg-transparent text-info cursorPointer">
                                            <img src="/resources/assets/img/icons/arrowUp2.png"   alt="arrow up"/>
                                        </button>

                                        <button data-target-modal-interceptor="#campaign-not-confirm-modal-${bo.campaignId}" class="border-0 bg-transparent text-info cursorPointer">
                                            <img src="/resources/assets/img/icons/arrowDown2.png" alt="arrow down"/>
                                        </button>

                                        <jsp:include page="../modals/updateReservationsModal.jsp">
                                            <jsp:param name="html-modal-id" value="campaign-confirm-modal-${bo.campaignId}"/>
                                            <jsp:param name="html-form-id"  value="campaign-confirm-form-${bo.campaignId}"/>
                                        </jsp:include>

                                        <jsp:include page="../modals/updateReservationsModal.jsp">
                                            <jsp:param name="html-modal-id" value="campaign-not-confirm-modal-${bo.campaignId}"/>
                                            <jsp:param name="html-form-id"  value="campaign-not-confirm-form-${bo.campaignId}"/>
                                        </jsp:include>
                                    </td>
                                    <td class="d-flex justify-content-center bg-transparent text-dark text-center">
                                        <form action="./set-exhibido/${bo.campaignId}?exhibido=true" method="post" class="m-0">
                                            <input type="hidden" name="id" value="${bo.campaignId}"/>
                                            <button class="border-0 bg-transparent text-info cursorPointer">
                                                <i class="fa fa-check-circle" aria-hidden="true"></i>
                                            </button>
                                        </form>
                                        <form action="./set-exhibido/${bo.campaignId}?exhibido=false" method="post" class="m-0">
                                            <input type="hidden" name="id" value="${bo.campaignId}"/>
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
                                    <td data-client="${bo.client}" class="bg-transparent text-dark">
                                            ${bo.client}
                                    </td>
                                    <td data-campaign-name="${bo.campaign}" class="bg-transparent text-dark">
                                            ${bo.campaign}
                                    </td>
                                    <td data-startDate="${bo.startDate}" class="bg-transparent text-dark text-center">
                                        <fmt:parseDate var="parsedDate" value="${bo.startDate}" pattern="yyyy-MM-dd" />
                                        <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />
                                    </td>
                                    <td data-endDate="${bo.finishDate}" class="bg-transparent text-dark text-center">
                                        <fmt:parseDate var="parsedDate" value="${bo.finishDate}" pattern="yyyy-MM-dd" />
                                        <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />
                                    </td>
                                    <td class="bg-transparent text-dark text-center">
                                            ${bo.reservationAmount}
                                    </td>
                                    <td class="bg-transparent text-dark">
                                        ${bo.reservationState.description}
                                    </td>
                                    <td class="bg-transparent text-dark text-center">
                                        <a href="./${bo.campaignId}/circuitos/list"><i class="fa fas fa-info-circle"></i></a>
                                    </td>
                                    <td class="bg-transparent text-effect">
                                        <div class="center pointer" id="order-number-div-${bo.id}" data-toggle="modal" data-target="#exampleModal" data-reserva-id="${bo.id}" data-campania-name="${bo.client}" data-order-number="${bo.orderNumber}" >
                                            <a href="#" id="order-number-${bo.id}">
                                                    ${bo.orderNumber}
                                            </a>
                                            <i class="fas fa-pencil-alt"></i>
                                        </div>
                                    </td>
                                    <td id="export-circuit-cell" class="text-center">
                                        <button data-export-button-campaign="${bo.campaignId}" class="border-0 bg-transparent text-info cursorPointer">
                                            <img src="/resources/assets/img/icons/arrowDown2.png" alt="arrow down">
                                        </button>
                                    </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade confirm-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" id="modal-delete-campaign">
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
                <h4 style="margin-top: 0;">&iquest;Esta seguro que desea eliminar esta campa&ntilde;a?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-dismiss="modal">No</button>
                <a class="btn btn-fill btn-default">Si</a>
            </div>
        </div>
    </div>
</div>

<!-- Export all circuits on campaign -->
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
                        <p>Campa&ntilde;a: <span data-export-campaign></span></p>
                    </div>
                </div>
                <div class="form-group col-6">
                    <div>
                        <p>Fecha desde: <span data-export-startDate>xxxx-xx-xx</span></p>
                    </div>
                </div>
                <div class="form-group col-6">
                    <div>
                        <p>Cliente: <span data-export-client></span></p>
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


<!-- Modal -->
<div class="modal fade modal-confirm" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="order-id">Orden ID:</label>
                        <input type="text" autocomplete="off" class="form-control" id="order-number" placeholder="Ingrese el ID de la orden">
                    </div>

                    <div class="form-group">
                        <input type="hidden" autocomplete="off" class="form-control" id="reserva-id" placeholder="Ingrese el ID de la campaña">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="#" class="btn btn-light pointer" data-dismiss="modal">Cancelar</button>
                <button  class="btn btn-secondary btn-fill pointer" onclick="updateOrderNumber()">Guardar</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">New message</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="recipient-name" class="col-form-label">Recipient:</label>
                        <input type="text" class="form-control" id="recipient-name">
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Message:</label>
                        <textarea class="form-control" id="message-text"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary pointer" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary pointer">Send message</button>
            </div>
        </div>
    </div>
</div>

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
const modal = $('#export-excel-modal');
const [endDateModal, startDateModal, campaignModal, clientModal] = ['[data-export-endDate]', '[data-export-startDate]', '[data-export-campaign]', '[data-export-client]'].map(selector => $(selector));

let campaignId;
let circuitDTOS = [];


$(document).ready(function() {
    let campaignTable = $('#campaignDataTable').DataTable({
        ...window.dataTableSharedConfig,
        initComplete: function () {
            let state = JSON.parse( sessionStorage.getItem( 'DataTables_campaignDataTable_/campanas/list' ) );
            if(!state) {
                $('#startDateColumnTop').click();
                $('#startDateColumnTop').click();
            }
            var api = this.api();
            api.$('tr').click(function (event) {
                if($(this).hasClass("already-load")) return;
                const row = this;
                $(row).find('#export-circuit-cell').click(function () {
                    $(row).addClass("already-load");
                    const campaignProps = [
                        'campaign-name',
                        'client',
                        'startDate',
                        'endDate'
                    ]
                    campaignId = $(this).find('button').attr('data-export-button-campaign');
                    const [campaignName, client, startDate, endDate] = campaignProps.map(prop => ({ el: $(row).find('[data-' + prop + ']'), prop })).map(({ el, prop }) => $(el).attr('data-' + prop))
                    endDateModal.html(endDate);
                    startDateModal.html(startDate);
                    campaignModal.html(campaignName);
                    clientModal.html(client)


                    modal.modal('show');
                    $('#modal-success').show();
                    $('#modal-error').hide();
                });
                if($.contains($(this).find('#export-circuit-cell')[0], event.target)) {
                    $(this).find('#export-circuit-cell')[0].click();
                }
            });
        }
    });

});

$('#btn-download-excel').on("click", async function () {

    $('.content').css('opacity', '0.7');
    $('.load').css('opacity', '1').show();

    const exportCircuitsRequest = {
        endDate: endDateModal.html(),
        startDate: startDateModal.html(),
        campaign: campaignModal.html(),
        client: clientModal.html(),
        campaignId: campaignId,
        ...Object.fromEntries(new FormData(document.getElementById('form')).entries()),
        circuitDTOS
    };
    Object.keys(exportCircuitsRequest).forEach(key => {
        if (key.startsWith('parameters')) {
            if (!exportCircuitsRequest.parameters) exportCircuitsRequest.parameters = {};
            let temp = JSON.parse(exportCircuitsRequest[key]);
            exportCircuitsRequest.parameters = { ...exportCircuitsRequest.parameters, ...temp };
        }
    });

    $.ajax({
        url: '/campanas/excel-campaign-export',
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

$('#btn-download-pdf').on("click", async function () {

    $('.content').css('opacity', '0.7');
    $('.load').css('opacity', '1').show();

    const exportCircuitsRequest = {
        endDate: endDateModal.html(),
        startDate: startDateModal.html(),
        campaign: campaignModal.html(),
        client: clientModal.html(),
        campaignId: campaignId,
        ...Object.fromEntries(new FormData(document.getElementById('form')).entries()),
        circuitDTOS
    };

    Object.keys(exportCircuitsRequest).forEach(key => {
        if (key.startsWith('parameters')) {
            if (!exportCircuitsRequest.parameters) exportCircuitsRequest.parameters = {};
            let temp = JSON.parse(exportCircuitsRequest[key]);
            exportCircuitsRequest.parameters = { ...exportCircuitsRequest.parameters, ...temp };
        }
    });

    $.ajax({
        url: '/campanas/pdf-campaign-export',
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
</script>


<div class="row pt-4 px-2">
    <div class="col-6">
        <button type="submit" form="load-map-form" formaction="maps" formmethod="post" class="btn btn-info btn-fill">
            <i class="fas fa-map-marker-alt"></i>&nbsp;Mapa
        </button>
    </div>
</div>




<!-- Delete campaign -->
<script>
    $('#modal-delete-campaign').on('show.bs.modal', function(e) {
        let formId = $(e.relatedTarget).attr('data-form')
        $(".btn-default").click(function(){
            $('#' + formId).submit();
        });
    });
</script>