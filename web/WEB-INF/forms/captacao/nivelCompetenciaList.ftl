<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<title>Nível de Competência</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="nivelCompetencias" id="nivelCompetencia" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?nivelCompetencia.id=${nivelCompetencia.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?nivelCompetencia.id=${nivelCompetencia.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="ordem" title="Ordem" style="width:50px; text-align:right;"/>
		<@display.column property="descricao" title="Descrição"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
