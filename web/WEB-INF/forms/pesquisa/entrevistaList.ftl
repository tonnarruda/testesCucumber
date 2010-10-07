<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>

	<title>Modelos de Entrevistas de Desligamento</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="entrevistas" id="entrevista" class="dados">
		<@display.column title="Ações" style="width:175px">

			<#if entrevista.questionario.aplicarPorAspecto>
				<a href="../questionario/prepareAplicarByAspecto.action?questionario.id=${entrevista.questionario.id}&preview=true"><img border="0" title="Visualizar entrevista" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			<#else>
				<a href="../questionario/prepareAplicar.action?questionario.id=${entrevista.questionario.id}&preview=true"><img border="0" title="Visualizar entrevista" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			</#if>

			<a href="prepareUpdate.action?entrevista.id=${entrevista.id}&quantidadeDeResposta=${entrevista.questionario.quantidadeDeResposta}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>

			<#if 0 < entrevista.questionario.quantidadeDeResposta>
				<img border="0" title="Questionário - Já existe resposta para esta entrevista, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Aspectos - Já existe resposta para esta entrevista, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="../pergunta/list.action?questionario.id=${entrevista.questionario.id}"><img border="0" title="Questionário da entrevista" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>"></a>
				<a href="../aspecto/list.action?questionario.id=${entrevista.questionario.id}"><img border="0" title="Aspectos da entrevista" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>"></a>
			</#if>

			<a href="../questionario/imprimir.action?questionario.id=${entrevista.questionario.id}"><img border="0" title="Imprimir entrevista" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<a href="../questionario/imprimir.action?questionario.id=${entrevista.questionario.id}&imprimirFormaEconomica=true"><img border="0" title="Imprimir entrevista em formato econômico" src="<@ww.url includeParams="none" value="/imgs/iconPrint.gif"/>"></a>
			<a href="../questionario/prepareResultado.action?questionario.id=${entrevista.questionario.id}"><img border="0" title="Relatório da Entrevista" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>"></a>
			<a href="clonarEntrevista.action?entrevista.id=${entrevista.id}"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			<#if 0 < entrevista.questionario.quantidadeDeResposta>
				<img border="0" title="Já existe resposta para esta entrevista, não é permitido excluir." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="javascript:if (confirm('Confirma exclusão?')) window.location='delete.action?entrevista.id=${entrevista.id}&entrevista.questionario.empresa.id=${entrevista.questionario.empresa.id}&page=${page}'"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			</#if>
		</@display.column>

		<@display.column property="questionario.titulo" title="Título" />
		<@display.column title="Ativa" style="width:20px">
			<#if entrevista.ativa>
				<img border="0" title="Sim" src="<@ww.url includeParams="none" value="/imgs/flag_green.gif"/>">
			<#else>
				<img border="0" title="Não" src="<@ww.url includeParams="none" value="/imgs/flag_red.gif"/>">
			</#if>
		</@display.column>

	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>