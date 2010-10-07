<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
</style>
<title>Planejamentos de Realinhamentos</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<@display.table name="tabelaReajusteColaboradors" id="tabelaReajusteColaborador" class="dados">
		<@display.column title="Ações" media="html" style="width:95px;">
			<a href="../reajusteRelatorio/formFiltro.action?tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}"><img border="0" title="<@ww.text name="list.print.hint"/>" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<#if tabelaReajusteColaborador.ehUltimo>
				<a href="javascript:if (confirm('Tem certeza que deseja desfazer os Realinhamentos?')) window.location='cancelarReajuste.action?tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}'"><img title="Cancelar Reajuste" border="0" src="<@ww.url includeParams="none" value="/imgs/undo.gif"/>"></a>
				<img border="0" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<img border="0" title="Não é possível desfazer os Realinhamentos" src="<@ww.url includeParams="none" value="/imgs/undo.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<#if !tabelaReajusteColaborador.aprovada>
					<a href="visualizar.action?tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}"><img border="0" title="Visualizar Realinhamentos" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>"></a>
					<a href="prepareUpdate.action?tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			 		<a href="javascript:if (confirm('Confirma exclusão?')) window.location='delete.action?tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}&page=${page}'"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
				<#else>
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				</#if>
			</#if>
		</@display.column>
		<@display.column property="nome" title="Promoção/Reajuste"/>
		<@display.column property="data" title="Data de Aplicação" format="{0,date,dd/MM/yyyy}"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>

</body>
</html>