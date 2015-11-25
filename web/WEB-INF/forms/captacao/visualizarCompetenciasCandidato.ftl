<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CompetenciaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		
		.tabelaCompetencias { padding: 10px; }
		.dados { width: 100%; }
		.dados td { border: none; }
	</style>
	
	<title>Competências do Candidato</title>
</head>
<body>
	<div class="tabelaCompetencias">
		<#if solicitacoesNiveisCompetenciaFaixaSalariaisSalvos?exists && 0 < solicitacoesNiveisCompetenciaFaixaSalariaisSalvos?size>
			<span style='background-color: #BFC0C3; float: left;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span style='float: left;'>&nbsp;Níveis de Competência exigidos para o Cargo/Faixa Salarial</span><br /><br /><br />
		
			<#assign i = 0/>
			<#list solicitacoesNiveisCompetenciaFaixaSalariaisSalvos as solicitacaonivelCompetencia>
				<caption>
					<div style="text-align:center;line-height: 20px;; background-color: #999999;font-weight: bold; color: #FFF; height: 20px">Soicitação: ${solicitacaonivelCompetencia.descricao}&nbsp&nbsp&nbsp&nbsp&nbsp&nbspCargo/Faixa Salarial: ${solicitacaonivelCompetencia.faixaSalarial.nomeDeCargoEFaixa}</div>
				</caption> 
				<table id="configuracaoNivelCompetencia" class="dados">
					<thead>
						<tr>
							<th>Competência</th>
							<#list solicitacaonivelCompetencia.nivelCompetencias as nivel>
								<th>${nivel.descricao}</th>
							</#list>
						</tr>
					</thead> 
					<#list solicitacaonivelCompetencia.configuracaoNivelCompetencias as configuracaoNivelCompetencia>
						<tbody>
							<#if (i%2) == 0>
								<tr class="old">
							<#else>
								<tr class="even">
							</#if>
								<td class="competencia-${configuracaoNivelCompetencia.competenciaId} tipo-competencia-${configuracaoNivelCompetencia.tipoCompetencia}">${configuracaoNivelCompetencia.competenciaDescricao}</td>
								<#list solicitacaonivelCompetencia.nivelCompetencias as nivel>
									<#if configuracaoNivelCompetencia.nivelCompetenciaFaixaSalarial?exists && nivel.id == configuracaoNivelCompetencia.nivelCompetenciaFaixaSalarial.id>
										<td style="text-align: center; background-color:#BFC0C3" class="nivel-${nivel.id}">
									<#else>	
										<td style="text-align: center;" class="nivel-${nivel.id}">
									</#if>
									<#if nivel.id == configuracaoNivelCompetencia.nivelCompetencia.id>
										<img border="0" style="align: top;" src="<@ww.url includeParams="none" value="/imgs/check_ok.gif"/>">
									</#if>
									</td>
								</#list>
							</tr>
						</tbody>
					<#assign i = i + 1/>
					</#list>
				</table>
			</#list>
		</#if>
	</div>
</body>
</html>
