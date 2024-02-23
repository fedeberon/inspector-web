<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Empresa</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="dataTableToCompleteList" class="display" style="width:100%">
                            <thead>
                                <th>ID</th>
                                <th class="text-center">Editar</th>
                                <th class="text-center">Baja/Alta Logica</th>
                                <th>Descripcion</th>
                                <th>Telefono</th>
                                <th>E-Mail</th>
                                <th>Logo</th>
                                <th>Orden</th>
                                <th>Html</th>
                                <th>GP+</th>
                            </thead>

                            <tbody>

                            <c:forEach items="${empresas}" var="bo">

                                <tr>
                                    <td><a href="/empresa/${bo.id}">${bo.id}</a></td>
                                    <td class="text-center">
                                        <a href="<c:url value='/empresa/update?id=${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/edit2.png" alt="">
                                    </td>
                                    <td class="text-center">
                                        <a ${bo.bajaLogica == true ? 'class="d-none"' : ''} href="<c:url value='/empresa/dropBajaLogica?id=${bo.id}'/>"/>
                                        <img ${bo.bajaLogica == true ? 'class="d-none"' : ''} src="/resources/assets/img/icons/delete.png" alt="">
                                        <a ${bo.bajaLogica == false ? 'class="d-none"' : ''} href="<c:url value='/empresa/upBajaLogica?id=${bo.id}'/>"/>
                                        <img ${bo.bajaLogica == false ? 'class="d-none"' : ''} src="/resources/assets/img/icons/arrowUp2.png" alt="">
                                    </td>
                                    <td>${bo.descripcion}</td>
                                    <td>${bo.telefono}</td>
                                    <td>${bo.mail}</td>

                                    <td class="row" style="justify-content: space-around">
                                        <div class="text-center cursorPointer" onclick="initCarouselLogoOfMapCompany('${bo.id}', '${bo.descripcion}')">
                                            <i class="fas fa-camera"></i>
                                        </div>
                                        -
                                        <div type="button" class="border-none p-0 m-0 bg-transparent" data-toggle="modal" onclick="openUploadLogoModal('${bo.id}')">
                                            <i class="fas fa-file-upload"></i>
                                        </div>
                                    </td>
                                   <!-- <td>${bo.logo}</td> -->
                                    <td>${bo.orden}</td>
                                    <td>${bo.html}</td>
                                    <td>${bo.GPMas}</td>
                                </tr>

                            </c:forEach>

                            </tbody>
                        </table>
                        <div class="row pt-4 px-2">
                            <div class="col-6">

                                <form name="search" action="list" method="get">

                                    <a href="../tablas" class="btn btn-light pull-left mr-3"><i class="fas fa-angle-double-left pr-2"></i>Volver</a>

                                    <a href="create" class="btn btn-primary btn-fill"><i class="fas fa-plus"></i>&nbsp;Nuevo</a>

                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<script>
    var element = document.getElementById("geoplanning");
    element.classList.add("active");

    $(document).ready(function(){

        var valueOfSearchStoraged = sessionStorage['empresaSearchDataTable'] || '';
        $('.dataTables_filter input[type="search"]').val(valueOfSearchStoraged).keyup();

        $('.dataTables_filter input').unbind().keyup(function(e) {

            var valueSearchDataTable = $(this).val();
            if (valueSearchDataTable.length>=1) {
                dataTableToCompleteList.search(valueSearchDataTable).draw();
                sessionStorage['empresaSearchDataTable'] = valueSearchDataTable;

            } else {
                dataTableToCompleteList.search('').draw();
                sessionStorage['empresaSearchDataTable'] = "";
            }
        });

    });
</script>


<!-- Modal -->
<div class="modal fade confirm-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" id="modal-confirmacion">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" style="margin-top: 0;">&iquest;Desea eliminar esta imagen?</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="modal-btn2-si">Si</button>
                <button type="button" class="btn btn-primary" id="modal-btn2-no">No</button>
            </div>
        </div>
    </div>
</div>


<input type="hidden" id="fileToDelete"/>
<!-- Modal -->
<div class="modal fade" id="modal-upload-image" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                    <div class="files-geoplanning d-none"></div>
                    <div class="upload-content">
                        <div class="single-upload">
                            <input id="upload-image-input" type="file" name="file" class="file-input"/>
                            <button type="button" onclick="uploadImage()" class="btn btn-sm btn-primary btn-fill confirmUpload-button ml-3">Confirmar</button>
                        </div>
                    </div>
                </div>

                <div id="uploadExcelSuccess"></div>
                <div id="uploadExcelError"></div>

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


<script>
    const selectedImageInput = document.getElementById('upload-image-input');
    let selectedImage = null;
    selectedImageInput.addEventListener("change", () => { selectedImage = selectedImageInput.files[0]; });

    ('modal-upload-image')
    const modalUploadImage = $('#modal-upload-image');
    const openUploadLogoModal = (companyId) => {
        modalUploadImage.attr("data-comany-id", companyId);
        modalUploadImage.modal('show');
    };

    async function uploadImage(){
        //#region reusable functions
        const toggleLoading = (toggle) => {
            toggle ? $('.load').show() : $('.load').hide();
            $('.content').css('opacity', toggle ? '0.3': '1');
        }
        const clearModal = () => {
            document.getElementById("uploadExcelError").style.display = "none";
            document.getElementById("uploadExcelSuccess").style.display = "none";
        }
        const displayErrorMsg = (message) => {
            clearModal();
            toggleLoading(false);
            let uploadExcelError = document.querySelector('#uploadExcelError');
            uploadExcelError.innerHTML = "<p><i class='far fa-times-circle' style='color:darkred'></i>"+message+"</p>";
            uploadExcelError.style.display = "block";
        }
        const displaySuccessMsg = (message) => {
            clearModal();
            toggleLoading(false);
            uploadExcelSuccess.innerHTML = "<p><i class='far fa-check-circle' style='color:green'></i> Se han cargado la imagen correctamente.</p>";
            uploadExcelSuccess.style.display = "block";
        }
        //#endregion
        if(!selectedImageInput.files[0]) return displayErrorMsg('Por favor seleccione un archivo');
        clearModal(); toggleLoading(true);
        try {
            const companyId = modalUploadImage.attr('data-comany-id');
            let data = new FormData();
            data.append("archivo", selectedImageInput.files[0], 'logo');
            $.ajax({
                type: 'POST',
                url: window.fileApiUrl+'/fotos_map/map-company-logos/'+companyId+'/logo.jpg',
                enctype: 'multipart/form-data',
                data: data,
                contentType: false,
                processData: false,
                success: function(res) {
                    displaySuccessMsg("Se subio la imagen prro!!!")
                },
                error: function(xhr, status, err) {
                    displayErrorMsg(err);
                }
            });

        } catch (eMsg) {
            displayErrorMsg(eMsg);
        }
    }
</script>