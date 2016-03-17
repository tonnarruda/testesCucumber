<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

	<title>Ocorrências</title>
	<#assign formAction="buscaOcorrencia.action"/>
	<#assign accessKey="F"/>

	<#if dataIni?exists>
		<#assign valueDataIni = dataIni?date/>
	<#else>
		<#assign valueDataIni = ""/>
	</#if>

	<#if dataFim?exists>
		<#assign valueDataFim = dataFim?date/>
	<#else>
		<#assign valueDataFim = ""/>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/OcorrenciaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		function populaColaboradores(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0], 'areaCheck');
			var estabelecimentoIds = getArrayCheckeds(document.forms[0], 'estabelecimentoCheck');
			var situacao = $('#situacao').val();
			
			ColaboradorDWR.getByAreaEstabelecimentoEmpresasResponsavel(createListcolaborador, ${usuarioLogado.id}, areasIds, estabelecimentoIds, empresaId, empresaIds, situacao, true);
		}

		function createListcolaborador(data)
		{
			addChecksByMap('colaboradorCheck',data)
		}
		
		function populaEstabelecimento(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(function(data){createListEstabelecimento(data, empresaId);
									}, empresaId, empresaIds);
		}

		function createListEstabelecimento(data, empresaId)
		{
			addChecksByMap('estabelecimentoCheck',data, 'populaColaboradores()');
		}
		
		function populaOcorrencia(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			OcorrenciaDWR.getByEmpresas(createListOcorrencia, empresaId, empresaIds);
		}

		function createListOcorrencia(data)
		{
			addChecksByMap('ocorrenciaCheck',data);
		}
		
		function populaArea(empresaId)
		{
			console.log(empresaId);
		
			DWRUtil.useLoadingMessage('Carregando...');
			
			AreaOrganizacionalDWR.getAreasPermitidas(function(data){createListArea(data, empresaId);}, ${usuarioLogado.id}, empresaId, empresaIds);
		}

		function createListArea(data, empresaId)
		{
			addChecksByMap('areaCheck', data, 'populaColaboradores()');
		}
		
		function populaChecks()
		{
			$('#wwctrl_areaCheck [type="checkbox"]').attr('checked', false);
			$('#wwctrl_estabelecimentoCheck [type="checkbox"]').attr('checked', false);
			
			var empresaId = $('#empresa').val() == 0 ? null : $('#empresa').val() ;
			
			DWREngine.setAsync(false);
			
			populaArea(empresaId);
			populaEstabelecimento(empresaId);
			populaColaboradores(empresaId);
			populaOcorrencia(empresaId);
		}
		
		function validarCampos(tipo)
		{
			$('#tipo').val(tipo);
		
			return validaFormularioEPeriodo('form', new Array('dataPrevIni','dataPrevFim'), new Array('dataPrevIni','dataPrevFim'));
		}
		
		$(function()
		{
			$('#situacao').change(function(){
			
			});
			
			$('#detalhe').click(function() {
			  	if($(this).attr('checked'))
			    {
			 		$('#providencia').removeAttr('disabled');
			 		$('#agruparPorColaborador').removeAttr('disabled');
			 	}
			 	else
			 	{
			 		$('#providencia').attr("disabled", true);
					$('#agruparPorColaborador').attr("disabled", true);
				}
			});
			
			$('#agruparPorColaborador').click(function() {
			  	if($(this).val() == 'true')
			 		$('#providencia').removeAttr('disabled');
			 	else
					$('#providencia').attr("disabled", true);
			});
				
			populaChecks();
		});
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.hidden id="tipo" name="tipo"/>
		
		<#if compartilharColaboradores>
			<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaChecks();"/>
		<#else>
			<@ww.hidden id="empresa" name="empresa.id"/>
			<li class="wwgrp">
				<label>Empresa:</label><br />
				<strong><@authz.authentication operation="empresaNome"/></strong>
			</li>
		</#if>

		<div>Período*:</div>
		<@ww.datepicker name="dataIni" id="dataPrevIni" liClass="liLeft" value="${valueDataIni}" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft"/>
		<@ww.datepicker name="dataFim" id="dataPrevFim" value="${valueDataFim}" cssClass="mascaraData validaDataFim"/>
		<@frt.checkListBox name="ocorrenciaCheck" label="Ocorrência" list="ocorrenciaCheckList" width="500" height="120"  filtro="true"/>
		
		<@frt.checkListBox name="estabelecimentoCheck" label="Estabelecimento" list="estabelecimentoCheckList" width="500" height="120" onClick="populaColaboradores($('#empresa').val());" filtro="true"/>
		<@frt.checkListBox name="areaCheck" label="Área Organizacional" list="areaCheckList" width="500" height="120" onClick="populaColaboradores($('#empresa').val());" filtro="true" selectAtivoInativo="true"/>
		
		<fieldset style="padding: 5px 0px 5px 5px; width: 495px;">
			<legend>Colaboradores</legend>
			<@ww.select label="Situação" name="situacao" id="situacao" list="situacaos" onchange="populaColaboradores($('#empresa').val());"/>
			<@frt.checkListBox id="colaboradorCheck" name="colaboradorCheck" label="Colaborador" list="colaboradorCheckList" width="487" height="180" filtro="true"/>
		</fieldset>
		<br />
		<@ww.checkbox label="Detalhado" id="detalhe" labelPosition="left" name="detalhamento"/>
		
		<@ww.select label="Agrupar Por" name="agruparPorColaborador" list=r"#{true:'Colaborador',false:'Providência'}" id="agruparPorColaborador"/>
		<@ww.checkbox label="Exibir Providências"  id="providencia" labelPosition="left" name="exibirProvidencia"/>
	</@ww.form>

	<div class="buttonGroup">
		<button type="button" onclick="validarCampos('P')" class="btnRelatorio"></button>
		<button type="button" onclick="validarCampos('X')" class="btnRelatorioExportar"></button>
		<button type="button" onclick="window.location='list.action'" class="btnVoltar" ></button>
	</div>
<br><br>

</body>
</html>