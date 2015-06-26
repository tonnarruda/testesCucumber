<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<title>Extintores</title>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('form', null, new Array('inicio','fim'), true)"/>

	<#include "../ftl/showFilterImports.ftl" />
	<script type="text/javascript">
		$(function() { 
			<#if tipoBusca?exists>
				$('#tipo').val('${tipoBusca}');
			</#if> 
		});
		
		function pesquisar()
		{
			$('#pagina').val(1);
			$('#form').attr('action','list.action');
			$('#form').submit();
		}
		
		function imprimir()
		{ 
			$('#form').attr('action','imprimirLista.action');
			$('#form').submit();
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" onsubmit="${validarCampos}" method="POST">
		
		<@ww.select label="Tipo" id="tipo" required="true" name="tipoBusca" list=r"#{'T':'Todos','1':'AG','2':'AP','4':'CO2','3':'PQS'}" />
		<@ww.textfield label="Número do cilindro" name="numeroBusca" id="numeroBusca" cssStyle="width:50px;text-align:right;" maxLength="40" onkeypress="return(somenteNumeros(event,''));"/>
		<@ww.select label="Ativo" name="ativo" id="ativo" list=r"#{'T':'Todos','S':'Sim','N':'Não'}" />

		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" onclick="pesquisar();" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<@display.table name="extintors" id="extintor" class="dados">
		<@display.column title="Ações" class="acao" style="width:25px;">
			<a href="prepareUpdate.action?extintor.id=${extintor.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?extintor.id=${extintor.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" title="Descrição" style="width:170px;"/>
		<@display.column property="ultimoHistorico.localizacao" title="Localização" style="width:140px;"/>
		<@display.column property="numeroCilindro" title="Cilindro" style="width:10px;"/>
		<@display.column property="tipoDic" title="Tipo" style="width:160px;"/>
	</@display.table>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="N"></button>
		<button class="btnImprimir" onclick="imprimir();"></button>
	</div>
</body>
</html>