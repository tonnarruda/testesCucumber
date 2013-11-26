<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
			.ordem { width: 40px; text-align: right; }
		</style>
		
		<title>Fases</title>
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		
		<#include "pcmatLinks.ftl"/>
		
		<@display.table name="fasesPcmat" id="fasePcmat" class="dados">
			<@display.column title="Ações" class="acao">
				<a href="prepareUpdate.action?fasePcmat.id=${fasePcmat.id}&fasePcmat.pcmat.id=${pcmat.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?fasePcmat.id=${fasePcmat.id}&pcmat.id=${pcmat.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			</@display.column>
			<@display.column title="Ordem" property="ordem" class="ordem"/>
			<@display.column title="Fase" property="fase.descricao"/>
		</@display.table>
		
		<div class="buttonGroup">
			<button class="btnInserir" onclick="window.location='prepareInsert.action?fasePcmat.pcmat.id=${pcmat.id}'"></button>
		</div>
	</body>
</html>
