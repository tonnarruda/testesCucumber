<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Responder Avaliação do Aluno</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/perguntasAvaliacao.js"/>'></script>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		.pergunta { padding: 5px; }
		.dados th:first-child { text-align: left; padding-left: 5px; }
	</style>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<b>${avaliacao.titulo}</b><br/>
	<b>Aluno: ${colaborador.nome}</b><br/>
	<#if avaliacao?exists && avaliacao.cabecalho?exists>
		<pre><h4>${avaliacao.cabecalho}</h4></pre>
	</#if>
	
	<#if perguntas?exists && 0 < perguntas?size>
		<@ww.form name="form" action="responderAvaliacaoAluno.action" method="POST">
			<#include "../avaliacao/includePerguntasAvaliacao.ftl" />
			<@ww.hidden name="avaliacao.id" />
			<@ww.hidden name="colaboradorQuestionario.avaliacao.id" />
			<@ww.hidden name="colaboradorQuestionario.colaborador.id" />
			<@ww.hidden name="turmaId" />
			<@ww.hidden name="cursoId" />
			<@ww.hidden name="avaliacaoCursoId" />
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="validaRespostas(null, null, true, true, false, false, true)" class="btnGravar"></button>
	<#else>
		<div class="buttonGroup">
	</#if>
			<button class="btnCancelar" onclick="window.location='${urlVoltar}'"></button>
		</div>
</body>
</html>