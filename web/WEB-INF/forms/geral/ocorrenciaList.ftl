<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
<title>Tipos de Ocorrências</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" method="POST" id="formBusca">
			<@ww.textfield label="Descrição" name="ocorrencia.descricao" cssStyle="width: 350px;"/>
			
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<input type="button" value="" onclick="document.getElementById('pagina').value = 1; document.formBusca.submit();"  class="btnPesquisar grayBGE">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	<@display.table name="ocorrencias" id="ocorrencia" class="dados">
		<@display.column title="Ações" class="acao">
			<@frt.link verifyRole="ROLE_CAD_OCORRENCIA_EDITAR" href="prepareUpdate.action?ocorrencia.id=${ocorrencia.id}" imgTitle="Editar" imgName="edit.gif"/>
			<@frt.link verifyRole="ROLE_CAD_OCORRENCIA_EXCLUIR" href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?ocorrencia.id=${ocorrencia.id}&page=${page}'});" imgTitle="Excluir" imgName="delete.gif"/>
		</@display.column>
		<@display.column property="descricao" title="Descrição"/>
		<@display.column property="pontuacao" title="Pontuação" style = "width:80px;"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<@authz.authorize ifAllGranted="ROLE_CAD_OCORRENCIA_INSERIR">
			<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I"></button>
		</@authz.authorize>
	</div>
</body>
</html>