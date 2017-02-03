<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		function populaEstabelecimento(empresaId)
		{
			dwr.util.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(empresaId, empresaIds, createListEstabelecimento);
		}

		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
		
		function populaArea(empresaId)
		{
			dwr.util.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(empresaId, empresaIds, null, createListArea);
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
			dwr.engine.setAsync(false);
		
			var empresa = $('#empresa').val();
			
			populaArea(empresa);
			populaEstabelecimento(empresa);
		});
	</script>

	<#include "../ftl/mascarasImports.ftl" />

	<@ww.head/>
	<title>Aniversariantes do mês</title>

</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="relatorioAniversariantes.action" method="POST">
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
		<@ww.select label="Exibir" name="exibir" list=r"#{'A':'Área Organizacional','C':'Cargo'}"/>
		<@ww.checkbox labelPosition="right" label="Exibir nome comercial" name="exibirNomeComercial" />
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="return submeterAction('relatorioAniversariantes.action');" class="btnRelatorio" ></button>
		<button onclick="return submeterAction('relatorioAniversariantesXls.action');" class="btnRelatorioExportar"></button>
	</div>
</body>
</html>