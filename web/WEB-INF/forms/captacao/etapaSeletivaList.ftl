<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Etapas Seletivas</title>
</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>
	<@display.table name="etapaSeletivas" id="etapaSeletiva" class="dados">
		<@display.column title="Ações" media="html" class="acao">
			<a href="prepareUpdate.action?etapaSeletiva.id=${etapaSeletiva.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?etapaSeletiva.id=${etapaSeletiva.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="ordem" title="Ordem" style="width: 60px; text-align: right;"/>
		<@display.column property="nome" title="Nome"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I"></button>
	</div>
</body>
</html>