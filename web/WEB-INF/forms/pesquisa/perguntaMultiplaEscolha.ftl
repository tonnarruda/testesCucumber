<#assign contadorId = 0/>
<#list pergunta.respostas as respostaObjetiva>

	<#assign checkResposta = ""/>
	<#list colaboradorRespostas as colaboradorResposta>
		<#if colaboradorResposta.resposta?exists && colaboradorResposta.resposta.id?exists && colaboradorResposta.resposta.id == respostaObjetiva.id>
			<#assign checkResposta = "checked"/>
			<#break>
		<#else>
			<#assign checkResposta = ""/>
		</#if>
	</#list>

	<input type="checkBox" class="opcaoResposta${pergunta.id},radio" ${checkResposta} name="RM${pergunta.id?string?replace('.', '')}" id="RM${respostaObjetiva.id}"/><label for="RM${respostaObjetiva.id}">${respostaObjetiva.texto}</label>
	<br>

</#list>

<#if pergunta.comentario?exists && pergunta.comentario>
	${pergunta.textoComentario}<br>

	<#assign valueResposta = ""/>
	<#list colaboradorRespostas as colaboradorResposta>
		<#if pergunta.id == colaboradorResposta.pergunta.id && colaboradorResposta.comentario?exists>
			<#assign valueResposta = colaboradorResposta.comentario/>
		</#if>
	</#list>

	<textarea name="RC${pergunta.id?string?replace('.', '')}" style="height:50px;width:730px;overflow-y:scroll">${valueResposta}</textarea>
</#if>
