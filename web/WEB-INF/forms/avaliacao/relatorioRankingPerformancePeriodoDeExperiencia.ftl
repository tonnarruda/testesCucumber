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

					
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AvaliacaoDesempenhoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript">
		$(function() {
			$('#empresaId').change(function() {
										$('#listCheckBoxcolaboradorsCheck').empty();
			 
										getAvaliacoes(this.value); 
										getEstabelecimentos(this.value);
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
			AreaOrganizacionalDWR.getByEmpresa(createListAreasOrganizacionais, empresaId);
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
		
		<@ww.datepicker label="Período" required="true" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
		<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/>
		<@ww.select label="Empresa" name="empresa.id" id="empresaId" listKey="id" listValue="nome" list="empresas" headerKey="-1" headerValue="Todas" cssClass="selectEmpresa"/>
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