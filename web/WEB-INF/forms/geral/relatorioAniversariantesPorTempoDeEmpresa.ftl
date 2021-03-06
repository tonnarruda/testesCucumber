<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript'>
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
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds, null);
		}

		function createListArea(data)
		{
			addChecks('areasCheck',data);
		}
		
		function submeterAction(action)
		{
			$('form[name=form]').attr('action', action);
			return validaFormulario('form', null, null);
		}
		
		$(document).ready(function($)
		{
			DWREngine.setAsync(false);
		
			var empresa = $('#empresa').val();
			
			populaArea(empresa);
			populaEstabelecimento(empresa);
		});
	</script>

	<#include "../ftl/mascarasImports.ftl" />

	<@ww.head/>
	<title>Aniversariantes por tempo de empresa</title>

</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="relatorioAniversariantesPorTempoDeEmpresa.action" method="POST">
		<#list empresas as empresa>
			<input type="hidden" name="empresasPermitidas" value="${empresa.id}" />
		</#list>
		
		<#if compartilharColaboradores>
			<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaEstabelecimento(this.value);populaArea(this.value);"/>
		<#else>
			<@ww.hidden id="empresa" name="empresa.id"/>
			<li class="wwgrp">
				<label>Empresa:</label><br />
				<strong><@authz.authentication operation="empresaNome"/></strong>
			</li>
		</#if>
		
		<@ww.select label="Mês" name="mes" list="meses"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
		<@ww.checkbox labelPosition="right" label="Agrupar por Área Organizacional" name="agruparPorArea" />
		<@ww.checkbox labelPosition="right" label="Exibir nome comercial" name="exibirNomeComercial" />
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="return submeterAction('relatorioAniversariantesPorTempoDeEmpresa.action');" class="btnRelatorio" ></button>
		<button onclick="return submeterAction('relatorioAniversariantesPorTempoDeEmpresaXLS.action');" class="btnRelatorioExportar"></button>
	</div>
</body>
</html>