<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Análise das Etapas Seletivas</title>

	<#assign validarCampos="return validaFormulario('form', new Array('ano'))"/>	
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<@ww.form name="form" action="imprimirRelatorioProdutividade.action" onsubmit="${validarCampos}"  method="POST">
		<@ww.textfield id="ano" label="Ano" name="ano" required="true" size="4" maxLength="4" onkeypress="return(somenteNumeros(event,''));"/>
	</@ww.form>
	
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio" accesskey="I">
		</button>
	</div>
</body>
</html>