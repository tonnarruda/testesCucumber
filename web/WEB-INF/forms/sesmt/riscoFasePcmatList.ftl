<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
			#menuPcmat a#menuFases { border-bottom: 2px solid #5292C0; }
		</style>
		
		<title>PCMAT - Fase ${fasePcmat.fase.descricao} - Riscos e Medidas de Segurança</title>
	</head>
	<body>
		<#include "pcmatLinks.ftl"/>
		
		<@ww.actionmessage />
		<@ww.actionerror />
		
		<@display.table name="riscosFasePcmat" id="riscoFasePcmat" class="dados">
			<@display.column title="Ações" class="acao">
				<a href="prepareUpdate.action?riscoFasePcmat.id=${riscoFasePcmat.id}&riscoFasePcmat.fasePcmat.id=${fasePcmat.id}&pcmatId=${fasePcmat.pcmat.id}&ultimoPcmatId=${ultimoPcmatId}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				
				<#if ultimoPcmatId == fasePcmat.pcmat.id>	
					<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?riscoFasePcmat.id=${riscoFasePcmat.id}&fasePcmat.id=${fasePcmat.id}&pcmatId=${fasePcmat.pcmat.id}&ultimoPcmatId=${ultimoPcmatId}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
				<#else>
					<img border="0" title="Só é possível excluir risco do último PCMAT" src="<@ww.url value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				</#if>
				
			</@display.column>
			<@display.column title="Risco" property="risco.descricao"/>
		</@display.table>
		
		<div class="buttonGroup">
			<#if ultimoPcmatId == fasePcmat.pcmat.id>
				<button class="btnInserir" onclick="window.location='prepareInsert.action?riscoFasePcmat.fasePcmat.id=${fasePcmat.id}&pcmatId=${fasePcmat.pcmat.id}&ultimoPcmatId=${ultimoPcmatId}'"></button>
			</#if>
			<button class="btnVoltar" onclick="window.location='../fasePcmat/list.action?pcmat.id=${fasePcmat.pcmat.id}&pcmatId=${fasePcmat.pcmat.id}&ultimoPcmatId=${ultimoPcmatId}'"></button>
		</div>
	</body>
</html>
