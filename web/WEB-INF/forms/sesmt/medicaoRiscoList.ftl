<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#if controlaRiscoPor == 'A'>
		<title>Medição dos Riscos do Ambiente</title>
	<#else>
		<title>Medição dos Riscos da Função</title>
	</#if>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<#include "../ftl/showFilterImports.ftl" />
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" method="POST">
		<@ww.hidden id="controlaRiscoPor" name="controlaRiscoPor"/>

		<#if controlaRiscoPor == 'A'>
			<@ww.select label="Ambiente" id="ambiente" name="ambiente.id" list="ambientes" listValue="nome" listKey="id" headerKey="-1" headerValue="Todos" />
		<#else>
			<@ww.select label="Função" id="funcao" name="funcao.id" list="funcoes" listValue="nome" listKey="id" headerKey="-1" headerValue="Todas" />
		</#if>

		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" onclick="document.getElementById('pagina').value = 1;" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<@display.table name="medicaoRiscos" id="medicaoRisco" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?controlaRiscoPor=${controlaRiscoPor}&medicaoRisco.id=${medicaoRisco.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?controlaRiscoPor=${controlaRiscoPor}&medicaoRisco.id=${medicaoRisco.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column title="Data da Medição" property="data" format="{0,date,dd/MM/yyyy}" />
		
		<#if controlaRiscoPor == 'A'>
			<@display.column title="Ambiente" property="ambiente.nome" />
			<@display.column title="Estabelecimento" property="ambiente.estabelecimento.nome" />
		<#else>
			<@display.column title="Função" property="funcao.nome" />
		</#if>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?controlaRiscoPor=${controlaRiscoPor}'"></button>
	</div>
</body>
</html>
