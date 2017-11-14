<html>
<head>
<@ww.head/>

	<title>Alterar senha de Usuário</title>
	<#assign validarCampos="return validaFormulario('form', new Array('senha','novaSenha','confSenha'), null)"/>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#if colaborador?exists && colaborador.id?exists>

		<@ww.form name="form" action="updateSenhaUsuario.action" onsubmit="${validarCampos}" validate="true" method="POST">
		
			<#if mensagem?exists>
				<script>
					jAlert('${mensagem}');
				</script>
			</#if>
		
			<@ww.password label="Senha atual" name="usuario.senha" id="senha" required="true"/>
			<@ww.password label="Nova senha" name="usuario.novaSenha" id="novaSenha"  required="true"/>
			<@ww.password label="Confirmação nova senha" name="usuario.confNovaSenha" id="confSenha"  required="true"/>
			<@ww.hidden name="colaborador.id" value="${colaborador.id}" />
		
		<@ww.token/>
		</@ww.form>
		
		
		<div class="buttonGroup">
			<button  class="btnGravar" accesskey="G" onclick="${validarCampos};">
			</button>
		</div>
	</#if>
</body>
</html>