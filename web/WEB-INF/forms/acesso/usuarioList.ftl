<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>

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
			if(document.getElementById('nomeBusca').value != "" || document.getElementById('cpfBusca').value != "")
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
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Usuários</title>


<#include "../ftl/showFilterImports.ftl" />
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formPage" action="list.action" validate="true" method="POST" id="formBusca">
			<@ww.textfield label="Nome do usuário" name="usuario.nome" id="usuario.nome" liClass="liLeft" cssStyle="width: 350px;"/>
			<@ww.hidden id="pagina" name="page"/>
			<@ww.select label="Empresa do usuário" name="empresa.id" list="empresas" listKey="id" listValue="nome" headerValue="Todas as Empresas Cadastradas" headerKey=""/>
			<button class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1; document.formPage.submit();" accesskey="B">
			</button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />


	<br>
	<@display.table name="usuarios" id="usuario" class="dados" defaultsort=2 >
		<@display.column title="Ações" media="html" class="acao">
			<a href="javascript: executeLink('prepareUpdate.action?usuario.id=${usuario.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?usuario.id=${usuario.id}&page=${page}');});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column property="login" title="Login"/>
		<@display.column title="Ativo" style="width: 20px; text-align: center;">
			<#if usuario.acessoSistema?exists && usuario.acessoSistema>
				<img border="0" style="align: top;" src="<@ww.url includeParams="none" value="/imgs/Ok.gif"/>">
			<#else>
				<img border="0" style="align: top;" src="<@ww.url includeParams="none" value="/imgs/notOk.gif"/>">
			</#if>
		</@display.column>
	</@display.table>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>


	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');" accesskey="I"></button>
		<button class="btnCriarUsuariosAuto" onclick="javascript: executeLink('prepareAutoInsert.action');" accesskey="I"></button>
		<button class="btnImprimirPdf" onclick="javascript: executeLink('imprimirUsuariosPerfis.action');">
	</div>
</body>
</html>