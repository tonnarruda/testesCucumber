<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Providências</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="providencias" id="providencia" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="javascript: executeLink('prepareUpdate.action?providencia.id=${providencia.id}');"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){executeLink('delete.action?providencia.id=${providencia.id}');});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" title="Descrição"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');"></button>
	</div>
</body>
</html>
