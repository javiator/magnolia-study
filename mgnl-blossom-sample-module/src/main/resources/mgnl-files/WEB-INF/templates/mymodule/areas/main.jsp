<%@ include file="/WEB-INF/templates/mymodule/includes/taglibs.jsp"%>

<div id="main">
    <c:forEach items="${components}" var="component">
        <cms:component content="${component}" />
    </c:forEach>
</div>
