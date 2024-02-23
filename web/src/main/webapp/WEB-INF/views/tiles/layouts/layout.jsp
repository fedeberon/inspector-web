<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.ideaas.services.enums.ReservationState" %>

<html lang="en">

<tiles:insertAttribute name="header" />

<body>
    <div class="wrapper">
        <spring:eval expression="@environment.getProperty('spring.api.files.url')" var="fileApiUrl" />
        <span id="fileApiUrl" hidden>${fileApiUrl}</span>
        <script>
            window.fileApiUrl = document.getElementById("fileApiUrl").innerHTML;
        </script>
        <div class="sidebar" data-color="black">
            <tiles:insertAttribute name="menu" />
        </div>

        <div id="body-loader" class="col patern-loader" style="transform: translatey(45vh);position: absolute">
            <div class="col-md-12" style="margin-top: -5%;">
                <div class="loader">
                    <div class="loader-inner box1"></div>
                    <div class="loader-inner box2"></div>
                    <div class="loader-inner box3"></div>
                </div>
            </div>
            <div class="col-md-12"><h5 id="info-loader" style="text-align: center"></h5></div>
        </div>

        <div class="main-panel">

            <tiles:insertAttribute name="navbar" />

            <div id="content-body" class="d-none content">
                <tiles:insertAttribute name="body"  />
            </div>

        </div>

    </div>

    <tiles:insertAttribute name="footer" />


<script>
    $( ".form-check-sign" ).on( "click", function() {
        var check = $( this ).attr('id');
        $('form-check-input-' + check).val(true);
    });

    var selectpickerIsClicked = false;

    $(function () {

        $('#select-formatos').selectpicker({
            container: 'body',
            dropupAuto: false
        });

        $('#select-empresas').selectpicker({
            container: 'body',
            dropupAuto: false
        });

        $('#select-localidades').selectpicker({
            container: 'body',
            dropupAuto: false
        });

        $('#select-medios').selectpicker({
            container: 'body',
            dropupAuto: false
        });


        $('#select-provincias').selectpicker({
            container: 'body',
            dropupAuto: false
        });


        $('#select-elementos').selectpicker({
            container: 'body',
            dropupAuto: false
        });

        $('#select-estados').selectpicker({
            container: 'body',
            dropupAuto: false,
            liveSearch: false
        });

        $('#select-geolocalizacion').selectpicker({
            container: 'body',
            dropupAuto: false,
            liveSearch: false
        });

        $('#select-maxResults').selectpicker({
            container: 'body',
            dropupAuto: false,
            liveSearch: false
        });

        $('#select-poiEntidades').selectpicker({
            container: 'body',
            dropupAuto: false,
            liveSearch: true
        });

        $('#select-poiSectores').selectpicker({
            container: 'body',
            dropupAuto: false,
            liveSearch: true
        });

        $('#select-inspector').selectpicker({
            container: 'body',
            dropupAuto: false
        });

        $('#select-formulario').selectpicker({
            container: 'body',
            dropupAuto: false
        });

        $('#select-clientes').selectpicker({
            container: 'body',
            dropupAuto: false
        });

        $('#select-clientes-reservacion').selectpicker({
            container: 'body',
            dropupAuto: false
        });

        $('#select-estado-reservacion').selectpicker({
            container: 'body',
            dropupAuto: false
        });

        $('.selectpicker-default').selectpicker({
            container: 'body',
            dropupAuto: false
        });

        $('.dropdown-menu').on('click', function (e) {
            if ($(e.target).closest('.bootstrap-select.open').is(':visible') || $(e.target).closest('.btn.dropdown-toggle').is(':visible')) {
                selectpickerIsClicked = true;
            }
        });

        // when the dialog is closed....
            $('.dropdown').on('hide.bs.dropdown', function (e) {
            if (selectpickerIsClicked) {
                e.preventDefault();
                selectpickerIsClicked = false;
            }
        });

        const empresasSelectInput = $('#select-usuario-empresas');
        const tipoUsuarioSelectInput = $('#select-tipo-usuairo');

        if(empresasSelectInput.length > 0) {
            empresasSelectInput.selectpicker({
                container: 'body',
                dropupAuto: false
            });
        }

        if(tipoUsuarioSelectInput.length > 0&&empresasSelectInput.length > 0&&$('#select-tipo-usuairo option[selected]').length > 0) {
            empresasSelectInput.attr('disabled', ($('#select-tipo-usuairo option[selected]').value() != 6));
            tipoUsuarioSelectInput.on( "change", (e) => { empresasSelectInput.attr('disabled', (e.target.value != 6)); });
        }
    });


    $(document).ready(function(){
        $( ".load-data" ).on( "click", function() {
            $(".content").hide();
            $("#info-loader").text("Buscando datos...");
            $(".patern-loader").fadeIn('slow');
        });

        $('[data-toggle = "popover"]').popover({});

        $('[data-toggle="tooltip"]').tooltip();

        $('#dataTableToCompleteList_paginate').on('click', function() {
            $('[data-toggle = "popover"]').popover({});
        });
        /*
        const updateTooltip = () => {
            $('[data-toggle = "popover"]').popover({})
            $('[data-toggle="tooltip"]').tooltip();
        }

        new MutationObserver(updateTooltip).observe(document.querySelector('#dataTableToCompleteList'), {
            childList: true, // observe direct children
        });*/

        $( ".save-data" ).on( "click", function() {
            $(".content").hide();
            $("#info-loader").text("Guardando monitoreo...");
            $(".patern-loader").fadeIn('slow');
        });

        var moreToolsOptions = $('.more-options').clone();
        moreToolsOptions.css('display', 'inline');
        moreToolsOptions.appendTo('#tools-button');

        //map_ubicacion filters
        $('#select-empresas').selectpicker('val', ${ubicacionRequest.empresasSelected});
        $('#select-elementos').selectpicker('val', ${ubicacionRequest.elementosSelected});
        $('#select-formatos').selectpicker('val', ${ubicacionRequest.formatosSelected});
        $('#select-medios').selectpicker('val', ${ubicacionRequest.mediosSelected});
        $('#select-localidades').selectpicker('val', ${ubicacionRequest.localidadesSelected});
        $('#select-provincias').selectpicker('val', ${ubicacionRequest.provinciasSelected});
        $('#select-estados').selectpicker('val' , ${ubicacionRequest.estadoSelected});
        $('#select-geolocalizacion').selectpicker('val' , ${ubicacionRequest.geolocalizacionSelected});
        $('#select-maxResults').selectpicker('val' , ${ubicacionRequest.maxResultsSelected});
        $('#input-ids').val(${ubicacionRequest.idsSearching});
        $('#input-searching').val(${ubicacionRequest.valueSearching});

        //map_ubicacion_actualizaciones filters
        $('#idSearchUbiAct').val(${ubicacionActualizacionRequest.idsSearching});
        $('#maxResultsUbiAct').selectpicker('val' , ${ubicacionActualizacionRequest.maxResultsSelected});

        //map_ubicacion_actualizaciones_especiales filters
        $('#idSearchUbiActEspecial').val(${mapUbiActEspecialRequest.idsSearching});
        $('#maxResultsUbiActEspecial').selectpicker('val' , ${mapUbiActEspecialRequest.maxResultsSelected});

        //map_pois filter
        $('#select-poiEntidades').selectpicker('val' , ${mapPoiRequest.poisEntidadSelected});
        $('#maxResultsPoi').selectpicker('val' , ${mapPoiRequest.maxResultsSelected});
    });

    function showOptions() {
        $('.dt-buttons').slideToggle( 500, function(){

            var text = $('#span-more-options').html();

            if(text === '&nbsp;Mas Opciones'){
                $('#icon-options').removeClass('nc-simple-add').addClass('nc-simple-remove');
                $('#span-more-options').html('&nbsp;Cerrar Opciones');
            }

            else {
                $('#icon-options').removeClass('nc-simple-remove').addClass('nc-simple-add');
                $('#span-more-options').html('&nbsp;Mas Opciones');
            }

        } );
    }


        $( "#arrowUp" ).click(function() {
        $( ".tabla-ubicaciones" ).animate({
            marginTop: "-60vh",
            opacity: 1
        }, 500 );

        $( "#arrowUp" ).hide();
        $( "#arrowDown" ).show();
    });


    $( "#arrowDown" ).click(function() {
        $( ".tabla-ubicaciones" ).animate({
            marginTop: "-15vh",
            opacity: 0.9
        }, 500 );

        $( "#arrowDown" ).hide();
        $( "#arrowUp" ).show();
    });


    $('#collapseExample').on('hidden.bs.collapse', function () {
        // do something…
        $( ".main-panel" ).animate({
            width: "100%"
        }, 100 );

        $( ".sidebar" ).animate({
            width: "0px"
        }, 300 );

        buttonHideShowMenu();
    });


    $('#collapseExample').on('show.bs.collapse', function () {
        $('.main-panel').css('transition', '0.1s');
        $('.main-panel').css('width', '');

        $( ".sidebar" ).animate({
            width: "260px"
        }, 100 );

        buttonHideShowMenu();

    });

    function buttonHideShowMenu() {
        var text = $('#span-close-option').html();

        if(text === '&nbsp;Ocultar Menu'){
            $('.container').css('max-width', '100%');
            $('#icon-close-menu').removeClass('nc-stre-left').addClass('nc-stre-right');
            $('#span-close-option').html('&nbsp;Mostrar Menu');
        }

        else {
            $('#icon-close-menu').removeClass('nc-stre-right').addClass('nc-stre-left');
            $('#span-close-option').html('&nbsp;Ocultar Menu');
        }
    }

</script>
<script>

    function selectAll() {
        var inputCheckAll = document.getElementById('checkbox-all');
        var inputs = document.getElementsByClassName("form-check-input");
        // console.log(inputs)
        if(inputCheckAll.checked){
            iterateInputWithValue(inputs, true);
        }
        else {
            iterateInputWithValue(inputs, false);
        }
    }

    function iterateInputWithValue(inputs, checked) {
        for (var i = 0; i < inputs.length; i++) {
            var input = inputs[i];
            let id=obtenerId(input);
            let selectAll={
                checkbox:checked,
                arriba:true,
                agregarTodas:true
            }
                saveCheck(id,selectAll )
            input.checked = checked;
        }
    }

    function obtenerId(input){
        let id=input.id.split("-")
        return parseInt(id[3])
    }

</script>
<script>
    (function addLoaderToTables() {
        const container = $('#content-body');
        const loader = $('#body-loader');

       $().ready(() => {
                container.removeClass('d-none');
                const tableHead = $($('#content-body')).find('.dataTables_scrollHead');
                if(tableHead.length > 0) {
                    $(tableHead).find('div').css('width','100%')
                    $(tableHead).find('table').css('width','100%')
                }
                $(container).find('#map').length > 0 && ('#map') && container.removeClass('content');
                loader.addClass('d-none');
        });
    })();
</script>
    <script>
        $(document).ready(function () {

            // Format a form's date field matching the pattern dd-MM-yyyy, to yyyy-MM-dd,
            // which is the pattern expected by the server
            function formatFormDates(form, ...dates) {
                const regExpDatePatter = (/^([0-9]{2})[\-]([0-9]{2})[\-]([0-9]{4})$/);
                const testStringDate  = (date) => regExpDatePatter.test(date);
                const splitStringDate = (date) => (date).split(regExpDatePatter).slice(1,-1);
                const formatDate = (date) => {
                    if(date && testStringDate(date)) {
                        const [day, month, year] = splitStringDate(date);
                        return (year+'-'+month+'-'+day);
                    } else {
                        return date;
                    }
                }

                dates.forEach( date => form[date] = formatDate(form[date]) );
            }

            // Display the errors and warnnings during the form validation
            function displayErrors(container, errors) {
                container.empty();
                const displayError = ({icon, title, text, campaigns}) => {
                    return`
                <div id="confirmed-reservations">
                    <h5 class="font-weight-bold"><i class='`+icon+`'></i> `+title+`</h5>
                    <p>`+text+`</p>
                    <ul class="list-group">`+displayErrorCampaigns(campaigns)+`</ul>
                </div>`;
                }
                const displayErrorCampaigns = (campaigns) => campaigns.map(campaign => ('<li class="list-group-item">' + campaign.name + '</li>')).join('');
                container.html(errors.map( error => displayError(error)).join(`<hr>`));
            }

            // The possible current errors and warnnings that can occur during form validation.
            const errors = {
                sameCampaign: {
                    icon: "far fa-times-circle text-danger",
                    title: "Reservaciones duplicadas:",
                    text: "No se puede completar la operación porque ya existe una reservación de la ubicación con las mismas fechas por parte del mismo cliente en la misma campañas",
                },
                alreadyConfirmedCampaigns: {
                    icon: "far fa-times-circle text-danger",
                    title: "Hay campañas con reservaciones confirmadas:",
                    text: "No se puede completar la operación porque hay campañas con reservaciones confirmdas en la misma ubicación con las fechas dadas",
                },
                campaignsWarning: {
                    icon: "fas fa-exclamation-circle text-warning",
                    title: "Hay campañas con reservaciones no confirmadas:",
                    text: "Si confirma la operación se cancelaran reservaciones de la siguientes campañas",
                }
            };

            (function addUpdateReservationsFormsEvents() {
                const CONFIRMED_RESERVATION_CODE = ${ReservationState.CONFIRMED.code};

                $('[data-target-modal-interceptor]').each((_, modalBtn) => {
                    const modal = $($(modalBtn).attr("data-target-modal-interceptor"));
                    const submitBtn = modal.find('.modal-footer button[type="submit"]');
                    const form = $('#' + submitBtn.attr('form'));

                    $(modalBtn).click(function () {
                        let formObj = Object.fromEntries(new FormData(form[0]));
                        formatFormDates(formObj, 'startDate', 'finishDate', 'updatedStartDate', 'updatedFinishDate');
                        $('.content').css('opacity', '0.7');
                        $('.load').show();
                        submitBtn.prop("disabled",true);
                        $.ajax({
                            url: "./can-update",
                            type: "POST",
                            dataType: 'json',
                            data: JSON.stringify(formObj),
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            },
                            success: function ({sameCampaign, campaignWithConfirmedReservations, campaignWithReservations}) {
                                modal.modal('show');
                                $('.content').css('opacity', '1');
                                $('.load').hide();
                                sameCampaign = sameCampaign ? [sameCampaign] : [];
                                let isConfirmingReservations = formObj.reservationStateCode == CONFIRMED_RESERVATION_CODE;
                                const [isSameCampaignError, isAlreadyConfirmedCampaignsError, isCampaignsWarning] = [sameCampaign, campaignWithConfirmedReservations, campaignWithReservations].map((error) => error&&error.length > 0);
                                const errorsArr = [];
                                if(isSameCampaignError) errorsArr.push({...errors.sameCampaign, campaigns: sameCampaign});
                                if(isAlreadyConfirmedCampaignsError) errorsArr.push({...errors.alreadyConfirmedCampaigns, campaigns: campaignWithConfirmedReservations});
                                if(isCampaignsWarning&&isConfirmingReservations) errorsArr.push({...errors.campaignsWarning, campaigns: campaignWithReservations});
                                displayErrors(modal.find('.modal-body'), errorsArr);
                                submitBtn.prop("disabled",(isSameCampaignError||isAlreadyConfirmedCampaignsError));
                            },
                            error: function () {
                                $('.content').css('opacity', '1');
                                $('.load').hide();
                                $.notify({
                                    title: '<strong>Hubo un problema!</strong>',
                                    message: 'Se produjo un error al intentar guardar los cambios.'
                                }, {
                                    type: 'danger'
                                });
                            }
                        });
                    });
                });
            })();
        });
    </script>
</body>
</html>