<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Resultado da Avaliação de Desempenho (${avaliacaoDesempenho.titulo})</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AvaliacaoDesempenhoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	
	<#assign validarCampos="return validaFormulario('form', new Array('@colaboradorsCheck'), null)"/>
	
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
			
			dwr.engine.setAsync(true);
			dwr.util.useLoadingMessage('Carregando...');
			AvaliacaoDesempenhoDWR.getParticipantesByAvalEmpresaAreaCargo($('#avaliacaoDesempenhoId').val(), $('#empresa').val(), areasIds, cargosIds, populaListAvaliados);
		}
		
		function populaListAvaliados(data)
		{
			addChecks('colaboradorsCheck',data);
		}
		
		function populaArea(empresaId)
		{
			dwr.util.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(empresaId, empresaIds, null, createListArea);
		}
		
		function createListArea(data)
		{
			addChecks('areasCheck',data, 'populaCargosByAreaVinculados();populaAvaliados();');
		}
				
		function filtrarOpt(opcao)
		{
			if (opcao == 'avaliador')
				$('#opcoesDoRelatorio').hide();
			else
				$('#opcoesDoRelatorio').show();
		}
	
		function verificaCargoSemAreaRelacionada(empresaId)
		{
			CargoDWR.verificaCargoSemAreaRelacionada(empresaId, exibeCheckCargoSemArea);
		}
		
		function exibeCheckCargoSemArea(data)
		{
			$('#wwgrp_cargoSemArea').toggle(data);
		}
	
		function populaCargosByAreaVinculados()
		{
			dwr.util.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
			var empresaId = $('#empresa').val();
			
			if ($('#cargosVinculadosAreas').is(":checked") && areasIds.length != 0)
				CargoDWR.getCargoByArea(areasIds, "getNomeMercadoComEmpresa", empresaId, createListCargosByArea);
			else
				CargoDWR.getByEmpresas(empresaId, empresaIds, createListCargosByArea);
		}
		
		function createListCargosByArea(data)
		{
			addChecks('cargosCheck',data, 'populaAvaliados()');
		}
	
		function changeEmpresaAvaldesempenho(value)
		{
			populaAvaliados();
			populaArea(value);
			populaCargosByAreaVinculados();
			verificaCargoSemAreaRelacionada(value);
		}
	
		$(document).ready(function($){
			var opcaoResultado = $('#opcaoResultado');
			filtrarOpt(opcaoResultado.val());
			
			var empresa = $('#empresa').val();
	
			populaArea(empresa);
			populaCargosByAreaVinculados(empresa);
			
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
	
	<@ww.form name="form" action="resultado.action" onsubmit="${validarCampos}" method="POST">
		
		<@ww.select label="Empresa" name="empresaId" id="empresa" list="empresas" listKey="id" listValue="nome" headerKey="" headerValue="Todas" onchange="changeEmpresaAvaldesempenho(this.value);" />
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" width="600" onClick="populaCargosByAreaVinculados();populaAvaliados();" filtro="true" selectAtivoInativo="true"/>
		<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais acima." id="cargosVinculadosAreas" name="" labelPosition="left"/>
		<@frt.checkListBox label="Cargo / Função Pretendida" name="cargosCheck" id="cargosCheck" list="cargosCheckList"  width="600" onClick="populaAvaliados();" filtro="true" selectAtivoInativo="true"/>
		
		<@frt.checkListBox id="colaboradorsCheck" name="colaboradorsCheck" label="Avaliados *" list="colaboradorsCheckList" width="600" filtro="true" />
		<@ww.select label="Resultado" required="true" name="opcaoResultado" id="opcaoResultado" list=r"#{'avaliador':'Resultado por Avaliador', 'criterio':'Resultado por Perguntas'}" onchange="filtrarOpt(this.value);"/>
		
		<div id="opcoesDoRelatorio">
			<@ww.checkbox label="Exibir observações dos avaliadores" id="exibirObsAvaliadores" name="exibirObsAvaliadores" labelPosition="left"/>
			<@ww.checkbox label="Exibir todas as respostas" id="exibirRespostas" name="exibirRespostas" labelPosition="left"/>
			<@ww.checkbox label="Exibir comentários" id="exibirComentarios" name="exibirComentarios" labelPosition="left"/>
			<@ww.checkbox label="Agrupar perguntas por aspecto" id="agruparPorAspectos" name="agruparPorAspectos" labelPosition="left"/>
			<@ww.checkbox label="Desconsiderar auto-avaliação" id="desconsiderarAutoAvaliacao" name="desconsiderarAutoAvaliacao" labelPosition="left"/>
		</div>
		
		<@ww.hidden id="avaliacaoDesempenhoId" name="avaliacaoDesempenho.id"  />
		<@ww.hidden name="avaliacaoDesempenho.titulo"  />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>		
		<button onclick="window.location='list.action'" class="btnVoltar"></button>
	</div>
</body>
</html>