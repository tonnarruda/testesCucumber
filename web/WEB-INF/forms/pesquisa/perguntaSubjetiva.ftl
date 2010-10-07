
	<#assign valueResposta = ""/>
	<#list colaboradorRespostas as colaboradorResposta>
		<#if pergunta.id == colaboradorResposta.pergunta.id && colaboradorResposta.comentario?exists>
			<#assign valueResposta = colaboradorResposta.comentario/>
		</#if>
	</#list>

	<textarea name="RS${pergunta.id?string?replace('.', '')}" class="opcaoResposta${pergunta.id}" style="height:75px;width:730px;overflow-y:scroll">${valueResposta}</textarea><br>
