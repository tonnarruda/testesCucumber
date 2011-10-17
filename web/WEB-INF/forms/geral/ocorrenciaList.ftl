<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
<title>Tipos de Ocorrências</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" method="POST" id="formBusca">
			<@ww.textfield label="Descrição" name="ocorrencia.descricao" cssStyle="width: 350px;"/>
			
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<input type="button" value="" onclick="document.getElementById('pagina').value = 1; document.formBusca.submit();"  class="btnPesquisar grayBGE">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	<@display.table name="ocorrencias" id="ocorrencia" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?ocorrencia.id=${ocorrencia.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?ocorrencia.id=${ocorrencia.id}&page=${page}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" title="Descrição"/>
		<@display.column property="pontuacao" title="Pontuação" style = "width:80px;"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
	<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
	</button>
	</div>
</body>
</html>