<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>

	<title>Modelos de Fichas Médicas</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="fichaMedicas" id="fichaMedica" class="dados">
		<@display.column title="Ações" style="width:135px">

			<#if fichaMedica.questionario.aplicarPorAspecto>
				<a href="../../pesquisa/questionario/prepareAplicarByAspecto.action?questionario.id=${fichaMedica.questionario.id}&preview=true"><img border="0" title="Visualizar Questionário de Ficha Médica" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			<#else>
				<a href="../../pesquisa/questionario/prepareAplicar.action?questionario.id=${fichaMedica.questionario.id}&preview=true"><img border="0" title="Visualizar Questionário de Ficha Médica" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			</#if>

			<a href="prepareUpdate.action?fichaMedica.id=${fichaMedica.id}&quantidadeDeResposta=${fichaMedica.questionario.quantidadeDeResposta}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>

			<#if 0 < fichaMedica.questionario.quantidadeDeResposta>
				<img border="0" title="Questionário - Já existe resposta para este questionário, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Aspectos - Já existe resposta para este questionário, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="../../pesquisa/pergunta/list.action?questionario.id=${fichaMedica.questionario.id}"><img border="0" title="Questionário da ficha médica" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>"></a>
				<a href="../../pesquisa/aspecto/list.action?questionario.id=${fichaMedica.questionario.id}"><img border="0" title="Aspectos da ficha médica" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>"></a>
			</#if>

			<a href="clonarFichaMedica.action?fichaMedica.id=${fichaMedica.id}"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			<#if 0 < fichaMedica.questionario.quantidadeDeResposta>
				<img border="0" title="Já existe resposta para este questionário, não é permitido excluir." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?fichaMedica.id=${fichaMedica.id}&fichaMedica.questionario.empresa.id=${fichaMedica.questionario.empresa.id}&page=${page}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			</#if>
			<a href="imprimirFichaMedicaList.action?questionario.id=${fichaMedica.questionario.id}"><img border="0" title="Imprimir ficha" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			
		</@display.column>

		<@display.column property="questionario.titulo" title="Título" />
		<@display.column title="Ativa" style="width:30px">
			<#if fichaMedica.ativa>
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