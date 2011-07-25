<html>
<head>
<@ww.head/>
<#if questionario.id?exists>
	<title>${tipoQuestionario.getDescricaoMaisc(questionario.tipo)} - ${questionario.titulo}</title>
	<#assign formAction="salvaRespostaPesquisa.action"/>
	<#assign buttonClass="btnGravar"/>
	<#assign accessKey="A"/>

	<#assign exibirPorAspecto = questionario.aplicarPorAspecto/>
	<#assign aspectoPerguntaAnterior="_"/>
</#if>

<style>
	.perguntaTexto
	{
		padding-top: 10px;
		font-weight: bold;
	}
</style>


</head>
<body>

<#function exibeAspecto aspectoPerguntaAtual, aspectoPerguntaAnterior>
	<#local aspecto="">

	<#if aspectoPerguntaAtual?exists && aspectoPerguntaAtual != aspectoPerguntaAnterior>
		<#local aspecto>
			<@ww.div cssStyle="background-color: #DDDDDD; padding:5px;">
				<b>${aspectoPerguntaAtual}</b>
			</@ww.div>
		</#local>
	</#if>

	<#return aspecto>
</#function>

<div class="cabecalho">
	<#if questionario?exists && questionario.cabecalho?exists>
		<pre>${questionario.cabecalho}</pre>
	</#if>
	<br>
	<b>Colaborador: </b> ${colaborador.nomeComercial}
	<br>
	<br>
</div>
<div class="listaPerguntas">
	<ul>
		<#assign perguntaId=0/>
		
		<#list colaboradorRespostas as colaboradorResposta>
	
			<#if exibirPorAspecto>
				<#assign temAspecto = colaboradorResposta.pergunta.aspecto?exists && colaboradorResposta.pergunta.aspecto.nome?exists>
	
				<#if temAspecto>
					<#assign aspectoPerguntaAtual=colaboradorResposta.pergunta.aspecto.nome/>
					${exibeAspecto(aspectoPerguntaAtual, aspectoPerguntaAnterior)}
					<#assign aspectoPerguntaAnterior=colaboradorResposta.pergunta.aspecto.nome/>
				</#if>
	
			</#if>
	
			<#if colaboradorResposta.pergunta.id?string != perguntaId?string><#--Utilizado para não repetir a pergunta nas respostas multiplas escolhas-->
				<li class="perguntaTexto">${colaboradorResposta.pergunta.ordem}) ${colaboradorResposta.pergunta.texto}</li>
			</#if>
			
			<#if colaboradorResposta.pergunta.tipo == tipoPergunta.objetiva>
	
				<b>Resposta: </b> ${colaboradorResposta.resposta.texto}
				<br>
				<#if colaboradorResposta.comentario?exists>
					<b>Comentário: </b> ${colaboradorResposta.comentario}
				</#if>

			</#if>

			<#if colaboradorResposta.pergunta.tipo == tipoPergunta.multiplaEscolha && colaboradorResposta.pergunta.id?string != perguntaId?string>
				<b>Respostas: </b><br>
				<#list colaboradorRespostas as colaboradorRespostaMultiplaEscolha>
					<#if colaboradorRespostaMultiplaEscolha.pergunta.id == colaboradorResposta.pergunta.id> 
						${colaboradorRespostaMultiplaEscolha.resposta.texto} <br>
					</#if>
				</#list>
				<#if colaboradorResposta.comentario?exists>
					<b>Comentário: </b> ${colaboradorResposta.comentario}
				</#if>
			</#if>
			
			<#assign perguntaId>${colaboradorResposta.pergunta.id}</#assign>
			
			<#if colaboradorResposta.pergunta.tipo == tipoPergunta.subjetiva>
				<#if colaboradorResposta.comentario?exists>
					<b>Resposta: </b> 
					${colaboradorResposta.comentario}
				</#if>
			</#if>
			
			<#if colaboradorResposta.pergunta.tipo == tipoPergunta.nota>
	
				<#if colaboradorResposta.valor?exists>
					<b>Nota: </b> 
					${colaboradorResposta.valor}
				</#if>
				<br>
				<#if colaboradorResposta.comentario?exists>
					<b>Comentário: </b> ${colaboradorResposta.comentario}
				</#if>
	
			</#if>
		</#list>
	</ul>
</div>

<#if !ocultarBotaoVoltar>
	<div class="buttonGroup">
		<button onclick="window.location='list.action?questionario.id=${questionario.id}'" class="btnVoltar" accesskey="V"></button>
	</div>
</#if>

</body>
</html>