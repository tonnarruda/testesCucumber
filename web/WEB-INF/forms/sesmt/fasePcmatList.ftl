<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
			#menuPcmat a#menuFases{ border-bottom: 2px solid #5292C0; }
			.crono { width: 120px; text-align: center; }
		</style>
		
		<title>PCMAT</title>
	</head>
	<body>
		<#include "pcmatLinks.ftl"/>
		
		<@ww.actionmessage />
		<@ww.actionerror />
		
		<@display.table name="fasesPcmat" id="fasePcmat" class="dados">
			<@display.column title="Ações" class="acao">
				<a href="../riscoFasePcmat/list.action?fasePcmat.id=${fasePcmat.id}&pcmatId=${pcmat.id}&ultimoPcmatId=${ultimoPcmatId}"><img border="0" title="Riscos e medidas de segurança" src="<@ww.url value="/imgs/form2.gif"/>"></a>
				<a href="prepareUpdate.action?fasePcmat.id=${fasePcmat.id}&ultimoPcmatId=${ultimoPcmatId}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<#if ultimoPcmatId == pcmat.id>	
					<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?fasePcmat.id=${fasePcmat.id}&pcmat.id=${pcmat.id}&ultimoPcmatId=${ultimoPcmatId}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
				<#else>
					<img border="0" title="Só é possível excluir fases do último PCMAT" src="<@ww.url value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				</#if>
				
				<@ww.hidden name="fasePcmatId" value="${fasePcmat.id}"/>
			</@display.column>
			<@display.column title="Fase" property="fase.descricao"/>
			<@display.column title="Cronograma" class="crono">
				${fasePcmat.mesIni}&ordm; ao ${fasePcmat.mesFim}&ordm; mês
			</@display.column>
		</@display.table>
		
		<div class="buttonGroup">
			<#if ultimoPcmatId == pcmat.id>
				<button class="btnInserir" onclick="window.location='prepareInsert.action?fasePcmat.pcmat.id=${pcmat.id}&ultimoPcmatId=${ultimoPcmatId}'"></button>
			</#if>
		</div>
	</body>
</html>
