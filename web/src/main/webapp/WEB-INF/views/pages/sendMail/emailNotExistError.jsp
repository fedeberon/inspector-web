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
        <div class="fontStyleError col-md-4 p-3" style="margin-top: 6%">
            <h5 class="mt-3">¡Hubo un problema!</h5>
            <p style="color:white; font-size: 13px">El correo electronico ingresado no esta asociado a ninguna cuenta.</p>
            <a class="btnSend" href="/forgot-password" style="width: 20%!important;" role="button">Volver</a>
            <div id="supportBrowsersLog">
                <img src="resources/assets/img/ScopesiLogo.png" width="142">
            </div>
        </div>

    </div>
</div>