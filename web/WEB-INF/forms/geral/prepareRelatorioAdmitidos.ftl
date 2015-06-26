<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Admitidos</title>

	<#include "../ftl/mascarasImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

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
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds, null);
		}

		function createListArea(data)
		{
			addChecks('areasCheck',data);
		}
				
		$(document).ready(function($)
		{
			var empresa = $('#empresa').val();
			
			populaArea(empresa);
			populaEstabelecimento(empresa);
		});
	</script>
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

	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('dataIni', 'dataFim', '@estabelecimentosCheck'), new Array('dataIni', 'dataFim'))"/>
	
</head>

<body>
	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioAdmitidos.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<#if compartilharColaboradores>
			<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="0" onchange="populaEstabelecimento(this.value);populaArea(this.value);"/>
		<#else>
			<@ww.hidden id="empresa" name="empresa.id"/>
			<li class="wwgrp">
				<label>Empresa:</label><br />
				<strong><@authz.authentication operation="empresaNome"/></strong>
			</li>
		</#if>

		<@ww.select label="Colocação" name="vinculo" list="vinculos" cssStyle="width: 150px;" id="vinculo" headerKey="" headerValue="Todas" />
		
		<div>Período*:</div>
		<@ww.datepicker name="dataIni" id="dataIni" liClass="liLeft" value="${valueDataIni}"  cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft"/>
		<@ww.datepicker name="dataFim" id="dataFim"  value="${valueDataFim}" cssClass="mascaraData validaDataFim"/>
		<@frt.checkListBox label="Estabelecimentos*" name="estabelecimentosCheck" id="estabelecimentoCheck" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areaCheck" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
		
		<@ww.select label="Exibir apenas colaboradores ativos" id="exibirSomenteAtivos" name="exibirSomenteAtivos" list=r"#{true:'Sim',false:'Não'}" cssStyle="width: 96px;"/>
		
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="$('form[name=form]').attr('action', 'relatorioAdmitidos.action');${validarCampos};"></button>
		<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'relatorioAdmitidosXLS.action');${validarCampos};"></button>
	</div>
</body>
</html>