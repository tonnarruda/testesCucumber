<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		
		.Irregular {
		color: red !important;
		}
		
	</style>
	<title>Extintores - Inspeção</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('formBusca', null, new Array('inicio','fim'), true)"/>

	<#include "../ftl/showFilterImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ExtintorDWR.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	
	<script type="text/javascript">


		<#if extintorId?exists>
		  var extintor = "${extintorId}"
		</#if>

		var marcarExtintor = function() {
			$("#extintor option").each(function(i, item){
				 if(typeof extintor != "undefined" && item.value == extintor){
				   $(item).attr("selected",true);
				 }
			})
		}
		
		function populaExtintores()
	    {
	      var estabelecimentoId = document.getElementById("estabelecimento").value;

	      dwr.util.useLoadingMessage('Carregando...');
	      ExtintorDWR.getExtintorByEstabelecimento(estabelecimentoId, "Todos", createListExtintores);
	    }

	    function createListExtintores(data)
	    {
	      dwr.util.removeAllOptions("extintor");
	      document.getElementById("extintor").options[0] = new Option("Todos", "");
	      dwr.util.addOptions("extintor", data);
	      marcarExtintor();
	    }
	    
		$(document).ready(function(){
		
			populaExtintores();
		
		    $("#btnListaDeInspecaoExtintores").click(function(){
			    var estabelecimento = $("#estabelecimento").val();
			    var extintor = $("#extintor").val();
			    var regularidade = $("#form_regularidade").val();
				var inicio = $("#inicio").val();
				var fim = $("#fim").val();
				if(!validaDate({value:inicio})) {
					inicio = limpaCamposMascaraData(inicio);
				}
				if(!validaDate({value:fim})) {
					fim = limpaCamposMascaraData(fim);
				}
			    var host = "imprimirListaInspecaoDeExtintores.action?estabelecimentoId=" + estabelecimento + "&extintorId=" + extintor + "&regularidade=" + regularidade + "&inicio=" + inicio + "&fim=" + fim;
			    window.location = host;
			});
		});
		
		$(function() {
			
			populaExtintores();		
				
			$('#relatorioTooltipHelp').qtip({
				content: '<strong>Listagem de Inspeção de Extintores</strong><br />Será listado no relatório somente informações presentes no filtro.'
				,
				style: {
		        	 width: '100px'
		        }
			});
				
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

	<#if extintor?exists >
			<#assign tipoRegularidade = "Irregular"/>
		<#else>
			<#assign tipoRegularidade = "Regular"/>
	</#if>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../util/topFiltro.ftl" />
	
	<@ww.form name="formBusca" id="formBusca" action="list.action" onsubmit="${validarCampos}" method="POST">

		<@ww.select label="Estabelecimento" id="estabelecimento" name="estabelecimentoId" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Todos" headerKey="" onchange="javascript:populaExtintores();" cssStyle="width:240px;"/>
		<@ww.select label="Extintor" id="extintor" name="extintorId" list="extintors" listKey="id" listValue="descricao" headerValue="Todos" headerKey="" cssStyle="width:240px;"/>
		<@ww.textfield label="Localização" name="localizacao" id="localizacao" maxLength="50" cssStyle="width:240px;"/>
		<@ww.select label="Regularidade" name="regularidade" list=r"#{'0':'Todos','1':'Regulares','2':'Irregulares'}"/>		
	
		Período:<br>
		<@ww.datepicker name="inicio" id="inicio"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="fim" id="fim" value="${dateFim}" cssClass="mascaraData" />

		<@ww.hidden id="pagina" name="page"/>
		<@ww.hidden id="showFilter" name="showFilter"/>
		<input type="submit" value="" onclick="document.getElementById('pagina').value = 1;" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>
	
	<div id="legendas" align="right">
		&nbsp;&nbsp;<span style='background-color: red;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Irregular
	</div>
	<br/>

	<@display.table name="extintorInspecaos" id="extintorInspecao" class="dados">
		<@display.column title="Ações" class="acao" style="width:25px;">
			<a href="prepareUpdate.action?extintorInspecao.id=${extintorInspecao.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?extintorInspecao.id=${extintorInspecao.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="data" title="Data da Inspeção" format="{0,date,dd/MM/yyyy}" style="width:80px; text-align:center" class="${extintorInspecao.tipoDeRegularidade}"/>
		<@display.column property="extintor.localizacao" title="Localização" style="width:190px;" class="${extintorInspecao.tipoDeRegularidade}"/>
		<@display.column property="extintor.numeroCilindro" title="Cilindro" style="width:40px;" class="${extintorInspecao.tipoDeRegularidade}"/>
		<@display.column property="extintor.tipoDic" title="Tipo" style="width:180px;" class="${extintorInspecao.tipoDeRegularidade}"/>
		<@display.column title="Obs." style="text-align: center;width: 50px">
			<#if extintorInspecao.observacao?exists && extintorInspecao.observacao?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${extintorInspecao.observacao?j_string}');return false">...</span>
			</#if>
		</@display.column>
	</@display.table>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>
			
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="N"></button>
		
		<button class="btnListaDeInspecaoExtintores" id="btnListaDeInspecaoExtintores" ></button>
		<img id="relatorioTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" style="margin-left: -25px" width="16" height="16" /><br>
				
	</div>
	
</body>
</html>