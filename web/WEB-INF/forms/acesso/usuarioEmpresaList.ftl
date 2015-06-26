<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Usuários Empresas</title>
</head>
<body>
	<@display.table name="usuarioEmpresas" id="usuarioEmpresa" pagesize=10 class="dados" defaultsort=2 sort="list">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?usuarioEmpresa.id=${usuarioEmpresa.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?usuarioEmpresa.id=${usuarioEmpresa.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="id" title="Id"/>
		<@display.column property="usuario.id" title="Usuario"/>
		<@display.column property="perfil.id" title="Perfil"/>
		<@display.column property="empresa" title="Empresa"/>
	</@display.table>

	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>