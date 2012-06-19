<html>
<head>
<@ww.head/>

<title>Exportação de Treinamentos</title>
	<#assign validarCampos="return validaFormulario('form', new Array(''), null)"/>

	<#if dataDe?exists>
		<#assign dataDeTmp = dataDe?date/>
	<#else>
		<#assign dataDeTmp = ""/>
	</#if>
	<#if dataAte?exists>
		<#assign dataAteTmp = dataAte?date/>
	<#else>
		<#assign dataAteTmp = ""/>
	</#if>
	
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresas?exists>
			<#list empresas as empresa>
				empresaIds.push(${empresa.id});
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
			addChecks('areasCheck',data);
		}
		
		$(document).ready(function($)
		{
			var empresaValue = $('#empresaId').val();
			
			populaArea(empresaValue);
			populaEstabelecimento(empresaValue);
		});
	</script>
	
</head>



<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="gerarArquivoExportacao.action" validate="true" onsubmit="${validarCampos}" method="POST">
		<@ww.file label="Arquivo CSV" name="arquivo" id="arquivo"/>
		
		<@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1"  onchange="populaEstabelecimento(this.value);populaArea(this.value);" disabled="!compartilharColaboradores"/>		
		<@ww.select name="curso.id" id="curso" list="cursos" listKey="id" required="true" listValue="nome" label="Curso" headerKey="" headerValue="Selecione..."/>
		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"/>
		
		
		<@ww.token/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnCarregar"></button>
	</div>
</body>
</html>