<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Colaboradores por Cargo</title>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		DWRUtil.useLoadingMessage('Carregando...');
		
		function populaEstabelecimento(empresaId)
		{
			EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId, empresaIds);
		}

		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
		
		function populaCargo(empresaId)
		{
			CargoDWR.getByEmpresas(createListCargo, empresaId, empresaIds);
		}
		
		function populaCargoByAreaGrupo(empresaId)
		{
			var areaIds;
			var grupoIds;
			
			CargoDWR.getByEmpresas(createListCargo, empresaId, empresaIds);
		}

		function createListCargo(data)
		{
			addChecks('cargosCheck',data);
		}
		
		function populaArea(empresaId)
		{
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds);
		}

		function createListArea(data)
		{
			addChecks('areaOrganizacionalsCheck',data);
		}
		
		$(document).ready(function($)
		{
			var empresa = $('#empresa').val();
			
			populaArea(empresa);
			populaCargo(empresa);
			populaEstabelecimento(empresa);
		});
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<#assign validarCampos="return validaFormulario('form', new Array('data'), new Array('data'))"/>
	<#if data?exists>
		<#assign dataTemp = data?date/>
	<#else>
		<#assign dataTemp = ""/>
	</#if>

	<@ww.form name="form" action="relatorioColaboradorGrupoOcupacional.action" onsubmit="${validarCampos}" validate="true" method="POST">
		
		<#if compartilharColaboradores>
			<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaEstabelecimento(this.value);populaArea(this.value);"/>
		<#else>
			<@ww.hidden id="empresa" name="empresa.id"/>
			<li class="wwgrp">
				<label>Empresa:</label><br />
				<strong><@authz.authentication operation="empresaNome"/></strong>
			</li>
		</#if>
		
		<@ww.datepicker label="Data de Referência" id="data" name="data" required="true" cssClass="mascaraData" value="${dataTemp}"/><br>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" />
		<@frt.checkListBox name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="500" />
		<@frt.checkListBox name="gruposCheck" id="gruposCheck" label="Grupos Ocupacionais" list="gruposCheckList" width="500" />
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" />
		<@ww.select label="Colocação do Colaborador" name="vinculo" id="vinculo" list="vinculos" headerKey="" headerValue="Todas" cssStyle="width: 180px;" />

	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="${validarCampos};"></button>
	</div>
</body>
</html>
