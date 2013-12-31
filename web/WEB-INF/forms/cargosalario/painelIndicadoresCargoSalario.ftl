<html>
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		
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
			width: 300px;
			height: 250px;
		}
		#pieLegendBox{
			float: left;
			width: 350px;
			height: 250px !important;
		}
		
		.btnImprimir { float: right; margin: 10px; }
		.icoImprimir { float: right; }
	</style>
		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
		
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
			
				BrowserDetect.init( function ( informacaoesDesteBrowser ){
				    if(informacaoesDesteBrowser.name == 'Explorer')
				    	$('.btnImprimir').hide();
				});
			
				$("#box").dialog({autoOpen: false});
			
				salarioAreasOrdered = ${grfSalarioAreas}.sort(function (a, b){
					return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
				});
				
				graficoPizza(salarioAreasOrdered, '#salarioAreas', '#salarioAreasLegenda', '#salarioAreasImprimir', 2);
			
				var folha = ${grfEvolucaoFolha};
				var faturamento = ${grfEvolucaoFaturamento};
				
			    var plot = $.plot(	$("#evolucaoFolha"), 
			    					[
			    						{label: 'Evolução Salarial', data: folha}, 
			    						{label: 'Faturamento', data: faturamento }
			    					], 
			    					{
									    series: {
						                   lines: { show: true },
						                   points: { show: true }
						                },
						                grid: { hoverable: true },
								        xaxis: { 
								        	mode: "time",
								        	ticks: $.map( folha, function(item, i) { return item[0] }),
								        	timeformat: '%b/%y ',
								        	monthNames: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"]
								        },
								        yaxis: { 
								        	 tickFormatter: function (v) {return formataNumero(v);}
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
					        {label: 'Horizontal', data: promocaoHorizontal , bars:{align : "right", barWidth: 900000020}},
					        {label: 'Vertical', data: promocaoVertical, bars:{align : "left", barWidth: 900000000} }
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
					        	ticks: $.map( promocaoHorizontal, function(item, i) { return item[0] }),
					        	timeformat: '%b/%y ',
					        	monthNames: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"]
					        }
				    	});
			    
			    $("#faixaSalarial").bind("plothover", function (event, pos, item) {
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
			
			function graficoPizza(dados, divGrafico, divLegenda, btnImprimir, numColunas, titulo, divTitulo)
			{
				montaPie(dados, divGrafico, {
					radiusLabel:0.9, 
					percentMin: 0.02, 
					pieLeft: 0, 
					noColumns: numColunas, 
					container: divLegenda,
					hoverable: true,
        			clickable: true,
					legendLabelFormatter: function(label, series) {
						return '<span class="legend">' + label + ' &#x2013; '+ series.percent.toFixed(2) + '% ('+ formataNumero(series.datapoints.points[1]) + ')</span>';
					}
				});
				
				$(divGrafico).bind("plothover", plotPieHover)
							 .bind("plotclick", pieClick);
							 
				if (titulo && divTitulo)
					$(divTitulo).text(titulo);
				
				if (btnImprimir) 
					$(btnImprimir).unbind().bind('click', { dados: dados }, function(event) { imprimirPizza(event.data.dados); });
			}
			
			var popup;
			function imprimirPizza(dados) 
			{
				popup = window.open("<@ww.url includeParams="none" value="/grafico.jsp"/>");
				
				popup.window.onload = function() 
				{
					popup.focus();
					var infoPopup = $('.legendTotal').text();
					if($("#box").dialog("isOpen"))
						infoPopup = $('.ui-dialog-titlebar').text().replace('close','');
						
					popup.document.getElementById('info').innerHTML = infoPopup;
					
					popup.window.opener.graficoPizza(dados, popup.document.getElementById('popupGrafico'), popup.document.getElementById('popupGraficoLegenda'), false, 1, 'Salário por Área Organizacional', popup.document.getElementById('popupTitulo'));
					popup.window.print();
					popup.window.close();
				}

			}

			//CUIDADO com o tamanho do grafico(bug da sombra)http://code.google.com/p/flot/issues/detail?id=5#c110

			var urlFind = "<@ww.url includeParams="none" value="/cargosalario/historicoColaborador/grfSalarioAreasFilhas.action"/>";
			var dataBase_ = '${dateBase}';
			
			function pieClick(event, pos, obj)
			{
				if(event.currentTarget.id == "salarioAreas")
					var areaId_ = salarioAreasOrdered[obj.seriesIndex].id;
				else
					var areaId_ = salarioAreasOrderedBox[obj.seriesIndex].id;
				
				$.ajax({
					url: urlFind,
					dataType: "json",
					async: false,
					data: {areaId: areaId_, dataBase: dataBase_},
					success: function(data){
						if(data.length == 0)
						{
							jAlert("Área Organizacional não possui área filha.");
							return false;
						}
						
						$('#pieBox, #pieLegendBox').empty();
						
						salarioAreasOrderedBox = data.sort(function (a, b){
							return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
						});
						
						graficoPizza(salarioAreasOrderedBox, '#pieBox', '#pieLegendBox', '#pieImprimirBox', 1);
						
						var percent = parseFloat(obj.series.percent).toFixed(2);
						var descricaoArea = data[0].descricao;
						var titleSubArea = descricaoArea + ' &#x2013; '+ percent + '% (' + formataNumero(obj.series.datapoints.points[1]) + ')';
						
						$("#box").dialog( "option" , { zIndex: 9999, title: titleSubArea, width: 700, height: 350 });
						$("#box").dialog("open");
					}
				});
				
			}		
			
			function plotPieHover(event, pos, item) {
	            if (item) 
	            {
            		previousIndex = item.dataIndex;
                    $("#tooltip").remove();
                    showTooltip(pos.pageX, pos.pageY, "Clique para exibir áreas filhas");
	            }
				else 
                	$("#tooltip").remove();
			}
			
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
			<h1>
				Salário por Área Organizacional
				<img id="salarioAreasImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
			</h1>
		    <div id="salarioAreas" class="graph"></div>
		    <div id="salarioAreasLegenda"></div>
		</div>
		
		<div style="clear: both"></div>

		<div class="fieldGraph bigger">
			<h1>Evolução Salarial - Faturamento</h1>
	   		<div id="evolucaoFolha" style="margin: 25px; height: 300px; width: 900px"></div>
	    </div>

		<div class="fieldGraph bigger">
			<h1>Promoção</h1>
	   		<div id="faixaSalarial" style="margin: 25px; height: 300px; width: 900px"></div>
	   		<div style="margin: 5px;text-align: right;">
		   		 * Promoção Horizontal: Mudança de Faixa Salarial ou Salário. / Promoção Vertical: Mudança de Cargo.
	   		</div>
	    </div>

		<div style="clear: both"></div>

	    <div style="clear: both"></div>
		<a name="pagebottom"></a>
		
		<div id="box">
			<div id="pieBox"></div>
			<div id="pieLegendBox"></div>

			<button class="btnImprimir" id="pieImprimirBox"></button>
			
			<div style="clear: both"></div>
		</div>
		<div id="aviso"></div>
		
	</body>
</html>