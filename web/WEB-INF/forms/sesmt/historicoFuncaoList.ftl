<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Historicos de Funcões</title>
</head>
<body>
<@display.table name="historicoFuncaos" id="historicoFuncao" pagesize=10 class="dados" defaultsort=2 sort="list">
	<@display.column title="Ações" class="acao">
		<a href="prepareUpdate.action?historicoFuncao.id=${historicoFuncao.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
		<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?historicoFuncao.id=${historicoFuncao.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
	</@display.column>
	<@display.column property="id" title="Id"/>
	<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}"/>
	<@display.column property="atividade" title="Atividade"/>
	<@display.column property="funcao.id" title="Funcao"/>
</@display.table>

<div class="buttonGroup">
<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
</button>
</div>
</body>
</html>