<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>

	<title>Ranking de Performance das Avaliações dos Alunos</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type="text/javascript">
		function getTurmas()
		{
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			
			var cursoIds = getArrayCheckeds(document.forms[0], 'cursosCheck');
			
			TurmaDWR.getTurmasByCursos(populaTurmas, cursoIds);
			CursoDWR.getAvaliacaoCursos(populaAvaliacaoCursos, cursoIds);
		}

		function populaTurmas(data)
		{
			addChecks('turmasCheck', data);
		}
		
		function populaAvaliacaoCursos(data)
		{
			addChecksByCollection('avaliacaoCursosCheck', data, 'titulo');
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioRankingAvaliacaoAluno.action" method="POST">
		<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos" list="cursosCheckList" onClick="getTurmas();" filtro="true"/>
		<@frt.checkListBox name="turmasCheck" id="turmasCheck" label="Cursos / Turmas" list="turmasCheckList" filtro="true"/>
		<@frt.checkListBox name="avaliacaoCursosCheck" id="avaliacaoCursosCheck" label="Avaliações" list="avaliacaoCursosCheckList" filtro="true"/>
	
		<div class="buttonGroup">
			<button type="submit" class="btnRelatorio"></button>
		</div>
	</@ww.form>
</body>
</html>