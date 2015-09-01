<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<#include "../ftl/mascarasImports.ftl" />
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<#if funcao.id?exists>
		<title>Editar Função - ${cargoTmp.nome}</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
		<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>
	<#else>
		<title>Inserir Função - ${cargoTmp.nome}</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
		<#assign validarCampos="return validaFormulario('form', new Array('dataHist','nome','descricao'), new Array('dataHist'))"/>
	</#if>

	<#if funcao.data?exists>
		<#assign data = historicoFuncao.data>
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


		<@ww.hidden name="cargoTmp.id" value="${cargoTmp.id}"/>
		<@ww.hidden name="funcao.id" />
		<@ww.hidden name="veioDoSESMT" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};"  class="btnGravar"></button>

	<#if funcao.id?exists && historicoFuncaos?exists>
		</div>
		<br>
		<@display.table name="historicoFuncaos" id="historicoFuncao" pagesize=10 class="dados">
			<@display.column title="Ações" class="acao">
				<a href="../historicoFuncao/prepareUpdate.action?historicoFuncao.id=${historicoFuncao.id}&funcao.id=${funcao.id}&funcao.nome=${funcao.nome}&cargoTmp.id=${cargoTmp.id}&cargoTmp.nome=${cargoTmp.nome}&veioDoSESMT=${veioDoSESMT?string}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<#if 1 < historicoFuncaos?size>
					<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='../historicoFuncao/delete.action?historicoFuncao.id=${historicoFuncao.id}&funcao.id=${funcao.id}&cargoTmp.id=${cargoTmp.id}&veioDoSESMT=${veioDoSESMT?string}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<#else>
					<a href="javascript:;"><img border="0" title="Não é possível remover o único histórico da função" src="<@ww.url value="/imgs/delete.gif"/>"  style="opacity:0.2;filter:alpha(opacity=20);"></a>
				</#if>
			</@display.column>
			<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center;width:80px;"/>
			<@display.column property="descricao" title="Histórico - Descrição"/>
		</@display.table>


		<div class="buttonGroup">
			<button onclick="window.location='../historicoFuncao/prepareInsert.action?funcao.id=${funcao.id}&funcao.nome=${funcao.nome}&cargoTmp.id=${cargoTmp.id}&cargoTmp.nome=${cargoTmp.nome}&veioDoSESMT=${veioDoSESMT?string}'" class="btnInserir"></button>
	</#if>

		<#if veioDoSESMT?exists && veioDoSESMT>
			<button onclick="window.location='listFiltro.action?cargoTmp.id=${cargoTmp.id}'" class="btnVoltar"></button>
		<#else>
			<button onclick="window.location='list.action?cargoTmp.id=${cargoTmp.id}'" class="btnVoltar"></button>
		</#if>
	</div>


</body>
</html>