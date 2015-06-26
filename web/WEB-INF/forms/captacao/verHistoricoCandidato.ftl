<html>
<head>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/curriculo.css?version=${versao}"/>');
</style>
<title></title>
</head>
<body>
<div id="containerHc" style="width:700px;">
<#if (historicos?size == 0)>
	<div align="center">Candidato não possui histórico.</div>
<#else>
<#list historicos as historicoSolicitacao>
	<table width='700px' class='cabeca'>
		<tr>
			<td width='35%'><b>Área:</b> <#if historicoSolicitacao?exists && historicoSolicitacao.solicitacao?exists && historicoSolicitacao.solicitacao.areaOrganizacional?exists && historicoSolicitacao.solicitacao.areaOrganizacional.nome?exists>${historicoSolicitacao.solicitacao.areaOrganizacional.nome}</#if></td>
			<td width='30%'><b>Cargo:</b> <#if historicoSolicitacao?exists && historicoSolicitacao.solicitacao?exists && historicoSolicitacao.solicitacao.faixaSalarial?exists && historicoSolicitacao.solicitacao.faixaSalarial.cargo?exists && historicoSolicitacao.solicitacao.faixaSalarial.cargo.nomeMercado?exists> ${historicoSolicitacao.solicitacao.faixaSalarial.cargo.nomeMercado}</#if></td>
			<td width='25%'><b>Solicitante:</b> <#if historicoSolicitacao?exists && historicoSolicitacao.solicitacao?exists && historicoSolicitacao.solicitacao.solicitante?exists && historicoSolicitacao.solicitacao.solicitante.nome?exists> ${historicoSolicitacao.solicitacao.solicitante.nome}</#if></td>
			<td width='10%'><b>Vagas:</b> <#if historicoSolicitacao?exists && historicoSolicitacao.solicitacao?exists && historicoSolicitacao.solicitacao.quantidade?exists> ${historicoSolicitacao.solicitacao.quantidade}</#if></td>
		</tr>
	</table>

	<table width='700px' class='dadosHist' cellspacing='0' >
		<thead>
			<tr>
				<th>Etapa</th>
				<th>Data</th>
				<th>Responsável</th>
				<th>Apto</th>
				<th>Observação</th>
			</tr>
		</thead>
		<tbody>
			<#list historicoSolicitacao.historicos as hist>
				<tr>
					<td width='25%'><#if hist.etapaSeletiva?exists && hist.etapaSeletiva.nome?exists>${hist.etapaSeletiva.nome}</#if></td>
					<td width='10%'><#if hist.data?exists>${hist.data?string("dd'/'MM'/'yyyy")}</#if></td>
					<td width='10%'><#if hist.responsavel?exists>${hist.responsavel}<#else>&nbsp;</#if></td>
					<td width='5%'><#if hist.aptoFormatado?exists>${hist.aptoFormatado}<#else>-</#if></td>
					<td width='50%'><#if hist.observacao?exists && hist.observacao != "">${hist.observacao}<#else>&nbsp;</#if></td>
				</tr>
			</#list>
		</tbody>
	</table>
	<br>
</#list>
</#if>
</div>
</body>
</html>