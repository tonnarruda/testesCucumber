<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>

	<title>Ranking de Performance das Avaliações de Desempenho</title>

	<#assign validarCamposAvaliacaoDesempenho="return validaFormularioEPeriodo('form', new Array('@avaliacaoCheck','periodoIni','periodoFim'), new Array('periodoIni','periodoFim'))"/>
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

					
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AvaliacaoDesempenhoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<script type="text/javascript">
		var empresaIds = new Array();
		<#if empresas?exists && 0 < empresas?size>
			<#list empresas as empresa>
				empresaIds.push(${empresa.id});
			</#list>
		</#if>
		
		$(function() {
			$('#empresaId').change(function() {
				getAreasOrganizacionais(this.value);
			});
			
			$("input[name='avaliacaoCheck']").live('click', function() { getColaboradores(); });
		});
		
		function getAvaliacoes(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AvaliacaoDesempenhoDWR.getAvaliacoesByEmpresa(createListAvaliacoes, empresaId);
			return false;
		}
		
		function createListAvaliacoes(data)
		{
			addChecks('avaliacaoCheck', data);
		}

		function getEstabelecimentos(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresa(createListEstabelecimentos, empresaId);
			return false;
		}
		
		function createListEstabelecimentos(data)
		{
			addChecks('estabelecimentoCheck', data);
		}

		function getAreasOrganizacionais(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(createListAreasOrganizacionais, empresaId, empresaIds, null);
			return false;
		}
		
		function createListAreasOrganizacionais(data)
		{
			addChecks('areasCheck', data);
		}

		function getColaboradores()
		{
			var avaliacoes = $("input[name='avaliacaoCheck']:checked");
			var avaliacoesIds = new Array(); 
			$(avaliacoes).each(function() {
				avaliacoesIds.push($(this).val());
			});
			
			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.getColaboradoresByAvaliacoes(createListColaboradorAvaliacoes, $(avaliacoesIds).toArray());
			return false;
		}
		
		function createListColaboradorAvaliacoes(data)
		{
			addChecks('colaboradorsCheck', data);
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="${action}" onsubmit="${validarCamposAvaliacaoDesempenho}" method="POST">
		
		<#list empresas as empresa>
			<input type="hidden" name="empresasPermitidas" value="${empresa.id}" />
		</#list>
		
		<@ww.datepicker label="Período" required="true" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
		<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/>
		<@ww.select label="Empresa" name="empresa.id" id="empresaId" listKey="id" listValue="nome" list="empresas" headerKey="" headerValue="Todas" cssClass="selectEmpresa"/>
		<@frt.checkListBox label="Avaliações*" name="avaliacaoCheck" id="avaliacaoCheck" list="avaliacaoCheckList" onClick="getColaboradores();" filtro="true"/>						
		<@frt.checkListBox label="Colaboradores" name="colaboradorsCheck" id="colaboradorsCheck" list="colaboradorsCheckList" filtro="true"/>
		<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList" filtro="true"/>						
		<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
		<@ww.checkbox label="Agrupar por área organizacional" name="agruparPorArea" labelPosition="right" /><br>
		
	</@ww.form>

	<div class="buttonGroupAvaliacaoDesempenho">
		<button class="btnRelatorio"  onclick="$('form[name=form]').attr('action', 'imprimeRelatorioRankingPerformancePeriodoDeExperiencia.action');${validarCamposAvaliacaoDesempenho};"></button>
		<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'imprimeRelatorioRankingPerformancePeriodoDeExperienciaXLS.action');${validarCamposAvaliacaoDesempenho};"></button>
	</div>
</body>
</html>