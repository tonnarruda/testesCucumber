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
	</style>
	
	<style type="text/css">
		input
		{
			border: 1px solid #FFF !important;
		}
		.loginExternoForm
		{
			background-color: #E9E9E9;
    		border: 1px solid #000000;
		}
		.loginExternoTable {
		    border: 4px solid #E9E9E9;
		}
	</style>

	<#if empresaId?exists>
		<#assign urlServlet = "${request.contextPath}/externo/layout?empresaId=${empresaId}&tipo=" />
	<#else>
		<#assign urlServlet = "${request.contextPath}/externo/layout?tipo=" />
	</#if>

	<link rel="stylesheet" href="${urlServlet}layout" />
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
					<table cellpadding="8">
					<tr>
						<td align="center">
							<img class="loginExternoLogo" border="0" src="${urlServlet}logotipo"/>
						</td>
					</tr>
					<tr>
						<td>
							<#if mensagemLogin?exists && mensagemLogin != "">	
								${mensagemLogin}
							</#if>		
							<br><br>
							<span class="linkCadastro">
								<a href="prepareInsert.action?moduloExterno=true&empresaId=${empresaId}">Clique aqui para se cadastrar</a>
							</span>
						</td>
					</tr>
					</table>
				</td>
				<td>
					<table class="loginExternoForm" cellpadding="8">
					<tr>
						<td colspan="4" align="center">
							<img border="0" src="<@ww.url value="/imgs/bloquear.gif"/>">&nbsp;&nbsp;<b>Identifique-se</b>
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
							<button onclick="${validarCampos};" accesskey="e" class="btnEntrar , botao">
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