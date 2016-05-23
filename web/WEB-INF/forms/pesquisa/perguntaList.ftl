<html>
<head>
<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/pergunta.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/questionario.css?version=${versao}"/>');
	</style>

	<script type="text/javascript">
		function montaSelect(id,ntMin,ntMax)
		{
			var texto = "<select id='resposta' class='nota'>";
			texto += "<option>Selecione...<option>";
			for(i = ntMin; i <= ntMax; i++)
			{
				texto += "<option value='" + i + "'>" + i + "</option>";
			}

			texto += "</select>";
			document.getElementById(id).innerHTML = texto;
		}

		var novaPosicaoAberta = 0;
		var posicaoAbertaAtualmente;

		function exibeNovaPosicao(spanId)
		{
			if(novaPosicaoAberta == 1)
				document.getElementById(posicaoAbertaAtualmente).style.visibility = "hidden";

			document.getElementById(spanId).style.visibility = "visible";
			posicaoAbertaAtualmente = spanId;
			novaPosicaoAberta = 1;
		}

		function alterarPosicao(perguntaId, novaOrdem)
		{
			var posicaoSugerida = document.getElementById(novaOrdem).value;

			if(posicaoSugerida > ${ultimaOrdenacao} || posicaoSugerida < 1 )
				jAlert("Posição não permitida");
			else
				window.location="alterarPosicao.action?pergunta.id="+perguntaId+"&questionario.id=${questionario.id}&posicaoSugerida="+posicaoSugerida;
		}
	</script>

	<#include "../ftl/showFilterImports.ftl" />
	<#if questionario.anonimo>
		<#assign complementoTitulo="(anônimo)"/>
	</#if>

	<#assign complementoTitulo=""/>
	<#assign validarCampos="return validaFormulario('formBusca', null, null)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>${tipoQuestionario.getDescricaoMaisc(questionario.tipo)} - ${questionario.titulo} ${complementoTitulo}</title>
</head>
<body>

	<@ww.actionerror/>
	<@ww.actionmessage/>

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" onsubmit="${validarCampos}" validate="true" method="POST" id="formBusca">
			<@ww.select label="Exibir apenas aspecto" name="aspecto.id" id="aspecto" list="aspectos" listKey="id" listValue="nome" headerKey="" headerValue="[Todos]"  cssStyle="width: 355px;" onchange="${validarCampos};"/>
			<@ww.hidden name="questionario.id"/>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />

	<div class="periodo">
		${tipoQuestionario.getDescricaoMaisc(questionario.tipo)}: <span class="negrito">${questionario.titulo}</span><br>

		<#if questionario.dataInicio?exists && questionario.dataFim?exists>
			<span>Período: <b>${questionario.dataInicio} a ${questionario.dataFim}</b></span>
		</#if>
	</div>

	<#list perguntas as pergunta>
		<div class="novaPergunta">
			<a href="javascript: executeLink('prepareInsert.action?questionario.id=${questionario.id}&ordemSugerida=${pergunta.ordem}');"><img src="<@ww.url value="/imgs/mais.gif"/>"> Inserir pergunta aqui</a>
		</div>

		<div class="pergunta">
			<#if pergunta.aspecto.nome?exists>
				<div class="aspecto">${pergunta.aspecto.nome}</div>
			</#if>

			<div class="acaoPerguntas">
				<a href="javascript: executeLink('prepareUpdate.action?pergunta.id=${pergunta.id}&questionario.id=${questionario.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='../pergunta/delete.action?pergunta.id=${pergunta.id}&questionario.id=${questionario.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<#if 1 < pergunta.ordem>
					<a href="reordenar.action?pergunta.id=${pergunta.id}&questionario.id=${questionario.id}&sinal=-"><img src="<@ww.url value="/imgs/up.gif"/>" title="Mover para cima"/></a>
				<#else>
					<img src="<@ww.url value="/imgs/up.gif" />" title="Mover para cima" class="imgApagada"/>
				</#if>
				<#if !(pergunta.ordem == ultimaOrdenacao)>
					<a href="reordenar.action?pergunta.id=${pergunta.id}&questionario.id=${questionario.id}"><img src="<@ww.url value="/imgs/down.gif"/>"  title="Mover para baixo"/></a>
				<#else>
					<img src="<@ww.url value="/imgs/down.gif"/>" title="Mover para baixo" class="imgApagada"/>
				</#if>

				<a href="javascript:exibeNovaPosicao('span${pergunta.id}')"><img src="<@ww.url value="/imgs/updown.gif"/>" title="Mudar posi&ccedil;&atilde;o" /></a>

				<span class="posicao" id="span${pergunta.id}">
					Nova posição <input id="novaOrdem${pergunta.id}" onkeypress="if(event.keyCode == 13) alterarPosicao('${pergunta.id}', 'novaOrdem${pergunta.id}');return(somenteNumeros(event,''));" maxlength="3"></input>
				</span>
			</div>

			<p class="perguntaTitulo"><span class="negrito">${pergunta.ordem})</span> ${pergunta.texto}</p>

			<#if pergunta.tipo == tipoPergunta.objetiva >
				<ul class="objetiva">
				<#list pergunta.respostas as resposta>
					<li><input type="radio" name="resposta${pergunta.id}"/> ${resposta.texto}</li>
				</#list>
				</ul>
			</#if>
			<#if pergunta.tipo == tipoPergunta.multiplaEscolha >
				<ul class="objetiva">
				<#list pergunta.respostas as resposta>
					<li><input type="checkbox" name="resposta${pergunta.id}"/> ${resposta.texto}</li>
				</#list>
				</ul>
			</#if>
			<#if pergunta.tipo == tipoPergunta.subjetiva >
				<p class="subjetiva"><textarea readonly="readonly"></textarea></p>
			</#if>
			<#if pergunta.tipo == tipoPergunta.nota >
				<p class="selectInfo">Selecione a nota de ${pergunta.notaMinima} a ${pergunta.notaMaxima}:</p>
				<div id="resposta${pergunta.id}"></div>
				<script type="text/javascript">
					montaSelect('resposta${pergunta.id}',${pergunta.notaMinima},${pergunta.notaMaxima});
				</script>
			</#if>
			<#if pergunta.comentario>
				<p class="comentarioTexto"><#if pergunta.textoComentario?exists>${pergunta.textoComentario}</#if></p>
				<p class="comentario"><textarea readonly="readonly"></textarea></p>
			</#if>

		</div>
	</#list>

	<div>
		<a href="javascript: executeLink('prepareInsert.action?questionario.id=${questionario.id}');"><img src="<@ww.url value="/imgs/mais.gif"/>"> Inserir pergunta aqui</a>
	</div>

	<#if 0 < perguntas?size >
		<button class="btnAplicarNaOrdemAtual" onclick="javascript: executeLink('../questionario/prepareAplicar.action?questionario.id=${questionario.id}');"></button>
		<button class="btnAplicarOrdenadoPorAspectos" onclick="javascript: executeLink('../questionario/prepareAplicarByAspecto.action?questionario.id=${questionario.id}');"></button>
		<button class="btnEmbaralhar" onclick="javascript: executeLink('embaralharPerguntas.action?questionario.id=${questionario.id}');"></button>
	</#if>

	<#-- Monta o botão de acordo com o destino pesquisa, avaliação, entrevista-->
	<#if urlVoltar?exists>
		<button class="btnVoltar" onclick="window.location='${urlVoltar}'"></button>
	</#if>

	<script type="text/javascript">
		<#if (0 < aspectos?size) && (perguntas?size == 0)>
			exibeFiltro('${urlImgs}','divFiltroForm');
		</#if>
	</script>
</body>
</html>