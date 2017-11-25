<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
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
	
	<#if historicoAmbiente.data?exists>
			<#assign data = historicoAmbiente.data>
		<#else>
			<#assign data = "">
	</#if>
	
	<script type="text/javascript">
		$(function() {
			<#if historicoAmbiente?exists && historicoAmbiente.riscoAmbientes?exists>
				<#list historicoAmbiente.riscoAmbientes as riscoAmbiente>
					$('#check' + ${riscoAmbiente.risco.id}).attr('checked', true).parent().parent().find('input, select, textarea').attr('disabled', false);
					<#if riscoAmbiente.grauDeRisco?exists>
						$('#grauDeRisco' + ${riscoAmbiente.risco.id}).val('${riscoAmbiente.grauDeRisco}');
					</#if>
					<#if riscoAmbiente.periodicidadeExposicao?exists>
						$('#perExposicao' + ${riscoAmbiente.risco.id}).val('${riscoAmbiente.periodicidadeExposicao}');
					</#if>
					<#if riscoAmbiente.epcEficaz>
						$('#epcEficaz' + ${riscoAmbiente.risco.id}).attr('checked', true);
					</#if>
					<#if riscoAmbiente.medidaDeSeguranca?exists>
						$('#medidaDeSeguranca' + ${riscoAmbiente.risco.id}).val('${riscoAmbiente.medidaDeSeguranca?js_string}');
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
			
			toggleEstabecimento();
		});
		
		function habilitarDesabilitarCamposLinha(campoRisco)
		{
			$(campoRisco).parent().parent().find('input, select, textarea').not(campoRisco).attr('disabled', !campoRisco.checked);
		}
		
		function submitForm(){
			if($('#localAmbiente').val() == 1){
				if(validaFormulario('form', new Array('dataHist','descricao','nome', 'estabelecimento'), new Array('dataHist'), !validaRiscosExistentes()))
					$('#form').submit();
			}
			else{
				if(validaFormulario('form', new Array('dataHist','descricao','nome', 'cnpjEstabelecimentoDeTerceiros'), new Array('dataHist', 'cnpjEstabelecimentoDeTerceiros'), !validaRiscosExistentes()))
					$('#form').submit();
			}
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" method="POST">

		<#include "includeHistoricoAmbiente.ftl" />

		<@ww.hidden name="historicoAmbiente.id" />
		<@ww.hidden name="historicoAmbiente.ambiente.id" />
		<@ww.hidden name="ambiente.id" />
		
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="submitForm();" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='../ambiente/prepareUpdate.action?ambiente.id=${ambiente.id}'" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>