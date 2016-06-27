<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Bairros</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<script type="text/javascript">
		function populaCidades()
		{
			if(document.getElementById('estado').value == "")
			{
				DWRUtil.removeAllOptions("cidade");
			}else{
				DWRUtil.useLoadingMessage('Carregando...');
				CidadeDWR.getCidades(createListCidades, document.getElementById("estado").value);
			}
		}

		function createListCidades(data)
		{
			DWRUtil.removeAllOptions("cidade");
			DWRUtil.addOptions("cidade", data);
		}
	</script>


	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formPage" action="list.action" validate="true" method="POST" id="formBusca">
			<@ww.hidden id="pagina" name="page"/>

			<@ww.select label="Estado" name="estado.id" id="estado" list="estados" listKey="id" listValue="nome" liClass="liLeft" cssStyle="width: 150px;" headerKey="" headerValue="" onchange="javascript:populaCidades()"/>
			<@ww.select label="Cidade" name="bairro.cidade.id" id="cidade" list="cidades" listKey="id" listValue="nome" cssStyle="width: 250px;" headerKey="" headerValue=""/>

			<@ww.textfield label="Bairro" name="bairro.nome" id="nome" cssStyle="width: 400px;"  maxLength="20"/>
			<button class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1; document.formPage.submit();">
			</button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="bairros" id="bairro" class="dados" sort="list">
		<@display.column title="Ações" style="text-align:center; width: 80px" media="html">
			<a href="javascript: executeLink('prepareUpdate.action?bairro.id=${bairro.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?bairro.id=${bairro.id}');});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column property="cidade.nome" title="Cidade"/>
	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');" ></button>
		<button class="btnUnirBairros" onclick="javascript: executeLink('prepareMigrar.action');" ></button>
	</div>
</body>
</html>