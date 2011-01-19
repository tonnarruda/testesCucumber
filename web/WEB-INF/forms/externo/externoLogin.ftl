<!--
  Autor: Robertson Freitas
  Requisito: RFA030
 -->
<html>
<head>
<@ww.head/>
	<title>Cadastro De Currículo</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');

		input
		{
			border: 1px solid #FFF !important;
		}
		
		#mensagemModuloExterno{
			border: 1px solid #BFB6B3 !important;
		 	margin: 0 auto;
			width: 450px;
			background-color: #E9E9E9;
			padding: 10px;
		}
		
		
	</style>

	<link rel="stylesheet" href="${request.contextPath}/externo/layout?tipo=layout" />
	<#include "../ftl/mascarasImports.ftl" />

</head>
<#assign validarCampos="return validaFormulario('form', new Array('cpfRH','senhaRH'), new Array('cpfRH'))"/>
<body>
	<@ww.actionerror />
<#if empresaId?exists>
	<@ww.form name="form" action="checaLogin.action?empresaId=${empresaId}" method="POST" onsubmit="${validarCampos}">
		<table class="loginExternoTable" cellpadding="5" style="margin: 0 auto; width: 470px">
			<tr>
				<td>
					<img border="0" style="width:128px;heigtht:111px;" src="${request.contextPath}/externo/layout?tipo=logotipo"/>
					<br><br>
					Se você não é registrado,<br>
					cadastre já seu currículo e tenha<br>
					acesso às vagas
					disponíveis em <br>
					nossa empresa.<br>
					<br>
					<span class="linkCadastro">
						<a href="prepareInsert.action?moduloExterno=true&empresaId=${empresaId}">Clique aqui para se cadastrar</a>
					</span>
				</td>
				<td>
					<table class="loginExternoForm" cellpadding="8">
					<tr>
						<td colspan="4" align="center">
							<img border="0" src="<@ww.url value="/imgs/liberar.gif"/>">&nbsp;&nbsp;<b>Identifique-se</b>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							CPF:<br>
							<@ww.textfield name="cpf" id="cpfRH" theme="simple" cssClass="mascaraCpf"/>
						</td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td>
							Senha:<br>
							<@ww.password name="senha" id="senhaRH" theme="simple"/>
						</td>
						<td></td>
					</tr>
						<td></td>
						<td>
							<button onclick="${validarCampos};" accesskey="e" class="botao">
								Entrar
							</button>
							<br><br>
							<span class="linkEsqueci">
								<a href="prepareRecuperaSenha.action?empresaId=${empresaId}" class="linkbranco">Esqueci minha senha</a>&nbsp;
							</span>
						</td>
						<td></td>
					</table>
				</td>
			</tr>
			
			<#if mensagemLogin?exists && mensagemLogin != "">
				<tr>
					<td colspan="2">
							<div id="mensagemModuloExterno">${mensagemLogin}</div>
					</td>
				</tr>
			</#if>			
		
		</table>
		<br><br>
				
	</@ww.form>

<#else>

	<h3><img align="absmiddle" border="0" src="<@ww.url value="/imgs/warning.gif"/>"> Atenção, empresa não identificada.</h3>

</#if>

	<#if msg?exists && msg != "">
		<script>
			jAlert('${msg}');
			document.getElementById('cpfRH').focus();
		</script>
	</#if>

</body>
</html>