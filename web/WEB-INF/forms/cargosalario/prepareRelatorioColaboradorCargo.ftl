<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Colaboradores por Cargo</title>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
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
		
		function populaCargo(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			CargoDWR.getByEmpresas(createListCargo, empresaId, empresaIds);
		}

		function createListCargo(data)
		{
			addChecks('cargosCheck',data);
		}
		
		jQuery(document).ready(function($)
		{
			var empresa = jQuery('#empresa').val();
			
			populaCargo(empresa);
			populaEstabelecimento(empresa);
		});
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<#assign validarCampos="return validaFormulario('form', new Array('data','@estabelecimentosCheck'), new Array('data'))"/>
	<#if data?exists>
		<#assign dataTemp = data?date/>
	<#else>
		<#assign dataTemp = ""/>
	</#if>

	<@ww.form name="form" action="relatorioColaboradorCargo.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaEstabelecimento(this.value);populaCargo(this.value);"/>
		
		<@ww.datepicker label="Data de Referência" id="data" name="data" required="true" cssClass="mascaraData" value="${dataTemp}"/><br>
		Exibir apenas colaboradores admitidos a mais de <@ww.textfield theme="simple" name="qtdMeses" id="qtdMeses" cssStyle="width:30px; text-align:right;" /> meses considerando a data 
		<@ww.select theme="simple" name="opcaoFiltro" list=r"#{'0':'Atual','1':'de Referência'}"/><br><br>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" />
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" />
		
		<@ww.checkbox label="Exibir relatório resumido" name="relatorioResumido" labelPosition="left"/>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="${validarCampos};"></button>
	</div>
</body>
</html>
