<html>
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
	<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/indicadores.css?version=${versao}"/>');
		
		.formula { margin: 7px 5px; }
		.icoImprimir { float: right; cursor: pointer; }
		
		#indicadores li { padding: 10px; }
		.divFiltro { margin-left: 5px; }
	</style>
	
	<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<#include "../ftl/showFilterImports.ftl" />
	
	<title>Painel de Indicadores de Recrutamento e Seleção</title>

	<script type="text/javascript">
		$(function () {
		
			$(":checkbox[name='estabelecimentosCheck'], :checkbox[name='areasCheck']").click(populaSolicitacaoByEstabelecimentosAreas);
		
			$('#tooltipHelp').qtip({
				content: 'Esse índice servirá para que o RH avalie a eficiência no processo seletivo. Ele mostrará o resultado com o percentual de colaboradores que se mantiveram na empresa após o período de experiência. <br />Fórmula utilizada: 100 - (colab. desligados em até 90 dias / nº de admitidos em até 90 dias * 100). Apenas a data final do período é considerada para este cálculo.' 
			});
		
			var totalVagas = 0;
			$('.qtdVagaCargo').each(function() {
			    totalVagas = totalVagas + parseInt($(this).text());
			});
			$('#totalVagas').text(totalVagas);
			
			var contratadosFaixa = ${grfContratadosFaixa};
			var totalVagasPreenchidasCargo = 0;
			$(contratadosFaixa).each(function() {
			    totalVagasPreenchidasCargo += parseInt(this.data);
			});
			$('#totalVagasPreenchidasCargo').text(totalVagasPreenchidasCargo);

			var contratadosArea = ${grfContratadosArea};
			var totalVagasPreenchidasArea = 0;
			$(contratadosArea).each(function() {
			    totalVagasPreenchidasArea += parseInt(this.data);
			});
			$('#totalVagasPreenchidasArea').text(totalVagasPreenchidasArea);

			var contratadosMotivo = ${grfContratadosMotivo};
			var totalVagasPreenchidasMotivo = 0;
			$(contratadosMotivo).each(function() {
			    totalVagasPreenchidasMotivo += parseInt(this.data);
			});
			$('#totalVagasPreenchidasMotivo').text(totalVagasPreenchidasMotivo);

			var divulgacaoVaga = ${grfDivulgacaoVaga};
			
			montaGrafico("#vagasPorCargo", contratadosFaixa, 'Vagas Preenchidas por Cargo', 0.02);
			montaGrafico("#vagasPorArea", contratadosArea, 'Vagas Preenchidas por Área', 0.02);
			montaGrafico("#vagasPorMotivo", contratadosMotivo, 'Vagas Preenchidas por Motivo', 0.02);
			montaGrafico("#divulgacaoVaga", divulgacaoVaga, 'Estatística de Divulgação de Vagas', 0);
		});
		
		var popup;
		
		function montaGrafico(obj, dados, titulo, combinePercMin)
		{
			montaPie(dados, obj, { combinePercentMin: combinePercMin, percentMin: 0.03, noColumns: 2, container: obj + "Legenda" });
			
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
								
								popup.window.opener.montaPie(dados, popup.document.getElementById('popupGrafico'), { container: popup.document.getElementById('popupGraficoLegenda'), combinePercentMin: combinePercMin, percentMin: 0.03} );
								popup.window.print();
								popup.window.close();
							}
						}
					);
		}
		
		function imprimirDados(titulo, conteudo)
		{
			popup = window.open("<@ww.url includeParams="none" value="/conteudo.jsp"/>");
			popup.window.onload = function() 
			{
				popup.focus();
				popup.document.getElementById('popupTitulo').innerHTML = titulo;
				popup.document.getElementById('popupConteudo').innerHTML = conteudo;
				popup.window.print();
				popup.window.close();
			}
		}
		
		function validaForm()
		{
			return validaFormularioEPeriodo('formBusca', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'));
		}

		function populaSolicitacaoByEstabelecimentosAreas()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
			var estabelecimentosIds = getArrayCheckeds(document.forms[0],'estabelecimentosCheck');
			
			SolicitacaoDWR.getByEmpresaEstabelecimentosAreas(createListSolicitacoesByAreas, ${empresaSistema.id}, estabelecimentosIds , areasIds);
		}
		
		function createListSolicitacoesByAreas(data)
		{
			addChecks('solicitacaosCheckIds',data);
		}
	</script>

	<#include "../ftl/mascarasImports.ftl" />

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
		<@ww.form name="formBusca" id="formBusca" action="painel.action" method="post">
			Período:*<br>
			<@ww.datepicker name="dataDe" id="dataDe"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.datepicker name="dataAte" id="dataAte" value="${dateFim}" cssClass="mascaraData validaDataFim" />
			<@ww.select label="Referenciar o período acima com a data da" name="dataStatusAprovacaoSolicitacao" list="tiposDataStatusAprovacaoSolicitacao" cssStyle="width: 300px;" />
			<@ww.hidden name="statusSolicitacao" value = 'S'/>			
			<@ww.checkbox label="Considerar contratações futuras para estatística de preenchimento de vaga" id="considerarContratacaoFutura" name="considerarContratacaoFutura" labelPosition="left"/>
			<@frt.checkListBox label="Estabelecimentos*" name="estabelecimentosCheck" id="estabelecimentoCheck" list="estabelecimentosCheckList" filtro="true" width="460" liClass="liLeft"/>
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areaCheck" list="areasCheckList" filtro="true" selectAtivoInativo="true" width="460" liClass="liLeft"/>
			<@frt.checkListBox label="Solicitações de Pessoal" name="solicitacaosCheckIds" id="solicitacao" list="solicitacaosCheck" width="926" filtro="true"/>
			
			<button onclick="return validaForm();" class="btnPesquisar grayBGE"></button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />

	<table class="grid" cellspacing="5">
		<tr>
			<td class="grid-cell">
				<div class="cell-title">
					Vagas Disponíveis (total: <span id="totalVagas"></span>) 
					<img onclick="imprimirDados('Vagas Disponíveis', $('#vagasDisponiveis').html())" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
				</div>
			    <div id="vagasDisponiveis" class="table-wrapper">
			    	<@display.table name="faixaSalarials" id="faixa" class="dados" style="width: 460px;">
						<@display.column property="descricao" title="Cargo" style="width: 415px;"/>
						<@display.column property="qtdVagasAbertas" class="qtdVagaCargo" title="Qtd." style="width: 50px;text-align: right;" />
					</@display.table>
			    </div>
			</td>
			<td class="grid-cell">
				<div class="cell-title">
					Indicadores de R&S <img id="indicadoresImprimir" onclick="imprimirDados('Indicadores de R&S', $('#indicadores').html())" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
				</div>
		    	<div id="indicadores">
		    		<ul>
		    			<li>Nº de currículos recebidos/cadastrados:	${qtdCandidatosCadastrados}</li>
		    			<li>Nº de candidatos participantes de uma ou mais etapas seletivas: ${qtdCandidatosAtendidos}</li>
		    			<li>Nº de etapas realizadas: ${qtdEtapasRealizadas?string(",##0.##")}</li>
		    			<li>Nº de vagas preenchidas: ${qtdVagasPreenchidas}</li>
		    			<li>Média de candidatos atendidos p/ preench. de uma vaga: ${qtdCandidatosAtendidosPorVaga?string(",##0.##")}</li>
		    			<li>Índice de eficiência do processo seletivo: ${indiceProcSeletivo?string(",##0.##")}%
		    				<img align='absMiddle' id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16"  />
		    			</li>
		    		</ul>
		    	</div>
			</td>
		</tr>
		<tr>
			<td class="grid-cell" colspan="2">
				<div class="cell-title">
					Estatística de Preenchimento de Vaga 
					<img onclick="imprimirDados('Estatística de Preenchimento de Vaga', $('#estatisticaPreenchimento').html())" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
				</div>
				<div id="estatisticaPreenchimento" class="table-wrapper">
			   		<@display.table name="indicadorDuracaoPreenchimentoVagas" id="indicador" class="dados" style="width: 945px;">
						<@display.column property="areaOrganizacional.nome" title="Área Organizacional" />
						<@display.column property="cargo.nome" title="Cargo" style="width: 240px;"/>
						<@display.column title="Vagas Disponíveis" style="width: 70px; text-align: right;">
							<#if indicador.qtdVagas?exists>
								${indicador.qtdVagas}
							<#else>
								0
							</#if>
						</@display.column>
						<@display.column title="Candidatos Atendidos" style="width: 70px; text-align: right;">
							<#if indicador.qtdCandidatos?exists>
								${indicador.qtdCandidatos}
							<#else>
								0
							</#if>
						</@display.column>
						<@display.column title="Vagas Preenchidas" style="width: 70px; text-align: right;">
							<#if indicador.qtdContratados?exists>
								${indicador.qtdContratados}
							<#else>
								0
							</#if>
						</@display.column>
						<@display.column title="Média de Dias" style="width: 60px; text-align: right;">
							${indicador.mediaDias?string(",##0.#")}
						</@display.column>
					</@display.table>
				</div>
			</td>
		</tr>
		<tr>
			<td class="grid-cell">
				<div class="cell-title">
					Vagas Preenchidas por Cargo (total: <span id="totalVagasPreenchidasCargo"></span>) 
					<img id="vagasPorCargoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
				</div>
		    	<div id="vagasPorCargo" class="graph"></div>
		    	<div style="clear:both"></div>
		    	<div id="vagasPorCargoLegenda"></div>
			</td>
			<td class="grid-cell">
		    	<div class="cell-title">
		    		Vagas Preenchidas por Área (total: <span id="totalVagasPreenchidasArea"></span>) 
		    		<img id="vagasPorAreaImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
		    	</div>
		    	<div id="vagasPorArea" class="graph"></div>
		    	<div style="clear:both"></div>
		    	<div id="vagasPorAreaLegenda"></div>
			</td>
		</tr>
		<tr>
			<td class="grid-cell">
				<div class="cell-title">
					Vagas Preenchidas por Motivo (total: <span id="totalVagasPreenchidasMotivo"></span>) 
					<img id="vagasPorMotivoImprimir" border="0" class="icoImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"/>
				</div>
			    <div id="vagasPorMotivo" class="graph"></div>
			    <div style="clear:both"></div>
			    <div id="vagasPorMotivoLegenda"></div>
			</td>
			<td class="grid-cell">
				<div class="cell-title">
					Estatística de Divulgação de Vagas 
					<img id="divulgacaoVagaImprimir" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" border="0" class="icoImprimir"/>
				</div>
		    	<div id="divulgacaoVaga" class="graph"></div>
		    	<div style="clear:both"></div>
		    	<div id="divulgacaoVagaLegenda"></div>
			</td>
		</tr>
	</table>
</body>
</html>