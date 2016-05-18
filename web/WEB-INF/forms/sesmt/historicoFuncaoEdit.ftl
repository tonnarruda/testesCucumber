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

<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>

<script type="text/javascript">
	$(function() {
		<#if historicoFuncao?exists && historicoFuncao.riscoFuncaos?exists>
			<#list historicoFuncao.riscoFuncaos as riscoFuncao>
				$('#check' + ${riscoFuncao.risco.id}).attr('checked', true).parent().parent().find('input, select, textarea').attr('disabled', false);
				<#if riscoFuncao.periodicidadeExposicao?exists>
					$('#perExposicao' + ${riscoFuncao.risco.id}).val('${riscoFuncao.periodicidadeExposicao}');
				</#if>
				<#if riscoFuncao.epcEficaz>
					$('#epcEficaz' + ${riscoFuncao.risco.id}).attr('checked', true);
				</#if>
				<#if riscoFuncao.medidaDeSeguranca?exists>
					$('#medidaDeSeguranca' + ${riscoFuncao.risco.id}).val('${riscoFuncao.medidaDeSeguranca?js_string?replace('\'', '\'')}');
				</#if>
			</#list>
		</#if>

		$('.tooltipHelp').qtip({
			content: 'Os dados informados neste campo serão utilizados no cadastro da Ordem de Serviço.'
		});
		
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

		<#include "includeHistoricoFuncao.ftl" />
		
		<@ww.hidden name="funcao.id" />
		<@ww.hidden name="funcao.nome" />
		<@ww.hidden name="historicoFuncao.id" />
		<@ww.hidden name="historicoFuncao.funcao.id" />
		<@ww.hidden name="cargoTmp.id" />
		<@ww.hidden name="cargoTmp.nome" />
		<@ww.hidden name="veioDoSESMT" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar"></button>
		<button onclick="window.location='../funcao/prepareUpdateFromHistorico.action?funcao.id=${funcao.id}&cargoTmp.id=${cargoTmp.id}&veioDoSESMT=${veioDoSESMT?string}'" class="btnCancelar"></button>
	</div>
</body>
</html>