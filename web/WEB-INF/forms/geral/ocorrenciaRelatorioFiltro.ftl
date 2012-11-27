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
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/OcorrenciaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
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
			
			ColaboradorDWR.getByAreaEstabelecimentoEmpresas(createListcolaborador, areasIds, estabelecimentoIds, empresaId, empresaIds);
		}

		function createListcolaborador(data)
		{
			addChecks('colaboradorCheck',data)
		}
		
		function populaEstabelecimento(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(function(data){createListEstabelecimento(data, empresaId);
									}, empresaId, empresaIds);
		}

		function createListEstabelecimento(data, empresaId)
		{
			addChecks('estabelecimentoCheck',data, "populaColaboradores(" + empresaId + ");");
		}
		
		function populaOcorrencia(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			OcorrenciaDWR.getByEmpresas(createListOcorrencia, empresaId, empresaIds);
		}

		function createListOcorrencia(data)
		{
			addChecks('ocorrenciaCheck',data);
		}
		
		function populaArea(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(function(data){createListArea(data, empresaId);
												}, empresaId, empresaIds);
		}

		function createListArea(data, empresaId)
		{
			addChecks('areaCheck', data, "populaColaboradores(" + empresaId + ");");
		}
		
		function populaChecks(empresa)
		{
			$('#wwctrl_areaCheck [type="checkbox"]').attr('checked', false);
			$('#wwctrl_estabelecimentoCheck [type="checkbox"]').attr('checked', false);
			
			populaArea(empresa);
			populaEstabelecimento(empresa);
			populaColaboradores(empresa);
			populaOcorrencia(empresa);
		}
		
		$(function()
		{
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
				
			var empresa = $('#empresa').val();
			populaChecks(empresa);
		});
	</script>
</head>
<body>
	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('dataPrevIni','dataPrevFim'), new Array('dataPrevIni','dataPrevFim'))"/>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<#if compartilharColaboradores>
			<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaChecks(this.value);"/>
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
		<@frt.checkListBox name="ocorrenciaCheck" label="Ocorrência" list="ocorrenciaCheckList" width="500" height="120"/>
		
		<@frt.checkListBox name="estabelecimentoCheck" label="Estabelecimento" list="estabelecimentoCheckList" width="500" height="120" onClick="populaColaboradores($('#empresa').val());"/>
		<@frt.checkListBox name="areaCheck" label="Área Organizacional" list="areaCheckList" width="500" height="120" onClick="populaColaboradores($('#empresa').val());"/>
		<@frt.checkListBox name="colaboradorCheck" label="Colaborador" list="colaboradorCheckList" width="500" height="180"/>

		<@ww.checkbox label="Detalhado" id="detalhe" labelPosition="left" name="detalhamento"/>
		<@ww.select label="Agrupar Por:" name="agruparPorColaborador" list=r"#{true:'Colaborador',false:'Providência'}" id="agruparPorColaborador"/>
		<@ww.checkbox label="Exibir Providências"  id="providencia" labelPosition="left" name="exibirProvidencia"/>
		
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
		<button onclick="window.location='list.action'" class="btnVoltar" ></button>
	</div>
<br><br>

</body>
</html>