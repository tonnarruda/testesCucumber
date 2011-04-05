<html>
	<head>
	<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url includeParams="none" value="/css/painelIndicadoresTreinamentos.css"/>');
		</style>
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		
		<title>Painel de Indicadores de Info. Funcionais</title>
	
		<style type="text/css">
			div.graph
			{
				width: 460px;
				height: 180px;
				float: left;
			}
			div.graph2
			{
				width: 860px;
				height: 380px;
				float: left;
			}
			.label
			{
				color: #5C5C5A !important;
				font-size:5px;
				text-align:center;
			}
			.legend
			{
				color: #5C5C5A !important;
				font-size:5px !important;
			}
		</style>
		<script type="text/javascript">
			$(function () {
			
				montaPie(${grfFormacaoEscolars}, "#formacaoEscolar", 1, 0.05, -120);
				montaPie(${grfFaixaEtarias}, "#faixaEtaria", 1, 0.05, -120);
				montaPie(${grfSexo}, "#sexo", 1, 0, -120);
				montaPie(${grfEstadoCivil}, "#estadoCivil", 1, 0.02, -120);
				montaPie(${grfDeficiencia}, "#deficiencia", 1, 0.03, -120);
				montaPie(${grfDesligamento}, "#desligamento", 0.9, 0.02, -190);
				
				
				//$("#interactive").bind("plotclick", pieClick);
			});
		
			function montaPie(data, class, radiusLabel, percentMin, pieLeft)
			{
			    $.plot($(class), data, 
				{
			        series: {
			            pie: {
							show: true,
			            	offset:{
			            		top: 0,
			            		left: pieLeft
			            	},
			            	 stroke:{
							    color: '#FFF',
							    width: 1
							},
			            	tilt: 0.8,
			            	innerRadius: 0.5,
			                radius: 0.8,
				                combine: {
				                    color: '#999',
				                    threshold: 0,
				                    label: 'Outros'
				                },
			                label: {
			                    show: true,
			                    radius: radiusLabel,
			                    threshold: percentMin,
			                    formatter: function(label, series){
			                        	return '<div class="label">'+series.percent.toFixed(2)+'%</div>';
			                    }
			                }
			            }
			        },
			        grid: {
						//hoverable: true
						//clickable: true
					},
					colors: ["#edc240", "#afd8f8", "#cb4b4b", "#4da74d", "#9440ed"],//"#D7ECFC","#DECD99","#226FA5","#8080FF"
					legend: {
						margin: 2,
						labelBoxBorderColor: '#FFF',
			    		labelFormatter: function(label, series) {
							return '<span class="legend">' + label + ' &#x2013; ' + series.percent.toFixed(2) + '% ('+ series.datapoints.points[1] + ')</span>';
						}
			  		}
				});
				
				//$(class).bind("plothover", pieHover);
			}
			
			function enviaForm1()
			{
				return validaFormulario('formBusca1', new Array('dataBase'), new Array('dataBase'));
			}
			function enviaForm2()
			{
				return validaFormularioEPeriodo('formBusca2', new Array('dataIni','dataFim'), new Array('dataIni','dataFim'));
			}
			
			
		</script>
	
		<#include "../ftl/mascarasImports.ftl" />
	
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
	
	</head>
	<body>
		<@ww.form name="formBusca" id="formBusca1" action="painelIndicadores.action" method="POST">
			<@ww.datepicker label="Data" name="dataBase" value="${dateBase}" id="dataBase"  cssClass="mascaraData" />
			
			<@ww.hidden name="dataIni"/>	
			<@ww.hidden name="dataFim"/>
			
			<button onclick="return enviaForm1();" class="btnPesquisar"></button>
		
			<div class="gogDivFormularioX">
			    <div id="formacaoEscolar" class="graph" ></div>
			    <div id="faixaEtaria" class="graph"></div>
		    </div>
	   		<div class="gogDivFormularioX">
		    	<div id="sexo" class="graph"></div>
		    	<div id="estadoCivil" class="graph"></div>
		    </div>
		    <div class="gogDivFormularioX">
		    	<div id="deficiencia" class="graph"></div>
		    </div>
		</@ww.form>
		
		<br>
		
		<@ww.form name="formBusca" id="formBusca2" action="painelIndicadores.action" method="POST">
			<@ww.datepicker name="dataIni" id="dataIni" value="${dateIni}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />
			
			<@ww.hidden name="dataBase"/>
			
			<button onclick="return enviaForm2();" class="btnPesquisar"></button>
		
		    <div class="gogDivFormularioX">
		    	<div id="desligamento" class="graph2"></div>
		    </div>
		</@ww.form>
	</body>
</html>