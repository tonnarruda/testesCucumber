<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<title>Afastamentos</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('form', null, new Array('inicio','fim'), true)"/>

	<#include "../ftl/showFilterImports.ftl" />

	<#if colaboradorAfastamento.inicio?exists >
		<#assign inicio = colaboradorAfastamento.inicio?date/>
	<#else>
		<#assign inicio = ""/>
	</#if>
	<#if colaboradorAfastamento.fim?exists>
		<#assign fim = colaboradorAfastamento.fim?date/>
	<#else>
		<#assign fim = ""/>
	</#if>

</head>
<body>

<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" onsubmit="${validarCampos}" method="POST">
		Período:<br>
		<@ww.datepicker name="colaboradorAfastamento.inicio" id="inicio"  value="${inicio}" liClass="liLeft" cssClass="mascaraData"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="colaboradorAfastamento.fim" id="fim" value="${fim}" cssClass="mascaraData" />

		<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" cssStyle="width: 260px;"/>
		<@ww.textfield label="Colaborador" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>
		<@ww.select label="Motivo" name="colaboradorAfastamento.afastamento.id" id="tipo" list="afastamentos" listKey="id" listValue="descricao" headerKey="" headerValue="Todos"/>

		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" onclick="document.getElementById('pagina').value = 1;" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<@display.table name="colaboradorAfastamentos" id="colaboradorAfastamento" class="dados" >
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?colaboradorAfastamento.id=${colaboradorAfastamento.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?colaboradorAfastamento.id=${colaboradorAfastamento.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="colaborador.matricula" title="Matrícula" style="width:45px;"/>
		<@display.column property="colaborador.nome" title="Colaborador" style="width:310px;"/>
		<@display.column property="periodoFormatado" title="Período" style="width:153px;" />
		<@display.column property="colaborador.estabelecimento.nome" title="Estabelecimento" style="width:180px;"/>
		<@display.column property="afastamento.descricao" title="Motivo" style="width:180px;"/>
		<@display.column property="cid" title="CID" style="width:50px;"/>
	</@display.table>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="N">
		</button>
	</div>
</body>
</html>