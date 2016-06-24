<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<title>Exames</title>
</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" method="POST" id="formBusca">
			<@ww.textfield label="Nome" name="exame.nome" cssStyle="width: 350px;"/>
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	
	<@display.table name="exames" id="exame" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="javascript: executeLink('prepareUpdate.action?exame.id=${exame.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){javascript: executeLink('delete.action?exame.id=${exame.id}&page=${page}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column title="Periódico" style="width:50px;text-align:center;">
			<#if exame.periodico?exists && exame.periodico>
				Sim
			<#else>
				Não
			</#if>
		</@display.column>
		<@display.column title="Periodicidade (em meses)" style="width:160px;text-align:right;">
			<#if exame.periodicidade?exists && 0 < exame.periodicidade>
				${exame.periodicidade}
			</#if>
		</@display.column>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');" accesskey="I">
		</button>
	</div>
</body>
</html>