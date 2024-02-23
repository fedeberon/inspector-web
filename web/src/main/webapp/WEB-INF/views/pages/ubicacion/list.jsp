<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="<c:url value='/resources/assets/js/search.js'/>"></script>


<style>
    .form-check{
        margin-top: 10px;
    }

    .hidden{
        display: none;
    }

    .dt-buttons{
        display: none;
    }

    .fancybox-button--fb {
        padding: 10px;
        margin-right: 5px;
    }

    .fancybox-button--fb svg path{
        stroke-width: 0;
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
            <div class="col-md-12"><h5 id="info-loader" style="text-align: center"></h5></div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header">
                        <h4 class="card-title">Clientes</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <form:form action="search" modelAttribute="myWrapper" name="myWrapper" id="myWrapper" method="post">
                            <input type="hidden" name="page" value="${ubicacionRequest.page}"/>
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
                                    <th>Fotos</th>
                                    <th>Editar</th>
                                    <th>ID</th>
                                    <th>ID Referencia</th>
                                    <th>Empresa</th>
                                    <th>Elemento</th>
                                    <th>Formato</th>
                                    <th>Medio</th>
                                    <th>Direccion</th>
                                    <sec:authorize access="hasAnyRole(
                                        T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
                                        ">
                                    <c:forEach items="${parametros}" var="bo" varStatus="status">
                                    <th>${bo.nombre}</th>
                                    </c:forEach>
                                    </sec:authorize>
                                    <th>Referencia</th>
                                    <th>Nro.Agip</th>
                                    <th>Localidad</th>
                                    <th>Provincia</th>
                                    <th>Cantidad</th>
                                    <th>Transito</th>
                                    <th>Iluminacion</th>
                                    <th>Medidas</th>
                                    <th>Latitud</th>
                                    <th>Longitud</th>
                                    <sec:authorize access="hasAnyRole(
                                    T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                    ">
                                    <th>Metros Contacto</th>
                                    <th>Coeficiente</th>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole(
                                    T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                    ">
                                    <th>ID Buses</th>
                                    </sec:authorize>
                                    <th>Baja Logica</th>
                                    <th>Fecha de Tranferencia</th>
                                    <th>Fecha de Alta</th>
                                    <sec:authorize access="hasAnyRole(
                                    T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                    ">
                                    <th>Altura</th>
                                    <th>Visibilidad</th>
                                    <th>Poligono</th>
                                    </sec:authorize>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${ubicaciones}" var="bo" varStatus="status">
                                        <tr>
                                            <td>
                                                <div class="form-check">
                                                    <label class="form-check-label">
                                                        <input class="form-check-input checkbox"  type="checkbox" id="form-check-input-${bo.id}" name="idAppUbicacionesList" onclick="saveCheck(${bo.id})" value="${bo.id}">
                                                        <span class="form-check-sign" id="${bo.id}"></span>
                                                    </label>
                                                </div>
                                            </td>

                                            <td>
                                                <div class="text-center cursorPointer" onclick="createCarousel('${bo.id}' , '${bo.mapEmpresa.id}' , '${bo.mapEmpresa.descripcion}')">
                                                    <i class="fas fa-camera"></i>
                                                </div>
                                            </td>

                                            <td class="text-center">
                                                <input type="hidden" id="wrapperId" value="${bo.id}" name="id"/>
                                                <button type="submit" id="sudmit-${bo.id}" name="editar" style="display: none;"></button>
                                                <a href="#" onclick="submitEdit(${bo.id})"><img src="/resources/assets/img/icons/edit2.png" alt=""></a>
                                            </td>
                                            <td>
                                                <a href="/ubicacion/${bo.id}">${bo.id}</a>
                                                <%--<input type="hidden" value="${bo.id}" name="list[${status.index}].id"/>--%>
                                            </td>
                                            <td>${bo.idReferencia}</td>
                                            <td>
                                                ${bo.mapEmpresa.descripcion}
                                                <%--<input type="hidden" value="${bo.mapEmpresa.descripcion}" name="list[${status.index}].name"/>--%>
                                                <%--<input type="hidden" value="${bo.mapEmpresa.id}" name="list[${status.index}].idEmpresa"/>--%>
                                            </td>

                                            <td>
                                                ${bo.mapElemento.descripcion}
                                                <%--<input type="hidden" value="${bo.mapElemento.descripcion}" name="list[${status.index}].description"/>--%>
                                            </td>

                                            <td>${bo.mapFormato.descripcion}</td>
                                            <td>${bo.mapMedio.descripcion}</td>
                                            <td>
                                                ${bo.direccion}
                                                <%--<input type="hidden" value="${bo.direccion}" name="list[${status.index}].address"/>--%>
                                            </td>
                                            <sec:authorize access="hasAnyRole(
                                            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
                                            ">
                                            <c:forEach items="${parametros}" var="boI" varStatus="statusI">
                                            <td>
                                            <c:if test="${bo.parametros!=null}">
                                            <c:forEach items="${bo.parametros}" var="boJ" varStatus="statusJ">
                                            <c:if test="${boJ.parametro.idParametro.equals(boI.idParametro)}">
                                                ${bo.parametros[statusJ.index].descripcion}
                                            </c:if>
                                            </c:forEach>
                                            </c:if>
                                            </td>
                                            </c:forEach>
                                            </sec:authorize>
                                            <td>${bo.referencia}</td>
                                            <td>${bo.nroAgip}</td>
                                            <td>
                                                ${bo.mapLocalidad.descripcion}
                                                <%--<input type="hidden" value="${bo.mapLocalidad.descripcion}" name="list[${status.index}].localidad"/>--%>
                                            </td>
                                            <td>
                                                ${bo.mapProvincia.descripcion}
                                                <%--<input type="hidden" value="${bo.mapProvincia.descripcion}" name="list[${status.index}].provincia"/>--%>
                                            </td>
                                            <td>${bo.cantidad}</td>
                                            <td>${bo.transito}</td>
                                            <td>${bo.iluminacion}</td>
                                            <td>${bo.medidas}</td>
                                            <td>
                                                ${bo.latitud}
                                                <%--<input type="hidden" value="${bo.latitud}" name="list[${status.index}].latitud"/>--%>
                                            </td>
                                            <td>
                                                ${bo.longitud}
                                                <%--<input type="hidden" value="${bo.longitud}" name="list[${status.index}].longitud"/>--%>
                                            </td>
                                            <sec:authorize access="hasAnyRole(
                                            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                            ">
                                            <td>${bo.metrosContacto}</td>
                                            <td>${bo.coeficiente}</td>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole(
                                            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                            ">
                                            <td>${bo.mapBuses.id}</td>
                                            </sec:authorize>
                                            <td>${bo.bajaLogica}</td>
                                            <td>${bo.fechaTransf}</td>
                                            <td>${bo.fechaAlta}</td>
                                            <sec:authorize access="hasAnyRole(
                                            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
                                            ">
                                            <td>${bo.mapUbicacionAltura.descripcion}</td>
                                            <td>${bo.mapUbicacionVisibilidad.descripcion}</td>
                                            <td>${bo.polygonLatLong}
                                            </sec:authorize> <%--<input type="hidden" value='${bo.polygonLatLong}' name="list[${status.index}].polygonCoordinates"/>--%></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                        <!-- Modal Editar campos -->
                    <%--    <div class="modal fade" id="optionModalEdit" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Datos a Cambiar</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <input type="hidden" name="page" value="${ubicacionRequest.page}"/>
                                    <div class="modal-body row">

                                        <div class="form-group col-6">
                                            <label for="idEmpresa">Empresas</label>
                                            <select name="request.idEmpresa" class="form-control" title="Seleccione una empresa">
                                                <option  value="-1">Seleccione una Empresa</option>
                                                <c:forEach items="${empresas}" var="bo">
                                                    <option  value="${bo.id}">${bo.descripcion}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="form-group col-6">
                                            <label for="idElemento">Elementos</label>
                                            <select name="request.idElemento" class="form-control">
                                                <option  value="-1">Seleccione un Elemento</option>
                                                <c:forEach items="${elementos}" var="bo">
                                                    <option value="${bo.id}">${bo.descripcion}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="form-group col-6">
                                            <label for="idFormato">Formatos</label>
                                            <select name="request.idFormato" items="${formatos}" class="form-control" title="Seleccione un formato">
                                                <option  value="-1">Seleccione un Formato</option>
                                                <c:forEach items="${formatos}" var="bo">
                                                    <option value="${bo.id}">${bo.descripcion}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="form-group col-6">
                                            <label for="idMedio">Medios</label>
                                            <select name="request.idMedio" items="${medios}" class="form-control" title="Seleccione un Medio   ">
                                                <option  value="-1">Seleccione una Medio</option>
                                                <c:forEach items="${medios}" var="bo">
                                                    <option value="${bo.id}">${bo.descripcion}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="form-group col-6">
                                            <label for="idLocalidad">Localidades</label>
                                            <select name="request.idLocalidad" items="${localidades}" class="form-control" title="Seleccione una Localidad">
                                                <option  value="-1">Seleccione una Localidad</option>
                                                <c:forEach items="${localidades}" var="bo">
                                                    <option value="${bo.id}">${bo.descripcion}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="form-group col-6">
                                            <label for="idProvincia">Provincias</label>
                                            <select name="request.idProvincia" items="${provincias}" class="form-control" title="Seleccione una Provincia">
                                                <option  value="-1">Seleccione una Provincia</option>
                                                <c:forEach items="${provincias}" var="bo">
                                                    <option value="${bo.id}">${bo.descripcion}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="form-group col-6">
                                            <label for="idAltura">Alturas</label>
                                            <select name="request.idAltura" class="form-control" title="Seleccione una Altura">
                                                <option  value="-1">Seleccione una altura</option>
                                                <c:forEach items="${alturas}" var="bo">
                                                    <option value="${bo.id}">${bo.descripcion}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="form-group col-6">
                                            <label for="idVisibilidad">Visibilidades</label>
                                            <select name="request.idVisibilidad" class="form-control" title="Seleccione una Visibilidad">
                                                <option  value="-1">Seleccione una visibilidad</option>
                                                <c:forEach items="${visibilidades}" var="bo">
                                                    <option value="${bo.id}">${bo.descripcion}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="form-group col-6">
                                            <label for="metrosContactoRequest">Metros Contacto</label>
                                            <input  type="number" name="request.metrosContactoRequest" class="form-control" title="Metros Contacto" placeholder="Ingrese metros de contacto">
                                        </div>

                                        <div class="form-group col-6">
                                            <label for="coeficienteRequest">Coeficiente</label>
                                            <input  type="number" step="any" name="request.coeficienteRequest" class="form-control" title="Coeficiente" placeholder="Ingrese coeficiente">
                                        </div>

                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>

                                        <button name="saveList" type="submit" class="btn btn-primary"><i class="far fa-save"></i>&nbsp;Guardar resultados</button>
                                    </div>

                                </div>
                            </div>
                        </div>

                  --%>

                        <div class="col-8">
<%--                            <tags:paginador page="${ubicacionRequest.page}"  formName="myWrapper" noMorePages="${Integer.valueOf(ubicaciones.size() / 10) + Integer.valueOf(ubicaciones.size() % 10 >= 1 ? 1 : 0)}"/>--%>
                            <%-- <tags:paginador page="${ubicacionRequest.page}"  formName="myWrapper"/> --%>

                            <button type="submit" name="maps" class="btn btn-info btn-fill"><i class="fas fa-map-marker-alt"></i>&nbsp;Mapa</button>

                            <a href="create" class="btn btn-primary btn-fill"><i class="fas fa-plus"></i>&nbsp;Nuevo</a>

                        </div>

                            <%--Modal editar campos--%>
                            <jsp:include page="../modals/editUbicacionesModal.jsp"/>

                            <%--Modal filtrar campos--%>
                            <jsp:include page="../modals/filterUbicacion.jsp"/>

                            <%--Modal error stock --%>
                            <jsp:include page="../modals/stockError.jsp"/>

                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- Modal -->
<div class="modal fade" id="modal-info-files" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content" style="margin-top: 45%;">
            <div class="modal-header">
                <strong class="modal-title" id="exampleModalLabel">Gestor de imagenes</strong>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="data-ubicacion"></div>
                <button onclick="showImages('fotos_map')" class="btn btn-sm btn-fill btn-secondary"><i class="far fa-images"></i> Ver Imagenes</button>
                <a data-toggle="modal" href="#myModal2" class="btn btn-sm btn-fill btn-secondary"><i class="fas fa-file-upload"></i> Subir imagenes</a>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>

<div class="modal" id="myModal2">
    <div class="modal-dialog  modal-md">
        <div class="modal-content" style="margin-top: 15%; max-height: 55%">
            <div id="loader-block" class="loader-block"></div>
            <div id="modal-loader" class="col load mt-5" style="display: none; position:absolute; z-index: 2000;">
                <div class="col-md-12">
                    <div class="loader">
                        <div class="loader-inner box1"></div>
                        <div class="loader-inner box2"></div>
                        <div class="loader-inner box3"></div>
                    </div>
                </div>
            </div>

            <div style="text-align: center;">
                <button type="button" class="close" data-dismiss="modal" onclick="clearResponseLabel()" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3>Selecci&oacute;n de imagenes</h3>
            </div>

            <div class="upload-container" style="margin: 15px;">
                <div class="upload-content">
                    <div class="single-upload">
                        <h5><strong>Subir una imagen</strong></h5>
                        <form id="singleUploadForm" name="singleUploadForm">
                            <input id="singleFileUploadInput" type="file" name="file" class="file-input" required />
                            <button type="submit" class="btn btn-sm btn-primary btn-fill confirmUpload-button">Confirmar</button>
                        </form>
                    </div>
                    <div class="multiple-upload">
                        <h5><strong>Subir varias imagenes</strong></h5>
                        <form id="multipleUploadForm" name="multipleUploadForm">
                            <input id="multipleFileUploadInput" type="file" name="files" class="file-input" multiple required />
                            <button type="submit" class="btn btn-sm btn-primary btn-fill confirmUpload-button">Confirmar</button>
                        </form>
                    </div>
                    <div id="upload-response" class="upload-response">
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<li class="nav-item more-options" style="display: none">
    <a href="#" class="nav-link" onclick="openModal('searchModal')">
        <i class="nc-icon nc-zoom-split"></i>
        <span class="d-lg-block">&nbsp;Buscar</span>
    </a>
</li>

<li class="nav-item more-options" style="display: none">
    <a href="#" class="nav-link">
        <i class="nc-icon nc-simple-add"></i>
        <span id="span-more-options" class="d-lg-block" onclick="showOptions()">&nbsp;Mas Opciones</span>
    </a>
</li>

<li class="nav-item more-options" style="display: none">
    <a href="#" class="nav-link" onclick="openModal('optionModal')">
        <i class="nc-icon nc-simple-add"></i>
        <span class="d-lg-block">&nbsp;Editar Resultados</span>
    </a>
</li>

<!-- Modal -->
<div class="modal fade confirm-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" id="modal-delete-file">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <input type="hidden" id="fileToDelete"/>
            <div class="modal-header">
                <h5></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <h4 style="margin-top: 0;" id="myModalLabel">&iquest;Esta seguro que desea eliminar esta imagen?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-fill btn-default" onclick="deleteFile()">Si</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value='/resources/assets/js/filemanager.js'/>"></script>
<%-- Select item from sidebar && add search filter --%>
<script>
    var element = document.getElementById("geoplanning");
    element.classList.add("active");

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

    });

</script>
<script>
    //ubicacion list


    function saveCheck(id,selectAllCheckbox){
        selectAllCheckbox = selectAllCheckbox || false;

        let ids=JSON.parse(sessionStorage.getItem('listaChekboxUbicacionGeoplaning'))
        if(ids==null){ids=[]}
        let identificadorInput= "#form-check-input-"+id
        let input=document.querySelector(identificadorInput)

        if(selectAllCheckbox.arriba==true) {
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