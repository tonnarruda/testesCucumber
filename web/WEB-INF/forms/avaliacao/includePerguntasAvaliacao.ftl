<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign i = 0/>

<#assign aspectoAnterior="-1"/>

<#list perguntas as pergunta>

	<div id="perguntaResposta">
		<p id="tituloPergunta" class="pergunta${pergunta.id}">${pergunta.ordem}) ${pergunta.texto}</p>
		
		<#if pergunta.tipo == tipoPergunta.objetiva >
			<#list pergunta.respostas as resposta>
				<input type="radio" class="opcaoResposta${pergunta.id},radio" name="perguntas[${i}].colaboradorRespostas[0].resposta.id" value="${resposta.id}" id="${resposta.id}" <#if perguntas[i].colaboradorRespostas[0].temResposta() && (resposta.id == perguntas[i].colaboradorRespostas[0].resposta.id)>checked</#if>/><label for="${resposta.id}">${resposta.texto}</label><br>
			</#list>
			
			<@ww.hidden name="perguntas[${i}].colaboradorRespostas[0].pergunta.id" />
			<@ww.hidden name="perguntas[${i}].colaboradorRespostas[0].pergunta.tipo" value="${pergunta.tipo}"/>
			
			<#if pergunta.comentario>
				${pergunta.textoComentario}<br>
				<#if perguntas[i].colaboradorRespostas[0].comentario?exists>
					<#assign valueComentario = perguntas[i].colaboradorRespostas[0].comentario />
				<#else>
					<#assign valueComentario = "" />
				</#if>
				<textarea name="perguntas[${i}].colaboradorRespostas[0].comentario" style="height:75px;width:730px;overflow-y:scroll">${valueComentario}</textarea><br>
			</#if>
		
		</#if>
		
		<#if pergunta.tipo == tipoPergunta.multiplaEscolha >
			
			<#assign j = 0/>
			<#assign valueComentario = "" />
			
			<#list pergunta.respostas as resposta>
				<#assign checked = "" />
				<#list pergunta.colaboradorRespostas as colaboradorResposta >
					<#if colaboradorResposta.temResposta() && (resposta.id == colaboradorResposta.resposta.id)>
						<#assign checked = "checked" />
						<#break/>
					</#if>
					
					<#if colaboradorResposta.comentario?exists>
						<#assign valueComentario = colaboradorResposta.comentario />
					</#if>
					
				</#list>
				
				<input type="checkbox" class="opcaoResposta${pergunta.id},radio" name="perguntas[${i}].colaboradorRespostas[${j}].resposta.id" value="${resposta.id}" id="${resposta.id}" ${checked}/><label for="${resposta.id}">${resposta.texto}</label><br>
				<@ww.hidden name="perguntas[${i}].colaboradorRespostas[${j}].pergunta.id" value="${pergunta.id}"/>
				<@ww.hidden name="perguntas[${i}].colaboradorRespostas[${j}].pergunta.tipo" value="${pergunta.tipo}"/>
				<@ww.hidden name="perguntas[${i}].colaboradorRespostas[${j}].pergunta.comentario" value="${pergunta.comentario?string}"/>
				
				<#assign j = j + 1/>
			</#list>
			
			<#if pergunta.comentario>
				${pergunta.textoComentario}<br>
				<textarea name="perguntas[${i}].colaboradorRespostas[0].comentario" style="height:75px;width:730px;overflow-y:scroll">${valueComentario}</textarea><br>
			</#if>
		</#if>
		
		<#if pergunta.tipo == tipoPergunta.subjetiva >
			<#assign valueResposta = ""/>
			
			<#list pergunta.colaboradorRespostas as colabResposta>
				<#if colabResposta.pergunta.id == pergunta.id && colabResposta.comentario?exists>
					<#assign valueResposta = colabResposta.comentario/>
					<#break/>
				</#if> 
			</#list>
			
			<textarea name="perguntas[${i}].colaboradorRespostas[0].comentario" class="opcaoResposta${pergunta.id}" style="height:75px;width:730px;overflow-y:scroll">${valueResposta}</textarea><br>
			<@ww.hidden name="perguntas[${i}].colaboradorRespostas[0].pergunta.id" />
			<@ww.hidden name="perguntas[${i}].colaboradorRespostas[0].pergunta.tipo" value="${pergunta.tipo}"/>
			
		</#if>
		
		<#if pergunta.tipo == tipoPergunta.nota >
			<@ww.select label="Selecione a nota de ${pergunta.notaMinima} a ${pergunta.notaMaxima}" cssClass="opcaoResposta${pergunta.id}" name="perguntas[${i}].colaboradorRespostas[0].valor" list=perguntas[i].colaboradorRespostas[0].getNotas() headerKey="" headerValue="Selecione..."/>
			<@ww.hidden name="perguntas[${i}].colaboradorRespostas[0].pergunta.id" />
			<@ww.hidden name="perguntas[${i}].colaboradorRespostas[0].pergunta.tipo" value="${pergunta.tipo}"/>
		</#if>
	</div>

	<#assign i = i + 1/>
</#list>

<br>
<@ww.textarea label="Observações" name="colaboradorQuestionario.observacao" cssStyle="width:600px;"/>

<@ww.hidden name="colaboradorQuestionario.avaliacao.id" />
<@ww.hidden name="colaboradorQuestionario.colaborador.id" />
<@ww.hidden name="colaboradorQuestionario.id" />