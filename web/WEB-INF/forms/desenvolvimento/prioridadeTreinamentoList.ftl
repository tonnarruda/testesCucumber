<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Prioridades de Treinamentos</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@display.table name="prioridadeTreinamentos" id="prioridadeTreinamento" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?prioridadeTreinamento.id=${prioridadeTreinamento.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?prioridadeTreinamento.id=${prioridadeTreinamento.id}&page=${page}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" title="Descrição"/>
		<@display.column property="sigla" title="Sigla" style="text-align:center; width:80px"/>
		<@display.column property="numero" title="Peso" style="text-align:center; width:80px"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>