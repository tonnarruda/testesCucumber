<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<#if cargoTmp?exists && cargoTmp.nome?exists>
		<title>Funções - ${cargoTmp.nome}</title>
	<#else>
		<title>Funções</title>
	</#if>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign showFilter = true/>
	<#include "../ftl/showFilterImports.ftl" />
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="listFiltro.action" validate="true" method="POST" id="formBusca">
			<@ww.select label="Cargo" name="cargoTmp.id" list="cargos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 347px;"/>
			<@ww.hidden id="pagina" name="page"/>
		</@ww.form>

		<button class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1; document.formBusca.submit();"></button>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	
	<#if cargoTmp?exists && cargoTmp.id?exists>
		<@display.table name="funcaos" id="funcao" class="dados" sort="list">
			<@display.column title="Ações" class="acao">
				<a href="prepareUpdateFiltro.action?funcao.id=${funcao.id}&cargoTmp.id=${cargoTmp.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='deleteFiltro.action?funcao.id=${funcao.id}&cargoTmp.id=${cargoTmp.id}&page=${page}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</@display.column>
			<@display.column property="nome" title="Nome"/>
		</@display.table>
	
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?cargoTmp.id=${cargoTmp.id}&" page='${page}'/>
	
		<div class="buttonGroup">
			<button class="btnInserir" onclick="window.location='prepareInsertFiltro.action?cargoTmp.id=${cargoTmp.id}'"></button>
		</div>
	</#if>
</body>
</html>