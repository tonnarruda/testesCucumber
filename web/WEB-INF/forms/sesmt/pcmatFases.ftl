<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	
	<title>PCMAT</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#include "pcmatLinks.ftl"/>
	
	<@display.table name="fases" id="fase" class="dados">
		<@display.column title="Fases" property="descricao" style="width:34%"/>
		<@display.column title="Riscos" style="width:33%"/>
		<@display.column title="Medidas de Segurança" style="width:33%"/>
	</@display.table>
</body>
</html>
