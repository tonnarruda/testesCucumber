<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Colaboradores por Grupo Ocupacional</title>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/GrupoOcupacionalDWR.js"/>'></script>
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
		DWREngine.setAsync(false);

		$(function() {
			var empresa = $('#empresa').val();
			
			populaArea();
			populaCargo(empresa);
			populaGrupo(empresa);
			populaEstabelecimento(empresa);
			
			$("input[name='areaOrganizacionalsCheck'], input[name='gruposCheck']").live('click', populaCargoByAreaGrupo);
		});
		
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
		
		function createListCargo(data)
		{
			addChecks('cargosCheck',data);
		}
		
		function populaArea()
		{
			AreaOrganizacionalDWR.getByEmpresas(createListArea, $('#empresa').val(), empresaIds, $('#ativa').val());
		}

		function createListArea(data)
		{
			addChecks('areaOrganizacionalsCheck',data);
		}
		
		function populaGrupo(empresaId)
		{
			GrupoOcupacionalDWR.getByEmpresas(createListGrupo, empresaId, empresaIds);
		}

		function createListGrupo(data)
		{
			addChecks('gruposCheck',data);
		}
		
		function populaCargoByAreaGrupo()
		{
			var empresaId = $('#empresa').val();
			var areaIds   = getArrayCheckeds(document.form, 'areaOrganizacionalsCheck');
			var grupoIds  = getArrayCheckeds(document.form, 'gruposCheck');
			
			if ($(areaIds).size() > 0 || $(grupoIds).size() > 0)
				CargoDWR.getCargosByAreaGrupo(createListCargo, areaIds, grupoIds, empresaId);
			else
				CargoDWR.getByEmpresas(createListCargo, empresaId, empresaIds);
		}
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
			<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaEstabelecimento(this.value);populaArea();"/>
		<#else>
			<@ww.hidden id="empresa" name="empresa.id"/>
			<li class="wwgrp">
				<label>Empresa:</label><br />
				<strong><@authz.authentication operation="empresaNome"/></strong>
			</li>
		</#if>
		
		<@ww.datepicker label="Data de Referência" id="data" name="data" required="true" cssClass="mascaraData" value="${dataTemp}"/><br>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" />
		<fieldset style="width:482px">
			<legend>Áreas Organizacionais</legend>
			<@ww.select id="ativa" name="ativa" list=r"#{'S':'Ativas','N':'Inativas'}" headerKey="T" headerValue="Ativas e inativas" onchange="populaArea();" cssStyle="width:475px"/>
			<@frt.checkListBox name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" onClick="populaCargoByAreaGrupo()" list="areaOrganizacionalsCheckList" width="475"/>
		</fieldset>
		<@frt.checkListBox name="gruposCheck" id="gruposCheck" onClick="populaCargoByAreaGrupo()" label="Grupos Ocupacionais" list="gruposCheckList" width="500" />
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" />
		<@ww.select label="Colocação do Colaborador" name="vinculo" id="vinculo" list="vinculos" headerKey="" headerValue="Todas" cssStyle="width: 180px;" />

	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="${validarCampos};"></button>
	</div>
</body>
</html>
