<html>
<head>
<@ww.head/>
	<#if colaboradorQuestionario?exists && colaboradorQuestionario.id?exists>
		<title>Editar Acompanhamento do Período de Experiência</title>
		<#assign desabilitarAvaliacaoSelect="true"/>

		<#if colaborador?exists && colaborador.id?exists>
			<#assign formAction="updateAvaliacaoExperiencia.action"/>
		<#else>
			<#assign formAction="updateAvaliacaoSolicitacao.action"/>
		</#if>
	<#else>
		<title>Inserir Acompanhamento do Período de Experiência</title>
		<#assign desabilitarAvaliacaoSelect="false"/>
		
		<#if colaborador?exists && colaborador.id?exists>
			<#assign formAction="insertAvaliacaoExperiencia.action"/>
		<#else>
			<#assign formAction="insertAvaliacaoSolicitacao.action"/>
		</#if>
	</#if>

	<#if respostaColaborador>
		<#assign desabilitarAvaliacaoSelect="true"/>
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
	
		<#if colaborador?exists && colaborador.id?exists>
			<h4>Colaborador: ${colaborador.nome}</h4>
		<#else>
			<h4>
				Candidato: ${candidato.nome}<br>
				Avaliação: ${colaboradorQuestionario.avaliacao.titulo}
			</h4>
		</#if>
	
		<#if colaborador?exists && colaborador.id?exists>
			<@ww.form name="formAvaliacao" action="prepareInsertAvaliacaoExperiencia.action" method="POST">
				<@ww.select label="Avaliação do Período de Experiência" name="colaboradorQuestionario.avaliacao.id" id="cidade" list="avaliacaoExperiencias" listKey="id" listValue="titulo" cssStyle="width: 480px;" headerKey="" headerValue="Selecione..."  onchange="this.form.submit();" disabled="${desabilitarAvaliacaoSelect}"/>
				
				<@ww.hidden name="colaboradorQuestionario.candidato.id" />
				<@ww.hidden name="colaboradorQuestionario.colaborador.id" />
			</@ww.form>
		</#if>
	</@ww.div><br/>	
	
	<#if perguntas?exists && 0 < perguntas?size>
		<@ww.form name="form" action="${formAction}" method="POST">
			<@ww.datepicker label="Data" id="data" name="colaboradorQuestionario.respondidaEm" required="true" cssClass="mascaraData" value="${data}"/>
			<#include "../avaliacao/includePerguntasAvaliacao.ftl" />

			<#if candidato?exists && candidato.id?exists>
				<@ww.hidden name="colaboradorQuestionario.candidato.id" value="${candidato.id}"/>
			</#if>
			
			<@ww.hidden name="solicitacao.id" />
			<@ww.hidden name="respostaColaborador" />
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="validaRespostas(new Array('data'), new Array('data'), true, true, false, false, true);" class="btnGravar"></button>
	<#else>
		<div class="buttonGroup">
	</#if>

		<#if colaborador?exists && colaborador.id?exists>
			<#if !respostaColaborador>
				<button class="btnCancelar" onclick="window.location='periodoExperienciaQuestionarioList.action?colaborador.id=${colaborador.id}'"></button>
			</#if>		
		<#else>
			<button class="btnCancelar" onclick="window.location='list.action?solicitacao.id=${solicitacao.id}'"></button>		
		</#if>
	</div>
</body>
</html>