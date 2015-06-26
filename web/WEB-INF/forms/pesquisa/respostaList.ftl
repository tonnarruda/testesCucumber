<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Opções de Respostas</title>
</head>
<body>

	<b>${pesquisa.titulo}<br>
	<#if pesquisa.dataInicio?exists && pesquisa.dataFim?exists>
	${pesquisa.dataInicio?string("dd'/'MM'/'yyyy")} a ${pesquisa.dataFim?string("dd'/'MM'/'yyyy")}</b>
	</#if>
	<br>
	<#if pesquisa.tipo != 4>
	${pergunta.texto}
	</#if>
	<br>
	<br>


	<@display.table name="respostas" id="resposta" class="dados" sort="list">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?resposta.id=${resposta.id}&pergunta.id=${pergunta.id}&pesquisa.id=${pesquisa.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?resposta.id=${resposta.id}&pergunta.id=${pergunta.id}&pesquisa.id=${pesquisa.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="texto" title="Texto"/>
	</@display.table>


	<div class="buttonGroup">
		<button class="btnNovaOpcaoResposta" onclick="window.location='prepareInsert.action?pergunta.id=${pergunta.id}&pesquisa.id=${pesquisa.id}'" accesskey="N">
		</button>
		<button class="btnVoltar" onclick="window.location='../pergunta/list.action?pesquisa.id=${pesquisa.id}'" accesskey="V">
		</button>
	</div>
</body>
</html>