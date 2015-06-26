<!--
  Autor: Robertson Freitas
  Data: 23/06/2006 
  Requisito: RFA015
 -->
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Solicitações Recebidas</title>
</head>
<body>
<@display.table name="solicitacaos" id="solicitacao" pagesize=10 class="dados" defaultsort=2 >
	<@display.column title="Ações" media="html" class="acao">
		<a href="prepareRecebidasUpdate.action?solicitacao.id=${solicitacao.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
	</@display.column>
	<@display.column property="cargo.nome" title="Cargo"/>	
	<@display.column property="areaOrganizacional.nome" title="Área"/>	
	<@display.column property="solicitador.nome" title="Solicitante"/>	
	<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}"/>
	<@display.column property="situacao" title="Situação"/>
</@display.table>


</body>
</html>