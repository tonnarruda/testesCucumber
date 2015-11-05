<html>
<head>
	<@ww.head/>
	<title>Inserir Usuário</title>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/botoes.css?version=${versao}"/>');
	</style>
</head>
<body>
<@ww.actionerror />
<#if colaborador.usuario?exists && colaborador.usuario.id?exists>
	<h3 style="color:red">Já existe um usuário para este colaborador</h3>
<#else>
	<@ww.form name="form" action="criarUsuario.action" validate="true" method="POST">
		<@ww.hidden name="usuario.nome" value=""/>
		<@ww.textfield label="Login" name="usuario.login" required="true"/>
		<@ww.password label="Senha" name="usuario.senha" required="true"/>
		<@ww.password label="Confirme sua senha" name="confirmacao" required="true"/>
		<@ww.hidden name="colaborador.id" />
	</@ww.form>
	<div class="buttonGroup">
		<button onclick="document.form.submit();" class="btnGravar" >
		</button>
	</div>
</#if>
</body>
</html>