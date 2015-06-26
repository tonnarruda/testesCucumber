<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Items Investimentos</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="gastoEmpresaItems" id="gastoEmpresaItem" class="dados" defaultsort=2 sort="list">
		<@display.column title="Ações" class="acao">
			<a href="javascript:prepareUpdateItem(${gastoEmpresaItem.id})"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="javascript:newConfirm('Confirma exclusão?', function(){removeItem(${gastoEmpresaItem.id});});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="gasto.nome" title="Investimentos"/>
		<@display.column property="valor" title="Valor" format="{0, number, #,##0.00}"/>
	</@display.table>

	<div class="buttonGroup">
	<button class="btnInserir grayBG" onclick="prepareInsertItem();" accesskey="N">
	</button>
	</div>
</body>
</html>