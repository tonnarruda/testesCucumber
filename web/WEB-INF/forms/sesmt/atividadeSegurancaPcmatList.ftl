<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		#menuPcmat a#menuAtividadesSeguranca { border-bottom: 2px solid #5292C0; }
	</style>

	<title>PCMAT</title>
</head>
<body>
	<#include "pcmatLinks.ftl"/> 
	
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="atividadesSegurancaPcmat" id="atividadeSegurancaPcmat" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?atividadeSegurancaPcmat.id=${atividadeSegurancaPcmat.id}&ultimoPcmatId=${ultimoPcmatId}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
		
			<#if ultimoPcmatId == pcmat.id>	
				<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?atividadeSegurancaPcmat.id=${atividadeSegurancaPcmat.id}&pcmat.id=${pcmat.id}&ultimoPcmatId=${ultimoPcmatId}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			<#else>
				<img border="0" title="Só é possível excluir atividade de segurança do último PCMAT" src="<@ww.url value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
		
		</@display.column>
		<@display.column title="Nome" property="nome"/>
		<@display.column title="Mês/Ano" property="data" format="{0,date,MM/yyyy}" style="width:90px;text-align:center;"/>
		<@display.column title="Carga Horária" property="cargaHorariaEmHora" style="width:90px;text-align:center;"/>
	</@display.table>
	
	<div class="buttonGroup">
		<#if ultimoPcmatId == pcmat.id>
			<button class="btnInserir" onclick="window.location='prepareInsert.action?atividadeSegurancaPcmat.pcmat.id=${pcmat.id}&ultimoPcmatId=${ultimoPcmatId}'"></button>
		</#if>
	</div>
</body>
</html>
