<html>
<head>
<@ww.head/>
	<title>Criar Usuários Automaticamente</title>

<#assign validarCampos="return validaFormulario('form', new Array('senhaPadrao'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.div cssStyle="background-color: #EEEEEE; padding:5px;">
		Esta funcionalidade cria automaticamente usuários para colaboradores que ainda não tem acesso ao Fortes RH.<br>
		O login do usuário será o CPF do Colaborador.<br>
		Os usuários serão criados com o perfil <b>${perfil.nome}</b> e a senha informada abaixo.<br>
	</@ww.div>
	<br/>
	<@ww.form name="form" action="createAuto.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Senha Padrão" name="senhaPadrao" id="senhaPadrao" cssStyle="width:102px" maxLength="15" required="true"/>
		<@ww.hidden name="perfil.id" />

	<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnCriarUsuarios" >
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" >
		</button>
	</div>
</body>
</html>