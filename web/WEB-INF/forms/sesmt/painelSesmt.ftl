<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<html>
	<head>
		<@ww.head/>
		
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
			@import url('<@ww.url value="/css/indicadores.css"/>');
			
			.semAcidentes { text-align: center; color: #060; border: 2px solid #060; background-color: #EFE; }
			.semAcidentes p { font-size: 14px; font-weight: bold; }
			.semAcidentes span { font-size: 22px; color: #333; }
			.graph li { padding: 13px; }
		</style>
	
		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
		
		<title>Painel de Indicadores de SESMT</title>

		<script type="text/javascript">
			$(function () {
				montaPie(${grfQtdCatsPorDiaSemana}, "#catsDiaSemana", {combinePercentMin: 0.05, percentMin: 0.05});
				montaPie(${grfQtdCatsPorHorario}, "#catsHorario", {combinePercentMin: 0.05, percentMin: 0.05});
				montaPie(${grfQtdAfastamentosPorMotivo}, "#afastamentosMotivo", {combinePercentMin: 0.05, percentMin: 0.05});
			});
			
			function validaForm()
			{
				return validaFormularioEPeriodo('formBusca', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'));
			}
		</script>
	
		
		<#if dataDe?exists>
			<#assign dateIni = dataDe?date/>
		<#else>
			<#assign dateIni = ""/>
		</#if>
		<#if dataAte?exists>
			<#assign dateFim = dataAte?date/>
		<#else>
			<#assign dateFim = ""/>
		</#if>
		
		<#include "../ftl/mascarasImports.ftl" />
		<#include "../ftl/showFilterImports.ftl" />
	</head>
	
	<body>
		<div class="semAcidentes">
	   		<p>Estamos trabalhando há <span>${qtdDiasSemAcidentes}</span> dias sem acidentes.</p>
	   	</div>
	   	<br clear="all"/>
	   	
		<#include "../util/topFiltro.ftl" />
			<@ww.form name="formBusca" id="formBusca" action="painel.action" method="POST">
				Período:*<br>
				<@ww.datepicker name="dataDe" id="dataDe" value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.datepicker name="dataAte" id="dataAte" value="${dateFim}" cssClass="mascaraData validaDataFim" />
				
				<button onclick="return validaForm();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />

		<div class="fieldGraph">
			<h1>CATs por Dia da Semana</h1>
		    <div id="catsDiaSemana" class="graph"></div>
	    </div>
	    
		<div class="fieldGraph">
			<h1>CATs por Horário</h1>
	    	<div id="catsHorario" class="graph"></div>
	    </div>
	    
	    <div class="fieldGraph">
			<h1>Total de Afastamentos por Motivo</h1>
	    	<div id="afastamentosMotivo" class="graph"></div>
	    </div>
	    
	    <div class="fieldGraph">
			<h1>Estatísticas do SESMT</h1>
	    	<div class="graph">
	    		<ul>
	    			<li>Nº de Exames Realizados: </li>
	    			<li>Nº de Registros de Prontuários:	</li>
	    			<li>Nº de Afastamentos pelo INSS: </li>
	    			<li>Nº de Afastamentos (não afastados pelo INSS): </li>
	    		</ul>
	    	</div>
	    </div>
	    
		<div class="fieldGraph bigger">
			<h1>Estatísticas de Resultados de Exames</h1>
	   		<div id="resultadosExames" class="graph2"></div>
	   	</div>
	    
	    <div style="clear: both"></div>
		<a name="pagebottom"></a>
	</body>
</html>