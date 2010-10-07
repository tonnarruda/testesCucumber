<#if exibirRespostas>
	<@ww.div cssClass="divRespSubjetiva">
		<#list colaboradorRespostas as colaboradorResposta>
			<#if pergunta.id == colaboradorResposta.pergunta.id && colaboradorResposta.comentario?exists && colaboradorResposta.comentario != "">
				<#if questionario.anonimo?exists && questionario.anonimo>
					<b>Resposta:</b>
				<#else>
					<b>${colaboradorResposta.colaboradorQuestionario.colaborador.nomeComercial}:</b>
				</#if>
				${colaboradorResposta.comentario}<br>
			</#if>
		</#list>
	</@ww.div>
</#if>
