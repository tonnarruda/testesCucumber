<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<title>Históricos Faixa Salarial</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="faixaSalarialHistoricos" id="faixaSalarialHistorico" pagesize=10 class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?faixaSalarialHistorico.id=${faixaSalarialHistorico.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?faixaSalarialHistorico.id=${faixaSalarialHistorico.id}&faixaSalarialHistorico.status=${faixaSalarialHistorico.status}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}"/>
		<@display.column property="tipo" title="Tipo"/>	
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>