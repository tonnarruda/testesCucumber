<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
	<#include "../ftl/showFilterImports.ftl" />
	<#assign validarCampos="return validaFormulario('formBusca', null, null, true)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<title>Avaliacões dos Alunos</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" onsubmit="${validarCampos}" validate="true" method="POST" id="formBusca">
			<@ww.textfield label="Nome" name="avaliacaoCurso.titulo" cssStyle="width: 350px;"/>
			<@ww.hidden id="pagina" name="page"/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	<@display.table name="avaliacaoCursos" id="avaliacaoCurso" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?avaliacaoCurso.id=${avaliacaoCurso.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?avaliacaoCurso.id=${avaliacaoCurso.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="titulo" title="Título"/>
		<@display.column title="Tipo" style="width: 150px;">
			${tipos.getDescricao(avaliacaoCurso.tipo)}
		</@display.column>
		<@display.column title="Mínimo para Aprovação" style="width: 200px;text-align:right;">
			${avaliacaoCurso.minimoAprovacao?string(",##0.00")}
		</@display.column>
	</@display.table>
	
	<div class="buttonGroup">
		<button onclick="window.location='prepareInsert.action'" class="btnInserir">
		</button>
	</div>
</body>
</html>