<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style>
    #rendered-form {
        max-width: 90%;
        margin: auto;
        border-style: outset;
        border-radius: 5px;
        padding: 10px;
    }
    #errorsTable{
        width: 100%;
        overflow-x: scroll;
    }
</style>
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
                        <h4 class="card-title">Clientes para Ruta</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <%--@elvariable id="myWrapper" type="com.ideaas.services.bean.Wrapper"--%>
                        <form:form modelAttribute="myWrapper" name="myWrapper" id="myWrapper" method="post">
                            <table id="dataTableToCompleteList" class="display" style="width:100%">
                                <thead>
                                    <th>
                                        <div class="form-check">
                                            <label class="form-check-label">
                                                <input type="checkbox" value=""  id="checkbox-all" onclick="selectAll()">
                                                <span class="form-check-sign"></span>
                                            </label>
                                        </div>
                                    </th>
                                    <th>ID</th>
                                    <th class="text-center">Editar</th>
                                    <th>ID Ruta</th>
                                    <th>ID Cliente</th>
                                    <th>Direccion</th>
                                    <th>Localidad</th>
                                    <th>Provincia</th>
                                    <th>Coordenadas</th>
                                    <th>Cantidad</th>
                                    <th>EVP</th>
                                    <th>Elemento</th>
                                    <th>Anunciante</th>
                                    <th>Producto</th>
                                    <th>Referencias</th>
                                    <th>Fecha de finalizaci&oacute;n</th>
                                    <th>One OrdenS</th>
                                    <th>One Circuito</th>
                                </thead>

                                <tbody>

                                <c:forEach items="${ubicaciones}" var="bo" varStatus="status">
                                <c:choose>
                                <c:when test="${bo.appUbicacion != null}">
                                <tr>
                                    <td>
                                        <div class="form-check">
                                            <label class="form-check-label">
                                                <input class="form-check-input checkbox" type="checkbox" id="form-check-input-${bo.id}" name="idAppUbicacionesList" onclick="saveCheck(${bo.id})" value="${bo.id}">
                                                <span class="form-check-sign" id="${bo.id}"></span>
                                            </label>
                                        </div>
                                    </td>
                                    <td>
                                        <a href="/appUbicacionRelevamiento/${bo.id}">${bo.id}</a>
                                        <%--<input type="hidden" value="${bo.id}" name="list[${status.index}].id"/>--%>
                                    </td>
                                    <td class="text-center">
                                        <a href="<c:url value='/appUbicacionRelevamiento/update?id=${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/edit2.png" alt="">
                                    </td>
                                    <td>
                                        <a href="/relevamiento/${bo.appRelevamiento.id}">${bo.appRelevamiento.id}</a>
                                        <%--<input type="hidden" value="${bo.appRelevamiento.id}" name="list[${status.index}].appRelevamiento"/>--%>
                                    </td>
                                    <td>
                                        <a href="/appUbicacion/${bo.appUbicacion.id}">${bo.appUbicacion.id}</a>
<%--                                        <input type="hidden" value="${bo.appUbicacion.id}" name="list[${status.index}].appUbicacion"/>--%>
                                        <c:if test = "${empty bo.appUbicacion.latitud || empty bo.appUbicacion.longitud}">
                                            <i class="fas fa-exclamation-circle cursorHelp" title="La ubicacion de esta ubicacion relevamiento no tiene coordenadas asignadas."></i>
                                        </c:if>
                                    </td>

                                    <td>
                                            ${bo.appUbicacion.direccion}
<%--                                        <input type="hidden" value="${bo.appUbicacion.direccion}" name="list[${status.index}].address"/>--%>
<%--                                        <input type="hidden" value="${bo.appUbicacion.latitud}" name="list[${status.index}].latitud"/>--%>
<%--                                        <input type="hidden" value="${bo.appUbicacion.longitud}" name="list[${status.index}].longitud"/>--%>
                                    </td>
                                    <td>
                                            ${bo.appUbicacion.localidad}
                                    </td>
                                    <td>
                                            ${bo.appUbicacion.provincia}
                                    </td>
                                    <td>
                                            ${bo.appUbicacion.latitud}
                                            ${bo.appUbicacion.longitud}
                                    </td>
                                    <td>
                                            ${bo.cantidad}
<%--                                        <input type="hidden" value="${bo.cantidad}" name="list[${status.index}].cantidad"/>--%>
                                    </td>

                                    <td>
                                            ${bo.evp}
<%--                                        <input type="hidden" value="${bo.evp}" name="list[${status.index}].evp"/>--%>
                                    </td>

                                    <td>
                                            ${bo.elemento}
<%--                                        <input type="hidden" value="${bo.elemento}" name="list[${status.index}].elemento"/>--%>
                                    </td>

                                    <td>
                                            ${bo.anunciante}
<%--                                        <input type="hidden" value="${bo.anunciante}" name="list[${status.index}].anunciante"/>--%>
                                    </td>

                                    <td>
                                            ${bo.producto}
<%--                                        <input type="hidden" value="${bo.producto}" name="list[${status.index}].producto"/>--%>
                                    </td>

                                    <td>
                                            ${bo.referencias}
<%--                                        <input type="hidden" value="${bo.referencias}" name="list[${status.index}].referencias"/>--%>
                                    </td>

                                    <td>
                                            ${bo.fechaFinalizacion}
                                    </td>

                                    <td>
                                            ${bo.oneOrdenS}
<%--                                        <input type="hidden" value="${bo.oneOrdenS}" name="list[${status.index}].oneOrdenS"/>--%>
                                    </td>

                                    <td>
                                            ${bo.oneCircuito}
<%--                                        <input type="hidden" value="${bo.oneCircuito}" name="list[${status.index}].oneCircuito"/>--%>
                                    </td>
                                </tr>
                                </c:when>
                                <c:otherwise>
                                <tr>
                                    <td style="background-color: #fc727a !important;" class="text-white">
                                        N/A
                                    </td>
                                    <td class="alert-danger">
                                        <a href="/appUbicacionRelevamiento/${bo.id}">${bo.id}</a>
                                    </td>
                                    <td class="alert-danger text-white text-center">
                                        <a href="<c:url value='/appUbicacionRelevamiento/update?id=${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/edit2.png" alt="">
                                    </td>
                                    <td class="alert-danger text-white">
                                        <a href="/relevamiento/${bo.appRelevamiento.id}">${bo.appRelevamiento.id}</a>
                                    </td>
                                    <td class="alert-danger text-white">
                                        <a href="/appUbicacion/${bo.appUbicacion.id}">${bo.appUbicacion.id}</a>
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.appUbicacion.direccion}
                                    </td>
                                    <td class="alert-danger text-white">
                                            ${bo.appUbicacion.localidad}
                                    </td>
                                    <td class="alert-danger text-white">
                                            ${bo.appUbicacion.provincia}
                                    </td>
                                    <td class="alert-danger text-white">
                                            ${bo.appUbicacion.latitud}
                                            ${bo.appUbicacion.longitud}
                                    </td>
                                    <td class="alert-danger text-white">
                                            ${bo.cantidad}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.evp}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.elemento}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.anunciante}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.producto}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.referencias}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.oneOrdenS}
                                    </td>

                                    <td class="alert-danger text-white">
                                            ${bo.oneCircuito}
                                    </td>
                                </tr>
                                </c:otherwise>
                                </c:choose>

                                </c:forEach>
                            </table>

                            <div class="col-8">

                                <%-- <a href="<c:url value='/appUbicacionRelevamiento/create'/>" class="btn btn-primary btn-fill"><i class="fas fa-plus"></i>&nbsp;Nuevo</a> --%>

                                <button type="button" class="btn btn-info btn-fill" data-toggle="modal" data-target="#modal-upload-xls">
                                    <i class="fas fa-file-upload"></i>&nbsp;Subir excel
                                </button>

                                <button type="button" class="btn btn-info btn-fill" title="Descargar formato para ubicaciones" onclick="downloadExcelTemplate()">
                                    <i class="fas fa-file-download"></i>&nbsp;Formato excel
                                </button>

                                <%-- <button type="submit" name="maps" onclick= "form.action = 'search';" class="btn btn-secondary btn-fill" ><i class="fas fa-map-marker-alt" ></i>&nbsp;Mapa</button> --%>
                                <button type="submit" name="delete" onclick= "form.action = 'deleteAll';" class="btn btn-secondary btn-fill"><i class="fas fa-trash-alt"></i>&nbsp;Borrar</button>

                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="modal-show-form" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content" style="height: 50%; overflow-y: scroll; margin-top: 0px;">
                <div class="modal-header">
                    <strong class="modal-title">Respuesta:</strong>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div id="rendered-form"></div>
                </div>
                <div class="modal-footer"></div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="modal-upload-xls" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-md" role="document">
            <div class="modal-content" style="position: absolute; top:25vh; max-width: 82vh">
                <div class="modal-header">
                    <strong class="modal-title">Selecci&oacute;n de archivo:</strong>
                    <button type="button" class="close" data-dismiss="modal" onclick="clearResponseLabel()" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="upload-container" style="margin: 15px;">
                        <div class="upload-content">
                            <div class="single-upload">
                                <input id="uploadExcelInput" type="file" name="file" class="file-input" accept=".xls,.xlsx"/>
                                <button type="button" onclick="readDataFile()" class="btn btn-sm btn-primary btn-fill confirmUpload-button ml-3">Confirmar</button>
                            </div>
                        </div>
                    </div>

                    <div id="uploadExcelSuccess"></div>
                    <div id="uploadExcelError"></div>
                    <table id="errorsTable" class="table"></table>

                    <div class="col load mt-5" style="display: none; z-index: 1000;">
                        <div class="col-md-12">
                            <div class="loader">
                                <div class="loader-inner box1"></div>
                                <div class="loader-inner box2"></div>
                                <div class="loader-inner box3"></div>
                            </div>
                        </div>
                        <div class="col-md-12"><h5 id="info-loader" style="text-align: center">Cargando...</h5></div>
                    </div>
                </div>
                <div class="modal-footer"></div>
            </div>
        </div>
    </div>

</div>

<script src="<c:url value='/resources/assets/js/plugins/FormBuilder/form-builder.min.js'/>"></script>
<script src="<c:url value='/resources/assets/js/plugins/FormBuilder/form-render.min.js'/>"></script>
<script src="<c:url value='/resources/assets/js/plugins/xlsx.full.min.js'/>"></script>

<script>
    var element = document.getElementById("auditapp");
    element.classList.add("active");

    var uploadExcelInput = document.querySelector('#uploadExcelInput');
    var selectFile;

    $(document).ready(function(){

        var valueOfSearchingDataTable = sessionStorage['appUbicacionSearchDataTable'] || '';
        $('.dataTables_filter input[type="search"]').val(valueOfSearchingDataTable).keyup();

        $('.dataTables_filter input').unbind().keyup(function(e) {

            var valueSearchDataTable = $(this).val();
            if (valueSearchDataTable.length>=1) {
                dataTableToCompleteList.search(valueSearchDataTable).draw();
                sessionStorage['appUbicacionSearchDataTable'] = valueSearchDataTable;

            } else {
                dataTableToCompleteList.search('').draw();
                sessionStorage['appUbicacionSearchDataTable'] = "";
            }
        });

    });

    function disableFieldsOfResponseForm(form){
        form.querySelectorAll("input").forEach(function (i) {
            i.disabled = true;
        });
        form.querySelectorAll("select").forEach(function (i) {
            i.disabled = true;
        });
    }

    // function showForm(idUbicacion , idRelevamiento){
    //     $('.load').show();
    //     $('.content').css('opacity', '0.7');
    //     var url = "/appRespuesta/getAnswer/" + idUbicacion + "/" + idRelevamiento;
    //
    //     $.ajax( {
    //         url: url,
    //         type: "GET",
    //         dataType: 'json',
    //         headers: {
    //             'Accept': 'application/json',
    //             'Content-Type': 'application/json'
    //         },
    //         success:  function (data) {
    //             $('.load').hide();
    //             $('.content').css('opacity', '1');
    //
    //             var formData = data.respuesta;
    //
    //             if(formData){
    //                 var fbRender = document.getElementById("rendered-form");
    //                 $(fbRender).formRender({ formData: formData });
    //
    //                 disableFieldsOfResponseForm(fbRender);
    //                 $("#modal-show-form").modal('show');
    //             }else{
    //                 $.notify({
    //                     title: '<strong>Ups!</strong>',
    //                     message: 'Esta ubicacion a&uacute;n no tiene respuesta, por favor vuelva a intentarlo m&aacute;s tarde!'
    //                 }, {
    //                     timer: 8000,
    //                     z_index: 2031
    //                 });
    //             }
    //         },
    //         error: function (e) {
    //             $('.load').hide();
    //             $('.content').css('opacity', '1');
    //             console.log(e);
    //             if(e.status === 404){
    //                 $.notify({
    //                     title: '<strong>Ups!</strong>',
    //                     message: 'Esta ubicacion a&uacute;n no tiene respuesta, por favor vuelva a intentarlo m&aacute;s tarde!'
    //                 }, {
    //                     timer: 8000,
    //                     z_index: 2031
    //                 });
    //             }else{
    //                 $.notify({
    //                     title: '<strong>Hubo un problema!</strong>',
    //                     message: 'Se produjo un error al intentar obtener los resultados.'
    //                 }, {
    //                     type: 'danger'
    //                 });
    //             }
    //         }
    //     });
    // }

    function clearResponseLabel(){
        document.getElementById("uploadExcelError").style.display = "none";
        document.getElementById("uploadExcelSuccess").style.display = "none";
        document.getElementById("errorsTable").style.display = "none";
        document.getElementById("errorsTable").innerHTML = "";
    }

    uploadExcelInput.addEventListener("change", function(event){
        selectFile = event.target.files[0];
    });

    var confirmedFile = false; // Verificar si el excel de ubicaciones subido ha sido confirmado.
    
    function readDataFile() {
        debugger;
        $('.load').show();
        $('.content').css('opacity', '0.7');
        clearResponseLabel();
        if (selectFile && !confirmedFile) { 

            var fileReader = new FileReader();
            fileReader.readAsBinaryString(selectFile);
            fileReader.onload = function(event) {
                var data = event.target.result;
                var workbook = XLSX.read(data, { type: "binary" });

                workbook.SheetNames.forEach(function(sheet) {
                    var rowObject = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheet]);

                    uploadFileData(rowObject); //Upload endpoint
                });
                confirmedFile = true; // Establece que el archivo ha sido confirmado
                hideConfirmButton(); // Ocultar el botón de confirmar
            };
            fileReader.onerror = function(e){
                $('.load').hide();
                $('.content').css('opacity', '1');

                var uploadExcelError = document.querySelector('#uploadExcelError');

                uploadExcelError.innerHTML = "<p><i class='far fa-times-circle' style='color:darkred'></i> Ocurri&oacute; un error al leer el archivo, por favor actualice la p&aacute;gina e int&eacute;ntelo nuevamente, " +
                    "si el problema persiste, verifique la integridad del archivo</p>";
                uploadExcelError.style.display = "block";

                console.log(e);
            };

        } else {
            $('.load').hide();
            $('.content').css('opacity', '1');

            var uploadExcelError = document.querySelector('#uploadExcelError');

            uploadExcelError.innerHTML = "<p><i class='far fa-times-circle' style='color:darkred'></i> Por favor seleccione un archivo</p>";
            uploadExcelError.style.display = "block";
        }
    }

    // Oculta el botón de confirmar luego de confirmar el archivo subido.
    function hideConfirmButton() {
        var confirmButton = document.querySelector('.confirmUpload-button');
        if (confirmButton) {
            confirmButton.style.display = 'none';
        }
    }
</script>

<script>



    function saveCheck(id,selectAllCheckbox){
        selectAllCheckbox = selectAllCheckbox || false;

        let ids=JSON.parse(sessionStorage.getItem('listaChekboxUbicacionRelevamiento'))
        if(ids==null){ids=[]}
        let identificadorInput= "#form-check-input-"+id
        let input=document.querySelector(identificadorInput)

        if(selectAllCheckbox.arriba==true) {
            if (!ids.includes(id)) {
                ids.push(id)
                sessionStorage.setItem('listaChekboxUbicacionRelevamiento', JSON.stringify(ids))
            }
        }
        if(selectAllCheckbox.checkbox==false){
            sessionStorage.setItem('listaChekboxUbicacionRelevamiento',JSON.stringify([]))
        }else{
              if(!ids.includes(id)){
                ids.push(id)
                sessionStorage.setItem('listaChekboxUbicacionRelevamiento',JSON.stringify(ids))
              }else if(ids.includes(id)&&selectAllCheckbox.agregarTodas!=undefined&&(selectAllCheckbox.arriba==false)||selectAllCheckbox==false){

                const index = ids.indexOf(id);
                if (index > -1) {
                    ids.splice(index, 1);
                    sessionStorage.setItem('listaChekboxUbicacionRelevamiento',JSON.stringify(ids))
                }
            }
        }
    }

    function cargarCheckbox(){
        let lista=JSON.parse(sessionStorage.getItem('listaChekboxUbicacionRelevamiento'))
        if(lista){
            for(let i=0;i<lista.length;i++){
                let identificador="#form-check-input-"+lista[i];
                let checkbox=  document.querySelector(identificador)
                if(checkbox!=null){
                    checkbox.checked=true
                }


            }
        }
    }
    window.onload = function() {
        cargarCheckbox()
    }
</script>