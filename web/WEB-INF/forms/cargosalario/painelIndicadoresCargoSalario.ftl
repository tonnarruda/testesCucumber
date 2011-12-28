<html>
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
		
		div.graph {
			width: 366px !important;
			height: 416px;
			float: left;
		}		
		.fieldGraph {
			float: left;
			margin-top: 5px;
			margin-right: 5px;
			border: 1px solid #7E9DB9;
		}
		.fieldGraph h1 {
			display: block;
			margin: 0;
			padding: 3px 4px;
			width: 958px;
			font-size: 13px;
			font-weight: normal;
			background: #EBECF1;
			border-bottom: 1px solid #7E9DB9;
		}
		#salarioAreasLegenda{
			float: left;
			width: 600px !important;
		}
		.label {
			color: #5C5C5A !important;
			text-align: center;
			font-size: 10px !important;
		}
		.legend {
			color: #5C5C5A !important;
		}
		
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
			width: 200px;
			height: 300px;
		}
		#pieLegendBox{
			float: left;
			width: 200px;
			height: 250px !important;
		}
		


	</style>
	

		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
		<#include "../ftl/showFilterImports.ftl" />
		
		<title>Painel de Indicadores de C&S</title>

		<#if dataBase?exists>
		  <#assign dateBase = dataBase?date/>
		<#else>
		  <#assign dateBase = ""/>
		</#if>
		<#if dataIni?exists>
		  <#assign dateIni = dataIni?date/>
		<#else>
		  <#assign dateIni = ""/>
		</#if>
		<#if dataFim?exists>
		  <#assign dateFim = dataFim?date/>
		<#else>
		  <#assign dateFim = ""/>
		</#if>

		<script type="text/javascript">
			$(function () {
				$("#box").dialog({autoOpen: false});
								
				var folha = ${grfEvolucaoFolha};
				var faturamento = ${grfEvolucaoFaturamento};
				
			    $.plot($("#evolucaoFolha"), 
							[
								{ label: 'Evolução Salarial', data: folha }, 
								{ label: 'Faturamento', data: faturamento }
							],
							{
								series: {
									lines: { show: true },
									points: { show: true }
								},
								grid: { hoverable: true },
								xaxis: { 
									mode: 'time',
									ticks: folha.map(function(item) { return item[0]; }),
									timeformat: '%b/%y ',
									monthNames: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez']
								},
								yaxis: { 
									tickFormatter: function(v) { return formataNumero(v); }
								}
						    });
				
				var previousPoint = null;				
				$("#evolucaoFolha").bind("plothover", function (event, pos, item) {
				    if (item) 
				    {
				    	if (previousPoint != item.dataIndex) 
				    	{
				    		previousPoint = item.dataIndex;
				            $("#tooltip").remove();
				            var y = item.datapoint[1].toFixed(2);		                    
				            showTooltip(item.pageX, item.pageY, formataNumero(y));
				        }
				    }
					else 
					{
				    	$("#tooltip").remove();
				    	previousPoint = null;            
				    }
				});
				
				var promocaoHorizontal = ${grfPromocaoHorizontal};
				var promocaoVertical = ${grfPromocaoVertical};
				
				$.plot($("#faixaSalarial"), 
						[
					        { label: 'Horizontal', data: promocaoHorizontal , bars:{align : "right", barWidth: 900000020} },
					        { label: 'Vertical', data: promocaoVertical, bars:{align : "left", barWidth: 900000000} }
					    ], 
						{
				    		series: {
				            	bars: {
				                	show: true, 
				                 	align: 'center'
								}
					        },
					        grid: { hoverable: true },
					        xaxis: { 
					         	mode: "time",
					        	ticks: promocaoHorizontal.map(function(item){return item[0]}),
					        	timeformat: '%b/%y ',
					        	monthNames: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"]
					        }
					    });
				
				
				$("#faixaSalarial").bind("plothover", function(event, pos, item) {
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

			
			function enviaForm()
			{
				return validaFormulario('formBusca', new Array('dataBase','mesAnoIni','mesAnoFim'), new Array('dataBase','mesAnoIni','mesAnoFim'));
			}
			
			function formataNumero(value)
			{
				return 'R$' + $('<span>' + value + '</span>').format({}).text().replace(/,/g,'#').replace(/\./g,',').replace(/#/g,'.');
			}

			function populaAreas(empresaId)
			{
				DWRUtil.useLoadingMessage('Carregando...');
				AreaOrganizacionalDWR.getByEmpresa(createListAreas, empresaId);
				$('.empresa').val(empresaId);
			}
	
			function createListAreas(data)
			{
				addChecks('areasCheck', data);
			}
		</script>
	
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
		<script type="text/javascript">
			google.load("visualization", "1", {packages:["corechart"]});
			google.setOnLoadCallback(drawChart);
		
			var chart;
			var data;
			var popupChart;
			var popupData;
			var formatter;
			var salarioAreasOrdered = ${grfSalarioAreas};
		  
			function drawChart() {
				formatter = new google.visualization.NumberFormat({
				    fractionDigits: 2,
				    prefix: 'R$ '
				});
			
				data = new google.visualization.DataTable();
				data.addColumn('string', 'areaNome');
				data.addColumn('number', 'areaTotalSalarios');
				data.addColumn('number', 'areaId');
				data.addRows(salarioAreasOrdered);
				
				formatter.format(data, 1);
		
				var options = {
					width: 850, height: 400,
					is3D: true
				};
		
				chart = new google.visualization.PieChart(document.getElementById('salarioAreas'));
				chart.draw(data, options);
		
				google.visualization.events.addListener(chart, 'select', selectHandler);
			}
		  
			function selectHandler(isPopup) {
				var selection = chart.getSelection();
				var dados = data;
				if (isPopup) {
					selection = popupChart.getSelection();
					dados = popupData;
				}
				
				if (selection.length > 0) {
					var item = selection[0];
					var areaNome = dados.getValue(item.row, 0);
					var valor = formataNumero(dados.getValue(item.row, 1));
					var areaId = dados.getValue(item.row, 2);
					
					$.ajax({
						url: "<@ww.url includeParams="none" value="/cargosalario/historicoColaborador/grfSalarioAreasFilhas.action"/>",
						dataType: "json",
						async: false,
						data: {areaId: areaId, dataBase: '${dateBase}'},
						success: function(retorno){
							if(retorno.length == 0)
							{
								jAlert("Área Organizacional não possui área filha.");
								return false;
							}
							
							$('#pieBox').empty();
							
							popupData = new google.visualization.DataTable();
						    popupData.addColumn('string', 'areaNome');
							popupData.addColumn('number', 'areaTotalSalarios');
							popupData.addColumn('number', 'areaId');
						    popupData.addRows(retorno);
						    
						    formatter.format(popupData, 1);
						
						    var options = {
						      width: 700, height: 300,
						      is3D: true
						    };
						
						    popupChart = new google.visualization.PieChart(document.getElementById('pieBox'));
						    popupChart.draw(popupData, options);
						    
						    google.visualization.events.addListener(popupChart, 'select', selectHandlerPopup);

							$("#box").dialog( "option" , { zIndex: 9999, title: areaNome + ' (' + valor + ')', minWidth: 750, minHeight: 330 });
							$("#box").dialog("open");
						}
					});
				}
				
				function selectHandlerPopup() {
					selectHandler(true);
				}
			}
		  
			function imprimirGrafico(btn) {
				var iframe = $(btn).parent().find("iframe").eq(0).attr('name');
				eval('window.' + iframe + '.focus()');
				eval('window.' + iframe + '.print()');
			}
		</script>
		
		<#include "../ftl/mascarasImports.ftl" />
	
	</head>
	<body>
		<#include "../util/topFiltro.ftl" />
			<@ww.form name="formBusca" id="formBusca" action="painelIndicadoresCargoSalario.action" method="POST">
				<@ww.select label="Empresa" name="empresa.id" id="empresa" cssClass="empresa" listKey="id" listValue="nome" list="empresas" onchange="populaAreas(this.value);" />
			
				<li>&nbsp;</li>
				<li><strong>Indicador de Salário por Área Organizacional</strong></li>
				<@ww.datepicker label="Data" name="dataBase" value="${dateBase}" id="dataBase"  cssClass="mascaraData" />
				
				<li>&nbsp;</li>
				<li><strong>Indicadores de Evolução Salarial e Promoção</strong></li>
				<@ww.textfield label="Mês/Ano" name="dataMesAnoIni" id="mesAnoIni" cssClass="mascaraMesAnoData" liClass="liLeft"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.textfield label="Mês/Ano" name="dataMesAnoFim" id="mesAnoFim" cssClass="mascaraMesAnoData"/>
				<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList"/>

				<button onclick="return enviaForm();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
		
		<div class="legendTotal">Valor total da folha em ${dateBase}: ${valorTotalFolha}</div>
		<div class="fieldGraph">
			<h1>Salário por Área Organizacional</h1>
		    <div id="salarioAreas" class="graph"></div>
			<br clear="all"/>
			<button class="btnImprimir" onclick="imprimirGrafico(this);"></button>
		</div>
		
		<div style="clear: both"></div>

		<div class="fieldGraph bigger">
			<h1>Evolução Salarial - Faturamento</h1>
	   		<div id="evolucaoFolha" style="margin: 25px;height:300px;"></div>
	    </div>

		<div class="fieldGraph bigger">
			<h1>Promoção</h1>
	   		<div id="faixaSalarial" style="margin: 25px;height:300px;"></div>
	   		<div style="margin: 5px;text-align: right;">
		   		 * Promoção Horizontal: Mudança de Faixa Salarial ou Salário. / Promoção Vertical: Mudança de Cargo.
	   		</div>
	    </div>

		<div style="clear: both"></div>

	    <div style="clear: both"></div>
		<a name="pagebottom"></a>
		
		<div id="box">
			<div id='pieBox'></div>
			<br clear="all"/>
			<button class="btnImprimir" onclick="imprimirGrafico(this);"></button>
			<div style="clear: both"></div>
		</div>
		<div id="aviso"></div>
		
	</body>
</html>