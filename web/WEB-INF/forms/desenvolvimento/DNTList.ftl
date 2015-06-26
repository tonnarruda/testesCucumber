<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>DNT - Diagnóstico de Necessidade de Treinamento</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<@display.table name="dnts" id="d" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="listDetalhes.action?dnt.id=${d.id}"><img border="0" title="Colaborador X Curso X Prioridade" src="<@ww.url value="/imgs/usuarios.gif"/>"></a>
			<a href="prepareUpdate.action?dnt.id=${d.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?dnt.id=${d.id}&page=${page}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome" />
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width: 80px;"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
	<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
	</button>
	</div>
</body>
</html>