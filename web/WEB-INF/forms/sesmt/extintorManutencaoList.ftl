<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Extintores - Manutenção</title>

<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('form', null, new Array('inicio','fim'), true)"/>

	<#include "../ftl/showFilterImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ExtintorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>'></script>

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
	      document.getElementById("extintor").options[0] = new Option("Todos", "");
	      DWRUtil.addOptions("extintor", data);
	    }
	    
	    $(function() {
	    	$('#relatorioTooltipHelp').qtip({
				content: '<strong>Listagem de Manutenção de Extintores</strong><br />Será listado no relatório somente informações presentes no filtro.'
				,
				style: {
		        	 width: '100px'
		        }
			});
	    
		    $("#btnPesquisar").click(function(){
		    	document.getElementById('pagina').value = 1;
			    document.form.action = "list.action";
		    	validaFormularioEPeriodo('form', null, new Array('inicio','fim'), false);
			});
			
			$("#btnListaDeManutencaoExtintores").click(function(){
		    	document.form.action = "imprimirListaManutencaoDeExtintores.action";
		    	validaFormularioEPeriodo('form', null, new Array('inicio','fim'), false);
			});
			
			populaExtintores();
	    });
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
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" onsubmit="${validarCampos}" method="POST">

		<@ww.select label="Estabelecimento" id="estabelecimento" name="estabelecimentoId" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Todos" headerKey="" onchange="javascript:populaExtintores();" cssStyle="width:240px;"/>
		<@ww.select label="Extintor" id="extintor" name="extintorId" list="extintors" listKey="id" listValue="descricao" headerValue="Todos" headerKey="" cssStyle="width:240px;"/>
		<@ww.textfield label="Localização" name="localizacao" id="localizacao" maxLength="50" cssStyle="width:240px;"/>

		Período:<br>
		<@ww.datepicker name="inicio" id="inicio"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="fim" id="fim" value="${dateFim}" cssClass="mascaraData validaDataFim" />

		<@ww.checkbox label="Exibir apenas extintores sem data de retorno" name="somenteSemRetorno" labelPosition="left"/>

		<@ww.hidden id="pagina" name="page"/>
		<@ww.hidden id="showFilter" name="showFilter"/>
		<input type="submit" id="btnPesquisar" value="" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<@display.table name="extintorManutencaos" id="extintorManutencao" class="dados" defaultsort=2 sort="list">
		<@display.column title="Ações" class="acao" style="width:25px;">
			<a href="javascript: executeLink('prepareUpdate.action?extintorManutencao.id=${extintorManutencao.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('delete.action?extintorManutencao.id=${extintorManutencao.id}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="saida" title="Data Saída" format="{0,date,dd/MM/yyyy}" style="width:50px; text-align:center"/>
		<@display.column property="retorno" title="Data Retorno" format="{0,date,dd/MM/yyyy}" style="width:50px; text-align:center"/>
		<@display.column property="extintor.ultimoHistorico.localizacao" title="Localização" style="width:200px;"/>
		<@display.column property="extintor.numeroCilindro" title="Cilindro" style="width:40px;"/>
		<@display.column property="extintor.tipoDic" title="Tipo" style="width:180px;"/>
		<@display.column title="Obs." style="text-align: center;width: 50px">
			<#if extintorManutencao.observacao?exists && extintorManutencao.observacao?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${extintorManutencao.observacao?j_string}');return false">...</span>
			</#if>
		</@display.column>
	</@display.table>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');" accesskey="N"></button>
		
		<button class="btnListaDeManutencaoExtintores" id="btnListaDeManutencaoExtintores" ></button>
		<img id="relatorioTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" style="margin-left: -25px" width="16" height="16" /><br>
	</div>
</body>
</html>