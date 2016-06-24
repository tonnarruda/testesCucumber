<html>
<head>
<@ww.head/>
<#if exame.id?exists>
	<title>Editar Exame</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Exame</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>
	<script type='text/javascript'>
		function enviaForm()
		{
			if(document.getElementById("periodico").value == "true")
				return validaFormulario('form', new Array('nome','periodicidade'), null);
			else
				return validaFormulario('form', new Array('nome'), null);
		}
	</script>
	<#assign validarCampos="enviaForm()"/>

	<#if exame?exists && exame.periodicidade?exists && 0 < exame.periodicidade>
		<#assign valuePeriodicidade=exame.periodicidade/>
	<#else>
		<#assign valuePeriodicidade=""/>
	</#if>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">

		<@ww.textfield label="Nome" id="nome" name="exame.nome" cssClass="inputNome" maxLength="100" required="true"/>
		<@ww.select label="Periódico" id="periodico" name="exame.periodico" list=r"#{true:'Sim',false:'Não'}" liClass="liLeft" onchange="habilitaCampo(this.value, 'periodicidade');"/>
		<@ww.textfield label="Periodicidade (em meses)" id="periodicidade" name="exame.periodicidade" value="${valuePeriodicidade}" onkeypress="return(somenteNumeros(event,''));" size="2" maxlength="2" required="true"/>

		<@ww.hidden name="exame.id" />
		<@ww.token/>

	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="javascript: executeLink('list.action');" class="btnCancelar" accesskey="V">
		</button>
	</div>

	<script type='text/javascript'>
		habilitaCampo(document.getElementById("periodico").value, 'periodicidade');
	</script>
</body>
</html>