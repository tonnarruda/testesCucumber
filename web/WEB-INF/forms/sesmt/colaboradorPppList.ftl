<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<#include "../ftl/mascarasImports.ftl" />

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#include "../ftl/showFilterImports.ftl" />

	<#assign validarCampos="return validaFormulario('formBusca', null, null, true)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>Lista de Colaboradores</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" validate="true" onsubmit="${validarCampos}"  method="POST" id="formBusca">
			<@ww.textfield label="Nome Comercial" name="nomeBusca" id="nomeBusca" liClass="liLeft" cssStyle="width: 350px;"/>
			<@ww.textfield label="CPF" name="cpfBusca" id="cpfBusca" cssClass="mascaraCpf"/>
			<@ww.hidden id="pagina" name="page"/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;${validarCampos}">
		</@ww.form>

	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="colaboradors" id="colaborador" pagesize=20 class="dados" defaultsort=3 >
	<#assign style=""/>
		<@display.column title="Ações" media="html" class="acao" style = "width:130px;">
			<a href="javascript:window.location='prepareRelatorio.action?colaborador.id=${colaborador.id}'"><img border="0" title="Gerar PPP" src="<@ww.url includeParams="none" value="/imgs/report.gif"/>"></a>
		</@display.column>

		<#if colaborador?exists && colaborador.dataDesligamento?exists>
			<#assign style="color:#e36f6f;"/>
		</#if>

		<@display.column property="matricula" title="Matrícula" style='${style}'/>
		<@display.column property="nomeMaisNomeComercial" title="Nome Comercial" style='${style}'/>
		<@display.column property="pessoal.cpf" title="CPF" style='${style}'/>
		<@display.column property="dataAdmissao" title="Data de Admissão" format="{0,date,dd/MM/yyyy}" style='${style}'/>
	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>
</body>
</html>