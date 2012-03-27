<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
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
		<#assign validarCampos="return validaFormulario('form', new Array('estabelecimento','dataHist','nome','descricao'), new Array('dataHist'))"/>
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
			$(campoRisco).parent().parent().find('input, select').not(campoRisco).attr('disabled', !campoRisco.checked);
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
					<@ww.datepicker label="A partir de" name="historicoAmbiente.data" id="dataHist" required="true" cssClass="mascaraData"/>
					<@ww.textfield label="Tempo de Exposição" name="historicoAmbiente.tempoExposicao" id="tempoExposicao" maxLength="40" style="width:100px;"/>
					<@ww.textarea label="Descrição do Ambiente" name="historicoAmbiente.descricao" id="descricao" required="true"/>
					<@frt.checkListBox label="EPCs existentes no Ambiente" name="epcCheck" list="epcCheckList" />
					
					<#assign i = 0/>
					<#if empresaControlaRiscoPor == 'A'> 
						<@display.table name="riscosAmbientes" id="riscoAmbiente" class="dados" style="width:500px;border:none;">
							<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(this);' />" style="width: 30px; text-align: center;">
								<input type="checkbox" onclick="desabilitaEpcEficaz(this);" id="check${riscoAmbiente.risco.id}" value="${riscoAmbiente.risco.id}" name="riscoChecks" />
							</@display.column>
							<@display.column property="risco.descricao" title="Risco" style="width: 240px;"/>
							<@display.column property="risco.descricaoGrupoRisco" title="Tipo" style="width: 240px;"/>
							<@display.column title="EPI Eficaz" style="width: 140px;text-align:center;">
								<#if riscoAmbiente.risco.epiEficaz == true> 
								Sim
								<#else>
								NA
								</#if>
							</@display.column>
							<@display.column title="Periodicidade" style="text-align:center;">
								<@ww.select name="riscosAmbientes[${i}].periodicidadeExposicao" id="perExposicao${riscoAmbiente.risco.id}" headerKey="" headerValue="Selecione" list=r"#{'C':'Contínua','I':'Intermitente','E':'Eventual'}" disabled="true"/>
							</@display.column>
							<@display.column title="EPC Eficaz" style="width: 140px;text-align:center;">
								<@ww.checkbox id="epcEficaz${riscoAmbiente.risco.id}" name="riscosAmbientes[${i}].epcEficaz" disabled="true"/>
								<@ww.hidden name="riscosAmbientes[${i}].risco.id"/>
							</@display.column>
							
							<#assign i = i + 1/>
						</@display.table>
					<#else>
						<#list riscosAmbientes as riscoAmbiente>
							<@ww.hidden name="riscosAmbientes[${i}].periodicidadeExposicao" />
							<@ww.hidden name="riscosAmbientes[${i}].epcEficaz" />
							<@ww.hidden name="riscosAmbientes[${i}].risco.id" />
							<#assign i = i + 1/>
						</#list>
					</#if>	
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
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='../historicoAmbiente/delete.action?historicoAmbiente.id=${historicoAmbiente.id}&ambiente.id=${ambiente.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
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