<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
<title>Perfis</title>
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>
	
	<@display.table name="perfils" id="perfil" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?perfil.id=${perfil.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?perfil.id=${perfil.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
	</@display.table>
	<@ww.hidden id="pagina" name="page"/>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
		<button class="btnImprimirPdf" onclick="window.location='imprimirPerfis.action'">
	</div>
</body>
</html>