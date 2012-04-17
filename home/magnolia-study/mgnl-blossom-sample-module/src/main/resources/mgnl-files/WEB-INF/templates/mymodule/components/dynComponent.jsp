<%@ include file="/WEB-INF/templates/mymodule/includes/taglibs.jsp"%>

<c:set var="ajxDyn" value="${requestScope['_ajxDyn']}" />

<c:if test="${empty ajxDyn}">

	<c:set var="handle" ><cmsold:out nodeDataName="uuid" /></c:set>
	<c:set var="componentId" >comp_${fn:replace(handle, '-', '_')}</c:set>	

	<script type="text/javascript"
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>


	<script type="text/javascript">
		function dynComponentGet${componentId}(queryString) {
			//$cityName = document.getElementById('articleId').value;
			//alert("hello");
			$.get("?_ajxDyn=<blossom:pecid />&" + queryString, function(data) {
				//alert(data);
				$("#dynDivArea${componentId}").html(data);
			});
			//form.submit();
		}
		function dynComponentPost${componentId}(queryString) {
			//$cityName = document.getElementById('articleId').value;
			//alert(cityName);
			$.get("?" + queryString, {
				_ajxDyn : '<blossom:pecid />'
			}, function(data) {
				//alert(data);
				$("#dynDivArea${componentId}").html(data);
			});
			//form.submit();
		}
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			dynComponentGet${componentId}('');
		});
	</script>
	<form action="?" method="POST" id="ajaxText">
		<blossom:pecid-input />
		<input type="hidden" name="articleId" id="articleId" value="0" />
	</form>
	<div id="dynDivArea${componentId}" class="dynDivArea"></div>
</c:if>


<c:if test="${!empty ajxDyn}">
	<c:set var="handle" ><cmsold:out nodeDataName="uuid" /></c:set>
	<c:set var="componentId" >comp_${fn:replace(handle, '-', '_')}</c:set>	
	<c:import url = "/WEB-INF/templates/${_viewName}">
		<c:param name = "componentId" value = "${componentId}"/>
	</c:import>	
</c:if>
