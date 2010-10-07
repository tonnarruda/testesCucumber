<html>
<head>
<@ww.head/>
	<#if prontuario.id?exists>
		<title>Editar Prontuário</title>
		<#assign formAction="update.action"/>
	<#else>
		<title>Registrar Prontuário</title>
		<#assign formAction="insert.action"/>
	</#if>
	<#if prontuario?exists && prontuario.data?exists>
		<#assign data = prontuario.data/>
	<#else>
		<#assign data = ""/>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />	
	<#assign validarCampos="return validaFormulario('form', new Array('data','descricao'), new Array('data'))"/>
	
	<script type="text/javascript">	
		function cancelar()
		{
			document.form.action = "list.action";
			document.getElementById("data").value = "";
			
			document.form.submit();
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<h4>Colaborador: ${colaboradorNome}</h4>
	
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.datepicker label="Data" id="data" name="prontuario.data" required="true" cssClass="mascaraData" value="${data}"/>
		<@ww.textarea label="Descrição" id="descricao" name="prontuario.descricao" required="true" cssStyle="width:600px;"/>
		
		<@ww.hidden name="prontuario.id" />
		<@ww.hidden name="colaborador.id" />
		<@ww.hidden name="colaborador.nome" />
		<@ww.hidden name="colaborador.pessoal.cpf" />
		<@ww.hidden name="colaborador.matricula" />
		<@ww.token/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar"></button>
		<button onclick="cancelar();" class="btnCancelar"></button>
	</div>
	
</body>
</html>