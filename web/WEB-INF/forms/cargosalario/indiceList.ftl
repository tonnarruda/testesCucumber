<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<#include "../ftl/showFilterImports.ftl" />
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<title>Índices</title>
	
	<script type="text/javascript">
		function pesquisar() {
			$('#pagina').val(1);
			$('#formBusca').attr('action','list.action');
			$('#formBusca').submit();
		}
	</script>
	
</head>
<body>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" validate="true" method="POST" id="formBusca">
			<@ww.textfield label="Nome" name="indiceAux.nome" cssStyle="width: 350px;"/>
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<input type="button" value="" onclick="pesquisar();" class="btnPesquisar grayBGE">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="indices" id="indice" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?indiceAux.id=${indice.id}"><img title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<#if !integradoAC>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?indiceAux.id=${indice.id}'});"><img title="<@ww.text name="list.del.hint"/>" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</#if>
		</@display.column>
		<@display.column property="nome" title="Nome"/>	
		<#if integradoAC>
			<@display.column property="grupoAC" title="Grupo AC" style="width: 50px;"/>	
		</#if>
	</@display.table>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}' />
	
	<#if !integradoAC>
		<div class="buttonGroup">
			<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
			</button>
		</div>
	</#if>
</body>
</html>