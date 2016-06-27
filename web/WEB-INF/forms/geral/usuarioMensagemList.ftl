<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Cadastro de Usuario Mensagens</title>
</head>
<body>
<@display.table name="usuarioMensagems" id="usuarioMensagem" pagesize=10 class="dados" defaultsort=2 sort="list">
	<@display.column title="Ações" class="acao">
		<a href="javascript: executeLink('prepareUpdate.action?usuarioManager.id=${usuarioManager.id});"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
		<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?usuarioManager.id=${usuarioManager.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
	</@display.column>
</@display.table>

<hr class="divider">
<div class="buttonGroup">
<button class="button" onclick="javascript: executeLink('prepareInsert.action');" accesskey="N">
<u>N</u>ovo Usuario Mensagem
</button>
</div>
</body>
</html>