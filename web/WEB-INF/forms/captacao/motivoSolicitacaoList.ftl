<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Motivos de Solicitação</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@display.table name="motivoSolicitacaos" id="motivoSolicitacao" pagesize=15 class="dados" defaultsort=2 sort="list">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?motivoSolicitacao.id=${motivoSolicitacao.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?motivoSolicitacao.id=${motivoSolicitacao.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" title="Descrição"/>
		<#if exibeFlagTurnover>
			<@display.column title="Turnover" style="width: 120px; text-align: center;">
				<#if motivoSolicitacao.turnover>
					Sim
				<#else>
					Não
				</#if>
			</@display.column>
		</#if>
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>