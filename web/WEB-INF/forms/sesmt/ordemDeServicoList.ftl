<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>Ordens de Serviço - ${colaborador.nome}</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="ordensDeServico" id="ordemDeServico" class="dados">
		<@display.column title="Ações" class="acao" style="width:95px";>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?ordemDeServico.id=${ordemDeServico.id}&colaborador.id=${colaborador.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<a href="visualizar.action?ordemDeServico.id=${ordemDeServico.id}"><img border="0" title="Visualizar" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			<a href="prepareUpdate.action?ordemDeServico.id=${ordemDeServico.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="imprimir.action?ordemDeServico.id=${ordemDeServico.id}"><img border="0" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
		</@display.column>
		<@display.column property="revisao" title="Nº da Revisão"/>
		<@display.column property="dataFormatada" title="Data"/>

	</@display.table>

	<@ww.hidden id="pagina" name="page"/>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?colaborador.id=${colaborador.id}'"></button>
		<button onclick="window.location='listGerenciamentoOS.action'" class="btnVoltar"></button>
	</div>

</body>
</html>