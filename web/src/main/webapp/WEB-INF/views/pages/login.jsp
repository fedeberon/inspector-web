<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>


<style>

    .sidebar{

        width: 5%;

    }


    .main-panel{

        float: none;
        width: 100%;

    }

    .justify-content-center{
        margin-top: 6%;
    }

    .field-icon {
        float: right;
        margin-right: 12px;
        margin-top: -51px;
        position: relative;
        z-index: 2;
    }

</style>

<div class="container">
    <div class="row justify-content-center">
         <div class="col-md-4 border border-primary rounded mt-2 p-3">

            <!-- Default form login -->
            <form class="form-signin" name='f' action="login" style="text-align: center" method='POST'>  <!--action='/j_spring_security_check'-->
                <p class="h4 mb-4">Iniciar sesi&oacute;n</p>

                <!-- Email -->
                <input type="username" name="username" id="defaultLoginFormEmail" class="form-control mb-4" placeholder="Usuario"> <!-- name='j_username' -->

                <!-- Password -->
                <input type="password" name="password" id="inputPassword" class="form-control mb-4" placeholder="Contraseña"> <!-- name='j_password' -->
                <span id="showPassword" class="fas fa-eye field-icon" onclick="showPassword()"></span>

                <!-- Sign in button -->
                <button class="btn btn-info btn-block my-4" style="cursor: pointer" type="submit">Ingresar</button>

                <!-- Register -->
<%--                <p>Not a member?--%>
<%--                    <a href="">Registrate</a>--%>
<%--                </p>--%>

<%--                <!-- Social login -->--%>
<%--                <p>or sign in with:</p>--%>

<%--                <a href="#" class="mx-2" role="button"><i class="fab fa-facebook-f light-blue-text"></i></a>--%>
<%--                <a href="#" class="mx-2" role="button"><i class="fab fa-twitter light-blue-text"></i></a>--%>
<%--                <a href="#" class="mx-2" role="button"><i class="fab fa-linkedin-in light-blue-text"></i></a>--%>
<%--                <a href="#" class="mx-2" role="button"><i class="fab fa-github light-blue-text"></i></a>--%>

            </form>
             <div class="d-flex justify-content-around">
                 <div>
                     <!-- Forgot password -->
                     <a href="/forgot-password">&iquest;Olvidaste tu contraseña?</a>
                 </div>
             </div>
         </div>
    </div>
    <div class="pt-2" style="text-align: center; color: red;">
        <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
            <div class="error">
                <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
            </div>
        </c:if>
    </div>
</div>