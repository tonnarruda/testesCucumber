<html>
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/indicadores.css"/>');
		
		.formula { margin: 7px 5px; }
		.icoImprimir { float: right; cursor: pointer; }
		
		#indicadores li { padding: 10px; }
		.divFiltro { margin-left: 5px; }
		
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
			
				montaGraficoPizza(${grfFaixaEtarias}, "#faixaEtaria", {}, "Faixa Etária");
				montaGraficoPizza(${grfEstadoCivil}, "#estadoCivil", {percentMin: 0.02}, "Estado Civil");
				montaGraficoPizza(${grfDeficiencia}, "#deficiencia", {percentMin: 0.03}, "Deficiência");
				montaGraficoPizza(${grfColocacao}, "#colocacao", {percentMin: 0.02}, "Colocação");
				montaGraficoPizza(${grfOcorrencia}, "#ocorrencia", {percentMin: 0.02, radiusLabel:0.9, combinePercentMin: 0.03}, "Ocorrências");
				montaGraficoPizza(${grfProvidencia}, "#providencia", {percentMin: 0.02, radiusLabel:0.9}, "Providências");
				montaGraficoPizza(${grfFormacaoEscolars}, "#formacaoEscolar", {pieLeft:-190}, "Formação Escolar");
				montaGraficoPizza(${grfSexo}, "#sexo", {percentMin:0}, "Sexo");

				montaGraficoPizza(${grfDesligamento}, "#desligamento", {radiusLabel:0.9, percentMin: 0.02, pieLeft:-190}, "Motivo Desligamento");
				
				var absenteismo = ${grfEvolucaoAbsenteismo};
				var turnover = ${grfEvolucaoTurnover};
				
				var somaAbsenteismo = 0;
				$.each(absenteismo, function (){
				    somaAbsenteismo = this[1] + somaAbsenteismo;
				});
				
				$('#mediaAbsenteismo').text('Absenteísmo: ' + (somaAbsenteismo / absenteismo.length).toFixed(4));
				
				montaGraficoLinha(absenteismo, "#evolucaoAbsenteismo", "Absenteismo");
				montaGraficoLinha(turnover, "#evolucaoTurnover", "Turnover");
				
				populaAreas();
				
			});
			
			var popup;
			
			function montaGraficoLinha(dados, obj, titulo)
			{
				montaLine(dados, obj);
				$(obj + "Imprimir")
						.unbind()
						.bind('click', 
							function(event) 
							{ 
								popup = window.open("<@ww.url includeParams="none" value="/grafico.jsp"/>");
								popup.window.onload = function() 
								{
									popup.focus();
									popup.document.getElementById('popupTitulo').innerHTML = titulo;
									popup.document.getElementById('popupGraficoLegenda').innerHTML = '<br />' + $(obj + 'Info .formula').text()+'<br /><br />' + $(obj + 'Info .fieldDados').text();
									popup.window.opener.montaLine(dados, popup.document.getElementById('popupGrafico'));
									popup.window.print();
									popup.window.close();
								}
							}
						);
			}
			
			
			function montaGraficoPizza(dados, obj, configGrafico, titulo)
			{
				configGrafico.noColumns = 2;
				configGrafico.container = obj + "Legenda"; 

				montaPie(dados, obj, configGrafico);
				
				$(obj + "Imprimir")
						.unbind()
						.bind('click', 
							function(event) 
							{ 
								popup = window.open("<@ww.url includeParams="none" value="/grafico.jsp"/>");
								popup.window.onload = function() 
								{
									popup.focus();
									popup.document.getElementById('popupTitulo').innerHTML = titulo;
									
									configGrafico.noColumns = 1;
									configGrafico.container = popup.document.getElementById('popupGraficoLegenda');
									
									popup.window.opener.montaPie(dados, popup.document.getElementById('popupGrafico'), configGrafico);
									popup.window.print();
									popup.window.close();
								}
							}
						);
			}
			
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
		
		<table class="grid" cellspacing="5">
			
			<tr>
				<td class="grid-cell">
					<div class="cell-title">
						Faixa Etária 
						<img id="faixaEtariaImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
				    <div id="faixaEtaria" class="graph"></div>
			    	<div style="clear:both"></div>
	    			<div id="faixaEtariaLegenda"></div>
				</td>
				<td class="grid-cell">
					<div class="cell-title">
						Estado Civil 
						<img id="estadoCivilImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
			    	<div id="estadoCivil" class="graph"></div>
			    	<div style="clear:both"></div>
	    			<div id="estadoCivilLegenda"></div>
				</td>
			</tr>
			<tr>
				<td class="grid-cell">
					<div class="cell-title">
						Deficiência 
						<img id="deficienciaImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
					<div id="deficiencia" class="graph"></div>
			    	<div style="clear:both"></div>
	    			<div id="deficienciaLegenda"></div>
				</td>
				<td class="grid-cell">
					<div class="cell-title">
						Colocação 
						<img id="colocacaoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
					<div id="colocacao" class="graph"></div>
			    	<div style="clear:both"></div>
	    			<div id="colocacaoLegenda"></div>
				</td>
			</tr>
			<tr>
				<td class="grid-cell">
					<div class="cell-title">
						Ocorrências 
						<img id="ocorrenciaImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
					<div id="ocorrencia" class="graph"></div>
			    	<div style="clear:both"></div>
	    			<div id="ocorrenciaLegenda"></div>
				</td>
				<td class="grid-cell">
					<div class="cell-title">
						Providências 
						<img id="providenciaImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
					<div id="providencia" class="graph"></div>
			    	<div style="clear:both"></div>
	    			<div id="providenciaLegenda"></div>
				</td>
			</tr>
		</table>
		<table class="grid" cellspacing="5">
			<tr>
				<td class="grid-cell medium">
					<div class="cell-title">
						Formação Escolar 
						<img id="formacaoEscolarImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
					<div id="formacaoEscolar" class="graph"></div>
			    	<div style="clear:both"></div>
	    			<div id="formacaoEscolarLegenda"></div>
				</td>
				<td class="grid-cell small">
					<div class="cell-title">
						Sexo 
						<img id="sexoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
					<div id="sexo" class="graph"></div>
			    	<div style="clear:both"></div>
	    			<div id="sexoLegenda"></div>
				</td>
			</tr>
			<tr>
				<td class="grid-cell bigger" colspan="2">
					<div class="cell-title">
						Motivos de Desligamento
						<img id="desligamentoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
					<div class="graphWrapper" style="height: 390px !important;">
				    	<div id="desligamento" class="graph2"></div>
		    			<div id="desligamentoLegenda"></div>
				    </div>
				</td>
			</tr>
			<tr>
				<td class="grid-cell bigger" colspan="2">
					<div class="cell-title">
						Turnover
						<img id="evolucaoTurnoverImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
					<div class="graphWrapper" style="height: 350px !important;">
				    	<div id="evolucaoTurnover" style="margin: 25px;height:300px;"></div>
				    </div>
					<div id="evolucaoTurnoverInfo">
						<div class="formula">Fórmula: [(Qtd. Admitidos + Qtd. Demitidos / 2) / Qtd. Colaboradores Ativos no início do mês] * 100</div>
				    
						<div style="clear: both"></div>
						
						<div class="fieldDados" style="border:none;border-top:1px solid #7E9DB9;">
							<div>Admitidos: ${countAdmitidos}</div>
							<div>Demitidos: ${countDemitidos}</div>
							<div>Turnover: ${turnover}</div>
						</div>
				 	</div>
				</td>
			</tr>
			<tr>
				<td class="grid-cell bigger" colspan="2">
					<div class="cell-title">
						Absenteísmo
						<img id="evolucaoAbsenteismoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
					</div>
					<div class="graphWrapper" style="height: 360px !important;">
				    	<div id="evolucaoAbsenteismo" style="margin: 30px;height:300px;"></div>
				    </div>
					<div id="evolucaoAbsenteismoInfo">
						<div class="formula">Fórmula: [Total de faltas do mês<img id="tooltipAbsenteismo" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" align="absmiddle" /> / (Qtd. colaboradores ativos no início do mês * Dias trabalhados no mês)]</div>
						
						<div style="clear: both"></div>
						
						<div class="fieldDados" style="border:none;border-top:1px solid #7E9DB9;">
							<div id="mediaAbsenteismo"></div>
						</div>
				 	</div>
				</td>
			</tr>
		</table>	    
	    <div style="clear: both"></div>
		<a name="pagebottom"></a>
	</body>
</html>