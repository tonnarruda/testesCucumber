<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Cadastro de Mensagems</title>
</head>
<body>
<@display.table name="mensagems" id="mensagem" pagesize=10 class="dados" defaultsort=2 sort="list">
	<@display.column title="Ações" class="acao">
		<a href="prepareUpdate.action?mensagem.id=${mensagem.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
		<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?mensagem.id=${mensagem.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
	</@display.column>
	<@display.column property="id" title="Id"/>	
</@display.table>

<hr class="divider">
<div class="buttonGroup">
<button class="button" onclick="window.location='prepareInsert.action'" accesskey="N">
<u>N</u>ovo Mensagem
</button>
</div>
</body>
</html>