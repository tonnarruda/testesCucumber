<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Áreas Organizacionais</title>

	<#assign validaDataCamposExtras = ""/>
	<#if habilitaCampoExtra>
		<#list configuracaoCampoExtras as configuracaoCampoExtra>		

			<#if configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data1">
				<#assign validaDataCamposExtras = validaDataCamposExtras + ", 'data1', 'data1Fim'"/>
			</#if>
			<#if configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data2">
				<#assign validaDataCamposExtras = validaDataCamposExtras + ", 'data2', 'data2Fim'"/>
			</#if>
			<#if configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data3">
				<#assign validaDataCamposExtras = validaDataCamposExtras + ", 'data3', 'data3Fim'"/>
			</#if>

		</#list>
	</#if>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<script type="text/javascript">
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		function populaEstabelecimento(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId, empresaIds);
		}

		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
		
		function populaArea(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds);
		}

		function createListArea(data)
		{
			addChecks('areaOrganizacionalsCheck',data);
		}
	
		function validarCampos()
		{
			return validaFormulario('form', new Array(), new Array('naoApague' ${validaDataCamposExtras}));
		}
	
		$(document).ready(function($)
		{
			var empresa = $('#empresa').val();
			
			populaArea(empresa);
			populaEstabelecimento(empresa);
		});
		
	</script>


</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="gerarRelatorio.action" onsubmit="return validarCampos();" validate="true" method="POST">
		<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaEstabelecimento(this.value);populaArea(this.value);"/>
		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" width="600" />

		<@frt.checkListBox name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="600" />
		
		<#if habilitaCampoExtra>
			<fieldset class="fieldsetPadrao" style="width:583px;">
				<ul>
					<legend>Campos Extras</legend>
					<#include "camposExtrasFiltro.ftl" />
				</ul>
			</fieldset>
		</#if>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="return validarCampos();"></button>
	</div>
</body>
</html>