<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign validarCampos="return validaFormulario('formBusca', null, null, true)"/>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>Cargos</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" onsubmit="${validarCampos}" validate="true" method="POST" id="formBusca">
			<@ww.textfield label="Nomenclatura Mercado" name="cargo.nomeMercado" cssStyle="width: 350px;"/>
			<@ww.select label="Área Organizacional" name="areaOrganizacional.id" list="areas" listKey="id" listValue="descricao" headerValue="" headerKey="-1" cssStyle="width:445px;"/>
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="cargos" id="cargo" class="dados" >
		<@display.column title="Ações" class="acao" style="width: 120px;">
		<@authz.authorize ifAllGranted="ROLE_TRANSFERIR_FAIXAS_AC">
			<a href="prepareTransferirFaixasCargo.action?cargo.id=${cargo.id}"><img border="0" title="Transferir Faixas para este cargo" src="<@ww.url includeParams="none" value="/imgs/faixas.gif"/>"></a>
		</@authz.authorize>
			<a href="../../sesmt/funcao/list.action?cargoTmp.id=${cargo.id}"><img border="0" title="Funções" src="<@ww.url includeParams="none" value="/imgs/db_add.gif"/>"></a>
			<a href="../faixaSalarial/list.action?cargo.id=${cargo.id}"><img border="0" title="Faixas Salariais" src="<@ww.url includeParams="none" value="/imgs/insertCell.gif"/>"></a>
			<a href="prepareUpdate.action?cargo.id=${cargo.id}&page=${page}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?cargo.id=${cargo.id}'"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			<a href="imprimir.action?cargo.id=${cargo.id}"><img border="0" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nomenclatura"/>
		<@display.column property="nomeMercado" title="Nomenclatura Mercado"/>
		<@display.column property="grupoOcupacional.nome" title="Grupo Ocupacional"/>
	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>