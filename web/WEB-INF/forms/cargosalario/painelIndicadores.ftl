<html>
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/indicadores.css?version=${versao}"/>');
		
		.formula { margin: 7px 5px; }
		.icoImprimir { float: right; cursor: pointer; }
		
		#indicadores li { padding: 10px; }
		.divFiltro { margin-left: 5px; }
		
		.aba { display: inline; padding: 2px 8px; *padding: 5px 8px; background-color: #D5D5D5; border: 1px solid #CCC; }
		.aba a { font-family: Verdana, sans-serif; font-size: 10px; color: #000; text-decoration: none; text-transform: uppercase; }
		.aba a:hover { text-decoration: none; color: #600; }
		.aba.ativa { background-color: #F6F6F6; border-bottom-color: #F6F6F6; }
		#abas { position: relative; z-index: 500; margin-bottom: 2px; }
		
		.conteudo { background-color: #F6F6F6; padding: 10px; border: 1px solid #CCC; }
		
		.fieldDados { width: 923px !important; }
		.fieldDadosTurnover { border: none; border-top: 1px solid #BEBEBE; height: inherit; }
		.fieldDadosTurnover th { font-weight: normal; border-bottom: 1px solid #000; }
		.fieldDadosTurnover td.val { text-align: right; } 
		.grid-cell { background-color: #FFF; }
		
		.filtro-esquerda, .filtro-direita { width: 465px; }
		.filtro-esquerda { float: left; }
		.filtro-direita { float: right; }
		
		fieldset { padding: 10px; margin-bottom: 10px; background: inherit; }
		
		.subForm { width: 100%; }
		
		.legend { float: left; width: 100%; }
		.grid-cell table tr td { vertical-align: top;}
		.grid-cell table tr td.legendLabel { min-width: 150px;}
	</style>
	
		<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js?version=${versao}"/>'></script>
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		
		<#include "../ftl/showFilterImports.ftl" />
		
		<title>Painel de Indicadores de Info. Funcionais</title>

		<script type="text/javascript">
			var empresasPermitidasIds = new Array();
			<#if empresaIds?exists>
				<#list empresaIds as empresaId>
					empresasPermitidasIds.push(${empresaId});
				</#list>
			</#if>
			
			$(function () {
				$('#tooltipAbsenteismo').qtip({
					content: 'São consideradas faltas as Ocorrências cujo o tipo está marcado como Absenteísmo e os Afastamentos cujo o motivo está marcado como Absenteísmo.'
				});
			
				populaCargosByAreaVinculados();
			
				$('#cargosVinculadosAreas').click(function() {
					populaCargosByAreaVinculados();
				});
				
				$('#cargosVinculadosAreas').attr('checked', true);
			
				montaGraficoPizza(${grfFaixaEtarias}, "#faixaEtaria", {}, "Faixa Etária");
				montaGraficoPizza(${grfEstadoCivil}, "#estadoCivil", {percentMin: 0.02}, "Estado Civil");
				montaGraficoPizza(${grfDeficiencia}, "#deficiencia", {percentMin: 0.03}, "Deficiência");
				montaGraficoPizza(${grfColocacao}, "#colocacao", {percentMin: 0.02}, "Colocação");
				montaGraficoPizza(${grfOcorrencia}, "#ocorrencia", {percentMin: 0.02, radiusLabel:0.9, combinePercentMin: 0.03}, "Ocorrências", true);
				montaGraficoPizza(${grfProvidencia}, "#providencia", {percentMin: 0.02, radiusLabel:0.9}, "Providências");
				montaGraficoPizza(${grfFormacaoEscolars}, "#formacaoEscolar", {pieLeft:-190}, "Formação Escolar");
				montaGraficoPizza(${grfSexo}, "#sexo", {percentMin:0}, "Sexo");
				montaGraficoPizza(${grfTurnoverTempoServico}, "#turnoverTempoServico", {}, "Turnover por Tempo de Serviço");

				montaGraficoPizza(${grfDesligamento}, "#desligamento", {radiusLabel:0.9, percentMin: 0.02, pieLeft:-190}, "Motivo de Desligamento");
				
				var absenteismo = ${grfEvolucaoAbsenteismo};
				var turnover = ${grfEvolucaoTurnover};
				
				var somaAbsenteismo = 0;
				$.each(absenteismo, function (){
				    somaAbsenteismo = this[1] + somaAbsenteismo;
				});
				
				$('#mediaAbsenteismo').text('Absenteísmo: ' + (somaAbsenteismo / absenteismo.length).toFixed(2).replace('.',','));
				
				montaGraficoLinha([{data: absenteismo}], "#evolucaoAbsenteismo", "Absenteismo", 2);
				montaGraficoLinha(turnover, "#evolucaoTurnover", "Turnover", 2);
				
				populaAreas();

				$('.conteudo-aba').hide();
				
				$('.aba a').click(function() {
					var aba = $(this).parent();
					var idAba = aba.attr('id').replace('aba','');
					
					$('.aba').removeClass('ativa');
					aba.addClass('ativa');

					$('.conteudo-aba').hide();
					$('#abaMarcada').val(idAba);
					$('.conteudo-' + idAba).show();
				});
				
				$('#aba${abaMarcada} a').click();
				
				<#list 0..(tempoServicoIni?size-1) as i>
					addPeriodo(${tempoServicoIni[i]}, ${tempoServicoFim[i]});
				</#list>
				
			});
			
			var popup;
			
			function populaOcorrencias() {
				DWRUtil.useLoadingMessage('Carregando...');
				var dataIni = $("#dataIni").val();
				var dataFim = $("#dataFim").val();
				var qtdItens = $("#qtdItensOcorrencia").val();
				var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
				var empresaIds = getArrayCheckeds(document.forms[0],'empresasCheck');
				var estabelecimentosIds =  getArrayCheckeds(document.forms[0],'estabelecimentosIds');
				var cargosIds =  getArrayCheckeds(document.forms[0],'cargosIds');

				ColaboradorDWR.getOcorrenciasByPeriodo(createListOcorrenciasByPeriodo, dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, cargosIds, qtdItens, "getDescricao", 0);
			}
			
			function montaGraficoLinha(dados, obj, titulo, precisao)
			{
				montaLine(dados, obj, precisao);
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
			
			
			function montaGraficoPizza(dados, obj, configGrafico, titulo, showDatasCombine)
			{
				configGrafico.noColumns = 2;
				configGrafico.container = obj + "Legenda"; 

				montaPie(dados, obj, configGrafico, showDatasCombine);
				
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
									
									if(showDatasCombine)
										configGrafico.combinePercentMin = 0;
										
									popup.window.opener.montaPie(dados, popup.document.getElementById('popupGrafico'), configGrafico);
									popup.window.print();
									popup.window.close();
								}
							}
						);
			}
			
			function enviaForm()
			{
				var valida = true;
				var foco;
				$("input[name='tempoServicoIni'],input[name='tempoServicoFim']").each(function(i, item) {
					if ( !$(this).val() ) {
						valida = false;
						$(this).css('background-color', '#FFEEC2');
					} else
						$(this).css('background-color', '#FFFFFF');
				});
				
				if (!valida) {
					jAlert("Preencha os períodos corretamente");
					return false;
				}
				
				return validaFormulario('formBusca', new Array('@empresasCheck','dataBase','dataIni','dataFim','dataIniDeslig','dataFimDeslig','dataIniTurn','dataFimTurn','dataMesAnoIni','dataMesAnoFim'), new Array('dataBase','dataIni','dataFim','dataIniDeslig','dataFimDeslig','dataIniTurn','dataFimTurn','dataMesAnoIni','dataMesAnoFim'));
			}
					
			function populaEstabelecimentos() {
				var empresasIds = getArrayCheckeds(document.forms[0],'empresasCheck');
				EstabelecimentoDWR.getByEmpresas(null, empresasIds, createListEstabelecimentos);
			}	
			
			function createListEstabelecimentos(data) {
				addChecksByMap("estabelecimentosCheck", data);
			}
			
			function populaAreas()
			{
				DWRUtil.useLoadingMessage('Carregando...');
				var empresaIds = getArrayCheckeds(document.forms[0], 'empresasCheck');
				if(empresaIds.length > 0)
					AreaOrganizacionalDWR.getByEmpresas(createListAreas, null, empresaIds, null);
				else
					AreaOrganizacionalDWR.getByEmpresas(createListAreas, null, empresasPermitidasIds, null);
					
				populaCargosByAreaVinculados();
			}
	
			function createListAreas(data)
			{
				addChecks('areasCheck', data, 'populaCargosByAreaVinculados();populaOcorrencias();');
			}
			
			function populaCargosByAreaVinculados()
			{
				DWRUtil.useLoadingMessage('Carregando...');
				var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
				var empresasIds = getArrayCheckeds(document.forms[0],'empresasCheck');
				
				if ($('#cargosVinculadosAreas').is(":checked"))
				{
					if(areasIds.length == 0)
					{
						CargoDWR.getByEmpresas(createListCargosByArea, 0, empresasIds);
					}
					else
						CargoDWR.getCargoByArea(createListCargosByArea, areasIds, "getNomeMercadoComEmpresa", 0);
				}
				else
					CargoDWR.getByEmpresas(createListCargosByArea, 0, empresasIds);
			}
			
			function createListCargosByArea(data)
			{
				addChecks('cargosCheck',data);
			}
			
			function createListOcorrenciasByPeriodo(data)
			{
				addChecks('ocorrenciasCheck',data);
			}
			
			function delPeriodo(item)
			{
				$(item).parent().parent().remove();
			}
			
			function addPeriodo(ini, fim)
			{
				if ( $('#periodos li').size() >= 12 ) 
				{
					jAlert('Não é possível inserir mais do que 12 períodos para esse gráfico.');
					return false;
				}
				
				ini = ini != undefined ? ini : "";
				fim = fim != undefined ? fim : "";
				
				var periodo = '<li><span>';
				periodo += '<img title="Remover período" onclick="delPeriodo(this)" src="<@ww.url includeParams="none" value="/imgs/remove.png"/>" border="0" align="absMiddle" style="cursor:pointer;" />&nbsp;';
				periodo += '<input type="text" name="tempoServicoIni" id="tempoServicoIni" value="' + ini + '" style="width:30px; text-align:right;" maxlength="4" onkeypress="return somenteNumeros(event,\'\');"/>';
				periodo += '&nbsp;a&nbsp;';
				periodo += '<input type="text" name="tempoServicoFim" id="tempoServicoFim" value="' + fim + '" style="width:30px; text-align:right;" maxlength="4" onkeypress="return somenteNumeros(event,\'\');"/>';
				periodo += '&nbsp;meses</span></li>';
			
				$('#periodos').append(periodo);
				$('#tempoIni, #tempoFim').val('');
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
		
		<#if dataIniDeslig?exists>
		  <#assign dateIniDeslig = dataIniDeslig?date/>
		<#else>
		  <#assign dateIniDeslig = ""/>
		</#if>
		<#if dataFimDeslig?exists>
		  <#assign dateFimDeslig = dataFimDeslig?date/>
		<#else>
		  <#assign dateFimDeslig = ""/>
		</#if>
		
		<#if dataIniTurn?exists>
		  <#assign dateIniTurn = dataIniTurn?date/>
		<#else>
		  <#assign dateIniTurn = ""/>
		</#if>
		<#if dataFimTurn?exists>
		  <#assign dateFimTurn = dataFimTurn?date/>
		<#else>
		  <#assign dateFimTurn = ""/>
		</#if>
	
	</head>
	<body>
		<@ww.form name="formBusca" id="formBusca" action="painelIndicadores.action" method="POST">
			
			<div class="legendTotal">Total de Colaboradores: ${qtdColaborador}</div>
			
			<div id="abas">
				<div id="aba1" class="aba"><a href="javascript:;">Informações Sociais</a></div>
				<div id="aba2" class="aba"><a href="javascript:;">Ocorrências/Absenteísmo</a></div>
				<div id="aba3" class="aba"><a href="javascript:;">Turnover</a></div>
			</div>
			
			<div class="conteudo">
				<#include "../util/topFiltro.ftl" />
		
					<@ww.hidden name="abaMarcada" id="abaMarcada" value="1"/>
					<@ww.hidden name="empresa.turnoverPorSolicitacao"/>
					
					<table class="filtros">
						<tr>
							<td colspan="3" align="left">
								<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais marcadas" id="cargosVinculadosAreas" name="" labelPosition="left"/>
							</td>
						</tr>
						<tr>
							<td>
								<@frt.checkListBox label="Empresas" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formBusca')" onClick="populaAreas();populaEstabelecimentos();populaOcorrencias();" width="450" filtro="true"/>
							</td>
							<td>
								<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" onClick="populaOcorrencias();" form="document.getElementById('formBusca')" width="450" filtro="true"/>
							</td>
						</tr>
					    <tr>
							<td>
								<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList" onClick="populaCargosByAreaVinculados();populaOcorrencias();" width="450" filtro="true" selectAtivoInativo="true"/>
							</td>
							<td>
								<@frt.checkListBox label="Cargos" name="cargosCheck" id="cargosCheck" list="cargosCheckList" onClick="populaOcorrencias();" width="450" filtro="true" selectAtivoInativo="true"/>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<div class="conteudo-1 conteudo-aba">
									<fieldset>
										<@ww.datepicker label="Colaboradores admitidos até" name="dataBase" value="${dateBase}" id="dataBase" cssClass="mascaraData" />
										<@frt.checkListBox name="vinculosCheck" id="vinculosCheck" label="Colocação" list="vinculosCheckList" height="105" width="300"/>
									</fieldset>
								</div>
								
								<div class="conteudo-2 conteudo-aba">
									<fieldset style="float:left;margin-right:10px;">
										<legend>Ocorrências e Providêcias</legend>
										<table>
											<tr>
												<td>
													<@ww.datepicker label="Data Início" name="dataIni" id="dataIni" value="${dateIni}" cssClass="mascaraData validaDataIni" liClass="liLeft" onchange="populaOcorrencias()" onblur="populaOcorrencias()" />
													<@ww.datepicker label="Data Fim" name="dataFim" id="dataFim" value="${dateFim}" onchange="populaOcorrencias()" onblur="populaOcorrencias()"  cssClass="mascaraData validaDataFim" liClass="liLeft"/>
												</td>
												<td valign="bottom">
													Exibir os
													<@ww.textfield theme="simple" name="qtdItensOcorrencia" value="${qtdItensOcorrencia}" id="qtdItensOcorrencia" onchange="populaOcorrencias()" cssStyle="width:20px; text-align:right;" maxLength="2" onkeypress="return(somenteNumeros(event,''));"/> 
													itens de maior percentual.
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<div>
														<@frt.checkListBox label="Ocorrências" name="ocorrenciasCheck" id="ocorrenciasCheck" list="ocorrenciasCheckList" width="500" filtro="true" selectAtivoInativo="true"/>
													</div>
												</td>
											</tr>
										</table>
									</fieldset>
									
									<fieldset style="float:left;width:200px;margin-right:10px;">
										<legend>Absenteísmo</legend>
				
										<@ww.textfield label="Mês/Ano" name="dataMesAnoIni" id="dataMesAnoIni" cssClass="mascaraMesAnoData validaDataIni" liClass="liLeft"/>
										<@ww.textfield label="Mês/Ano" name="dataMesAnoFim" id="dataMesAnoFim" cssClass="mascaraMesAnoData validaDataFim"/>
									</fieldset>
									
								</div>
								
								<div class="conteudo-3 conteudo-aba">
									<fieldset style="float:left;width:300px;height:192px;margin-right:10px;">
										<legend>Motivo de Desligamento</legend>
										
										<@ww.datepicker label="Data Início" name="dataIniDeslig" id="dataIniDeslig" value="${dateIniDeslig}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
										<@ww.datepicker label="Data Fim" name="dataFimDeslig" id="dataFimDeslig" value="${dateFimDeslig}" cssClass="mascaraData validaDataFim"/>
										Exibir os
										<@ww.textfield theme="simple" name="qtdItensDesligamento" value="${qtdItensDesligamento}" id="qtdItensDesligamento" cssStyle="width:20px; text-align:right;" maxLength="2" onkeypress = "return(somenteNumeros(event,''));"/> 
										itens de maior percentual.
									</fieldset>
									
									<fieldset>
										<legend>Turnover</legend>
										<table>
											<tr>
												<td>
													<@ww.datepicker label="Data Início" name="dataIniTurn" id="dataIniTurn" value="${dateIniTurn}" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
													<@ww.datepicker label="Data Fim" name="dataFimTurn" id="dataFimTurn" value="${dateFimTurn}" cssClass="mascaraData validaDataFim"/>
													<@frt.checkListBox name="vinculosTurnoverCheck" id="vinculosTurnoverCheck" label="Colocação" list="vinculosTurnoverCheckList" height="105" width="300"/>
												</td>
												<td>
													Períodos de tempo de serviço:
													<div id="periodosServico" style="margin-left:20px">
														<ul id="periodos"></ul>
														<a title="Adicionar período" href="javascript:;" onclick="addPeriodo();">
															<img src="<@ww.url includeParams="none" value="/imgs/add.png"/>" border="0" align="absMiddle" /> 
															Adicionar período
														</a>
													</div>
												</td>
											</tr>
										</table>
									</fieldset>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<button onclick="return enviaForm();" class="btnPesquisar grayBGE"></button>
							</td>
						</tr>
					</table>
				<#include "../util/bottomFiltro.ftl" />
		
				<div class="conteudo-1 conteudo-aba">
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
							<td class="grid-cell" colspan="2">
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
							<td class="grid-cell" colspan="2">
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
							<td class="grid-cell medium" colspan="2">
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
					</table>
				</div>
				
				<div class="conteudo-2 conteudo-aba">
					<table class="grid" cellspacing="5">
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
							<td class="grid-cell" colspan="2">
								<div class="cell-title">
									Providências 
									<img id="providenciaImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
								</div>
								<div id="providencia" class="graph"></div>
						    	<div style="clear:both"></div>
				    			<div id="providenciaLegenda"></div>
							</td>
						</tr>
						<tr>
							<td class="grid-cell" colspan="3">
								<div class="cell-title">
									Absenteísmo
									<img id="evolucaoAbsenteismoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
								</div>
								<div class="graphWrapper" style="height: 360px !important;">
							    	<div id="evolucaoAbsenteismo" style="margin: 30px;height:300px;"></div>
							    </div>
								<div id="evolucaoAbsenteismoInfo">
									<div class="formula">Fórmula: [Total de faltas do mês<img id="tooltipAbsenteismo" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" align="absmiddle" /> / (Qtd. colaboradores ativos no início do mês * Dias trabalhados no mês)] * 100</div>
									
									<div style="clear: both"></div>
									
									<div class="fieldDados" style="border:none;border-top:1px solid #BEBEBE;">
										<div id="mediaAbsenteismo"></div>
									</div>
							 	</div>
							</td>
						</tr>
						
					</table>
				</div>
				
				<div class="conteudo-3 conteudo-aba">
					<table class="grid" cellspacing="5">
						<tr>
							<td class="grid-cell" colspan="3">
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
							<td class="grid-cell" colspan="3">
								<div class="cell-title">
									Turnover
									<img id="evolucaoTurnoverImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
								</div>
								<div class="graphWrapper" style="height: 350px !important;">
							    	<div id="evolucaoTurnover" style="margin: 25px;height:300px;"></div>
							    </div>
								<div id="evolucaoTurnoverInfo">
									<div class="fieldDados fieldDadosTurnover">
										<table cellspacing="10">
											<tr>
												<th></th>
												<th>Admitidos</th>
												<th>Demitidos</th>
												<th>Turnover</th>
												<th>Fórmula</th>
											</tr>
											<#list turnOverCollections as col>
												<tr>
													<td>${col.empresaNome}</td>
													<td class="val">${col.qtdAdmitidos}</td>
													<td class="val">${col.qtdDemitidos}</td>
													<td class="val">${col.media}</td>
													<td align="center"><span href=# style="cursor: help;" onmouseout="$('#tooltip').remove();" onmouseover="$('#tooltip').remove();showTooltip(event.pageX, event.pageY+20, '${col.formula?j_string}')">...</span></td>
												</tr>
											</#list>
										</table>
									</div>
							 	</div>
							</td>
						</tr>
						<tr>
							<td class="grid-cell" colspan="3">
								<div class="cell-title">
									Turnover por tempo de serviço
									<img id="turnoverTempoServicoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
								</div>
								<div class="graphWrapper" style="height: 390px !important;">
							    	<div id="turnoverTempoServico" class="graph2"></div>
					    			<div id="turnoverTempoServicoLegenda"></div>
							    </div>
							</td>
						</tr>
					</table>
				</div>
				
			</div>
		
		</@ww.form>
		   
	    <div style="clear: both"></div>
		<a name="pagebottom"></a>
	</body>
</html>