<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		#menuPcmat a#menuAreasVivencia { color: #FFCB03; }
	</style>

	<title>PCMAT</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#include "pcmatLinks.ftl"/> 
	
	<@display.table name="areasVivenciaPcmat" id="areaVivenciaPcmat" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?areaVivenciaPcmat.id=${areaVivenciaPcmat.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?areaVivenciaPcmat.id=${areaVivenciaPcmat.id}&pcmat.id=${pcmat.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column title="Área de Vivência" property="areaVivencia.nome" style="width:120px"/>
		<@display.column title="Descrição" property="descricao"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?areaVivenciaPcmat.pcmat.id=${pcmat.id}'"></button>
	</div>
</body>
</html>
