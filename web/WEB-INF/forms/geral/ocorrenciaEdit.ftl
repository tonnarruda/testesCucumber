<html>
<head>
<@ww.head/>
<#if ocorrencia.id?exists>
	<title>Editar Ocorrência</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Ocorrência</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>
<#assign validarCampos="return validaFormulario('form', new Array('descricao','pontuacao'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}"  validate="true" method="POST">	
		<@ww.textfield label="Descrição" name="ocorrencia.descricao" id="descricao" cssStyle="width: 282px;" maxLength="40" required="true"/>
		<@ww.textfield label="Pontuação" name="ocorrencia.pontuacao" id="pontuacao" maxLength="10" onkeypress="return(somenteNumeros(event,'-'));" cssStyle="width:70px;"  required="true" cssClass="pontuacao"/>

		<#if empresaIntegradaComAC>
			<@ww.checkbox label="Enviar para o Fortes Pessoal" id="integraAC" name="ocorrencia.integraAC" labelPosition="left"/>
		</#if>
		
		<@ww.checkbox label="Considerar como absenteísmo" name="ocorrencia.absenteismo"  labelPosition="left"/>
		<@ww.checkbox label="Exibir em performance profissional" name="ocorrencia.performance"  labelPosition="left"/>

		<@ww.hidden label="Id" name="ocorrencia.id" />
		<@ww.hidden name="ocorrencia.codigoAC" />

	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>