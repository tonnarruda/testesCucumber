<html>
	<head>
		<@ww.head/>
		<#if avaliacaoPratica.id?exists>
			<title>Editar AvaliacaoPratica</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir AvaliacaoPratica</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('titulo','notaMinima'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="avaliacaoPratica.id" />
			<@ww.hidden name="avaliacaoPratica.empresa.id" />
			<@ww.textfield label="Título" name="avaliacaoPratica.titulo" id="titulo" cssClass="inputNome" maxLength="100" required="true" />
			<@ww.textfield id="notaMinima" label="Nota Mínima para Aprovação" name="avaliacaoPratica.notaMinima" maxLength="4" onkeypress="return(somenteNumeros(event,','));" cssStyle="width:50px;text-align:right;" required="true"/>
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
