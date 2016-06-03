<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	<@ww.head/>
		<title>Reprocessar Certificações</title>
		<#assign validarCampos="return validaFormulario('form', null, null)"/>
	
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<#if empresaSistema.controlarVencimentoPorCertificacao>
			<@ww.form name="form" action="reprocessaCertificacao" onsubmit="${validarCampos}" method="POST">
	        	<h4>
	        		OBS:</br>
	       			Essa operação irá descertificar todos os colaboradores e certificá-los novamente, <br/>
	        		removendo todos os histórico de certificações dos colaboradores existentes até o momento.
	        	</h4>
	        	
	        	<@frt.checkListBox label="Certificações" name="certificacoesCheck" list="certificacoesCheckList"/>
			</@ww.form>
			<div class="buttonGroup">
				<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
				<button onclick="processando('${urlImgs}');${validarCampos};" class="btnCarregar"></button>
			</div>
		<#else>
			<h4>
	        	OBS:</br>
	       		Não é possível reprocessar certificações, pois a empresa não controlar vencimento da certificação por periodicidade da certificação. 
	        </h4>
		</#if>
	</body>
</html>