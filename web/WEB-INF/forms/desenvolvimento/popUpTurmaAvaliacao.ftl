<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<@display.table name="avaliacaoTurmas" id="avaliacaoTurma" class="dados" >
	<@display.column title="Ações" style="width:70px;text-align:center;">
		<#if avaliacaoTurma.liberada>
			<img title="Bloquear Avaliação" src="<@ww.url includeParams="none" value="/imgs/cadeado_green.gif"/>" onclick="bloquearAvaliacaoTurma(${avaliacaoTurma.turma.id}, ${avaliacaoTurma.id})" align="absmiddle" style="cursor:pointer;"/>
		<#else>
			<img title="Liberar Avaliação" src="<@ww.url includeParams="none" value="/imgs/cadeado_red.gif"/>" onclick="liberarAvaliacaoTurma(${avaliacaoTurma.turma.id}, ${avaliacaoTurma.id})" align="absmiddle" style="cursor:pointer;"/>
		</#if>

		<#if (avaliacaoTurma.qtdColaboradorQuestionario > 0)>
			<img title="Resultado das Avaliações" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>" onclick="exibeResultados(${avaliacaoTurma.turma.id}, ${avaliacaoTurma.questionario.id})" align="absmiddle" style="cursor:pointer;"/>
		<#else>
			<img title="Não existe questionário respondido para esta avaliação" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>" align="absmiddle" style="opacity:0.3;filter:alpha(opacity=40);"/>
		</#if>
		
		<img title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" align="absmiddle" onclick="imprimirAvaliacao(${avaliacaoTurma.turma.id}, ${avaliacaoTurma.questionario.id})" style="cursor:pointer;"/>
	</@display.column>
	
	<@display.column property="questionario.titulo" title="Avaliação" />
</@display.table>

<div style="text-align:right;">
	<button onclick="$('.popup').dialog('close');" class="btnFechar"></button>
</div>