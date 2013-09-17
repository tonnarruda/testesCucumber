<html>
<head>
<@ww.head/>

	<title>Alterar senha de Usuário</title>
	<#assign validarCampos="return validaFormulario('form', new Array('senha','novaSenha','confSenha'), null)"/>
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/botoes.css"/>" media="screen" type="text/css">

	<#if moduloExterno?exists && moduloExterno>
		<style type="text/css">
			.btnGravar
			{
				background-image:url(../imgs/btnGravarExterno.gif) !important;
				width: 79px !important;
			}
			.btnCancelar
			{
				background-image:url(../imgs/btnCancelarExterno.gif) !important;
				width: 89px !important;
			}
			input,textarea,select,fieldset {
				border: 1px solid #BFB6B3 !important;
			}
		</style>
	</#if>
</head>
<body>
<@ww.actionerror />

<strong>Alterar senha:</strong>
<br /><br />
<@ww.form name="form" action="updateExternoSenha.action" onsubmit="${validarCampos}" validate="true" method="POST">
	<@ww.password label="Senha atual" name="candidato.senha" id="senha" required="true"/>
	<@ww.password label="Nova senha" name="candidato.novaSenha" id="novaSenha"  required="true"/>
	<@ww.password label="Confirmação nova senha" name="candidato.confNovaSenha" id="confSenha"  required="true"/>
	<@ww.hidden name="${SESSION_CANDIDATO_ID}"/>
	<@ww.token/>
</@ww.form>


<div class="buttonGroup">
	<button  class="btnGravar" accesskey="G" onclick="${validarCampos};">
	</button>
	<button onclick="window.location='prepareListAnuncio.action'" class="btnCancelar" accesskey="V">
	</button>
</div>
	<#if msg?exists>
		<script>
			jAlert('${msg}');
		</script>
	</#if>
</body>
</html>