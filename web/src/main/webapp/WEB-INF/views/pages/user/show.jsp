<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title" style="text-align: center">Usuario: ${usuario.id}</h4>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="example" class="table table-hover table-striped" >

                            <tbody>
                            <tr>
                                <th>Username:</th>
                                <td>${usuario.username}</td>
                            </tr>
                            <tr>
                                <th>Contrase&ntilde;a:</th>
                                <td>${usuario.password}</td>
                            </tr>
                            <tr>
                                <th>Nombre completo:</th>
                                <td>${usuario.nombreCompleto}</td>
                            </tr>
                            <tr>
                                <th>E-Mail:</th>
                                <td>${usuario.eMail}</td>
                            </tr>
                            <tr>
                                <th>Telefono:</th>
                                <td>${usuario.telefono}</td>
                            </tr>
                            <tr>
                                <th>Estado:</th>
                                <td>${usuario.estado}</td>
                            </tr>
                            <tr>
                                <th>Tipo de usuario:</th>
                                <td>${usuario.tipoUsuario.descripcion}</td>
                            </tr>
                            <tr>
                                <th>Empresa:</th>
                                <td>${usuario.cargo}</td>
                            </tr>
                            <tr>
                                <th>Contrato Inversion:</th>
                                <td>${usuario.contratoInv.descripcion}</td>
                            </tr>
                            <tr>
                                <th>Contrato Auditoria:</th>
                                <td>${usuario.contratoAud.descripcion}</td>
                            </tr>
                            <tr>
                                <th>Contrato Mapping:</th>
                                <td>${usuario.contratoMap.descripcion}</td>
                            </tr>
                            <tr>
                                <th>Fecha Vencimiento:</th>
                                <td>${usuario.fechaVencimiento}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="py-2" style="text-align: center;">
                        <a class="btn btn-light" href="list"><i class="fas fa-angle-double-left pr-2"></i>Volver a la lista</a>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>
<script>
    var element = document.getElementById("users");
    element.classList.add("active");
</script>