<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<title>Extintores</title>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('form', null, new Array('inicio','fim'), true)"/>

	<#include "../ftl/showFilterImports.ftl" />

</head>
<body>

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" onsubmit="${validarCampos}" method="POST">

		<@ww.select label="Tipo" id="tipo" required="true" name="tipoBusca" list=r"#{'T':'Todos','1':'AG','2':'AP','4':'CO2','3':'PQS'}" />
		<@ww.textfield label="Número do cilindro" name="numeroBusca" id="numeroBusca" cssStyle="width:50px;text-align:right;" maxLength="40" onkeypress="return(somenteNumeros(event,''));"/>
		<@ww.select label="Ativo" name="ativo" id="ativo" list=r"#{'T':'Todos','S':'Sim','N':'Não'}" />

		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" onclick="document.getElementById('pagina').value = 1;" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<@display.table name="extintors" id="extintor" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?extintor.id=${extintor.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?extintor.id=${extintor.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" title="Descrição" style="width:300px;"/>
		<@display.column property="localizacao" title="Localização" style="width:200px;"/>
		<@display.column property="numeroCilindro" title="Cilindro" style="width:60px;"/>
		<@display.column property="tipoDicAbreviado" title="Tipo" style="width:70px;"/>
	</@display.table>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

<div class="buttonGroup">
<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="N"></button>
</div>
</body>
</html>