<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Funções - ${cargoTmp.nome}</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@display.table name="funcaos" id="funcao" class="dados" sort="list">
		<@display.column title="Ações" class="acao">
			<a href="javascript:;" onclick="javascript: executeLink('prepareUpdate.action?funcao.id=${funcao.id}&cargoTmp.id=${cargoTmp.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?funcao.id=${funcao.id}&cargoTmp.id=${cargoTmp.id}&page=${page}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?cargoTmp.id=${cargoTmp.id}&" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action?cargoTmp.id=${cargoTmp.id}');" accesskey="I">
		</button>
		<button onclick="window.location='../../cargosalario/cargo/list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>