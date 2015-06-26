<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<title>${pesquisa.titulo}</title>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/preview.css?version=${versao}"/>');
</style>
<script src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>
<script type='text/javascript'>
	function prepareSubmit()
	{
		<!-- Monta respostas com o form simples -->
		var resposta = "";

		for(i = 0; i < document.form.elements.length; i++)
		{
			var respostaTmp = "";

			if(document.form.elements[i].type == "hidden"  && document.form.elements[i].id == "pergunta")
			{
				respostaTmp += "P" + document.form.elements[i].value + "&&&";
			}

			if(document.form.elements[i].type == "select-one")
			{
				respostaTmp += "V" + document.form.elements[i].value + "&&&";
			}

			if(respostaTmp != "")
			{
				resposta += respostaTmp;
			}
		}

		<!-- Submete apenas o formAction -->
		document.formAction.respostas.value = resposta;
		document.formAction.submit();
	}

	function montaSelect(id, valorSelected)
	{
		var texto = "<SELECT id='resposta' class='nota'>";

		for(i = ${pesquisa.valorMin}; i <= ${pesquisa.valorMax}; i++)
		{
			if(valorSelected == i)
				texto += "<OPTION value='" + i + "' selected='selected'>" + i + "</OPTION>";
			else
				texto += "<OPTION value='" + i + "'>" + i + "</OPTION>";
		}

		texto += "</SELECT>";
		document.getElementById(id).innerHTML = texto;;
	}
</script>

</head>
<body>
<#if colaboradorRespostas?exists && colaboradorRespostas?size != 0>
	<#assign formAction="editPesquisaRespondida.action"/>
<#else>
	<#assign formAction="salvaPesquisaRespondida.action"/>
</#if>

<#function valorSelected gabaritos idPergunta>
	<#local retorno="null">
	<#if gabaritos?exists && gabaritos?size != 0>
		<#list gabaritos as respostaGabarito>
			<#if respostaGabarito.pergunta.id == idPergunta>
					<#local retorno>${respostaGabarito.valor}</#local>
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
		<table class="tblSoma" width="100%" cellpadding="0" cellspacing="2">
		<tr>
			<td width="95%"><b>Pergunta</b></td>
			<td width="5%"><b>Nota</b></td>
		</tr>
		<#assign zebra=true/>
		<#assign stilo="linhaPintada"/>
		<#list pesquisa.perguntas as pergunta>
		<tr>
			<td class="${stilo}"><b>${pergunta.ordem} -</b> ${pergunta.texto}</td>
			<td class="${stilo}">
				<input type="hidden" value="${pergunta.id}" id="pergunta" />
				<div id="resposta${pergunta.id}">
				</div>
				<script>
					montaSelect('resposta${pergunta.id}', ${valorSelected(colaboradorRespostas,pergunta.id)});
				</script>
			</td>
		</tr>
		<#if zebra>
			<#assign zebra=false/>
			<#assign stilo=""/>
		<#else>
			<#assign zebra=true/>
			<#assign stilo="linhaPintada"/>
		</#if>
		</#list>
		<table>
	</div>
</form>
	<p class="fechar">
		<a href="javascript:prepareSubmit();"><img border="0" src="<@ww.url includeParams="none" value="/imgs/bot_enviar.gif"/>"></a>&nbsp;&nbsp;
		<a href="javascript:window.close();"><img border="0" src="<@ww.url includeParams="none" value="/imgs/bot_cancelar.gif"/>"></button>
	</p><br>
</body>
</html>