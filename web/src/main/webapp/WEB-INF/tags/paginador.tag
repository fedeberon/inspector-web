<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="page" required="true"  %>
<%@attribute name="formName" required="true"  %>
<%--<%@attribute name="noMorePages"%>--%>

<c:if test="${pageScope.page>0}">
    <button name="paginate" onclick="pagAnterior('${pageScope.formName}')"  class="btn btn-info btn-fill"><i class="nc-icon nc-stre-left"></i>&nbsp;Anterior</button>

</c:if>
<c:if test="${pageScope.noMorePages!=0}">
    <button name="paginate" onclick="pagSiguiente('${pageScope.formName}')"  class="btn btn-info btn-fill">Siguiente&nbsp;<i class="nc-icon nc-stre-right"></i></button>
</c:if>
