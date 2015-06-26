<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Empresas BDS (Banco de Dados Solidário)</title>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@display.table name="empresaBdss" id="empresaBds" class="dados">
	<@display.column title="Ações" media="html" class="acao" style="text-align:center; width: 80px;">
		<a href="prepareUpdate.action?empresaBds.id=${empresaBds.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
		<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?empresaBds.id=${empresaBds.id}&page=${page}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
	</@display.column>
	<@display.column property="nome" title="Empresa" />
	<@display.column property="contato" title="Contato" style="width: 120px;"/>
	<@display.column property="email" title="E-mail"/>
	<@display.column title="Telefone" style="width: 100px;">
	(${empresaBds.ddd}) ${empresaBds.fone}
	</@display.column>
</@display.table>

<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

<div class="buttonGroup">
	<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
	</button>
</div>
</body>
</html>