<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/painelIndicadoresTreinamentos.css"/>');
	</style>

	<title>Painel de Indicadores de T&D</title>

	<#include "../ftl/mascarasImports.ftl" />
	<script type="text/javascript">
		function enviaForm(opcao)
		{
			if (opcao == 1)
			{
				document.formBusca.action="list.action";
			}
			else if(opcao == 2)
			{
				document.formBusca.action="imprimir.action";
			}

			return validaFormularioEPeriodo('formBusca', new Array('dataIni','dataFim'), new Array('dataIni','dataFim'));
		}
	</script>

	<#if indicadorTreinamento?exists && indicadorTreinamento.dataIni?exists>
		<#assign dateIni = indicadorTreinamento.dataIni?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if indicadorTreinamento?exists && indicadorTreinamento.dataFim?exists>
		<#assign dateFim = indicadorTreinamento.dataFim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>

	<#assign parametrosDatas = "?indicadorTreinamento.dataIni=${indicadorTreinamento.dataIni?date}&indicadorTreinamento.dataFim=${indicadorTreinamento.dataFim?date}" />
</head>
<body>
	<@ww.form name="formBusca" id="formBusca" action="list.action" method="POST">
		Período:<br>
		<@ww.datepicker name="indicadorTreinamento.dataIni" id="dataIni" value="${dateIni}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="indicadorTreinamento.dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />

		<button onclick="return enviaForm(1);" class="btnPesquisar"></button>
		<button onclick="return enviaForm(2);" class="btnImprimirPdf"></button>

		</div><br>
			<@ww.div>
					<div id="gogDiv">
					<div class="gogDivEsq">
						<div class="gogDivTituloX">Indicadores de T&D</div>
						<div class="gogDivFormularioX">
							<dl>
								<dt>Custo médio da hora de treinamento (R$)</dt>
								<dd>${indicadorTreinamento.custoMedioHoraFmt}</dd>
								<dt>Custo per capita (R$)</dt>
								<dd>${indicadorTreinamento.custoPerCapitaFmt}</dd>
								<dt>Horas de treinamento per capita</dt>
								<dd>${indicadorTreinamento.horasPerCapitaFmt}</dd>
								<dt>Total de investimentos (R$)</dt>
								<dd>${indicadorTreinamento.custoTotalFmt}</dd>
								<dt>Percentual de frequência (aprovados) </dt>
								<dd>${percentualFrequencia}%</dd>
							</dl>
						</div>
						<br />
						<div class="gogDivTituloX">Cumprimento do Plano de Treinamento</div>
						<div class="gogDivFormularioX">
							<img src="graficoCumprimentoPlanoTreinamento.action${parametrosDatas}" class="grafico"/>
							<span><div class="quadradinho" style="background: #ff8080"></div>&nbsp;Realizados (${indicadorTreinamento.graficoQtdTreinamentosRealizados}) </span><br /><br />
							<span><div class="quadradinho" style="background: #8080ff"></div>&nbsp;Não realizados (${indicadorTreinamento.graficoQtdTreinamentosNaoRealizados})</span><br /><br />
							<div style="clear: both"></div>
						</div>
						<br />
					</div>
					<div class="gogDivDir">
						<div class="gogDivTituloX">Vagas x Inscritos</div>
						<div class="gogDivFormularioX">
							<img src="graficoVagasPorInscrito.action${parametrosDatas}" class="grafico"/>
							<span><div class="quadradinho" style="background: #ff8080"></div>&nbsp;Vagas (${qtdParticipantesPrevistos})</span><br /><br />
							<span><div class="quadradinho" style="background: #8080ff"></div>&nbsp;Inscritos (${qtdTotalInscritosTurmas})</span><br /><br />
							<div style="clear: both"></div>
						</div>
						<br />
						<div class="gogDivTituloX">Aproveitamento dos Treinamentos</div>
						<div class="gogDivFormularioX">
							<img src="graficoDesempenho.action${parametrosDatas}" class="grafico"/>
							<span><div class="quadradinho" style="background: #ff8080"></div>&nbsp;Aprovados (${indicadorTreinamento.graficoQtdAprovados})</span><br /><br />
							<span><div class="quadradinho" style="background: #8080ff"></div>&nbsp;Reprovados (${indicadorTreinamento.graficoQtdReprovados})</span><br /><br />
							<div style="clear: both"></div>
						</div>
						<br />
					</div>
				
			</@ww.div>
	</@ww.form>
</body>
</html>