<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

<title>Exportar Curso/Turma como ocorrência para o TRU</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js?version=${versao}"/>'></script>
	
	<#assign validarCampos="return validaFormulario('form', new Array('@cursosCheck'), null)"/>
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type='text/javascript'>
		$(function(){
			var turmasCheck;
		
			populaFiltros($('#empresaId').val());
			
			$('#empresaId').change(function(){
				populaFiltros($(this).val());
			});
			
		});
		
		function populaFiltros(empresaId)
		{
			dwr.engine.setAsync(false);
			populaEstabelecimento(empresaId);
			populaArea(empresaId);
			populaCurso(empresaId);
			populaTurma();
		}
		
		function populaEstabelecimento(empresaId)
		{
			dwr.engine.setAsync(false);
			dwr.util.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresa(empresaId, createListEstabelecimento);
		}
	
		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
			
		function populaArea(empresaId)
		{
			
			dwr.util.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresa(empresaId, createListArea);
		}

		function createListArea(data)
		{
			addChecks('areasCheck',data);
		}
		
		function populaCurso(empresaId)
		{
			dwr.engine.setAsync(false);
			dwr.util.useLoadingMessage('Carregando...');
			
			CursoDWR.getCursosByEmpresa(empresaId, populaCursos);
		}

		function populaCursos(data)
		{
			addChecks('cursosCheck', data, 'populaTurma();');
		}
		
		function populaTurma()
		{
			turmasCheck = $("input[name=turmasCheck]:checked").map(function(){ return $(this) });
			dwr.util.useLoadingMessage('Carregando...');
			var cursoIds = getArrayCheckeds(document.forms[0], 'cursosCheck');
			TurmaDWR.getTurmasByCursos(cursoIds, populaTurmas);
		}

		function populaTurmas(data)
		{
			addChecks('turmasCheck', data);
			
			$(turmasCheck).each(function(){
				$("#" + $(this).attr("id") ).attr("checked", "checked");
			});
		}
	</script>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="gerarArquivoExportacao.action" validate="true" onsubmit="${validarCampos}" method="POST" >
		<@ww.select label="Empresas Integradas" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" />		
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos"  list="estabelecimentosCheckList" width="600" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"  width="600" filtro="true" selectAtivoInativo="true"/>
		<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos*" list="cursosCheckList"  width="600" onClick="populaTurma();" filtro="true"/>
		<@frt.checkListBox name="turmasCheck" id="turmasCheck" label="Cursos / Turmas" list="turmasCheckList" width="600" filtro="true"/>
		<@ww.select label="Considerar como ocorrência somente os dias da" name="considerarSomenteDiasPresente" list=r"#{false:'Turma',true:'Presença'}" cssStyle="width: 320px;"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnExportar"></button>
	</div>
</body>
</html>