	Selecione a nota de ${pergunta.notaMinima} a ${pergunta.notaMaxima}:

	<#-- Pega o valor da resposta-->
	<#list colaboradorRespostas as colaboradorResposta>
		<#if pergunta.id == colaboradorResposta.pergunta.id && colaboradorResposta.valor?exists>
			<#assign notaResposta = colaboradorResposta.valor/>
		</#if>
	</#list>
	<#-- Fim do pega o valor da resposta-->
	<select name="RN${pergunta.id?string?replace('.', '')}" class="opcaoResposta${pergunta.id}">
		<option value="">Selecione...</option>
		<#list pergunta.notaMinima..pergunta.notaMaxima as valorNota>

			<#if notaResposta?exists && notaResposta == valorNota>
				<#assign selectedResposta = "selected"/>
			<#else>
				<#assign selectedResposta = ""/>
			</#if>

			<option ${selectedResposta} value="${valorNota}">${valorNota}</option>
		</#list>
	</select>

	<br>
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
	<br>