<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<title>PCMAT</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="obras" id="obra" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="listPcmats.action?obra.id=${obra.id}"><img border="0" title="Listar PCMATs" src="<@ww.url value="/imgs/form2.gif"/>"></a>
		</@display.column>
		<@display.column title="Obra" property="nome"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
