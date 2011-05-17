<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<title>ComoFicouSabendoVaga</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="comoFicouSabendoVagas" id="comoFicouSabendoVaga" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?comoFicouSabendoVaga.id=${comoFicouSabendoVaga.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?comoFicouSabendoVaga.id=${comoFicouSabendoVaga.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
