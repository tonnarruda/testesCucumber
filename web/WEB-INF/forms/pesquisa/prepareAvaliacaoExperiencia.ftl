<html>
<head>
<@ww.head/>
	<#if colaboradorQuestionario?exists && colaboradorQuestionario.id?exists>
		<title>Editar Avaliação do Período de Experiência</title>
		<#assign formAction="updateAvaliacaoExperiencia.action"/>
		<#assign desabilitarAvaliacaoSelect="true"/>
	<#else>
		<title>Inserir Avaliação do Período de Experiência</title>
		<#assign formAction="insertAvaliacaoExperiencia.action"/>
		<#assign desabilitarAvaliacaoSelect="false"/>
	</#if>
	<#if colaboradorQuestionario?exists && colaboradorQuestionario.respondidaEm?exists>
		<#assign data = colaboradorQuestionario.respondidaEm?date/>
	<#else>
		<#assign data = ""/>
	</#if>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/perguntasAvaliacao.js"/>'></script>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.div cssClass="divInfo" cssStyle="width: 500px;">
		<h4>Colaborador: ${colaborador.nome}</h4>
		<@ww.form name="formAvaliacao" action="prepareInsertAvaliacaoExperiencia.action" method="POST">
			<@ww.select label="Avaliação do Período de Experiência" name="colaboradorQuestionario.avaliacao.id" id="cidade" list="avaliacaoExperiencias" listKey="id" listValue="titulo" cssStyle="width: 245px;" headerKey="" headerValue="Selecione..."  onchange="this.form.submit();" disabled="${desabilitarAvaliacaoSelect}"/>
			<@ww.hidden name="colaboradorQuestionario.colaborador.id" />
		</@ww.form>
	</@ww.div><br/>	
	
	<#if perguntas?exists && 0 < perguntas?size>
		<@ww.form name="form" action="${formAction}" method="POST">
			<@ww.datepicker label="Data" id="data" name="colaboradorQuestionario.respondidaEm" required="true" cssClass="mascaraData" value="${data}"/>
			<#include "../avaliacao/includePerguntasAvaliacao.ftl" />
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="validaRespostas(new Array('data'), new Array('data'), true, true, false, false, true);" class="btnGravar"></button>
	<#else>
		<div class="buttonGroup">
	</#if>
			<button class="btnCancelar" onclick="window.location='periodoExperienciaQuestionarioList.action?colaborador.id=${colaborador.id}'"></button>		
		</div>
</body>
</html>