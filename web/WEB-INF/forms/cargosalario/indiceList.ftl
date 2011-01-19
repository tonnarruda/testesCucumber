<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<title>Índices</title>
</head>
<body>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="indices" id="indice" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?indiceAux.id=${indice.id}"><img title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<#if !integradoAC>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?indiceAux.id=${indice.id}'});"><img title="<@ww.text name="list.del.hint"/>" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</#if>
		</@display.column>
		<@display.column property="nome" title="Nome"/>	
	</@display.table>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}' />
	
	<#if !integradoAC>
		<div class="buttonGroup">
			<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
			</button>
		</div>
	</#if>
</body>
</html>