<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Tipo de Despesa</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="tipoDespesas" id="tipoDespesa" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="javascript: executeLink('prepareUpdate.action?tipoDespesa.id=${tipoDespesa.id}');"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?tipoDespesa.id=${tipoDespesa.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" title="Descrição"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');"></button>
	</div>
</body>
</html>
