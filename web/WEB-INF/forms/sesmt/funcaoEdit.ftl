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

	<title>Inserir Função</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
	<#assign validarCampos="return validaFormulario('form', new Array('dataHist','funcaoNome','descricao', 'codigoCBO'), new Array('dataHist'))"/>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="insert.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<#if funcao.id?exists>
			<@ww.hidden name="funcao.id" />
		<#else>
			<li>
				<fieldset>
					<legend>Dados do Primeiro Histórico da Função</legend>
					<ul>
						<#include "includeHistoricoFuncao.ftl" />
					</ul>
				</fieldset>
			</li>
		</#if>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};">Gravar</button>
		<button onclick="window.location='list.action'">Cancelar</button>
	</div>
</body>
</html>