<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Áreas de Interesse</title>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
	<@display.table name="areaInteresses" id="areaInteresse" class="dados">
		<@display.column title="Ações" style="text-align:center; width: 80px" media="html">
			<a href="javascript: executeLink('prepareUpdate.action?areaInteresse.id=${areaInteresse.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?areaInteresse.id=${areaInteresse.id}&page=${page}');});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column property="observacao" title="Observações"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="executeLink('prepareInsert.action');" accesskey="I">
		</button>
	</div>
</body>
</html>