<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
<title>Aspectos da Avaliação</title>
</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>
	<@display.table name="aspectos" id="aspecto" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?aspecto.id=${aspecto.id}&avaliacao.id=${avaliacao.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='deleteAvaliacao.action?aspecto.id=${aspecto.id}&avaliacao.id=${avaliacao.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
	</@display.table>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action" page='${page}'/>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?avaliacao.id=${avaliacao.id}'"></button>
		<button onclick="window.location='../../avaliacao/modelo/list.action'" class="btnVoltar"></button>
	</div>
</body>
</html>