<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<#if colaboradorQuestionario.avaliacaoDesempenho?exists && colaboradorQuestionario.avaliacaoDesempenho.id?exists >
		<title>Visualizar Avaliação de Desempenho</title>
	<#else>
		<title>Visualizar Acompanhamento do Período de Experiência</title>
	</#if>	
	<#assign respostasCompactas=colaboradorQuestionario.avaliacao.respostasCompactas />
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#if colaboradorQuestionario.avaliacaoDesempenho?exists && colaboradorQuestionario.avaliacaoDesempenho.titulo?exists >
		<b>${colaboradorQuestionario.avaliacaoDesempenho.titulo}</b><br/>
	</#if>	
	<#if colaboradorQuestionario.avaliador?exists && colaboradorQuestionario.avaliador.nome?exists >
		<b>Avaliador: ${avaliador.nome}</b><br/>
	</#if>	
	<b>Avaliado: ${colaborador.nome}</b>
	<#if colaboradorQuestionario.avaliacao?exists && colaboradorQuestionario.avaliacao.cabecalho?exists>
		<pre><h4>${colaboradorQuestionario.avaliacao.cabecalho}</h4></pre>
	</#if>
	
	<#if perguntas?exists && 0 < perguntas?size>
		<@ww.form name="form" method="POST">
			<#include "includePerguntasAvaliacao.ftl" />
		</@ww.form>
	</#if>
	<div class="buttonGroup">
		<button class="btnFechar" onclick="window.close();"></button>		
	</div>
</body>
</html>