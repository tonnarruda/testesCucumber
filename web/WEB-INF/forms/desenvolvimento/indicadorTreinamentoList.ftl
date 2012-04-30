<html>
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/painelIndicadoresTreinamentos.css"/>');
		@import url('<@ww.url value="/css/indicadores.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
		
		.gogDivEsq div.legend > table, .gogDivDir div.legend > table {
		    border-spacing: 0 !important;
		    left: 240px;
		    width: 240px;
		}
	</style>

	<title>Painel de Indicadores de T&D</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#include "../ftl/showFilterImports.ftl" />
	
	<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/qtip.js"/>'></script>
	
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
		
		$(function () {
			montaPie(${grfTreinamento}, "#treinamento", {combinePercentMin: -1, percentMin: 0} );
			montaPie(${grfFrequencia}, "#frequencia", {combinePercentMin: -1, percentMin: 0} );
			montaPie(${grfDesempenho}, "#desempenho", {combinePercentMin: -1, percentMin: 0} );
			montaPie(${grfCusto}, "#custo", { combinePercentMin: -1, percentMin: 0.02, legendLabelFormatter: formataLegendaCusto });
			
	    	$('#tooltipHelpIndicadoresTeD').qtip({
				content:'<strong>O resultado dos indicadores de T&D são obtidos através dos seguintes cálculos:</strong>'+
						'<br/><br/><strong>- Investimento médio da hora de treinamento (R$)</strong><br/>'+
						'&nbsp&nbsp custoMedioHora = somaCusto / somaHoras<br/>'+
						'&nbsp&nbsp a somaCusto e Hora dos cursos é referente ao período e empresa selecionados no filtro.<br/>'+
						'<br/>'+
						'<strong>- Investimento per capita (R$)</strong><br/>'+
						'&nbsp&nbsp custoPerCapita (por pessoa) = CustoTotal / Qtd. Colaboradores Ativos e Inscritos<br/>'+
						'&nbsp&nbsp Qtd. Colaboradores Ativos e inscritos: colaboradores ativos na empresa com data de admissão menor que a data fim do filtro e<br/>'+
						'&nbsp&nbsp que não esteja desligado ou com data de desligamento depois da data fim do filtro.<br/>'+
						'<br/>'+
						'<strong>- Horas de treinamento per capita</strong><br/>'+
						'&nbsp&nbsp horasPerCapita = ((QtdHoras / 60) * QtdParticipantes) / QtdAtivos<br/><br/>'+
						'&nbsp&nbsp QtdHoras: soma das horas.<br/>'+
						'&nbsp&nbsp QtdParticipantes: colaboradores da turma que estejam dentro do período do filtro.<br/>'+
						'&nbsp&nbsp QtdAtivos: colaboradores ativos na empresa com data admissao menor que a data fim do filtro e<br/>'+
						'&nbsp&nbsp que não esteja desligado ou com data de desligamento depois da data fim do filtro.<br/>'+
						'<br/>'+
						'<strong>- Total de investimentos dos treinamentos realizados (R$)</strong><br/>'+
						'&nbsp&nbsp Soma de todos os custos lançados para os cursos em determinado período e empresa.<br/>'+
						'<br/>'+
						'<strong>- Percentual de investimentos em relação ao faturamento</strong><br/>'+
						'&nbsp&nbsp Percentual = (custos / somafaturamentoPeríodo) * 100<br/><br/>'+
						'&nbsp&nbsp somafaturamentoPeriodo: total do faturamento no período e empresa.<br/>'+
						'&nbsp&nbsp * apenas para faturamento maior que zero.<br/>'+
						'<br/>'+
						'<strong>- Percentual de frequência (aprovados)</strong><br/>'+
						'&nbsp&nbsp Resultado = (QtdDiasPresentes / QtdDiasTotal ) * 100<br/><br/>'+
						'&nbsp&nbsp QtdDiasTotal: quantidade de colaboradores na turma x a quantidade de dias de curso.<br/>'+
						'&nbsp&nbsp QtdDiasPresentes: soma de todas as turmas do período selecionado.<br/><br/>'+
						'&nbsp&nbsp * apenas das turmas realizadas<br/>'
				,
				style: {
		        	 width: '100px'
		        }
			});
			
		});
		
		function formataLegendaCusto(label, series)
		{
			return '<span class="legend">' + label + ' &#x2013; '+ (isNaN(series.percent) ? 0 : series.percent.toFixed(2)) + '% (R$ '+ series.datapoints.points[1].toFixed(2).replace(/,/g,'#').replace(/\./g,',').replace(/#/g,'.') + ')</span>';
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
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" id="formBusca" action="list.action" method="POST">
			Período:<br>
			<@ww.datepicker name="indicadorTreinamento.dataIni" id="dataIni" value="${dateIni}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.datepicker name="indicadorTreinamento.dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />
	
			<button onclick="return enviaForm(1);" class="btnPesquisar grayBGE"></button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
		</div><br>
			<@ww.div>
				<div id="gogDiv">
					<div class="gogDivTotal">
						<div class="gogDivTituloX">
							Indicadores de T&D
							<img id="tooltipHelpIndicadoresTeD" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" /><br>
						</div>
						<div class="gogDivFormulario">
							<dl style="float:right;">
								<dt>Total de investimentos dos treinamentos realizados (R$)</dt>
								<dd>${indicadorTreinamento.custoTotalFmt}</dd>
								<dt>Percentual de investimentos em relação ao faturamento</dt>
								<dd>${percentualInvestimentoEmTeD?string(",##0.00")}%</dd>
								<dt>Percentual de frequência (aprovados) </dt>
								<dd>${percentualFrequencia?string(",##0.00")}%</dd>
							</dl>
							<dl>
								<dt>Investimento médio da hora de treinamento (R$)</dt>
								<dd>${indicadorTreinamento.custoMedioHoraFmt}</dd>
								<dt>Investimento per capita (R$)</dt>
								<dd>${indicadorTreinamento.custoPerCapitaFmt}</dd>
								<dt>Horas de treinamento per capita</dt>
								<dd>${indicadorTreinamento.horasPerCapitaFmt}</dd>
							</dl>
						</div>
						<br />
					</div>
				
					<div class="gogDivEsq">
						<div class="gogDivTituloX">Qtd. Prevista de Participantes x Inscritos</div>
						<div class="gogDivFormularioX">
							<div id="frequencia" class="graph" ></div>
						</div>
						<br />
						<div class="gogDivTituloX">Aproveitamento dos Treinamentos</div>
						<div class="gogDivFormularioX">
							<div id="desempenho" class="graph" ></div>
						</div>
						<br />
					</div>
					
					<div class="gogDivDir">
						<div class="gogDivTituloX">Cumprimento do Plano de Treinamento</div>
						<div class="gogDivFormularioX">
							<div id="treinamento" class="graph"></div>
						</div>
						<br />
						<div class="gogDivTituloX">Custo por Tipo de Despesa</div>
						<div class="gogDivFormularioX">
							<div id="custo" class="graph" ></div>
						</div>
						<br />
					</div>
				</div>
			</@ww.div>
	
</body>
</html>