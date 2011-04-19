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
			width: 400px;
			height: 250px;
		}
		#pieBox{
			float: left;
			width: 150px;
			height: 250px;
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
			        	timeformat: '%m/%y'
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

				
			});
			

			
			

			function pieClick(event, pos, obj)
			{
				var urlFind = "<@ww.url includeParams="none" value="/cargosalario/historicoColaborador/grfSalarioAreasFilhas.action"/>";
				
				if(event.currentTarget.id == "salarioAreas")
					var areaId_ = salarioAreasOrdered[obj.seriesIndex].id;
				else
					var areaId_ = salarioAreasOrderedBox[obj.seriesIndex].id;

				dataBase_ = '19/04/2011';

				$.ajax({
					  url: urlFind,
					  dataType: "json",
					  async: false,
					  data: {areaId: areaId_, dataBase: dataBase_},
					  success: function(data){
							salarioAreasOrderedBox = data.sort(function (a, b){
				   					return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
							});

					  		if (!obj)
								return;
					
							var percent = parseFloat(obj.series.percent).toFixed(2);
							
							montaPie(salarioAreasOrderedBox, "#pieBox", {
								radiusLabel:0.8,
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
							
							$("#box").dialog( "option" , { zIndex: 9999, title: obj.series.label + ' &#x2013; '+ percent + '% (' + formataNumero(obj.series.datapoints.points[1]) + ')', minWidth: 400, minHeight: 250 });

							if(!$("#box").dialog("isOpen"))
							{
								$("#box").dialog("open");
								$("#pieBox").bind("plotclick", pieClick);
							}
					  }
					});
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
		            opacity: 0.80
		        }).appendTo("body").fadeIn(200);
		    }

			
			function enviaForm1()
			{
				return validaFormulario('formBusca1', new Array('dataBase'), new Array('dataBase'));
			}
			function enviaForm2()
			{
				return validaFormularioEPeriodo('formBusca2', new Array('dataIni','dataFim'), new Array('dataIni','dataFim'));
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
				<@ww.textfield label="Mês/Ano" name="dataMesAnoIni" id="mesAnoIni" cssClass="mascaraDataMesAno" liClass="liLeft"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.textfield label="Mês/Ano" name="dataMesAnoFim" id="mesAnoFim" cssClass="mascaraDataMesAno"/>
				
				<@ww.hidden name="dataBase"/>

				<button onclick="return enviaForm2();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
		<div class="fieldGraph bigger">
		<h1>Evolução da Folha</h1>
	   		<div id="evolucaoFolha" style="margin: 25px;height:300px;"></div>
	    </div>

		<div style="clear: both"></div>

	    <div style="clear: both"></div>
		<a name="pagebottom"></a>
		
		
		<div id="box">
			<div id="pieBox"></div>
			<div id="pieLegendBox"/>
			<div style="clear: both"></div>
		</div>
		
		
	</body>
</html>