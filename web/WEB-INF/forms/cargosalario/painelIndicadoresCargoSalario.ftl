<html>
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
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
		
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
			
			    salarioAreasOrdered = ${grfSalarioAreas}.sort(function (a, b){
   					return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
				});
				 
				montaPie(salarioAreasOrdered, "#salarioAreas", {
					radiusLabel:0.9, 
					percentMin: 0.02, 
					pieLeft: 0, 
					noColumns: 2, 
					container: '#salarioAreasLegenda',
					hoverable: true,
        			clickable: true,
					legendLabelFormatter: function(label, series) {
						return '<span class="legend">' + label + ' &#x2013; '+ series.percent.toFixed(2) + '% ('+ formataNumero(series.datapoints.points[1]) + ')</span>';
					}
				});
				
				$("#salarioAreas").bind("plothover", plotPieHover);
				$("#salarioAreas").bind("plotclick", pieClick);
				
				var folha = ${grfEvolucaoFolha};
			    var options = {
				    series: {
	                   lines: { show: true },
	                   points: { show: true }
	                },
	                grid: { hoverable: true },
			        xaxis: { 
			        	mode: "time",
			        	ticks: folha.map(function (item){return item[0]}),
			        	timeformat: '%b/%y ',
			        	monthNames: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"]
			        },
			        yaxis: { 
			        	 tickFormatter: function (v) {return formataNumero(v);}
			        }
			    };			    
			    var plot = $.plot($("#evolucaoFolha"), [folha], options);

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
		        
		        var temp = ${grfPromocaoHorizontal};
			    var data = [
			        {label: 'Horizontal', data: ${grfPromocaoHorizontal}},
			        {label: 'Vertical', data: ${grfPromocaoVertical}}
			    ];
			    var options = {
			        series: {stack: 0,
			                 lines: {show: false,
			                 		 steps: false,
			                 		 fill: true 
			                 },
			                 bars: {show: true, 
			                 		barWidth: 900000000,
			                 		align: 'center',
			                 		fill: true,
      								fillColor: { colors: [ { opacity: 0.8 }, { opacity: 0.1 } ] }
			                 },
			        },
			        xaxis: { 
			        	autoscaleMargin:.05,
			        	minTickSize:1,
			        	tickSize:1,
			        	mode: "time",
			        	ticks: temp.map(function (item){return item[0]}),
			        	timeformat: '%b/%y ',
			        	monthNames: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"]
			        },
			    };
			    
			    
			    
			    
			
			    $.plot($("#faixaSalarial"), data, options);
			});

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
						
						$('#pieBox').remove();
						$('#box').prepend("<div id='pieBox'></div>");
						
						salarioAreasOrderedBox = data.sort(function (a, b){
			   					return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
						});
						
						montaPie(salarioAreasOrderedBox, "#pieBox", {
							radiusLabel:0.9,
							radius: 0.6,  
							percentMin: 0.02, 
							noColumns: 1, 
							hoverable: true,
		        			clickable: true,
		        			container: '#pieLegendBox',
							legendLabelFormatter: function(label, series) {
								return '<span class="legend">' + label + ' &#x2013; '+ series.percent.toFixed(2) + '% ('+ formataNumero(series.datapoints.points[1]) + ')</span>';
							}
						});

						var percent = parseFloat(obj.series.percent).toFixed(2);
						var descricaoArea = data[0].descricao;
						
						$("#box").dialog( "option" , { zIndex: 9999, title: descricaoArea + ' &#x2013; '+ percent + '% (' + formataNumero(obj.series.datapoints.points[1]) + ')', minWidth: 450, minHeight: 300 });
						$("#box").dialog("open");
						$("#pieBox").bind("plothover", plotPieHover);
						$("#pieBox").bind("plotclick", pieClick);
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

			
			function enviaForm1()
			{
				return validaFormulario('formBusca1', new Array('dataBase'), new Array('dataBase'));
			}
			function enviaForm2()
			{
				return validaFormularioEPeriodo('formBusca2', new Array('mesAnoIni','mesAnoFim'), new Array('mesAnoIni','mesAnoFim'));
			}
			
			function formataNumero(value)
			{
				return 'R$' + $('<span>' + value + '</span>').format({}).text().replace(/,/g,'#').replace(/\./g,',').replace(/#/g,'.');
			}

		</script>
	
		<#include "../ftl/mascarasImports.ftl" />
	
	</head>
	<body>
		<#include "../util/topFiltro.ftl" />
			<@ww.form name="formBusca1" id="formBusca1" action="painelIndicadoresCargoSalario.action" method="POST">
			
				<@ww.datepicker label="Data" name="dataBase" value="${dateBase}" id="dataBase"  cssClass="mascaraData" />
				
				<@ww.hidden name="dataMesAnoIni"/>	
				<@ww.hidden name="dataMesAnoFim"/>
				<button onclick="return enviaForm1();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
		<div class="legendTotal">Valor total da folha em ${dateBase}: ${valorTotalFolha}</div>
		<div class="fieldGraph">
			<h1>Salário por Área Organizacional</h1>
		    <div id="salarioAreas" class="graph"></div>
		    <div id="salarioAreasLegenda"></div>
		</div>
		
		<div style="clear: both"></div>

				<br>
		
		<div class="divFiltro">
			<div class="divFiltroLink">
				<a href="javascript:exibeFiltro('${urlImgs}','divFiltroForm2');" id="linkFiltro"><img alt="Ocultar\Exibir Filtro" src="<@ww.url includeParams="none" value="${imagemFiltro}"/>"> <span id="labelLink" class="labelLink">${labelFiltro}</span></a>
			</div>
			<div id="divFiltroForm2" class="divFiltroForm ${classHidden}">
			<@ww.form name="formBusca2" id="formBusca2" action="painelIndicadoresCargoSalario.action#pagebottom" method="POST">
				<@ww.textfield label="Mês/Ano" name="dataMesAnoIni" id="mesAnoIni" cssClass="mascaraMesAnoData" liClass="liLeft"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.textfield label="Mês/Ano" name="dataMesAnoFim" id="mesAnoFim" cssClass="mascaraMesAnoData"/>
				
				<@ww.hidden name="dataBase"/>

				<button onclick="return enviaForm2();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
		<div class="fieldGraph bigger">
			<h1>Evolução Salarial</h1>
	   		<div id="evolucaoFolha" style="margin: 25px;height:300px;"></div>
	    </div>

		<div class="fieldGraph bigger">
			<h1>Faixa Salarial</h1>
	   		<div id="faixaSalarial" style="margin: 25px;height:300px;"></div>
	    </div>

		<div style="clear: both"></div>

	    <div style="clear: both"></div>
		<a name="pagebottom"></a>
		
		
		<div id="box">
			<!--<a href="#" style="float: right;" tabIndex="-1" onclick="voltar()">&lt;&lt; Voltar</a>-->
			<div id="pieLegendBox"/>
			<div style="clear: both"></div>
		</div>
		<div id="aviso"></div>
		
	</body>
</html>