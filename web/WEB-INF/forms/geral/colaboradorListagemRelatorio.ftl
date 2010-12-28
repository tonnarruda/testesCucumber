<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Colaboradores</title>

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

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
		.grade
		{
			width: 100%;
		}
		.grade .grade_field {
			float: left;
			width: 32%;
			padding: 2px 3px;
		}	
	</style>

	
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
			return validaFormulario('form', new Array('@estabelecimentosCheck','@estabelecimentosCheck', '@areaOrganizacionalsCheck'), new Array('naoApague' ${validaDataCamposExtras}));
		}
	
		jQuery(document).ready(function($)
		{
			var empresa = jQuery('#empresa').val();
			
			populaArea(empresa);
			populaEstabelecimento(empresa);
		});
		
		var maxSize = 780;
		var totalSize = 0;
		var espace = 0;
		function verifySize(check)
		{
		
			if(check.checked)
    			totalSize += new Number(check.className) + espace; 
			else
    			totalSize -= (new Number(check.className) + espace); 
			
			if (totalSize > maxSize)
			{
				alert("campo exagerado");
				check.checked = false;
				totalSize -= (new Number(check.className) + espace);
			}
		}
		
	</script>


</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioDinamico.action" onsubmit="return validarCampos();" validate="true" method="POST">
		<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaEstabelecimento(this.value);populaArea(this.value);"/>
		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" width="600" />

		<@frt.checkListBox name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="600" />
		
		<#if habilitaCampoExtra>
			<fieldset class="fieldsetPadrao" style="width:583px;">
				<ul>
					<legend>Campos para impressão</legend>
					<div class='grade'>
						<#list colunas as coluna>
							<div class='grade_field'>
								<@ww.checkbox label="${coluna.name}" fieldValue="${coluna.property}" id = "${coluna.property}" name="colunasMarcadas" labelPosition="left" value="" cssClass="${coluna.size}"  onclick="verifySize(this);"/>
							</div>
						 </#list>
					</div>
				</ul>
			</fieldset>
		</#if>
		
		<@ww.hidden name="habilitaCampoExtra" />
		
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="return validarCampos();"></button>
	</div>
</body>
</html>