<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Comissões</title>
</head>
<body>
<@ww.actionmessage />
<@ww.actionerror />
<@display.table name="comissaos" id="comissao" class="dados" style="width: 480px;">
	<@display.column title="Ações" class="acao">
		<a href="javascript: executeLink('prepareUpdate.action?comissao.id=${comissao.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
		<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?comissao.id=${comissao.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
	</@display.column>
	<@display.column property="periodoFormatado" title="Período" style="width: 140px;"/>
	<@display.column property="eleicao.estabelecimento.nome" title="Estabelecimento" style="width: 220px;"/>
</@display.table>

<div class="buttonGroup">
	<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');" accesskey="I"></button>
</button>
</div>
</body>
</html>