<html>
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
		



	</style>
	

		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
		
		<#include "../ftl/showFilterImports.ftl" />
		
		<title>Painel de Indicadores de C&S</title>

		<script type="text/javascript">
			$(function () {
			
			    var salarioAreasOrdered = ${grfSalarioAreas}.sort(function (a, b){
   					return (a.data > b.data) ? -1 : (a.data < b.data) ? 1 : 0;
				});
				 
				montaPie(salarioAreasOrdered, "#salarioAreas", {
					radiusLabel:0.9, 
					percentMin: 0.02, 
					pieLeft: 0, 
					noColumns: 2, 
					container: '#salarioAreasLegenda',
					legendLabelFormatter: function(label, series) {
						return '<span class="legend">' + label + ' &#x2013; '+ series.percent.toFixed(2) + '% ('+ formataNumero(series.datapoints.points[1]) + ')</span>';
					}
				});
			});
			
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
			<@ww.form name="formBusca1" id="formBusca1" action="painelIndicadoresCargoSalario.action" method="POST">
				<@ww.datepicker label="Data" name="dataBase" value="${dateBase}" id="dataBase"  cssClass="mascaraData" />
				
				<@ww.hidden name="dataIni"/>	
				<@ww.hidden name="dataFim"/>
				<button onclick="return enviaForm1();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
		
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
				<@ww.datepicker name="dataIni" id="dataIni" value="${dateIni}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim"/>
				<@ww.hidden name="dataBase"/>

				<button onclick="return enviaForm2();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
		<div class="fieldGraph bigger">
		<h1>Evolução da Folha</h1>
	   		<div id="desligamento" class="graph2"></div>
	    </div>
		<div style="clear: both"></div>


	    <div style="clear: both"></div>
		<a name="pagebottom"></a>
	</body>
</html>