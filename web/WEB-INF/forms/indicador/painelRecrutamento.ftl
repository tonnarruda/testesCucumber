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
		div.graph { height: 220px; }
		div.graph li { padding: 10px; }
		div.fieldGraph { height: 280px; }
		.btnImprimir { float: right; margin: 5px; }
	</style>
	

	<!--[if lte IE 8]><script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.flot.pie.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/grafico.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<#include "../ftl/showFilterImports.ftl" />
	
	<title>Painel de Indicadores de Recrutamento e Seleção</title>

	<script type="text/javascript">
		$(function () {
		
			$('#tooltipHelp').qtip({
				content: 'Esse índice servirá para que o RH avalie a eficiência no processo seletivo. Ele mostrará o resultado com o percentual de colaboradores que se mantiveram na empresa após o período de experiência. <br />Fórmula utilizada: 100 - (colab. desligados em até 90 dias / nº de admitidos no período * 100)'
			});
		
			totalVagas = 0;
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
			
			montaGrafico("#vagasPorCargo", contratadosFaixa, 'Vagas Preenchidas por Cargo');
			montaGrafico("#vagasPorArea", contratadosArea, 'Vagas Preenchidas por Área');
			montaGrafico("#vagasPorMotivo", contratadosMotivo, 'Vagas Preenchidas por Motivo');
			montaGrafico("#divulgacaoVaga", divulgacaoVaga, 'Estatística de Divulgação de Vagas');
		});
		
		var popup;
		
		function montaGrafico(obj, dados, titulo)
		{
			montaPie(dados, obj, {combinePercentMin: 0.05, percentMin: 0.05});
			
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
								
								popup.window.opener.montaPie(dados, popup.document.getElementById('popupGrafico'), { container: popup.document.getElementById('popupGraficoLegenda'), combinePercentMin: 0.05, percentMin: 0.05} );
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
			
			<button onclick="return validaForm();" class="btnPesquisar grayBGE"></button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />

	<div class="fieldGraph">
		<h1>Vagas Disponíveis (total: <span id="totalVagas"></span>)</h1>
	    <div id="vagasDisponiveis" class="graph" style="overflow: scroll; overflow-x: hidden; width: 477px !important; height: 220px;">
	    	<@display.table name="faixaSalarials" id="faixa" class="dados" style="width: 460px;">
				<@display.column property="descricao" title="Cargo" style="width: 415px;"/>
				<@display.column property="qtdVagasAbertas" class="qtdVagaCargo" title="Qtd." style="width: 50px;text-align: right;" />
			</@display.table>
	    </div>
	    <button class="btnImprimir" id="vagasDisponiveisImprimir" onclick="imprimirDados('Vagas Disponíveis', $('#vagasDisponiveis').html())"></button>
    </div>
	<div class="fieldGraph">
		<h1>Indicadores de R&S</h1>
    	<div id="indicadores" class="graph">
    		<ul>
    			<li>Nº de Currículos Recebidos/Cadastrados:	${qtdCandidatosCadastrados}</li>
    			<li>Nº de Candidatos Atendidos:	${qtdCandidatosAtendidos}</li>
    			<li>Nº de Vagas Preenchidas: ${qtdVagasPreenchidas}</li>
    			<li>Nº de Candidatos Atendidos p/ Preench. de uma Vaga: ${qtdCandidatosAtendidosPorVaga?string(",##0.##")}</li>
    			<li>Índice de Eficiência do Processo Seletivo: ${indiceProcSeletivo?string(",##0.##")} 
    				<img align='absMiddle' id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16"  />
    			</li>
    		</ul>
    	</div>
		<button class="btnImprimir" id="indicadoresImprimir" onclick="imprimirDados('Indicadores de R&S', $('#indicadores').html())"></button>
    </div>
    
	<div class="fieldGraph bigger">
		<h1>Estatística de Preenchimento de Vaga</h1>
		<div id="estatisticaPreenchimento" style="height:220px; overflow:scroll; overflow-x: hidden;">
	   		<@display.table name="indicadorDuracaoPreenchimentoVagas" id="indicador" class="dados" style="width: 945px;">
				<@display.column property="cargo.nome" title="Cargo"/>
				<@display.column title="Vagas Disponíveis" style="width: 120px; text-align: right;">
					<#if indicador.qtdVagas?exists>
						${indicador.qtdVagas}
					<#else>
						0
					</#if>
				</@display.column>
				<@display.column title="Candidatos Atendidos" style="width: 135px; text-align: right;">
					<#if indicador.qtdCandidatos?exists>
						${indicador.qtdCandidatos}
					<#else>
						0
					</#if>
				</@display.column>
				<@display.column title="Vagas Preenchidas" style="width: 125px; text-align: right;">
					<#if indicador.qtdContratados?exists>
						${indicador.qtdContratados}
					<#else>
						0
					</#if>
				</@display.column>
				<@display.column title="Média de Dias" style="width: 95px; text-align: right;">
					${indicador.mediaDias?string(",##0.#")}
				</@display.column>
			</@display.table>
		</div>
		<button class="btnImprimir" id="estatisticaPreenchimentoImprimir" onclick="imprimirDados('Estatística de Preenchimento de Vaga', $('#estatisticaPreenchimento').html())"></button>
    </div>
 
    <div class="fieldGraph">
		<h1>Vagas Preenchidas por Cargo (total: <span id="totalVagasPreenchidasCargo"></span>)</h1>
    	<div id="vagasPorCargo" class="graph"></div>
    	<div style="clear:both"></div>
	    <button class="btnImprimir" id="vagasPorCargoImprimir"></button>
    </div>
    <div class="fieldGraph">
    	<h1>Vagas Preenchidas por Área (total: <span id="totalVagasPreenchidasArea"></span>)</h1>
    	<div id="vagasPorArea" class="graph"></div>
    	<div style="clear:both"></div>
	    <button class="btnImprimir" id="vagasPorAreaImprimir"></button>
    </div>
    
	<div class="fieldGraph">
		<h1>Vagas Preenchidas por Motivo (total: <span id="totalVagasPreenchidasMotivo"></span>)</h1>
	    <div id="vagasPorMotivo" class="graph" ></div>
	    <div style="clear:both"></div>
	    <button class="btnImprimir" id="vagasPorMotivoImprimir"></button>
	</div>
	<div class="fieldGraph">
		<h1>Estatística de Divulgação de Vagas</h1>
    	<div id="divulgacaoVaga" class="graph"></div>
    	<div style="clear:both"></div>
	    <button class="btnImprimir" id="divulgacaoVagaImprimir"></button>
    </div>
    
    <div style="clear: both"></div>
</body>
</html>