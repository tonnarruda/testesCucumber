<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<title>OrdemDeServico</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="ordemDeServicos" id="ordemDeServico" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?ordemDeServico.id=${ordemDeServico.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?ordemDeServico.id=${ordemDeServico.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
