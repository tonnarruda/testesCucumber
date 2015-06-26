<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Resultado da ${tipoQuestionario.getDescricaoMaisc(questionario.tipo)}</title>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/questionario.css?version=${versao}"/>');
	</style>
</head>

<body>
	<#assign aspectoTemp = ""/>
	<div id="resultadoPesquisa">
		<#if questionario.dataInicio?exists && questionario.dataFim?exists>
			<@ww.div cssClass="divInformacao">
				<span>${tipoQuestionario.getDescricaoMaisc(questionario.tipo)}: <b>${questionario.titulo}</b></span>
				<#if questionario.dataInicio?exists && questionario.dataFim?exists>
					<span>Período: <b>${questionario.dataInicio} a ${questionario.dataFim}</b></span>
				</#if>
			</@ww.div>
		</#if>
		<div class="perguntas">
			<#list perguntas as pergunta>
				<#--Organiza por aspectos ou imprime todos os aspectos-->
				<br>
				<#if pergunta.aspecto?exists && pergunta.aspecto.nome?exists>
					<#if agruparPorAspectos>
						<#if aspectoTemp != pergunta.aspecto.nome>
							<@ww.div cssClass="divAspecto">
								${pergunta.aspecto.nome}
							</@ww.div>
							<br>
						</#if>
						<#assign aspectoTemp = pergunta.aspecto.nome/>
					</#if>
				</#if>
				<b>
					${pergunta.ordem}) ${pergunta.texto}
					<#if pergunta.aspecto?exists && pergunta.aspecto.nome?exists && !agruparPorAspectos>
							[${pergunta.aspecto.nome}]
					</#if>
				</b>
				<#-- Exibir total de respostas-->
				<@ww.div cssClass="divBordaResposta">
					<#assign contadorRespostas = 0/>
					<#list colaboradorRespostas as colaboradorResposta>
						<#if pergunta.id == colaboradorResposta.pergunta.id && colaboradorResposta.verificaResposta(pergunta) >
							<#assign contadorRespostas = contadorRespostas + 1/>
						</#if>
					</#list>
					<br>
					<#if pergunta.tipo == tipoPergunta.objetiva>
						<#include "pesquisaResultadoObjetiva.ftl" />
					</#if>
					<#if pergunta.tipo == tipoPergunta.subjetiva>
						<#include "pesquisaResultadoSubjetiva.ftl" />
					</#if>
					<#if pergunta.tipo == tipoPergunta.nota>
						<#include "pesquisaResultadoNota.ftl" />
					</#if>
					<#if 1 == contadorRespostas>
						${contadorRespostas} resposta
					</#if>
					<#if 1 < contadorRespostas>
						${contadorRespostas} respostas
					</#if>
					<#if 0 == contadorRespostas>
						Não há respostas
					</#if>
					<br><br>
					<#-- Exibir total de respostas-->
				</@ww.div>
			</#list>
		</div>
	</div>

	<button onclick="imprimir('resultadoPesquisa', false, '<@ww.url includeParams="none" value="/css/questionario.css?version=${versao}"/>')" class="btnRelatorio" accesskey="m"></button>
	<#if urlVoltar?exists && urlVoltar == "menu">
		<button onclick="window.location='prepareResultadoEntrevista.action?questionario.id=${questionario.id}'" class="btnVoltar" accesskey="V"></button>
	<#else>
		<#if questionario.tipo == tipoQuestionario.getAVALIACAOTURMA()>
			<button onclick="window.location='prepareResultado.action?questionario.id=${questionario.id}&turmaId=${turmaId}&cursoId=${cursoId}'" class="btnVoltar" ></button>
		<#else>
			<button onclick="window.location='prepareResultado.action?questionario.id=${questionario.id}'" class="btnVoltar" ></button>
		</#if>
	</#if>
</body>
</html>