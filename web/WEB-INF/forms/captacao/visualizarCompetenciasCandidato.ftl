<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CompetenciaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		
		.tabelaCompetencias { padding: 10px; }
		.dados { width: 710px; }
		.dados td { border: none; }
	</style>
	
	<script type="text/javascript">
		$(function() {
			DWRUtil.useLoadingMessage('Carregando...');
			
			<#if solicitacao?exists && solicitacao.id?exists>
				$('#solicitacao').val(${solicitacao.id});
				getNiveisCargo(${solicitacao.id});
			</#if>
			
			$('#solicitacao').change(function() {
				getNiveisCargo(this.value);
			});
		});
		
		function getNiveisCargo(solicitacaoId)
		{
			$('tr.even > td').css('background-color', '#EFEFEF');
			$('tr.odd > td').css('background-color', '#FFF');
		
			if (solicitacaoId != '')
				CompetenciaDWR.getSugestoesBySolicitacao(sugerirNiveisCargo, solicitacaoId);
		}
		
		function sugerirNiveisCargo(data)
		{
			$(data).each(function(i, nivelSugerido) {
				var linhaSugerida = $('tr').has('td.competencia-' + nivelSugerido.competenciaId).has('td.tipo-competencia-' + nivelSugerido.tipoCompetencia);
				linhaSugerida.find('.nivel-' + nivelSugerido.nivelCompetencia.id).css('background-color', '#BFC0C3');			
			});
		}
	</script>

	<title>Competências do Candidato</title>
</head>
<body>
	<div class="tabelaCompetencias">
		<#if niveisCompetenciaFaixaSalariaisSalvos?exists && 0 < niveisCompetenciaFaixaSalariaisSalvos?size>
			<p>Comparar as competências do candidato com as exigidas pelo Cargo da seguinte solicitação:</p>

			<div style='width: 710px;'>
				<@ww.select label="Solicitação" name="solicitacao.id" id="solicitacao" headerKey="" headerValue="Selecione" listKey="id" listValue="descricao" list="solicitacoes" theme="simple"/>
				<span style='float: right;'>&nbsp;Níveis de Competência exigidos para o Cargo/Faixa Salarial</span></span><span style='background-color: #BFC0C3; float: right;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<div/>
		
			<br />
		
			<@display.table name="niveisCompetenciaFaixaSalariaisSalvos" id="configuracaoNivelCompetencia" class="dados">
				<@display.column title="Competência" property="competenciaDescricao" class="competencia-${configuracaoNivelCompetencia.competenciaId} tipo-competencia-${configuracaoNivelCompetencia.tipoCompetencia}"/>
				
				<#list nivelCompetencias as nivel>			
					<@display.column title="${nivel.descricao}" class="nivel-${nivel.id}" style="text-align: center;">
						<#if nivel.id == configuracaoNivelCompetencia.nivelCompetencia.id>
							<img border="0" style="align: top;" src="<@ww.url includeParams="none" value="/imgs/check_ok.gif"/>">
						</#if>
					</@display.column>
				</#list>
			</@display.table>
		</#if>
	</div>
</body>
</html>
