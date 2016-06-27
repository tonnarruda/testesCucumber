<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Estabelecimentos</title>
</head>
<body>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<@ww.actionerror />
	<@ww.actionmessage />
	<@display.table name="estabelecimentos" id="estabelecimento" class="dados" defaultsort=2>
		<@display.column title="Ações" class="acao" style="text-align:center; width: 80px">
			<a href="javascript: executeLink('prepareUpdate.action?estabelecimento.id=${estabelecimento.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<#if !integradoAC>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?estabelecimento.id=${estabelecimento.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</#if>
		</@display.column>
		<@display.column property="nome" title="Nome" style="width: 500px"/>
		<@display.column property="cnpjFormatado" title="CNPJ" style="width: 120px"/>
	</@display.table>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}' />

	<#if !integradoAC>
	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');" accesskey="I">
		</button>
	</div>
	</#if>
</body>
</html>