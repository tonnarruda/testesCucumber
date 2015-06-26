<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>Fases</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" validate="true" method="POST" id="formBusca">
			<@ww.textfield label="Descrição" name="descricao" id="descricao" cssStyle="width: 350px;"/>
			<button type="submit" class="btnPesquisar grayBGE"></button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	
	<br />
	
	<@display.table name="fases" id="fase" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?fase.id=${fase.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?fase.id=${fase.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column title="Descrição" property="descricao"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
