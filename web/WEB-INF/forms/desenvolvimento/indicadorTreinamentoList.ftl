<html>
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/indicadores.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/painelIndicadoresTreinamentos.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		
		.grid-cell div.legend > table {
		    border-spacing: 0 !important;
		    left: 240px;
            width: 240px;
		}
		
		.icoImprimir { float: right; cursor: pointer; }
		
		.legendColorBox { width: 20px; border: none; }
		.legendColorBox > div { border: 1px solid #fff !important; }
		
		#custo, #desempenho, #treinamento { width: 220px !important; }
		#custoLegenda, #desempenhoLegenda, #treinamentoLegenda { float: right; width: 220px; height: 195px; overflow-y: auto; }
		
		#custoPorCursoLegenda { float: right; width: 500px; height: 400px; overflow-y: auto; }
		#custoPorCurso { width: 400px !important;   height: 400px !important;}
		
		#formBusca { padding: 5px; }
		
		#box{
			width: 450px;
			height: 300px;
		}
		#box a{
			color: #85B5D9 !important;
			text-decoration: none;
		}
		#pieBox{
			float: left;
			width: 300px;
			height: 250px;
		}
		#pieLegendBox{
			float: left;
			width: 350px;
			height: 250px !important;
		}
		fieldset { background: inherit; width: 904px;}
		fieldset select { background: #FFFFFF; }
	</style>

	<title>Painel de Indicadores de T&D</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#include "../ftl/showFilterImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>'></script>
	
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
			$("#box").dialog({autoOpen: false});
		
	        montaGraficoFrequencia();
	    			    
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
			
			cursosOrdered = ${grfCustoPorCurso}.sort(function (a, b){
					return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
				});
			
			montaGrafico("#treinamento", ${grfTreinamento}, 'Cumprimento do Plano de Treinamento', {combinePercentMin: -1, percentMin: 0, container:'#treinamentoLegenda'});
			montaGrafico("#desempenho", ${grfDesempenho}, 'Aproveitamento dos Treinamentos', {combinePercentMin: -1, percentMin: 0, container:'#desempenhoLegenda'});
			montaGrafico("#custo", ${grfCusto}, 'Custo por Tipo de Despesa', { combinePercentMin: -1, percentMin: 0.02, legendLabelFormatter: formataLegendaCusto, container:'#custoLegenda' });
			montaGrafico("#custoPorCurso", cursosOrdered, 'Custo por Curso', {radius: 0.9, radiusLabel: 1, combinePercentMin: -1, percentMin: 0.02, legendLabelFormatter: formataLegendaCusto, clickable: true, hoverable: true, container:'#custoPorCursoLegenda' });
			
			$("#custoPorCurso").bind("plothover", plotPieHover).bind("plotclick", pieClick);
			
	    	$('#tooltipHelpIndicadoresTeD').qtip({
				content:'<strong>O resultado dos indicadores de T&D são obtidos através dos seguintes cálculos:</strong>'+
						'<strong>- Investimento médio da hora de treinamento (R$)</strong><br/>'+
						'&nbsp&nbsp custoMedioHora = SomaCusto / SomaHoras<br/>'+
						'&nbsp&nbsp SomaCusto: É a soma do custo de cada turma referente ao filtro.<br/>'+
						'&nbsp&nbsp SomaHoras: É o somatório de horas do curso vezes a quantidade de colaboradores.<br/>'+
						'<br/>'+
						'<strong>- Investimento per capita (R$)</strong><br/>'+
						'&nbsp&nbsp custoPerCapita (por pessoa) = CustoTotal / Qtd. Colaboradores Ativos e Inscritos<br/>'+
						'&nbsp&nbsp Qtd. Colaboradores Ativos e inscritos: colaboradores ativos na empresa com data de admissão menor que a data fim do filtro e<br/>'+
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
						'&nbsp&nbsp * apenas das turmas realizadas<br/><br/>'
				,
				style: {
		        	 width: '100px'
		        }
			});
			
			$('#tooltipHelpIndicadoresHorasDeTreinamento').qtip({
				content:'<strong>O resultado dos indicadores de T&D são obtidos através dos seguintes cálculos:</strong>'+
						'<strong>- Horas de treinamento per capita</strong><br/>'+
						'&nbsp&nbsp horasPerCapita = (QtdHoras * QtdParticipantes) / QtdAtivos<br/><br/>'+
						'&nbsp&nbsp QtdHoras: somatório das horas de cada dia da turma de acordo com o período.<br/>'+
						'&nbsp&nbsp QtdParticipantes: colaboradores da(s) turma(s) que estejam dentro do período do filtro.<br/>'+
						'&nbsp&nbsp QtdAtivos: colaboradores ativos na(s) empresa(s) selecionadas(*) com data de admissao menor que a data fim do filtro e<br/>'+
						'&nbsp&nbsp que não esteja desligado ou com data de desligamento posterior a data fim do filtro.<br/>'+
						'&nbsp&nbsp * Caso não seja selecionada nenhuma empresa o cálculo irá considerá todas as empresas listadas no filtro.'+
						'<br/>'+
						'<br/><br/><strong>- Total de horas de treinamento (h:min)</strong><br/>'+
						'&nbsp&nbsp totalHorasTreinamento = &sum; ( CargaHorariaCurso * QtdTurmasRealizadas )<br/>'+
						'&nbsp&nbsp * Este cálculo realiza o somatório da multiplicação entre CargaHorariaCurso e QtdTurmasRealizadas para todos os cursos filtrados.<br/><br/>'+
						'&nbsp&nbsp CargaHorariaCurso: É a carga horária prevista para o curso.<br/>'+
						'&nbsp&nbsp QtdTurmasRealizadas: É o número de turmas realizadas para cada curso.<br/>'+
						'<br/>'
				,
				style: {
		        	 width: '100px'
		        }
			});
		});
		
		function montaGrafico(obj, dados, titulo, config)
		{
			montaPie(dados, obj, config);
			
			$(obj + "Imprimir")
					.unbind()
					.bind('click', 
						function(event) 
						{ 
							popup = window.open("<@ww.url includeParams="none" value="/grafico.jsp"/>");
							popup.window.onload = function() 
							{
								popup.focus();
								popup.document.getElementById('popupTitulo').innerHTML = titulo;
								
								popup.window.opener.montaPie(dados, popup.document.getElementById('popupGrafico'),  { container: popup.document.getElementById('popupGraficoLegenda'), combinePercentMin: -1, percentMin: 0.03} );
								popup.window.print();
								popup.window.close();
							}
						}
					);
		}
		
		function mountPlotFrequencia(e) {
				var participantes = ${grfFrequenciaParticipantes};
	        	var inscritos = ${grfFrequenciaInscritos};
	        	var presentes = ${grfFrequenciaPresentes};
	        
			   $.plot($(e), 
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
		};
		
		function montaGraficoFrequencia() {
			mountPlotFrequencia("#frequencia");
 	
		   	$("#frequenciaImprimir")
				.unbind()
				.bind('click', 
					function(event) 
					{ 
						popup = window.open("<@ww.url includeParams="none" value="/grafico.jsp"/>");
						popup.window.onload = function() 
						{
							popup.focus();
							popup.document.getElementById('popupTitulo').innerHTML = 'Qtd. Prevista de Participantes x Inscritos x Presentes';
							
							popup.window.opener.mountPlotFrequencia(popup.document.getElementById('popupGrafico'));
							popup.window.print();
							popup.window.close();
						}
					}
				);
		}
		
		function plotPieHover(event, pos, item) {
            if (item) 
            {
        		previousIndex = item.dataIndex;
                $("#tooltip").remove();
                showTooltip(pos.pageX, pos.pageY, "Clique para exibir as despesas.");
            }
			else {
            	$("#tooltip").remove();
            }
		}
		
		var urlFind = "<@ww.url includeParams="none" value="/desenvolvimento/indicadores/grfTipoDespesaPorCurso.action"/>";
		function pieClick(event, pos, obj)
		{
			var cursoId_ = cursosOrdered[obj.seriesIndex].id;
			var dataIni_ = $("#dataIni").val();
			var dataFim_ = $("#dataFim").val();
			var indicadorTreinamento = {cursoId: cursoId_, dataIni: dataIni_, dataFim: dataFim_};
			
			$.ajax({
				url: urlFind,
				contentType: 'application/json; charset=utf-8',
			    dataType: 'text json',
				async: false,
				data: {cursoId: cursoId_, dataIni: dataIni_, dataFim: dataFim_},
				success: function(data){
					if(data.length == 0)
					{
						jAlert("O curso não possui despesas detalhadas.");
						return false;
					}
					
					$('#pieBox, #pieLegendBox').empty();
					
					var tipoDespesaPorCurso = JSON.parse(data).sort(function (a, b){
						return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
					});
					
					montaPie(tipoDespesaPorCurso, "#pieBox", { combinePercentMin: -1, percentMin: 0.02, legendLabelFormatter: formataLegendaCusto, container:'#pieLegendBox' });
					 
					var percent = parseFloat(obj.series.percent).toFixed(2);
					var descricaoArea = tipoDespesaPorCurso[0].descricao;
					var titleSubArea = descricaoArea + ' &#x2013; '+ percent + '% (R$' + formataNumero(obj.series.datapoints.points[1]) + ')';
					
					$("#boxImprimir")
					.unbind()
					.bind('click', 
						function(event) 
						{ 
							popup = window.open("<@ww.url includeParams="none" value="/grafico.jsp"/>");
							popup.window.onload = function() 
							{
								popup.focus();
								popup.document.getElementById('popupTitulo').innerHTML = titleSubArea;
								
								popup.window.opener.mountPlotFrequencia(popup.document.getElementById('popupGrafico'));
								popup.window.opener.montaPie(tipoDespesaPorCurso, popup.document.getElementById('popupGrafico'), { combinePercentMin: -1, percentMin: 0.02, legendLabelFormatter: formataLegendaCusto, container: popup.document.getElementById('popupGraficoLegenda') });
								popup.window.print();
								popup.window.close();
							}
						}
					);
					$("#box").dialog("option", { zIndex: 9999, title: titleSubArea, width: 700, height: 350 });
					$("#box").dialog("open");
				},
				error: function(data) {
					console.log(data);
				}
			});
		}		
		
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
			<@ww.checkbox label="Para os Indicadores de Horas de Treinamentos, considerar os dias de realização dos cursos que estão compreendidos no período especificado acima" id="considerarDiaTurmaCompreendidoNoPeriodo" name="considerarDiaTurmaCompreendidoNoPeriodo" labelPosition="left"/>
			<@frt.checkListBox label="Empresas" id="empresasCheck" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formBusca')" liClass="liLeft" onClick="populaCursos();populaAreas();populaEstabelecimentos();" width="465" filtro="true"/>
			<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" liClass="liLeft" form="document.getElementById('formBusca')" width="465" filtro="true"/>
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" list="areasCheckList" form="document.getElementById('formBusca')" width="465" liClass="liLeft" filtro="true" selectAtivoInativo="true"/>
			<@frt.checkListBox label="Cursos" name="cursosCheck" id="cursosCheck" list="cursosCheckList" form="document.getElementById('formBusca')" liClass="liLeft" width="465" filtro="true"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<button onclick="return enviaForm(1);" class="btnPesquisar grayBGE"></button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	
	<br>
	<@ww.div>
		<table class="grid" cellspacing="5">
			<tbody>
				<tr>
					<td id="gogDiv" class="grid-cell" colspan="2">
						<div class="cell-title">
								Indicadores de Horas de Treinamentos
								<img id="tooltipHelpIndicadoresHorasDeTreinamento" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" /><br>
						</div>
						<dl style="float:right;">
							<dt>Total de horas de treinamento (h:min)</dt>
							<dd>${indicadorTreinamento.totalHorasTreinamento}</dd>
						</dl>
						<dl>
							<dt>Horas de treinamento per capita</dt>
							<dd>${indicadorTreinamento.horasPerCapitaFmt}</dd>
						</dl>
					</td>
				</tr>
				<tr>
					<td id="gogDiv" class="grid-cell" colspan="2">
						<div class="cell-title">
								Indicadores de T&D
								<img id="tooltipHelpIndicadoresTeD" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" /><br>
						</div>
						<dl style="float:right;">
							<dt>Investimento médio da hora de treinamento (R$)</dt>
							<dd>${indicadorTreinamento.custoMedioHoraFmt}</dd>
							<dt>Investimento per capita (R$)</dt>
							<dd>${indicadorTreinamento.custoPerCapitaFmt}</dd>
						</dl>
						<dl>
							<dt>Total de investimentos dos treinamentos realizados (R$)</dt>
							<dd>${indicadorTreinamento.custoTotalFmt}</dd>
							<dt>Percentual de investimentos em relação ao faturamento</dt>
							<dd>${indicadorTreinamento.percentualInvestimento?string(",##0.00")}%</dd>
							<dt>Percentual de frequência (aprovados) </dt>
							<dd>${indicadorTreinamento.percentualFrequencia?string(",##0.00")}%</dd>
						</dl>
					</td>
				</tr>
				<tr>
					<td class="grid-cell">
						<div class="cell-title">
							Qtd. Prevista de Participantes x Inscritos x Presentes
							<img id="frequenciaImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
						</div>
						<div id="frequenciaLegenda"></div>
						<div id="frequencia" class="graph" ></div>
					</td>
					<td class="grid-cell">
						<div class="cell-title">
							Cumprimento do Plano de Treinamento
							<img id="treinamentoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
						</div>
						<div id="treinamentoLegenda"></div>
						<div id="treinamento" class="graph"></div>
					</td>
				</tr>
				<tr>
					<td class="grid-cell">
						<div class="cell-title">
							Aproveitamento dos Treinamentos
							<img id="desempenhoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
						</div>
						<div id="desempenhoLegenda"></div>
						<div id="desempenho" class="graph" ></div>
					</td>
					<td class="grid-cell">
						<div class="cell-title">
							Custo por Tipo de Despesa
							<img id="custoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
						</div>
						<div id="custoLegenda"></div>
						<div id="custo" class="graph"></div>
					</td>
				</tr>	
				<tr>
					<td class="grid-cell" colspan="2">
						<div class="cell-title">
							Custo por Curso
							<img id="custoPorCursoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
						</div>
						<div id="custoPorCursoLegenda"></div>
						<div id="custoPorCurso" class="graph"></div>
					</td>
				</tr>	
			</tbody>
		</table>
	</@ww.div>
	</div></div>
	<div id="box">
		<div id="pieBox"></div>
		<div id="pieLegendBox"></div>

		<div style="clear: both"></div>
		<img id="boxImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
	</div>
</body>
</html>