<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>

	<title>Ranking de Performance das Avaliações dos Alunos</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<#if periodoIni?exists>
		<#assign periodoIniFormatado = periodoIni?date/>
	<#else>
		<#assign periodoIniFormatado = ""/>
	</#if>
	<#if periodoFim?exists>
		<#assign periodoFimFormatado = periodoFim?date/>
	<#else>
		<#assign periodoFimFormatado = ""/>
	</#if>
	
	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('periodoIni','periodoFim'), new Array('periodoIni','periodoFim'))"/>
	
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

	<@ww.form name="form" action="" method="POST" onsubmit="${validarCampos}">
		<@ww.datepicker label="Período" required="true" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
		<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/>
		
		<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos" list="cursosCheckList" onClick="getTurmas();" />
		<@frt.checkListBox name="turmasCheck" id="turmasCheck" label="Cursos / Turmas" list="turmasCheckList" />
		<@frt.checkListBox name="avaliacaoCursosCheck" id="avaliacaoCursosCheck" label="Avaliações" list="avaliacaoCursosCheckList" />
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio"  onclick="${validarCampos}"></button>
	</div>
</body>
</html>