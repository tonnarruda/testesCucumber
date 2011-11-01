<html>
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
	<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/indicadores.css"/>');
		
		.formula { margin: 7px 5px; }
	</style>
	

		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
		
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
		<#include "../ftl/showFilterImports.ftl" />
		
		<title>Painel de Indicadores de Recrutamento e Seleção</title>

		<script type="text/javascript">
			$(function () {
				totalVagas = 0;
				$('.qtdVagaCargo').each(function() {
				    totalVagas = totalVagas + parseInt($(this).text());
				});
				$('#totalVagas').text(totalVagas);
				
			});
			
			function validaForm()
			{
				return validaFormularioEPeriodo('form', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'));
			}
		</script>
	
		<#include "../ftl/mascarasImports.ftl" />
	
		<style type="text/css">
			.graph li{
				padding: 13px;
			}
		</style>
	
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
	
	</head>
	<body>
		<#include "../util/topFiltro.ftl" />
			<@ww.form name="formBusca" id="formBusca" action="painelIndicadores.action" method="POST">
				Período:*<br>
				<@ww.datepicker name="dataDe" id="dataDe"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.datepicker name="dataAte" id="dataAte" value="${dateFim}" cssClass="mascaraData validaDataFim" />
				
				<button onclick="return validaForm();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
	
		<div class="fieldGraph">
			<h1>Vagas Disponíveis (total: <span id="totalVagas"></span>)</h1>
		    <div id="vagasDisponiveis" class="graph" style="overflow: scroll;overflow-x: hidden;width: 477px !important;">
		    	
		    	<@display.table name="faixaSalarials" id="faixa" class="dados" style="width: 460px;"  >
					<@display.column property="descricao" title="Cargo" style="width: 415px;"/>
					<@display.column property="qtdVagasAbertas" class="qtdVagaCargo" title="Qtd." style="width: 50px;text-align: right;" />
				</@display.table>
		    </div>
	    </div>
		<div class="fieldGraph">
			<h1>Indicadores de R&S</h1>
	    	<div id="indicadores" class="graph">
	    		<ul>
	    			<li>Nº de Currículos Recebidos/Cadastrados:	</li>
	    			<li>Nº de Candidatos Atendidos:	</li>
	    			<li>Nº de Vagas Preenchidas:	</li>
	    			<li>Nº de Candidatos Atendidos p/ Preenchimento de uma Vaga:	</li>
	    		</ul>
	    	</div>
	    </div>
	    
		<div class="fieldGraph bigger">
			<h1>Duração para Preenchimento de Vaga</h1>
	   		<div id="evolucaoTurnover" style="margin: 25px;height:300px;"></div>
			<div style="clear: both"></div>
	    </div>
	 
	    <div class="fieldGraph">
			<h1>Vagas Preenc. por Cargo</h1>
	    	<div id="vagasPorCargo" class="graph"></div>
	    </div>
	    <div class="fieldGraph">
			<h1>Vagas Preenc. por Área</h1>
	    	<div id="vagasPorArea" class="graph"></div>
	    </div>
	    
		<div class="fieldGraph">
			<h1>Vagas Preenc. por Motivo</h1>
		    <div id="vagasPorMotivo" class="graph" >
		    	
		    </div>
		</div>
   		<div class="fieldGraph">
			<h1>Estatística de Divulgação de Vagas</h1>
	    	<div id="estatistica" class="graph"></div>
	    </div>
	    
	    <div style="clear: both"></div>
	</body>
</html>