<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Responder Avaliação de Desempenho</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/perguntasAvaliacao.js"/>'></script>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<b>${colaboradorQuestionario.avaliacaoDesempenho.titulo}</b><br/>
	<b>Avaliador: ${avaliador.nome}</b><br/>
	<b>Avaliado: ${colaborador.nome}</b>
	<#if colaboradorQuestionario.avaliacao?exists && colaboradorQuestionario.avaliacao.cabecalho?exists>
		<pre><h4>${colaboradorQuestionario.avaliacao.cabecalho}</h4></pre>
	</#if>
	<@authz.authorize ifAllGranted="ROLE_MOV_AVALIACAO">
	<@ww.form id="form_ordenacao" name="form_ordenacao" action="prepareResponderAvaliacaoDesempenho.action"	method="GET">
		<@ww.select label="Ordenar Por:" name="ordenarPorAspecto" list=r"#{true:'Aspecto',false:'Numeração'}" onchange="jQuery('#form_ordenacao').submit();"/>
		<@ww.hidden name="colaboradorQuestionario.id" />
	</@ww.form>
	</@authz.authorize>
	
	<#if perguntas?exists && 0 < perguntas?size>
		<@ww.form name="form" action="responderAvaliacaoDesempenho.action" method="POST">
			<#include "includePerguntasAvaliacao.ftl" />
			<@ww.hidden name="colaboradorQuestionario.id" />
			<@ww.hidden name="colaboradorQuestionario.respondidaEm" />
			<@ww.hidden name="colaboradorQuestionario.avaliacaoDesempenho.id" />
			<@ww.hidden name="colaboradorQuestionario.avaliador.id" />
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="validaRespostas(null, null, true, true, false, false, true);" class="btnGravar"></button>
	<#else>
		<div class="buttonGroup">
	</#if>
			<button class="btnCancelar" onclick="window.location='avaliacaoDesempenhoQuestionarioList.action?avaliacaoDesempenho.id=${colaboradorQuestionario.avaliacaoDesempenho.id}'"></button>		
		</div>
</body>
</html>