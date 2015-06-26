<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<title>${pesquisa.titulo}</title>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/preview.css?version=${versao}"/>');
</style>
<script type='text/javascript'>
	function prepareSubmit()
	{
		<!-- Monta respostas com o form simples -->
		var resposta = "";
		var qtdPerguntas = 0;
		var qtdRespostas = 0;
		for(i = 0; i < document.form.elements.length; i++)
		{
			var respostaTmp = "";
			if(document.form.elements[i].type == "hidden"  && document.form.elements[i].id == "pergunta")
			{
				respostaTmp += "P" + document.form.elements[i].value + "&&&";
				qtdPerguntas++;
			}

			if(document.form.elements[i].type == "radio" && document.form.elements[i].checked)
			{
				respostaTmp += "R" + document.form.elements[i].value + "&&&";
				qtdRespostas++;
			}

			if(respostaTmp != "")
			{
				resposta += respostaTmp;
			}
		}
		if (qtdPerguntas != qtdRespostas)
		{
			jAlert('Atenção: todas as perguntas devem ser respondidas.');
		}
		else
		{
			<!-- Submete apenas o formAction -->
			document.formAction.respostas.value = resposta;
			document.formAction.submit();
		}
	}
</script>
</head>

<body>

<#if colaboradorRespostas?exists && colaboradorRespostas?size != 0>
	<#assign formAction="editPesquisaRespondida.action"/>
<#else>
	<#assign formAction="salvaPesquisaRespondida.action"/>
</#if>

<#function checkChecked gabaritos idResposta idPergunta>
	<#local retorno="">
	<#if gabaritos?exists && gabaritos?size != 0>
		<#list gabaritos as respostaGabarito>
			<#if respostaGabarito.pergunta.id == idPergunta>
				<#if respostaGabarito.resposta.id == idResposta>
					<#local retorno="checked">
					<#return retorno>
				</#if>
			</#if>
		</#list>
	</#if>
	<#return retorno>
</#function>

<#assign zebrar=true/>
<@ww.form name="formAction" action="${formAction}" method="POST">
		<@ww.hidden name="respostas" id="respostas" />
		<@ww.hidden name="colaborador.id" id="colaborador.id"/>
		<@ww.hidden name="pesquisa.id" id="pesquisa.id"/>
</@ww.form>
<div class="cabecalho">
	<#if pesquisa?exists && pesquisa.questionario?exists && pesquisa.questionario.cabecalho?exists>
		<pre>${pesquisa.questionario.cabecalho}</pre>
	</#if>
</div>
<form name="form">
	<br>
	<h3>${pesquisa.titulo}</h3>

	<#if pesquisa?exists && pesquisa.objetivo?exists && pesquisa.objetivo != "">
		<fieldset>
			<legend>Objetivo</legend>
			<pre>${pesquisa.objetivo}</pre>
		</fieldset>
	</#if>

	<#if pesquisa?exists && pesquisa.instrucoes?exists && pesquisa.instrucoes != "">
		<fieldset>
			<legend>Instruções</legend>
			<pre>${pesquisa.instrucoes}</pre>
		</fieldset>
	</#if>

	<div align="center">
	<table class="definida">
		<tr>
			<td>&nbsp;</td>
			<#list respostas as resposta>
				<td class="cabecalhoOpcoes">${resposta.texto}</td>
			</#list>
		</tr>

		<#list pesquisa.perguntas as pergunta>

		<#if zebrar>
			<tr class="linhaPintada">
			<#assign zebrar=false/>
		<#else>
			<tr>
			<#assign zebrar=true/>
		</#if>

			<td class="pergunta">
				<b>${pergunta.ordem} -</b> ${pergunta.texto}
				<input type="hidden" value="${pergunta.id}" id="pergunta" />
			</td>

			<#list respostas as resposta>
				<td class="opcoes">
					<input type="radio" name="resposta${pergunta.id}" value="${resposta.id}" ${checkChecked(colaboradorRespostas, resposta.id ,pergunta.id)}/>
				</td>
			</#list>
		</tr>
		</#list>
	</table>
	</div>
</form>
	<p class="fechar">
		<a href="javascript:prepareSubmit();"><img border="0" src="<@ww.url includeParams="none" value="/imgs/bot_enviar.gif"/>"></a>&nbsp;&nbsp;
		<a href="javascript:window.close();"><img border="0" src="<@ww.url includeParams="none" value="/imgs/bot_cancelar.gif"/>"></button>
	</p><br>
</body>
</html>