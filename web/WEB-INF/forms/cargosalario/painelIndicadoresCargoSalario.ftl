<html>
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		
		div.graph {
			width: 800px !important;
			height: 380px;
			float: left;
		}		
		.fieldGraph {
			display: inline;
			float: left;
			margin-top: 5px;
			margin-right: 5px;
			border: 1px solid #7E9DB9;
		}
		#salarioAreasLegenda{
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



	</style>
	

		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
		
		<#include "../ftl/showFilterImports.ftl" />
		
		<title>Painel de Indicadores de C&S</title>

		<script type="text/javascript">
			$(function () {
			
				montaPie(${grfSalarioAreas}, "#salarioAreas", 0.5, 0.2, 0.05, 0, 2);
				montaPie(${grfDesligamento}, "#desligamento", 0.8, 1, 0.03, -120, 1);
			});
			
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
		
		    <div id="salarioAreasLegenda"></div>
		<div class="fieldGraph">
			<h1>Salário por Área Organizacional</h1>
		    <div id="salarioAreas" class="graph"></div>
		</div>
		
		<div style="clear: both"></div>

			
	</body>
</html>