<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Análise de Desempenho das Competências do Colaborador</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AvaliacaoDesempenhoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<#assign validarCampos="return validaFormulario('form', new Array('avaliacao', 'avaliados'), null)"/>
	
	<script type="text/javascript">
	
		var empresaIds = new Array();
		<#if empresas?exists>
			<#list empresas as empresa>
				empresaIds.push(${empresa.id});
			</#list>
		</#if>
		
		function populaAvaliados()
		{
			var areasIds   = getArrayCheckeds(document.form, 'areasCheck');
			var cargosIds  = getArrayCheckeds(document.form, 'cargosCheck');
			
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			AvaliacaoDesempenhoDWR.getAvaliados(populaSelectAvaliados, $('#avaliacao').val(), ${empresaSistema.id}, areasIds, cargosIds);
		}
			
		function populaSelectAvaliados(data)
		{
			$('#avaliados').find('option').remove().end();
			if(data != "")
				addOptionsByCollection('avaliados', data, 'Selecione...');
			else
				$('#avaliados').append('<option value="" selected="selected">Não existem avaliados para o filtro informado.</option>');
		}
		
		function populaArea(empresaId)
		{
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds, null);
		}
		
		function createListArea(data)
		{
			addChecks('areasCheck',data, 'populaCargosByAreaVinculados();populaAvaliados();');
		}
	
		function populaCargosByAreaVinculados()
		{
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
			var empresaId = ${empresaSistema.id};
			
			if ($('#cargosVinculadosAreas').is(":checked") && areasIds.length != 0)
				CargoDWR.getCargoByArea(createListCargosByArea, areasIds, "getNomeMercadoComEmpresa", empresaId);
			else
				CargoDWR.getByEmpresas(createListCargosByArea, empresaId, empresaIds);
		}
		
		function createListCargosByArea(data)
		{
			addChecks('cargosCheck',data, 'populaAvaliados()');
		}
	
		$(document).ready(function($){
			var empresa = ${empresaSistema.id};
	
			populaArea(empresa);
			populaCargosByAreaVinculados();
			populaAvaliados();
			
			$('#cargosVinculadosAreas').click(function() {
				populaCargosByAreaVinculados();
			});
			
			$('#cargosVinculadosAreas').attr('checked', true);
		});
	</script>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="analiseDesempenhoCompetenciaColaborador.action" onsubmit="${validarCampos}" method="POST">
		<@ww.select label="Avaliação de Desempenho" required="true" name="avaliacaoDesempenho.id" id="avaliacao" list="avaliacaoDesempenhos" listKey="id" listValue="titulo" cssStyle="width: 600px;" headerKey="" headerValue="Selecione..." onchange="populaAvaliados();"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" width="600" onClick="populaCargosByAreaVinculados();populaAvaliados();" filtro="true" selectAtivoInativo="true"/>
		<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais acima." id="cargosVinculadosAreas" name="" labelPosition="left"/>
		<@frt.checkListBox label="Cargos" name="cargosCheck" id="cargosCheck" list="cargosCheckList"  width="600" onClick="populaAvaliados();" filtro="true" selectAtivoInativo="true"/>
		<@ww.select label="Avaliado" required="true" name="avaliado.id" id="avaliados" list="participantesAvaliadores" listKey="id" listValue="nome" cssStyle="width: 600px;" headerKey="-1" headerValue="Selecione uma avaliação de desempenho"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>		
	</div>
</body>
</html>