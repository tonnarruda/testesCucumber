<#include "questionarioAplicarInclude.ftl" />

<#list aspectos as aspecto>
	<#if 0 < aspecto.perguntas?size>
		<div class="aspecto">${aspecto.nome}</div>
	</#if>
	<#list aspecto.perguntas as pergunta>
		<div id="perguntaResposta">
			<p id="tituloPergunta">${pergunta.ordem + ") " + pergunta.texto}</p>
			<#if pergunta.tipo == tipoPergunta.objetiva >
				<#list pergunta.respostas as resposta>
					<input type="radio" class="radio" name="resposta${pergunta.id}" label="${resposta.texto}"/> ${resposta.texto}<br>
				</#list>
			</#if>
			<#if pergunta.tipo == tipoPergunta.multiplaEscolha >
				<#list pergunta.respostas as resposta>
					<input type="checkbox" class="check" name="resposta${pergunta.id}" label="${resposta.texto}"/> ${resposta.texto}<br>
				</#list>
			</#if>
			<#if pergunta.tipo == tipoPergunta.subjetiva >
				<textarea style="height:75px;width:730px;overflow-y:scroll"></textarea>
				<br>
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
</#list>

	<#if preview == false>
		<button class="btnConcluir" onclick="javascript: executeLink('aplicarByAspecto.action?questionario.id=${questionario.id}');" accesskey="C">
		</button>
		<button class="btnVoltar" onclick="javascript: executeLink('../pergunta/list.action?questionario.id=${questionario.id}');" accesskey="V">
		</button>
	<#else>
		<#-- Monta o botão de acordo com o destino pesquisa, avaliação, entrevista-->
		<#if urlVoltar?exists>
			<button class="btnVoltar" onclick="javascript: executeLink('${urlVoltar}');"></button>
		</#if>
	</#if>
</body>
</html>