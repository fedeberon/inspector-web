<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-expand-lg " color-on-scroll="500">
    <div class="container-fluid">
        <a class="navbar-brand" href="/home"> Manager </a>
        <div class="collapse navbar-collapse justify-content-end" id="navigation">
            <ul id="tools-button" class="nav navbar-nav mr-auto">

            </ul>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <span class="no-icon"></span>
                    </a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="no-icon">
                            <sec:authorize access="isAuthenticated()">
                                <sec:authentication property="name"/>
                            </sec:authorize>
                        </span>
                    </a>
                    <div class="dropdown-menu" style="min-width: 50px;" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="/logout">
                            <i class="fas fa-sign-out-alt"></i>
                            <span class="no-icon">Salir</span>
                        </a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>

<li class="nav-item more-options" style="display: none">
    <a class="nav-link" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        <i id="icon-close-menu" class="nc-icon nc-stre-left"></i>
        <span id="span-close-option" class="d-lg-block">&nbsp;Ocultar Menu</span>
    </a>
</li>