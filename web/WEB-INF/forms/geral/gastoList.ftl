<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Investimentos</title>
</head>
<body>
	<@display.table name="gastos" id="gasto" class="dados" >
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?gasto.id=${gasto.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?gasto.id=${gasto.id}&page=${page}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column property="grupoGasto.nome" title="Grupo de Investimento"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}' idFormulario=""/>

	<div class="buttonGroup">
	<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
	</button>
</div>
</body>
</html>