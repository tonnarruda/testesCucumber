<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		.col-data { text-align:center; width:100px; }
	</style>
	
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>PCMATs da Obra - ${nomeObra}</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="pcmats" id="pcmat" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?pcmat.id=${pcmat.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?pcmat.id=${pcmat.id}&obra.id=${obra.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column title="A partir de" property="APartirDe" format="{0,date,dd/MM/yyyy}" class="col-data"/>
		<@display.column title="Início da Obra" property="dataIniObra" format="{0,date,dd/MM/yyyy}" class="col-data"/>
		<@display.column title="Fim da Obra" property="dataFimObra" format="{0,date,dd/MM/yyyy}" class="col-data"/>
		<@display.column title="">
		</@display.column>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?pcmat.obra.id=${obra.id}'"></button>
		<button class="btnVoltar" onclick="window.location='list.action'"></button>
	</div>
</body>
</html>
