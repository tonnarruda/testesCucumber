<html>
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
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
		
		.legendColorBox { width: 20px; border: none; }
		.legendColorBox > div { border: 1px solid #fff !important; }
		
		#custo { width: 220px !important; }
		#custoLegenda { float: right; width: 220px; height: 195px; overflow-y: auto; }
		
		#formBusca { padding: 5px; }
	</style>

	<title>Painel de Indicadores de T&D</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#include "../ftl/showFilterImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/qtip.js"/>'></script>
	
	<script type='text/javascript'>
		DWREngine.setAsync(true);
	
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

			return validaFormularioEPeriodo('formBusca', new Array('dataIni', 'dataFim'), new Array('dataIni','dataFim'));
		}
		
		$(function () {
	        var participantes = ${grfFrequenciaParticipantes};
	        var inscritos = ${grfFrequenciaInscritos};
	        var presentes = ${grfFrequenciaPresentes};
		    
		    $.plot($("#frequencia"), 
		    		[
				        {label: 'Qtd. Prev. de Participantes (' + participantes[0][1] + ')', data: participantes},
				        {label: 'Inscritos (' + inscritos[0][1] + ')', data: inscritos},
				        {label: 'Presentes (' + presentes[0][1] + ')', data: presentes}
				    ], 
		    		{
		    			series: {
			                bars: {
			                	show: true, 
			                 	align: 'left',
			                 	barWidth: 0.5
			                }
				        },
				        grid: { hoverable: true },
				        legend: {
		                    position: "nw", // position of default legend container within plot
		                    backgroundColor: null, // null means auto-detect
		                    backgroundOpacity: 0 // set to 0 to avoid background
		                },
				        xaxis: {
				        	ticks: [],
				        	autoscaleMargin: 0.6
				        }
			    	});
	    
	    			    
		    $("#frequencia").bind("plothover", function (event, pos, item) {
	            if (item) 
	            {
            		previousPoint = item.dataIndex;
                    $("#tooltip").remove();
                    var y = item.datapoint[1].toFixed(0);		                    
                    showTooltip(item.pageX, item.pageY, y);
	            }
				else 
				{
                	$("#tooltip").remove();
                	previousPoint = null;            
	            }
			});
			
			function showTooltip(x, y, contents) 
			{
		        $('<div id="tooltip">' + contents + '</div>').css( {
		            position: 'absolute',
		            display: 'none',
		            top: y - 30,
		            left: x + 5,
		            border: '1px solid #fdd',
		            padding: '2px',
		            'background-color': '#fee',
		            opacity: 0.80,
		            'z-index': 20000
		        }).appendTo("body").fadeIn(0);
		    }
			
			montaPie(${grfTreinamento}, "#treinamento", {combinePercentMin: -1, percentMin: 0} );
			montaPie(${grfDesempenho}, "#desempenho", {combinePercentMin: -1, percentMin: 0} );
			montaPie(${grfCusto}, "#custo", { combinePercentMin: -1, percentMin: 0.02, legendLabelFormatter: formataLegendaCusto, container:'#custoLegenda' });
			
	    	$('#tooltipHelpIndicadoresTeD').qtip({
				content:'<strong>O resultado dos indicadores de T&D são obtidos através dos seguintes cálculos:</strong>'+
						'<br/><br/><strong>- Investimento médio da hora de treinamento (R$)</strong><br/>'+
						'&nbsp&nbsp custoMedioHora = SomaCusto / SomaHoras<br/>'+
						'&nbsp&nbsp SomaCusto: É a soma do custo de cada turma referente ao filtro.<br/>'+
						'&nbsp&nbsp SomaHoras: É o somatório de horas do curso vezes a quantidade de colaboradores.<br/>'+
						'<br/>'+
						'<strong>- Investimento per capita (R$)</strong><br/>'+
						'&nbsp&nbsp custoPerCapita (por pessoa) = CustoTotal / Qtd. Colaboradores Ativos e Inscritos<br/>'+
						'&nbsp&nbsp Qtd. Colaboradores Ativos e inscritos: colaboradores ativos na empresa com data de admissão menor que a data fim do filtro e<br/>'+
						'&nbsp&nbsp que não esteja desligado ou com data de desligamento depois da data fim do filtro.<br/>'+
						'<br/>'+
						'<strong>- Horas de treinamento per capita</strong><br/>'+
						'&nbsp&nbsp horasPerCapita = (QtdHoras * QtdParticipantes) / QtdAtivos<br/><br/>'+
						'&nbsp&nbsp QtdHoras: somatório das horas de cada dia da turma de acordo com o período.<br/>'+
						'&nbsp&nbsp QtdParticipantes: colaboradores da(s) turma(s) que estejam dentro do período do filtro.<br/>'+
						'&nbsp&nbsp QtdAtivos: colaboradores ativos na(s) empresa(s) selecionadas(*) com data de admissao menor que a data fim do filtro e<br/>'+
						'&nbsp&nbsp que não esteja desligado ou com data de desligamento posterior a data fim do filtro.<br/>'+
						'&nbsp&nbsp * Caso não seja selecionada nenhuma empresa o cálculo irá considerá todas as empresas listadas no filtro.<br/>'+
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
		
		function populaCursos()
		{
			var empresasIds = getArrayCheckeds(document.getElementById('formBusca'), 'empresasCheck');
			CursoDWR.getCursosByEmpresasParticipantes(empresasIds, 'ROLE_TED_PAINEL_IND', createListCursos);
		}
		
		function createListCursos(data)
		{
			addChecksByMap("cursosCheck", data);
		}
		
		function populaAreas()
		{
			var empresasIds = getArrayCheckeds(document.getElementById('formBusca'), 'empresasCheck');
			AreaOrganizacionalDWR.getByEmpresas(null, empresasIds, null, createListAreas);
		}
		
		function populaEstabelecimentos() {
			var empresasIds = getArrayCheckeds(document.getElementById('formBusca'), 'empresasCheck');
			EstabelecimentoDWR.getByEmpresas(null, empresasIds, createListEstabelecimentos);
		}
		
		function createListAreas(data)
		{
			addChecksByMap("areasCheck", data);
		}
		
		function createListEstabelecimentos(data) {
			addChecksByMap("estabelecimentosCheck", data);
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
			<@frt.checkListBox label="Empresas" id="empresasCheck" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formBusca')" liClass="liLeft" onClick="populaCursos();populaAreas();populaEstabelecimentos();" width="465" filtro="true"/>
			<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" liClass="liLeft" form="document.getElementById('formBusca')" width="465" filtro="true"/>
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" list="areasCheckList" form="document.getElementById('formBusca')" width="465" liClass="liLeft" filtro="true" selectAtivoInativo="true"/>
			<@frt.checkListBox label="Cursos" name="cursosCheck" id="cursosCheck" list="cursosCheckList" form="document.getElementById('formBusca')" liClass="liLeft" width="465" filtro="true"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<button onclick="return enviaForm(1);" class="btnPesquisar grayBGE"></button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	</div></div>
		<br>
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
								<dd>${indicadorTreinamento.percentualInvestimento?string(",##0.00")}%</dd>
								<dt>Percentual de frequência (aprovados) </dt>
								<dd>${indicadorTreinamento.percentualFrequencia?string(",##0.00")}%</dd>
							</dl>
							<dl>
								<dt>Total de horas de treinamento</dt>
								<dd>${indicadorTreinamento.somaHoras}</dd>
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
						<div class="gogDivTituloX">Qtd. Prevista de Participantes x Inscritos x Presentes</div>
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
							<div id="custoLegenda"></div>
							<div id="custo" class="graph"></div>
						</div>
						<br />
					</div>
				</div>
			</@ww.div>
	
</body>
</html>