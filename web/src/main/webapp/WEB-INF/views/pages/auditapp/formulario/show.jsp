<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    #rendered-form {
        max-width: 90%;
        margin: auto;
        border-style: outset;
        border-radius: 5px;
        padding: 10px;
    }
</style>
<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title" style="text-align: center">Formulario ID: ${formulario.id}</h4>
                    </div>
                    <div class="card-body" style="overflow-x: scroll">
                        <table id="example" class="table table-hover table-striped" >

                            <tbody>
                                <tr>
                                    <th>Nombre del formulario:</th>
                                    <td>${formulario.nombreFormulario}</td>
                                </tr>
                                <tr>
                                    <th>Formulario:</th>
                                    <td>
                                        <div class="text-center cursorPointer" onclick="showForm(${formulario.id})" title="Ver formulario">
                                            <input type="hidden" id="data-${formulario.id}" value='${formulario.formulario}'>
                                            <i class="fas fa-eye"></i>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="py-2" style="text-align: center;">
                        <a class="btn btn-light" href="list"><i class="fas fa-angle-double-left pr-2"></i>Volver a la lista</a>
                    </div>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="modal-show-form" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="height: 50%; overflow-y: scroll; margin-top: 0px;">
                        <div class="modal-header">
                            <strong class="modal-title" id="exampleModalLabel">Items del formulario:</strong>
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

        </div>

    </div>
</div>
<script src="<c:url value='/resources/assets/js/plugins/FormBuilder/form-builder.min.js'/>"></script>
<script src="<c:url value='/resources/assets/js/plugins/FormBuilder/form-render.min.js'/>"></script>
<script src="<c:url value="/resources/assets/js/formBuilder.js"/>"></script>

<script>
    var element = document.getElementById("auditapp");
    element.classList.add("active");

</script>