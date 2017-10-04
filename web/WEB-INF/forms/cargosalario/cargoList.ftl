<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#include "../ftl/showFilterImports.ftl" />
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>Cargos</title>
	
	<script type="text/javascript">
		function pesquisar()
		{
			$('#pagina').val(1);
			$('#formBusca').attr('action','list.action');
			$('#formBusca').submit();
		}

		function imprimir()
		{ 
			$('#formBusca').attr('action','imprimirLista.action');
			$('#formBusca').submit();
		}
	</script>
	
	<#if empresaIntegradaEAderiuAoESocial>
		<#assign empresaEstaIntegradaEAderiuAoESocial=true/>
	<#else>
		<#assign empresaEstaIntegradaEAderiuAoESocial=false/>
	</#if>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" validate="true" method="POST" id="formBusca">
			<@ww.textfield label="Nomenclatura Mercado" name="cargo.nomeMercado" cssStyle="width: 350px;"/>
			<@ww.select label="Área Organizacional" name="areaOrganizacional.id" list="areas" listKey="id" listValue="descricao" headerValue="" headerKey="-1" cssStyle="width:445px;"/>
			<@ww.checkbox label="Ativos" name="cargo.ativo" labelPosition="left"/>
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<input type="button" value="" onclick="pesquisar();" class="btnPesquisar grayBGE">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="cargos" id="cargo" class="dados" >
		<@display.column title="Ações" class="acao" style="width: 120px;">
			<@authz.authorize ifAllGranted="ROLE_TRANSFERIR_FAIXAS_AC">
				<a href="prepareTransferirFaixasCargo.action?cargo.id=${cargo.id}"><img border="0" title="Transferir Faixas para este cargo" src="<@ww.url includeParams="none" value="/imgs/faixas.gif"/>"></a>
			</@authz.authorize>
		
			<@authz.authorize ifAllGranted="ROLE_CAD_FAIXA_SALARIAL">
				<a href="../faixaSalarial/list.action?cargo.id=${cargo.id}"><img border="0" title="Faixas Salariais" src="<@ww.url includeParams="none" value="/imgs/insertCell.gif"/>"></a>
			</@authz.authorize>
			<@authz.authorize ifAllGranted="ROLE_CAD_CARGO_EDITAR">
				<a href="prepareUpdate.action?cargo.id=${cargo.id}&page=${page}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			</@authz.authorize>
			<@authz.authorize ifAllGranted="ROLE_CAD_CARGO_EXCLUIR">
				<#if !empresaEstaIntegradaEAderiuAoESocial || !cargo.possuiFaixaSalarial>
					<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?cargo.id=${cargo.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
				<#elseif empresaEstaIntegradaEAderiuAoESocial && cargo.possuiFaixaSalarial>
					<@ww.hidden name="cargo.possuiFaixaSalarial"/>
					<img border="0" title="Devido as adequações ao eSocial, não é possível excluir cargo que possui faixa salarial." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.5;filter:alpha(opacity=50);">
				</#if>
			</@authz.authorize>
			<@authz.authorize ifAllGranted="ROLE_CAD_CARGO_IMPRIMIR">
				<a href="imprimir.action?cargo.id=${cargo.id}"><img border="0" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			</@authz.authorize>
		</@display.column>
		<@display.column property="nome" title="Nomenclatura"/>
		<@display.column property="nomeMercado" title="Nomenclatura Mercado"/>
		<@display.column property="grupoOcupacional.nome" title="Grupo Ocupacional"/>
	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>

	<div class="buttonGroup">
		<@authz.authorize ifAllGranted="ROLE_CAD_CARGO_INSERIR">
			<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I"></button>
		</@authz.authorize>
		<button class="btnImprimir" onclick="imprimir();" accesskey="P"></button>
	</div>
</body>
</html>