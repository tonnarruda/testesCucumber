<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>

	<title>Ranking de Performance das Avaliações de Desempenho</title>

	<#assign validarCamposAvaliacaoDesempenho="return validaFormularioEPeriodo('form', new Array('@avaliacaoCheck','periodoIni','periodoFim'), new Array('periodoIni','periodoFim'))"/>
	<#--
	<#assign validarCamposAvaliacao="return validaFormularioEPeriodo('form', new Array('avaliacao','periodoIni','periodoFim'), new Array('periodoIni','periodoFim'))"/>
	-->
	<#assign action="imprimeRelatorioRankingPerformancePeriodoDeExperiencia.action"/>
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
		function getColaboradores()
		{
			var avaliacaoIds = $("input[name='avaliacaoCheck']:checked");
			var avaliacoesIds = avaliacaoIds.toArray().map(function(item) {
				return parseInt($(item).val());
			});
			
			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.getColaboradoresByAvaliacoes(createListColaboradorAvaliacoes, $(avaliacoesIds).toArray());
			return false;
		}
		
		function createListColaboradorAvaliacoes(data)
		{
			addChecks('colaboradorsCheck',data);
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

		<@ww.form name="form" action="${action}" onsubmit="${validarCamposAvaliacaoDesempenho}" method="POST">
			
			<@ww.datepicker label="Período" required="true" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
			<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/>
			<@frt.checkListBox label="Avaliações*" name="avaliacaoCheck" id="avaliacaoCheck" list="avaliacaoCheckList" onClick="getColaboradores();"/>						
			<@frt.checkListBox label="Colaboradores" name="colaboradorsCheck" id="colaboradorsCheck" list="colaboradorsCheckList"/>
			<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList"/>						
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList"/>

		</@ww.form>

		<div class="buttonGroupAvaliacaoDesempenho">
			<button class="btnRelatorio"  onclick="$('form[name=form]').attr('action', 'imprimeRelatorioRankingPerformancePeriodoDeExperiencia.action');${validarCamposAvaliacaoDesempenho};"></button>
			<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'imprimeRelatorioRankingPerformancePeriodoDeExperienciaXLS.action');${validarCamposAvaliacaoDesempenho};"></button>
		</div>
</body>
</html>