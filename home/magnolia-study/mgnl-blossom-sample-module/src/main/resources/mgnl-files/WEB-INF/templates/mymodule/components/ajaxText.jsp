<%@ include file="/WEB-INF/templates/mymodule/includes/taglibs.jsp"%>
<h1>
	<cmsold:out nodeDataName="body" />
</h1>
<br />
Select Article:
<br />
<a href="javascript:dynComponentGet${param.componentId}('articleId=1');">Article1
</a>
<br />
<a href="javascript:dynComponentGet${param.componentId}('articleId=2');">Article2
</a>
<br />
<a href="javascript:dynComponentGet${param.componentId}('articleId=4');">Article4
</a>
<br />
<a href="javascript:dynComponentGet${param.componentId}('articleId=5');">Article5
</a>
<br />

Your Article - ${sampleText}
