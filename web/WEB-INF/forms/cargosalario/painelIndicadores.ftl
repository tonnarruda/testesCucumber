<html>
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/indicadores.css"/>');
		
		.formula { margin: 7px 5px; }
	</style>
	

		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
		
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
		<#include "../ftl/showFilterImports.ftl" />
		
		<title>Painel de Indicadores de Info. Funcionais</title>

		<script type="text/javascript">
			$(function () {
				$('#tooltipAbsenteismo').qtip({
					content: 'São consideradas faltas as Ocorrências cujo o tipo está marcado como Absenteísmo e os Afastamentos cujo o motivo está marcado como Absenteísmo.'
				});
			
				montaPie(${grfFormacaoEscolars}, "#formacaoEscolar", {pieLeft:-190});
				montaPie(${grfFaixaEtarias}, "#faixaEtaria");
				montaPie(${grfSexo}, "#sexo", {percentMin:0});
				montaPie(${grfEstadoCivil}, "#estadoCivil", {percentMin: 0.02});
				montaPie(${grfOcorrencia}, "#ocorrencia", {percentMin: 0.02, radiusLabel:0.9, combinePercentMin: 0.03});
				montaPie(${grfProvidencia}, "#providencia", {percentMin: 0.02, radiusLabel:0.9});
				montaPie(${grfDeficiencia}, "#deficiencia", {percentMin: 0.03});
				montaPie(${grfColocacao}, "#colocacao", {percentMin: 0.02});
				montaPie(${grfDesligamento}, "#desligamento", {radiusLabel:0.9, percentMin: 0.02, pieLeft:-190});
				
				var absenteismo = ${grfEvolucaoAbsenteismo};
				var turnover = ${grfEvolucaoTurnover};
				
				var somaAbsenteismo = 0;
				$.each(absenteismo, function (){
				    somaAbsenteismo = this[1] + somaAbsenteismo;
				});
				
				$('#mediaAbsenteismo').text('Absenteísmo: ' + (somaAbsenteismo / absenteismo.length).toFixed(4));
				
				montaLine(absenteismo, "#evolucaoAbsenteismo");
				montaLine(turnover, "#evolucaoTurnover");
				
				populaAreas();
			});
			
			function enviaForm()
			{
				return validaFormulario('formBusca', new Array('@empresasCheck', 'dataBase','dataIni','dataFim','dataMesAnoIni','dataMesAnoFim'), new Array('dataBase','dataIni','dataFim','dataMesAnoIni','dataMesAnoFim'));
			}
						
			function populaAreas()
			{
				DWRUtil.useLoadingMessage('Carregando...');
				var empresaIds = getArrayCheckeds(document.forms[0], 'empresasCheck');
				if(empresaIds.length > 0)
					AreaOrganizacionalDWR.getByEmpresas(createListAreas, null, empresaIds);
				else
					createListAreas(null);
			}
	
			function createListAreas(data)
			{
				addChecks('areasCheck', data);
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
			<@ww.form name="formBusca" id="formBusca" action="painelIndicadores.action" method="POST">
				<@frt.checkListBox label="Empresas" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formBusca')" onClick="populaAreas();"/>
				<@ww.hidden name="empresa.turnoverPorSolicitacao"/>
				<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList"/>
				
				<li>&nbsp;</li>
				<li><strong>Indicadores de Faixa Etária, Estado Civil, Deficiência, Colocação, Formação Escolar e Sexo</strong></li>
				<@ww.datepicker label="Data" name="dataBase" value="${dateBase}" id="dataBase"  cssClass="mascaraData" />
				
				<li>&nbsp;</li>
				<li><strong>Indicadores de Motivos de Desligamento, Turnover, Ocorrências e Providêcias</strong></li>
				<@ww.datepicker label="Data Início" name="dataIni" id="dataIni" value="${dateIni}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.datepicker label="Data Fim" name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" liClass="liLeft"/>
				<li>&nbsp;</li>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Exibir os
				<@ww.textfield theme="simple" name="qtdItensDesligamento" value="${qtdItensDesligamento}" id="qtdItensDesligamento" cssStyle="width:20px; text-align:right;" maxLength="2" onkeypress = "return(somenteNumeros(event,''));"/> 
				itens de maior percentual.
				
				<li>&nbsp;</li>
				<li><strong>Indicador de Absenteísmo</strong></li>
				<@ww.textfield label="Mês/Ano" name="dataMesAnoIni" id="dataMesAnoIni" cssClass="mascaraMesAnoData validaDataIni" liClass="liLeft"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.textfield label="Mês/Ano" name="dataMesAnoFim" id="dataMesAnoFim" cssClass="mascaraMesAnoData validaDataFim"/>
				
				<button onclick="return enviaForm();" class="btnPesquisar grayBGE"></button>
			</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
	
		<div class="legendTotal">Total de Colaboradores: ${qtdColaborador}</div>
		
		<div class="fieldGraph">
			<h1>Faixa Etária</h1>
			<div class="graphWrapper">
		    	<div id="faixaEtaria" class="graph"></div>
		    </div>
	    </div>
		<div class="fieldGraph">
			<h1>Estado Civil</h1>
			<div class="graphWrapper">
		    	<div id="estadoCivil" class="graph"></div>
		    </div>
	    </div>
	    <div class="fieldGraph">
			<h1>Deficiência</h1>
			<div class="graphWrapper">
		    	<div id="deficiencia" class="graph"></div>
		    </div>
	    </div>
	    <div class="fieldGraph">
			<h1>Colocação</h1>
			<div class="graphWrapper">
		    	<div id="colocacao" class="graph"></div>
		    </div>
	    </div>
	    <div class="fieldGraph">
			<h1>Ocorrências</h1>
			<div class="graphWrapper">
		    	<div id="ocorrencia" class="graph"></div>
		    </div>
	    </div>
	    <div class="fieldGraph">
			<h1>Providências</h1>
			<div class="graphWrapper">
		    	<div id="providencia" class="graph"></div>
		    </div>
	    </div>
		<div class="fieldGraph medium">
			<h1>Formação Escolar</h1>
			<div class="graphWrapper">
			    <div id="formacaoEscolar" class="graph" ></div>
			</div>
		</div>
   		<div class="fieldGraph small">
			<h1>Sexo</h1>
			<div class="graphWrapper">
		    	<div id="sexo" class="graph"></div>
		    </div>
	    </div>
	    
		<div class="fieldGraph bigger">
			<h1>Motivos de Desligamentos</h1>
	   		<div id="desligamento" class="graph2"></div>
	   	</div>
	    	
		<div class="fieldGraph bigger">
			<h1>Turnover</h1>
   			<div id="evolucaoTurnover" style="margin: 25px;height:300px;"></div>

			<div class="formula">Fórmula: [(Qtd. Admitidos + Qtd. Demitidos / 2) / Qtd. Colaboradores Ativos no início do mês] * 100</div>
	    
			<div style="clear: both"></div>
			
			<div class="fieldDados" style="border:none;border-top:1px solid #7E9DB9;">
				<div>Admitidos: ${countAdmitidos}</div>
				<div>Demitidos: ${countDemitidos}</div>
				<div>Turnover: ${turnover}</div>
			</div>
	    </div>

		<div class="fieldGraph bigger">
			<h1>Absenteísmo</h1>
	   		<div id="evolucaoAbsenteismo" style="margin: 25px;height:300px;"></div>
			<div class="formula">Fórmula: [Total de faltas do mês<img id="tooltipAbsenteismo" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" align="absmiddle" /> / (Qtd. colaboradores ativos no início do mês * Dias trabalhados no mês)]</div>
			
			<div style="clear: both"></div>
			
			<div class="fieldDados" style="border:none;border-top:1px solid #7E9DB9;">
				<div id="mediaAbsenteismo"></div>
			</div>
	    </div>
	    
	    <div style="clear: both"></div>
		<a name="pagebottom"></a>
	</body>
</html>