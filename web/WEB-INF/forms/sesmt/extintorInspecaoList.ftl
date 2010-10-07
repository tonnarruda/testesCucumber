<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<title>Extintores - Inspeção</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('form', null, new Array('inicio','fim'), true)"/>

	<#include "../ftl/showFilterImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ExtintorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript">

		function populaExtintores()
	    {
	      var estabelecimentoId = document.getElementById("estabelecimento").value;

	      DWRUtil.useLoadingMessage('Carregando...');
	      ExtintorDWR.getExtintorByEstabelecimento(createListExtintores, estabelecimentoId, "Todos");
	    }

	    function createListExtintores(data)
	    {
	      DWRUtil.removeAllOptions("extintor");
	      DWRUtil.addOptions("extintor", data);
	    }
    </script>

    <#if inicio?exists >
		<#assign dateIni = inicio?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if fim?exists>
		<#assign dateFim = fim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>

	<#assign validarCampos="return validaFormulario('form', null, new Array('inicio','fim'), true)"/>
</head>
<body>

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" onsubmit="${validarCampos}" method="POST">

		<@ww.select label="Estabelecimento" id="estabelecimento" name="estabelecimentoId" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Todos" headerKey="" onchange="javascript:populaExtintores();" cssStyle="width:240px;"/>
		<@ww.select label="Extintor" id="extintor" name="extintorId" list="extintors" listKey="id" listValue="descricao" headerValue="Todos" headerKey="" cssStyle="width:240px;"/>

		Período:<br>
		<@ww.datepicker name="inicio" id="inicio"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="fim" id="fim" value="${dateFim}" cssClass="mascaraData" />

		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" onclick="document.getElementById('pagina').value = 1;" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<@display.table name="extintorInspecaos" id="extintorInspecao" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?extintorInspecao.id=${extintorInspecao.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?extintorInspecao.id=${extintorInspecao.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="data" title="Data da Inspeção" format="{0,date,dd/MM/yyyy}" style="width:100px;"/>
		<@display.column property="extintor.descricao" title="Extintor" style="width:280px;"/>
		<@display.column property="extintor.localizacao" title="Localização" style="width:280px;"/>
		<@display.column property="extintor.numeroCilindro" title="Cilindro" style="width:100px;"/>
		<@display.column property="extintor.tipoDicAbreviado" title="Tipo" style="width:60px;"/>
	</@display.table>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="N"></button>
	</div>
</body>
</html>