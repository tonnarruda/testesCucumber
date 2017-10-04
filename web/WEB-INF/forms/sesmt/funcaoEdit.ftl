<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<#include "../ftl/mascarasImports.ftl" />
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
	    @import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	    
	    #wwgrp_descricaoCBO
	    {
			float: left;
	    	background-color: #E9E9E9;
			width: 420px;
			padding-left: 4px;
		}
	</style>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/autoCompleteFortes.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>

	<#if funcao.id?exists>
		<title>Editar Função - ${funcao.nome}</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
		<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>
	<#else>
		<title>Inserir Função</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
		<#assign validarCampos="return validaFormulario('form', new Array('dataHist','nome','descricao'), new Array('dataHist'))"/>
	</#if>

	<#if funcao.data?exists>
		<#assign data = historicoFuncao.data?date>
	<#else>
		<#assign data = "">
	</#if>

	<script type="text/javascript">
		$(function() {
			$('#md').click(function() {
				var checked = $(this).attr('checked');
				$('input[name="riscoChecks"]').each(function() { $(this).attr('checked', checked); habilitarDesabilitarCamposLinha(this); });
			});
			
			$('input[name="riscoChecks"]').click(function() {
				habilitarDesabilitarCamposLinha(this);
			});
			
			var urlFind = "<@ww.url includeParams="none" value="/geral/codigoCBO/find.action"/>";
				
			$("#descricaoCBO").autocomplete({
				source: ajaxData(urlFind),				 
				minLength: 2,
				select: function( event, ui ) { 
					$("#codigoCBO").val(ui.item.id);
				}
			}).data( "autocomplete" )._renderItem = renderData;

			$('#descricaoCBO').focus(function() {
			    $(this).select();
			});
			
		});
		
		function habilitarDesabilitarCamposLinha(campoRisco)
		{
			$(campoRisco).parent().parent().find('input, select, textarea').not(campoRisco).attr('disabled', !campoRisco.checked);
		}
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">

		<@ww.textfield label="Nome da Função" name="funcao.nome" id="nome"  cssClass="inputNome" maxLength="100" required="true" />

		<@ww.textfield label="Cód. CBO" name="funcao.codigoCbo" id="codigoCBO" onkeypress="return(somenteNumeros(event,''));" cssStyle="margin-top: 1px" size="6"  maxLength="6" liClass="liLeft"/>
		<@ww.textfield label="Busca CBO (Código ou Descrição)" name="descricaoCBO" id="descricaoCBO" cssStyle="width: 414px;"/>
		<div style="clear:both"></div>



		<#if !funcao.id?exists>
			<li>
				<fieldset>
					<legend>Dados do Primeiro Histórico da Função</legend>
					<ul>
						<#include "includeHistoricoFuncao.ftl" />
					</ul>
				</fieldset>
			</li>
		</#if>

		<@ww.hidden name="funcao.id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};"  class="btnGravar"></button>

	<#if funcao.id?exists && historicoFuncaos?exists>
		</div>
		<br>
		<@display.table name="historicoFuncaos" id="historicoFuncao" pagesize=10 class="dados">
			<@display.column title="Ações" class="acao">
				<a href="../historicoFuncao/prepareUpdate.action?historicoFuncao.id=${historicoFuncao.id}&funcao.id=${funcao.id}&funcao.nome=${funcao.nome}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<#if 1 < historicoFuncaos?size>
					<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='../historicoFuncao/delete.action?historicoFuncao.id=${historicoFuncao.id}&funcao.id=${funcao.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<#else>
					<a href="javascript:;"><img border="0" title="Não é possível remover o único histórico da função" src="<@ww.url value="/imgs/delete.gif"/>"  style="opacity:0.2;filter:alpha(opacity=20);"></a>
				</#if>
			</@display.column>
			<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center;width:80px;"/>
			<@display.column property="descricao" title="Histórico - Descrição"/>
		</@display.table>


		<div class="buttonGroup">
			<button onclick="window.location='../historicoFuncao/prepareInsert.action?funcao.id=${funcao.id}&funcao.nome=${funcao.nome}'" class="btnInserir"></button>
	</#if>

		<button onclick="window.location='list.action'" class="btnVoltar"></button>
	</div>


</body>
</html>