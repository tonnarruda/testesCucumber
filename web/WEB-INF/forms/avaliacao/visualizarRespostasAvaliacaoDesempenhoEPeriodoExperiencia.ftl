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
	<#assign exibirPerformance=(mostrarPerformanceAvalDesempenho && !(avaliador.id == colaborador.id && !colaboradorQuestionario.avaliacaoDesempenho.exibeResultadoAutoAvaliacao)) />
	
	<#if !colaboradorQuestionario.avaliacao.id?exists || colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
		<#include "includeCompetenciasAvaliacaoHead.ftl" />
	</#if>
	
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
	<#if exibirPerformance && colaboradorQuestionario.avaliacao.id?exists >
		<pre id="performanceQuestionario" style="text-align:right; font-weight: bold;">Performance Questionário: - </pre>
	</#if>
	
	<@ww.form name="form" method="POST">
		<#if perguntas?exists && 0 < perguntas?size>
			<#include "includePerguntasAvaliacao.ftl" />
		</#if>
		<#if !colaboradorQuestionario.avaliacao.id?exists || colaboradorQuestionario.avaliacao.avaliarCompetenciasCargo>
			<#include "includeCompetenciasAvaliacaoBody.ftl" />
		</#if>
	</@ww.form>
	<div class="buttonGroup">
		<button class="btnFechar" onclick="window.close();"></button>		
	</div>

	<script type="text/javascript">
		$(function() {
			$(':checkbox').attr('disabled',true);
			$('select').attr('disabled',true);
			$(':radio:not(:checked)').attr('disabled', true);
			$('input[type="text"], textarea').attr('readonly','readonly');
		});
	</script>
</body>
</html>