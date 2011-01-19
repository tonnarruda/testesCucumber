<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>

	<title>Pesquisas</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="pesquisas" id="pesquisa" class="dados">
		<@display.column title="Ações" style="width:210px">
			<#if !pesquisa.questionario.liberado>
				<a href="javascript:newConfirm('Deseja liberar esta pesquisa?', function(){window.location='../questionario/liberar.action?questionario.id=${pesquisa.questionario.id}'});"><img border="0" title="Liberar Pesquisa" src="<@ww.url includeParams="none" value="/imgs/liberar.gif"/>"></a>
			<#else>
				<img border="0" title="Liberar Pesquisa - Esta pesquisa já está liberada." src="<@ww.url includeParams="none" value="/imgs/liberar.gif"/>" style="opacity:0.3;filter:alpha(opacity=30);">
			</#if>
			<#if pesquisa.questionario.aplicarPorAspecto>
				<a href="../questionario/prepareAplicarByAspecto.action?questionario.id=${pesquisa.questionario.id}&preview=true"><img border="0" title="Visualizar pesquisa" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			<#else>
				<a href="../questionario/prepareAplicar.action?questionario.id=${pesquisa.questionario.id}&preview=true"><img border="0" title="Visualizar pesquisa" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			</#if>
			<a href="prepareUpdate.action?pesquisa.id=${pesquisa.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<#if !pesquisa.questionario.liberado>
				<a href="../pergunta/list.action?questionario.id=${pesquisa.questionario.id}"><img border="0" title="Questionário da pesquisa" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>"></a>
				<a href="../aspecto/list.action?questionario.id=${pesquisa.questionario.id}"><img border="0" title="Aspectos da pesquisa" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>"></a>
			<#else>
				<img border="0" title="Questionário da pesquisa - Pesquisa já liberada, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Aspectos da pesquisa - Pesquisa já liberada, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
			<a href="../colaboradorQuestionario/list.action?questionario.id=${pesquisa.questionario.id}"><img border="0" title="Colaboradores" src="<@ww.url includeParams="none" value="/imgs/usuarios.gif"/>"></a>
			<a href="../questionario/imprimir.action?questionario.id=${pesquisa.questionario.id}"><img border="0" title="Imprimir pesquisa" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<a href="../questionario/imprimir.action?questionario.id=${pesquisa.questionario.id}&imprimirFormaEconomica=true"><img border="0" title="Imprimir pesquisa em formato econômico" src="<@ww.url includeParams="none" value="/imgs/iconPrint.gif"/>"></a>
			<a href="../questionario/prepareResultado.action?questionario.id=${pesquisa.questionario.id}"><img border="0" title="Relatório da Pesquisa" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>"></a>
			<a href="clonarPesquisa.action?pesquisa.id=${pesquisa.id}"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			<a href="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?pesquisa.id=${pesquisa.id}&pesquisa.questionario.empresa.id=${pesquisa.questionario.empresa.id}&page=${page}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>

		</@display.column>

		<@display.column property="questionario.titulo" title="Título" />

		<@display.column title="Período" style="width: 140px;">
			<#if pesquisa?exists && pesquisa.questionario?exists && pesquisa.questionario.dataInicio?exists && pesquisa.questionario.dataFim?exists>
				${pesquisa.questionario.dataInicio?string("dd'/'MM'/'yyyy")} a ${pesquisa.questionario.dataFim?string("dd'/'MM'/'yyyy")}
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