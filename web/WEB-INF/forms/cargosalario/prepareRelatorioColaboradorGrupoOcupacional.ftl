<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Colaboradores por Grupo Ocupacional</title>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/GrupoOcupacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
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
			AreaOrganizacionalDWR.getByEmpresas(createListArea, $('#empresa').val(), empresaIds, 'T');
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
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox label="Áreas Organizacionais" name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" onClick="populaCargoByAreaGrupo()" list="areaOrganizacionalsCheckList" filtro="true" selectAtivoInativo="true"/>
		<@frt.checkListBox name="gruposCheck" id="gruposCheck" onClick="populaCargoByAreaGrupo()" label="Grupos Ocupacionais" list="gruposCheckList" width="500" filtro="true"/>
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" filtro="true" selectAtivoInativo="true"/>
		<@ww.select label="Colocação do Colaborador" name="vinculo" id="vinculo" list="vinculos" headerKey="" headerValue="Todas" cssStyle="width: 180px;" />
		<@ww.select label="Exibir a coluna de " name="selectColuna" list=r"#{'A':'Área Organizacional','C':'Cargo'}"  cssStyle="width:180px"/>

	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="${validarCampos};"></button>
	</div>
</body>
</html>
