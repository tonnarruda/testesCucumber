<table border="0" cellspacing="0">
	<tr>
		<td width="100px">
			<#if periodoIni?exists>
				<#assign dataIni=periodoIni?date />
			<#else>
				<#assign dataIni="" />
			</#if>
			<#if periodoFim?exists>
				<#assign dataFim=periodoFim?date />
			<#else>
				<#assign dataFim="" />
			</#if>
			<#if turmaId?exists>
				<#assign turmaIdAux=turmaId/>
			<#else>
				<#assign turmaIdAux="" />
			</#if>
			
			<img src="graficoObjetiva.action?pergunta.id=${pergunta.id}&areasIds=${areasIds}&periodoIni=${dataIni}&periodoFim=${dataFim}&turmaId=${turmaIdAux}"/></div>
		</td>
		<td width="500px" valign="top">
			<ol style="list-style-type: lower-alpha;">
				<#list respostas as resposta>
					<#if pergunta.id == resposta.pergunta.id>
						<li>
							${resposta.texto}
							<#list resultadoObjetivas as resultadoObjetiva>
								<#if resultadoObjetiva.respostaId?exists && resultadoObjetiva.respostaId == resposta.id>
									(${resultadoObjetiva.qtdPercentualRespostas}% ${resultadoObjetiva.qtdRespostas})
								</#if>
							</#list>
						</li>
						<#assign perguntaId>${pergunta.id}</#assign>
					</#if>
				</#list>
			</ol>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<#if exibirComentarios>
				<@ww.div cssClass="divRespSubjetiva">
				<b>Comentários:</b> <br>
					<#list colaboradorRespostas as colaboradorResposta>
						<#if pergunta.id == colaboradorResposta.pergunta.id && colaboradorResposta.comentario?exists && colaboradorResposta.comentario != "">
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