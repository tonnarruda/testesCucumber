<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		</style>
		<title>Certificações</title>
		
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		
<#include "../ftl/showFilterImports.ftl" />
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		
		<#assign linkFiltro=""/>
		<#if nomeCursoBusca?exists>
			<#assign linkFiltro="${linkFiltro}&nomeBusca=${nomeBusca}"/>
		</#if>
		
		<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" id="form" action="list.action" method="POST">
			<@ww.textfield label="Nome" name="nomeBusca" id="nome" cssClass="inputNome" maxLength="100" cssStyle="width: 340px;"/>
			<@ww.hidden id="pagina" name="page"/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
		</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
		<br>
		
		<@display.table name="certificacaos" id="certificacao" class="dados">
			<@display.column title="Ações" class="acao">
				<a href="javascript: executeLink('prepareUpdate.action?certificacao.id=${certificacao.id}${linkFiltro}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?certificacao.id=${certificacao.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<a href="javascript: executeLink('imprimir.action?certificacao.id=${certificacao.id}');"><img border="0" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			</@display.column>
			<@display.column property="nome" title="Nome"/>	
		</@display.table>
		
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link=""  idFormulario="form" page='${page}'/>
		
		<div class="buttonGroup">
			<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');"></button>
		</div>
	</body>
</html>