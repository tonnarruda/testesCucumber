<html>
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<head>
	<@ww.head/>
	
	<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
	
		<style type="text/css">
			@import url('<@ww.url includeParams="none" value="/css/painelIndicadoresTreinamentos.css"/>');
		</style>

		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		
		<#include "../ftl/showFilterImports.ftl" />
		
		<title>Painel de Indicadores de Info. Funcionais</title>
	
		<style type="text/css">
			div.graph
			{
				width: 466px !important;
				height: 180px;
				float: left;
			}
			div.graph2
			{
				width: 959px;
				height: 380px;
				float: left;
			}
			.label
			{
				color: #5C5C5A !important;
				text-align:center;
				font-size: 10px !important;
			}
			.legend
			{
				color: #5C5C5A !important;
			}
			
			.fieldGraph {
				display: inline;
				float: left;
				margin-top: 5px;
				margin-right: 5px;
				border: 1px solid #7E9DB9;
				width: 477px !important;
			}
			.fieldGraph h1 {
			    display: block;
			    margin: 0;
			    padding: 3px 4px;
			    width: 468px;
			    font-size: 13px;
			    font-weight: normal;
				background: #EBECF1;
			    border-bottom: 1px solid #7E9DB9;
			}
			.fieldGraph div.legend > table {
				left: 240px;
			}
			.fieldGraph.bigger{
				width: 963px !important;
			}
			.fieldGraph.bigger h1 {
				width: 955px;
			}
			.fieldGraph.bigger div.legend > table {
				left: inherit;
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
		<#include "../util/topFiltro.ftl" />
			<@ww.form name="formBusca1" id="formBusca1" action="painelIndicadores.action" method="POST">
				<@ww.datepicker label="Data" name="dataBase" value="${dateBase}" id="dataBase"  cssClass="mascaraData" />
				
				<@ww.hidden name="dataIni"/>	
				<@ww.hidden name="dataFim"/>
				<button onclick="return enviaForm1();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
		
		<div class="fieldGraph">
			<h1>Formação escolar</h1>
		    <div id="formacaoEscolar" class="graph" ></div>
		</div>
		<div class="fieldGraph">
			<h1>Formação escolar</h1>
		    <div id="faixaEtaria" class="graph"></div>
	    </div>
   		<div class="fieldGraph">
			<h1>Formação escolar</h1>
	    	<div id="sexo" class="graph"></div>
	    </div>
		<div class="fieldGraph">
			<h1>Formação escolar</h1>
	    	<div id="estadoCivil" class="graph"></div>
	    </div>
	    <div class="fieldGraph">
			<h1>Formação escolar</h1>
	    	<div id="deficiencia" class="graph"></div>
	    </div>
	    
		<div style="clear: both"></div>
		<br><br>
		
		<div class="divFiltro">
			<div class="divFiltroLink">
				<a href="javascript:exibeFiltro('${urlImgs}','divFiltroForm2');" id="linkFiltro"><img alt="Ocultar\Exibir Filtro" src="<@ww.url includeParams="none" value="${imagemFiltro}"/>"> <span id="labelLink" class="labelLink">${labelFiltro}</span></a>
			</div>
			<div id="divFiltroForm2" class="divFiltroForm ${classHidden}">
			<@ww.form name="formBusca2" id="formBusca2" action="painelIndicadores.action" method="POST">
				<@ww.datepicker name="dataIni" id="dataIni" value="${dateIni}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />
				
				<@ww.hidden name="dataBase"/>
				
				<button onclick="return enviaForm2();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
			<div class="fieldGraph bigger">
			<h1>Formação escolar</h1>
		   		<div id="desligamento" class="graph2"></div>
		    </div>
		    <div style="clear: both"></div>
	</body>
</html>