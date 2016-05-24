<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Aspectos da Avaliação</title>
</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>
	<@display.table name="aspectos" id="aspecto" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="javascript: executeLink('prepareUpdate.action?aspecto.id=${aspecto.id}&avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}');"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('deleteAvaliacao.action?aspecto.id=${aspecto.id}&avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
	</@display.table>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action" page='${page}'/>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}');"></button>
		
		<#if modeloAvaliacao = tipoModeloAvaliacao.getSolicitacao()>
			<#assign urlVoltar = "../../avaliacao/modeloCandidato/list.action" />
		<#else>
			<#assign urlVoltar = "../../avaliacao/modelo/list.action" />
		</#if>
		<button onclick="window.location='${urlVoltar}?modeloAvaliacao=${modeloAvaliacao}'" class="btnVoltar"></button>
	</div>
</body>
</html>