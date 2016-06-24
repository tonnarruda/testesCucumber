<html>
	<head>
		<@ww.head/>
		<#if naturezaLesao.id?exists>
			<title>Editar Natureza da Lesão</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Natureza da Lesão</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('descricao'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		    <@ww.textfield  label="Descrição"  name="naturezaLesao.descricao" id="descricao" maxLength="100" cssStyle="width: 450px;"/>
			<@ww.hidden name="naturezaLesao.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="javascript: executeLink('list.action');" class="btnCancelar"></button>
		</div>
	</body>
</html>
