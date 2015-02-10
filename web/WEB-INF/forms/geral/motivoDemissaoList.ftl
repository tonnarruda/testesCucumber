<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<title>Motivos de Desligamento</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@display.table name="motivoDemissaos" id="motivoDemissao" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?motivoDemissao.id=${motivoDemissao.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?motivoDemissao.id=${motivoDemissao.id}&page=${page}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="motivo" title="Motivo"/>
		<@display.column title="Turnover" style="width: 120px; text-align: center;">
			<#if motivoDemissao.turnover>
				Sim
			<#else>
				Não
			</#if>
		</@display.column>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="N">
		</button>
	</div>
</body>
</html>