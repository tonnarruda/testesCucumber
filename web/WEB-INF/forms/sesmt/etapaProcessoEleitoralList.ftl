<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>CIPA - Etapas do Processo Eleitoral</title>
</head>
<body>
	<@display.table name="etapaProcessoEleitorals" id="etapaProcessoEleitoral" class="dados" >
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?etapaProcessoEleitoral.id=${etapaProcessoEleitoral.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?etapaProcessoEleitoral.id=${etapaProcessoEleitoral.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Etapa" style="width:320px;"/>
		<@display.column property="prazoLegal" title="Prazo Legal" style="width:250px;"/>
		<@display.column property="prazoFormatado" title="Prazo" style="width:250px;"/>
	</@display.table>

	<#if eleicao?exists && eleicao.id?exists>
		<#assign paramEleicaoId="?eleicao.id=${eleicao.id}"/>
	<#else>
		<#assign paramEleicaoId=""/>
	</#if>
	<div class="buttonGroup">
		<button onclick="window.location='prepareInsert.action${paramEleicaoId}'" accesskey="N" class="btnInserir">
		</button>
		<#if !etapaProcessoEleitorals?exists || etapaProcessoEleitorals.empty>
			<button onclick="window.location='gerarEtapasModelo.action${paramEleicaoId}'" class="btnSugerirEtapas">
			</button>			
		</#if>
	</div>
</div>
</body>
</html>