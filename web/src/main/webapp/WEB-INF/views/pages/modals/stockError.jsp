<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page   import="com.ideaas.services.exception.StockException.Code" %>

<!-- Modal -->


<c:choose>
<c:when test="${stockErrorCode != null}">
<div class="modal fade" id="stock-error-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content" style="position: absolute; top:25vh; max-width: 82vh">
            <div class="modal-header">
                <strong class="modal-title">Error al cargar el Stock</strong>
                <button type="button" class="close" data-dismiss="modal" onclick="clearResponseLabel()" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div>
                    <c:choose>
                        <c:when test="${stockErrorCode.toString() == 'WRONG_MINIMUM_DEVICES_NUMBER_BETWEEN_DEPOSIT_AND_REPAIR'}">
                            La cantidad mínima de dispositivos en depósito y reparación debe ser igual o mayor que la cantidad de ubicaciones inactivas: ${inactiveLocationsCount}
                        </c:when>
                        <c:when test="${stockErrorCode == 'NOT_INITIALIZED'}">
                            No se han cargado datos en la tabla Stock. Antes de realizar culaquier modificai&oacute;n, se debe cargar la tabla de stock.
                        </c:when>
                        <c:when test="${stockErrorCode == 'WRONG_CANT_ELEMENT_DEPOSIT'}">
                            La cantidad de dispositivos que est&aacute; intentando mover de dep&oacute;sito a calle es inferior a la cantidad que posee en esta &uacute;ltima. Por favor, actualice su stock de dispositivos antes de realizar  la operaci&oacute;n.
                        </c:when>
                        <c:when test="${stockErrorCode == 'WRONG_CANT_ELEMENT_STREET'}">
                            La cantidad de dispositivos que est&aacute; intentando est&aacute; intentando mover de calle a reparaci&oacute;n es inferior a la cantidad que posee en esta &uacute;ltima. Por favor, actualice su stock de dispositivos antes de realizar  la operaci&oacute;n.
                        </c:when>
                        <c:when test="${stockErrorCode ==  'WRONG_CANT_ELEMENT_REPAIR'}">
                            La cantidad de dispositivos que est&aacute; intentando mover de reparaci&oacute;n a calle es inferior a la cantidad que posee en esta &uacute;ltima. Por favor, actualice su stock de dispositivos antes de realizar  la operaci&oacute;n.
                        </c:when>
                    </c:choose>
                </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>

                <a href="/stock/list" type="submit" class="btn btn-primary"><i class="nc-icon nc-map-big"></i>  Ir a Stock</a>
            </div>
        </div>
    </div>
</div>
<script>
    $('#stock-error-modal').modal('show');
</script>
</c:when>
</c:choose>