<%@ page import="com.ideaas.services.enums.TipoUsuarioEnum" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
.iconTable{
    width: 36px;
    margin-right: 10px;
}

.card {
    -webkit-box-shadow: 3px 0px 17px -8px rgba(0,0,0,0.75);
    -moz-box-shadow: 3px 0px 17px -8px rgba(0,0,0,0.75);
    box-shadow: 3px 0px 17px -8px rgba(0,0,0,0.75);
}
.border{
    border-color: #c3b9b9!important;
}

@media (min-width: 768px) {
    .row-cols-3 > * {
        flex: 0 0 auto;
        width: 33.3333333333%!important;
    }
}

.row-cols-3 > * {
    padding-right: 15px;
    padding-left: 15px;
    flex-basis: auto;
}

</style>


<script>
    var element = document.getElementById("geoplanning");
    element.classList.add("active");
</script>


<div class="content">
    <div class="container-fluid">
        <div class="row row-cols-3">

            <sec:authorize access="hasAnyRole(
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
            ">
            <div class="col">
                <div class="card border rounded" >
                    <div class="card-header">
                        <h4 class="font-italic mt-0">
                            <a href="/empresa/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">Empresas</a>
                        </h4>
                    </div>
                </div>
            </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
               T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString(),
               T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
            ">
                <div class="col">
                    <div class="card border rounded">
                        <div class="card-header">
                            <h4 class="font-italic mt-0">
                                <a href="/provincia/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">Provincias</a>
                            </h4>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
            ">
                <div class="col">
                    <div class="card border rounded">
                        <div class="card-header">
                            <h4 class="font-italic  mt-0">
                                <a href="/poiSector/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">POI Sector</a>
                            </h4>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
               T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString(),
               T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
            ">
                <div class="col">
                    <div class="card border rounded">
                        <div class="card-header ">
                            <h4  class="font-italic mt-0">
                                <a href="/elemento/list" class="card-link"> <img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">Elementos</a>
                            </h4>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
               T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString(),
               T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
            ">
                <div class="col">
                    <div class="card border rounded" >
                        <div class="card-header">
                            <h4 class="font-italic mt-0">
                                <a href="/localidad/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">Localidades</a>
                            </h4>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
            ">
                <div class="col">
                    <div class="card border rounded">
                        <div class="card-header">
                            <h4 class="font-italic  mt-0">
                                <a href="/ubicacionActualizacion/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">Ubicacion Actualizacion</a>
                            </h4>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
               T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString(),
               T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
            ">
                <div class="col">
                    <div class="card border rounded">
                        <div class="card-header">
                            <h4 class="font-italic mt-0">
                                <a href="/formato/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">Formatos</a>
                            </h4>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
            ">
                <div class="col">
                    <div class="card border  rounded">
                        <div class="card-header">
                            <h4 class="font-italic mt-0">
                                <a href="/poi/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">POI</a>
                            </h4>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
            ">
                <div class="col">
                    <div class="card border rounded">
                        <div class="card-header">
                            <h4 class="font-italic  mt-0">
                                <a href="/ubicacionActEspecial/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">Ubicacion Act. Especial</a>
                            </h4>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
            ">
                <div class="col">
                    <div class="card border border-primary rounded">
                        <div class="card-header ">
                            <h4 class="font-italic mt-0">
                                <a href="/medio/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">Medios</a>
                            </h4>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
            ">
                <div class="col">
                    <div class="card border  rounded">
                        <div class="card-header">
                            <h4 class="font-italic mt-0">
                                <a href="/poiEntidad/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">POI Entidad</a>
                            </h4>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString(),
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
            ">
            <div class="col">
                <div class="card border rounded">
                    <div class="card-header">
                        <h4 class="font-italic  mt-0">
                            <a href="/parametro/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">Par&aacute;metros</a>
                        </h4>
                    </div>
                </div>
            </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString(),
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
            ">
            <div class="col">
                <div class="card border rounded">
                    <div class="card-header">
                        <h4 class="font-italic  mt-0">
                            <a href="/stock/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">Stock</a>
                        </h4>
                    </div>
                </div>
            </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole(
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString(),
                T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
            ">
                <div class="col">
                    <div class="card border rounded">
                        <div class="card-header">
                            <h4 class="font-italic  mt-0">
                                <a href="/cliente/list" class="card-link"><img src="/resources/assets/img/icons/iconfinder_document-financial-business-finance_3522170.png" class="iconTable">Clientes</a>
                            </h4>
                        </div>
                    </div>
                </div>
            </sec:authorize>
        </div>
    </div>
    <hr>
</div>




