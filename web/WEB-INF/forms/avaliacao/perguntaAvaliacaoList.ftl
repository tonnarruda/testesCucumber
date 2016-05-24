<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Perguntas da Avaliação</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="perguntas" id="pergunta" class="dados">
		<@display.column title="Ações" class="acao" style="width:60px;">
		
			<#if !temCriterioRespondido>
				<#assign editarOuVisualizar = "Editar" />
			<#else>
				<#assign editarOuVisualizar = "Visualizar" />
			</#if>
			<a href="javascript: executeLink('prepareUpdate.action?pergunta.id=${pergunta.id}&avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}');"><img border="0" title="${editarOuVisualizar}" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			
			<#if !temCriterioRespondido>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?pergunta.id=${pergunta.id}&avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</#if>
			
		</@display.column>
		<@display.column title="Ordem" property="ordem" style="width:50px;" />
		<@display.column title="Pergunta" property="texto" style="width:900px;" />
		<@display.column title="Tipo" property="tipoTexto" style="width:90px;" />
	</@display.table>
	
	<div class="buttonGroup">
		<#if !temCriterioRespondido>
			<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}');"></button>
		</#if>
		<#if modeloAvaliacao = tipoModeloAvaliacao.getSolicitacao()>
			<#assign urlVoltar = "../../avaliacao/modeloCandidato/list.action" />
		<#else>
			<#assign urlVoltar = "../../avaliacao/modelo/list.action" />
		</#if>
		
		
		<button onclick="window.location='${urlVoltar}?modeloAvaliacao=${modeloAvaliacao}'" class="btnVoltar"></button>
	</div>
</body>
</html>
