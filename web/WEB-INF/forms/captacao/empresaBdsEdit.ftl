<!--
  Autor: Igo Coelho
  Data: 29/06/2006
  Requisito: RFA020
 -->
<html>
<head>
<@ww.head/>
<#if empresaBds.id?exists>
	<title>Editar Empresa BDS</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Empresa BDS</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome','email','ddd','fone'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Empresa" name="empresaBds.nome" id="nome" required="true" cssClass="inputNome" maxLength="100" liClass="liLeft"/>
		<@ww.textfield label="Contato" name="empresaBds.contato" maxLength="100" />
		<@ww.textfield label="E-mail" name="empresaBds.email" id="email" required="true" cssClass="inputNome" maxLength="100" liClass="liLeft"/>
		<@ww.textfield label="DDD" name="empresaBds.ddd" id="ddd"  required="true" liClass="liLeft" onkeypress = "return(somenteNumeros(event,''));" maxLength="2" cssStyle="width:25px;"/>
		<@ww.textfield label="Telefone" name="empresaBds.fone" id="fone"  required="true" onkeypress="return(somenteNumeros(event,''));" maxLength="8" cssStyle="width:60px;"/>
		<@ww.hidden name="empresaBds.id" />
		<@ww.hidden name="empresaBds.empresa.id" />
	<@ww.token/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>