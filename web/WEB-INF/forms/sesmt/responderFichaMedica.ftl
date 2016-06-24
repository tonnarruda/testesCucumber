<html>
<head>
<@ww.head/>

<#include "../ftl/mascarasImports.ftl" />

<#if questionario.id?exists>
	<#assign complementoTitulo=""/>

	<#if questionario.anonimo>
		<#assign complementoTitulo="(${tipoQuestionario.getDescricaoMaisc(questionario.tipo)} anônima)"/>
	</#if>

	<title>${tipoQuestionario.getDescricaoMaisc(questionario.tipo)} - ${questionario.titulo} ${complementoTitulo}</title>
	<#assign formAction="salvaRespostaPesquisa.action"/>
	<#assign buttonClass="btnGravar"/>
	<#assign accessKey="A"/>
	<#assign retorno="../../"/>

	<#if voltarPara?exists>
		<#assign retorno=voltarPara/>
	</#if>

	<#assign exibirPorAspecto = questionario.aplicarPorAspecto/>
	<#assign aspectoPerguntaAnterior="_"/>
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
function submete(formulario, validando){
	
	var formularioValido = validaFormulario('form',new Array('respondidaEm'), new Array('respondidaEm'), true);
	
	if(!formularioValido)
		return false;

	agrupaPerguntaseRespostas(formulario);

	document.formAction.submit();
}

function agrupaPerguntaseRespostas(formulario){
	var elemento = null;
	var perguntasRespostas = "";
	var respostas = document.getElementById("respostas");
	respostas.value = "";

	for(i = 0;i < formulario.length;i++)
	{
		elemento = formulario.elements[i];

		if(elemento.name.substring(0,2) == "PG"){
			perguntasRespostas += "PG"+elemento.name.substring(2)+"¨";
		}
		else if(elemento.name.substring(0,2) == "RO" && elemento.checked){
			perguntasRespostas += "RO"+elemento.id.substring(2)+"¨";
		}
		else if(elemento.name.substring(0,2) == "RM" && elemento.checked){
			perguntasRespostas += "RM"+elemento.id.substring(2)+"¨";
		}
		else if(elemento.name.substring(0,2) == "RN"){
			perguntasRespostas += "RN"+elemento.value+"¨";
		}
		else if(elemento.name.substring(0,2) == "RS" && elemento.value.trim().length > 0){
			perguntasRespostas += "RS"+elemento.value+"¨";
		}
		else if(elemento.name.substring(0,2) == "RC" && elemento.value.trim().length > 0){
			perguntasRespostas += "RC"+elemento.value+"¨";
		}
	}

	respostas.value = perguntasRespostas.substring(0,perguntasRespostas.length-1);
}
</script>
</head>
<body>
	<@ww.actionmessage />
	
	<@ww.form name="formAction" action="../../pesquisa/colaboradorResposta/salvaQuestionarioRespondido.action?tela=${tela}" method="POST">
	
	<#if !questionario.anonimo>
		<#if colaborador?exists && colaborador.nome?exists>
			<#if vinculo?exists && vinculo == 'A'>
				<b>Candidato: ${colaborador.nome}</b>
			<#else>
				<b>Colaborador: ${colaborador.nome}</b>
			</#if>
		</#if>
	</#if>
	
	<#if respondidaEm?exists>
		<#assign dataResposta = respondidaEm?date/>
	<#else>
		<#assign dataResposta = ""/>
	</#if>
	<br/>
	<@ww.datepicker label="Data" name="respondidaEm" id="respondidaEm" cssClass="mascaraData" required="true" value="${dataResposta}"/>

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

	<@ww.actionerror />
	<@ww.actionmessage />

		<@ww.hidden name="respostas" id="respostas" />
		<@ww.hidden name="questionario.id" />
		<@ww.hidden name="questionario.liberado" />
		<@ww.hidden name="questionario.tipo" />
		<@ww.hidden name="colaborador.id" />
		<@ww.hidden name="voltarPara" />
		<@ww.hidden name="vinculo" />
		<@ww.hidden name="turmaId"  />
		<@ww.hidden name="inserirFichaMedica"  />
		<@ww.hidden name="colaboradorQuestionario.id"  />
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
				<p id="tituloPergunta">${pergunta.ordem}) ${pergunta.texto}</p> <input type="hidden" name="PG${pergunta.id}"/>
				<#if pergunta.tipo == tipoPergunta.objetiva>
					<#include "../pesquisa/perguntaObjetiva.ftl"/>
				</#if>
				<#if pergunta.tipo == tipoPergunta.multiplaEscolha>
					<#include "../pesquisa/perguntaMultiplaEscolha.ftl"/>
				</#if>
				<#if pergunta.tipo == tipoPergunta.subjetiva>
					<#include "../pesquisa/perguntaSubjetiva.ftl"/>
				</#if>
				<#if pergunta.tipo == tipoPergunta.nota>
					<#include "../pesquisa/perguntaNota.ftl"/>
				</#if>
			</div>

		</#list>

		<#if colaboradorTurma?exists>
			<@ww.hidden name="colaboradorTurma.id"  />
		</#if>

	</form>
	
	<#if questionario?exists && questionario.fichaMedica?exists && questionario.fichaMedica.rodape?exists>
		<div class="cabecalho">
			<p>${questionario.fichaMedica.rodape}</p>
		</div>
	</#if>
	
	<br><br>
	
	<div class="buttonGroup">
	
		<#if questionario.perguntas?exists && 0 < questionario.perguntas?size>
			<button onclick="${validarCampos}" class="${buttonClass}" accesskey="${accessKey}"></button>
		</#if>
		<button onclick="javascript: executeLink('${retorno}');" class="btnVoltar" accesskey="V"></button>
	</div>
</body>
</html>