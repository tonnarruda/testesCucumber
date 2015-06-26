<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
		
		#aPartirDe{width: 80px;}
		.calendar { z-index: 99998 !important; }
		.col-data { text-align:center; width:100px; }
		#formDialog { display: none; width: 600px; text-align: center; }
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type='text/javascript'>
		function clonar(pcmatOrigemId)
		{
			$('#pcmatId').val(pcmatOrigemId);
			$('#formDialog').dialog({ modal: true, width: 200, title: 'Clonar PCMAT'});
		}
	</script>
	
	<#include "../ftl/showFilterImports.ftl" />
	<#include "../ftl/mascarasImports.ftl" />
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCamposClonar = "return validaFormulario('formModal', new Array('aPartirDe'), new Array('aPartirDe'))"/>

	<title>PCMATs da Obra - ${nomeObra}</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@display.table name="pcmats" id="pcmat" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="gerar.action?pcmat.id=${pcmat.id}&obra.id=${obra.id}"><img border="0" title="Gerar Documento" src="<@ww.url value="/imgs/ico_file_word.png"/>"></a>
			<a href="prepareUpdate.action?pcmat.id=${pcmat.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<#if ultimoPcmat?exists && ultimoPcmat.id ==  pcmat.id>
				<a href="javascript:;" onclick="javascript:clonar(${pcmat.id});"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
				<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?pcmat.id=${pcmat.id}&obra.id=${obra.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			<#else>
				<img border="0" title="Só é possível clonar o último PCMAT" src="<@ww.url value="/imgs/clonar.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Só é possível excluir o último PCMAT" src="<@ww.url value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>	
		
		</@display.column>
		<@display.column title="A partir de" property="APartirDe" format="{0,date,dd/MM/yyyy}" class="col-data"/>
		<@display.column title="Início da Obra" property="dataIniObra" format="{0,date,dd/MM/yyyy}" class="col-data"/>
		<@display.column title="Fim da Obra" property="dataFimObra" format="{0,date,dd/MM/yyyy}" class="col-data"/>
		<@display.column title="">
		</@display.column>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?pcmat.obra.id=${obra.id}'"></button>
		<button class="btnVoltar" onclick="window.location='list.action'"></button>
	</div>
	
	<div id="formDialog">
		<#if aPartirDe?exists>
			<#assign aPartirDe=aPartirDe?date />
		<#else>
			<#assign aPartirDe=""/>
		</#if>
	
		<@ww.form name="formModal" id="formModal" action="clonar.action" method="POST" onsubmit="${validarCamposClonar}">
			<@ww.datepicker label="Data do PCMAT" name="aPartirDe" id="aPartirDe" value="${aPartirDe}" required="true" cssClass="mascaraData"/>
			<@ww.hidden name="pcmat.id" id="pcmatId"/>
			<@ww.hidden name="obra.id" id="obraId"/>
			<button class="btnClonar" type="submit"></button>
		</@ww.form>
	</div>
</body>
</html>
