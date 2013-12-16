<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		#menuPcmat a#menuEPCs { color: #FFCB03; }
	</style>

	<title>PCMAT</title>
</head>
<body>
	<#include "pcmatLinks.ftl"/>

	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="epcPcmats" id="epcPcmat" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?epcPcmat.id=${epcPcmat.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?epcPcmat.id=${epcPcmat.id}&pcmat.id=${pcmat.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column title="Nome" property="epc.descricao"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?epcPcmat.pcmat.id=${pcmat.id}'"></button>
	</div>
</body>
</html>
