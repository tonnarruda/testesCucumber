<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Colaboradores por Área Organizacional</title>

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
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/populaEstabAreaCargo.js?version=${versao}"/>"></script>
	
	<script type="text/javascript">
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		function validarCampos()
		{
			return validaFormulario('form', new Array(), new Array('naoApague' ${validaDataCamposExtras}));
		}
		
		$(function() {
			var empresa = $('#empresa').val();
	
			populaAreaComCargoVinculado(empresa);
			populaEstabelecimento(empresa);
			populaCargosByAreaVinculados(empresa);
			
			$('#cargosVinculadosAreas').click(function() {
				populaCargosByAreaVinculados();
			});
			
			$('#cargosVinculadosAreas').attr('checked', true);;
		});
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="gerarRelatorio.action" onsubmit="return validarCampos();" validate="true" method="POST">
		<#list empresas as empresa>
			<input type="hidden" name="empresasPermitidas" value="${empresa.id}" />
		</#list>

		<#if compartilharColaboradores>
			<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" cssClass="empresaSelect" headerValue="Todas" headerKey="" onchange="newChangeEmpresa(this.value);"/>
		<#else>
			<@ww.hidden id="empresa" name="empresa.id"/>
			<li class="wwgrp">
				<label>Empresa:</label><br />
				<strong><@authz.authentication operation="empresaNome"/></strong>
			</li>
		</#if>
		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" width="600" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areaCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="600" onClick="populaCargosByAreaVinculados();" filtro="true" selectAtivoInativo="true" />
		<@ww.checkbox label="Exibir somente os cargos vinculados às áreas organizacionais acima." id="cargosVinculadosAreas" name="" labelPosition="left"/>
		<@frt.checkListBox name="cargosCheck" label="Cargos" list="cargosCheckList" width="600" filtro="true" selectAtivoInativo="true" />
		
		<#if habilitaCampoExtra>
			<fieldset class="fieldsetPadrao" style="width:583px;">
				<ul>
					<legend>Campos Extras</legend>
					<#include "camposExtrasFiltro.ftl" />
				</ul>
			</fieldset>
		</#if>
		
		<@authz.authorize ifAllGranted="EXIBIR_SALARIO_RELAT_COLAB_CARGO">
			<@ww.checkbox label="Exibir Salário" name="exibirSalario" id="exibirSalario" labelPosition="left" cssStyle="margin-top: 10px;"/>
		</@authz.authorize>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="$('form[name=form]').attr('action', 'gerarRelatorio.action');return validarCampos();"></button>
		<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'gerarRelatorioXLS.action');return validarCampos();"></button>
	</div>
</body>
</html>