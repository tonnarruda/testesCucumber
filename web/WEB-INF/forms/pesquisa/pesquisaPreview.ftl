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

			if(document.form.elements[i].type == "textarea" && document.form.elements[i].value != "")
			{
				respostaTmp += "C" + document.form.elements[i].value + "&&&";
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

<#function checkChecked gabaritos idResposta>
	<#local retorno="">
	<#if gabaritos?exists && gabaritos?size != 0>
		<#list gabaritos as respostaGabarito>
			<#if respostaGabarito.resposta.id == idResposta>
				<#local retorno="checked">
				<#return retorno>
			</#if>
		</#list>
	</#if>
	<#return retorno>
</#function>

<#function exibirComentario gabaritos idPergunta>
	<#local retorno="">
	<#if gabaritos?exists && gabaritos?size != 0>
		<#list gabaritos as respostaGabarito>
			<#if respostaGabarito.pergunta.id == idPergunta>
				<#local retorno><#if respostaGabarito.comentario?exists>${respostaGabarito.comentario}</#if></#local>
				<#return retorno>
			</#if>
		</#list>
	</#if>
	<#return retorno>
</#function>

<@ww.form name="formAction" action="${formAction}" method="POST">
		<@ww.hidden name="respostas" id="respostas" />
		<@ww.hidden name="colaborador.id" id="colaborador.id"/>
		<@ww.hidden name="pesquisa.id" id="pesquisa.id"/>
</@ww.form>
<form name="form">
	<br>
	<h3>${pesquisa.titulo}</h3>

	<#if pesquisa?exists && pesquisa.objetivo?exists && pesquisa.objetivo != "">
		<fieldset>
			<legend>Objetivo</legend>
			<p>${pesquisa.objetivo}</p>
		</fieldset>
	</#if>

	<#if pesquisa?exists && pesquisa.instrucoes?exists && pesquisa.instrucoes != "">
		<fieldset>
			<legend>Instruções</legend>
			<p>${pesquisa.instrucoes}</p>
		</fieldset>
	</#if>

	<div class="perguntas">

		<#list pesquisa.perguntas as pergunta>
			<p><b>${pergunta.ordem} -</b> ${pergunta.texto}</p>

			<input type="hidden" value="${pergunta.id}" id="pergunta" />

			<ul class="resposta">
				<#list respostas as resposta>
					<#if resposta.pergunta.id == pergunta.id>
						<li>
							<input type="radio" name="resposta${pergunta.id}" value="${resposta.id}" ${checkChecked(colaboradorRespostas, resposta.id)}/> ${resposta.texto}
						</li>
					</#if>
				</#list>
				<#if pergunta.comentario>
					<div class="comentario">
						<li>
							${pergunta.textoComentario}
						</li>
						<li>
							<textarea>${exibirComentario(colaboradorRespostas, pergunta.id)}</textarea>
						</li>
					</div>
				</#if>
			</ul>
		</#list>
	</div>
</form>
	<p class="fechar">
		<a href="javascript:prepareSubmit();"><img border="0" src="<@ww.url includeParams="none" value="/imgs/bot_enviar.gif"/>"></a>&nbsp;&nbsp;
		<a href="javascript:window.close();"><img border="0" src="<@ww.url includeParams="none" value="/imgs/bot_cancelar.gif"/>"></button>
	</p><br>
</body>
</html>