<html>
<head>
<@ww.head/>
<#if questionario.id?exists>
	<#assign complementoTitulo=""/>

	<#if questionario.anonimo>
		<#assign complementoTitulo="(${tipoQuestionario.getDescricaoMaisc(questionario.tipo)} anônima)"/>
	</#if>

	<title>${tipoQuestionario.getDescricaoMaisc(questionario.tipo)} - ${questionario.titulo} ${complementoTitulo}</title>
	<#assign formAction="salvaRespostaPesquisa.action"/>
	<#assign buttonClass="btnGravar"/>
	<#assign retorno="../../"/>

	<#if voltarPara?exists>
		<#assign retorno=voltarPara/>
	</#if>

	<#assign exibirPorAspecto = questionario.aplicarPorAspecto/>
	<#assign aspectoPerguntaAnterior="_"/>
	<link rel="stylesheet" href="${request.contextPath}/externo/layout?tipo=trafego" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/perguntasAvaliacao.js"/>'></script>
</#if>

<style type="text/css">

	.perguntaTexto
	{
		padding-top: 10px;
		font-weight: bold;
	}

	#aspecto
	{
		margin: 10px 0;
		padding: 5px;
		background-color: #DDDDDD;
	}

	input.radio
	{
		border: 0;
	}

	#perguntaResposta
	{
		padding: 5px;
	}

	#perguntaResposta input
	{
		margin-left: -2px;
		_margin-left: -5px;
	}

	#tituloPergunta
	{
		margin: 5px 0;
		padding: 0;
		font-weight: bold;
	}
</style>

<script>
function submete(formulario, validando)
{
	if(validando)
	{
		<#if respondePorOutroUsuario>
			var formularioValido = validaRespostas(null, null, false, true, true, false, false);
		<#else>
			var formularioValido = validaRespostas(null, null, false, true, true, true, true);
		</#if> 
		
		if(!formularioValido)
			return false;
	}

	agrupaPerguntaseRespostas(formulario);

	document.formAction.submit();
}

function agrupaPerguntaseRespostas(formulario)
{
	var elemento = null;
	var perguntasRespostas = "";
	var respostas = document.getElementById("respostas");
	respostas.value = "";

	for(i = 0;i < formulario.length;i++)
	{
		elemento = formulario.elements[i];

		if(elemento.name.substring(0,2) == "PG"){
			perguntasRespostas += "PG"+elemento.name.substring(2)+"_";
		}
		else if(elemento.name.substring(0,2) == "RO" && elemento.checked){
			perguntasRespostas += "RO"+elemento.id.substring(2)+"_";
		}
		else if(elemento.name.substring(0,2) == "RM" && elemento.checked){
			perguntasRespostas += "RM"+elemento.id.substring(2)+"_";
		}
		else if(elemento.name.substring(0,2) == "RN"){
			perguntasRespostas += "RN"+elemento.value+"_";
		}
		else if(elemento.name.substring(0,2) == "RS" && elemento.value.trim().length > 0){
			perguntasRespostas += "RS"+elemento.value.trim()+"_";
		}
		else if(elemento.name.substring(0,2) == "RC" && elemento.value.trim().length > 0){
			perguntasRespostas += "RC"+elemento.value.trim()+"_";
		}
	}

	respostas.value = perguntasRespostas.substring(0,perguntasRespostas.length-1);
}
</script>
</head>
<body>
	<@ww.actionmessage />
	<#if !questionario.anonimo>
		<#if colaborador?exists && colaborador.nome?exists>
			<#if vinculo?exists && vinculo == 'A'>
				<b>Candidato: ${colaborador.nome}</b>
			<#else>
				<b>Colaborador: ${colaborador.nome}</b>
			</#if>
		</#if>
	</#if>

	<#function exibeAspecto aspectoPerguntaAtual, aspectoPerguntaAnterior>
		<#local aspecto="">

		<#if aspectoPerguntaAtual?exists && aspectoPerguntaAtual != aspectoPerguntaAnterior>
			<#local aspecto>
				<div id="aspecto">
					<b>${aspectoPerguntaAtual}</b>
				</div>
			</#local>
		</#if>

		<#return aspecto>
	</#function>

	<#assign validarCampos="return submete(document.forms[1],false);"/>
	<#if validarFormulario?exists && validarFormulario>
		<#assign validarCampos="return submete(document.forms[1],true);"/>
	</#if>
	<@ww.actionerror />

	<@ww.form name="formAction" action="salvaQuestionarioRespondido.action?tela=${tela}#c${colaborador.id}" method="POST">
		<@ww.hidden name="respostas" id="respostas" />
		<@ww.hidden name="questionario.id" />
		<@ww.hidden name="questionario.liberado" />
		<@ww.hidden name="questionario.tipo" />
		<@ww.hidden name="colaborador.id" />
		<@ww.hidden name="voltarPara" />
		<@ww.hidden name="vinculo" />
		<@ww.hidden name="turmaId"  />
		<@ww.hidden name="empresaCodigo"  />
		<@ww.hidden name="matricula"  />
	<@ww.token />
	</@ww.form>


	<div class="cabecalho">
		<#if questionario?exists && questionario.cabecalho?exists>
			<pre>${questionario.cabecalho}</pre>
		</#if>
	</div>
	<div>
	<form name="form">

		<#list questionario.perguntas as pergunta>

			<#if exibirPorAspecto>
				<#assign temAspecto = pergunta.aspecto?exists && pergunta.aspecto.nome?exists>

				<#if temAspecto>
					<#assign aspectoPerguntaAtual=pergunta.aspecto.nome/>
					${exibeAspecto(aspectoPerguntaAtual, aspectoPerguntaAnterior)}
					<#assign aspectoPerguntaAnterior=pergunta.aspecto.nome/>
				</#if>

			</#if>

			<div id="perguntaResposta">
				<p id="tituloPergunta"  class="pergunta${pergunta.id}">${pergunta.ordem}) ${pergunta.texto}</p> <input type="hidden" name="PG${pergunta.id}"/>
				<#if pergunta.tipo == tipoPergunta.objetiva>
					<#include "perguntaObjetiva.ftl"/>
				</#if>
				<#if pergunta.tipo == tipoPergunta.multiplaEscolha>
					<#include "perguntaMultiplaEscolha.ftl"/>
				</#if>
				<#if pergunta.tipo == tipoPergunta.subjetiva>
					<#include "perguntaSubjetiva.ftl"/>
				</#if>
				<#if pergunta.tipo == tipoPergunta.nota>
					<#include "perguntaNota.ftl"/>
				</#if>
			</div>

		</#list>

		<#if colaboradorTurma?exists>
			<@ww.hidden name="colaboradorTurma.id"  />
		</#if>

	</form>
	</div>
	<div class="buttonGroup">
		<#if questionario.perguntas?exists && 0 < questionario.perguntas?size>
			<button onclick="${validarCampos}" class="${buttonClass}"></button>
		</#if>
		<button onclick="window.location='${retorno}'" class="btnVoltar"></button>
	</div>
</body>
</html>