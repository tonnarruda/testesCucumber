<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
<#if historicoFuncao.id?exists>
	<title>Editar Histórico da Função - ${cargoTmp.nome} / ${funcao.nome}</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Histórico da Função - ${cargoTmp.nome} / ${funcao.nome}</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#if historicoFuncao.data?exists>
		<#assign data = historicoFuncao.data>
	<#else>
		<#assign data = "">
</#if>
	<#include "../ftl/mascarasImports.ftl" />

<#assign validarCampos="return validaFormulario('form', new Array('dataHist','descricao'), new Array('dataHist'))"/>
<#assign empresaControlaRiscoPor><@authz.authentication operation="empresaControlaRiscoPor"/></#assign>

<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>

<script type="text/javascript">
	$(function() {
		<#if historicoFuncao?exists && historicoFuncao.riscoFuncaos?exists>
			<#list historicoFuncao.riscoFuncaos as riscoFuncao>
				$('#check' + ${riscoFuncao.risco.id}).attr('checked', true).parent().parent().find('input, select').attr('disabled', false);
				<#if riscoFuncao.periodicidadeExposicao?exists>
					$('#perExposicao' + ${riscoFuncao.risco.id}).val('${riscoFuncao.periodicidadeExposicao}');
				</#if>
				<#if riscoFuncao.epcEficaz>
					$('#epcEficaz' + ${riscoFuncao.risco.id}).attr('checked', true);
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
		<@ww.datepicker label="A partir de" name="historicoFuncao.data" id="dataHist" required="true" value="${data}" cssClass="mascaraData"/>
		<@ww.textarea label="Descrição das Atividades" name="historicoFuncao.descricao" id="descricao" required="true"/>
		<@frt.checkListBox label="Exames (PCMSO)" name="examesChecked" id="exame" list="examesCheckList" />
		<@frt.checkListBox label="EPIs (PPRA)" name="episChecked" id="epi" list="episCheckList" />

		<#assign i = 0/>
		<#if empresaControlaRiscoPor == 'F'> 
			Riscos existentes:<br>
			<@display.table name="riscosFuncoes" id="riscoFuncao" class="dados" style="width:500px;border:none;">
				<@display.column title="<input type='checkbox' id='md'/>" style="width: 30px; text-align: center;">
					<input type="checkbox" id="check${riscoFuncao.risco.id}" value="${riscoFuncao.risco.id}" name="riscoChecks" />
				</@display.column>
				<@display.column property="risco.descricao" title="Risco" style="width: 240px;"/>
				<@display.column property="risco.descricaoGrupoRisco" title="Tipo" style="width: 240px;"/>
				<@display.column title="EPI Eficaz" style="width: 140px;text-align:center;">
					<#if riscoFuncao.risco.epiEficaz == true> 
						Sim
					<#else>
						NA 
					</#if>
				</@display.column>
				<@display.column title="Periodicidade" style="text-align:center;">
					<@ww.select name="riscosFuncoes[${i}].periodicidadeExposicao" id="perExposicao${riscoFuncao.risco.id}" headerKey="" headerValue="Selecione" list=r"#{'C':'Contínua','I':'Intermitente','E':'Eventual'}" disabled="true"/>
				</@display.column>
				<@display.column title="EPC Eficaz" style="width: 140px;text-align:center;">
					<@ww.checkbox id="epcEficaz${riscoFuncao.risco.id}" name="riscosFuncoes[${i}].epcEficaz" disabled="true"/>
					<@ww.hidden name="riscosFuncoes[${i}].risco.id"/>
				</@display.column>
				
				<#assign i = i + 1/>
			</@display.table>
		<#else>
			<#list riscosFuncoes as riscoFuncao>
				<@ww.hidden name="riscosFuncoes[${i}].periodicidadeExposicao" />
				<@ww.hidden name="riscosFuncoes[${i}].epcEficaz" />
				<@ww.hidden name="riscosFuncoes[${i}].risco.id" />
				<#assign i = i + 1/>
			</#list> 
		</#if>

		<@ww.hidden name="funcao.id" />
		<@ww.hidden name="historicoFuncao.id" />
		<@ww.hidden name="historicoFuncao.funcao.id" />
		<@ww.hidden name="cargoTmp.id" />
		<@ww.hidden name="veioDoSESMT" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar"></button>
		<button onclick="window.location='../funcao/prepareUpdate.action?funcao.id=${funcao.id}&cargoTmp.id=${cargoTmp.id}&veioDoSESMT=${veioDoSESMT?string}'" class="btnCancelar"></button>
	</div>
</body>
</html>