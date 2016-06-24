<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>PCMAT</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" validate="true" method="POST" id="formBusca">
			<@ww.textfield label="Obra" name="nomeObra" id="nomeObra" cssStyle="width: 350px;"/>
			<button type="submit" class="btnPesquisar grayBGE"></button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	
	<br />
	
	<@display.table name="obras" id="obra" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="javascript: executeLink('listPcmats.action?obra.id=${obra.id}');"><img border="0" title="Listar PCMATs" src="<@ww.url value="/imgs/form2.gif"/>"></a>
		</@display.column>
		<@display.column title="Obra" property="nome"/>
	</@display.table>
</body>
</html>
