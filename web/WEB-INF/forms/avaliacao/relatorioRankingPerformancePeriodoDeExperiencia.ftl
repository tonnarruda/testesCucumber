<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>

	<title>Ranking de Performance das Avaliações de Desempenho</title>

	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('avaliacaoDesempenho','periodoIni','periodoFim'), new Array('periodoIni','periodoFim'))"/>
	<#include "../ftl/mascarasImports.ftl" />

	<#if periodoIni?exists>
		<#assign periodoIniFormatado = periodoIni?date/>
	<#else>
		<#assign periodoIniFormatado = ""/>
	</#if>
	<#if periodoFim?exists>
		<#assign periodoFimFormatado = periodoFim?date/>
	<#else>
		<#assign periodoFimFormatado = ""/>
	</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript">
		
		$(document).ready(function($){
			
			$("#agruparPorModelo").change(function() {
				if(this.checked)
				{
					$('#opcaoAgrupadoPorModeloAvaliacao,  #considerarAutoAvaliacao').show();
					$('#opcaoPorAvaliacaoDesempenho').hide();
				}
				else
				{				
					$('#opcaoAgrupadoPorModeloAvaliacao,  #considerarAutoAvaliacao').hide();
					$('#opcaoPorAvaliacaoDesempenho').show();
				}
			});
			
			$('#opcaoAgrupadoPorModeloAvaliacao, #considerarAutoAvaliacao').toggle($('#agruparPorModelo').is(':checked'));
		
			
		});
		function pesquisar(avaliacaoId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.getColaboradoresByAvaliacao(createListColaboradorAvaliacao, avaliacaoId);
			return false;
		}
		
		function createListColaboradorAvaliacao(data)
		{
			addChecks('colaboradorsCheck',data);
		}
		
	</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

		<@ww.form name="form" action="imprimeRelatorioRankingPerformancePeriodoDeExperiencia.action" onsubmit="${validarCampos}" method="POST">
			
			<@ww.checkbox label="Agrupar por Modelo de Avaliação" id="agruparPorModelo" name="agruparPorModelo" labelPosition="left" />
			<@ww.datepicker label="Período" required="true" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
			<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/>

			<div id="opcaoPorAvaliacaoDesempenho">
				<@ww.select label="Avaliação" required="true" name="avaliacaoDesempenho.id" id="avaliacaoDesempenho" list="avaliacaoDesempenhos" listKey="id" listValue="titulo" headerKey="" headerValue="Selecione..." />
			</div>
		
			<div id="opcaoAgrupadoPorModeloAvaliacao">
				<@ww.select label="Modelo de Avaliação de Desempenho" required="true" name="avaliacao.id" id="avaliacao" list="avaliacoes" listKey="id" listValue="titulo" headerKey="" headerValue="Selecione..." onchange="pesquisar(this.value);" />
				<@frt.checkListBox label="Colaboradores" name="colaboradorsCheck" id="colaboradorsCheck" list="colaboradorsCheckList"/>
			</div>
			
			<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList"/>						
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList"/>
	
			<div id="considerarAutoAvaliacao">
				<@ww.checkbox label="Considerar Auto-avaliação" name="considerarAutoAvaliacao" labelPosition="left" />
			</div>
				
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="$('form[name=form]').attr('action', 'imprimeRelatorioRankingPerformancePeriodoDeExperiencia.action');${validarCampos};"></button>
			<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'imprimeRelatorioRankingPerformancePeriodoDeExperienciaXLS.action');${validarCampos};"></button>
		</div>
</body>
</html>