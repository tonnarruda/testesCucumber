<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Análise de Desempenho das Competências da Organização</title>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AvaliacaoDesempenhoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CompetenciaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<script type="text/javascript">
		$(function(){
			populaEstabelecimentos();
			populaCompetencias();
			mostraFiltros();

			$('#agrupamentoDasCompetencias').change(function() {
				populaListas();
				mostraFiltros();
			});
			$('#competenciasConsideradas').change(function() {
				populaCompetencias();
			});
		})

		function mostraMensagemCarregando()
		{
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
		}
		
		function mostraFiltros() 
		{
			var  agrupamentoDasCompetencias = $('#agrupamentoDasCompetencias').val();
			$('#divArea').toggle(agrupamentoDasCompetencias == '${analiseDesempenhoOrganizacaoPorArea}') ;
			$('#divCargo').toggle(agrupamentoDasCompetencias == '${analiseDesempenhoOrganizacaoPorCargo}');
		}
		
		function populaListas()
		{
			populaEstabelecimentos();
			populaCompetencias();
			
			if($('#agrupamentoDasCompetencias').val() == '${analiseDesempenhoOrganizacaoPorArea}') 
				populaAreasOrganizacionais();
			else if($('#agrupamentoDasCompetencias').val() == '${analiseDesempenhoOrganizacaoPorCargo}')
				populaCargos();
		}

		function getAvaliacoesIds()
		{
			var avaliacoesIds = $('input[name="avaliacoesCheck"]:checked').map(function(){return $(this).val();}).get();
			
			if(avaliacoesIds.length == 0 )
				avaliacoesIds = $('input[name="avaliacoesCheck"]').not(':checked').map(function(){return $(this).val();}).get();
				
			return avaliacoesIds;
		}
		
		function populaEstabelecimentos()
		{
			var avaliacoesIds = getAvaliacoesIds();
		
			mostraMensagemCarregando()
			AvaliacaoDesempenhoDWR.getEstabelecimentosDosParticipantes(createListEstabelecimentos, avaliacoesIds);
		}
		
		function createListEstabelecimentos(data)
		{
			addChecks('estabelecimentosCheck',data);
			addChecks('estabelecimentosCheckAux',data);
		}
		
		function populaAreasOrganizacionais()
		{
			var avaliacoesIds = getAvaliacoesIds();
		
			mostraMensagemCarregando()
			AvaliacaoDesempenhoDWR.getAreasOrganizacionaisDosParticipantes(createListAreasOrganizacionais, avaliacoesIds);
		}
		
		function createListAreasOrganizacionais(data)
		{
			addChecks('areasCheck',data);
			addChecks('areasCheckAux',data);
		}
		
		function populaCargos()
		{
			var avaliacoesIds = getAvaliacoesIds();
		
			mostraMensagemCarregando()
			AvaliacaoDesempenhoDWR.getCargosDosParticipantes(createListCargos, avaliacoesIds);
		}
		
		function createListCargos(data)
		{
			addChecks('cargosCheck',data);
			addChecks('cargosCheckAux',data);
		}
		
		function populaCompetencias()
		{
			var avaliacoesIds = getAvaliacoesIds();

			mostraMensagemCarregando()
			CompetenciaDWR.getByAvaliacoes(createListCompetencias, ${empresaSistema.id}, avaliacoesIds, $('#competenciasConsideradas').val());
		}
		
		function createListCompetencias(data)
		{
			addChecks('competenciasCheck',data);
			addChecks('competenciasCheckAux',data);
		}
		
		function gerarRelatorio(tipoRelatorio)
		{
			if($('#agrupamentoDasCompetencias').val() == '${analiseDesempenhoOrganizacaoPorArea}') 
				$('input[name="areasCheckAux"]').attr('checked','checked');
			else if($('#agrupamentoDasCompetencias').val() == '${analiseDesempenhoOrganizacaoPorCargo}')
				$('input[name="cargosCheckAux"]').attr('checked','checked');
				
			$('input[name="estabelecimentosCheckAux"]').attr('checked','checked');
			$('input[name="competenciasCheckAux"]').attr('checked','checked');
		
			if(tipoRelatorio == 'PDF')
				$('form[name=form]').attr('action', 'imprimeAnaliseDesempenhoCompetenciaOrganizacao.action');
			else
				$('form[name=form]').attr('action', 'imprimeAnaliseDesempenhoCompetenciaOrganizacaoXls.action');
				
			return validaFormulario('form', new Array('@avaliacoesCheck'), null);
		}
		
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form id="form" name="form" action="imprimeAnaliseDesempenhoCompetenciaOrganizacao.action" method="POST">
		<@ww.select label="Agrupamento das competências" name="agrupamentoDasCompetencias" id="agrupamentoDasCompetencias" list="listaAgrupamentoDasCompetencias" cssStyle="width: 600px;" onClick="mostraFiltros();"/>
		<@frt.checkListBox label="Avaliações de Desempenho" name="avaliacoesCheck" id="avaliacoesCheck" list="avaliacoesCheckList" width="600" onClick="populaListas();" filtro="true" required="true"/>
		<@frt.checkListBox label="Estabelecimentos" name="estabelecimentosCheck" id="estabelecimentosCheck" list="estabelecimentosCheckList"  width="600" filtro="true"/>
		<div id="divArea">
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList" width="600" filtro="true" selectAtivoInativo="true"/>
		</div>
		<div id="divCargo">
			<@frt.checkListBox label="Cargos" name="cargosCheck" id="cargosCheck" list="cargosCheckList"  width="600" filtro="true" selectAtivoInativo="true"/>
		</div>
		<fieldset style="padding: 5px 0px 5px 5px; width: 593px;">
			<legend>Competências consideradas no relatório</legend>
			<@ww.select label="Considerar" name="competenciasConsideradas" id="competenciasConsideradas" list="competenciasConsideradas" />
			<@frt.checkListBox label="Competências" id="competenciasCheck" name="competenciasCheck" list="competenciasCheckList" width="586" height="180" filtro="true"/>
		</fieldset>
		<!-- Estes campos são utilizados para enviar os ids dos registros, caso não marcados, para a action -->
		<div id="divCheckboxAuxiliares" style="display: none;">
			<@frt.checkListBox name="estabelecimentosCheckAux" id="estabelecimentosCheckAux" list="estabelecimentosCheckList" />
			<@frt.checkListBox name="areasCheckAux" id="areasCheckAux" list="areasCheckList" />
			<@frt.checkListBox name="cargosCheckAux" id="cargosCheckAux" list="cargosCheckList" />
			<@frt.checkListBox name="competenciasCheckAux" id="competenciasCheckAux" list="competenciasCheckList" />
		</div>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="gerarRelatorio('PDF');"></button>		
		<button class="btnRelatorioExportar" onclick="gerarRelatorio('XLS');"></button>
	</div>
</body>
</html>