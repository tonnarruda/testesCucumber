<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Grupos de Investimentos</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@display.table name="grupoGastos" id="grupoGasto" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="../gasto/prepareAgrupar.action?grupoGastoAgrupar.id=${grupoGasto.id}"><img border="0" title="Agrupar Investimentos" src="<@ww.url value="/imgs/agrupar.gif"/>"></a>
			<a href="prepareUpdate.action?grupoGasto.id=${grupoGasto.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?grupoGasto.id=${grupoGasto.id}&page=${page}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>