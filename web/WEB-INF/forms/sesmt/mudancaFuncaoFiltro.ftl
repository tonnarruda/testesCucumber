<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<script>
		function prepareBuscar()
		{
			if(validaCampos())
			{
				document.formBusca.submit();
			}
		}

		function validaCampos()
		{
			if(document.getElementById('nomeBusca').value != "")
			{
				return true;
			}
			else
			{
				jAlert("É obrigatório o preenchimento de pelo menos um campo da pesquisa");
				return false;
			}
		}

	</script>

	<#if areaBusca?exists && areaBusca.id?exists>
		<#assign areaId = areaBusca.id>
	<#else>
		<#assign areaId = "">
	</#if>

	<#if nomeBusca?exists>
		<#assign nomeBuscado = nomeBusca>
	<#else>
		<#assign nomeBuscado = "">
	</#if>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>Mudança de Função</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="mudancaFuncaoFiltro.action" validate="true" method="POST" id="formBusca">
			<@ww.select label="Área Organizacional" name="areaBusca.id" list="areas" headerKey="" headerValue="[Selecione...]" listKey="id" listValue="descricao" cssStyle="width: 450px;"/>
			<@ww.textfield label="Nome Comercial" name="nomeBusca" id="nomeBusca" cssStyle="width: 350px;"/>
			<@ww.hidden id="pagina" name="page"/>
		</@ww.form>

		<button class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1; document.formBusca.submit();" accesskey="B">
		</button>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="colaboradors" id="colaborador" pagesize=20 class="dados" defaultsort=3 >
	<#assign style=""/>
		<@display.column title="Ações" media="html" class="acao" style = "width:130px;">
			<a href="javascript:window.location='prepareMudancaFuncao.action?colaborador.id=${colaborador.id}&areaBusca.id=${areaId}&nomeBusca=${nomeBuscado}&page=${page}'"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
		</@display.column>

		<#if colaborador?exists && colaborador.dataDesligamento?exists>
			<#assign style="color:#e36f6f;"/>
		</#if>

		<@display.column property="matricula" title="Matrícula" style='${style}'/>
		<@display.column property="nomeComercial" title="Nome Comercial" style='${style}'/>
		<@display.column property="pessoal.cpf" title="CPF" style='${style}'/>
		<@display.column property="dataAdmissao" title="Data de Admissão" format="{0,date,dd/MM/yyyy}" style='${style}'/>
	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>


</body>
</html>