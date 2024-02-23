<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<style>

    .sidebar{

        width: 0%;

    }
    .container{
        max-width: 100%;
    }

    .main-panel{

        float: none;
        width: 100%;

    }
</style>
<div class="container fondo">
    <div class="row justify-content-center">
        <div class="col-md-4 p-3" style="margin-top: 6%">
            <form class="form-signin fontStyle formELement" name="form-login" action="sendEmail" method="POST">
                <h5 class="mb-4">Recuperaci&oacute;n de cuenta</h5>

                <div class="row justify-content-center">
                    <div class="col-sm-10"><p>Por favor ingrese su correo electronico:</p></div>
                </div>

                <div class="row justify-content-center">
                    <div class="col-sm-10 form-label-group"><input type="email" name="email" class="form-control emailInput" placeholder="ejemplo@gmail.com" required></div>
                </div>

                <button class="btnSend mt-3" type="submit">Enviar correo</button>
            </form>
            <div id="supportBrowsersLog">
                <img src="resources/assets/img/ScopesiLogo.png" width="142">
            </div>
        </div>
    </div>
</div>
