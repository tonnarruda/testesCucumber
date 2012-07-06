<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

<title>Exportar Curso/Turma como ocorrência para o TRU</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<#assign validarCampos="return validaFormulario('form', new Array('@cursosCheck'), null)"/>
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type='text/javascript'>
		$(function(){
			populaFiltros($('#empresaId').val());
			
			$('#empresaId').change(function(){
				populaFiltros($(this).val());
			});
		});
		
		function populaFiltros(empresaId)
		{
			populaEstabelecimento(empresaId);
			populaCurso(empresaId);
			populaArea(empresaId);
		}
		
		function populaEstabelecimento(empresaId)
		{
			DWREngine.setAsync(false);
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresa(createListEstabelecimento, empresaId);
		}
	
		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
			
		function populaArea(empresaId)
		{
			DWREngine.setAsync(false);
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresa(createListArea, empresaId);
		}

		function createListArea(data)
		{
			addChecks('areasCheck',data);
		}
		
		function populaCurso(empresaId)
		{
			DWREngine.setAsync(false);
			DWRUtil.useLoadingMessage('Carregando...');
			
			CursoDWR.getCursosByEmpresa(populaCursos, empresaId);
		}

		function populaCursos(data)
		{
			addChecks('cursosCheck', data);
		}
	</script>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="gerarArquivoExportacao.action" validate="true" onsubmit="${validarCampos}" method="POST" >
		<@ww.select label="Empresas Integradas" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" />		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos"  list="estabelecimentosCheckList" width="600"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"  width="600"/>
		<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos" list="cursosCheckList"  width="600" />
	</@ww.form>
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnExportar"></button>
	</div>
</body>
</html>