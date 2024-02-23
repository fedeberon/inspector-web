/**
 * Created by federicoberon on 19/11/2019.
 */


var emptyImages = false;

function disabledOptionsNotFounds(){
    $('.modal-body').css('opacity', '0.3');
    $('.load').show();
    var dataToSend = {
        "empresas": document.getElementById('select-empresas').value,
        "elementos" : document.getElementById('select-elementos').value,
        "provincias": document.getElementById('select-provincias').value,
        "formatos": document.getElementById('select-formatos').value,
        "localidades" : document.getElementById('select-localidades').value,
        "medios": document.getElementById('select-medios').value
     };

    var dataJson = JSON.stringify(dataToSend);

    $.ajax({
        url:"/api/filter",
        type:"POST",
        data: dataJson,
        contentType:"application/json; charset=utf-8",
        dataType:"json",
        success: function( data ) {
            checkOptions('#select-empresas' , data.empresas);
            checkOptions('#select-elementos' , data.elementos);
            checkOptions('#select-provincias' , data.provincias);
            checkOptions('#select-formatos' , data.formatos);
            checkOptions('#select-localidades' , data.localidades);
            checkOptions('#select-medios' , data.medios);
            $('.load').hide();
            $('.modal-body').css('opacity', '1');
        },
        error: function(data) {
            $('.load').hide();
            $('.modal-body').css('opacity', '1');

            $.notify({
                title: '<strong>Hubo un problema!</strong>',
                message: 'Se produjo un error al intentar chequear resultados.'
            },
            {
                timer: 'none',
                z_index:2000,
                type: 'danger'
            });
        }
    });
}

function checkOptions(selectToCheck, data){
    $( selectToCheck + ' > option').each(function() {
        $(this).removeAttr("data-subtext");
        if(!data) return;

        var exist = existValue($(this).val(), data);

        if(!exist) {
            $(this).attr("data-subtext" , "(sin datos)");
        }

    });

    $(selectToCheck).selectpicker('refresh').trigger('change');

}


/**
 *
 * @param data Array
 *  @param value
 */
function existValue(value, data){
    for(var i = 0; i < data.length; i++){
        if(data[i] === null) continue;
        if(data[i].descripcion.trim() === value.trim()){
            return true;
        }
    }
    return false;
}

function createCarousel(idUbicacion , idEmpresa , empresa){
    $('.data-ubicacion').html("");
    $('.load').show();
    $('.map-container').css('opacity', '0.3');
    $('.content').css('opacity', '0.3');

    var url = document.getElementById("fileApiUrl").innerHTML+'/file';
    var pathsArray = getAllFilesPathOfParam(url , idEmpresa , 'fotos_map');

    pathsArray = filterFilesPath(pathsArray, idUbicacion);

    var fileNameArray = getFileNames(pathsArray);

    var urlsArray = pathsArray.map(function (path) {
        path.includes("(") || path.includes(")") ? path = path.replace(/\(/g, '%28').replace(/\)/g, '%29') : path;
        return url + path;
    });

    if(urlsArray.length === 0){
        emptyImages = true;
    }else{
        emptyImages = false;
    }

    var idEmpresaVar = $('<input>', {
        type : 'hidden',
        value : idEmpresa,
        id : 'idEmpresa'
    });
    idEmpresaVar.appendTo('.data-ubicacion');

    var idUbicacionVar = $('<input>', {
        type : 'hidden',
        value : idUbicacion,
        id : 'idUbicacion'
    });
    idUbicacionVar.appendTo('.data-ubicacion');

    var empresaVar = $('<input>', {
        type : 'hidden',
        value : empresa,
        id : 'empresa'
    });
    empresaVar.appendTo('.data-ubicacion');



    for (var i = 0; i < urlsArray.length; i++) {
        var a = $('<a>',{
            class : 'fancybox hidden',
            rel : 'group',
            href: urlsArray[i],
            'data-caption': fileNameArray[i] + " - " + empresa,
            'data-fancybox':'gallery',
            'data-buttons' : '["slideShow","fullScreen","thumbs","fb","close"]'
        });
        var img = $('<img>',{
            src: urlsArray[i]
        });
        a.append(img);
        a.appendTo('.data-ubicacion');
    }

    $('.load').hide();
    $('.map-container').css('opacity', '1');
    $('.content').css('opacity', '1');
    $('#modal-info-files').modal('show');

}

function createFolderList(user , foldersArray , urlsArray){

    var folderList = document.getElementById("folders");
    folderList.innerHTML = ""; //clear element

    foldersArray.map(function (folder) {
        var row = folderList.insertRow(-1);
        var cell1 = row.insertCell(0);
        cell1.innerHTML = "<a id='folder-"+ folder +"' class='cursorPointer' onclick='showAuditappFiles(\""+folder+"\" , \""+urlsArray+"\")' title='Ver archivos'><i class='fas fa-folder-open'></i> "+ folder +"</a>";

        var cell2 = row.insertCell(1);
        cell2.innerHTML = "<a class='cursorPointer' onclick='downloadFilesOfUser("+user+", \""+folder+"\")' title='Descargar archivos'><i class='fas fa-file-download text-info'></i></a>";
        cell2.style.textAlign = "center";
    });

}

function initCarouselAuditapp(idUsuario){
    $('.data-auditapp').html("");
    $('.load').show();
    $('.content').css('opacity', '0.3');

    var urlBase = document.getElementById("fileApiUrl").innerHTML+'/fotos_app';
    var pathsArray = getAllFilesPathOfParam(urlBase , idUsuario , 'fotos_app');

    var urlsArray = pathsArray.map(function (path) {
        return urlBase + path;
    });

    if(urlsArray.length === 0){
        $('.load').hide();
        $('.content').css('opacity', '1');

        $.notify({
            title: '<strong>Ups!</strong>',
            message: 'No hay archivos cargados de este usuario: ' + idUsuario
        }, {
            timer: 8000,
            z_index: 2031
        });

        return;
    }

    var regexFilterDate = /\d{1,2}-\d{1,2}-\d{4}/;
    var allFolderOfUser = [];

    urlsArray.map(function (url) {
        allFolderOfUser.push(url.match(regexFilterDate)[0]);
    });

    var foldersArray = [];

    $.each(allFolderOfUser, function(i, folder){
        if($.inArray(folder, foldersArray) === -1) foldersArray.push(folder);
    });

    foldersArray.sort(function(a,b) {
        a = a.replace(/(^|\D)(\d)(?!\d)/g, '$10$2');
        b = b.replace(/(^|\D)(\d)(?!\d)/g, '$10$2');
        a = a.split('-').reverse().join('');
        b = b.split('-').reverse().join('');
        return a.localeCompare(b);
    });

    foldersArray.reverse();

    createFolderList(idUsuario,foldersArray , urlsArray);

    $('.load').hide();
    $('.content').css('opacity', '1');
    $('#modal-folders').modal('show');
}
function initCarouselLogoOfMapCompany(idMapCompany, nameMapCompany){
    $('.files-auditapp').html(""); //clear component
    $('.load').show();
    $('.content').css('opacity', '0.3');

    let urlBase = document.getElementById("fileApiUrl").innerHTML+'/fotos_map';
    console.log(`${urlBase}/map-company-logos/${idMapCompany}/`)

    $.ajax({
        url: `${urlBase}/map-company-logos/${idMapCompany}/`,
        dataType: 'json',
        async: false,
        success: function (data) {
            if(data.length === 0){
                $('.load').hide();
                $('.content').css('opacity', '1');

                $.notify({
                    title: '<strong>Ups!</strong>',
                    message: `La empresa ${nameMapCompany} no tiene logo cargado`,
                }, {
                    timer: 8000,
                    z_index: 2031
                });
                return;
            }
            let pathsArray = getFilesPath(data , "fotos_map");
            pathsArray.map(decodeURI).map( urlImg => {
                let a = $('<a>',{
                    class : 'fancybox hidden',
                    rel : 'group',
                    href: `${urlBase}${urlImg}`,
                    'data-caption':  `Empresa: ${nameMapCompany}`,
                    'data-fancybox':'gallery',
                    'data-buttons' : '["slideShow","fullScreen","thumbs","fb","close"]'
                });
                let img = $('<img>',{
                    src: `${urlBase}${urlImg}`
                });
                $('.files-geoplanning').empty();
                a.append(img);
                a.appendTo('.files-geoplanning');
            });
            showImages('fotos_map');

            $('.load').hide();
            $('.content').css('opacity', '1');
        },
        error: function () {
            $('.load').hide();
            $('.content').css('opacity', '1');

            $.notify({
                title: '<strong>Hubo un problema!</strong>',
                message: 'Se produjo un error al buscar las imagenes del relevamiento.'
            }, {
                type: 'danger'
            });
        }
    });
}

function initCarouselOfRelevamiento(idRelevamiento){
    $('.files-auditapp').html(""); //clear component
    $('.load').show();
    $('.content').css('opacity', '0.3');

    let urlBase = document.getElementById("fileApiUrl").innerHTML+'/fotos_app';
    console.log(urlBase + '/' + idRelevamiento + '/')

    $.ajax({
        url: urlBase + '/' + idRelevamiento + '/',
        dataType: 'json',
        async: false,
        success: function (data) {

            if(data.length === 0){
                $('.load').hide();
                $('.content').css('opacity', '1');

                $.notify({
                    title: '<strong>Ups!</strong>',
                    message: 'No hay archivos cargados de este relevamiento: ' + idRelevamiento
                }, {
                    timer: 8000,
                    z_index: 2031
                });

                return;
            }

            let pathsArray = getFilesPath(data , "fotos_app");
            let fileNameArray = getFileNames(pathsArray);

            let urlsArray = pathsArray.map(function (path) {
                path.includes("(") || path.includes(")") ? path = path.replace(/\(/g, '%28').replace(/\)/g, '%29') : path;
                return urlBase + path;
            });

            for (let i = 0; i < urlsArray.length; i++) {

                let url = urlsArray[i].split('.');
                let extension = url[url.length - 1];
                if(extension === 'mp4'){
                    let a = $('<a>',{
                        class : 'fancybox hidden',
                        rel : 'group',
                        href: urlsArray[i],
                        'data-caption': fileNameArray[i] + " - Relevamiento ID: " + idRelevamiento,
                        'data-fancybox':'gallery',
                        'data-buttons' : '["slideShow","fullScreen","thumbs","fb","close"]',
                        'data-width':"640",
                        'data-height':"360"
                    });
                    a.appendTo('.files-auditapp');
                }else{
                    let a = $('<a>',{
                        class : 'fancybox hidden',
                        rel : 'group',
                        href: urlsArray[i],
                        'data-caption': fileNameArray[i] + " - Relevamiento ID: " + idRelevamiento,
                        'data-fancybox':'gallery',
                        'data-buttons' : '["slideShow","fullScreen","thumbs","fb","close"]'
                    });
                    let img = $('<img>',{
                        src: urlsArray[i]
                    });
                    a.append(img);
                    a.appendTo('.files-auditapp');
                }
            }

            showImages('fotos_app');

            $('.load').hide();
            $('.content').css('opacity', '1');
        },
        error: function () {
            $('.load').hide();
            $('.content').css('opacity', '1');

            $.notify({
                title: '<strong>Hubo un problema!</strong>',
                message: 'Se produjo un error al buscar las imagenes del relevamiento.'
            }, {
                type: 'danger'
            });
        }
    });
}

function getAllFilesPathOfParam(url , param , folderOrigin) {

    var pathsArray = [];

    $.ajax({
        url: url + '/' + param,
        dataType: 'json',
        async: false,
        success: function (data) {
            pathsArray = getFilesPath(data , folderOrigin);
        },
        error: function () {
            $('.load').hide();
            $('.map-container').css('opacity', '1');
            $('.content').css('opacity', '1');

            $.notify({
                title: '<strong>Hubo un problema!</strong>',
                message: 'Se produjo un error al buscar las imagenes de la ubicacion.'
            }, {
                type: 'danger'
            });
        }
    });

    return pathsArray;
}

function getFilesPath(absolutePathsArray , folderOrigin) {

    /* The first group on this regex matches the path needed of an absolute paths string */
    var regexPath = new RegExp(folderOrigin + '([/].*([.]jpg|[.]png|[.]mp4))');

    /* Returns paths that was matched and delete from array the ones that don't */
    return absolutePathsArray.map(function (path) {
        if (regexPath.test(path)) {
            return path.match(regexPath)[1];
        }
    }).filter(function (i) {
        return i;
    });
}

function filterFilesPath(pathsArray , idUbicacion) {
    /* The group on this regex matches the numbers found in the path of the file, this numbers is referring to idUbicacion */
    var regexIdUbicacion = /[/].+[/]([0-9]*)[\s]{0,1}/;

    return pathsArray.filter(function (path) {
        return path.match(regexIdUbicacion)[1] === idUbicacion;
    });
}

function getFileNames(pathsArray){
    /* The group on this regex matches the full file name on the path */
    var regexNameUbicacion = /[/].+[/](.*)/;

    return pathsArray.map(function (path) {
        return path.match(regexNameUbicacion)[1];
    });
}

function showImages(typeFile) {
    if(emptyImages === true){

        $.notify({
            title: '<strong>Ups!</strong>',
            message: 'No hay archivos cargados para el registro seleccionado!'
        }, {
            timer: 8000,
            z_index: 2031
        });
    }

    if(typeFile === 'fotos_map'){
        $.fancybox.defaults.btnTpl.fb = '<button style="font-size: small" data-fancybox-fb onclick="initDeleteFile()" class="fancybox-button fancybox-button--fb" title="Delete">' +
            '<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="trash" class="svg-inline--fa fa-trash fa-w-14" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 750 512">' +
            '<path fill="currentColor" d="M432 32H312l-9.4-18.7A24 24 0 0 0 281.1 0H166.8a23.72 23.72 0 0 0-21.4 13.3L136 32H16A16 16 0 0 0 0 48v32a16 16 0 0 0 16 16h416a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16zM53.2 467a48 48 0 0 0 47.9 45h245.8a48 48 0 0 0 47.9-45L416 128H32z"></path>' +
            '</svg>' +
            '</button>';

    }else{
        $.fancybox.defaults.btnTpl.fb = '<button style="font-size: small" data-fancybox-fb onclick="initDeleteFileApp()" class="fancybox-button fancybox-button--fb" title="Delete">' +
            '<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="trash" class="svg-inline--fa fa-trash fa-w-14" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 750 512">' +
            '<path fill="currentColor" d="M432 32H312l-9.4-18.7A24 24 0 0 0 281.1 0H166.8a23.72 23.72 0 0 0-21.4 13.3L136 32H16A16 16 0 0 0 0 48v32a16 16 0 0 0 16 16h416a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16zM53.2 467a48 48 0 0 0 47.9 45h245.8a48 48 0 0 0 47.9-45L416 128H32z"></path>' +
            '</svg>' +
            '</button>';
    }

    $( '[data-fancybox="gallery"]' ).fancybox({
        buttons : [
            'fb'
        ]
    });

    $.fancybox.open( $('.fancybox'), {
        touch: false
    });
}

function showAuditappFiles(folderDate , urlsArray){

    $('.data-auditapp').html(""); //clear component

    urlsArray =  urlsArray.split(",");

    var regexFilterDate = /\d{1,2}-\d{1,2}-\d{4}/;
    var urlsArrayFilteredForDate = urlsArray.filter(function (url) {
        return url.match(regexFilterDate)[0] === folderDate;
    });

    var regexFolderAndFileName = /[/].+[/](.+[/].*)/;
    var pathsArray = urlsArrayFilteredForDate.map(function (url) {
        return url.match(regexFolderAndFileName)[1];
    });


    for (var i = 0; i < urlsArrayFilteredForDate.length; i++) {

        var url = urlsArrayFilteredForDate[i].split('.');
        var extension = url[url.length - 1];
        if(extension === 'mp4'){
            var a = $('<a>',{
                class : 'fancybox hidden',
                rel : 'group',
                href: urlsArrayFilteredForDate[i],
                'data-caption': pathsArray[i],
                'data-fancybox':'gallery',
                'data-buttons' : '["slideShow","fullScreen","thumbs","fb","close"]',
                'data-width':"640",
                'data-height':"360"
            });
            a.appendTo('.data-auditapp');
        }else{
            var a = $('<a>',{
                class : 'fancybox hidden',
                rel : 'group',
                href: urlsArrayFilteredForDate[i],
                'data-caption': pathsArray[i],
                'data-fancybox':'gallery',
                'data-buttons' : '["slideShow","fullScreen","thumbs","fb","close"]'
            });
            var img = $('<img>',{
                src: urlsArrayFilteredForDate[i]
            });
            a.append(img);
            a.appendTo('.data-auditapp');
        }
    }

    showImages('fotos_app');
}

function initDeleteFile() {
    document.getElementById("fileToDelete").value = $.fancybox.getInstance().current.src;

    $("#modal-delete-file").modal('show');

    $('#myModal2').modal('hide');

}

function initDeleteFileApp() {
    document.getElementById("fileToDelete").value = $.fancybox.getInstance().current.src;

    $("#modal-delete-file").modal('show');

}

function createTableUbicacionInformation(data){
    var table = $('.table-data-ubicaciones').clone();

    var tr = $('<tr>');
    var td = $('<td>');
    td.append(data.mapEmpresa.descripcion);
    td.appendTo(tr);

    var tdDireccion = $('<td>');
    tdDireccion.append(data.direccion);
    tdDireccion.appendTo(tr);
    tr.appendTo(table);

    var tdFormato = $('<td>');
    tdFormato.append(data.mapFormato.descripcion);
    tdFormato.appendTo(tr);
    tr.appendTo(table);

    var tdElemento = $('<td>');
    tdElemento.append(data.mapElemento.descripcion);
    tdElemento.appendTo(tr);
    tr.appendTo(table);

    return table;
}


function deleteFile() {
    let url = document.getElementById("fileToDelete").value;

    url = url.replace('%28', '(');
    url = url.replace('%29', ')');

    $.fancybox.close();
    $("#modal-delete-file").modal('hide');
    $('#modal-info-files').modal('hide');


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
            $('#myModal2').modal('hide');

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

function modificarCoordenadas(id) {

    deleteMarker(id);   var lat = map.getCenter().lat();
    var lng = map.getCenter().lng();
    console.log(lat);
    console.log(lng);

    var latLong = new google.maps.LatLng(lat, lng);

    var marker = new google.maps.Marker({
        id: id,
        position: latLong,
        map: map,
        draggable: true,
        animation: google.maps.Animation.DROP
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
}


function actualizarCoordenadas(id, address, localidad, provincia ){
    var addressToReverseGeocoding;
    /*this if/else statement is building the 'addressToReverseGeocoding' variable
      depending on 'localidad' or 'provincia' are emptys.*/
    if(localidad !== undefined && provincia !== undefined){
        addressToReverseGeocoding = address + ',' + localidad + ',' + provincia;
    }else if(provincia !== undefined){
        addressToReverseGeocoding = address + ',' + provincia;
    }else if (localidad !== undefined){
        addressToReverseGeocoding = address + ',' + localidad;
    }else{
        addressToReverseGeocoding = address;
    }

    var dataToSend = {
        "address": addressToReverseGeocoding
    };

    $.ajax( {
        url: '/map/searchCoordinatesByAdress',
        type: "GET",
        dataType: 'json',
        data: dataToSend,
        beforeSend: function () {

        },
        success:  function (data) {
            deleteMarker(id);

            $("#" + id + "-lat").html(data.location.lat);
            $("#" + id + "-lon").html(data.location.lng);

            var latLong = new google.maps.LatLng(data.location.lat, data.location.lng);
            var marker = new google.maps.Marker({

                id: id,
                class: "marker",
                position: latLong,
                map: map,
                draggable: true,
                animation: google.maps.Animation.DROP

            });

            marker.addListener('dragend', function(event){

                $('#'+id+'-lat').html(event.latLng.lat());
                $('#'+id+'-lng').html(event.latLng.lng());

            });

            var bounds = new google.maps.LatLngBounds();
            var myLatLng = new google.maps.LatLng(data.location.lat, data.location.lng);
            bounds.extend(myLatLng);
            map.fitBounds(bounds);
            $("#" + id + "-save").show();
            $("#" + id + "-update").hide();
        }
    });
}

function guardarCoordenadas(id) {
    var isMapObject = /map/;
    var ajaxUrl = '';
    var lat = $("#" + id + "-lat").html();
    var lng  = $("#" + id + "-lon").html();
    var dataToSend = { "id": id, "latitud": lat, "longitud": lng };

    var dataJson = JSON.stringify(dataToSend);

    for (var i = 0; i < markers.length; i++) { //this for statement is for check if object is MapUbicacion or AppUbicacionRelevamiento and set the correct url.
        if (markers[i].id == id) {
            if(isMapObject.test(markers[i].title)){
                ajaxUrl = '/api/updateCoordenadas';
            }else{
                ajaxUrl = '/api/updateCoordenadasApp'
            }
        }
    }

    $.ajax( {
        url: ajaxUrl,
        type: "POST",
        dataType: 'json',
        data: dataJson,
        beforeSend: function () {
            $("#icon-" + id).removeClass("fa-bars").addClass("fa-spinner fa-spin");
            $("#" + id + "-save").html('Guardando');
        },
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success:  function (data) {
            $("#icon-" + id).removeClass("fa-spinner fa-spin").addClass("fa-bars");
            $("#" + id + "-save").hide();
            $("#" + id + "-update").show();
            $("#" + id + "-save").html('Guardar');

            $.notify({
                title: '<strong>Geolocalizacion guardada exitosamente!</strong>',
                message: 'La nueva posicion del punto seleccionado es lat:' + data.latitud + '. , lng: .' + data.longitud
            }, {
                timer: 8000
            });


        },
        error: function (jqXHR, exception) {
            console.log(jqXHR);
            $.notify({
                title: '<strong>Hubo un problema!</strong>',
                message: 'Se produjo un error al intentar guardar la nueva posicion.'
            }, {
                type: 'danger'
            });
        }
    });

}

function guardarPoligono(id , polygon) {

    var polygonCoords = [];

    if (polygon.id === id) {
        for (var i = 0; i < polygon.getPath().getLength(); i++) {
            var onePolygon = { //vertices
                'lat': polygon.getPath().getAt(i).lat(),
                'lng': polygon.getPath().getAt(i).lng()
            };
            polygonCoords.push(onePolygon);
        }

        var dataToSend = {"id": id, 'polygonLatLong': JSON.stringify(polygonCoords)};

        var dataJson = JSON.stringify(dataToSend);

        $.ajax({
            url: '/api/savePolygon',
            type: "POST",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: dataJson,

            success: function (data) {

                $.notify({
                    title: '<strong>Poligono guardado exitosamente!</strong>',
                    message: 'Se ha persistido correctamente el poligono para el registro: ' + data.id
                }, {
                    type: 'success',
                    timer: 8000
                });

            },
            error: function (data) {
                $.notify({
                    title: '<strong>Hubo un problema!</strong>',
                    message: 'Se produjo un error al intentar guardar el poligono.'
                }, {
                    type: 'danger'
                });
            }
        });

    }

}

var stopDrawing = true;

var polygonsArray = [];

function initDrawingControl(element , id){

    stopDrawing = !stopDrawing;

    var drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: google.maps.drawing.OverlayType.POLYGON,
        drawingControl: true,
        drawingControlOptions: {
            position: google.maps.ControlPosition.TOP_RIGHT,
            drawingModes: ['polygon']
        },
        polygonOptions: {
            id: id,
            fillColor: '#b2b2b2',
            fillOpacity: 0.5,
            strokeWeight: 2,
            strokeColor: '#000000',
            clickable: false,
            editable: false,
            zIndex: 1
        }
    });

    drawingManager.setMap(map);

    google.maps.event.addListener(drawingManager, 'polygoncomplete', function (polygon) {
        document.getElementById('info').innerHTML += "Puntos del pol&iacute;gono:" + "<br>";

        for (var i = 0; i < polygon.getPath().getLength(); i++) {
            document.getElementById('info').innerHTML += polygon.getPath().getAt(i).toUrlValue(6) + "<br>";
        }
        polygonsArray.push(polygon);

        $("#modal-confirm-polyg").modal('show');

        modalConfirm3(function(confirm){
            if(confirm){
                guardarPoligono(id , polygon);
                document.getElementById('info').innerHTML = "";
                drawingManager.setMap(null);

            } else {
                polygon.setMap(null)
                drawingManager.setMap(null);
                document.getElementById('info').innerHTML = "";
                polygon.id = null;
            }
        });
    });
}

var polygons = [];

function initPolygon(element ,id ){

    var coordinates = $('#pol-' + id).val();
    var jsonCoordinates = JSON.parse(coordinates);

    // Construct the polygon.
    var poligono = new google.maps.Polygon({
        id: id,
        paths: jsonCoordinates,
        strokeColor: '#FF0000',
        strokeOpacity: 0.8,
        strokeWeight: 3,
        fillColor: '#FF0000',
        fillOpacity: 0.35,
        visible: true
    });

    polygons.push(poligono);
    poligono.setMap(map);

    poligono.addListener('click', function (event) {

        var contentString = '<b>Poligono seleccionado: </b>'+ this.id + '<br>' + '<br>' + '<button onclick="disablePolygon(' + this.id + ')"><i class="fas fa-eye-slash"></i>Ocultar poligono</button>';

        infoWindow.setContent(contentString);
        infoWindow.setPosition(event.latLng);

        infoWindow.open(map);

    });

    infoWindow = new google.maps.InfoWindow;

}

function disablePolygon(id) {
    var i;
    for (i = 0; i < polygons.length; i++) {
        if (polygons[i].id == id) {
            var polygon = polygons[i];
            polygon.setVisible(false);
            infoWindow.close(map);
        }
    }

}


function showPassword() {
    var x = document.getElementById("inputPassword");

    if (x.type === "password") {
        x.type = "text";
        $('#showPassword').removeClass("fa-eye").addClass("fa-eye-slash");
    } else {
        x.type = "password";
        $('#showPassword').removeClass("fa-eye-slash").addClass("fa-eye");
    }
}

function submitEdit(id) {

    $("#wrapperId").val(id);

    $("#sudmit-" + id).click();
}

function deletePolygon(idUbicacion) {

    var dataToSend = {
        "idUbicacion": idUbicacion
    };

    $.ajax({
        url: '/map/deletePolygon',
        type: "POST",
        dataType: 'json',
        data: dataToSend,

        success: function () {
            $.notify({
                title: '<strong>Poligono borrado exitosamente!</strong>',
                message: 'Se ha borrado correctamente el poligono!'
            }, {
                type: 'success',
                timer: 8000
            });

        },
        error: function (data, textStatus, jqXHR) {
            $.notify({
                title: '<strong>Hubo un problema!</strong>',
                message: 'Se produjo un error al intentar borrar el poligono.'
            }, {
                type: 'danger'
            });
        }
    });
}

function openModal(id){
    $('#' + id ).modal('show')
}

function closeModal(id){
    $('#' + id ).modal('hide')
}

function actualizarEntidades(){

    $('.modal-body').css('opacity', '0.3');
    $('.load').show();

    var poiSectorSelected = $('#select-poiSectores').val().toString();

    if(poiSectorSelected === ""){
        $('.load').hide();
        $('.modal-body').css('opacity', '1');

        $.notify({
            title: '<strong>Error al filtrar Entidades!</strong>',
            message: 'Por favor seleccione al menos un [POIs Sector].'
        }, {
            type: 'warning',
            z_index: 2031
        });

        return;
    }

    var dataToSend = {
        "poiSectorDescripciones" : poiSectorSelected
    };

    $.ajax({
        url: '/poi/findEntidad',
        type: "POST",
        dataType: 'json',
        data: dataToSend,

        success: function( data ) {
            $('.load').hide();
            $('.modal-body').css('opacity', '1');

            var selectPoiEntidad = document.getElementById("select-poiEntidades");
            var selectPoiEntidadOptions = selectPoiEntidad.options;
            var newOptions = [];
            var lengthOptions = selectPoiEntidadOptions.length;

            for(var i = 0; i < lengthOptions; i++ ) {
                selectPoiEntidadOptions.remove(selectPoiEntidadOptions[0]);
            }

            data.map(function (option) {
                var oneOption  = {text: option.descripcion, value: option.descripcion};
                newOptions.push(oneOption);
            });

            newOptions.forEach(function(option){
                selectPoiEntidadOptions.add(new Option(option.text, option.value, option.selected))
            });

            $(selectPoiEntidad).selectpicker('refresh').trigger('change');
        },
        error: function () {
            $('.load').hide();
            $('.modal-body').css('opacity', '1');

            $.notify({
                title: '<strong>Hubo un problema!</strong>',
                message: 'Se produjo un error al intentar cargar el campo de Entidad.'
            }, {
                type: 'danger'
            });
        }

    });
}

function preventSpaceBar(event) {
    if(event.target.value === ""){
        if(event.which === 32)
        {
            event.preventDefault();
            return false;
        }
    }
}

function downloadFilesOfUser(user , date) {
    $('.load').show();
    $('.modal-content').css('opacity', '0.7');
    $('.content').css('opacity', '0.3');

    var urlBase = document.getElementById("fileApiUrl").innerHTML+'/fotos_app';
    var zip = new JSZip();
    var promiseArray = [];

    var pathsArray = getAllFilesPathOfParam(urlBase , user , 'fotos_app');

    var urlsArray = pathsArray.map(function (path) {
        return urlBase + path;
    });

    var regexFilterDate = /\d{1,2}-\d{1,2}-\d{4}/;
    var urlsArrayFilteredForDate = urlsArray.filter(function (url) {
        return url.match(regexFilterDate)[0] === date;
    });

    urlsArrayFilteredForDate.forEach(function (url) {
        promiseArray.push(new Promise(function (resolve, reject) {
            var request = new XMLHttpRequest();
            request.open('GET', url, true);
            request.responseType = 'blob';
            request.onload = function () {
                if(request.status === 200 || request.status === 201 ){
                    var regexFileName = /[/].+[/](.+[/].*)/;
                    var fileName = request.responseURL.match(regexFileName)[1];

                    zip.file(fileName, request.response, {base64: true});
                    resolve();
                }else{
                    reject();
                }
            };

            request.send();
        }));
    });

    Promise.all(promiseArray).then(function () {
        zip.generateAsync({type: "blob"})
            .then(function (content) {
                $('.load').hide();
                $('.modal-content').css('opacity', '1');
                $('.content').css('opacity', '1');

                saveAs(content, "audiencias.zip");
            });
    })
    .catch(function (e) {
        $('.load').hide();
        $('.modal-content').css('opacity', '1');
        $('.content').css('opacity', '1');
        console.log(e);
        $.notify({
            title: '<strong>Hubo un problema!</strong>',
            message: 'Se produjo un error al crear el archivo de descarga.'
        }, {
            type: 'danger'
        });

    });

}

function downloadFilesOfRelevamiento(idRelevamiento , nombreRelevamiento){
    $('.files-auditapp').html(""); //clear component
    $('.load').show();
    $('.content').css('opacity', '0.3');

    nombreRelevamiento = nombreRelevamiento.replace(/ /g,"_");
    let urlBase = document.getElementById("fileApiUrl").innerHTML+'/fotos_app';

    $.ajax({
        url: urlBase + '/' + idRelevamiento + '/',
        dataType: 'json',
        async: false,
        success: function (data) {

            if (data.length === 0) {
                $('.load').hide();
                $('.content').css('opacity', '1');

                $.notify({
                    title: '<strong>Ups!</strong>',
                    message: 'No hay archivos cargados de este relevamiento: ' + idRelevamiento
                }, {
                    timer: 8000,
                    z_index: 2031
                });

                return;
            }

            let pathsArray = getFilesPath(data, "fotos_app");

            let urlsArray = pathsArray.map(function (path) {
                return urlBase + path;
            });

            var zip = new JSZip();
            var promiseArray = [];

            urlsArray.forEach(function (url) {
                promiseArray.push(new Promise(function (resolve, reject) {
                    var request = new XMLHttpRequest();
                    request.open('GET', url, true);
                    request.responseType = 'blob';
                    request.onload = function () {
                        if(request.status === 200 || request.status === 201 ){
                            var regexFileName = /[/].+[/](.+[/].*)/;
                            var fileName = request.responseURL.match(regexFileName)[1];

                            zip.file(fileName, request.response, {base64: true});
                            resolve();
                        }else{
                            reject();
                        }
                    };

                    request.send();
                }));
            });

            Promise.all(promiseArray).then(function () {
                zip.generateAsync({type: "blob"})
                    .then(function (content) {
                        $('.load').hide();
                        $('.content').css('opacity', '1');

                        saveAs(content, nombreRelevamiento + ".zip");
                    });
            })

        },
        error: function () {
            $('.load').hide();
            $('.content').css('opacity', '1');

            $.notify({
                title: '<strong>Hubo un problema!</strong>',
                message: 'Se produjo un error al buscar las imagenes del relevamiento.'
            }, {
                type: 'danger'
            });
        }
    });

}

function createReport(id) {
    $('.load').show();
    $('.modal-content').css('opacity', '0.7');
    $('.content').css('opacity', '0.3');

    $.ajax( {
        url: "/appRespuesta/createReport/" + id,
        type: "GET",
        dataType: 'json',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success:  function (data) {
            if(data.length === 0){
                $('.load').hide();
                $('.modal-content').css('opacity', '1');
                $('.content').css('opacity', '1');

                $.notify({
                    title: '<strong>Hubo un problema!</strong>',
                    message: 'No hay datos cargados para este relevamiento'
                }, {
                    type: 'danger'
                });
                return;
            }

            var dataJsonExport = [];
            var relevamiento = '';
            var project = '';
            // Get all the form labels from the first location, since they all have the
            // same form. Obtaining the form from the first location instead of obtaining
            // it through a new HTTP request is a valid option, since, if there are no
            // locations in the survey, the report cannot be generated.
            const formLabels = JSON.parse(data[0].appRelevamiento.formulario.formulario)
                .map(itemOfAnswerOfForm=>itemOfAnswerOfForm.label)
                .reduce((formLabels, label) => ({ ...formLabels, [label]:''}), {});

            // Functions to map the dates defined in an array to a string
            const customDateToString = ([year, month, day, hour, minute, second]) => `${year}-${month}-${day}T${hour}:${minute}:${second}`;
            const addZeroToStartNumber = (number) => number.toString().length < 2 ? `0${number}` : number;

            data.map(function (item) {
                var answersOfForm = JSON.parse(item.respuesta);
                relevamiento = item.appRelevamiento.nombreRelevamiento;
                project = item.appRelevamiento.project.name;
                var itemJsonExport = {
                    id_ubicacion : item.appUbicacionRelevamiento.appUbicacion.idUbicacion,
                    one_claveUbi: item.appUbicacionRelevamiento.appUbicacion.one_claveUbi,
                    one_orden_s: item.orden,
                    one_circuito: item.circuito,
                    fecha_finalizacion: (item.appUbicacionRelevamiento.fechaFinalizacion ? customDateToString(item.appUbicacionRelevamiento.fechaFinalizacion.map(addZeroToStartNumber)) : ''),
                    nombre_proyecto : project,
                    nombre_relevamiento : relevamiento,
                    direccion: item.appUbicacionRelevamiento.appUbicacion.direccion,
                    cantidad: item.appUbicacionRelevamiento.cantidad,
                    evp: item.appUbicacionRelevamiento.evp,
                    elemento: item.appUbicacionRelevamiento.elemento,
                    anunciante: item.appUbicacionRelevamiento.anunciante,
                    producto: item.appUbicacionRelevamiento.producto,
                    referencias: item.appUbicacionRelevamiento.referencias,
                    localidad: item.appUbicacionRelevamiento.appUbicacion.localidad,
                    provincia: item.appUbicacionRelevamiento.appUbicacion.provincia,
                    latitud : item.appUbicacionRelevamiento.appUbicacion.geo_latitud,
                    longitud: item.appUbicacionRelevamiento.appUbicacion.geo_longitud,
                    ...formLabels
                };
                if(answersOfForm){
                    answersOfForm.map(function (itemOfAnswerOfForm) {
                        if(itemOfAnswerOfForm.label) {
                            if(itemOfAnswerOfForm.type === 'text' || itemOfAnswerOfForm.type === 'textarea'){
                                itemJsonExport[itemOfAnswerOfForm.label] = itemOfAnswerOfForm.userData[0];
                            }else{ // If the input is multiple choice, e.g. select, checkbox, etc.
                                if(itemOfAnswerOfForm.userData) {
                                    itemJsonExport[itemOfAnswerOfForm.label] = itemOfAnswerOfForm.userData.join();
                                }
                            }
                        }
                    });

                }

                dataJsonExport.push(itemJsonExport);

            });

            jsonToXLSXAndDownload(dataJsonExport , relevamiento);

            $('.load').hide();
            $('.modal-content').css('opacity', '1');
            $('.content').css('opacity', '1');
        },
        error: function (jqXHR, exception) {
            $('.load').hide();
            $('.modal-content').css('opacity', '1');
            $('.content').css('opacity', '1');

            $.notify({
                title: '<strong>Hubo un problema!</strong>',
                message: 'Se produjo un error al intentar obtener los resultados.'
            }, {
                type: 'danger'
            });
        }
    });

}

function jsonToXLSXAndDownload(data , title){
    /* make the worksheet */
    var ws = XLSX.utils.json_to_sheet(data);

    // var merge = [
    //     { s: { r: 1, c: 0 }, e: { r: 2, c: 0 } },{ s: { r: 3, c: 0 }, e: { r: 4, c: 0 } },
    // ];
    // ws["!merges"] = merge;

    /* add to workbook */
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Reporte");

    /* generate an XLSX file */
    var fileName = title.replace(/ /g,"_");
    XLSX.writeFile(wb, fileName +".xlsx");
}

function uploadFileData(data){
    let dataFormat = [];

    if (data.length !== 0){
        for (const ubicacion of data){
            dataAux = {
                'appUbicacion' : {
                    'one_claveUbi' : ubicacion.one_claveUbi,
                    'direccion': ubicacion.direccion,
                    'barrio' : ubicacion.barrio,
                    'localidad' : ubicacion.localidad,
                    'provincia' : ubicacion.provincia,
                    'zona' : ubicacion.zona,
                    'geo_latitud' : ubicacion.geo_latitud,
                    'geo_longitud' : ubicacion.geo_longitud,
                    'bajaLogica' : false
                    },
                'appRelevamiento' : {
                    'idRelevamiento' : ubicacion.idRelevamiento
                },
                'cantidad' : ubicacion.cantidad,
                'evp' : ubicacion.evp,
                'elemento': ubicacion.elemento,
                'anunciante': ubicacion.anunciante,
                'producto': ubicacion.producto,
                'referencias': ubicacion.referencias,
                'one_orden_s': ubicacion.one_orden_s,
                'one_circuito': ubicacion.one_circuito,
                'bajaLogica' : false
            }
            dataFormat.push(dataAux);
        }
    }

    dataFormat = JSON.stringify(dataFormat);

    $.ajax( {
        url: "/appUbicacionRelevamiento/saveAll",
        type: "POST",
        dataType: 'json',
        data: dataFormat,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success:  function (data) {
            $('.load').hide();
            $('.content').css('opacity', '1');

            var uploadExcelSuccess = document.querySelector('#uploadExcelSuccess');
            var uploadExcelError = document.querySelector('#uploadExcelError');
            var errorsTable = document.querySelector('#errorsTable');

            uploadExcelError.style.display = "none";
            errorsTable.style.display = "none";
            uploadExcelSuccess.innerHTML = "<p><i class='far fa-check-circle' style='color:green'></i> Se han agregado "+ data[0].length + " registros correctamente.</p>";
            uploadExcelSuccess.style.display = "block";

            if(data[1].length > 0){ //this array has locations are already exists
                uploadExcelError.style.display = "block";
                errorsTable.style.display = "block";

                uploadExcelError.innerHTML = "<p><i class='far fa-times-circle' style='color:darkred'></i> Las siguientes ubicaciones ya existen en la base de datos: </p>";
                buildHtmlTableResponse(data[1]);
            }

        },
        error: function (data) {
            $('.load').hide();
            $('.content').css('opacity', '1');

            var errors = data.responseJSON;

            var uploadExcelSuccess = document.querySelector('#uploadExcelSuccess');
            var uploadExcelError = document.querySelector('#uploadExcelError');

            uploadExcelSuccess.style.display = "none";
            uploadExcelError.style.display = "block";
            if(errors){
                buildHtmlTableResponse(errors);
                uploadExcelError.innerHTML = data.status === 409 ?
                    "<p><i class='far fa-times-circle' style='color:darkred'></i> Todos los registros ya existen en la base de datos seg&uacute;n el n&uacute;mero de ONE_ClaveUbi </p>"
                    :
                    "<p><i class='far fa-times-circle' style='color:darkred'></i> Lo siguientes registros del archivo seleccionado poseen errores: </p>";
            }else{
                uploadExcelError.innerHTML = "<p><i class='far fa-times-circle' style='color:darkred'></i> Lo sentimos, ocurri&oacute; un error al procesar su archivo.</p>";
            }
        }
    });
}

function downloadExcelTemplate(){
    var dataTemplate = [{
        idRelevamiento: null,
        one_claveUbi: null,
        one_orden_s: null,
        direccion: null,
        cantidad: null,
        one_circuito: null,
        evp: null,
        elemento: null,
        anunciante: null,
        producto: null,
        referencias: null,
        barrio: null,
        localidad: null,
        provincia: null,
        zona: null,
        geo_latitud: null,
        geo_longitud: null
    }];

    jsonToXLSXAndDownload(dataTemplate , "formato_de_subida");
}

function buildHtmlTableResponse(data) {
    var table = document.getElementById("errorsTable");
    table.style.display = "block";
    var columns = addAllColumnHeaders(data, table);

    for (var i = 0; i < data.length; i++) {
        var row$ = $('<tr/>');
        for (var colIndex = 0; colIndex < columns.length; colIndex++) {
            var cellValue = data[i][columns[colIndex]];
            if (cellValue == null) cellValue = "";
            if(cellValue.id){
                cellValue = cellValue.id;
            }
            row$.append($('<td/>').html(cellValue));
        }
        $(table).append(row$);
    }
}

// Adds a header row to the table and returns the set of columns.
// Need to do union of keys from all records as some records may not contain
// all records.
function addAllColumnHeaders(data) {
    var table = document.getElementById("errorsTable");
    var columnSet = [];
    var headerTr$ = $('<tr/>');

    for (var i = 0; i < data.length; i++) {
        //Delete useless fields
        delete data[i].idUbicacion;

        var rowHash = data[i];
        for (var key in rowHash) {
            if ($.inArray(key, columnSet) == -1) {
                columnSet.push(key);
                headerTr$.append($('<th/>').html(key));
            }
        }
    }
    $(table).append(headerTr$);

    return columnSet;
}

// Mostrar el fondo y el modal de carga
function showLoader() {
    $(".page-background").show();
    $(".load").show();
}

// Ocultar el fondo y el modal de carga
function hideLoader() {
    $(".page-background").hide();
    $(".load").hide();
}
