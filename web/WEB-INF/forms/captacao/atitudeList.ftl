<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
<title>Atitudes</title>
</head>
<body>
	<#assign urlImgs><@ww.url value="/imgs/"/></#assign>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@display.table name="atitudes" id="atitude" class="dados">
		<@display.column title="Ações" media="html" style="text-align:center; width:80px;" >
			<a href="prepareUpdate.action?atitude.id=${atitude.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?atitude.id=${atitude.id}&page=${page}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column title="Obs." style="text-align: center;width: 50px">
			<#if atitude.observacao?exists && atitude.observacao?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${atitude.observacao?j_string}');return false">...</span>
			</#if>
		</@display.column>
	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>