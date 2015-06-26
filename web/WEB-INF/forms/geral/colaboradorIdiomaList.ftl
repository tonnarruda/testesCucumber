<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Colaboradores Idiomas</title>
</head>
<body>
	<@display.table name="colaboradorIdiomas" id="colaboradorIdioma" pagesize=10 class="dados" defaultsort=2 sort="list">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?colaboradorIdioma.id=${colaboradorIdioma.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?colaboradorIdioma.id=${colaboradorIdioma.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="id" title="Id"/>
		<@display.column property="colaborador.id" title="Colaborador"/>
		<@display.column property="idioma" title="Idioma"/>
		<@display.column property="nivel" title="Nivel"/>
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="N">
		</button>
	</div>
</body>
</html>