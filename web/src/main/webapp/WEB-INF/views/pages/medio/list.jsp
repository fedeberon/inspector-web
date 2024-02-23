<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card strpied-tabled-with-hover">
                    <div class="card-header ">
                        <h4 class="card-title">Medio</h4>
                        <p class="card-category">lista</p>
                    </div>
                    <div class="card-body table-full-width table-responsive">
                        <table id="dataTableToCompleteList" class="display" style="width:100%">
                            <thead>
                                <th>ID</th>
                                <th class="text-center">Editar</th>
                                <th class="text-center">Baja/Alta Logica</th>
                                <th>Descripcion</th>
                            </thead>

                            <tbody>

                            <c:forEach items="${medios}" var="bo">

                                <tr>
                                    <td><a href="/medio/${bo.id}">${bo.id}</a></td>
                                    <td class="text-center">
                                        <a href="<c:url value='/medio/update?id=${bo.id}'/>"/>
                                        <img src="/resources/assets/img/icons/edit2.png" alt="">
                                    </td>
                                    <td class="text-center">
                                        <a ${bo.bajaLogica == true ? 'class="d-none"' : ''} href="<c:url value='/medio/dropBajaLogica?id=${bo.id}'/>"/>
                                        <img ${bo.bajaLogica == true ? 'class="d-none"' : ''} src="/resources/assets/img/icons/delete.png" alt="">
                                        <a ${bo.bajaLogica == false ? 'class="d-none"' : ''} href="<c:url value='/medio/upBajaLogica?id=${bo.id}'/>"/>
                                        <img ${bo.bajaLogica == false ? 'class="d-none"' : ''} src="/resources/assets/img/icons/arrowUp2.png" alt="">
                                    </td>
                                    <td>${bo.descripcion}</td>
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

        var valueOfSearchStoraged = sessionStorage['medioSearchDataTable'] || '';
        $('.dataTables_filter input[type="search"]').val(valueOfSearchStoraged).keyup();

        $('.dataTables_filter input').unbind().keyup(function(e) {

            var valueSearchDataTable = $(this).val();
            if (valueSearchDataTable.length>=1) {
                dataTableToCompleteList.search(valueSearchDataTable).draw();
                sessionStorage['medioSearchDataTable'] = valueSearchDataTable;

            } else {
                dataTableToCompleteList.search('').draw();
                sessionStorage['medioSearchDataTable'] = "";
            }
        });

    });
</script>