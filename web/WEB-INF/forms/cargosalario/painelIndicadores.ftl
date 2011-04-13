<html>
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/indicadores.css"/>');
	</style>
	

		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
		
		<#include "../ftl/showFilterImports.ftl" />
		
		<title>Painel de Indicadores de Info. Funcionais</title>

		<script type="text/javascript">
			$(function () {
			
				montaPie(${grfFormacaoEscolars}, "#formacaoEscolar");
				montaPie(${grfFaixaEtarias}, "#faixaEtaria");
				montaPie(${grfSexo}, "#sexo", {percentMin:0});
				montaPie(${grfEstadoCivil}, "#estadoCivil", {percentMin: 0.02});
				montaPie(${grfDeficiencia}, "#deficiencia", {percentMin: 0.03});
				montaPie(${grfColocacao}, "#colocacao", {percentMin: 0.02});
				montaPie(${grfDesligamento}, "#desligamento", {radiusLabel:0.9, percentMin: 0.02, pieLeft:-190});
				
				//$("#interactive").bind("plotclick", pieClick);
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
		
		<div class="legendTotal">Total de Colaboradores: ${qtdColaborador}</div>
		
		<div class="fieldGraph">
			<h1>Faixa Etária</h1>
		    <div id="faixaEtaria" class="graph"></div>
	    </div>
		<div class="fieldGraph">
			<h1>Estado Civil</h1>
	    	<div id="estadoCivil" class="graph"></div>
	    </div>
	    <div class="fieldGraph">
			<h1>Deficiência</h1>
	    	<div id="deficiencia" class="graph"></div>
	    </div>
	    <div class="fieldGraph">
			<h1>Colocação</h1>
	    	<div id="colocacao" class="graph"></div>
	    </div>
		<div class="fieldGraph medium">
			<h1>Formação Escolar</h1>
		    <div id="formacaoEscolar" class="graph" ></div>
		</div>
   		<div class="fieldGraph small">
			<h1>Sexo</h1>
	    	<div id="sexo" class="graph"></div>
	    </div>
	    
		<div style="clear: both"></div>
		
		<br>
		
		<div class="divFiltro">
			<div class="divFiltroLink">
				<a href="javascript:exibeFiltro('${urlImgs}','divFiltroForm2');" id="linkFiltro"><img alt="Ocultar\Exibir Filtro" src="<@ww.url includeParams="none" value="${imagemFiltro}"/>"> <span id="labelLink" class="labelLink">${labelFiltro}</span></a>
			</div>
			<div id="divFiltroForm2" class="divFiltroForm ${classHidden}">
			<@ww.form name="formBusca2" id="formBusca2" action="painelIndicadores.action#pagebottom" method="POST">
				<@ww.datepicker name="dataIni" id="dataIni" value="${dateIni}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" liClass="liLeft"/>
				&nbsp&nbsp&nbsp&nbsp&nbsp;Exibir os
				<@ww.textfield theme="simple" name="qtdItensDesligamento" value="${qtdItensDesligamento}" id="qtdItensDesligamento" cssStyle="width:20px; text-align:right;" maxLength="2" onkeypress = "return(somenteNumeros(event,''));"/> 
				itens de maior percentual.<br>
				<@ww.hidden name="dataBase"/>
				<button onclick="return enviaForm2();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
			<div class="fieldGraph bigger">
			<h1>Motivos de Desligamentos</h1>
		   		<div id="desligamento" class="graph2"></div>
		    </div>
			<div style="clear: both"></div>
			<br>
			<div class="fieldDados">
				<div>Admitidos: ${countAdmitidos}</div>
				<div>Demitidos: ${countDemitidos}</div>
				<div>Turnover: ${turnover}</div>
			</div>

		    <div style="clear: both"></div>
			<a name="pagebottom"></a>
			
	</body>
</html>