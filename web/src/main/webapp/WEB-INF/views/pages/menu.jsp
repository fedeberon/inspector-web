<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
    .wordsMenu{
        font-style: italic;
        font-size: 18px;
    }
    .dropdown-menu{
        display: none;
    }
    .cursorPointer{
        cursor: pointer;
    }

    .cursorHelp{
        cursor: help;
    }
</style>

<div class="sidebar-wrapper collapse show" id="collapseExample">

    <%-- <div class="logo">
        <a href="http://www.scopesi.com.ar">
            <img src="/resources/assets/img/ScopesiLogo.png">
        </a>
    </div> --%>
    <ul class="nav">

        <sec:authorize access="hasAnyRole(
            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
        ">
        <li id="dashboard">
            <a class="nav-link" href="/dashboard/panel">
                <i class="fas fa-chart-line"></i>
                <p class="wordsMenu">Dashboard</p>
            </a>
        </li>

        <%-- <li id="mantenimiento">
            <a class="nav-link" href="/mantenimiento/list">
                <i class="fas fa-wrench"></i>
                <p class="wordsMenu">Mantenimiento</p>
            </a> 
        </li> --%>

        <li id="users">
            <a  class="nav-link" href="/usuario/list">
                <i class="fas fa-users"></i>
                <p class="wordsMenu">Usuarios</p>
            </a>
        </li>
        </sec:authorize>
<%--        <li id="auditorias">--%>
<%--            <a class="nav-link" href="#">--%>
<%--                <i class="fab fa-buffer"></i>--%>
<%--                <p class="wordsMenu">Auditorias</p>--%>
<%--            </a>--%>
<%--        </li>--%>

        <%-- <sec:authorize access="hasAnyRole(
           T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString(),
           T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR_OOH.toString())
        ">
        <li id="geoplanning">
            <a class="nav-link dropdown-toggle cursorPointer" id="dropdownMenuOffset" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-atlas"></i>
                <p class="wordsMenu">Geoplanning</p>
            </a>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuOffset">
                <a class="dropdown-item" href="/tablas">
                    <i class="fas fa-table"></i> Tablas
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/ubicacion/list">
                    <i class="fas fa-map-marker-alt"></i> Ubicaciones
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/planificacion/list">
                    <i class="fas fa-calendar"></i> Planificaci&oacute;n
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/campanas/list">
                    <i class="fa fa-ticket"></i> Reservas
                </a>
            </div>
        </li>
        </sec:authorize> --%>

        <sec:authorize access="hasAnyRole(
            T(com.ideaas.services.enums.TipoUsuarioEnum).ROLE_ADMINISTRADOR.toString())
        ">
        <li id="auditapp">
            <a class="nav-link dropdown-toggle cursorPointer" id="dropdownMenu" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-mobile-alt"></i>
                <p class="wordsMenu">APP</p>
            </a>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuOffset">
                <a class="dropdown-item" href="/formulario/list">
                    <i class="fas fa-paste"></i> Formularios
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/proyecto/list">
                    <i class="fas fa-project-diagram"></i> Proyectos
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/relevamiento/list">
                    <i class="fas fa-running"></i> Rutas
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/appUbicacion/list">
                    <i class="fas fa-map-marker-alt"></i> Clientes
                </a>
            </div>
        </li>
        </sec:authorize>
    </ul>
</div>