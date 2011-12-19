<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<title>Cursos</title>

<#include "../ftl/showFilterImports.ftl" />

</head>
<body>
	<#assign linkFiltro=""/>
	<#if nomeCursoBusca?exists>
		<#assign linkFiltro="${linkFiltro}&nomeCursoBusca=${nomeCursoBusca}"/>
	</#if>

	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" method="POST">
		<@ww.textfield label="Nome" name="nomeCursoBusca" id="nome" cssClass="inputNome" maxLength="100" cssStyle="width: 340px;"/>
		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="cursos" id="curso" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?curso.id=${curso.id}&curso.empresa.id=${curso.empresa.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="../turma/list.action?curso.id=${curso.id}"><img border="0" title="Turmas" src="<@ww.url value="/imgs/db_add.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?curso.id=${curso.id}&curso.empresa.id=${curso.empresa.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column property="cargaHorariaMinutos" title="Carga Horária" style="text-align:right;"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="N">
		</button>
	</div>
</body>
</html>