<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
		</style>
		
		<title>Riscos e Medidas de Segurança</title>
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		
		<#include "pcmatLinks.ftl"/>
		
		<@display.table name="riscosFasePcmat" id="riscoFasePcmat" class="dados">
			<@display.column title="Ações" class="acao">
				<a href="prepareUpdate.action?riscoFasePcmat.id=${riscoFasePcmat.id}&riscoFasePcmat.fasePcmat.id=${fasePcmat.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?riscoFasePcmat.id=${riscoFasePcmat.id}&fasePcmat.id=${fasePcmat.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			</@display.column>
			<@display.column title="Risco" property="risco.descricao"/>
		</@display.table>
		
		<div class="buttonGroup">
			<button class="btnInserir" onclick="window.location='prepareInsert.action?riscoFasePcmat.fasePcmat.id=${fasePcmat.id}'"></button>
		</div>
	</body>
</html>
