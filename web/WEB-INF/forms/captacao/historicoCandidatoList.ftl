<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Histórico do Candidato</title>
</head>
<body>

	<table width="100%">
		<tr>
			<td>Área: <#if candidatoSolicitacao.solicitacao.areaOrganizacional?exists && candidatoSolicitacao.solicitacao.areaOrganizacional.nome?exists>${candidatoSolicitacao.solicitacao.areaOrganizacional.nome}</#if></td>
			<td>Cargo: <#if candidatoSolicitacao.solicitacao?exists && candidatoSolicitacao.solicitacao.faixaSalarial?exists && candidatoSolicitacao.solicitacao.faixaSalarial.cargo?exists && candidatoSolicitacao.solicitacao.faixaSalarial.cargo.nome?exists>${candidatoSolicitacao.solicitacao.faixaSalarial.cargo.nome}</#if></td>
			<td>Solicitante: <#if candidatoSolicitacao.solicitacao.solicitante?exists && candidatoSolicitacao.solicitacao.solicitante.nome?exists>${candidatoSolicitacao.solicitacao.solicitante.nome}</#if></td>
			<td>Vagas: <#if candidatoSolicitacao.solicitacao.quantidade?exists>${candidatoSolicitacao.solicitacao.quantidade}</#if></td>
		</tr>

		<tr>
			<td colspan="4">
			<b>${candidatoSolicitacao.candidato.nome}</b>
			</td>
		</tr>
	</table>
	<br/>
	<@display.table name="historicoCandidatos" id="historicoCandidato" class="dados">
		<@display.column title="Ações" media="html" class="acao" style="width: 60px;">
			<a href="prepareUpdate.action?historicoCandidato.id=${historicoCandidato.id}&candidatoSol.id=${candidatoSolicitacao.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?historicoCandidato.id=${historicoCandidato.id}&candidatoSolicitacao.id=${candidatoSolicitacao.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="etapaSeletiva.nome" title="Etapa" />
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center;width: 90px;"/>
		<@display.column property="responsavel" title="Responsável" />
		<@display.column property="aptoFormatado" title="Apto" style="text-align: center;width: 50px"/>
		<@display.column title="Obs." style="text-align: center;width: 50px">
			<#if historicoCandidato.observacao?exists && historicoCandidato.observacao?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${historicoCandidato.observacao?j_string}');return false">...</span>
			</#if>
		</@display.column>
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?candidatoSol.id=${candidatoSolicitacao.id}'" accesskey="I"></button>
		<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${candidatoSolicitacao.solicitacao.id}'" class="btnVoltar" accesskey="V"></button>
		<button class="btnImprimirPdf" onclick="window.location='imprimirHistorico.action?candidatoSolicitacao.id=${candidatoSolicitacao.id}'">
	</div>
</body>
</html>