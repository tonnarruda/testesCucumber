<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<title>OrdemDeServico</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="ordensDeServico" id="ordemDeServico" class="dados">
		<@display.column title="Ações" class="acao" style="width: 150px;">
			<a href="prepareUpdate.action?ordemDeServico.id=${ordemDeServico.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?ordemDeServico.id=${ordemDeServico.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="revisao" title="Revisão"/>
		<@display.column property="dataFormatada" title="Data"/>
	</@display.table>

	<@ww.hidden id="pagina" name="page"/>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>
		
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
