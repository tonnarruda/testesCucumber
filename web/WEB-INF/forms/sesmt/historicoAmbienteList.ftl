<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Historicos Ambientes</title>
</head>
<body>
	<@display.table name="historicoAmbientes" id="historicoAmbiente" pagesize=10 class="dados" defaultsort=2 sort="list">
		<@display.column title="Ações" class="acao">
			<a href="javascript: executeLink('prepareUpdate.action?historicoAmbiente.id=${historicoAmbiente.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?historicoAmbiente.id=${historicoAmbiente.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="id" title="Id"/>
		<@display.column property="ambiente.id" title="Ambiente"/>
		<@display.column property="descricao" title="Descricao"/>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}"/>
		<@display.column property="dataInativo" title="DataInativo" format="{0,date,dd/MM/yyyy}"/>
	</@display.table>

	<div class="buttonGroup">
	<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');" accesskey="N">
	</button>
	</div>
</body>
</html>