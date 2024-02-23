<%--
  Created by IntelliJ IDEA.
  User: guerr
  Date: 16/02/2022
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<style>

    .modal-content {
        margin-top: 0px;
    }

    .hidden{
        display: none;
    }

    .confirm-modal{
        margin-top: 124px;
        z-index: 100000;
    }
    .map-container {
        width: 100%;
        height: 350px;
        max-height: 350px;
    }
    .carousel {
        height: 400px;
    }

    .carousel-item,
    .carousel-inner,
    .carousel-inner img {
        height: 100%;
        object-fit: contain;
        width: 100%;
    }

    .carousel-inner {
        background-color: #777777 !important;
    }

    .carousel-item {
        text-align: center;
    }

    .arrow {
        display: flex;
    }

    .arrow i, .arrow a{
        align-self: flex-end;
    }

    .box_textshadow {
        text-shadow: 2px 2px #000000; /* FF3.5+, Opera 9+, Saf1+, Chrome, IE10 */
    }
    .mytable>tbody>tr>td, .mytable>tbody>tr>th, .mytable>tfoot>tr>td, .mytable>tfoot>tr>th, .mytable>thead>tr>td, .mytable>thead>tr>th {
        padding: 4px;
    }

    .pac-controls label {
        font-family: Roboto;
        font-size: 13px;
        font-weight: 300;
    }

    #pac-input {
        background-color: #fff;
        font-family: Roboto;
        font-size: 15px;
        font-weight: 300;
        margin-top: 12px;
        margin-left: 5px;
        padding: 0 11px 0 13px;
        text-overflow: ellipsis;
        width: 200px;
    }

    #pac-input:focus {
        border-color: #4d90fe;
    }

</style>

<div class="container">
    <div class="row">
        <div class="col-sm-auto arrow" id="arrowLeft">
            <%--<i class="fas fa-arrow-alt-circle-left" style=""></i>--%>
        </div>
        <div class="col-sm">
            <%--Mapa--%>
            <div class="map-container ">
                <input id="pac-input" class="controls" type="text" placeholder="Ingrese un lugar..."/>
                <div id="map"></div>
            </div>
        </div>
        <div class="col-sm">
            <%--Informacion--%>
            <table class="table mytable">
                <thead>
                <tr>
                    <th>Datos del cliente</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th> Id cliente:</th>
                    <td><a href="/appUbicacion/${ubicacionRelevamiento.appUbicacion.id}">${ubicacionRelevamiento.appUbicacion.id}</a></td>
                </tr>
                <tr>
                    <th>Id ruta:</th>
                    <td><a href="/relevamiento/${ubicacionRelevamiento.appRelevamiento.id}">${ubicacionRelevamiento.appRelevamiento.id}</a></td>
                </tr>
                <tr>
                    <th>Nombre de la ruta:</th>
                    <td>${ubicacionRelevamiento.appRelevamiento.nombreRelevamiento}</td>
                </tr>
                <tr>
                    <th>Orden:</th>
                    <td>${ubicacionRelevamiento.oneOrdenS}</td>
                </tr>
                <tr>
                    <th>EVP:</th>
                    <td>${ubicacionRelevamiento.evp}</td>
                </tr>
                <tr>
                    <th>Elemento:</th>
                    <td>${ubicacionRelevamiento.elemento}</td>
                </tr>
                <tr>
                    <th>Producto:</th>
                    <td>${ubicacionRelevamiento.elemento}</td>
                </tr>
                <tr>
                    <th>Anunciante:</th>
                    <td>${ubicacionRelevamiento.anunciante}</td>
                </tr>
                <tr>
                    <th>Direccion:</th>
                    <td>${ubicacionRelevamiento.appUbicacion.direccion}</td>
                </tr>
                <tr>
                    <th>Fecha de guardado:</th>
                    <td>${ubicacionRelevamiento.fechaFinalizacion}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="col-sm-auto arrow" id="arrowRight">
            <%--<i class="fas fa-arrow-alt-circle-right" style=""></i>--%>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-auto arrow">
            <i> </i>
        </div>
        <div class="col-sm">
            <%--Carousel--%>
            <div id="carouselExampleIndicators" class="carousel slide" data-interval="false">
                <ol class="carousel-indicators"></ol>
                <div class="carousel-inner"></div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
                <button class="btn btn-danger mb-2 mt-1" data-toggle="modal" data-target="#modal-delete-file">
                    <i class="fa fa-trash"></i> Borrar imagen
                </button>
            </div>
        </div>
        <div class="col-sm">
            <%--Respuestas--%>
            <form>
                <div id="render-container"></div>
                <c:if test="${not empty respuesta.respuesta}">
                    <button id="btnUpdateRespuesta" type="button" class="btn btn-success mt-2">Guardar</button>
                    <i id="loadIcon" class="fas fa-spinner fa-spin hidden"></i>
                    <i style="color:green" id="successIcon" class='far fa-check-circle hidden'></i>
                </c:if>
            </form>
            <div class="mt-3">
                <a class="btn btn-primary" href="<c:url value='/appUbicacionRevisar/list/${ubicacionRelevamiento.appRelevamiento.id}'/>" role="button">Volver a la lista</a>
            </div>
        </div>
        <div class="col-sm-auto arrow">
            <i> </i>
        </div>
    </div>
</div>





<!-- Modal -->
<div class="modal fade confirm-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" id="modal-delete-file">
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
                <h4 style="margin-top: 0;" id="myModalLabel">&iquest;Esta seguro que desea eliminar esta imagen?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-fill btn-default" data-dismiss="modal" onclick="deleteImage()">Si</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="mi-modal">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">&iquest;Desea modificar la geolocalizaci&oacute;n?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="modal-btn-si">Si</button>
                <button type="button" class="btn btn-primary" id="modal-btn-no">No</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value='/resources/assets/js/plugins/FormBuilder/form-builder.min.js'/>"></script>
<script src="<c:url value='/resources/assets/js/plugins/FormBuilder/form-render.min.js'/>"></script>
<script src="<c:url value="/resources/assets/js/formBuilder.js"/>"></script>
<script>
    //Ejecuta esta funcion cuando el DOM termina de cargar
    $(document).ready(function(){
        //**Crea los dos botones de ir a la ubicacion anterior o siguiente**
        console.log(${previousId});
        if (${previousId} != -1){
            $("#arrowLeft").append('<a href="<c:url value='/appUbicacionRevisar/show/${ubicacionRelevamiento.appRelevamiento.id}/${previousId}'/>"><i class="fas fa-arrow-alt-circle-left fa-3x"></i></a>');
        };
        console.log(${nextId});
        if (${nextId} != -1){
            $("#arrowRight").append('<a href="<c:url value='/appUbicacionRevisar/show/${ubicacionRelevamiento.appRelevamiento.id}/${nextId}'/>"><i class="fas fa-arrow-alt-circle-right fa-3x"></i></a>');
        };

        //**Crea el mapa**
        var latLng = new google.maps.LatLng(`${ubicacionRelevamiento.appUbicacion.latitud}`, `${ubicacionRelevamiento.appUbicacion.longitud}`);
        var mapOptions = {
            zoom: 18,
            center: latLng,
            scrollwheel: true,
            zoomControlOptions: {
                position: google.maps.ControlPosition.LEFT_CENTER
            },
            streetViewControl: false,
        };
        var map = new google.maps.Map(document.getElementById("map"), mapOptions);
        var marker = new google.maps.Marker({
            id: ${ubicacionRelevamiento.appUbicacion.id},
            position: latLng,
            map: map,
            draggable: true,
            animation: google.maps.Animation.DROP
        });
        // Create the search box and link it to the UI element.
        const input = document.getElementById("pac-input");
        const searchBox = new google.maps.places.SearchBox(input);

        map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
        // Bias the SearchBox results towards current map's viewport.
        map.addListener("bounds_changed", () => {
            searchBox.setBounds(map.getBounds());
        });

        // Listen for the event fired when the user selects a prediction and retrieve
        // more details for that place.
        searchBox.addListener("places_changed", () =>{
            const places = searchBox.getPlaces();

            if (places.length == 0) {
                return;
            }
            // For each place, get the icon, name and location.
            const bounds = new google.maps.LatLngBounds();

            places.forEach((place) => {
                if (!place.geometry || !place.geometry.location) {
                    console.log("Returned place contains no geometry");
                    return;
                }
                if (place.geometry.viewport) {
                    // Only geocodes have viewport.
                    bounds.union(place.geometry.viewport);
                } else {
                    bounds.extend(place.geometry.location);
                }
            });
            map.fitBounds(bounds);
        });

        marker.addListener('dragend', function(event){

            $("#mi-modal").modal('show');

            modalConfirm(function(confirm){
                if(confirm){
                    //Acciones si el usuario confirma
                    handleEventToUpdate(event, marker);

                } else {
                    //Acciones si el usuario no confirma
                    $("#mi-modal").modal('hide');
                }
            });
        });


        // Get the location id but in the table, not "id ubicación" itself
        // /appUbicacionRevisar/show/25/163 -> 163
        function getImageId(data) {
            let url = window.location.href;

            let parts = url.split('/');

            let locationId = parts[parts.length - 1];

            return locationId;
        }


        // Get number at end of string before the format is indicated
        // 'fotos_app/132/1232_423_15.jpg' -> 15
        function findMatchingUrls(data, locationId) {
            let matchingUrls = [];

            // Regular expression to find number at end of string
            let regex = new RegExp('_([^_]+)_' + locationId + '\\b');

            matchingUrls = data.filter(url => regex.test(url));

            return matchingUrls;
        }


        //**Crea el carousel**
        $.ajax({
            url: window.fileApiUrl + '/fotos_app/${ubicacionRelevamiento.appRelevamiento.id}/',
            type: "GET",
            dataType: 'json',
            success: function (data) {

                let locationId = getImageId(data);
                console.log("LocationId: ", locationId);

                let matchingUrls = findMatchingUrls(data, locationId);
                console.log("Matching Urls: ", matchingUrls);

                // To only display images that match the location ID in the URL.
                data = data.filter(url => matchingUrls.includes(url));
                console.log("Data after filter: ", data);

                $.each(data, function(i){

                    // To prevent the difference in photo URL between Production and Development.
                    let imageUrl =  data[i].includes('/fotos_app/') ? 
                                    data[i].substring(data[i].indexOf('/fotos_app/')) : 
                                    '/' + data[i].substring(data[i].indexOf('fotos_app/'));

                    $(
                        '<div class="carousel-item"><img src="'+ window.fileApiUrl +
                        imageUrl +
                        '" class="d-block w-100"><div class="carousel-caption d-none d-md-block box_textshadow"><h5>' +
                        imageUrl +
                        '</h5></div></div>'
                    ).appendTo(".carousel-inner");
                    $(
                        '<li data-target="#carouselExampleIndicators" data-slide-to="' +
                        i +
                        '"></li>'
                    ).appendTo(".carousel-indicators");
                })

                $(".carousel-item").first().addClass("active");
                $(".carousel-indicators > li").first().addClass("active");
                $("#carouselExampleIndicators").carousel();
            },
            error: function () {
                alert("Error");
            }

        });

        //**Crea la forma con las respuestas**
        <c:if test="${not empty respuesta.respuesta && not empty respuesta.id}">
            let formData = JSON.stringify(${respuesta.respuesta});
            let formRenderInstance = $('#render-container').formRender({ formData });

            document.getElementById("btnUpdateRespuesta").addEventListener("click", () => {
                let dataToSend = { "idRespuesta": ${respuesta.id}, "respuesta": JSON.stringify(formRenderInstance.userData)};
                let dataJson = JSON.stringify(dataToSend);
                console.log(dataJson);
                $.ajax( {
                    url: "/appUbicacionRevisar/updateRespuesta/",
                    type: "POST",
                    dataType: 'json',
                    data: dataJson,
                    beforeSend: function () {
                        $("#successIcon").addClass("hidden");
                        $("#loadIcon").removeClass("hidden");
                    },
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    success:  function (data) {
                        $("#loadIcon").addClass("hidden");
                        $("#successIcon").removeClass("hidden");
                        <%--document.getElementById("appRespuestaTable").style.backgroundColor = "#0080004a";--%>

                        console.log(data);
                        $.notify({
                            title: '<strong>Cambios guardados exitosamente!</strong>',
                            message: ''
                        }, {
                            timer: 8000
                        });

                    },
                    error: function (jqXHR, exception) {
                        console.log(jqXHR);
                        $("#loadIcon").addClass("hidden");
                        $("#successIcon").addClass("hidden");
                        <%--document.getElementById("appRespuestaTable").style.backgroundColor = "#dc354563";--%>

                        $.notify({
                            title: '<strong>Hubo un problema!</strong>',
                            message: 'Se produjo un error al intentar guardar los cambios.'
                        }, {
                            type: 'danger'
                        });
                    }
                });
            });
        </c:if>
    });

    function deleteImage(){
        //Toma el src de la imagen activa en el carousel
        let url = $('.active').find('img').attr('src');

        $.ajax( {
            url: url,
            type: "DELETE",
            success:  function (response) { //una vez que el archivo recibe el request lo procesa y lo devuelve
                //$("#resultado").html(response);

                $.notify({
                    title: '<strong>Archivo borrado correctamente</strong>',
                    message: 'Por favor vuelva a cargar el gestor para visualizar los cambios.'
                }, {
                    timer: 3000,
                    z_index:2000
                });
            },
            error:function (e){
                $('#modal-delete-file').modal('hide');

                $.notify({
                    title: '<strong>Hubo un problema!</strong>',
                    message: 'Se produjo un error al intentar borrar el archivo.'
                }, {
                    timer: 'none',
                    z_index:2000,
                    type: 'danger'
                });
            }
        });
    }
    <%--¡¡
    var element = document.getElementById("auditapp");
    element.classList.add("active");--%>
</script>





