<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<title>Extintores - Troca de Localização</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('form', null, new Array('data'), true)"/>

	<#include "../ftl/showFilterImports.ftl" />
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>'></script>

    <#if data?exists >
		<#assign dt = data?date/>
	<#else>
		<#assign dt = ""/>
	</#if>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" onsubmit="${validarCampos}" method="POST">
		A partir de:<br>
		<@ww.datepicker name="data" id="data"  value="${dt}" cssClass="mascaraData"/>
		<@ww.select label="Estabelecimento" id="estabelecimentoId" name="estabelecimentoId" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Todos" headerKey="" cssStyle="width:240px;"/>
		<@ww.select label="Tipo" id="tipo" name="tipo" list="tipos" headerKey="" headerValue="Todos"/>
		<@ww.textfield label="Localização" name="localizacao" id="localizacao" maxLength="50" cssStyle="width:240px;"/>
		<@ww.hidden id="showFilter" name="showFilter"/>
		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" id="btnPesquisar" value="" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<@display.table name="historicoExtintores" id="historicoExtintor" class="dados">
		<@display.column title="Ações" class="acao" style="width:25px;">
			<a href="javascript: executeLink('prepare.action?troca=true&historicoExtintor.id=${historicoExtintor.id}&extintor.id=${historicoExtintor.extintor.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?troca=true&historicoExtintor.id=${historicoExtintor.id}&extintor.id=${historicoExtintor.extintor.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:50px; text-align:center"/>
		<@display.column property="localizacao" title="Localização" style="width:200px;"/>
		<@display.column property="extintor.descricao" title="Extintor - Nº Cilindro" style="width:200px;"/>
		<@display.column property="estabelecimento.nome" title="Estabelecimento" style="width:200px;"/>
	</@display.table>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>
	
	<div class="buttonGroup">
		<button class="btnTrocaExtintores" onclick="javascript: executeLink('prepareInsertTroca.action');"></button>
	</div>
</body>
</html>