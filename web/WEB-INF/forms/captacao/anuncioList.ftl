<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Anúncios</title>
</head>
<body>
<@display.table name="anuncios" id="anuncio" pagesize=10 class="dados" defaultsort=2 sort="list">
	<@display.column title="Ações" class="acao">
		<a href="prepareUpdate.action?anuncio.id=${anuncio.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
		<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?anuncio.id=${anuncio.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
	</@display.column>
	<@display.column property="id" title="Id"/>
	<@display.column property="titulo" title="Titulo"/>
	<@display.column property="cabecalho" title="Cabecalho"/>
	<@display.column property="informacoes" title="Informacoes"/>
	<@display.column property="solicitacao.id" title="Solicitacao"/>
	<@display.column property="mostraConhecimento" title="MostraConhecimento"/>
	<@display.column property="mostraBeneficio" title="MostraBeneficio"/>
	<@display.column property="mostraSalario" title="MostraSalario"/>
	<@display.column property="mostraCargo" title="MostraCargo"/>
	<@display.column property="mostraSexo" title="MostraSexo"/>
	<@display.column property="mostraIdade" title="MostraIdade"/>
	<@ww.hidden name="empresaId" />
</@display.table>


<div class="buttonGroup">
<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
</button>
</div>
</body>
</html>