<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>

<@ww.head/>
	
	<#if comTreinamento>
		<title>Relatório de colaboradores que fizeram um treinamento</title>
	<#else>
		<title>Relatório de colaboradores que não fizeram um treinamento</title>
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
	
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#if comTreinamento>
		<#assign formAction = "relatorioColaboradorComTreinamento.action" />
	<#else>
		<#assign formAction = "relatorioColaboradorSemTreinamento.action" />
	</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('curso'), null)"/>
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		<@ww.select name="curso.id" id="curso" list="cursos" listKey="id" required="true" listValue="nome" label="Curso" headerKey="" headerValue="Selecione..."/>
		
		<@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1"  onchange="populaEstabelecimento(this.value);populaArea(this.value);" disabled="!compartilharColaboradores"/>		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"/>
	
		<#if comTreinamento>
			<@ww.select label="Aprovado" name="aprovado" list=r"#{'T':'Todos','S':'Sim','N':'Não'}" />
		</#if>
		<@ww.hidden name="comTreinamento"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio">
		</button>
	</div>
</body>
</html>