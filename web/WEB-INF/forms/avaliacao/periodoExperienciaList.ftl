<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Períodos de Acompanhamento de Experiência</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="periodoExperiencias" id="periodoExperiencia" class="dados" style="width:430px;">
		<@display.column title="Ações" class="acao" style="width:50px;">
			<a href="javascript: executeLink('prepareUpdate.action?periodoExperiencia.id=${periodoExperiencia.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?periodoExperiencia.id=${periodoExperiencia.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column title="Dias" property="dias" style="width:80px;" />
		<@display.column title="Descrição" property="descricao" style="width:250px;" />
		<@display.column title="Ativo" style="width: 50px; text-align: center;">
			<#if periodoExperiencia.ativo>
				<img border="0" style="align: top;" src="<@ww.url includeParams="none" value="/imgs/Ok.gif"/>">
			<#else>
				<img border="0" style="align: top;" src="<@ww.url includeParams="none" value="/imgs/notOk.gif"/>">
			</#if>
		</@display.column>
		
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');"></button>
	</div>
</body>
</html>
