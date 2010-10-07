<#assign contador = 0/>
<#assign media = 0/>

<table border="0" cellspacing="0" width="100%">
	<tr>
		<#list colaboradorRespostas as colaboradorResposta>
			<#if pergunta.id == colaboradorResposta.pergunta.id && colaboradorResposta.verificaResposta(pergunta)>
				<#assign contador = contador + 1/>
				<#assign media = media + colaboradorResposta.valor/>
				<#assign perguntaId>${pergunta.id}</#assign>
			</#if>
		</#list>

		<td>
			<#if contador != 0>
				Média: ${media / contador}
			</#if>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<#if exibirComentarios>
				<@ww.div cssClass="divRespSubjetiva">
					<b>Comentários:</b><br>
					<#list colaboradorRespostas as colaboradorResposta>
						<#if pergunta.id == colaboradorResposta.pergunta.id && colaboradorResposta.comentario?exists  && colaboradorResposta.comentario != "">
							<#if questionario.anonimo?exists && questionario.anonimo>
								<img border="0" align="absMiddle" src="<@ww.url value="/imgs/folhaCheia.gif"/>">
							<#else>
								<b>${colaboradorResposta.colaboradorQuestionario.colaborador.nomeComercial}:</b>
							</#if>
							${colaboradorResposta.comentario} <br>
						</#if>
					</#list>
				</@ww.div>
			</#if>
		</td>
	</tr>
</table>
