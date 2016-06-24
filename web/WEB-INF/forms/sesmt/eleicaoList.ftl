<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Eleições</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<#if (eleicaos?exists && eleicaos?size > 0)>
		<@display.table name="eleicaos" id="eleicao" class="dados" style="width: 780px;">
			<@display.column title="Ações" class="acao" style="width: 60px;">
				<a href="javascript: executeLink('prepareUpdate.action?eleicao.id=${eleicao.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?eleicao.id=${eleicao.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</@display.column>
			<@display.column property="posse" title="Posse" format="{0,date,dd/MM/yyyy}" style="text-align:center;width: 90px;"/>
			<@display.column property="votacaoPeriodoFormatado" title="Votação" style="text-align:center;width: 180px;"/>
			<@display.column property="descricao" title="Descrição" style="width: 240px;"/>
			<@display.column property="estabelecimento.nome" title="Estabelecimento" style="width: 240px;"/>
		</@display.table>
	</#if>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');" accesskey="I"></button>
	</div>
</body>
</html>