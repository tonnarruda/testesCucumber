<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<#include "../ftl/mascarasImports.ftl" />
	<@ww.head/>
	<#if historicoAmbiente.id?exists>
		<title>Edição de Histórico de Ambiente - ${ambiente.nome}</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Histórico de Ambiente - ${ambiente.nome}</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>
	<#assign validarCampos="return validaFormulario('form', new Array('dataHist','descricao'), new Array('dataHist'))"/>
	<#assign empresaControlaRiscoPor><@authz.authentication operation="empresaControlaRiscoPor"/></#assign>
	

	<#if historicoAmbiente.data?exists>
			<#assign data = historicoAmbiente.data>
		<#else>
			<#assign data = "">
	</#if>
	
	<script type="text/javascript">
		$(function() {
			<#if historicoAmbiente?exists && historicoAmbiente.riscoAmbientes?exists>
				<#list historicoAmbiente.riscoAmbientes as riscoAmbiente>
					$('#check' + ${riscoAmbiente.risco.id}).attr('checked', true).parent().parent().find('input, select').attr('disabled', false);
					<#if riscoAmbiente.periodicidadeExposicao?exists>
						$('#perExposicao' + ${riscoAmbiente.risco.id}).val('${riscoAmbiente.periodicidadeExposicao}');
					</#if>
					<#if riscoAmbiente.epcEficaz>
						$('#epcEficaz' + ${riscoAmbiente.risco.id}).attr('checked', true);
					</#if>
				</#list>
			</#if>
			
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
		<@ww.datepicker label="A partir de" name="historicoAmbiente.data" id="dataHist" required="true" value="${data}" cssClass="mascaraData"/>
		<@ww.textarea label="Descrição do Ambiente" name="historicoAmbiente.descricao" id="descricao" required="true"/>
		
		<@ww.textfield label="Tempo de Exposição" name="historicoAmbiente.tempoExposicao" id="tempoExposicao" maxLength="40" style="width:100px;"/>

		<@frt.checkListBox label="EPCs existentes no Ambiente" name="epcCheck" list="epcCheckList" />
		
		<#assign i = 0/>
		<#if empresaControlaRiscoPor == 'A'> 
			Riscos existentes:<br>
			<@display.table name="riscosAmbientes" id="riscoAmbiente" class="dados" style="width:500px;border:none;">
				<@display.column title="<input type='checkbox' id='md'/>" style="width: 30px; text-align: center;">
					<input type="checkbox" id="check${riscoAmbiente.risco.id}" value="${riscoAmbiente.risco.id}" name="riscoChecks" />
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

		<@ww.hidden name="historicoAmbiente.id" />
		<@ww.hidden name="historicoAmbiente.ambiente.id" />
		<@ww.hidden name="ambiente.id" />
		
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='../ambiente/prepareUpdate.action?ambiente.id=${ambiente.id}'" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>