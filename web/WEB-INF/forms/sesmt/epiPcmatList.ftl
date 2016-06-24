<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		#menuPcmat a#menuEPIs { border-bottom: 2px solid #5292C0; }
	</style>

	<title>PCMAT</title>
</head>
<body>
	<#include "pcmatLinks.ftl"/> 

	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="epiPcmats" id="epiPcmat" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="javascript: executeLink('prepareUpdate.action?epiPcmat.id=${epiPcmat.id}&ultimoPcmatId=${ultimoPcmatId}');"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			
			<#if ultimoPcmatId == pcmat.id>	
				<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){executeLink('delete.action?epiPcmat.id=${epiPcmat.id}&pcmat.id=${pcmat.id}&ultimoPcmatId=${ultimoPcmatId}');});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			<#else>
				<img border="0" title="Só é possível excluir EPI do último PCMAT" src="<@ww.url value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
		
		</@display.column>
		<@display.column title="Nome" property="epi.nome"/>
	</@display.table>
	
	<div class="buttonGroup">
		<#if ultimoPcmatId == pcmat.id>
			<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action?epiPcmat.pcmat.id=${pcmat.id}&ultimoPcmatId=${ultimoPcmatId}');"></button>
		</#if>
	</div>
</body>
</html>
