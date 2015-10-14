<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Habilidades</title>
</head>
<body>
	<#assign urlImgs><@ww.url value="/imgs/"/></#assign>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@display.table name="habilidades" id="habilidade" class="dados">
		<@display.column title="Ações" media="html" style="text-align:center; width:80px;" >
			<a href="prepareUpdate.action?habilidade.id=${habilidade.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?habilidade.id=${habilidade.id}&page=${page}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column property="peso" title="Peso" style="width:50px; text-align:right;"/>
		<@display.column title="Obs." style="text-align: center;width: 50px">
			<#if habilidade.observacao?exists && habilidade.observacao?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${habilidade.observacao?j_string}');return false">...</span>
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