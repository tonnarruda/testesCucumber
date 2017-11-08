<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

<#if historicoFuncao.id?exists>
	<title>Editar Histórico</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Histórico da Função ${funcao.nome}</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#include "../ftl/mascarasImports.ftl" />
<#assign validarCampos="return validaFormulario('form', new Array('dataHist','descricao', 'codigoCBO'), new Array('dataHist'))"/>

<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	@import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
	@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
	@import url('<@ww.url value="/css/font-awesome_buttons.css"/>');
	    
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
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioAjudaESocialDWR.js?version=${versao}"/>'></script>

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
	});
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
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};">Gravar</button>
		<button onclick="window.location='../funcao/prepareUpdate.action?funcao.id=${funcao.id}'">Cancelar</button>
	</div>
</body>
</html>