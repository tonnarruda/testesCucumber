<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<#include "../ftl/mascarasImports.ftl" />
	<#if ambiente.id?exists>
		<title>Editar Ambiente</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
		<#assign validarCampos="return validaFormulario('form', new Array('estabelecimento','nome'), null)"/>
	<#else>
		<title>Inserir Ambiente</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
		<#assign validarCampos="return validaFormulario('form', new Array('estabelecimento','dataHist','nome','descricao'), new Array('dataHist'), validaRiscosExistentes())"/>
	</#if>
	
	<#if historicoAmbiente?exists && historicoAmbiente.data?exists>
		<#assign data = historicoAmbiente.data>
	<#else>
		<#assign data = "">
	</#if>
	
	<#assign empresaControlaRiscoPor><@authz.authentication operation="empresaControlaRiscoPor"/></#assign>
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
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
	
		<@ww.select label="Estabelecimento" name="ambiente.estabelecimento.id" id="estabelecimento" required="true" list="estabelecimentos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..."/>
		<@ww.textfield label="Nome" name="ambiente.nome" id="nome" cssClass="inputNome" maxLength="100" required="true" />
		
		<#if !ambiente.id?exists>
			<li>
			<fieldset>
				<ul>
					<legend>Dados do Primeiro Histórico do Ambiente</legend>
						<#include "includeHistoricoAmbiente.ftl" />
					</ul>
			</fieldset>
		</li>
			
			<script type="text/javascript">
				marcarDesmarcar(document.form.md);
			</script>
		</#if>

		<@ww.hidden name="ambiente.id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};"  class="btnGravar" accesskey="${accessKey}"></button>

	<#if ambiente.id?exists && historicoAmbientes?exists>
		</div>
		<br>
		<@display.table name="historicoAmbientes" id="historicoAmbiente" pagesize=10 class="dados">
			<@display.column title="Ações" class="acao">
				<a href="../historicoAmbiente/prepareUpdate.action?historicoAmbiente.id=${historicoAmbiente.id}&ambiente.id=${ambiente.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<#if 1 < historicoAmbientes?size>
					<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='../historicoAmbiente/delete.action?historicoAmbiente.id=${historicoAmbiente.id}&ambiente.id=${ambiente.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<#else>
					<a href="javascript:;"><img border="0" title="Não é possível remover o único histórico do ambiente" src="<@ww.url value="/imgs/delete.gif"/>"  style="opacity:0.2;filter:alpha(opacity=20);"></a>
				</#if>
			</@display.column>
			<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center;width:80px;"/>
			<@display.column property="descricao" title="Histórico - Descrição"/>
		</@display.table>

	<div class="buttonGroup">
		<button onclick="window.location='../historicoAmbiente/prepareInsert.action?ambiente.id=${ambiente.id}'" class="btnInserir" accesskey="I"></button>
	</#if>
		<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V"></button>
	</div>
</body>
</html>