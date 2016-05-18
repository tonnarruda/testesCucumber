<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Áreas de Formação</title>
	
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

</head>
<body>

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" method="POST">
		<@ww.textfield label="Nome" name="areaFormacao.nome" id="nome" cssClass="inputNome" maxLength="100" cssStyle="width: 340px;"/>
		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@display.table name="areaFormacaos" id="areaFormacao" class="dados">
		<@display.column title="Ações" media="html" style="text-align:center; width:80px;" >
			<a href="javascript:executeLink('prepareUpdate.action?areaFormacao.id=${areaFormacao.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?areaFormacao.id=${areaFormacao.id}&page=${page}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
   <@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="executeLink('prepareInsert.action');" accesskey="I">
		</button>
	</div>
</body>
</html>