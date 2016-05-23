<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Aspectos da ${tipoQuestionario.getDescricaoMaisc(questionario.tipo)} - ${questionario.titulo}</title>
</head>
<body>
<@ww.actionerror/>
<@ww.actionmessage/>
<@display.table name="aspectos" id="aspecto" class="dados">
	<@display.column title="Ações" class="acao">
		<#if pergunta?exists && pergunta.id?exists>
			<a href="javascript: executeLink('prepareUpdate.action?aspecto.id=${aspecto.id}&pergunta.id=${pergunta.id}&questionario.id=${questionario.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?aspecto.id=${aspecto.id}&pergunta.id=${pergunta.id}&questionario.id=${questionario.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		<#else>
			<a href="javascript: executeLink('prepareUpdate.action?aspecto.id=${aspecto.id}&questionario.id=${questionario.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?aspecto.id=${aspecto.id}&questionario.id=${questionario.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</#if>
	</@display.column>
	<@display.column property="nome" title="Nome"/>
</@display.table>

<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action" page='${page}'/>

<div class="buttonGroup">
	<#if pergunta?exists && pergunta.id?exists>
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action?pergunta.id=${pergunta.id}&questionario.id=${questionario.id}');" accesskey="I">
		</button>
		<button onclick="javascript: executeLink('../pergunta/prepareUpdate.action?pergunta.id=${pergunta.id}&questionario.id=${questionario.id}');" class="btnVoltar" accesskey="V">
		</button>
	<#else>
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action?questionario.id=${questionario.id}');" accesskey="I">
		</button>
		<#-- Monta o botão de acordo com o destino pesquisa, avaliação, entrevista-->
		<#if urlVoltar?exists>
			<button class="btnVoltar" onclick="javascript: executeLink('${urlVoltar}');"></button>
		</#if>
	</#if>
</div>
</body>
</html>