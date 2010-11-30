<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');

		input.radio
		{
			border: 0;
		}
		#aspecto
		{
			margin: 10px 0;
			padding: 5px;
			background-color: #DDDDDD;
			font-weight: bold;
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

	<script type="text/javascript">
		function montaSelect(id,ntMin,ntMax)
		{
			var texto = "<SELECT id='resposta' class='nota'>";
			texto += "<OPTION>Selecione...<OPTION>";
			for(i = ntMin; i <= ntMax; i++)
			{
				texto += "<OPTION value='" + i + "'>" + i + "</OPTION>";
			}

			texto += "</SELECT>";
			document.getElementById(id).innerHTML = texto;
		}
	</script>

	<#assign mostraFiltro="none"/>
	<#assign labelFiltro="Exibir Filtro"/>
	<#assign imagemFiltro="/imgs/arrow_down.gif"/>
	<#assign complementoTitulo=""/>

	<#if questionario?exists && questionario.anonimo>
		<#assign complementoTitulo="(anônimo)"/>
	</#if>

	<#if questionario?exists>
		<title>${tipoQuestionario.getDescricaoMaisc(questionario.tipo)} - ${questionario.titulo} ${complementoTitulo}</title>
	</#if>
	<#if avaliacao?exists && avaliacao.id?exists>
		<title>Avaliação</title>
	</#if>

	<#assign validarCampos="return validaFormulario('formBusca', null, null)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

</head>
<body>

<@ww.actionerror/>
<@ww.actionmessage/>
	<#if preview == false>
		<@ww.div cssStyle="background-color: #EEEEEE; padding:5px;">
			Confira abaixo como será exibido o questionário para quem vai respondê-lo.<br>
			Para confirmar, clique em <b>Concluir</b>.<br>
			Para voltar e fazer alterações na exibição, clique em <b>Voltar</b>.<br>
		</@ww.div>
	</#if>

	<br>
	<div class="cabecalho">
		<#if questionario?exists && questionario.cabecalho?exists>
			<pre>${questionario.cabecalho}</pre>
		</#if>
		<#if avaliacao?exists && avaliacao.id?exists>
			<p>Título: ${avaliacao.titulo}</p>	
			</br>Cabeçalho: <pre>${avaliacao.cabecalho}</pre></br>		
		</#if>
	</div>
	<#if perguntas?exists && 0 < perguntas?size >
		<#list perguntas as pergunta>
			<div id="perguntaResposta">
				<p id="tituloPergunta">${pergunta.ordem + ") " + pergunta.texto}</p>
				<#if pergunta.tipo == tipoPergunta.objetiva >
					<#list pergunta.respostas as resposta>
						<input type="radio" class="radio" name="resposta${pergunta.id}" label="${resposta.texto}"/>${resposta.texto}<br>
					</#list>
				</#if>
				<#if pergunta.tipo == tipoPergunta.multiplaEscolha >
					<#list pergunta.respostas as resposta>
						<input type="checkBox" class="radio" name="resposta${pergunta.id}" label="${resposta.texto}"/>${resposta.texto}<br>
					</#list>
				</#if>
				<#if pergunta.tipo == tipoPergunta.subjetiva >
					<textarea style="height:75px;width:730px;overflow-y:scroll"></textarea>
				</#if>
				<#if pergunta.tipo == tipoPergunta.nota >
						Selecione a nota de ${pergunta.notaMinima} a ${pergunta.notaMaxima}:
						<div id="resposta${pergunta.id}"></div>
						<script>
							montaSelect('resposta${pergunta.id}',${pergunta.notaMinima},${pergunta.notaMaxima});
						</script>
				</#if>
				<#if pergunta.comentario>
					<div style="margin-top:10px;">${pergunta.textoComentario}</div>
					<textarea style="height:50px;width:730px;overflow-y:scroll"></textarea>
				</#if>
			</div>
		</#list>
	</#if>
	
		<br>
	<div class="cabecalho">
		<#if questionario?exists && questionario.fichaMedica?exists && questionario.fichaMedica.rodape?exists>
			<p>${questionario.fichaMedica.rodape}</p>
		</#if>

	</div>
	
